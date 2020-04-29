package com.chd.hrp.cost.dao.analysis.c07;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C07Mapper extends SqlMapper {

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0701(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0701(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0702(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0702(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0703(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0703(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 *            RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0704(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 单元分析数据访问类<BR>
	 *              查询不带分页
	 * @param entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0704(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0701Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0702Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0703Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0704Print(
			Map<String, Object> entityMap) throws DataAccessException;

}
