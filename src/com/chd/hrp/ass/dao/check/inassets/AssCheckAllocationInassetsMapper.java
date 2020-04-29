package com.chd.hrp.ass.dao.check.inassets;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssCheckAllocationInassetsMapper extends SqlMapper {

	List<Map<String, Object>> queryAssCheckAllocationInassets(
			Map<String, Object> entityMap);

	List<Map<String, Object>> queryAssCheckAllocationInassets(
			Map<String, Object> entityMap, RowBounds rowBounds);

}
