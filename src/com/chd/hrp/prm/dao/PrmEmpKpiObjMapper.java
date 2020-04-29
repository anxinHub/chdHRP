
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
import com.chd.hrp.prm.entity.PrmEmpKpiObj;
/**
 * 
 * @Description:
 * 0402 职工绩效方案核算对象表
 * @Table:
 * PRM_EMP_KPI_OBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpKpiObjMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmEmpKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmEmpKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmEmpKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiObj
	 * @throws DataAccessException
	*/
	public int updateBatchPrmEmpKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmEmpKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmEmpKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0402 职工绩效方案核算对象表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiObj> queryPrmEmpKpiObj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0402 职工绩效方案核算对象表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiObj> queryPrmEmpKpiObj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0402 职工绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiObj
	 * @throws DataAccessException
	*/
	public PrmEmpKpiObj queryPrmEmpKpiObjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 查询结果集0202 职工绩效方案核算对象表left join hos_info <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmEmpKpiObjEmp(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0202 职工绩效方案核算对象表left join hos_info <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmEmpKpiObjEmp(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集 查询员工信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiObj> queryPrmEmp(Map<String,Object> entityMap) throws DataAccessException;	
	
	/**
	 * @Description 
	 * 根据编码查询员工信息<BR>
	 * @param  entityMap
	 * @return PrmEmpKpiObj
	 * @throws DataAccessException
	*/
	public PrmEmpKpiObj queryPrmEmpByCode(Map<String,Object> entityMap) throws DataAccessException;
	
}
