package com.chd.hrp.budg.serviceImpl.base.costdutydept;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.budg.dao.base.costdutydept.BudgCostDutyDeptMapper;
import com.chd.hrp.budg.service.base.costdutydept.BudgCostDutyDeptService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 支出预算归口设置 
 * @Table: BUDG_COST_DUTY_DEPT
 * @Author: slient
 * @email: slient@e-tonggroup.com
 * @Version: 1.0
 */
@Service("budgCostDutyDeptService")
public class BudgCostDutyDeptServiceImpl implements BudgCostDutyDeptService {
	private static Logger logger = Logger.getLogger(BudgCostDutyDeptServiceImpl.class);
	
	//引入DAO操作
	@Resource(name = "budgCostDutyDeptMapper")
	private final BudgCostDutyDeptMapper budgCostDutyDeptMapper = null;
	
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgCostDutyDeptMapper.addBatch(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgCostDutyDeptMapper.updateBatch(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgCostDutyDeptMapper.deleteBatch(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String dept_id : String.valueOf(entityMap.get("paramVo")).split(",")) {

			Map<String, Object> map = new HashMap<String, Object>();


			// 表的主键
			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));
			map.put("budg_year", entityMap.get("budg_year"));
			map.put("duty_dept_id", entityMap.get("duty_dept_id"));
			map.put("subj_code", entityMap.get("subj_code"));
			map.put("dept_id", dept_id);

			listVo.add(map);

		}
		
//		List<Map<String, Object>> budgCostDutyDeptList = (List<Map<String, Object>>) budgCostDutyDeptService.queryExists(mapVo);
//		if(budgCostDutyDeptList !=null && budgCostDutyDeptList.size()>0){
//			//return JSONObject.parseObject(budgCostDutyDeptService.updateBatch(listVo));
//			budgCostDutyDeptService.deleteBatch(budgCostDutyDeptList);
//		}
		try {
			budgCostDutyDeptMapper.delete(entityMap);
			budgCostDutyDeptMapper.addBatch(listVo);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();
		
		sysPage = (SysPage) entityMap.get("sysPage");
		
		if (sysPage.getTotal()==-1){
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgCostDutyDeptMapper.query(entityMap);
			return ChdJson.toJson(list);
		}else{
			
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>)budgCostDutyDeptMapper.query(entityMap, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
			
		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgCostDutyDeptMapper.queryByCode(entityMap);
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgCostDutyDeptMapper.queryExists(entityMap);
	}

	@Override
	public String copyBudgCostDutyDept(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgCostDutyDeptMapper.copyBudgCostDutyDept(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public int queryCostSubjByCode(Map<String, Object> mapVo) throws DataAccessException {
		return budgCostDutyDeptMapper.queryCostSubjByCode(mapVo);
	}
	
	@Override
	public String queryAccDeptAttr(Map<String, Object> mapVo) throws DataAccessException {
		return JSON.toJSONString(budgCostDutyDeptMapper.queryAccDeptAttr(mapVo));
	}
	
	@Override
	public Map<String, Object> queryDeptExist(Map<String, Object> mapVo) throws DataAccessException {
		return budgCostDutyDeptMapper.queryDeptExist(mapVo);
	}
	
	@Override
	public Map<String, Object> queryAccDeptAttrExit(Map<String, Object> mapVo) throws DataAccessException {
		return budgCostDutyDeptMapper.queryAccDeptAttrExit(mapVo);
	}
	
	
	@Override
	public String queryAccDeptAttrData(Map<String, Object> entityMap) throws DataAccessException {
		try {
			List<Map<String, Object>> list = budgCostDutyDeptMapper.queryAccDeptAttrData(entityMap);
			Map<String,Object> prarmMap = new HashMap<String, Object>();
			for (Map<String, Object> map : list) {
				//是否被选中
				prarmMap.put("group_id", entityMap.get("group_id"));
				prarmMap.put("hos_id", entityMap.get("hos_id"));
				prarmMap.put("copy_code", entityMap.get("copy_code"));
				prarmMap.put("budg_year", entityMap.get("budg_year"));
				prarmMap.put("subj_code", entityMap.get("subj_code"));
				prarmMap.put("dept_id", map.get("dept_id"));
				Map<String,Object> obj = budgCostDutyDeptMapper.queryByCode(prarmMap);
				if(null != obj && obj.size()>0){
					map.put("isDisabled", true);
					//是否被禁用
					if(entityMap.get("duty_dept_id") != null && 
							entityMap.get("duty_dept_id").toString().equals(obj.get("duty_dept_id").toString())){
						map.put("isChecked", true);
						map.put("isDisabled", false);
					}
				}
				
			}
			return ChdJson.toJson(list);
		} catch (Exception e) {
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}

}
