package com.chd.hrp.budg.dao.control;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface BudgControlBorrowMapper  extends SqlMapper{

	public Double queryBudgValue(Map<String,Object> mapVo);
		
	public List<Map<String,Object>>  queryUserDataValue(Map<String,Object> mapVo);
		
	public Double  queryExeDataValue(Map<String,Object> mapVo);
}
