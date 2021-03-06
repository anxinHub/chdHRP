/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.service.report;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 
 * @Description: 临床科室质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmClinicDeptReportService {


	/**
	 * @Description 查询报表
	 * @param entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public String queryPrmClinicDeptReport(Map<String, Object> entityMap) throws DataAccessException;

}
