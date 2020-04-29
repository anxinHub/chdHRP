
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

import com.chd.hrp.prm.entity.PrmHosKpiSummary;
/**
 * 
 * @Description:
 * 0210 医院KPI考评总结表
 * @Table:
 * PRM_HOS_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmHosKpiSummaryMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiSummary
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmHosKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0210 医院KPI考评总结表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiSummary> queryPrmHosKpiSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0210 医院KPI考评总结表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiSummary> queryPrmHosKpiSummary(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiSummary
	 * @throws DataAccessException
	*/
	public PrmHosKpiSummary queryPrmHosKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0210 医院KPI考评总结表<BR>全部 关联 hos_info
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiSummary> queryPrmHosKpiSummaryHosInfo(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0210 医院KPI考评总结表<BR>带分页  关联 hos_info
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiSummary> queryPrmHosKpiSummaryHosInfo(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取0210 医院KPI考评总结表<BR> 关联 hos_info
	 * @param  entityMap
	 * @return PrmHosKpiSummary
	 * @throws DataAccessException
	*/
	public PrmHosKpiSummary queryPrmHosKpiSummaryHosInfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	 
	
}
