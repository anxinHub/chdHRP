package com.chd.hrp.ass.dao.check.other;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssCheckAllocationOtherMapper extends SqlMapper {

	List<Map<String, Object>> queryAssCheckAllocationOther(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssCheckAllocationOther(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
