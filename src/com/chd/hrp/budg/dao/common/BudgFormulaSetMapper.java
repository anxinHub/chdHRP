/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.common;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 计算公式
 * @Table:
 * BUDG_FORMULA_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgFormulaSetMapper extends SqlMapper{
	/**
	 * 基本指标树加载
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBasicIndexTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 费用标准树加载
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryChargeStanTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 预算指标树加载
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgIndexTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 收入科目指标树加载
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryIncomeSubjTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 支出科目树加载
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostSubjTree(	Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 查询公式名称 是否已被占用
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryNameExist(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量添加批量添加计算公式元素栈数据
	 * @param addList
	 * @throws DataAccessException
	 */
	public void addBatchFormulaStack(List<Map<String, Object>> addList) throws DataAccessException;

	/**
	 * 删除 计算公式元素栈数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void deleteFormulaStack(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 计算公式元素栈数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryFormulaStack(Map<String, Object> mapVo) throws DataAccessException;
	
}
