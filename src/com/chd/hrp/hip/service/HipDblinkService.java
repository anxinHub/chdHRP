/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HipDblink;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HipDblinkService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHipDblink(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HipDblink queryHipDblinkByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String updateHipDblink(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String addHipDblink(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String delHipDblink(Map<String, Object> entityMap) throws DataAccessException;

}
