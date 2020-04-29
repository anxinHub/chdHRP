package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;

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

public interface AphiDeptBonusAuditService {


	/**
	 * 
	 */
	public String queryHpmDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description.
	 * 打印-查询
	 * @param
	 * entityMap
	 * @return List<Map<String,Object>>
	 */
	public List<Map<String,Object>> queryHpmDeptBonusAuditPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateHpmDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptBounsAuditGrid(Map<String, Object> map) throws DataAccessException;

	String querydataAudita(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 导入
	 */
	public String importHpmDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 增加
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String savedeptbonusauditAdd(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 删除
	 * @param map
	 * @param checkIds
	 * @return
	 * @throws DataAccessException
	 */
	public String deleteHpmDeptItem(List<Map<String, Object>> listVo) throws DataAccessException;


	public AphiDeptBonusData queryDeptName(Map<String, Object> mapVo) throws DataAccessException;
}
