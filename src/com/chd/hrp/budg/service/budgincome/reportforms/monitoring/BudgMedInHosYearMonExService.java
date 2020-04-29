package com.chd.hrp.budg.service.budgincome.reportforms.monitoring;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface BudgMedInHosYearMonExService  {

	public String queryBudgMedInHosYearMonEx(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 医院医疗收入预算执行 报表打印
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMedInHosYearMonExPrintDate(Map<String,Object> mapVo) throws DataAccessException;
	
}
 