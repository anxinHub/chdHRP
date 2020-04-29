
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmEmpTargetDataCalculate;
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
 


public interface PrmEmpTargetDataCalculateService {

	/**
	 * @Description 
	 * 添加0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量更新0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public String updateBatchPrmEmpTargetDataCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;

	public String queryPrmEmpTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryPrmEmpTargetPrmTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryPrmEmpTargetDataCalculatePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0412 职工绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmEmpTargetDataCalculate queryPrmEmpTargetDataCalculateByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 审核
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reAuditPrmEmpTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String createPrmEmpTargetDataCalculate(Map<String,Object> entityMap,String paramVo)throws DataAccessException;
	
}
