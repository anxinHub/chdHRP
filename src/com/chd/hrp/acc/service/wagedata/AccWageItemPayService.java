/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
 

package com.chd.hrp.acc.service.wagedata;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.acc.entity.AccWagePay;

/**
* @Title. @Description.
* 工资发放表<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccWageItemPayService {

	/**
	 * @Description 
	 * 工资发放表<BR> 添加AccWageItemPay
	 * @param AccWageItemPay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 批量添加AccWageItemPay
	 * @param  AccWageItemPay entityMap
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String addBatchAccWageItemPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 查询AccWageItemPay分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageItemPay(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 查询AccWageItemPayByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccWageItemPayByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 删除AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 批量删除AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String deleteBatchAccWageItemPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 更新AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 批量更新AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccWageItemPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资发放表<BR> 导入AccWageItemPay
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String importAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageItemPayPrint(Map<String,Object> entityMap) throws DataAccessException;

	public String collectAccBankNetWage(Map<String,Object> entityMap) throws DataAccessException;
	
}
