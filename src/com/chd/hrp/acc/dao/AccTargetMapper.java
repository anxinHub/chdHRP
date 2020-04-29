/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 基本数字表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccTargetMapper extends SqlMapper{
	
	public int addAccTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchAccTarget(List<Map<String, Object>> list)throws DataAccessException;
	
	public int addAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addBatchAccTargetData(List<Map<String, Object>> list)throws DataAccessException;
	
	public int updateAccTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAccTarget(List<Map<String, Object>> list)throws DataAccessException;
	
	public int deleteAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deleteBatchAccTargetData(List<Map<String, Object>> list)throws DataAccessException;

	public List<Map<String, Object>> queryAccTarget(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public Map<String, Object> queryAccTargetByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccTargetDataByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public int inheritDeleteAccTargetData(Map<String,Object> entityMap)throws DataAccessException;

	public int inheritAddAccTargetData(Map<String,Object> entityMap)throws DataAccessException;

	public int deleteImportAccTargetData(List<Map<String, Object>> list)throws DataAccessException;

	public int addImportAccTargetData(List<Map<String, Object>> list)throws DataAccessException;

	
	
}
