package com.chd.hrp.cost.dao.analysis.c08;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C08Mapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 医院工作量情况
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0800(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<?> queryAnalysisC0800(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 指标分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0801(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 指标分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0801(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 指标分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0802(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 指标分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0802(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0801Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0802Print(
			Map<String, Object> entityMap)throws DataAccessException;
	
}
