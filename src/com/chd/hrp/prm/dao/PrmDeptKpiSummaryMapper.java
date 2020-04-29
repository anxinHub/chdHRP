
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
 


public interface PrmDeptKpiSummaryMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiSummary
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKpiSummary(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKpiSummary(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0310 科室KPI考评总结表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiSummary> queryPrmDeptKpiSummary(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0310 科室KPI考评总结表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiSummary> queryPrmDeptKpiSummary(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0310 科室KPI考评总结表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiSummary
	 * @throws DataAccessException
	*/
	public PrmDeptKpiSummary queryPrmDeptKpiSummaryByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0310 科室KPI考评总结表<BR>全部  关联表 aphi_dept_kind   aphi_dept_dict
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiSummary> queryPrmDeptKpiSummaryDeptDict(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0310 科室KPI考评总结表<BR>带分页  关联表 aphi_dept_kind   aphi_dept_dict
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiSummary> queryPrmDeptKpiSummaryDeptDict(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0310 科室KPI考评总结表<BR>  关联表 aphi_dept_kind   aphi_dept_dict
	 * @param  entityMap
	 * @return PrmDeptKpiSummary
	 * @throws DataAccessException
	*/
	public PrmDeptKpiSummary queryPrmDeptKpiSummaryDeptDictByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
