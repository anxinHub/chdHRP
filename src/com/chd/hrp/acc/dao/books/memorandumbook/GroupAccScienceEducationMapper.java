package com.chd.hrp.acc.dao.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccScienceEducation;

public interface GroupAccScienceEducationMapper extends SqlMapper{
	
	public List<AccScienceEducation> queryGroupAccScienceEducation(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    public List<Map<String, Object>> queryGroupAccScienceEducationDataMiningbalsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccScienceEducationDataMiningbalot(Map<String,Object> entityMap) throws DataAccessException;
	
    public List<Map<String, Object>> queryGroupAccScienceEducationDataMiningmatchsr(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryGroupAccScienceEducationDataMiningmatchot(Map<String,Object> entityMap) throws DataAccessException;

	//科目项目备查簿   打印
	public List<Map<String, Object>> queryGroupAccScienceEducationPrint(Map<String,Object> entityMap) throws DataAccessException;
}
