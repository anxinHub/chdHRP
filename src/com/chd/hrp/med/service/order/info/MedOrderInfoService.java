package com.chd.hrp.med.service.order.info;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MedOrderInfoService extends SqlService{
	
	/**
	 * 订单信息查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOrderInfo(Map<String, Object> entityMap) throws DataAccessException;
}
