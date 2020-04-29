package com.chd.hrp.ass.dao.check.land;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssCheckAllocationLandMapper extends SqlMapper {

	List<Map<String, Object>> queryAssCheckAllocationLand(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssCheckAllocationLand(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
