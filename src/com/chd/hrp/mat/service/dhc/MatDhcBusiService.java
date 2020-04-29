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
 
public interface MatDhcBusiService{

	/**
	 * @Description 
	 * 查询数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatDhcBusi(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String,Object> impMatDhcBusi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询东华数据<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryDhcInMainBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcInDetailBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcOutMainBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcOutDetailBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcPrePayMainBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcPrePayDetailBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcPayMainBusi(Map<String,Object> entityMap)throws DataAccessException;
	public List<Map<String, Object>> queryDhcPayDetailBusi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 插入HRP数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> addMatIn(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException;
	public Map<String, Object> addMatOut(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException;
	public Map<String, Object> addMatPrePay(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException;
	public Map<String, Object> addMatPay(List<Map<String,Object>> mainList, List<Map<String, Object>> detailList)throws DataAccessException;

	/**
	 * @Description 
	 * 覆盖时需删除HRP数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String, Object> deleteMatIn(Map<String,Object> mainMap)throws DataAccessException;
	public Map<String, Object> deleteMatOut(Map<String,Object> entityMap)throws DataAccessException;
	public Map<String, Object> deleteMatPrePay(Map<String,Object> entityMap)throws DataAccessException;
	public Map<String, Object> deleteMatPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除数据<BR> 
	 * @param  entityList
	 * @return String
	 * @throws DataAccessException
	*/
	public Map<String,Object> deleteMatDhcBusi(List<Map<String,Object>> entityList)throws DataAccessException;
}
