package com.chd.hrp.ass.dao.check.mobile;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


public interface AssCheckMobileMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 更新数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int generate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public List<?> queryRepairs(Map<String, Object> entityMap)throws DataAccessException;
	public int repairsGenerate(List<?> entityList)throws DataAccessException;
	public String queryUserId(Map<String, Object> entityMap)throws DataAccessException;
	public int existsAssRepairs(Map<String, Object> entityMap)throws DataAccessException;
	
	public List<?> queryPolling(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryPollingDetail(Map<String, Object> entityMap)throws DataAccessException;
	public int addAssInspectionPollMain(Map<String, Object> entityMap) throws DataAccessException;
	public int addAssInspectionPollDetail(List<?> entityList)throws DataAccessException;
	public int queryPollingByCode(Map<String, Object> entityMap)throws DataAccessException;
	public Long queryPollingSequence()throws DataAccessException;
	
	public Long queryMainTainSequence()throws DataAccessException;
	public List<?> queryMainTain(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMainTainDetail(Map<String, Object> entityMap)throws DataAccessException;
	public int addAssInspectionMTMain(Map<String, Object> entityMap) throws DataAccessException;
	public int addAssInspectionMTDetail(List<?> entityList)throws DataAccessException;
	public int queryMainTainByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	public Long queryMeasureSequence()throws DataAccessException;
	public List<?> queryMeasure(Map<String, Object> entityMap)throws DataAccessException;
	public List<?> queryMeasureDetail(Map<String, Object> entityMap)throws DataAccessException;
	public int addAssInspectionMeasureMain(Map<String, Object> entityMap) throws DataAccessException;
	public int addAssInspectionMeasureDetail(List<?> entityList)throws DataAccessException;
	public int queryMeasureByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMoblieContrast(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryMoblieContrast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryCheckContrast(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryCheckContrast(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryCheckContrastDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryCheckContrastDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryCheckContrastStore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryCheckContrastStore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryCheckContrastHouseLand(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	 */
	public List<?> queryCheckContrastHouseLand(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
