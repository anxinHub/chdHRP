
/*
 *
 */
 package com.chd.hrp.eqc.dao.analyse;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Author: bell
 * @Version: 1.0
 */

/**
* 效益分析报表  DB管理
*
*/
public interface AssBenefitAnalysisMapper extends SqlMapper{
	
	/**
	 * 资产收益分析报表 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssProfitAnalysis(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产收益分析报表 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssProfitAnalysis(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 投资收益报表 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryInvestmentBenefitAnalys(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资投资收益报表 查询分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryInvestmentBenefitAnalys(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * 本量利分析  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCvpAnalysis(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 本量利分析 查询分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCvpAnalysis(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 设备收支明细 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssIncomAndCost(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 设备收支明细 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssIncomAndCost(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 贵重医疗设备使用效益分析表 查询 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssUseBenefitAnalys(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 贵重医疗设备使用效益分析表 查询 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAssUseBenefitAnalys(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
}
