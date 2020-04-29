/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.autovouch.HisAccMedVen;

/**
 * @Title. @Description
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HisAccMedVenService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HisAccMedVen queryHisAccMedVenByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String updateHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String addHisAccMedVen(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String delHisAccMedVen(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 药品供应商打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHisAccMedVenPrint(Map<String, Object> entityMap) throws DataAccessException;

}
