
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.dao;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.prm.entity.PrmDeptScheme;
/**
 * 
 * @Description:
 * 0303 科室绩效方案表
 * @Table:
 * PRM_DEPT_SCHEME
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptSchemeMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return PrmDeptScheme
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptScheme(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int deleteBatchPrmDeptSchemeByImport(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptScheme> queryPrmDeptScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptScheme> queryPrmDeptScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptScheme> queryPrmDeptSchemeLeftName(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0303 科室绩效方案表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptScheme> queryPrmDeptSchemeLeftName(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0303 科室绩效方案表<BR> 
	 * @param  entityMap
	 * @return PrmDeptScheme
	 * @throws DataAccessException
	*/
	public PrmDeptScheme queryPrmDeptSchemeByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public PrmDeptScheme queryPrmDeptSchemeMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public List<PrmDeptScheme> queryPrmDeptSchemeBySuperKpiCode(Map<String,Object> entityMap) throws DataAccessException;


	
}
