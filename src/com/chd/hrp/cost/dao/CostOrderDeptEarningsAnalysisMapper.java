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
import com.chd.hrp.cost.entity.CostOrderDeptEarnings;


/**
* @Title. @Description.
* 开单收入趋势分析表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/
public interface CostOrderDeptEarningsAnalysisMapper extends SqlMapper{

	
	/**
	 * @Description 
	 * 开单科室收入分析
	 * @param  entityMap RowBounds
	 * @return List<CostOrderDeptEarnings>
	 * @throws DataAccessException
	*/
	public List<CostOrderDeptEarnings> queryCostOrderDeptEarnings(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 *  开单科室收入分析
	 * @param  entityMap RowBounds
	 * @return List<CostOrderDeptEarnings>
	 * @throws DataAccessException
	*/
	public List<CostOrderDeptEarnings> queryCostOrderDeptEarnings(Map<String,Object> entityMap) throws DataAccessException;
}
