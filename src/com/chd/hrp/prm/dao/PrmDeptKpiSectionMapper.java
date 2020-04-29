
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

import com.chd.hrp.prm.entity.PrmDeptKpiSection;
/**
 * 
 * @Description:
 * 0305 科室指标区间法参数表
 * @Table:
 * PRM_DEPT_KPI_SECTION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptKpiSectionMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmDeptKpiSection(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmDeptKpiSection(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmDeptKpiSection(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiSection
	 * @throws DataAccessException
	*/
	public int updateBatchPrmDeptKpiSection(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmDeptKpiSection(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmDeptKpiSection(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0305 科室指标区间法参数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiSection> queryPrmDeptKpiSection(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0305 科室指标区间法参数表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmDeptKpiSection> queryPrmDeptKpiSection(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return PrmDeptKpiSection
	 * @throws DataAccessException
	*/
	public PrmDeptKpiSection queryPrmDeptKpiSectionByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
