/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.sys.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

import com.chd.hrp.sys.entity.Info;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface InfoMapper extends SqlMapper{
	
	/**
	 * @Description 查询刚查询序列号
	 * @param   
	 * @return long
	 * @throws DataAccessException
	*/
	public long queryCurrentSequence()throws DataAccessException;
	
	/**
	 * @Description 添加Info
	 * @param Info entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Info
	 * @param  Info entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchInfo(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Info分页
	 * @param  entityMap RowBounds
	 * @return List<Info>
	 * @throws DataAccessException
	*/
	public List<Info> queryInfo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 查询最大的Info排序
	 * @param  entityMap RowBounds
	 * @return List<Info>
	 * @throws DataAccessException
	*/
	public int queryInfoSortMax(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询Info所有数据
	 * @param  entityMap
	 * @return List<Info>
	 * @throws DataAccessException
	*/
	public List<Info> queryInfo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询InfoByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Info queryInfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Info
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Info
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchInfo(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Info
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateInfo(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新InfoCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateInfoCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新InfoName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateInfoName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Info
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchInfo(List<Map<String, Object>> entityMap)throws DataAccessException;
}
