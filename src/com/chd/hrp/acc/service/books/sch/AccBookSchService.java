/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.books.sch;
import java.util.Map;

import org.springframework.dao.DataAccessException;


/**
 * 账簿方案
 * @author
 *
 */ 
public interface AccBookSchService {
	
	//查询方案树形列表
	public String queryAccBookSchListForTree(Map<String,Object> entityMap)throws DataAccessException;
	
	//查询方案列表
	public String queryAccBookSchList(Map<String,Object> entityMap)throws DataAccessException;
	
	//方案保存
	public Map<String, Object> saveAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案条件保存
	public Map<String, Object> saveAccBookSchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案删除
	public Map<String, Object> deleteAccBookSch(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案查询 
	public Map<String, Object> queryAccBookSchById(Map<String, Object> entityMap) throws DataAccessException;
	
	//方案条件查询
	public Map<String, Object> queryAccBookSchDetailById(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询科目树形列表
	public String queryAccSubjListForTree(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccBookSchCheckNameBySchId(Map<String, Object> entityMap) throws DataAccessException;
}
