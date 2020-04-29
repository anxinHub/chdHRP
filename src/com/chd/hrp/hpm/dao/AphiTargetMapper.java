
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.dao;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiTarget;

/**
 * 
 * @Description:
 * 9901 绩效指标字典表
 * @Table:
 * PRM_TARGET
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiTargetMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmTarget(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return PrmTarget
	 * @throws DataAccessException
	*/
	public int updateBatchPrmTarget(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmTarget(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmTarget(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTarget> queryPrmTarget(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTarget> queryPrmTargetNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTarget> queryPrmTargetNature(Map<String,Object> entityMap) throws DataAccessException;
	public List<AphiTarget> queryPrmTargetNatureDanbiao(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集9901 绩效指标字典表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTarget> queryPrmTarget(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return PrmTarget
	 * @throws DataAccessException
	*/
	public AphiTarget queryPrmTargetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 获取9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return PrmTarget
	 * @throws DataAccessException
	*/
	public AphiTarget queryPrmTargetNatureGetByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 
	 * @param  entityMap
	 * @return PrmTarget
	 * @throws DataAccessException
	*/
	public List<AphiTarget> queryPrmTargetNatureCreate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 
	 * @param  entityMap
	 * @return PrmTarget
	 * @throws DataAccessException
	*/
	public List<AphiTarget> queryPrmTargetNatureCreateCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取9901 绩效指标字典表<BR> 
	 * @param  entityMap
	 * @return PrmTarget
	 * @throws DataAccessException
	*/
	public AphiTarget queryPrmTargetByName(Map<String,Object> entityMap)throws DataAccessException;
	
}
