package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpBonusAudit;

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

public interface AphiEmpBonusAuditService {

	/**
	 * 
	 */
	public String addEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpBonusAudit queryEmpBonusAuditByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpBonusAuditById(String[] ids) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;
}
