/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Proj;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ProjService {

	/**
	 * @Description 添加Proj
	 * @param Proj entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Proj
	 * @param  Proj entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchProj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Proj分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryProj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ProjByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Proj queryProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Proj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Proj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchProj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Proj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjName
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateProjName(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新ProjCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateProjCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Proj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchProj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Proj
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importProj(Map<String,Object> entityMap)throws DataAccessException;
	
}
