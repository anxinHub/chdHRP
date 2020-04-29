package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmpBonusCalculation;

public interface AphiEmpBonusCalculationMapper extends SqlMapper {

	/**
	 * 
	 */
	public List<Map<String,Object>> queryEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public List<Map<String,Object>> queryEmpBonusData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryEmpBonusDataPrint(Map<String, Object> entityMap) throws DataAccessException;

}
