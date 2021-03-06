/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.prm.dao.report;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:行政后勤质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface PrmLogisticsReportMapper extends SqlMapper {

	/**
	 * @Description 查询结果集
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPrmLogisticsReport(Map<String, Object> entityMap) throws DataAccessException;

}
