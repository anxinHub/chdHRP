/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.ass.dao.guanLiReports;


import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 资产原值变动
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */

public interface AssPriceChangeMapper extends SqlMapper{
	  
	public List<Map<String,Object>> queryBusTypes(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryBusTypesExists(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryAssPriceChangMainPrint(
			Map<String, Object> entityMap);
	
}
