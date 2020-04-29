
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.prm.entity.PrmDeptKpiScore;
/**
 * 
 * @Description:
 * 0309 科室KPI指标考评计算表
 * @Table:
 * PRM_DEPT_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptKpiScoreService {

	/**
	 * @Description 
	 * 添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmDeptKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmDeptKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmDeptKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptKpiScore(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmDeptKpiScoreDept(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 打印结果集0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmDeptKpiScoreDeptPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmDeptKpiScore queryPrmDeptKpiScoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String auditPrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reAuditPrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	public String collectDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryPrmDeptKpiScoreByScheme(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryPrmDeptKpiScoreBySchemeTree(Map<String,Object> entityMap)throws DataAccessException;
}
