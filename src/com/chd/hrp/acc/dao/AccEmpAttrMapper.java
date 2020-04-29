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

import com.chd.hrp.acc.entity.AccEmpAttr;

/**
* @Title. @Description.
* 职工字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEmpAttrMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 添加AccEmpAttr
	 * @param AccEmpAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量添加AccEmpAttr
	 * @param  AccEmpAttr entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttr分页
	 * @param  entityMap RowBounds
	 * @return List<AccEmpAttr>
	 * @throws DataAccessException
	*/
	public List<AccEmpAttr> queryAccEmpAttr(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttr所有数据
	 * @param  entityMap
	 * @return List<AccEmpAttr>
	 * @throws DataAccessException
	*/
	public List<AccEmpAttr> queryAccEmpAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttrByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccEmpAttr queryAccEmpAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 修改职工时<BR> 查询EmpByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccEmpAttr queryEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 添加职工时<BR> 查询HosEmpByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccEmpAttr queryHosEmpByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 删除AccEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量删除AccEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 职工字典属性表<BR> 更新AccEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量更新AccEmpAttr
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 根据职工id查询身份证号以及账户名
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public List<AccEmpAttr> queryAccEmpNumber(Map<String,Object> entityMap)throws DataAccessException;
	
	public int initAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	public int updateAccEmpAttrById(Map<String,Object> entityMap)throws DataAccessException;
	
	public int addAccEmpAttrById(Map<String,Object> entityMap)throws DataAccessException;
	
}
