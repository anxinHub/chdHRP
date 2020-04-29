package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiItem;

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

public interface AphiDeptBonusDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addBatchDeptBonusData(List<AphiDeptBonusData> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusData> queryDeptBonusDataForGrant(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusData> queryDeptBonusDataByFormulaCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptBonusData queryHpmDeptBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;


	/**
	 * 
	 */
	public int updateDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateBatchDeptBonusData(List<Map<String, Object>> entityList) throws DataAccessException;


	/**
	 * 
	 */
	public List<AphiDeptBonusData> queryDeptSchemeSeqForDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<Map<String,Object>> queryDeptBonusForBonusMoney(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryDeptBonusForBonusMoney(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 打印-查询
	 */
	public List<Map<String,Object>> queryDeptBonusForBonusMoneyPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据科室所用的计算公式得到指标 在根据指标取得 每个指标的数据
	 */
	public List<Map<String,Object>> queryHpmDeptBonusDataForTarget(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据科室所用的计算公式得到指标 在根据指标取得 每个指标的数据
	 */
	public List<Map<String,Object>> queryHpmDeptBonusDataForTarget(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 根据科室所用的计算公式得到指标 在根据指标取得 每个指标的数据
	 */
	public List<Map<String,Object>> queryHpmDeptBonusDataForFormula(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询核算科室所用的指标 作为奖金核算查询的表头
	 */
	public List<AphiDeptBonusData> queryHpmDeptBonusDataForTargetGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据年月查询所使用的计算公式
	 */
	public List<AphiDeptBonusData> queryDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询所有科室奖金数据
	 */
	public List<AphiDeptBonusData> queryDeptBonus(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchDeptBonusDataByListMap(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	
	
	public List<AphiItem> queryAphiDeptSchemeItem(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryDeptBonusByItemForBonusMoney(Map<String, Object> entityMap) throws DataAccessException;

	public AphiDeptBonusData queryDeptName(Map<String, Object> mapVo) throws DataAccessException;

	public List<AphiDeptBonusData> queryListDeptBonusData(
			Map<String, Object> entityMap) throws DataAccessException;
	
	public int updateBatchBack(List<Map<String, Object>> entityMap) throws DataAccessException;

	public AphiDeptBonusData queryHpmDeptBonusDataByCode_Audit(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiDeptBonusData> queryHpmDeptBonusDataByCode_AuditList(
			Map<String, Object> entityMap)  throws DataAccessException;

}
