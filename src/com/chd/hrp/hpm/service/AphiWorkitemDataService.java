package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiWorkitemConf;
import com.chd.hrp.hpm.entity.AphiWorkitemData;

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

public interface AphiWorkitemDataService {

	/**
	 * 
	 */
	public String addWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchWorkitemData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitemConf> getWorkitemConfSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryWorkitemData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryWorkitemDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiWorkitemData queryWorkitemDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteWorkitemData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateWorkitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getWorkitemTargetValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入跳转
	 * @param mapVo
	 * @return
	 */
	public String importHpmWorkitemData(Map<String, Object> entityMap) throws DataAccessException;
}
