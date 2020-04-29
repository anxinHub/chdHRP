package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpTargetData;
import com.chd.hrp.hpm.entity.AphiHospTargetData;

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

public interface AphiEmpTargetDataService {

	/**
	 * 
	 */
	public String addEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchEmpTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryEmpTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryEmpTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpTargetData queryEmpTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteBatchEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String shenhe(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public String getEmpTargetValue(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String importEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;
}
