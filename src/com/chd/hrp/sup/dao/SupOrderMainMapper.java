package com.chd.hrp.sup.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SupOrderMainMapper extends SqlMapper{

	
	/**
	 * 供应商订单查询
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> querySupOrderMain(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 订单结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> querySupOrderMain(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<?> queryDetail(Map<String,Object> entityMap) throws DataAccessException;
	public List<?> queryDetailWithDelivery(Map<String,Object> entityMap) throws DataAccessException;
	public List<?> queryDetailWithDeliveryView(Map<String,Object> entityMap) throws DataAccessException;
}
