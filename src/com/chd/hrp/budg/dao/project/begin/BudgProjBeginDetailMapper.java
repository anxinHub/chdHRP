package com.chd.hrp.budg.dao.project.begin;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgProjBegainDetail;

public interface BudgProjBeginDetailMapper  extends SqlMapper{
	
	public int queryDataExists(Map<String, Object> mapVo) throws DataAccessException;
	/*
	添加数据的时候,判断此数据已经存在
	
*/
	public BudgProjBegainDetail queryBudgProjBegainDetai(List<Map<String, Object>> entityList);
	public int updateBatchMain(List<Map<String, Object>> entityList) throws DataAccessException;
	public Map<String, Object> queryDetail(Map<String, Object> mapVo) throws DataAccessException;
}
