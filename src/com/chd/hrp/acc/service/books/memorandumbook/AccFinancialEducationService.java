package com.chd.hrp.acc.service.books.memorandumbook;

import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;


public interface AccFinancialEducationService {
	
	public String queryAccFinancialEducation(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccFinancialEducationDataMining(Map<String,Object> entityMap) throws DataAccessException;
}
