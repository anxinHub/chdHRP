package com.chd.hrp.hip.service.cache;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface CacheDataService {

	List<Map<String, Object>> queryCacheSql(Map<String, Object> map)
			throws DataAccessException;

}
