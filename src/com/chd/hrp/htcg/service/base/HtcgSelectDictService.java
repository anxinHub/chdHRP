package com.chd.hrp.htcg.service.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcgSelectDictService {

	// 是或否下拉框
	public String queryHtcgYearOrNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 医嘱分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgRecipeTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 医保类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgIdentityTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 诊断性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgIcd10NatureDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 诊断类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgIcd10TypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 麻醉种类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgAnestTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 转归字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgOutcomeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 药品类别
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgDrugTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 药品
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgDrugDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 诊疗项目
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryCostChargeItemArrtDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 材料
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryhtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 生产厂商
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHosFacDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgDeptDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 核算方案
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgSchemeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 病种分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgDrgsTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 病种字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgDrgsDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 诊断字典 
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgIcd10Dict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 手术字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgIcd9Dict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * ICD入组规则 (诊断,手术)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgIcdRuleDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 入组样本抽取规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgMrRuleDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 临床路径时程划分(期间)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgCipStepDateDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 临床路径时程划分(地点)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgCipStepPlaceDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 相似治疗效果合并规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
     public String queryHtcgRecipeMergeRuleDict(Map<String, Object> entityMap) throws DataAccessException;
     /**
 	 * 相似治疗效果项目性质
 	 * @param map
 	 * @param model
 	 * @return
 	 * @throws Exception
 	 */
 	public String queryHtcgChargeNatureDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 医嘱项目准入规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgRecipeGroupRuleDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 核算方案应用序号
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgSeqTableDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 期间类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgPeriodTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 期间
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryHtcgPeriodDict(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 药品管理成本科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public String queryDeptDrugAdminCostDict(Map<String, Object> entityMap) throws DataAccessException;
	
}
