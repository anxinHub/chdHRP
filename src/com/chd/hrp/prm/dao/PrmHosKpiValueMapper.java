
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
import com.chd.hrp.sys.entity.HosDict;
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
 


public interface PrmHosKpiValueMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiValue
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmHosKpiValue(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValue(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValue(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValueScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0208 医院KPI指标数据准备表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiValue> queryPrmHosKpiValueScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0208 医院KPI指标数据准备表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiValue
	 * @throws DataAccessException
	*/
	public PrmHosKpiValue queryPrmHosKpiValueByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiValue
	 * @throws DataAccessException
	*/
	public int auditPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public int cleanPrmHosKpiValue(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<HosDict> queryHosInfoByGroupId(Map<String,Object> entityMap)throws DataAccessException;
}
