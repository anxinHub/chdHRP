
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptHip;


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
 


public interface AphiDeptHipService {

	/**
	 * @Description 
	 * 添加8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addAphiDeptHip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchAphiDeptHip(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateAphiDeptHip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchAphiDeptHip(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteAphiDeptHip(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchAphiDeptHip(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryAphiDeptHip(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiDeptHip queryAphiDeptHipByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询序列
	*/
	public int queryAphiDeptHipNextval(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 导入
	*/
	public String importAphiDeptHip(Map<String, Object> entityMap)throws DataAccessException;
}
