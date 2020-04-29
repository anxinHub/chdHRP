/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.AccBusiMap;
import com.chd.hrp.acc.entity.autovouch.SysBusiTable;

public interface AccBusiMapMapper extends SqlMapper {

	/**
	 * 添加自动凭证元素科目配置表
	 * 
	 * */
	public int addAccBusiMap(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除自动凭证元素科目配置表
	 * 
	 * */
	public int deleteAccBusiMap(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除自动凭证元素科目配置表
	 * 
	 * */
	public AccBusiMap queryAccBusiMapByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 根据表名查询字段
	 * 
	 * */
	public SysBusiTable querySysBusiTableByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据表名查询字段
	 * 
	 * */
	public List<SysBusiTable> querySysBusiTable(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据表名查询字段
	 * 
	 * */
	public List<Map<String,Object>> querySelectSql(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据业务表最大级别
	 * 
	 * */
	public Integer maxTypeLevel(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public Map<String,Object> querySysBbusiTable(Map<String, Object> entityMap) throws DataAccessException;
	
    
	public List<Map<String,Object>> queryTableIdSql(Map<String, Object> entityMap) throws DataAccessException;
	
	
	public int addBatchAccBusiMap( List<Map<String,Object>> entityList) throws DataAccessException;

	/**
	 * 删除自动凭证元素科目配置表
	 * 
	 * */
	public int deleteBatchAccBusiMap(List<Map<String,Object>> entityList) throws DataAccessException;
	
}
