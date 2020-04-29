package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;


public interface GroupAccFinancialEducationService {
	
	public String queryGroupAccFinancialEducation(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryGroupAccFinancialEducationDataMining(Map<String,Object> entityMap) throws DataAccessException;
}
