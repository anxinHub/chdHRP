package com.chd.hrp.ass.dao.check.house;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssCheckAllocationHouseMapper extends SqlMapper {

	List<Map<String, Object>> queryAssCheckAllocationHouse(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssCheckAllocationHouse(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
