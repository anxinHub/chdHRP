package com.chd.hrp.med.service.order.receive;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MedOrderReceiveService extends SqlService{
	/**
	 * 收货通知单查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOrderReceive(Map<String, Object> entityMap) throws DataAccessException;
	
}
