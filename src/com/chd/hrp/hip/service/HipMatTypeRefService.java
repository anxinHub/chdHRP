/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */

package com.chd.hrp.hip.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hip.entity.HipMatTypeRef;

/**
 * @Title. @Description. <BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface HipMatTypeRefService {

	/**
	 * 查询
	 * 
	 * */
	public String queryHipMatTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * */
	public HipMatTypeRef queryHipMatTypeRefByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加
	 * 
	 * */
	public String addHipMatTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 添加
	 * 
	 * */
	public String addBatchHipMatTypeRef(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 删除
	 * 
	 * */
	public String delHipMatTypeRef(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 批量删除
	 * 
	 * */
	public String deleteBatchHipMatTypeRef(List<Map<String, Object>> entityList) throws DataAccessException;

}
