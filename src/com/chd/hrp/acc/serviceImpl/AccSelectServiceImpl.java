package com.chd.hrp.acc.serviceImpl;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.hrp.acc.dao.AccSelectMapper;
import com.chd.hrp.acc.service.AccSelectService;
@Service("accSelectService")
public class AccSelectServiceImpl implements AccSelectService {
	
	private static Logger logger = Logger.getLogger(AccSelectServiceImpl.class);

	@Resource(name = "accSelectMapper")
	private final AccSelectMapper accSelectMapper = null;
	
	RowBounds rowBoundsALL = new RowBounds(0, 20);
	
	@Override
	public String queryHosNatureDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(accSelectMapper.queryHosNatureDict(entityMap, rowBounds));
		
	}

	@Override
	public String queryHosInfoDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(accSelectMapper.queryHosInfoDict(entityMap, rowBounds));
		
	}

	@Override
	public String queryCopyDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(accSelectMapper.queryCopyDict(entityMap, rowBounds));
		
	}

	@Override
	public String queryAcctYearDict(Map<String, Object> entityMap) throws DataAccessException {
		
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (entityMap.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) entityMap.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(accSelectMapper.queryAcctYearDict(entityMap, rowBounds));
		
	}

	public String queryCheckTypeDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		
		if (mapVo.get("pageSize") != null) {
			
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
			
		}else{
			
			rowBounds = rowBoundsALL;
			 
		}
		
		return JSON.toJSONString(accSelectMapper.queryCheckTypeDict(mapVo, rowBounds));
	}

	public String queryWageAndNotInThisWage(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return JSON.toJSONString(accSelectMapper.queryWageAndNotInThisWage(mapVo));
	}

}
