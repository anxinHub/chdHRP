package com.chd.hrp.mat.service.order.receive;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatOrderReceiveService extends SqlService{
	/**
	 * 收货通知单查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderReceive(Map<String, Object> entityMap) throws DataAccessException;
	
}
