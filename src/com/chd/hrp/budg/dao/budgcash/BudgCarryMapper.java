/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcash;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgCarry;
/**
 * 
 * @Description:
 * 资金预算结转
 * @Table:
 * BUDG_CARRY
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCarryMapper extends SqlMapper{
	
	/**
	 * 已结转月份查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryYearMonthBefore(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 待结转月份查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>>  queryYearMonth(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 资金预算模块 启用年月查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryYearMonthStart(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询现金存量预算表中累计流入 累计流出   (与流量预算数据比较使用)
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryBudgCash(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询现金存量执行表中累计流入 累计流出   (与流量执行数据比较使用)  期末现金存量  (后续更新存量预算结转值  及后续月份期初期末数据使用)
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryBudgCashExe(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询现金流量预算表中累计流入 累计流出 (与存量预算数据比较使用)
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryBudgCashFlow(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询现金流量执行表中累计流入 累计流出  (与存量执行数据比较使用)
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryBudgCashFlowExe(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 更新现金存量预算表中 结转值
	 * @param entityMap
	 */
	public void updateCashCarry(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询  大于该结转月份 的月份值  和 净增加额
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMonthCashAddFromBudgCash(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 将重新计算后的 各月期初和期末值  更新入存量预算表中
	 * @param entityList
	 */
	public void updateBudgCash(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 查询数据是否存在
	 * @param entityMap
	 */
	public int queryDataExists(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询  预算结账表数据
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgCarry(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询  预算结账表当前年月数据
	 * @param entityMap
	 * @return
	 */
	public BudgCarry queryBudgCarryByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 修改结账标记、结账人、结账日期
	 * @param entityMap
	 */
	public int updateBudgCarry(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加
	 * @param entityMap
	 */
	public int addBudgCarry(Map<String, Object> entityMap) throws DataAccessException;
}
