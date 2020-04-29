package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiWorkitemConf;

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

public interface AphiWorkitemConfService {

	/**
	 * 
	 */
	public String addWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchWorkitemConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiWorkitemConf queryWorkitemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteWorkitemConf(Map<String, Object> mapVo, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String copyWorkitemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 */
	public String importHpmWorkitemConf(Map<String, Object> entityMap)throws DataAccessException;
}
