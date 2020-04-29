/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 智慧云康（北京）数据科技有限公司
*/
package com.chd.hrp.acc.dao.wagedata;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWagePay;
 
/** 
* @Title. @Description.
* 工资数据<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccPeopleWageItemMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItem分页
	 * @param  entityMap RowBounds
	 * @return List<AccPeopleWageItem>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccPeopleWageItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItem所有数据
	 * @param  entityMap
	 * @return List<AccPeopleWageItem>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccPeopleWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccPeopleWageItemByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccPeopleWageItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccPeopleWageItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccPeopleWageItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量更新AccPeopleWageItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccPeopleWageItem(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public AccWagePay queryAccPeopleWageItemByUserId(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccPeoplePay(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryAccPeoplePayMeals(Map<String,Object> entityMap) throws DataAccessException;

}
