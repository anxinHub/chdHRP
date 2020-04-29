package com.chd.hrp.med.dao.order.info;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedOrderInfoMapper extends SqlMapper{

	/**
	 * 订单信息查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedOrderInfo(Map<String, Object> entityMap) throws DataAccessException;
	
}
