/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.mat.dao.dhc;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * @Description:  东华基础字典读取
 * @Table: 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MatDhcBusiMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 查询数据<BR>
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatDhcBusi(Map<String,Object> entityMap) throws DataAccessException;

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
	public int addMatInMain(List<Map<String,Object>> mainList)throws DataAccessException;
	public int addMatInDetail(List<Map<String, Object>> detailList)throws DataAccessException;
	public int addMatOutMain(List<Map<String,Object>> mainList)throws DataAccessException;
	public int addMatOutDetail(List<Map<String, Object>> detailList)throws DataAccessException;
	public int addMatPrePayMain(List<Map<String,Object>> mainList)throws DataAccessException;
	public int addMatPrePayDetail(List<Map<String, Object>> detailList)throws DataAccessException;
	public int addMatPayMain(List<Map<String,Object>> mainList)throws DataAccessException;
	public int addMatPayDetail(List<Map<String, Object>> detailList)throws DataAccessException;

	/**
	 * @Description 
	 * 覆盖时需删除HRP数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public int deleteMatInMain(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatInDetail(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatOutMain(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatOutDetail(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatPrePayMain(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatPrePayDetail(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatPayMain(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteMatPayDetail(Map<String,Object> entityMap)throws DataAccessException;
}
