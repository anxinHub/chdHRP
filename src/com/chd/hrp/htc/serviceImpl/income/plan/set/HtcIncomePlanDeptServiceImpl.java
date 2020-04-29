package com.chd.hrp.htc.serviceImpl.income.plan.set;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.htc.dao.income.plan.set.HtcIncomePlanDeptMapper;
import com.chd.hrp.htc.entity.income.plan.set.HtcIncomePlanSet;
import com.chd.hrp.htc.service.income.plan.set.HtcIncomePlanDeptService;

@Service("htcIncomePlanDeptService")
public class HtcIncomePlanDeptServiceImpl implements HtcIncomePlanDeptService{
	
	private static Logger logger = Logger.getLogger(HtcIncomePlanDeptServiceImpl.class);
	
	@Resource(name = "htcIncomePlanDeptMapper")
	private final HtcIncomePlanDeptMapper htcIncomePlanDeptMapper = null;
	
	@Override
	public String addHtcIncomePlanDept(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		try{
            
			if(null != entityMap.get("proj_dept_id") && !"".equals(entityMap.get("proj_dept_id"))){
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			
			String  proj_dept_id = entityMap.get("proj_dept_id").toString();
			
			for (String s:proj_dept_id.split(";")){
				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", entityMap.get("group_id"));
				mapVo.put("hos_id", entityMap.get("hos_id"));
				mapVo.put("copy_code", entityMap.get("copy_code"));
				mapVo.put("acc_year", entityMap.get("acc_year"));
				mapVo.put("plan_code", entityMap.get("plan_code"));
				mapVo.put("proj_dept_id", Integer.parseInt(s));
				list.add(mapVo);
			}
			
			htcIncomePlanDeptMapper.deleteBatchHtcIncomePlanDept(list);
			htcIncomePlanDeptMapper.addBatchHtcIncomePlanDept(list);
			
			  return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			  
			}else {
				
			   return "{\"error\":\"已存在的核算科室不能为空.\",\"state\":\"false\"}";
			}
			
		}catch(Exception e){
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"添加失败 数据库异常 请联系管理员! 错误编码 addHtcIncomePlanDept\"}");

		}
	}
	
	
	@Override
	public String queryHtcIncomePlanDeptByPlanSetOk(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub

		List<Map<String, Object>> list = htcIncomePlanDeptMapper.queryHtcIncomePlanDeptByPlanSetOk(entityMap);
		
		return JSON.toJSONString(list);
	}

	@Override
	public String queryHtcIncomePlanDeptByPlanSetNO(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = htcIncomePlanDeptMapper.queryHtcIncomePlanDeptByPlanSetNO(entityMap);
		
		return JSON.toJSONString(list);
	}


}
