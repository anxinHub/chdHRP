/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.sys.entity.Fac;
 
/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface FacService {

	/**
	 * @Description 添加Fac
	 * @param Fac entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addFac(Map<String,Object> entityMap)throws DataAccessException;
	
	public String addFacTz(Map<String,Object> entityMap)throws DataAccessException;
	public String addFacType(Map<String,Object> entitMap)throws DataAccessException;
	/**
	 * @Description 批量添加Fac
	 * @param  Fac entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchFac(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Fac分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryFac(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询FacByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Fac queryFacByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteFac(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchFac(String param)throws DataAccessException;
	
	/**
	 * @Description 更新Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateFac(Map<String,Object> entityMap)throws DataAccessException;
	
	public String updateFacTz(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchFac(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Fac
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importFac(Map<String,Object> entityMap)throws DataAccessException;
	public String addGroupFac(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Fac
	 * @param  Fac entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchFac(List<Map<String, Object>> entityMap)throws DataAccessException;
}
