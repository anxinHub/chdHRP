
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

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
 


public interface PrmHosKpiObjService {

	/**
	 * @Description 
	 * 添加0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmHosKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmHosKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmHosKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmHosKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmHosKpiObj(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmHosKpiObj(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiObj(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmHosKpiObj queryPrmHosKpiObjByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0202 医院绩效方案核算对象表 left join hos_info <BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosKpiObjHos(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 *  查询 医院信息 add by alfred    返回JSP页面医院coloum名称JSON
	*/
	public String queryPrmHosInfo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 *  查询 医院信息 add by alfred    返回controller 所有医院的信息 为保存是使用
	*/
	public List<PrmHosKpiObj> queryPrmHosInfoForAdd(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 保存对象0202 医院绩效方案核算对象表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String savePrmHosKpiObj(Map<String, Object> entityMap) throws DataAccessException;
	
}
