package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

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

public interface AphiHospTargetDataService {

	/**
	 * 
	 */
	public String addHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	public String addBatchHospTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHospTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHospTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiHospTargetData queryHospTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteHospTargetData(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteBatchHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String shenhe(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryHospTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException;
}
