/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
 

public interface BudgCountProcessMapper extends SqlMapper{
	
	/**
	 * 查询 基本指标类 或 费用标注类  预算层次为 医院年度  的 计算项数据
	 * @param temp
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryHosBasicIndex(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询 基本指标类 或 费用标注类  预算层次为 科室年度  的 计算项数据 
	 * @param temp
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryDeptBasicIndex(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询  预算指标类 计算项 数据
	 * @param temp
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBudgIndex(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询  收入科目类 元素层次element_level 01 医院月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInHYBudg(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  收入科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInHMBudg(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询  收入科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInDYBudg(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  收入科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInDMBudg(Map<String, Object> temp) throws DataAccessException;
	
	
	/**
	 * 查询  收入科目类 元素层次element_level 01 医院月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInHYExecute(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  收入科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInHMExecute(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询  收入科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInDYExecute(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  收入科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgInDMExecute(Map<String, Object> temp) throws DataAccessException;
	
	
	
	
	/**
	 * 查询  支出科目类 元素层次element_level 01 医院月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostHYBudg(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  支出科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostHMBudg(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询  支出科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostDYBudg(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  支出科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 01本年预算 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostDMBudg(Map<String, Object> temp) throws DataAccessException;
	
	
	/**
	 * 查询  支出科目类 元素层次element_level 01 医院月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostHYExecute(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  支出科目类 元素层次element_level 02 医院月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostHMExecute(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 查询  支出科目类 元素层次element_level 03 科室年度，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostDYExecute(Map<String, Object> temp) throws DataAccessException;

	/**
	 * 查询  支出科目类 元素层次element_level 04 科室月份，预算值类型value_type_code 为 02上年执行 的 计算项 数据
	 * @param temp
	 * @return
	 */
	public Map<String, Object> queryBudgCostDMExecute(Map<String, Object> temp) throws DataAccessException;
	
	/**
	 * 计算项取值  递归  计算项 类型为 01 基本指标时  从 BUDG_BASIC_INDEX_GET_WAY(基本指标取值方法)表中 查询其取值方法；
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBasicIndexGetWay(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 计算项取值  递归  计算项 类型为 02 费用指标时  从 BUDG_CHARGE_STAND_GET_WAY(费用标准取值方法)表中 查询其取值方法；
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryChargeStanIndexGetWay(Map<String, Object> map) throws DataAccessException ;
	/**
	 * 计算项取值  递归  计算项 类型为 03 预算指标时  从 BUDG_WORK_EDIT_PLAN(业务预算编制方案)表中 查询其取值方法；
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBudgIndexGetWay(Map<String, Object> map) throws DataAccessException ;
	
	/**
	 * 计算项取值  递归  计算项 类型为 04 收入科目时  从 BUDG_MED_INCOME_EDIT_PLAN (医疗收入预算编制方案)表中 查询其取值方法；
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryIncomeIndexGetWay(Map<String, Object> map) throws DataAccessException;
}
