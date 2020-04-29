package com.chd.hrp.hip.dao.cache;

import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import com.chd.base.SqlMapper;

public interface CacheDataMapper extends SqlMapper {

	
	public List<Map<String,Object>> queryCacheSql(Map<String, Object> map) throws DataAccessException;
	
	public List<Map<String,Object>> queryCacheQuery(Map<String, Object> map) throws DataAccessException;
	
}
