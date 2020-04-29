
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
import com.chd.hrp.prm.entity.PrmDeptKpiObj;
/**
 * 
 * @Description:
 * 0302 科室绩效方案核算对象表
 * @Table:
 * PRM_DEPT_KPI_OBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptKpiObjMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiObj
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0302 科室绩效方案核算对象表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiObj> queryPrmDeptKpiObj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0302 科室绩效方案核算对象表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiObj> queryPrmDeptKpiObj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0302 科室绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiObj
	 * @throws DataAccessException
	*/
	public PrmDeptKpiObj queryPrmDeptKpiObjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 查询结果集0202 科室绩效方案核算对象表left join dept <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmDeptKpiObjDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0202 科室绩效方案核算对象表left join dept <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmDeptKpiObjDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集 查询科室信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiObj> queryPrmDept(Map<String,Object> entityMap) throws DataAccessException;	
}
