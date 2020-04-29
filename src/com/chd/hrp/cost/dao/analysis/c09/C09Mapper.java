package com.chd.hrp.cost.dao.analysis.c09;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C09Mapper extends SqlMapper{
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0901(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

    public int deleteCostAnalysisC0901(Map<String,Object> entityMap)throws DataAccessException;
	public int addCostAnalysisC0901(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC0901(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0902(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteCostAnalysisC0902(Map<String,Object> entityMap)throws DataAccessException;
	public int addCostAnalysisC0902(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0902(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0903(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteCostAnalysisC0903(Map<String,Object> entityMap)throws DataAccessException;
	public int addCostAnalysisC0903(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0903(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0904(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteCostAnalysisC0904(Map<String,Object> entityMap)throws DataAccessException;
	public int addCostAnalysisC0904(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0904(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0905(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteCostAnalysisC0905(Map<String,Object> entityMap)throws DataAccessException;
	public int addCostAnalysisC0905(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0905(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0906(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public int deleteCostAnalysisC0906(Map<String,Object> entityMap)throws DataAccessException;
	public int addCostAnalysisC0906(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryAnalysisC0906(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0901Print(
			Map<String, Object> entityMap)  throws DataAccessException;

	public List<Map<String, Object>> queryC0902Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0903Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0904Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0905Print(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC0906Print(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
