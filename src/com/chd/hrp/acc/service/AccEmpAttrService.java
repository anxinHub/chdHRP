/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccEmpAttr;

/**
* @Title. @Description.
* 职工字典属性表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEmpAttrService {

	/**
	 * @Description 
	 * 职工字典属性表<BR> 添加AccEmpAttr
	 * @param AccEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量添加AccEmpAttr
	 * @param  AccEmpAttr entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttr分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccEmpAttr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 查询AccEmpAttrByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccEmpAttr queryAccEmpAttrByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 修改职工时<BR> 查询EmpByCode
	 * @param  entityMap 
	 * @return String JSON
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
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量删除AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 更新AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 批量更新AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccEmpAttr(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 职工字典属性表<BR> 导入AccEmpAttr
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 根据职工id查询身份证号
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccEmpNumber(Map<String,Object> entityMap)throws DataAccessException;
	
	public String initAccEmpAttr(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
