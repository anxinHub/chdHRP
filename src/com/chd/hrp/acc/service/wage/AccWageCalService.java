/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wage;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWageCal;

/**
* @Title. @Description.
* 工资套合并计算公式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageCalService {

	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 添加AccWageCal
	 * @param AccWageCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量添加AccWageCal
	 * @param  AccWageCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageCal(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCal分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageCal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 查询AccWageCalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageCal queryAccWageCalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 删除AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量删除AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 更新AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资套合并计算公式<BR> 批量更新AccWageCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
}
