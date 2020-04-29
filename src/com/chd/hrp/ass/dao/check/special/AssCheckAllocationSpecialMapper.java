package com.chd.hrp.ass.dao.check.special;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssCheckAllocationSpecialMapper extends SqlMapper {

	List<Map<String, Object>> queryAssCheckAllocationSpecial(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssCheckAllocationSpecial(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
