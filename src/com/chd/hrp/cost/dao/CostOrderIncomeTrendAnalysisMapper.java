/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.cost.entity.CostOrderIncomeTrend;


/**
* @Title. @Description.
* 开单收入趋势分析表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface CostOrderIncomeTrendAnalysisMapper extends SqlMapper{

	
	/**
	 * @Description 
	 * 开单收入趋势分析表<BR> 查询CostOrderIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return List<CostOrderIncomeTrend>
	 * @throws DataAccessException
	*/
	public List<CostOrderIncomeTrend> queryCostOrderIncomeTrend(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 开单收入趋势分析表<BR> 查询CostOrderIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return List<CostOrderIncomeTrend>
	 * @throws DataAccessException
	*/
	public List<CostOrderIncomeTrend> queryCostOrderIncomeTrend(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 *  成本趋势分析
	 * @param  entityMap RowBounds
	 * @return List<CostOrderIncomeTrend>
	 * @throws DataAccessException
	*/
	public List<CostOrderIncomeTrend> queryCostTrendAnalysis(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 成本趋势分析
	 * @param  entityMap RowBounds
	 * @return List<CostOrderIncomeTrend>
	 * @throws DataAccessException
	*/
	public List<CostOrderIncomeTrend> queryCostTrendAnalysis(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
