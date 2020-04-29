/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）调整科技有限公司
*/


package com.chd.hrp.acc.service.wagedata;
import java.util.*;
import org.springframework.dao.DataAccessException;
import com.chd.hrp.acc.entity.AccWageItemCal;

/**
* @Title. @Description.
* 工资项目计算公式<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageItemCalService {

	/**
	 * @Description 
	 * 工资项目计算公式<BR> 添加AccWageItemCal
	 * @param AccWageItemCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 批量添加AccWageItemCal
	 * @param  AccWageItemCal entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageItemCal(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 查询AccWageItemCal分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageItemCal(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 查询AccWageItemCalByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWageItemCal queryAccWageItemCalByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 删除AccWageItemCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 批量删除AccWageItemCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageItemCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 更新AccWageItemCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 批量更新AccWageItemCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageItemCal(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 导入AccWageItemCal
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageItemCal(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资项目计算公式<BR> 查询AccWageItems
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageItemCalList(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccWageItemCalById(Map<String,Object> entityMap)throws DataAccessException;
	
	public String queryAccWageItemTree(Map<String,Object> entityMap) throws DataAccessException;
	
}
