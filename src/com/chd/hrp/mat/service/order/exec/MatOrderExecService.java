package com.chd.hrp.mat.service.order.exec;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatOrderExecService extends SqlService{
	
	/**
	 * 订单执行查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderExec(Map<String, Object> entityMap) throws DataAccessException;
	
}
