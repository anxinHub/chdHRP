/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.budgcash;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 现金流量预算
 * @Table:
 * BUDG_CASH_FLOW
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCashFlowExeMapper extends SqlMapper{
	
	/**
	 * 查询资金计划审核数据中未记账的数据   按年度  月份  项目 汇总
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCashPlanDet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询数据是否已经存在
	 * @param map
	 * @return
	 */
	public int queryDataExists(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询月份数据是否已结转
	 * @param entityList
	 */
	public String queryMonthFlag(Map<String, Object> flagMap) throws DataAccessException;
	
	/**
	 * 查询系统启用月份
	 * @param entityList
	 */
	public String querySysMonth(Map<String, Object> flagMap) throws DataAccessException;
	
	/**
	 * 查询数据  资金流向
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCashDire(Map<String, Object> map) throws DataAccessException;

	/**
	 * 根据需求条件   查询现金流量预算表中数据
	 * 对现金流量预算表未结账月份的数据按年度、月份、方向进行汇总得到各月份的现金流入和现金流出
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryCashFlow(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询结账表中结账数据中最大月份
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryMaxMonthFromBudgCarry(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 查询当前年度资金计划中最大月份
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> queryMaxMonthFromCashFlow(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据月份 到存量表中查询该月份期末现金存量  作为下一月的期初现金存量
	 * @param entityMap
	 * @return
	 */
	public Double queryCashEnd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量添加现金存量预算
	 * @param addCashList
	 */
	public void addBudgCashBatch(List<Map<String, Object>> addCashList) throws DataAccessException;
	
	/**
	 * 查询期初货币资金表中期初现金存量
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Double queryCashBegin(Map<String, Object> entityMap)  throws DataAccessException;
	
	/**
	 * 预算年度下拉框
	 * @param mapVo
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgYear(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 预算月份 下拉框
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryBudgMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 初始化添加现金存量预算  计算专用 
	 * @param initList
	 */
	public void addInitBudgCashBatch(List<Map<String, Object>> initList) throws DataAccessException;
	/**
	 * 每次计算前  清空存量预算数据
	 * @param initList
	 */
	public void delectCashBatch(List<Map<String, Object>> initList) throws DataAccessException;
	
	/**
	 * 查询系统启用年月
	 * @param entityMap
	 * @return
	 */
	public Map<String, Object> querySysYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 删除所有大于未结账最小月份的数据
	 * @param entityMap
	 */
	public void delectCash(Map<String, Object> entityMap) throws DataAccessException;
}
