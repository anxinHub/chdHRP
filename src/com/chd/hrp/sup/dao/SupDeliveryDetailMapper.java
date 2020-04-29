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
 * 
 * @Description:
 * 100102 送货单明细表
 * @Table:
 * SUP_DELIVERY_DETAIL
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 

public interface SupDeliveryDetailMapper extends SqlMapper{
	/**
	 * 查询最大序列号
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int queryDeliveryDetailNextval() throws DataAccessException;
	public List<?> queryDetail(Map<String,Object> entityMap)throws DataAccessException;
	public List<?> queryDetailWithDelivery(Map<String,Object> entityMap)throws DataAccessException;
	public List<?> queryExistsById(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量添加订单与送货单关系数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	*/
	public int addRelaBatch(List<?> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量删除订单与送货单关系数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int deleteRelaBatch(List<?> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 批量更新订单与送货单关系数据<BR> 
	 * @param  entityMap
	 * @return int
	 * @throws DataAccessException
	 */
	public int updateRelaBatch(List<?> entityMap)throws DataAccessException;
	
	public List<Map<String, Object>> queryDetailByStore(Map<String, Object> entityMap) throws DataAccessException;
	

}
