
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
import com.chd.hrp.prm.entity.PrmKpiLib;
import com.chd.hrp.prm.entity.PrmTarget;
/**
 * 
 * @Description:
 * 0502 KPI指标库表
 * @Table:
 * PRM_KPI_LIB
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmKpiLibMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmKpiLib(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmKpiLib(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmKpiLib(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return PrmKpiLib
	 * @throws DataAccessException
	*/
	public int updateBatchPrmKpiLib(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmKpiLib(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmKpiLib(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0502 KPI指标库表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmKpiLib> queryPrmKpiLib(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0502 KPI指标库表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmKpiLib> queryPrmKpiLib(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取0502 KPI指标库表<BR> 
	 * @param  entityMap
	 * @return PrmKpiLib
	 * @throws DataAccessException
	*/
	public PrmKpiLib queryPrmKpiLibByCode(Map<String,Object> entityMap)throws DataAccessException;


	
	/**
	 * @Description 
	 * 查询结果集0502 KPI指标库表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmKpiLib> queryPrmKpiLibDimPkn(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0502 KPI指标库表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmKpiLib> queryPrmKpiLibDimPkn(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集 按树形结构展示 KPI指标库表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmKpiLib> queryPrmKpiLibDimPkns(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集 按树形结构展示 KPI指标库表<BR>全部  
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmKpiLib> queryPrmKpiLibDimPkns(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
