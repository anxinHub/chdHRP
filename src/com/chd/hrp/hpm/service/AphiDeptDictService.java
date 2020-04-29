
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptDict;
/**
 * 
 * @Description:
 * 8804 科室字典变更表
 * @Table:
 * Prm_DEPT_DICT
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiDeptDictService {

	/**
	 * @Description 
	 * 添加8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmDeptDict(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmDeptDict(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象8804 科室字典变更表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public AphiDeptDict queryPrmDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;

	//科室与收入项目关系配置
	public String queryDeptIncomeAdd(Map<String, Object> page) throws DataAccessException;
	
}
