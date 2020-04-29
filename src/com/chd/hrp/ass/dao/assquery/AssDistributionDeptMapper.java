package com.chd.hrp.ass.dao.assquery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssDistributionDeptMapper extends SqlMapper{

	List<Map<String,Object>> queryAssDistributionDept(Map<String, Object> entityMap)throws DataAccessException;

	List<Map<String, Object>> queryAssDistributionDept(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	List<Map<String, Object>> queryAssDisPrint(Map<String, Object> entityMap);

}
