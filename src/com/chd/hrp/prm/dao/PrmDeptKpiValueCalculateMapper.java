
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
import com.chd.hrp.prm.entity.PrmDeptKpiValueCalculate;
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
 


public interface PrmDeptKpiValueCalculateMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchPrmDeptKpiValueCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiValueCalculate> queryPrmDeptKpiValueSchemeCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiValueCalculate> queryPrmDeptKpiValueSchemeCalculate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiValue
	 * @throws DataAccessException
	*/
	public PrmDeptKpiValue queryPrmDeptKpiValueCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int auditPrmDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	public int cleanPrmDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
}
