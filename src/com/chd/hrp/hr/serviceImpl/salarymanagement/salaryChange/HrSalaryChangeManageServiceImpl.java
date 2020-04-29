package com.chd.hrp.hr.serviceImpl.salarymanagement.salaryChange;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.salarymanagement.salaryChange.HrSalaryChangeManageMapper;
import com.chd.hrp.hr.service.base.HrBaseService;
import com.chd.hrp.hr.service.salarymanagement.salaryChange.HrSalaryChangeManageService;
import com.github.pagehelper.PageInfo;

@Service("hrSalaryChangeManageService")
public class HrSalaryChangeManageServiceImpl implements HrSalaryChangeManageService {

	private static Logger logger = Logger.getLogger(HrSalaryChangeManageServiceImpl.class);
	
	@Resource(name = "hrSalaryChangeManageMapper")
	private final HrSalaryChangeManageMapper hrSalaryChangeManageMapper = null;
	
	@Resource(name = "hrBaseService")
	private final HrBaseService hrBaseService = null;
	
	@Override
	public List<Map<String, Object>> querySalaryChangeTypeOption(
			Map<String, Object> mapVo) {
		return hrSalaryChangeManageMapper.querySalaryChangeTypeOption(mapVo);
	}

	@Override
	public String querySalaryChangeManageChangecode() {
		return hrSalaryChangeManageMapper.querySalaryChangeManageChangecode();
	}

