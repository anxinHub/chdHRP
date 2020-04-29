package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccFinancialEducation;
import com.chd.hrp.acc.entity.AccScienceEducation;

public interface AccFinancialEducationMapper extends SqlMapper{
	
	public List<AccFinancialEducation> queryAccFinancialEducation(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AccFinancialEducation> queryAccFinancialEducation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	public List<Map<String, Object>> queryAccFinancialEducationDataMiningbalsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccFinancialEducationDataMiningbalot(Map<String,Object> entityMap) throws DataAccessException;
	
	
    public List<Map<String, Object>> queryAccFinancialEducationDataMiningmatchsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccFinancialEducationDataMiningmatchot(Map<String,Object> entityMap) throws DataAccessException;
}
