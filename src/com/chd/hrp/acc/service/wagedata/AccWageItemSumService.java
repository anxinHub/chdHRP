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


public interface AccWageItemSumService {

	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemSum分页
	 * @param  entityMap RowBounds
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public String queryAccWageItemSum(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资数据<BR> 查询AccWageItemSumByCode
	 * @param  entityMap 
	 * @return String JSON
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccWageItemSumByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	public List<Map<String,Object>> queryAccWageItemSumPrint(Map<String,Object> entityMap) throws DataAccessException;
	
}
