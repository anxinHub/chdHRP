package com.chd.hrp.mat.dao.order.receive;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatOrderReceiveMapper extends SqlMapper{

	/**
	 * 收货通知单查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryOrderReceive(Map<String, Object> entityMap) throws DataAccessException;
	
}
