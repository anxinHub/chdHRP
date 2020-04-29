/**
 * 2015-1-20 SystemSelectDictMapper.java author:pengjin
 */
package com.chd.hrp.htc.dao.info.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.HrpAccSelect;
import com.chd.hrp.cost.entity.HrpCostSelect;

public interface HtcSelectDictMapper extends SqlMapper{

	/**
	 * 成本项目-上级项目
	 * @return
	 * @throws DataAccessException  
	 */
	public List<Map<String, Object>> queryHtcItemSuppDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	/**
	 * 成本项目字典表
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcCostItemDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 资金来源字典表
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcSourceDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 成本习性表
	 * @return
	 * @throws DataAccessException 
	 */
	public  List<Map<String, Object>> queryHtcDeptNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 成本项目来源字典表
	 * @return
	 * @throws DataAccessException 
	 */
	public  List<Map<String, Object>> queryHtcDataSource(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/***
	 * 级次
	 * @return
	 * @throws DataAccessException
	 */
	public	List<Map<String, Object>> queryHtcItemGrade(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 是或否下拉框
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcYearOrNo(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	
	/**
	 * 成本分类变更
	 * @return
	 * @throws DataAccessException 
	 */
	public  List<Map<String, Object>> queryHtcDeptTypeDictNo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 成本分摊类型
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String,Object>> queryHtcParaType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/***
	 * 收入类型
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHtcIncomeType(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;	
	
	/***
	 * 收入项目
	 * @return
	 * @throws DataAccessException
	 */
	public  List<Map<String,Object>> queryHtcIncomeItemDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 收费类别字典表
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcChargeKindArrt(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 收费项目字典表
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcChargeItemArrt(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/***
	 *  部门性质
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHtcDeptNatur(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/***
	 *  部门类型
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryHtcDeptType(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/***
	 *  部门级次
	 * @return
	 * @throws DataAccessException
	 */
	public  List<Map<String, Object>> queryHtcDeptLevel(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/***
	 *  部门分类
	 * @return
	 * @throws DataAccessException
	 */
	public  List<Map<String, Object>> queryHtcDeptKind(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询科室字典表 
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryHtcDeptDict(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 核算科室
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcProjDeptDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	/***
	 * 核算方法
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcCheckMethod(Map<String, Object> map,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 方案下拉框 
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcPlan(Map<String, Object> map,RowBounds rowBounds) throws DataAccessException;
	
	
	
	
	
	
	/**
	 * 职称信息下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcPeopleTitleDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 人员类别下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcPeopleTypeDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 人员
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcPeopleDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 工资项下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcWageItemDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 奖金项目下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcBonusItemDict(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 资产类别下拉框
	 * @return
	 * @throws DataAccessException
	 */                              
	public List<Map<String, Object>> queryHtcFassetTypeDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 固定资产资产信息字典
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcFassetDict(Map<String, Object> map,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 无形资产分类信息字典
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcIassetTypeDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 无形资产资产信息字典
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcIassetDict(Map<String, Object> map,RowBounds rowBounds) throws DataAccessException;
	/**
	 * 材料分类字典下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcMaterialTypeDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 材料信息字典下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcMaterialDict(Map<String, Object> map,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 计量单位
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcHosUnitDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 生产厂商
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcHosFacDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	
	/***
	 * 资源动因
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcResCauseDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 作业动因
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcWorkCauseDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	

	/***
	 * 作业分类
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcWorkTypeDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 作业字典下拉框
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryHtcWorkDict(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	
}
