
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
import com.chd.hrp.prm.entity.PrmHosTargetData;
import com.chd.hrp.prm.entity.PrmHosTargetDataCalculate;
/**
 * 
 * @Description:
 * 0212 院级绩效指标数据表
 * @Table:
 * PRM_HOS_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmHosTargetDataCalculateMapper extends SqlMapper{
	

	/**
	 * @Description 
	 * 添加0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<PrmHosTargetData> queryPrmHosTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0212 院级绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosTargetData> queryPrmHosTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0212 院级绩效指标数据表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosTargetDataCalculate> queryPrmHosTargetPrmTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0212 院级绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosTargetDataCalculate> queryPrmHosTargetPrmTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return PrmHosTargetData
	 * @throws DataAccessException
	*/
	public PrmHosTargetData queryPrmHosTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 删除0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int cleanPrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	public int auditPrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	

	public int updateBatchPrmHosTargetDataCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
}
