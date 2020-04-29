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
import com.chd.hrp.cost.entity.CostExecIncomeTrend;


/**
* @Title. @Description.
* 开单收入趋势分析表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface CostExecIncomeTrendAnalysisMapper extends SqlMapper{

	
	/**
	 * @Description 
	 * 开单收入趋势分析表<BR> 查询CostExecIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return List<CostExecIncomeTrend>
	 * @throws DataAccessException
	*/
	public List<CostExecIncomeTrend> queryCostExecIncomeTrend(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 开单收入趋势分析表<BR> 查询CostExecIncomeTrend分页
	 * @param  entityMap RowBounds
	 * @return List<CostExecIncomeTrend>
	 * @throws DataAccessException
	*/
	public List<CostExecIncomeTrend> queryCostExecIncomeTrend(Map<String,Object> entityMap) throws DataAccessException;
}
