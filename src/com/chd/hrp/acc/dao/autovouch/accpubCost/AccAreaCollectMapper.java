/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.autovouch.accpubCost;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 基本数字表<BR>
* @Author: XuXin
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccAreaCollectMapper extends SqlMapper{
	
	public int addAccPerson(Map<String,Object> entityMap)throws DataAccessException;
	
//	public int addBatchAccTarget(List<Map<String, Object>> list)throws DataAccessException;
//	
//	public int addAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
//	
//	public int addBatchAccTargetData(List<Map<String, Object>> list)throws DataAccessException;
//	
	public int updateAccPersonCollect(Map<String,Object> entityMap)throws DataAccessException;
	
 	public int updateAccPersonFtBl(Map<String,Object> entityMap)throws DataAccessException;
 	
 	public int updateAccPersonState(Map<String,Object> entityMap)throws DataAccessException;
 	
 	public int updateAccPersonDataFromLastMonth(Map<String,Object> entityMap)throws DataAccessException;
	
	public int deletePersonCollect(List<Map<String, Object>> list)throws DataAccessException;
	
	public int insertAccSysPerson(Map<String,Object> entityMap)throws DataAccessException;
	
	public int insertAccPersonByImport(List<Map<String, Object>> list)throws DataAccessException;
	
	public List<Map<String, Object>> queryAccPersonCollect(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryAccPersonCollect(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryDeptAllInfoDict(Map<String, Object> mapVo);
	
	public Map<String, Object> queryAccPersonByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccPersonByFirst(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryAccPersonByLastMonth(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryAccPersonById(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String, Object> queryTotalNum(Map<String,Object> entityMap) throws DataAccessException;
	
	public int queryQuoteOrNot(Map<String,Object> entityMap) throws DataAccessException;
//	
//	public int inheritDeleteAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
//
//	public int inheritAddAccTargetData(Map<String,Object> entityMap)throws DataAccessException;
//
//	public int deleteImportAccTargetData(List<Map<String, Object>> list)throws DataAccessException;
//
//	public int addImportAccTargetData(List<Map<String, Object>> list)throws DataAccessException;

	
	
}
