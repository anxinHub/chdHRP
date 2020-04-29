package com.chd.hrp.ass.service.payablequery;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssPayableQueryService{
	String queryGoodsArrivedInvoiceNot(Map<String,Object> entityMap)throws DataAccessException;
	
	String queryPaySearch(Map<String,Object> entityMap)throws DataAccessException;
	
	String queryPayCardSearch(Map<String,Object> entityMap)throws DataAccessException;
	
	String queryPayVenSearch(Map<String,Object> entityMap)throws DataAccessException;
}
