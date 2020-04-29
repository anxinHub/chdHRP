/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageVouch;
  
/** 
* @Title. @Description. 
* 工资转账<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageVouchService {

	/**
	 * @Description 
	 * 工资转账<BR> 添加AccWageVouch
	 * @param AccWageVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量添加AccWageVouch
	 * @param  AccWageVouch entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageVouch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouchByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageVouch queryAccWageVouchByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 删除AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量删除AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 更新AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 批量更新AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageVouch(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 导入AccWageVouch
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageVouch(Map<String,Object> entityMap)throws DataAccessException;

	public AccWageVouch queryAccWageVouchBySchemeCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资转账<BR> 查询AccWageVouch分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageVouchSubj(Map<String,Object> entityMap) throws DataAccessException;
	
	public String addTransferAccWageVouch(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	public String extendAccWageVouch(Map<String, Object> entityMap)throws DataAccessException;
	
	
}
