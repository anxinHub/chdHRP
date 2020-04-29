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

/**
 * @Title. @Description. 成本构成变化趋势<BR>
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public interface CostConstituteChangeAnalysisMapper extends SqlMapper {

	/**
	 * @Description 成本构成变化趋势表<BR>
	 *              查询CostConstituteChange分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostConstituteChange>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostConstituteChange(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 成本构成变化趋势<BR>
	 *              查询CostConstituteChange分页
	 * @param entityMap
	 *            RowBounds
	 * @return List<CostConstituteChange>
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryCostConstituteChange(Map<String, Object> entityMap) throws DataAccessException;
}