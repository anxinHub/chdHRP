package com.chd.hrp.htcg.dao.analysis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PatientDrgsAnalysisMapper extends SqlMapper{
	
	
	/*
	 * 单病种成本明细表
	 */
	public List<Map<String, Object>> queryPatientDrgsAnalysisCostDetail(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/*
	 * 单病种成本表
	 */
	public List<Map<String, Object>> queryPatientDrgsAnalysisCost(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
}
