package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptTargetData;
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

public interface AphiDeptTargetDataService {

	/**
	 * 
	 */
	public String addDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryDeptTargetView(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryDeptTargetViewGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryDeptTargetDataByTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptTargetData queryDeptTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteBatchDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String shenhe(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getDeptTargetValue(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String importDeptTargerData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryDeptTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;
}
