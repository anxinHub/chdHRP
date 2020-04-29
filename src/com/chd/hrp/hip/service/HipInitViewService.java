/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HipInitView;

/**
 * @Title. @Description.<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HipInitViewService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHipInitView(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HipInitView queryHipInitViewByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String updateHipInitView(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String addHipInitView(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 更改
	 * 
	 * */
	public String delHipInitView(Map<String, Object> entityMap) throws DataAccessException;

}
