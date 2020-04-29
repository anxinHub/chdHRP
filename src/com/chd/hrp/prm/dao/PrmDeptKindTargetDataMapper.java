
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
import com.chd.hrp.prm.entity.PrmDeptKindTargetData;
/**
 * 
 * @Description:
 * 0313 科室类别绩效指标数据表
 * @Table:
 * PRM_DEPT_KIND_TARGET_DATA 
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptKindTargetDataMapper extends SqlMapper{ 
	/**
	 * @Description 
	 * 添加0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKindTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKindTargetData
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKindTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKindTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKindTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0313 科室类别绩效指标数据表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKindTargetData> queryPrmDeptKindTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0313 科室类别绩效指标数据表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKindTargetData> queryPrmDeptKindTargetData(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0313 科室类别绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKindTargetData
	 * @throws DataAccessException
	*/
	public PrmDeptKindTargetData queryPrmDeptKindTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;

	public List<PrmDeptKindTargetData> getDeptKindTargetValueByTarget(
			Map<String, Object> entityMap);
	
	
	
	
}
