/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.service.analysis;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
* @Title. @Description.
* 构成结余分析服务类<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface AnalysisService {
	
	public String queryAnalysisC0201(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0203(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0204(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0205(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0303(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0304(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;

	public String queryCostDeptReport_2(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCostDeptReport_1(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCostDeptReportThead(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostDeptReport(Map<String, Object> entityMap) throws DataAccessException;
	
	public String querychengbenMain(Map<String, Object> entityMap) throws DataAccessException;

	public String querychengbenMain2(Map<String, Object> entityMap) throws DataAccessException;

	public String querychengbenMain3(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCostDeptCost(Map<String, Object> entityMap) throws DataAccessException;

	public String queryCostFLCost(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralHos(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralHosMed(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralDetailHos(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostGeneralDetailMedHos(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostVolumeProfit(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostVolumeProfitDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostVolumeProfitDetailChart(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostBreakeven(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostBreakevenDetailIncome(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostBreakevenDetailCost(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostRingRatio(Map<String, Object> entityMap) throws DataAccessException;
 
	public String queryCostRingRatioDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	public String queryCostRingRatioChart(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (1)医院各类科室直接成本表打印
	 * */
	public  List<Map<String, Object>>  queryCostDeptReport_1print(Map<String, Object> entityMap) throws DataAccessException;

	/*
	 * 
	 * (2)医院各类科室直接成本明细表打印
	 * */
	public List<Map<String, Object>> queryCostDeptReportprint(Map<String, Object> entityMap) throws DataAccessException;
	

	/*
	 * (3)医院临床服务类科室医疗成本分析表打印
	 * */
	public  List<Map<String, Object>>  querychengbenMainprint(Map<String, Object> entityMap) throws DataAccessException;
	

	/*
	 * (4)医院临床服务类科室医疗全成本分析表（财政）打印 
	 * */
	public List<Map<String, Object>> querychengbenMain2print(Map<String, Object> entityMap) throws DataAccessException;
	

	/*
	 * (5)医院临床服务类科室全成本分析表打印
	 * */
	public List<Map<String, Object>> querychengbenMain3print(Map<String, Object> entityMap) throws DataAccessException;
	

	/*
	 * (6)医院临床服务类科室全成本分析明细表打印
	 * */
	public List<Map<String, Object>> queryCostDeptReport_2print(Map<String, Object> entityMap) throws DataAccessException;
	

	/*
	 * (7)医院临床服务类科室各级成本构成分析表打印
	 * */
	public List<Map<String, Object>>queryAnalysisC0201print(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (8)临床服务类科室医疗成本构成分析明细表打印
	 * */
	public List<Map<String, Object>> queryAnalysisC0203print(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (9)临床服务类科室医疗全成本构成分析明细表打印
	 * */
	public List<Map<String, Object>> queryAnalysisC0204print(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (10)医院临床服务类科室全成本构成分析明细表打印
	 * */
	public List<Map<String, Object>> queryAnalysisC0205print(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (11)医院医疗成本分类构成表打印
	 * */
	public List<Map<String, Object>> queryAnalysisC0303print(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (12)医院医疗成本分类构成明细表打印
	 * */
	public List<Map<String, Object>> queryAnalysisC0304print(Map<String, Object> entityMap) throws DataAccessException;
	
	/*
	 * (13)医院科室医疗成本习性分析表打印
	 * */
	public List<Map<String, Object>> queryAnalysisC0401print(Map<String, Object> entityMap) throws DataAccessException;
	
	/* 
	 * 科室医疗成本分摊表
	 * */
	public List<Map<String, Object>>  queryCostDeptCostprint(Map<String, Object> entityMap) throws DataAccessException;

	/*
	 * 医疗成本分类分摊表
	 * */
	public List<Map<String, Object>>  queryCostFLCostprint(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public String  queryCostClinicalDeptIncomeItemColumns(Map<String, Object> entityMap) throws DataAccessException;
	
	public String  queryCostClinicalDeptIncomeAnalysis(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>>  queryCostClinicalDeptIncomeAnalysisPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String  queryCostClinicalDeptIncomeAnalysisAppl(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>>  queryCostClinicalDeptIncomeAnalysisApplPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String  queryCostClinicalDeptIncomeAnalysisExec(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>>  queryCostClinicalDeptIncomeAnalysisExecPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	public String  queryCostDeptIncomeAnalysisAchievementsAppl(Map<String, Object> entityMap) throws DataAccessException;
	
	public  List<Map<String, Object>>  queryCostDeptIncomeAnalysisAchievementsApplPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 临床科室收入分析构成表(自定义开单科室)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostCustomDeptIncomeAnalysisAppl(Map<String, Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisApplPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 临床科室收入分析构成表(自定义执行科室)
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryCostCustomDeptIncomeAnalysisExec(Map<String, Object> entityMap) throws DataAccessException;
	
	public  List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisExecPrint(Map<String, Object> entityMap) throws DataAccessException;

}
