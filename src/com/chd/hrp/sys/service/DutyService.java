/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.sys.service;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.sys.entity.Duty;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface DutyService {

	/**
	 * @Description 添加Duty
	 * @param Duty entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量添加Duty
	 * @param  Duty entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 查询Duty分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryDuty(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 查询DutyByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public Duty queryDutyByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 删除Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量删除Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 更新Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateDuty(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 批量更新Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchDuty(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 导入Duty
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importDuty(Map<String,Object> entityMap)throws DataAccessException;
	
}
