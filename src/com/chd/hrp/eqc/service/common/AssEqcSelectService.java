package com.chd.hrp.eqc.service.common;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface AssEqcSelectService {
	
	/**
	 * 部门类型下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门分类下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptKind(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门性质下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptNature(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 部门支出性质下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptOut(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 科室 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**  
	 * 固定资产类别 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssType(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出项目下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPaymentItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 支出项目下拉框 带变更号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPaymentItemDict(Map<String, Object> mapVo) throws DataAccessException;
	

	/**
	 * 资产性质 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssNatures(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产字典 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产字典 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssDictInassets(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 职工
	 * @param mapVo (f_code required=true)
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	public String queryEmpDict(Map<String, Object> mapVo) throws DataAccessException;
		
	/**
	 * 仓库  下拉框 不带NO
	 * @param mapVo
	 * @return
	 */
	public String queryHosStoreDict(Map<String, Object> mapVo) throws DataAccessException;	
	/**
	 * 仓库  下拉框 带NO
	 * @param mapVo
	 * @return
	 */
	public String queryHosStoreDictNo(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 仪器来源 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBusiDataSource(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资产卡片 设备下拉框   id（ass_card_no） text(ass_card_no ass_name )
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssCardDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 用户下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String querySysUser(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 服务项（收费类别 ） 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostChargeKind(Map<String, Object> mapVo) throws DataAccessException;
	 
	/**
	 * 计量单位 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosUnit(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 服务细项（收费项目 ） 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostChargeItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 消耗资源 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssEqcConsumableItem(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资源类型 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssEqcResourceType(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	 * 会计年度 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssYear(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 机组/设备 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssGroupOrCardDict(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 分析项 下拉框
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAssAnalysisObject(Map<String, Object> mapVo)  throws DataAccessException;
}
