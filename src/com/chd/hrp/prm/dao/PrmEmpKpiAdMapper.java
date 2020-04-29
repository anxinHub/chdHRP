
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
import com.chd.hrp.prm.entity.PrmEmpKpiAd;
/**
 * 
 * @Description:
 * 0406 职工指标加扣分法参数表
 * @Table:
 * PRM_EMP_KPI_AD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmEmpKpiAdMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmEmpKpiAd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmEmpKpiAd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmEmpKpiAd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiAd
	 * @throws DataAccessException
	*/
	public int updateBatchPrmEmpKpiAd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmEmpKpiAd(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmEmpKpiAd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0306 科室指标加扣分法参数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiAd> queryPrmEmpKpiAd(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0306 科室指标加扣分法参数表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmEmpKpiAd> queryPrmEmpKpiAd(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0306 科室指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return PrmEmpKpiAd
	 * @throws DataAccessException
	*/
	public PrmEmpKpiAd queryPrmEmpKpiAdByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
