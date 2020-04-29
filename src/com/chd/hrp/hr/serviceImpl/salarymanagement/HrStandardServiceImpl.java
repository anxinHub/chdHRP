package com.chd.hrp.hr.serviceImpl.salarymanagement;

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
import com.chd.hrp.hr.dao.salarymanagement.HrStandardMapper;
import com.chd.hrp.hr.service.salarymanagement.HrStandardService;
import com.github.pagehelper.PageInfo;

@Service("hrStandardService")
public class HrStandardServiceImpl implements HrStandardService {

	private static Logger logger = Logger.getLogger(HrStandardServiceImpl.class);
	
	@Resource(name = "hrStandardMapper")
	private HrStandardMapper hrStandardMapper = null;
	
	@Override
	public List<Map<String, Object>> queryStandardTabCodeOption(Map<String, Object> mapVo) {
		return hrStandardMapper.queryStandardTabCodeOption(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryStandardColCodeOption(Map<String, Object> mapVo) {
		return hrStandardMapper.queryStandardColCodeOption(mapVo);
	}

	@Override
	public String addStandard(Map<String, Object> mapVo) {
		
		try {
			
			List<Map> list = JSONArray.parseArray(String.valueOf(mapVo.get("arrVo")),
					Map.class);
			//验证数据是否大于八条
			if(list.size() > 8){
				return "{\"error\":\"添加失败,人员限定条件最多只能填写八条！\",\"state\":\"false\"}";
			}
			//排序字段
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("column_id", i+1);
			}
			
			//添加启用数据时-判断是否有启用状态的重复数据
			if(mapVo.get("state").toString().equals("1")){
				int submitState = hrStandardMapper.queryStandardStateCount(mapVo);
				if(submitState == 1){
					return "{\"error\":\"添加失败,存在重复的启用数据！\",\"state\":\"false\"}";
				}
			}
			
			mapVo.put("stan_id", hrStandardMapper.queryStanId());
			
			int standardCount = hrStandardMapper.addStandard(mapVo);
			if(standardCount == 0){
				new SysException();
			}
			
			if(list == null || list.size() == 0){
				return "{\"msgs\":\"添加成功！\",\"id\":"+mapVo.get("stan_id")+"}";
			}
			
			int countsl = hrStandardMapper.addStandardCdt(mapVo,list);
			if(countsl == 0){
				new SysException();
			}
			return "{\"msgs\":\"添加成功！\",\"id\":"+mapVo.get("stan_id")+"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryStandard(Map<String, Object> mapVo) {
		
		SysPage sysPage = new SysPage();
		 
		 sysPage = (SysPage) mapVo.get("sysPage");
		 
		 if (sysPage.getTotal()==-1){
				
				List<Map<String, Object>> list= hrStandardMapper.queryStandard(mapVo);
				
				return ChdJson.toJson(list);
			
			}else{
				
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String, Object>> list= hrStandardMapper.queryStandard(mapVo,rowBounds);
				
		        PageInfo page = new PageInfo(list);
				
				return ChdJson.toJson(list, page.getTotal());
				
			}
	}

	@Override
	public String deleteStandard(Map<String, Object> mapVo) {
		
		try {
			
			List<Integer> list = JSONArray.parseArray(String.valueOf(mapVo.get("arrid")),
					Integer.class);
			
			//首先删除明细数据
			int deleteweihu = hrStandardMapper.deleteweihu(mapVo,list);
			
			//删除薪资标准相关条件
			int standardDeleteCount = hrStandardMapper.deleteStandardCond(mapVo,list);
			
			//删除薪资标准
			int deleteStandardCount = hrStandardMapper.deleteStandard(mapVo,list);
			if(deleteStandardCount == 0){
				new SysException();
			}
			return "{\"msg\":\"删除成功！\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String qiyongStandard(Map<String, Object> mapVo) {

		try {
			
			List<Integer> list = JSONArray.parseArray(String.valueOf(mapVo.get("arrid")),
					Integer.class);
			
			int qidongStandardCount = hrStandardMapper.qiyongStandard(mapVo,list);
			if(qidongStandardCount == 0){
				new SysException();
			}
			
			if((int)mapVo.get("state") == 2){
				return "{\"msg\":\"启用成功！.\",\"state\":\"true\"}";
			}else{
				return "{\"msg\":\"停用成功！.\",\"state\":\"true\"}";
			}
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public Map<String, Object> queryStandardUpdate(Map<String, Object> mapVo) {
		
		//修改查询回显
		Map<String,Object> map = hrStandardMapper.queryStandardUpdate(mapVo);
		
		//查询是否存在维护数据话-是否允许修改明细数据
		int count = hrStandardMapper.querStandardUpdateCode(mapVo);
		
		map.put("state", count);
		
		return map;
	}

	@Override
	public String updateStandard(Map<String, Object> mapVo) {
		
		try {
			
			//判断人员限定条件是否大于8条
			List<Map> list = JSONArray.parseArray(String.valueOf(mapVo.get("arrVo")),
					Map.class);
			if(list.size() > 8){
				return "{\"error\":\"添加失败,人员限定条件最多只能填写八条！\",\"state\":\"false\"}";
			}
			//排序字段
			for (int i = 0; i < list.size(); i++) {
				list.get(i).put("column_id", i+1);
			}
			
			
			if(mapVo.get("state").toString().equals("1")){
				int submitState = hrStandardMapper.queryStandardStateCount(mapVo);
				if(submitState == 1){
					return "{\"error\":\"添加失败,存在重复的启用数据！\",\"state\":\"true\"}";
				}
			}
			
			hrStandardMapper.deleteStandardCdt(mapVo);

			int countsl = hrStandardMapper.updateStandard(mapVo);
			if (countsl == 0) {
				new SysException();
			}
			

			if(list == null || list.size() == 0){
				return "{\"msgs\":\"修改成功.\",\"id\":"+mapVo.get("stan_id")+"}";
			}
			
			int cunt = hrStandardMapper.addStandardCdt(mapVo, list);
			if (cunt == 0) {
				new SysException();
			}
			
			return "{\"msgs\":\"修改成功.\",\"id\":"+mapVo.get("stan_id")+"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryStandardCtd(Map<String, Object> mapVo) {
		return ChdJson.toJson(hrStandardMapper.queryStandardCtd(mapVo));
	}

	@Override
	public String addCopyStandard(Map<String, Object> mapVo) {
		
		try {
			
			//首先验证当前编码或者名称是否存在重复-(验证启用状态)
			int stateCount = hrStandardMapper.queryCopyStateStandardCount(mapVo);
			if(stateCount != 0){
				return "{\"error\":\"存在已启用的薪资标准表编码或名称!.\",\"state\":\"false\"}";
			}
			
			//查询需要复制的限定条件
			List<Map<String,Object>> list = hrStandardMapper.queryStandardCtd(mapVo);
			
			//查询需要复制的明细数据
			List<Map> listm = hrStandardMapper.queryStandardMaintainList(mapVo);
			
			mapVo.put("stan_id", hrStandardMapper.queryStanId());
			
			//复制薪资标准信息
			int addCount = hrStandardMapper.addCopyStandard(mapVo);
			if(addCount == 0){
				new SysException();
			}
			
			//判断是否有明细数据
			if(list == null || list.size() == 0){
				return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
			}
			
			//新增明细
			int addCountCtd = hrStandardMapper.addCopyStandardCdt(mapVo, list);
			if(addCountCtd == 0){
				new SysException();
			}
			
			//判断是否有明细-维护数据
			if(listm == null || listm.size() == 0){
				return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
			}
			
			//新增明细-维护数据
			int addCountm = hrStandardMapper.addMaintain(mapVo, listm);
			if(addCountm == 0){
				new SysException();
			}
			
			return "{\"msg\":\"复制成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String,Object>> queryStandardRankodeOption(Map<String, Object> mapVo) {
		return hrStandardMapper.queryStandardRankodeOption(mapVo);
	}

	@Override
	public List<Map<String,Object>> queryStandardMaintain(Map<String, Object> mapVo) {
		return hrStandardMapper.queryStandardMaintain(mapVo);
	}

	@Override
	public String addMaintain(Map<String, Object> mapVo) {
		
		try {
			
			//维护数据，先删除所有数据然后在新增
			int deleteCount = hrStandardMapper.deleteMaintain(mapVo);
			
			List<Map> list = JSONArray.parseArray(String.valueOf(mapVo.get("arr")), Map.class);
			
			if (list == null || list.size() == 0) {
				return "{\"msg\":\"保存成功!.\",\"state\":\"true\"}";
			}
			
			int addCount = hrStandardMapper.addMaintain(mapVo,list);
			if (addCount == 0) {
				return "{\"error\":\"保存失败,请重试.\",\"state\":\"true\"}";
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryMaintain(Map<String, Object> mapVo) {
		List<Map<String, Object>> list= hrStandardMapper.queryMaintain(mapVo);
		return ChdJson.toJson(list);
	}

	@Override
	public List<Map<String, Object>> queryStandardTabCodeOptionEditable(Map<String, Object> mapVo) {
		return hrStandardMapper.queryStandardTabCodeOptionEditable(mapVo);
	}

	@Override
	public List<Map<String, Object>> queryStandardColCodeOptionEditable(Map<String, Object> mapVo) {
		return hrStandardMapper.queryStandardColCodeOptionEditable(mapVo);
	}

}
