/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Title. @Description. 账龄区间表<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HisAccPreService {

	/**
	 * 住院
	 * 
	 * */
	public String queryHisAccPreI(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisAccPreIPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 住院
	 * 
	 * */
	public String queryHisAccPreDetailI(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisAccPreDetailIPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 门诊
	 * 
	 * */
	public String queryHisAccPreO(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisAccPreOPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 门诊
	 * 
	 * */
	public String queryHisAccPreDetailO(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisAccPreDetailOPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public int queryHisAccType(Map<String, Object> entityMap) throws DataAccessException;

}
