package com.chd.hrp.ass.dao.assmap;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assmap.AssAssMap;

public interface AssAssMapMapper extends SqlMapper{
	
	public List<AssAssMap> queryAssStoreDistribution(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryAssDeptDistribution(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryDeptAssDistribution(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryAssInStore(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryInDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryBackDept(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryStoreInStore(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<AssAssMap> queryDeptInDept(Map<String,Object> entityMap) throws DataAccessException;
}
