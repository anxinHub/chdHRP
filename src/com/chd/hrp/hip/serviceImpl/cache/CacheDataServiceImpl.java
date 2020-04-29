package com.chd.hrp.hip.serviceImpl.cache;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.datasource.DataSource;
import com.chd.hrp.hip.dao.cache.CacheDataMapper;
import com.chd.hrp.hip.service.cache.CacheDataService;

@Service("cacheDataService")
public class CacheDataServiceImpl implements CacheDataService{

	
	private static Logger logger = Logger.getLogger(CacheDataServiceImpl.class);

	@Resource(name = "cacheDataMapper")
	private final CacheDataMapper cacheDataMapper = null; 
	
	
	/**
	 * 查询cachesql
	 */
	@DataSource("cache")
	@Override
	public List<Map<String,Object>> queryCacheSql(Map<String, Object> map) throws DataAccessException {
		
		if(map.get("sql").toString().toLowerCase().indexOf("call ") != -1){
			return cacheDataMapper.queryCacheQuery(map);
		}else{
			return cacheDataMapper.queryCacheSql(map);
		}
		
	}
	
}
