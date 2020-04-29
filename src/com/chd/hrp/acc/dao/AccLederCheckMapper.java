/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccLederCheck;

/**
* @Title. @Description.
* 财务辅助账表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccLederCheckMapper extends SqlMapper{
	
	public  int addBatchAccLederCheck(Map<String, Object> entityList) throws DataAccessException;
	
	public int deleteAccLederCheck(Map<String, Object> entityList) throws DataAccessException;
	
	public int deleteAccLederCheckJz(Map<String, Object> entityList) throws DataAccessException;
	
	public  int deleteBatchAccLederCheck(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public int addAccLederCheckByCopy(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteAccLederCheckByCopy(Map<String, Object> entityMap)throws DataAccessException;

	public int deleteAccLederCheckBatch(List<Map<String, Object>> saveList);

	public int addAccLederCheckBatch(List<Map<String, Object>> saveList);
} 
