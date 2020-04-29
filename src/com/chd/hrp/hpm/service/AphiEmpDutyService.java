package com.chd.hrp.hpm.service;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpDuty;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiEmpDutyService {

	/**
	 * 
	 */
	public String addPrmEmpDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryEmpDuty(Map<String,Object> entityMap) throws DataAccessException;
	 
	
	/**
	 * 
	 */
	public String deleteEmpDuty(Map<String,Object> entityMap,String duty_code)throws DataAccessException;
	
	
	
	/**
	 * 
	 */
	public String deleteBatchPrmEmpDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String updateEmpDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 *  
	 */
	public AphiEmpDuty queryPrmEmpDutyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryHpmSysEmpDuty(Map<String,Object> entityMap) throws DataAccessException;
	
}
