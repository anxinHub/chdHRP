package com.chd.hrp.sup.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SupDeliveryCountMapper extends SqlMapper{

	/**
	 * 送货单执行查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> querySupDeliveryCount(Map<String, Object> entityMap) throws DataAccessException;
	
}
