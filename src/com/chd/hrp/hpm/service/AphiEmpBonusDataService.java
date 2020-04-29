package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpBonusData;

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

public interface AphiEmpBonusDataService {

	/**
	 * 
	 */
	public String addEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryEmpBonusDataPrint(Map<String, Object> entityMap) throws DataAccessException;
	

	/**
	 * 
	 */
	public String createEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String calculationEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpBonusData queryEmpBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String initEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String getEmpBonusDataValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String grantHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpBonus(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String auditHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String twoAuditHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String importHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String deleteHpmEmpBonusDataForReport(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHpmEmpBonusDataForReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询奖金核算的表头
	 */
	public String queryHpmEmpBonusDataForReportGrid(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 二次分配查询:显示当前科室发放总金额
	 */
	public String queryHpmEmpBonusDataDeptGrantSumMoney(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 二次分配查询:查询科室奖金上报状态
	 */
	public String queryAphiEmpBonusDataDeptState(Map<String, Object> entityMap) throws DataAccessException;

	public String querydataAuditaBonus(Map<String, Object> mapVo) throws DataAccessException;
}
