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
import com.chd.hrp.budg.entity.BudgCashInPlan;
/**
 * 
 * @Description:
 * 现金流入计划
 * @Table:
 * BUDG_CASH_IN_PLAN
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgCashPlanMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询数据 现金流量项目下拉框
	 */
	public List<Map<String, Object>> queryCashItem(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * @Description 
	 * 查询同年度 资金流动事项是否存在
	 */
	public int queryEventDataExist(Map<String, Object> item) throws DataAccessException;
	
	/**
	 * 批量添加资金计划明细
	 * @param entityList
	 */
	public void addPLanDetBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询月份数据是否已结转
	 * @param entityList
	 */
	public String queryMonthFlag(Map<String, Object> flagMap) throws DataAccessException;
	/**
	 * 查询系统启用月份
	 * @param entityList
	 */
	public String querySysYear(Map<String, Object> flagMap) throws DataAccessException;
	/**
	 * 查询系统启用月份
	 * @param entityList
	 */
	public String querySysMonth(Map<String, Object> flagMap) throws DataAccessException;
	
	/**
	 * 根据主表主键 删除明细
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public void deletePlanDet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据主表主键 查询资金计划明细
	 * @param mapVo
	 * @throws DataAccessException
	 */
	public List<BudgCashInPlan> queryBudgCashPlanDet(Map<String, Object> entityMap) throws DataAccessException;
	public List<BudgCashInPlan> queryBudgCashPlanDet(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 审核销审更新数据状态
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateReviewState(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 查询数据状态
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryState(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询现金流量项目表中所有未停用的现金流量项目id code等基本信息   匹配数据用
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryBudgCashFlowBeginDict(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 *批量删除明细数据
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void deletePlanDetBatch(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 修改时查询数据是否已经存在   修改专用 
	 * @param item
	 * @return
	 */
	public int queryEventDataExistForupdate(Map<String, Object> item) throws DataAccessException;
	
}
