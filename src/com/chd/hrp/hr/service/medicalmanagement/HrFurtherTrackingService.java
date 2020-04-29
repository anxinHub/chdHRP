package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrFurtherTrackingService {

	String query(Map<String, Object> page)throws DataAccessException;
	 /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryFurtherTrackingByPrint(Map<String, Object> page)throws DataAccessException;
	
}
