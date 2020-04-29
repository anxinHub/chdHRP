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

import com.chd.hrp.sys.entity.Political;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface PoliticalMapper extends SqlMapper{
	
	/**
	 * @Description 添加Political
	 * @param Political entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPolitical(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Political
	 * @param  Political entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPolitical(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Political分页
	 * @param  entityMap RowBounds
	 * @return List<Political>
	 * @throws DataAccessException
	*/
	public List<Political> queryPolitical(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询Political所有数据
	 * @param  entityMap
	 * @return List<Political>
	 * @throws DataAccessException
	*/
	public List<Political> queryPolitical(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询PoliticalByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public Political queryPoliticalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Political
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePolitical(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Political
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPolitical(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新Political
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePolitical(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Political
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchPolitical(List<Map<String, Object>> entityMap)throws DataAccessException;
}
