
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.prm.entity.PrmEmpKpiSummary;
/**
 * 
 * @Description:
 * 0410 职工KPI考评总结表
 * @Table:
 * PRM_EMP_KPI_SUMMARY
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpKpiSummaryService {

	/**
	 * @Description 
	 * 添加0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmEmpKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmEmpKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmEmpKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpKpiSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpKpiSummary queryPrmEmpKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询对象0410 职工KPI考评总结表<BR>  left join  aphi_dept_dict 和 prm_emp_dict 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpKpiSummary queryPrmEmpKpiSummaryEmpDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
