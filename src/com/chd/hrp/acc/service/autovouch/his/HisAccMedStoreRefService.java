/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.autovouch.HisAccMedStoreRef;

/**
 * @Title. @Description
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HisAccMedStoreRefService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHisAccMedStoreRef(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisAccMedStoreRefPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HisAccMedStoreRef queryHisAccMedStoreRefByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String updateHisAccMedStoreRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String addHisAccMedStoreRef(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String delHisAccMedStoreRef(List<Map<String, Object>> entityMap) throws DataAccessException;

}
