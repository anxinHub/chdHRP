package com.chd.hrp.ass.service.biandongdept;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
public interface AssBianDongDeptMainService {
	String queryAssBianDongDept(Map<String, Object> entityMap)throws DataAccessException;
	/**
	  * 打印
	  */
	public List<Map<String, Object>> queryAssBianDongDeptPrint(Map<String, Object> entityMap) throws DataAccessException;
}
