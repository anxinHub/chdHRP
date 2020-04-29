package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccEducation;

public interface AccEducationMapper extends SqlMapper{
	
	public List<AccEducation> queryAccEducation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//项目备查簿  东阳专用
	public List<AccEducation> queryAccEducationBySplit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEducationBySplit(Map<String,Object> entityMap) throws DataAccessException;

    public List<Map<String, Object>> queryAccEducationDataMiningbalsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEducationDataMiningbalot(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryAccEducationDataMiningmatchsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEducationDataMiningmatchot(Map<String,Object> entityMap) throws DataAccessException;

	//科目项目备查簿   打印
	public List<Map<String, Object>> queryAccEducationPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccEducationBySplitPrint(Map<String,Object> entityMap) throws DataAccessException;
}
