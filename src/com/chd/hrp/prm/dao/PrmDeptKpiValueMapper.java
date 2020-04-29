
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

import com.chd.hrp.prm.entity.PrmDeptKpiValue;
/**
 * 
 * @Description:
 * 0308 科室KPI指标数据准备表
 * @Table:
 * PRM_DEPT_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptKpiValueMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiValue
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiValue> queryPrmDeptKpiValue(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiValue> queryPrmDeptKpiValue(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiValue> queryPrmDeptKpiValueScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiValue> queryPrmDeptKpiValueScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiValue
	 * @throws DataAccessException
	*/
	public PrmDeptKpiValue queryPrmDeptKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int auditPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public int cleanPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryPrmDeptKpiValueByAudit(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
