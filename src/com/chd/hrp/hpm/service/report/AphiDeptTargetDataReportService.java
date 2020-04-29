package com.chd.hrp.hpm.service.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiDeptTargetDataReportService {

	/**
	 * 
	 */
	public String queryDeptTargetDataReport(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryDeptTargetDataReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
}
