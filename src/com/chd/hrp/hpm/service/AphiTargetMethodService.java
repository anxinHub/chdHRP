
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.service;
import java.util.List;
import java.util.Map;

import javax.script.ScriptException;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiTargetMethod;

/**
 * 
 * @Description:
 * 9904 绩效指标取值方法配置表
 * @Table:
 * PRM_TARGET_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiTargetMethodService {

	/**
	 * @Description 
	 * 添加9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmTargetMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmTargetMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmTargetMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmTargetMethod(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiTargetMethod queryPrmTargetMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集9903 指标取值方法参数表<BR>  关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmTargetMethodNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9904 绩效指标取值方法配置表<BR>  关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiTargetMethod queryPrmTargetMethodNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	public Object target_value(Map<String, Object> entityMap)throws DataAccessException, ScriptException;
	
}
