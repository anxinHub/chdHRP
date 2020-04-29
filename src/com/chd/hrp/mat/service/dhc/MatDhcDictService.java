/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.dhc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:  东华基础字典读取
 * @Table: 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatDhcDictService{

	/**
	 * @Description 
	 * 查询数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDhcDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String,Object> impMatDhcDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询东华数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDhcDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 插入HRP数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> addMatTypeDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addMatInvDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addHosStoreDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addHosDeptDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addHosSupDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addMatSourceDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addMatPayTypeDict(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> addMatProjDict(List<Map<String,Object>> entityList)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String,Object> deleteMatDhcDict(List<Map<String,Object>> entityList)throws DataAccessException;
}
