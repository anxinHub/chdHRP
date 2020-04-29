/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.dao.business.med;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.budg.entity.BudgMedCost;
/**
 * 
 * @Description:
 * 医疗支出预算
 * @Table:
 * BUDG_MED_COST
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 

public interface BudgMedCostMapper extends SqlMapper{
	
	/**
	 * 查询科室支出预算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<BudgMedCost> queryDeptMedCost(Map<String,Object> entityMap) throws DataAccessException;
	public List<BudgMedCost> queryDeptMedCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询医院支出预算
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<BudgMedCost> queryHosMedCost(Map<String,Object> entityMap) throws DataAccessException;
	public List<BudgMedCost> queryHosMedCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<BudgMedCost> queryAllMedCost(Map<String,Object> entityMap) throws DataAccessException;
	public List<BudgMedCost> queryAllMedCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryAllMedCostMonitor(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryAllMedCostMonitor(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*
	 * 医院支出预算报表
	 */
	public List<Map<String, Object>> queryMedAllCostReport(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAllCostReport(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*
	 * 医院医疗支出预算报表
	 */
	public List<Map<String, Object>> queryReportMedHosCost(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryReportMedHosCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/*
	 * 科室医疗支出预算报表
	 */
	public List<Map<String, Object>> queryReportMedDeptCost(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryReportMedDeptCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public List<Map<String, Object>> queryDeptMedCostMonitor(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDeptMedCostMonitor(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查询支出科目是否重复编制
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> querySubjCount(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询各支出表编制数据 
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryAddData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 批量添加生成数据
	 * @param addList
	 */
	public void addGenerateBatch(List<Map<String, Object>> addList) throws DataAccessException;
	
	/**
	 * 根据年月查询预算值、上月结转、执行预算值
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryBudgMedCostByYearMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量修改:上月结转、下月结转
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int updateBatchBudgMedCostValue(List<Map<String,Object>> entityList) throws DataAccessException;
	/**
	 * 根据主键 查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 导入查询 支出科目是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjExist(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 查询 所查 预算年度 是否有 执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryExecuteDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 所查 预算年度 是否有 预算数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有预算数据) 解决  没有预算数据，前台合计行 展现数据 窜行问题)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryBudgDataExist(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询 所查 预算年度 是否有 其他支出执行数据 ( flag 标识 (-1:表示 所查预算年度 没有执行数据) 解决  没有执行数据，前台合计行 展现数据 窜行问题)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryElseExecuteDataExist(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 *  查询 所查 预算年度 是否有 其他支出预算数据 ( budg_flag 标识 (-1:表示 所查预算年度 没有预算数据) 解决  没有预算数据，前台合计行 展现数据 窜行问题)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryElseBudgDataExist(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 其他支出预算执行监控查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryReportMedElseCostMonitor(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 其他支出预算执行监控查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryReportMedElseCostMonitor(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	public int addMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	public int delMonth(@Param("tableName") String tableName,@Param(value="list")List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int updateMonth(Map<String, Object> mapVo)throws DataAccessException;
	
	public int auditOrUnAudit(@Param("tableName") String tableName,@Param(value="list")List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int affiOrUnAffi(@Param("tableName") String tableName,@Param(value="list")List<Map<String, Object>> mapVo)throws DataAccessException;
	
	public int queryBudgMedCostState(Map<String, Object> entityMap)throws DataAccessException;
	
	public int colletMonth(Map<String, Object> mapVo)throws DataAccessException;
	
	public int deleteCollectYear(Map<String, Object> mapVo)throws DataAccessException;
	
	
	public int queryYearDeptDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException;
	
	public int queryYearHosDataExist(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 根据年月查询预算科目
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryYearDeptSubjDataExist(Map<String,Object> mapVo) throws DataAccessException;
	
	public int queryYearDeptExpensesDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException;
	
	public int collectMedMonthExpenses(Map<String, Object> mapVo)throws DataAccessException;
	
	public int addBatchDy(List<Map<String,Object>> entityList) throws DataAccessException;
	
	/**
	 * 根据主键 查询导入数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryImportDataExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 删除已存在费用汇总数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteMedMonthExpenses(Map<String, Object> mapVo)throws DataAccessException;
	
}
