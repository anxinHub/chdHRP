
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

import com.chd.hrp.prm.entity.PrmGoal;
/**
 * 
 * @Description:
 * 0101 目标管理表
 * @Table:
 * PRM_GOAL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmGoalMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmGoal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmGoal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmGoal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return PrmGoal
	 * @throws DataAccessException
	*/
	public int updateBatchPrmGoal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmGoal(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmGoal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0101 目标管理表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmGoal> queryPrmGoal(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public List<PrmGoal> queryPrmGoalByTree(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0101 目标管理表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmGoal> queryPrmGoal(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0101 目标管理表<BR> 
	 * @param  entityMap
	 * @return PrmGoal
	 * @throws DataAccessException
	*/
	public PrmGoal queryPrmGoalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0101 目标管理表<BR>全部 包含单位信息
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmGoal> queryPrmGoalHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0101 目标管理表<BR>包含单位信息 带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmGoal> queryPrmGoalHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	public Long queryPrmGoalByAudit(Map<String,Object> entityMap) throws DataAccessException;
	
}
