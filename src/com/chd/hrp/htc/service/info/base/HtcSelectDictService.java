/**
 * 2015-1-20 SelectDictService.java author:pengjin
 */
package com.chd.hrp.htc.service.info.base;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcSelectDictService {

	/*成本项目-上级项目*/
	public String queryHtcItemSuppDict(Map<String, Object> entityMap) throws DataAccessException;	
	
	//成本项目字典表
	public String queryHtcCostItemDict(Map<String, Object> entityMap) throws DataAccessException;
			
    //资金来源字典表
	public String queryHtcSourceDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//成本习性
	public String queryHtcDeptNature(Map<String, Object> entityMap) throws DataAccessException;
	
	// 成本项目来源
	public String queryHtcDataSource(Map<String, Object> entityMap) throws DataAccessException;
	
	// 级次
	public String queryHtcItemGrade(Map<String, Object> entityMap) throws DataAccessException;
		
	// 是或否下拉框
	public String queryHtcYearOrNo(Map<String, Object> entityMap) throws DataAccessException;
	
	// 成本分类变更
	public String queryHtcDeptTypeDictNo(Map<String, Object> entityMap) throws DataAccessException;
		
	//成本项目分摊类型
	public String queryHtcParaType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 收入类型
	public String queryHtcIncomeType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 收入项目
	public String queryHtcIncomeItemDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//收费类别字典表
	public String queryHtcChargeKindArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	//收费项目字典表
	public String queryHtcChargeItemArrt(Map<String, Object> entityMap) throws DataAccessException;
	
	// 部门性质
	public String queryHtcDeptNatur(Map<String, Object> entityMap) throws DataAccessException;
	
	// 部门类型
	public String queryHtcDeptType(Map<String, Object> entityMap) throws DataAccessException;
	
	// 部门级次
	public String queryHtcDeptLevel(Map<String, Object> entityMap) throws DataAccessException;
	
	// 部门分类
	public String queryHtcDeptKind(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询科室字典表
	public String queryHtcDeptDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//核算科室
	public String queryHtcProjDeptDict(Map<String, Object> map) throws DataAccessException;
		
	// 核算方法
	public String queryHtcCheckMethod(Map<String, Object> map) throws DataAccessException;
	
	//方案
	public String queryHtcPlan(Map<String, Object> map) throws DataAccessException;
	
	
	// 职称信息下拉框
	public String queryHtcPeopleTitleDict(Map<String, Object> entityMap) throws DataAccessException;

	// 人员类别下拉框
	public String queryHtcPeopleTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//人员字典
	public String queryHtcPeopleDict(Map<String, Object> map) throws DataAccessException;
	
	// 工资项目下拉框
	public String queryHtcWageItemDict(Map<String, Object> map) throws DataAccessException;
	
	// 奖金项目
	public String queryHtcBonusItemDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// 资产类别下拉框
	public String queryHtcFassetTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//固定资产资产信息字典
	public String queryHtcFassetDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// 无形资产分类信息字典
	public String queryHtcIassetTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	//无形资产资产信息字典
	public String queryHtcIassetDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// 材料分类字典下拉框
	public String queryHtcMaterialTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	
	// 材料信息字典下拉框
	public String queryHtcMaterialDict(Map<String, Object> entityMap) throws DataAccessException;
		
	//计量单位
	public String queryHtcHosUnitDict(Map<String, Object> entityMap) throws DataAccessException;
		
	//生产厂商
	public String queryHtcHosFacDict(Map<String, Object> entityMap) throws DataAccessException;
	// 资源动因
	public String queryHtcResCauseDict(Map<String, Object> entityMap) throws DataAccessException;
	// 作业动因
	public String queryHtcWorkCauseDict(Map<String, Object> entityMap) throws DataAccessException;
	// 作业分类
	public String queryHtcWorkTypeDict(Map<String, Object> entityMap) throws DataAccessException;
	// 作业字典
	public String queryHtcWorkDict(Map<String, Object> entityMap) throws DataAccessException;
	
	
	

	
}
