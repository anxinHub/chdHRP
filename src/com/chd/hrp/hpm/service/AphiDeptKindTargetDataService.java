package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptKindTargetData;
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

public interface AphiDeptKindTargetDataService {

	/**
	 * 
	 */
	public String addDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptKindTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 打印-查询
	 */
	public List<Map<String, Object>> queryDeptKindTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptKindTargetData queryDeptKindTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptKindTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteBatchDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptKindTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptKindTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String shenhe(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getDeptKindTargetValue(Map<String, Object> entityMap) throws DataAccessException;

}
