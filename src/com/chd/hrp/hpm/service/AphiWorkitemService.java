package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiWorkitem;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiWorkitemService {

	/**
	 * 
	 */
	public String addWorkitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchWorkitem(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryWorkitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiWorkitem queryWorkitemByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteWorkitem(Map<String, Object> mapVo, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateWorkitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	public String hpmWorkitemImport(Map<String, Object> map)throws DataAccessException;
}
