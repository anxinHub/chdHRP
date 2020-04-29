package com.chd.hrp.cost.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;
 
public interface HrpCostSelectService {
	public String queryHosDeptLevel(Map<String, Object> entityMap) throws DataAccessException;
	// 收入类型
	public String queryIncomeType(Map<String, Object> entityMap) throws DataAccessException;

	// 收入项目
	public String queryIncomeItemArrt(Map<String, Object> entityMap) throws DataAccessException;
 
	// 收费类别
	public String queryChargeKindArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	// 收费项目
	public String queryChargeItemArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 材料分类字典
	public String queryMateTypeArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 固定资产分类字典
	public String queryFassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 无形资产分类字典
	public String queryIassetTypeArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 职工职称字典
	public String queryEmpTitleArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 职工分类字典
	public String queryEmpTypeArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 职工字典
	public String queryEmpArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	// 职工变更字典
	public String queryCostEmpDict(Map<String, Object> entityMap) throws DataAccessException;

	// 工资项字典
	public String queryWageItemArrt(Map<String, Object> entityMap) throws DataAccessException;

	// 成本习性
	public String queryDeptNature(Map<String, Object> entityMap) throws DataAccessException;
	
	// 成本项目来源
	public String queryDataSource(Map<String, Object> entityMap) throws DataAccessException;

	// 成本项目
	public String queryItemDict(Map<String, Object> entityMap) throws DataAccessException;
	// 成本项目
	public String queryItemDictLast(Map<String, Object> entityMap) throws DataAccessException;

	// 成本分类变更
	public String queryDeptTypeDictNo(Map<String, Object> entityMap) throws DataAccessException;

	// 成本项目变更
	public String queryItemDictNoItemGrade(Map<String, Object> entityMap) throws DataAccessException;
	// 成本项目变更
	public String queryItemDictNo(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryItemDictNoLast(Map<String, Object> entityMap) throws DataAccessException;
	public String queryItemDictCodeLast(Map<String, Object> entityMap) throws DataAccessException;

	// 奖金项属性
	public String queryBonusItemArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	//职工工资方案
	public String queryWangSchemeSet(Map<String, Object> entityMap) throws DataAccessException;
	
	//职工奖金方案
	public String queryBonusSchemeSet(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryDeptLevel(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 科室变更字典
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDictNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室变更字典code
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDictCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室变更字典code
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDictCodeLast(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 病人类别字典
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostPatientTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 分摊参数
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptParaDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 材料信息字典
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMateArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 无形资产
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryIassetArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 无形资产字典查分类
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryIassetArrtType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 固定资产
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFassetArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 固定资产字典信息查分类
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFassetArrtType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资金来源
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySourceArrt(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 服务项目
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryServerItemDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室类型
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostDeptKindDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 科室性质
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostDeptNature(Map<String, Object> entityMap) throws DataAccessException;

	
	/**
	 * 收入项目
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostIncomeItemArrt(Map<String, Object> entityMap) throws DataAccessException;
	

	
	/**
	 * 2016/10/25 lxj
	 * 材料分类 上级编码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMateTypeSupperCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/10/25 lxj
	 * 固定资产分类 上级编码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryFassetTypeSupperCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 2016/10/25 lxj
	 * 无形资产分类 上级编码
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryIassetTypeSupperCode(Map<String, Object> entityMap) throws DataAccessException;
	public String queryParaType(Map<String, Object> entityMap) throws DataAccessException;
	public String queryParaNatur(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 末级科室下拉
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDeptDictNoLast(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 成本项目与会计科目对应关系表,成本科目不存在对应表中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostSubjItemMapByItem(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 成本项目与会计科目对应关系表,会计科目不存在对应表中
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostSubjItemMapBySubj(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 自定义分摊参数目标参数
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostUserDefinedParaTarget(Map<String, Object> entityMap) throws DataAccessException;
	
}

