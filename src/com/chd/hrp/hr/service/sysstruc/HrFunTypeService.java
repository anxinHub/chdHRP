
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hr.service.sysstruc;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.sysstruc.HrFunType;


/**
 * 
 * @Description:
 * 9909 绩效函数分类表
 * @Table:
 * PRM_FUN_TYPE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface HrFunTypeService {

	/**
	 * @Description 
	 * 添加9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmFunType(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmFunType(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmFunType(List<HrFunType> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmFunType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象9909 绩效函数分类表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public HrFunType queryPrmFunTypeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 函数分类树
	 */
	public String queryPrmFunTree(Map<String,Object> entityMap) throws DataAccessException;
}
