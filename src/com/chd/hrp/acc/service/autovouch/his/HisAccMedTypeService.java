/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.acc.service.autovouch.his;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.autovouch.HisAccMedType;

/**
 * @Title. @Description
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HisAccMedTypeService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHisAccMedType(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryHisAccMedTypePrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HisAccMedType queryHisAccMedTypeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String updateHisAccMedType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String addHisAccMedType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String delHisAccMedType(List<Map<String, Object>> entityMap) throws DataAccessException;

}
