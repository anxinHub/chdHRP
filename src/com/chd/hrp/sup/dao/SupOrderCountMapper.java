package com.chd.hrp.sup.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface SupOrderCountMapper extends SqlMapper{

	/**
	 * 订单执行查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> querySupOrderCount(Map<String, Object> entityMap) throws DataAccessException;
	
}
