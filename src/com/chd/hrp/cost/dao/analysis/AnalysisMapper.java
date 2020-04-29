package com.chd.hrp.cost.dao.analysis;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AnalysisMapper extends SqlMapper{
	
	
	/**
	 * @Description  
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds 
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0201(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0201(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0203(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0203(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0204(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0204(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0205(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 构成分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0205(Map<String,Object> entityMap) throws DataAccessException;
	

	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0304(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0303(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0303(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0304(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0401(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptReport_2(
			Map<String, Object> entityMap) throws DataAccessException ;

	public List<Map<String, Object>> queryCostDeptReport_2(
			Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptReport_1(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptReport_1(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptReportThead(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryCostDeptReport(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptReport(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> querychengbenMain(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> querychengbenMain(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> querychengbenMain2(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> querychengbenMain2(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> querychengbenMain3(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> querychengbenMain3(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptCost(
			Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryCostDeptCost(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	public List<Map<String, Object>> queryCostFLCost(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostFLCost(
			Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 综合信息分析<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostGeneralHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryCostGeneralHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 综合信息分析<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostGeneralHosMed(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryCostGeneralHosMed(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 综合信息分析明细<BR> 
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostGeneralDetailHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	 
	public List<Map<String, Object>> queryCostGeneralDetailMedHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 综合信息分析明细<BR> 
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	
	public List<Map<String, Object>> queryCostVolumeProfit(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostVolumeProfitDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostVolumeProfitDetailChart(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 科室盈亏分析<BR> 
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	
	public List<Map<String, Object>> queryCostBreakeven(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostBreakevenDetailIncome(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostBreakevenDetailCost(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 全院科室成本环比分析表查询<BR> 
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	
	public List<Map<String, Object>> queryCostRingRatio(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostRingRatioDetail(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostRingRatioChart(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	public List<Map<String, Object>> queryCostClinicalDeptIncomeItemColumns(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysis(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysisAppl(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysisAppl(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysisExec(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostClinicalDeptIncomeAnalysisExec(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostDeptIncomeAnalysisAchievementsAppl(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryCostDeptIncomeAnalysisAchievementsAppl(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 临床科室收入分析构成表(自定义开单科室)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	
	public List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisAppl(	Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisAppl(	Map<String, Object> entityMap, RowBounds rowBounds);

	/**
	 * 临床科室收入分析构成表(自定义执行科室)
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	public List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisExec(	Map<String, Object> entityMap);
	
	public List<Map<String, Object>> queryCostCustomDeptIncomeAnalysisExec(	Map<String, Object> entityMap, RowBounds rowBounds);
	
}

