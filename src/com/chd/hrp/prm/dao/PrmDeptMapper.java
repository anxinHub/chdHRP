
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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
 


public interface PrmDeptMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDept(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return PrmDept
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDept(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDept(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDept> queryPrmDept(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部  该查询结果关联科室分类字典表、科室性质字典表
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	
	public List<PrmDept> queryPrmDept_DeptKind_DeptNature(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDept> queryPrmDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部  该查询结果关联科室分类字典表、科室性质字典表
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDept> queryPrmDept_DeptKind_DeptNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取8801 科室字典表<BR> 
	 * @param  entityMap
	 * @return PrmDept
	 * @throws DataAccessException
	*/
	public PrmDept queryPrmDeptByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集8801 科室字典表<BR>全部  该查询结果关联科室分类字典表、科室性质字典表
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public PrmDept queryPrmDeptByCode_DeptKind_DeptNature(Map<String,Object> entityMap)throws DataAccessException;
	
	public PrmDept queryPrmDeptByCode_DeptKind_DeptNatureDeptCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 科室主表<BR>
	 *              查询序列
	 * @param entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int queryPrmDeptNextval() throws DataAccessException;
	
	/**
	 * @Description 
	 * 按科室编码查询 科室表<BR>
	 * @param  entityMap
	 * @return AphiDept
	 * @throws DataAccessException
	*/
	public PrmDept queryPrmDeptByDeptCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询全部  科室<BR>
	 * @param  entityMap
	 * @return AphiDept
	 * @throws DataAccessException
	*/
	public List<PrmDept> queryPrmDeptAll(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询全部  科室<BR>
	 * @param  entityMap
	 * @return AphiDept
	 * @throws DataAccessException
	 */
	public List<PrmDept> queryPrmDeptRelaByMaping(Map<String,Object> entityMap) throws DataAccessException;
}
