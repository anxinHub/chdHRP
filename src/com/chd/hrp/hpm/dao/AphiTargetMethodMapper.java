
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.hpm.dao;
import java.util.*;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiTargetMethod;
/**
 * 
 * @Description:
 * 9904 绩效指标取值方法配置表
 * @Table:
 * PRM_TARGET_METHOD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface AphiTargetMethodMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmTargetMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return PrmTargetMethod
	 * @throws DataAccessException
	*/
	public int updateBatchPrmTargetMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmTargetMethod(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmTargetMethod(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9904 绩效指标取值方法配置表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTargetMethod> queryPrmTargetMethod(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9904 绩效指标取值方法配置表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTargetMethod> queryPrmTargetMethod(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取9904 绩效指标取值方法配置表<BR> 
	 * @param  entityMap
	 * @return PrmTargetMethod
	 * @throws DataAccessException
	*/
	public AphiTargetMethod queryPrmTargetMethodByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9903 指标取值方法参数表<BR>带分页   关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	
	public List<AphiTargetMethod> queryPrmTargetMethodNature(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集9903 指标取值方法参数表<BR>带分页   关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<AphiTargetMethod> queryPrmTargetMethodNature(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取9904 绩效指标取值方法配置表<BR>  关联指标表、指标性质表、取值方法参数表、取值方法配置表
	 * @param  entityMap
	 * @return PrmTargetMethod
	 * @throws DataAccessException
	*/
	public AphiTargetMethod queryPrmTargetMethodNatureByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
