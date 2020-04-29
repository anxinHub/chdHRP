package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptKindBonusData;

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

public interface AphiDeptKindBonusDataService {

	/**
	 * 
	 */
	public String addDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptKindBonusData queryDeptKindBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptKindBonusDataById(String[] ids) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptKindBonusDataGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryTargetGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryTarget(Map<String, Object> entityMap) throws DataAccessException;
}
