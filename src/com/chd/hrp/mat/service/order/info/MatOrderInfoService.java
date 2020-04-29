package com.chd.hrp.mat.service.order.info;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatOrderInfoService extends SqlService{
	
	/**
	 * 订单信息查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderInfo(Map<String, Object> entityMap) throws DataAccessException;
}
