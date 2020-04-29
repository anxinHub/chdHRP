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


public interface AccWageItemSumMapper extends SqlMapper{
	
	public List<Map<String,Object>> queryAccWageItemSum(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemSum所有数据
	 * @param  entityMap
	 * @return List<AccWageItemSum>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccWageItemSum(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemSumByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccWageItemSumByCode(Map<String,Object> entityMap)throws DataAccessException;
	
}
