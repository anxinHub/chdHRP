
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
import com.chd.hrp.prm.entity.PrmEmpTargetData;
import com.chd.hrp.prm.entity.PrmEmpTargetDataCalculate;
/**
 * 
 * @Description:
 * 0412 职工绩效指标数据表
 * @Table:
 * PRM_EMP_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpTargetDataCalculateMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int updateBatchPrmEmpTargetDataCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpTargetDataCalculate> queryPrmEmpTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpTargetDataCalculate> queryPrmEmpTargetDataCalculate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpTargetDataCalculate> queryPrmEmpTargetPrmTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpTargetDataCalculate> queryPrmEmpTargetPrmTargetDataCalculate(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return PrmEmpTargetData
	 * @throws DataAccessException
	*/
	public PrmEmpTargetDataCalculate queryPrmEmpTargetDataCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int auditPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int cleanPrmEmptTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
