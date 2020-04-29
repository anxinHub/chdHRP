package com.chd.hrp.hr.serviceImpl.salarymanagement.salaryChange;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.StringTool;
import com.chd.hrp.hr.dao.salarymanagement.salaryChange.HrSalaryChangeTypeMapper;
import com.chd.hrp.hr.service.salarymanagement.salaryChange.HrSalaryChangeTypeService;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.github.pagehelper.PageInfo;

@Service("hrSalaryChangeTypeService")
public class HrSalaryChangeTypeServiceImpl implements HrSalaryChangeTypeService {

	private static Logger logger = Logger.getLogger(HrSalaryChangeTypeServiceImpl.class);
	
	@Resource(name = "hrSalaryChangeTypeMapper")
	private final HrSalaryChangeTypeMapper hrSalaryChangeTypeMapper = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	@Override
	public List<Map<String, Object>> queryItemOption(
			Map<String, Object> mapVo) {
		return hrSalaryChangeTypeMapper.queryItemOption(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryPlancodeOption(
			Map<String, Object> mapVo) {
		return hrSalaryChangeTypeMapper.queryPlancodeOption(mapVo);
	}

	@Override
	public String addSalaryChangeType(Map<String, Object> mapVo) {
		
		try {

			//新增查询是否存在重复的编码
			int changeTypeCount = hrSalaryChangeTypeMapper.queryAddChangeTypeCount(mapVo);
			if(changeTypeCount > 0){
				return "{\"error\":\"输入的编码已经存在.\",\"state\":\"true\"}";
			}
			
			//新增查询是否存在重复的名称
			int changeTypeNameCount = hrSalaryChangeTypeMapper.queryAddChangeTypeNameCount(mapVo);
			if(changeTypeNameCount > 0){
				return "{\"error\":\"输入的名称已经存在.\",\"state\":\"true\"}";
			}
			
			//获取本条数据的拼音码和五笔码
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			
			//添加
			int addChangeTypeCount = hrSalaryChangeTypeMapper.addSalaryChangeType(mapVo);
			if(addChangeTypeCount != 1){
				new SysException();
			}
			
			//取出前台传输的变动项目数组数据
			List<Map> changeList = JSONArray.parseArray(String.valueOf(mapVo.get("arrchange")), Map.class);
			if(changeList != null && changeList.size() != 0){
				int addChangeCount = hrSalaryChangeTypeMapper.addChangeProject(mapVo,changeList);
				if(addChangeCount == 0){
					new SysException();
				}
			}
			
			//取出前台传输的工资项目数组数据
			List<Map> salaryList = JSONArray.parseArray(String.valueOf(mapVo.get("arrsalary")), Map.class);
			if(salaryList != null && salaryList.size() != 0){
				int addSalaryCount = hrSalaryChangeTypeMapper.addSalaryProject(mapVo,salaryList);
				if(addSalaryCount == 0){
					new SysException();
				}
			}
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String querySalaryChangeType(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= hrSalaryChangeTypeMapper.querySalaryChangeType(mapVo);
				
				return ChdJson.toJson(list);
			
		}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= hrSalaryChangeTypeMapper.querySalaryChangeType(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}

	@Override
	public String deleteSalaryChangeType(Map<String, Object> mapVo) {
		
		try {
			
			
			   //判断是否被使用
			   String reStr="";
			   Map<String, Object> map = new HashMap<String, Object>();
			   map.put("dict_code", "hr_wage_change_type".toUpperCase());
			   map.put("group_id", mapVo.get("group_id"));
			   map.put("hos_id", mapVo.get("hos_id"));
			   map.put("copy_code", mapVo.get("copy_code"));
			   map.put("dict_id_str", mapVo.get("arrid"));
			   map.put("acc_year", "");
			   map.put("p_flag", "1");
			   sysFunUtilMapper.querySysDictDelCheck(map);
			   
			   if(map.get("reNote")!=null) {
			    reStr+=map.get("reNote");
			   }
			   
			   if(reStr!=null && !reStr.equals("")){
			    return "{\"error\":\"删除失败，选择的变动类型被以下业务使用：【"+reStr.substring(0,reStr.length()-1)+"】。\",\"state\":\"false\"}";
			   }
			   
			//替换验证时需要的单引号
			mapVo.put("arrid", mapVo.get("arrid").toString().replaceAll("'", ""));
			  
			//转换前台传过来的ID字符串
			String []  arrayId = mapVo.get("arrid").toString().split(",");
			if(arrayId.length == 0){
				return "{\"msg\":\"删除失败！.\",\"state\":\"true\"}";
			}
			
			//先删除变动项目数据
			int deleteCount = hrSalaryChangeTypeMapper.deleteSalaryChangeTypeChangeProject(mapVo,arrayId);
			if(deleteCount == 0){
				new SysException();
			}
			
			//先删除工资项目数据
			int deleteCounts = hrSalaryChangeTypeMapper.deleteSalaryChangeTypeProject(mapVo,arrayId);
			if(deleteCounts == 0){
				new SysException();
			}
			
			//删除薪资变动类型数据
			int deleteSalaryChangeTypeCount = hrSalaryChangeTypeMapper.deleteSalaryChangeType(mapVo,arrayId);
			if(deleteSalaryChangeTypeCount == 0){
				new SysException();
			}

			return "{\"msg\":\"删除成功！,部分数据被薪资变动管理使用会删除失败.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
		
	}

	@Override
	public Map<String, Object> queryUpdateSalaryChangeType(
			Map<String, Object> mapVo) {
		return hrSalaryChangeTypeMapper.queryUpdateSalaryChangeType(mapVo);
	}

	@Override
	public String querySalaryChangeTypeChangeProject(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= hrSalaryChangeTypeMapper.querySalaryChangeTypeChangeProject(mapVo);
				
				return ChdJson.toJson(list);
			
		}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= hrSalaryChangeTypeMapper.querySalaryChangeTypeChangeProject(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}

	@Override
	public String querySalaryChangeTypeSalaryProject(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= hrSalaryChangeTypeMapper.querySalaryChangeTypeSalaryProject(mapVo);
				
				return ChdJson.toJson(list);
			
		}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= hrSalaryChangeTypeMapper.querySalaryChangeTypeSalaryProject(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
		}
	}

	@Override
	public String updateSalaryChangeType(Map<String, Object> mapVo) {
		
		try {
			
			//修改时先判断输入的名称是否重复
			int queryTypeNameCode = hrSalaryChangeTypeMapper.queryAddChangeTypeNameCount(mapVo);
			if (queryTypeNameCode > 0) {
				return "{\"error\":\"修改的名称已经存在.\",\"state\":\"true\"}";
			}
			
			//获取本条数据的拼音码和五笔码
			mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("type_name").toString()));
			mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("type_name").toString()));
			
			//删除明细数据否则无法修改
			hrSalaryChangeTypeMapper.deleteChangeProject(mapVo);
			hrSalaryChangeTypeMapper.deleteSalaryProject(mapVo);
			
			//修改薪资变动类型数据
			int updateSalaryChangeTypeCount = hrSalaryChangeTypeMapper.updateSalaryChangeType(mapVo);
			if (updateSalaryChangeTypeCount != 1) {
				new SysException();
			}
			
			//取出前台传输的变动项目数组数据
			List<Map> changeList = JSONArray.parseArray(String.valueOf(mapVo.get("arrchange")), Map.class);
			if(changeList != null && changeList.size() != 0){
				int addChangeCount = hrSalaryChangeTypeMapper.addChangeProject(mapVo,changeList);
				if(addChangeCount == 0){
					new SysException();
				}
			}
			
			//取出前台传输的工资项目数组数据
			List<Map> salaryList = JSONArray.parseArray(String.valueOf(mapVo.get("arrsalary")), Map.class);
			if(salaryList != null && salaryList.size() != 0){
				int addSalaryCount = hrSalaryChangeTypeMapper.addSalaryProject(mapVo,salaryList);
				if(addSalaryCount == 0){
					new SysException();
				}
			}
			
			return "{\"msg\":\"修改成功！\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

}
