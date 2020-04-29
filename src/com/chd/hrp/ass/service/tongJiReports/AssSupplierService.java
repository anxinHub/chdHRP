package com.chd.hrp.ass.service.tongJiReports;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssSupplierService {    

	public String queryAssSupplier(Map<String, Object> page) throws DataAccessException;

	List<Map<String, Object>> queryAssSupplierPrint(
			Map<String, Object> entityMap) throws DataAccessException;

	

}
