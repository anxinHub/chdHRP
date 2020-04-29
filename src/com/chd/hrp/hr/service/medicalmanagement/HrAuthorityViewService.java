package com.chd.hrp.hr.service.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
/**
 * 药品权限查看
 * @author Administrator
 *
 */
public interface HrAuthorityViewService {

	String queryHrAuthorityView(Map<String, Object> page)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAuthorityViewByPrint(Map<String, Object> page)throws DataAccessException;
}
