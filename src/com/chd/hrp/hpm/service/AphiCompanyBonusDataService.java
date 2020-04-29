package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiCompanyBonusData;

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

public interface AphiCompanyBonusDataService {

	/**
	 * 
	 */
	public String addCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCompanyBonusData queryCompanyBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteCompanyBonusDataById(String[] ids) throws DataAccessException;

	/**
	 * 
	 */
	public String updateCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;
}
