package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccScienceEducation;

public interface AccScienceEducationMapper extends SqlMapper{
	
	public List<AccScienceEducation> queryAccScienceEducation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    public List<Map<String, Object>> queryAccScienceEducationDataMiningbalsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccScienceEducationDataMiningbalot(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryAccScienceEducationDataMiningmatchsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccScienceEducationDataMiningmatchot(Map<String,Object> entityMap) throws DataAccessException;

	//科目项目备查簿   打印
	public List<Map<String, Object>> queryAccScienceEducationPrint(Map<String,Object> entityMap) throws DataAccessException;
}
