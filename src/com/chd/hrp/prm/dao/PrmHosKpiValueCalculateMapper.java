
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
 


public interface PrmHosKpiValueCalculateMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosKpiValueCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 批量更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiValue
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosKpiValueCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValueCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValueCalculate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValueSchemeCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValueSchemeCalculate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiValue
	 * @throws DataAccessException
	*/
	public PrmHosKpiValue queryPrmHosKpiValueCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiValue
	 * @throws DataAccessException
	*/
	public int auditPrmHosKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int cleanPrmHosKpiValueCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
}
