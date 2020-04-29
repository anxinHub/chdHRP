
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.prm.entity.PrmDeptKpiSummary;
/**
 * 
 * @Description:
 * 0310 科室KPI考评总结表
 * @Table:
 * PRM_DEPT_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptKpiSummaryService {

	/**
	 * @Description 
	 * 添加0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmDeptKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDeptKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmDeptKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptKpiSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmDeptKpiSummary queryPrmDeptKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取对象0310 科室KPI考评总结表<BR>  left join aphi_dept_dict 和  left join aphi_dept_kind
	 * @param  entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public PrmDeptKpiSummary queryPrmDeptKpiSummaryDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
