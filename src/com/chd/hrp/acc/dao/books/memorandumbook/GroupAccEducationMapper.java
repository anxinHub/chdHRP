package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccEducation;

public interface GroupAccEducationMapper extends SqlMapper{
	
	public List<AccEducation> queryGroupAccEducation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	//项目备查簿  东阳专用
	public List<AccEducation> queryGroupAccEducationBySplit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccEducationBySplit(Map<String,Object> entityMap) throws DataAccessException;

    public List<Map<String, Object>> queryGroupAccEducationDataMiningbalsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccEducationDataMiningbalot(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryGroupAccEducationDataMiningmatchsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccEducationDataMiningmatchot(Map<String,Object> entityMap) throws DataAccessException;

	//科目项目备查簿   打印
	public List<Map<String, Object>> queryGroupAccEducationPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccEducationBySplitPrint(Map<String,Object> entityMap) throws DataAccessException;
}
