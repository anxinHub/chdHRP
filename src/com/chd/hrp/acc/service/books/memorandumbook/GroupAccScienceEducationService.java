package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;


public interface GroupAccScienceEducationService {
	public String queryGroupAccScienceEducation(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryGroupAccScienceEducationDataMining(Map<String,Object> entityMap) throws DataAccessException;
	
	//科目项目备查簿   打印
	public List<Map<String, Object>> queryGroupAccScienceEducationPrint(Map<String,Object> entityMap) throws DataAccessException;
}
