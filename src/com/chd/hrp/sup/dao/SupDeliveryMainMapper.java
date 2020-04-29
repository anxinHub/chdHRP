/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.sup.dao; 

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description: 100101 送货单主表
 * @Table: SUP_DELIVERY_MAIN
 * @Author: bell
 * @email: bell@s-chd.com 
 * @Version: 1.0
 */

public interface SupDeliveryMainMapper extends SqlMapper {

	/**
	 * 查询最大序列号
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDeliveryMainNextval() throws DataAccessException;

	public String queryDeliveryMaxNo(Map<String, Object> map) throws DataAccessException;
	public List<String> queryDeliveryOrderRelaExists(Map<String, Object> map) throws DataAccessException;
	
	public List<?> queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	public String queryCertCode(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * 查询订单Id
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryDeliveryOrderIds(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDeliveryAffiOrderIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 更新订单状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateDeliveryOrderStates(List<Map<String, Object>> entityMap) throws DataAccessException;

	
}
