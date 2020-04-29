
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
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
 


public interface PrmHosKpiSummaryService {

	/**
	 * @Description 
	 * 添加0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmHosKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmHosKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmHosKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmHosKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0210 医院KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmHosKpiSummary queryPrmHosKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0210 医院KPI考评总结表<BR>  left join HOS_INFO
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmHosKpiSummary queryPrmHosKpiSummaryHosInfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
