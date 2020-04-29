package com.chd.hrp.cost.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface CostEarningsMapper extends SqlMapper {

	/**
	 * @Description 结余分析数据访问类<BR>
	 *              查询带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0101(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	

	/**
	 * @Description 结余分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0101(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 结余分析数据访问类<BR>
	 *              查询带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0102(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	
	/**
	 * @Description 结余分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0102(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 结余分析数据访问类<BR>
	 *              查询带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0103(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
	
	/**
	 * @Description 结余分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0103(Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryAnalysisC0104(
			Map<String, Object> entityMap)throws DataAccessException;


	public List<Map<String, Object>> queryAnalysisC0104(
			Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	

}
