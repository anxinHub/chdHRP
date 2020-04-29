﻿
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiTarget;
/**
 * 
 * @Description:
 * 9901 绩效指标字典表
 * @Table:
 * PRM_TARGET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiTargetService {

	/**
	 * @Description 
	 * 添加9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmTarget(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmTarget(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmTarget(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiTarget queryPrmTargetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmTargetNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiTarget queryPrmTargetNatureGetByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String queryTargetTree(Map<String, Object> entityMap)throws DataAccessException;

	//导入
	public String hpmTargetImport(Map<String, Object> entityMap) throws DataAccessException;
}