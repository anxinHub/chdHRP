/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.budg.service.business.med;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
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
 


public interface BudgMedCostService extends SqlService {
	
	public String queryDeptMedCost(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询医院
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHosMedCost(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAllMedCost(Map<String,Object> entityMap) throws DataAccessException;
	
	/*
	 * 医院医疗支出预算报表
	 */
	public String queryReportMedHosCost(Map<String,Object> entityMap) throws DataAccessException;
	
	/*
	 * 医院医疗支出科室汇总
	 */
	public String collectMedMonthCost(Map<String,Object> entityMap) throws DataAccessException;
	/*
	 * 医院医疗支出科室费用汇总
	 */
	public String collectMedMonthExpenses(Map<String,Object> entityMap) throws DataAccessException;
	
	/*
	 * 科室医疗支出预算报表
	 */
	public String queryReportMedDeptCost(Map<String,Object> entityMap) throws DataAccessException;
	
	/*
	 * 医院支出预算报表
	 */
	public String queryMedAllCostReport(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryDeptMedCostMonitor(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAllMedCostMonitor(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String generateBudgMedCost(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 根据主键查询数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDataExist(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 导入查询 支出科目是否存在
	 * @param subjMapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int querySubjExist(Map<String, Object> subjMapVo) throws DataAccessException;
	/**
	 * 其他支出预算执行监控查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryReportMedElseCostMonitor(Map<String, Object> mapVo) throws DataAccessException;
	
	public String addMonth(Map<String,Object> mapVo) throws DataAccessException;
	
	public String queryMonth(Map<String,Object> mapVo) throws DataAccessException;
	
	public String updateMonth(Map<String,Object> mapVo) throws DataAccessException;
	
	public String delMonth(String tableName,List<Map<String,Object>> mapVo) throws DataAccessException;
	
	public String auditOrUnAudit(String tableName,List<Map<String, Object>> mapVo) throws DataAccessException;
	
	public String affiOrUnAffi(String tableName,List<Map<String, Object>> mapVo) throws DataAccessException;
	
	public List<String> queryBudgMedCostState(Map<String, Object> page) throws DataAccessException;
	
	public List<String> queryBudgCostYearDeptState(Map<String, Object> page) throws DataAccessException;
	
	public List<Map<String,Object>> queryDeptMedCostMonth(Map<String,Object> entityMap) throws DataAccessException;
	/*
	 * 医院医疗支出科室汇总检查科室成本数据是否存在未确认
	 */
	public String queryYearDeptDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException;
	/*
	 * 医院医疗支出科室汇总检查全院成本数据是否已存在
	 */
	public String queryYearHosDataExist(Map<String, Object> mapVo) throws DataAccessException;
	/*
	 * 医院医疗支出科室费用汇总检查费用成本数据是否存在未确认
	 */
	public String queryYearDeptExpensesDataExistNoCheck(Map<String, Object> mapVo) throws DataAccessException;
	/*
	 * 医院医疗支出科室费用汇总检查科室成本数据对应预算科目是否已生成
	 */
	public String queryYearDeptSubjDataExist(Map<String, Object> mapVo) throws DataAccessException;
	/*
	 * 医院医疗支出科室Dy
	 */
	public String addBatchDy(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * 根据主键查询导入数据是否存在
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public int queryImportDataExist(Map<String, Object> mapVo) throws DataAccessException;
}
