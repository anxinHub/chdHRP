
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmEmpKpiValue;
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
 


public interface PrmEmpKpiValueService {

	/**
	 * @Description 
	 * 添加0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmEmpKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmEmpKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmEmpKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmEmpKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmEmpKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpiValue(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpiValueScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0308 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpKpiValue queryPrmEmpKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
    public String createPrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String auditPrmEmpKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reAuditPrmEmpKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入 职工KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String importPrmEmpKpiValue(Map<String,Object> entityMap)throws DataAccessException;
}
