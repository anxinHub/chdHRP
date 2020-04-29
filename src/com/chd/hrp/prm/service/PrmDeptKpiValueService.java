
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmDeptDict;
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
 


public interface PrmDeptKpiValueService {

	/**
	 * @Description 
	 * 添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmDeptKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDeptKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmDeptKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptKpiValue(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptKpiValueScheme(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询对象0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmDeptKpiValue queryPrmDeptKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * CREATE
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String createDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public String auditPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reauditPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public Long queryPrmDeptKpiValueByAudit(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询 所有科室
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<PrmDeptDict> queryPrmDeptDictList(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0308 科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiValueByImport(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入  科室KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String importPrmDeptKpiValue(Map<String,Object> entityMap)throws DataAccessException;
}
