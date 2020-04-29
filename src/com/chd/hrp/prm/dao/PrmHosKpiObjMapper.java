
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

import com.chd.hrp.prm.entity.PrmHosKpiObj;
/**
 * 
 * @Description:
 * 0202 医院绩效方案核算对象表
 * @Table:
 * PRM_HOS_KPI_OBJ
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmHosKpiObjMapper extends SqlMapper{
	/**
	 * @Description 
	 * 添加0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addPrmHosKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchPrmHosKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int updatePrmHosKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiObj
	 * @throws DataAccessException
	*/
	public int updateBatchPrmHosKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 删除0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deletePrmHosKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 批量删除0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchPrmHosKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0202 医院绩效方案核算对象表<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiObj> queryPrmHosKpiObj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集 医院信息<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiObj> queryPrmHosInfo(Map<String,Object> entityMap) throws DataAccessException;	
	
	
	/**
	 * @Description 
	 * 查询结果集0202 医院绩效方案核算对象表<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<PrmHosKpiObj> queryPrmHosKpiObj(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0202 医院绩效方案核算对象表left join hos_info <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmHosKpiObjHos(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0202 医院绩效方案核算对象表left join hos_info <BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmHosKpiObjHos(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 获取0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiObj
	 * @throws DataAccessException
	*/
	public PrmHosKpiObj queryPrmHosKpiObjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取0202 医院信息<BR> 
	 * @param  entityMap
	 * @return PrmHosKpiObj
	 * @throws DataAccessException
	*/
	public PrmHosKpiObj queryPrmHosInfoByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
