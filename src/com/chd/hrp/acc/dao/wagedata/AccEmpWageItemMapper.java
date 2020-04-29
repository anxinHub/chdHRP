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
* 工资条信息<BR>
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


public interface AccEmpWageItemMapper extends SqlMapper{
	
	/**
	 * @Description 
	 * 工资条信息<BR> 查询AccEmpWageItem分页
	 * @param  entityMap RowBounds
	 * @return List<AccEmpWageItem>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccEmpWageItem(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 工资条信息<BR> 查询AccEmpWageItem所有数据
	 * @param  entityMap
	 * @return List<AccEmpWageItem>
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryAccEmpWageItem(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 查询AccEmpWageItem所有数据（不含合计）
	 * @param  entityMap
	 * @return List<AccEmpWageItem>
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryEmpWageItemList(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 查询AccEmpWageItemByCode
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public AccWagePay queryAccEmpWageItemByCode(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 更新AccEmpWageItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateAccEmpWageItem(Map<String,Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 工资条信息<BR> 批量更新AccEmpWageItem
	 * @param  entityMap 
	 * @return int
	 * @throws DataAccessException
	*/
	public int updateBatchAccEmpWageItem(List<Map<String, Object>> entityMap)throws DataAccessException;

}
