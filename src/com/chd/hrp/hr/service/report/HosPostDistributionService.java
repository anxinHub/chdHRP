package com.chd.hrp.hr.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 岗位分布表
 * @author Administrator
 *
 */
public interface HosPostDistributionService {

	String queryHrPostDistribution(Map<String, Object> page)throws DataAccessException;
	 /**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
List<Map<String,Object>> queryPostDistributionByPrint(Map<String, Object> page)throws DataAccessException;

}
