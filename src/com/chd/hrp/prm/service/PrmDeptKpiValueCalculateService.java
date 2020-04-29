
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 


public interface PrmDeptKpiValueCalculateService {

	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDeptKpiValueCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptKpiValueSchemeCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 打印结果集0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrmDeptKpiValueSchemeCalculatePrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询对象0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmDeptKpiValue queryPrmDeptKpiValueCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * CREATE
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String createDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditPrmDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String reauditPrmDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String collectDeptKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
}
