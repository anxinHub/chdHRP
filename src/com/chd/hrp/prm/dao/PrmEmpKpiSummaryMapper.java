
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
 


public interface PrmEmpKpiSummaryMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmEmpKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiSummary
	 * @throws DataAccessException
	*/
	public int updateBatchPrmEmpKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmEmpKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmEmpKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0410 职工KPI考评总结表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiSummary> queryPrmEmpKpiSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0410 职工KPI考评总结表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiSummary> queryPrmEmpKpiSummary(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiSummary
	 * @throws DataAccessException
	*/
	public PrmEmpKpiSummary queryPrmEmpKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0410 职工KPI考评总结表<BR>全部  关联表   hos_emp   hos_emp_dict  aphi_dept
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiSummary> queryPrmEmpKpiSummaryEmpDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0410 职工KPI考评总结表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiSummary> queryPrmEmpKpiSummaryEmpDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0410 职工KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiSummary
	 * @throws DataAccessException
	*/
	public PrmEmpKpiSummary queryPrmEmpKpiSummaryEmpDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
