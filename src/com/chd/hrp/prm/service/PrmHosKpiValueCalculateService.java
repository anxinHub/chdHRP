
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.hrp.prm.entity.PrmHosKpiValue;
/**
 * 
 * @Description:
 * 0208 医院KPI指标数据准备表
 * @Table:
 * PRM_HOS_KPI_VALUE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface PrmHosKpiValueCalculateService {

	/**
	 * @Description 
	 * 添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmHosKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;

	
	public String updateBatchPrmHosKpiValueCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiValueSchemeCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0208 打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmHosKpiValueSchemeCalculatePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiValueCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmHosKpiValue queryPrmHosKpiValueCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * CREATE
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String createPrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String auditPrmHosKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String reauditPrmHosKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
}
