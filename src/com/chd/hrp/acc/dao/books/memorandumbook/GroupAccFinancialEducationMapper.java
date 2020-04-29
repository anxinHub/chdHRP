package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccFinancialEducation;
import com.chd.hrp.acc.entity.AccScienceEducation;

public interface GroupAccFinancialEducationMapper extends SqlMapper{
	
	public List<AccFinancialEducation> queryGroupAccFinancialEducation(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccFinancialEducation> queryGroupAccFinancialEducation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	public List<Map<String, Object>> queryGroupAccFinancialEducationDataMiningbalsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccFinancialEducationDataMiningbalot(Map<String,Object> entityMap) throws DataAccessException;
	
	
    public List<Map<String, Object>> queryGroupAccFinancialEducationDataMiningmatchsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccFinancialEducationDataMiningmatchot(Map<String,Object> entityMap) throws DataAccessException;
}
