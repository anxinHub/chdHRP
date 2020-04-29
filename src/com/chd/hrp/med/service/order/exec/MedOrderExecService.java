package com.chd.hrp.med.service.order.exec;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MedOrderExecService extends SqlService{
	
	/**
	 * 订单执行查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOrderExec(Map<String, Object> entityMap) throws DataAccessException;
	
}
