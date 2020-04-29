
/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 package com.chd.hrp.prm.service;
import java.util.*;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.prm.entity.PrmDeptTargetData;
/**
 * 
 * @Description:
 * 0312 科室绩效指标数据表
 * @Table:
 * PRM_DEPT_TARGET_DATA
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 


public interface PrmDeptTargetDataCalculateService {

	/**
	 * @Description 
	 * 添加0312 科室绩效指标数据表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String addPrmDeptTargetData(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public String queryPrmDeptTargetPrmTargetDataCalculate(Map<String,Object> entityMap) throws DataAccessException;
	
	//打印
	public List<Map<String,Object>> queryPrmTargetDataCalculatePrint(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String updateBatchPrmDeptTargetDataCalculate(List<Map<String, Object>> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 生成
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String createPrmDeptTargetDataCalculate(Map<String,Object> entityMap,String paramVo)throws DataAccessException;
	
	/**
	 * @Description 
	 * 生成
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String collectPrmDeptTargetDataCalculate(Map<String,Object> entityMap)throws DataAccessException;
	
	
}
