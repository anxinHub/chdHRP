package com.chd.hrp.budg.dao.control;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface BudgControlPlanMapper extends SqlMapper{

	public List<Map<String,Object>> queryControlPlan(Map<String,Object> mapVo);
		
	public List<Map<String,Object>> queryControlDetail(Map<String,Object> mapVo);
	
	
}
