package com.chd.hrp.med.dao.order.receive;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedOrderReceiveMapper extends SqlMapper{

	/**
	 * 收货通知单查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryOrderReceive(Map<String, Object> entityMap) throws DataAccessException;
	
}
