/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccProjAttr;
import com.chd.hrp.acc.entity.AccProjAttr;

/**
* @Title. @Description.
* 项目字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccProjAttrService {

	/**
	 * @Description 
	 * 项目字典属性表<BR> 添加AccProjAttr
	 * @param AccProjAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量添加AccProjAttr
	 * @param  AccProjAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccProjAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccProjAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 查询AccProjAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccProjAttr queryAccProjAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 修改项目时<BR> 查询ProjByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccProjAttr queryProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加项目时<BR> 查询HosProjByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccProjAttr queryHosProjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public AccProjAttr queryAccProjAttrByProj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 删除AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量删除AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccProjAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 更新AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 批量更新AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccProjAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 项目字典属性表<BR> 导入AccProjAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccProjAttr(Map<String,Object> entityMap)throws DataAccessException;
	
}
