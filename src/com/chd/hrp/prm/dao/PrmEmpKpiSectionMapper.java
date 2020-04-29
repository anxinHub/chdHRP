
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
import com.chd.hrp.prm.entity.PrmEmpKpiSection;
/**
 * 
 * @Description:
 * 0405 职工指标区间法参数表
 * @Table:
 * PRM_EMP_KPI_SECTION
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpKpiSectionMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmEmpKpiSection(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmEmpKpiSection(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmEmpKpiSection(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiSection
	 * @throws DataAccessException
	*/
	public int updateBatchPrmEmpKpiSection(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmEmpKpiSection(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmEmpKpiSection(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0305 科室指标区间法参数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiSection> queryPrmEmpKpiSection(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0305 科室指标区间法参数表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiSection> queryPrmEmpKpiSection(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0305 科室指标区间法参数表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiSection
	 * @throws DataAccessException
	*/
	public PrmEmpKpiSection queryPrmEmpKpiSectionByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
