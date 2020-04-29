package com.chd.hrp.htc.service.info.basic;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HtcDeptDictService {
	
	
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 添加AccDeptAttr
	 * @param AccDeptAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addHtcDeptAttrDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 更新AccDeptAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateHtcDeptAttrDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量管理
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String htcDeptDictManage(List<Map<String,Object>> listVo)throws DataAccessException;
	
	/**
	 * @Description 获取部门字典树形结构<BR>
	 * @param entityMap
	 *            需要检索的唯一性字段
	 * @return AssTypeDict
	 * @throws DataAccessException
	 */
	public List<?> queryHtcDeptDictByTree(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DeptDict分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryHtcDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> queryHtcDeptAttrByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	public Map<String,Object> queryHtcDeptDictByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	
}
