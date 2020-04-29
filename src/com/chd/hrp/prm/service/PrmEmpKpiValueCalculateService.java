
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

import com.chd.hrp.prm.entity.PrmEmpKpiValueCalculate;
/**
 * 
 * @Description:
 * 0308 职工KPI指标数据准备表
 * @Table:
 * PRM_EMP_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpKpiValueCalculateService {

	/**
	 * @Description 
	 * 添加0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	

	
	public String updateBatchPrmEmpKpiValueCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpiValueCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpiValueSchemeCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 打印结果集0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryPrmEmpKpiValueSchemeCalculatePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpKpiValueCalculate queryPrmEmpKpiValueCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
    public String createPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String auditPrmEmpKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reAuditPrmEmpKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
}
