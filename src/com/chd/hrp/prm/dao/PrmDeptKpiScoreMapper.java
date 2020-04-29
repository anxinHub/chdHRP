
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
 


public interface PrmDeptKpiScoreMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiScore
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiScore> queryPrmDeptKpiScore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiScore> queryPrmDeptKpiScore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiScore> queryPrmDeptKpiScoreDept(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiScore> queryPrmDeptKpiScoreDept(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiScore
	 * @throws DataAccessException
	*/
	public PrmDeptKpiScore queryPrmDeptKpiScoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int auditPrmDeptKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public Map<String,Object> collectDeptKpiScore(Map<String,Object> entityMap) throws  DataAccessException;
	
	
	public List<PrmDeptKpiScore> queryPrmDeptKpiScoreBySchemeTree(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<PrmDeptKpiScore> queryPrmDeptKpiScoreByScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<PrmDeptKpiScore> queryPrmDeptKpiScoreByScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
