/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcost.empwagecost;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 科室职工工资总表
 * @Table:
 * BUDG_EMP_WAGE_TOTAL
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgEmpWageTotalMapper extends SqlMapper{
	/**
	 * 查询工资项目
	 */
	public List<Map<String, Object>> queryBudgWageItem(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryDataByCode(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 根据主键查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo)  throws DataAccessException;
	/**
	 * 查询所有工资项目信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgWageItemList(Map<String, Object> entityMap) throws DataAccessException ;
	/**
	 * 查询 科室基本信息(根据编码 匹配ID用)
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptData(Map<String, Object> map) throws DataAccessException ;

	/**
	 * 查询 所有职工信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEmpData(Map<String, Object> map)  throws DataAccessException;
	
	/**
	 * 查询所有职工类别信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgEmpType(Map<String, Object> map) throws DataAccessException;
	
}
