
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
import com.chd.hrp.prm.entity.PrmDeptTargetData;
/**
 * 
 * @Description:
 * 0312 科室绩效指标数据表
 * @Table:
 * PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptTargetDataMapper extends SqlMapper{ 
	/**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return PrmDeptTargetData
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	public int cleanPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 查询结果集0312 科室绩效指标数据表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptTargetData> queryPrmDeptTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0312 科室绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptTargetData> queryPrmDeptTargetPrmTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	
    public List<PrmDeptTargetData> queryPrmDeptTargetPrmTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0312 科室绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptTargetData> queryPrmDeptTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return PrmDeptTargetData
	 * @throws DataAccessException
	*/
	public PrmDeptTargetData queryPrmDeptTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public int auditPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;

	public List<PrmDeptTargetData> getDeptTargetValueByTarget(
			Map<String, Object> entityMap);

	public int deleteDeptTargetData(Map<String, Object> updateMap) throws DataAccessException;

	public List<PrmDeptTargetData> queryPrmDeptTargetDataByImport(Map<String, Object> entityMap);

	
	
}
