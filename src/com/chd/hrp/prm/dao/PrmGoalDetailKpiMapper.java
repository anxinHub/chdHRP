
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

import com.chd.hrp.prm.entity.PrmGoalDetailKpi;
/**
 * 
 * @Description:
 * 0103 目标管理明细指标表
 * @Table:
 * PRM_GOAL_DETAIL_KPI
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmGoalDetailKpiMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmGoalDetailKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return PrmGoalDetailKpi
	 * @throws DataAccessException
	*/
	public int updateBatchPrmGoalDetailKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmGoalDetailKpi(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmGoalDetailKpi(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0103 目标管理明细指标表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmGoalDetailKpi> queryPrmGoalDetailKpi(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0103 目标管理明细指标表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmGoalDetailKpi> queryPrmGoalDetailKpi(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0103 目标管理明细指标表<BR> 
	 * @param  entityMap
	 * @return PrmGoalDetailKpi
	 * @throws DataAccessException
	*/
	public PrmGoalDetailKpi queryPrmGoalDetailKpiByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