	@Override
	public List<Map<String, Object>> querySalaryChangeTypeChangeProjectOption(
			Map<String, Object> mapVo) {
		return hrSalaryChangeManageMapper.querySalaryChangeTypeChangeProjectOption(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryValueaftOption(
			Map<String, Object> mapVo) {
		return hrSalaryChangeManageMapper.queryValueaftOption(mapVo);
	}

	@Override
	public List<Map<String, Object>> querySalaryManageEmpOption(
			Map<String, Object> mapVo) {
		return hrSalaryChangeManageMapper.querySalaryManageEmpOption(mapVo);
	}

	@Override
	public String addSalaryManage(Map<String, Object> mapVo) {
		
		try {
			
			//生成编号
			Map<String, Object> mapVos = new HashMap<String, Object>();
			mapVos.put("group_id", SessionManager.getGroupId());
			mapVos.put("hos_id", SessionManager.getHosId());
			mapVos.put("copy_code", SessionManager.getCopyCode());
			mapVos.put("bill_code", "HR_WAGE_CHANGE");
			mapVos.put("prm_perfixe", "BD");
			String change_code = hrBaseService.QueryHrBillNo(mapVos);
			mapVo.put("change_code", change_code);
			
			//添加薪资管理添加
			int addSalaryManageCount = hrSalaryChangeManageMapper.addSalaryManage(mapVo);
			if(addSalaryManageCount == 0){
				new SysException();
			}
			hrBaseService.updateAndQueryHrBillNo(mapVos);
			//取出前台传输的变动项目数组数据
			List<Map> changeList = JSONArray.parseArray(String.valueOf(mapVo.get("arrchange")), Map.class);
			if(changeList != null && changeList.size() > 0){
				//添加薪资变动项目
				int addSalaryChangeCount = hrSalaryChangeManageMapper.addSalaryChange(mapVo,changeList);
				if(addSalaryChangeCount == 0){
					new SysException();
				}
			}
			
			//取出前台传输的工资项目数组数据
			List<Map> salaryList = JSONArray.parseArray(String.valueOf(mapVo.get("arrsalary")), Map.class);
			if(salaryList != null && salaryList.size() > 0){
				//添加薪资变动项目
				int addSalaryChangeCount = hrSalaryChangeManageMapper.addSalaryProject(mapVo,salaryList);
				if(addSalaryChangeCount == 0){
					new SysException();
				}
			}
			
			//切割前台传输的ID字符串-职工多选，多个ID
			String[] idarr = mapVo.get("emp_id").toString().split(",");
			if(idarr != null && idarr.length > 0){
				int addSalaryEmpCount = hrSalaryChangeManageMapper.addSalaryEmp(mapVo,idarr);
				if(addSalaryEmpCount == 0){
					new SysException();
				}
			}else {
				new SysException();
			}
			return "{\"msg\":\"添加成功！\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String querySalaryChangeManage(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= hrSalaryChangeManageMapper.querySalaryChangeManage(mapVo);
				
				return ChdJson.toJson(list);
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= hrSalaryChangeManageMapper.querySalaryChangeManage(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public String deleteSalaryChangeManage(Map<String, Object> mapVo) {
		
		try {
			
			//转换前台传过来的Id字符串
			List<String> listId = JSONArray.parseArray(mapVo.get("arrid").toString(), String.class);
			
			if (listId != null && listId.size() > 0) {
				//删除职工数据
				mapVo.put("table", "HR_WAGE_C_EMP");
				hrSalaryChangeManageMapper.deleteSalaryChangeManageCode(mapVo,listId);
				
				//删除变动工资项数据
				mapVo.put("table", "HR_WAGE_C_WAGE");
				hrSalaryChangeManageMapper.deleteSalaryChangeManageCode(mapVo,listId);
				
				//删除变动变动项数据
				mapVo.put("table", "HR_WAGE_C_ITEM");
				hrSalaryChangeManageMapper.deleteSalaryChangeManageCode(mapVo,listId);
				
				//删除薪资变动单
				int deleteCount = hrSalaryChangeManageMapper.deleteSalaryChangeManage(mapVo,listId);
				if (deleteCount == 0) {
					new SysException();
				}
			}else{
				return "{\"errpr\":\"删除失败！\",\"state\":\"true\"}";
			}
			return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public Map<String, Object> queryUpdateSalaryChangeManage(Map<String, Object> mapVo) {
		return hrSalaryChangeManageMapper.queryUpdateSalaryChangeManage(mapVo);
	}

	@Override
	public String updateSalaryChangeManageSubmit(Map<String, Object> mapVo) {

		try {
			
			//转换前台传过来的ID字符串
			List<String> listId = JSONArray.parseArray(mapVo.get("arrid").toString(), String.class);
			
			if(listId != null && listId.size() > 0){
				int submitCount = hrSalaryChangeManageMapper.updateSalaryChangeManageSubmit(mapVo,listId);
				if(submitCount == 0){
					new SysException();
				}else if(mapVo.get("state").equals(1)){
					return "{\"msg\":\"审核成功！\",\"state\":\"true\"}";
				}
				return "{\"msg\":\"销审成功！\",\"state\":\"true\"}";
			}else {
				return "{\"error\":\"操作失败！\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
	}

	@Override
	public String queryUpdateSalaryChangeTypeSalaryProject(
			Map<String, Object> mapVo) {
		List<Map<String, Object>> list = hrSalaryChangeManageMapper.queryUpdateSalaryChangeTypeSalaryProject(mapVo);
		return ChdJson.toJson(list);
	}

	@Override
	public String updateSalaryManage(Map<String, Object> mapVo) {

		try {
			
			//判断数据被是否被审,已审核无法修改
			int updateSalaryManageCount = hrSalaryChangeManageMapper.updateSalaryManageCount(mapVo);
			if(updateSalaryManageCount != 0){
				return "{\"error\":\"已被审核的数据无法修改!！\",\"state\":\"true\"}";
			}
			
			//转换前台传过来的Id
			List<String> listId = new ArrayList<String>(new Integer(1));
			listId.add(mapVo.get("change_code").toString());
			
			//删除职工数据
			mapVo.put("table", "HR_WAGE_C_EMP");
			hrSalaryChangeManageMapper.deleteSalaryChangeManageCode(mapVo,listId);
				
			//删除变动工资项数据
			mapVo.put("table", "HR_WAGE_C_WAGE");
			hrSalaryChangeManageMapper.deleteSalaryChangeManageCode(mapVo,listId);
				
			//删除变动变动项数据
			mapVo.put("table", "HR_WAGE_C_ITEM");
			hrSalaryChangeManageMapper.deleteSalaryChangeManageCode(mapVo,listId);

			//修改薪资变动管理数据
			int updateCount = hrSalaryChangeManageMapper.updateSalaryManage(mapVo);
			if(updateCount == 0){
				new SysException();
			}
			
			//取出前台传输的变动项目数组数据
			List<Map> changeList = JSONArray.parseArray(String.valueOf(mapVo.get("arrchange")), Map.class);
			if(changeList != null && changeList.size() > 0){
				//添加薪资变动项目
				int addSalaryChangeCount = hrSalaryChangeManageMapper.addSalaryChange(mapVo,changeList);
				if(addSalaryChangeCount == 0){
					new SysException();
				}
			}
			
			//取出前台传输的工资项目数组数据
			List<Map> salaryList = JSONArray.parseArray(String.valueOf(mapVo.get("arrsalary")), Map.class);
			if(salaryList != null && salaryList.size() > 0){
				//添加薪资变动项目
				int addSalaryChangeCount = hrSalaryChangeManageMapper.addSalaryProject(mapVo,salaryList);
				if(addSalaryChangeCount == 0){
					new SysException();
				}
			}
			
			//切割前台传输的ID字符串-职工多选，多个ID
			String[] idarr = mapVo.get("emp_id").toString().split(",");
			if(idarr != null && idarr.length > 0){
				int addSalaryEmpCount = hrSalaryChangeManageMapper.addSalaryEmp(mapVo,idarr);
				if(addSalaryEmpCount == 0){
					new SysException();
				}
			}else {
				new SysException();
			}
			
			return "{\"msg\":\"修改成功！\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(),e);
		}
		
	}

	@Override
	public String queryUpdateSalaryChangeTypeSalaryManageProject(Map<String, Object> mapVo) {
		return ChdJson.toJson(hrSalaryChangeManageMapper.queryUpdateSalaryChangeTypeSalaryManageProject(mapVo));
	}
	
}
