package com.chd.hrp.sup.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface SupOrderCountService extends SqlService{
	
	/**
	 * 订单执行查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String querySupOrderCount(Map<String, Object> entityMap) throws DataAccessException;
	
}
