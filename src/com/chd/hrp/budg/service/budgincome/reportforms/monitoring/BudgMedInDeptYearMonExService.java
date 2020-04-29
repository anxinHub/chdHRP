package com.chd.hrp.budg.service.budgincome.reportforms.monitoring;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface BudgMedInDeptYearMonExService {

	public String queryBudgMedInDeptYearMonEx(Map<String, Object> page) throws DataAccessException;
	
	public List<Map<String,Object>> queryMedInDeptYearMonExPrintDate(Map<String,Object> mapVo) throws DataAccessException;

}
