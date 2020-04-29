
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmDept;
/**
 * 
 * @Description:
 * 8801 科室字典表
 * @Table:
 * Prm_DEPT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptService {

	/**
	 * @Description 
	 * 添加8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部  该查询结果关联科室分类字典表、科室性质字典表
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	
	public String queryPrmDept_DeptKind_DeptNature(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询对象8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmDept queryPrmDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部  该查询结果关联科室分类字典表、科室性质字典表 where dept_id
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public PrmDept queryPrmDeptByCode_DeptKind_DeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部  该查询结果关联科室分类字典表、科室性质字典表  where dept_code
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public PrmDept queryPrmDeptByCode_DeptKind_DeptNatureDeptCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询序列
	*/
	public int queryPrmDeptNextval(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入
	*/
	public String importPrmDept(Map<String, Object> entityMap)throws DataAccessException;

	
}
