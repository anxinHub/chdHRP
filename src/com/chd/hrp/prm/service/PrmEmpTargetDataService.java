
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmEmpTargetData;
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
 


public interface PrmEmpTargetDataService {

	/**
	 * @Description 
	 * 添加0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量添加0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addBatchPrmEmpTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 更新0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updatePrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量更新0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmEmpTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 删除0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deletePrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 批量删除0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String deleteBatchPrmEmpTargetData(List<Map<String, Object>> entityMap)throws DataAccessException;
	

	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmEmpTargetPrmTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpTargetData queryPrmEmpTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	
	/**
	 * @Description 
	 * 审核
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditPrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reAuditPrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String createPrmEmpTargetData(Map<String,Object> entityMap,String paramVo)throws DataAccessException;
	
	/**
	 * @Description 
	 * 导入
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String importPrmEmpTargetData(Map<String,Object> entityMap)throws DataAccessException;
}
