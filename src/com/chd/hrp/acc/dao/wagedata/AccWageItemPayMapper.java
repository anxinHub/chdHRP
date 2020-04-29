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


public interface AccWageItemPayMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资数据<BR> 添加AccWageItemPay
	 * @param AccWageItemPay entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量添加AccWageItemPay
	 * @param  AccWageItemPay entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addBatchAccWageItemPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemPay分页
	 * @param  entityMap RowBounds
	 * @return List<AccWageItemPay>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWageItemPay(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemPay所有数据
	 * @param  entityMap
	 * @return List<AccWageItemPay>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWageItemPay(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemPayByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccWageItemPayByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 删除AccWageItemPay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量删除AccWageItemPay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteBatchAccWageItemPay(List<Map<String, Object>> entityMap)throws DataAccessException;
    
	/**
	 * @Description 
	 * 工资数据<BR> 更新AccWageItemPay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccWageItemPay(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 批量更新AccWageItemPay
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccWageItemPay(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageItemPaySum(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String,Object>> queryAccBankNetWage(Map<String,Object> entityMap) throws DataAccessException;
	
}
