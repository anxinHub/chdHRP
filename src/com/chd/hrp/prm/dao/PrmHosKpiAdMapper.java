
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

import com.chd.hrp.prm.entity.PrmHosKpiAd;
/**
 * 
 * @Description:
 * 0206 指标加扣分法参数表
 * @Table:
 * PRM_HOS_KPI_AD
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmHosKpiAdMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosKpiAd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosKpiAd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmHosKpiAd(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiAd
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosKpiAd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosKpiAd(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmHosKpiAd(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0206 指标加扣分法参数表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiAd> queryPrmHosKpiAd(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0206 指标加扣分法参数表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiAd> queryPrmHosKpiAd(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * @Description 
	 * 获取0206 指标加扣分法参数表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiAd
	 * @throws DataAccessException
	*/
	public PrmHosKpiAd queryPrmHosKpiAdByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	
}
