package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptBonusGrantItem;

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

public interface AphiDeptBonusGrantItemService {

	/**
	 * 
	 */
	public String addDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryDeptBonusGrantItemPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptBonusGrantItem queryDeptBonusGrantItemByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptBonusGrantItem(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptBonusGrantItemById(String[] ids) throws DataAccessException;
	
	/**
	 * 
	 */
	public String grantHpmDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String auditHpmDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptBonusGrantItem(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public String createHpmDeptBonusGrantItem(Map<String, Object> entityMap) throws DataAccessException;
}
