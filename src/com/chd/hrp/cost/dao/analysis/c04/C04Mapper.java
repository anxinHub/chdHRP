package com.chd.hrp.cost.dao.analysis.c04;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C04Mapper extends SqlMapper{
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0401(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	public int addAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public int deleteAnalysisC0401(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0402(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0402(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0403(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0403(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public int addAnalysisC0403(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public int deleteAnalysisC0403(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0404(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 习性分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0404(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0401Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0403Print(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
