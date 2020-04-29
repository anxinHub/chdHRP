package com.chd.hrp.ass.dao.assquery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssDistributionMapper extends SqlMapper{

	List<Map<String,Object>> queryAssDistribution(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDistribution(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssDistributionDetail(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDistributionDetail(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssDistributionCheck(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDistributionCheck(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssbuttonPrint(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssbuttonsPrint(Map<String, Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryAssbuttonsPrint(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

}
