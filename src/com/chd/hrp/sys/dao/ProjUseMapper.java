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

import com.chd.hrp.sys.entity.ProjUse;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjUseMapper extends SqlMapper{
	
	/**
	 * @Description 添加ProjUse
	 * @param ProjUse entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加ProjUse
	 * @param  ProjUse entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchProjUse(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询ProjUse分页
	 * @param  entityMap RowBounds
	 * @return List<ProjUse>
	 * @throws DataAccessException
	*/
	public List<ProjUse> queryProjUse(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 查询ProjUse所有数据
	 * @param  entityMap
	 * @return List<ProjUse>
	 * @throws DataAccessException
	*/
	public List<ProjUse> queryProjUse(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjUseByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public ProjUse queryProjUseByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除ProjUse
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除ProjUse
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchProjUse(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 更新ProjUse
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateProjUse(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新ProjUse
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchProjUse(List<Map<String, Object>> entityMap)throws DataAccessException;
}
