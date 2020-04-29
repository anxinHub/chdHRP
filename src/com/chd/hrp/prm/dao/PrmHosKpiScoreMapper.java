
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
import com.chd.hrp.prm.entity.PrmHosKpiScore;
import com.chd.hrp.prm.entity.PrmHosKpiScore;
/**
 * 
 * @Description:
 * 0209 医院KPI指标考评计算表
 * @Table:
 * PRM_HOS_KPI_SCORE
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmHosKpiScoreMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmHosKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiScore
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmHosKpiScore(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiScore> queryPrmHosKpiScore(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiScore> queryPrmHosKpiScore(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiScore> queryPrmHosKpiScoreHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0309 科室KPI指标考评计算表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiScore> queryPrmHosKpiScoreHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0309 科室KPI指标考评计算表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiScore
	 * @throws DataAccessException
	*/
	public PrmHosKpiScore queryPrmHosKpiScoreByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int auditPrmHosKpiScore(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public Map<String,Object> collectHosKpiScore(Map<String,Object> entityMap) throws  DataAccessException;
	
	
	public List<PrmHosKpiScore> queryPrmHosKpiScoreBySchemeTree(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<PrmHosKpiScore> queryPrmHosKpiScoreByScheme(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<PrmHosKpiScore> queryPrmHosKpiScoreByScheme(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
}
