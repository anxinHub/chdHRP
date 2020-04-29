/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.hr.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.base.HrAccDeptAttr;



/**
* @Title. @Description.
* 部门字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface HrAccDeptAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 添加AccDeptAttr
	 * @param AccDeptAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量添加AccDeptAttr
	 * @param  AccDeptAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccDeptAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 查询AccDeptAttr分页
	 * @param  entityMap RowBounds
	 * @return List<AccDeptAttr>
	 * @throws DataAccessException
	*/
	public List<HrAccDeptAttr> queryAccDeptAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 部门字典属性表<BR> 查询AccDeptAttr所有数据
	 * @param  entityMap
	 * @return List<AccDeptAttr>
	 * @throws DataAccessException
	*/
	public List<HrAccDeptAttr> queryAccDeptAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 查询AccDeptAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrAccDeptAttr queryAccDeptAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 修改部门时<BR> 查询DeptByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrAccDeptAttr queryDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 添加部门时<BR> 查询HosDeptByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public HrAccDeptAttr queryHosDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public HrAccDeptAttr queryAccOutDeptByName(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 删除AccDeptAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量删除AccDeptAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccDeptAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 部门字典属性表<BR> 更新AccDeptAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccDeptAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 部门字典属性表<BR> 批量更新AccDeptAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccDeptAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryAccDeptAttrByDeptId(List<Map<String,Object>> list) throws DataAccessException;
	
}
