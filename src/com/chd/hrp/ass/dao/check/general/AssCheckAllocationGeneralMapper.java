package com.chd.hrp.ass.dao.check.general;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssCheckAllocationGeneralMapper extends SqlMapper {

	List<Map<String, Object>> queryAssCheckAllocationGeneral(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssCheckAllocationGeneral(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
