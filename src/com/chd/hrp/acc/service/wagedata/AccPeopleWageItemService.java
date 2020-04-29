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
* 工资数据<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPeopleWageItemService {

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItem分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccPeopleWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItemByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccPeopleWageItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccPeopleWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateAccPeopleWageItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量更新AccPeopleWageItem
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String updateBatchAccPeopleWageItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public String queryAccPeoplePay(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String,Object>> queryAccPeopleWageItemPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public  List<Map<String,Object>>  queryAccPeoplePayPrint(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryAccPeoplePayMeals(Map<String,Object> entityMap) throws DataAccessException;
	
}
