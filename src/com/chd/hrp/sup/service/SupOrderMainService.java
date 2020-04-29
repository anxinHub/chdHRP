package com.chd.hrp.sup.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface SupOrderMainService extends SqlService{
	
	/**
	 * 供应商订单页面--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySupOrderMain(Map<String,Object> entityMap) throws DataAccessException;
	
	
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException;	
	public String queryDetailWithDelivery(Map<String, Object> entityMap) throws DataAccessException;	
	public String queryDetailWithDeliveryView(Map<String, Object> entityMap) throws DataAccessException;	

}
