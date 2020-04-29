/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Mod;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface ModService {

	/**
	 * @Description 添加Mod
	 * @param Mod entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addMod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Mod
	 * @param  Mod entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchMod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Mod分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryMod(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryModList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询ModByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Mod queryModByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteMod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchMod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateMod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchMod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Mod
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importMod(Map<String,Object> entityMap)throws DataAccessException;
	

	public String querySysModList(Map<String,Object> entityMap)throws DataAccessException;
	
}
