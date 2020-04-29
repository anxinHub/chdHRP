/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.books.sch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
* @Title. @Description.
* 账簿方案
* @Author: 
*/


public interface AccBookSchMapper extends SqlMapper{
	
	//查询方案列表
	public List<Map<String, Object>> queryAccBookSchList(Map<String,Object> entityMap)throws DataAccessException;
	
	//方案添加
	public int addAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案修改
	public int updateAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案条件删除
	public int deleteAccBookSchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案一般条件保存
	public int addAccBookSchCon(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案科目条件保存
	public int addAccBookSchSubj(List<Map<String, Object>> entityList) throws DataAccessException;
	
	//方案删除
	public int deleteAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询条件
	public Map<String, Object> queryAccBookSchById(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询方案一般条件
	public Map<String, Object> queryAccBookSchConById(Map<String, Object> entityMap) throws DataAccessException;

	//查询方案科目条件
	public List<Map<String, Object>> queryAccBookSchSubjById(Map<String, Object> entityMap) throws DataAccessException;
    
	//方案科目条件保存
  	public int addAccBookSchCheck(List<Map<String, Object>> entityList) throws DataAccessException;
  	
	//查询方案科目条件
  	public List<Map<String, Object>> queryAccBookSchCheckById(Map<String, Object> entityMap) throws DataAccessException;
  
  	public Map<String,Object> queryAccBookSchCheckTypeById(Map<String, Object> mapVo) throws DataAccessException;
  	
  	public Map<String,Object> queryAccBookSchCheckItemById(Map<String, Object> mapVo) throws DataAccessException;
  	
  	public List<Map<String, Object>> queryAccBookSchCheckNameBySchId(Map<String, Object> mapVo) throws DataAccessException;
}
