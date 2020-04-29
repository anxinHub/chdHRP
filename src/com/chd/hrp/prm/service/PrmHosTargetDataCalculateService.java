
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmHosTargetData;
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
 


public interface PrmHosTargetDataCalculateService {

	
	public String queryPrmHosTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 查询结果集0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryPrmHosTargetPrmTargetData(Map<String,Object> entityMap) throws DataAccessException;
	
	
	
	public String updateBatchPrmHosTargetDataCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询对象0212 院级绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public PrmHosTargetData queryPrmHosTargetDataByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String createPrmHosTargetDataCalculate(Map<String,Object> entityMap,String paramVo)throws DataAccessException;
	
	
	public String auditPrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	public String reAuditPrmHosTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String collectPrmHosTargetCalculate(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * @Description 
	 * 院级绩效指标数据表  打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryPrmHosTargetPrmTargetDataPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
