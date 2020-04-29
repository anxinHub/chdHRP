package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * 年度在职教育培训
 * @author Administrator
 *
 */
public interface HrInserviceStatisticsService {
	/**
	 * 查询年度在职教育
	 * @param page
	 * @return
	 */
	String queryInserviceStatistics(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryByPrint(Map<String, Object> page)throws DataAccessException;
	
}
