package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmp;
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

public interface AphiEmpBonusDataMapper extends SqlMapper { 

	/**
	 * 
	 */
	public int addEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int addBatchEmpBonusData(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteBatchEmpBonusData(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteEmpBonusDataByPerm(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateBatchEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	//public int auditAphiEmpBonusData(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	//public int initEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	//public List<AphiEmp> getEmp(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpBonusData> queryEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiEmpBonusData> queryEmpBonusData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	//public List<AphiEmpBonusData> getEmpBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpBonusData queryEmpBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiEmpBonusData> queryEmpBonusDataByKey(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	//public AphiEmpBonusData queryEmpBonusDataByImport(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * 
	 */
	//public List<AphiEmpBonusData> getEmpBonusDataValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	//public List<AphiEmpBonusData> getEmpBonus(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmp> queryAphiEmpByaphiSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryHpmEmpBonusDataForReport(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 二次分配上报 主查询
	 */
	public List<Map<String,Object>> queryHpmEmpBonusDataForUpReport(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 二次分配上报 主查询 打印
	 */
	public List<Map<String,Object>> queryHpmEmpBonusDataForUpReportPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 *  二次分配上报 主查询 分页
	 */
	public List<Map<String,Object>> queryHpmEmpBonusDataForUpReport(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiEmpBonusData> queryEmpBonusDataByDeptSumMoney(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 二次分配查询:查询科室奖金上报状态
	 */
	public List<AphiEmpBonusData> queryAphiEmpBonusDataDeptState(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 二次分配查询:查询科室奖金上报状态 分页
	 */
	public List<AphiEmpBonusData> queryAphiEmpBonusDataDeptState(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 二次分配核算 打印-查询
	 */
	public List<Map<String,Object>> queryEmpBonusDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiEmpBonusData> queryEmpBonusAuditByCode(
			Map<String, Object> entityMap) throws DataAccessException;

	public int updateEmpBonusData_grant(Map<String, Object> entityMap) throws DataAccessException;

	public int addEmpBonusData_grant(Map<String, Object> entityMap) throws DataAccessException;

	public int queryDeleteEmpList(List<Map<String, Object>> entityList) throws DataAccessException;

	public AphiEmpBonusData queryEmpBonusDataAdult(Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiEmpBonusData> queryEmpBonusAuditByCode_dept(
			Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询所有职工的总和
	 * 用于判断在审核时，验证部门项目金额与职工金额总数一致
	 * @param entityMap
	 * @return
	 */
	public String querySumHpmEmpBonusData(Map<String, Object> entityMap);
}
