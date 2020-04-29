package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptBonusGrant;

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

public interface AphiDeptBonusGrantService {

	/**
	 * 
	 */
	public String addDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryDeptBonusGrantPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptBonusGrant(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptBonusGrantById(String[] ids) throws DataAccessException;
	
	/**
	 * 
	 */
	public String grantHpmDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String auditHpmDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptBonusGrant(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public String createHpmDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;
}
