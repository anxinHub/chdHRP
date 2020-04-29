/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageTaxCal;

/**
* @Title. @Description.
* 个税计算公式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageTaxCalService {

	/**
	 * @Description 
	 * 个税计算公式<BR> 添加AccWageTaxCal
	 * @param AccWageTaxCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 批量添加AccWageTaxCal
	 * @param  AccWageTaxCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageTaxCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 查询AccWageTaxCal分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageTaxCal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 查询AccWageTaxCalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageTaxCal queryAccWageTaxCalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 删除AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 批量删除AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageTaxCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 更新AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 批量更新AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageTaxCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 个税计算公式<BR> 导入AccWageTaxCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageTaxCal(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 个税计算公式<BR> 继承
	 * @param  AccWageTaxCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String extendAccWageTaxCal(Map<String, Object> entityMap)throws DataAccessException;
	
	public String queryAccWageTaxCalById(Map<String, Object> entityMap)throws DataAccessException;
}
