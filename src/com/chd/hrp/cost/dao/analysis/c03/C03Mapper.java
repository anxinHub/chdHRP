package com.chd.hrp.cost.dao.analysis.c03;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C03Mapper extends SqlMapper{
	/**
	 * @Description 
	 * 收入分类分析 c0301<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0301(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public int deleteCostAnalysisC0301(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addCostAnalysisC0301(Map<String,Object> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 收入分类分析 c0301<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0301(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0302(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0302(Map<String,Object> entityMap) throws DataAccessException;
	
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
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0304(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
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
	public List<?> queryAnalysisC0305(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0305(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0306(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0306(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0307(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0307(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0308(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 分类分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0308(Map<String,Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0301Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0302Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0303Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0304Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0305Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0306Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0307Print(
			Map<String, Object> entityMap) throws DataAccessException;


	public List<Map<String, Object>> queryC0308Print(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
