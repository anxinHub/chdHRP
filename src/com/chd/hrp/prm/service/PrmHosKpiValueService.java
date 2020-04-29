
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 


public interface PrmHosKpiValueService {

	/**
	 * @Description 
	 * 添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmHosKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmHosKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmHosKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiValueScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiValue(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmHosKpiValue queryPrmHosKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * CREATE
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String createPrmHosTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String auditPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reauditPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String importPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
}
