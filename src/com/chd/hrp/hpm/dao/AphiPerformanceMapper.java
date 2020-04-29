package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
 
public interface AphiPerformanceMapper extends SqlMapper{ 

	public List<Map<String, Object>> queryHpmPerformance(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryHpmPerformance(
			Map<String, Object> entityMap, RowBounds rowBounds); 

	public String queryEmp(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryDeptList(Map<String, Object> entityMap);

}
