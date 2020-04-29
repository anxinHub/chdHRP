package com.chd.hrp.mat.dao.order.info;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatOrderInfoMapper extends SqlMapper{

	/**
	 * 订单信息查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatOrderInfo(Map<String, Object> entityMap) throws DataAccessException;
	
}
