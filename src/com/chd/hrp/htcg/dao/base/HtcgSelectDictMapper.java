package com.chd.hrp.htcg.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HtcgSelectDictMapper extends SqlMapper {
	
	/**
	 * 是或否下拉框
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcgYearOrNo(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 医嘱分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgRecipeTypeDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 医保类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgIdentityTypeDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 诊断性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgIcd10NatureDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 诊断类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgIcd10TypeDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 麻醉种类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgAnestTypeDict(Map<String, Object> map,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 转归字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgOutcomeDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 药品类别
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgDrugTypeDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 药品
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgDrugDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;

	/**
	 * 诊疗项目
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryCostChargeItemArrtDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 材料
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryhtcMaterialDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 生产厂商
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHosFacDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgDeptDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * 核算方案
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgSchemeDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 病种分类
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgDrgsTypeDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 病种字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgDrgsDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 诊断字典 
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgIcd10Dict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;

	/**
	 * 手术字典
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgIcd9Dict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * ICD入组规则 (诊断,手术)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgIcdRuleDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 入组样本抽取规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	// 抽取规则
	public List<Map<String, Object>> queryHtcgMrRuleDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 临床路径时程划分(期间)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgCipStepDateDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 临床路径时程划分(地点)
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgCipStepPlaceDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 相似治疗效果合并规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgRecipeMergeRuleDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 相似治疗效果项目性质
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgChargeNatureDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 医嘱项目准入规则
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgRecipeGroupRuleDict(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 核算方案应用序号
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgSeqTableDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 期间类型
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgPeriodTypeDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	/**
	 * 期间
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryHtcgPeriodDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 药品管理成本科室
	 * @param map
	 * @param model
	 * @return
	 * @throws Exception
	 */
	public List<Map<String, Object>> queryDeptDrugAdminCostDict(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
}
