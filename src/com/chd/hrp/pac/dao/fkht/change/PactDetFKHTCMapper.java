package com.chd.hrp.pac.dao.fkht.change;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactDetFKHTCMapper extends SqlMapper{
	/**
	 * 查询最大id
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryMaxDetailId(Map<String, Object> map) throws DataAccessException;


}
