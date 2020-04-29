package com.chd.hrp.ass.dao.assalteration;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface AssAlterationMapper extends SqlMapper {

	List<Map<String, Object>> queryAssAlteration(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAssAlteration(Map<String, Object> entityMap, RowBounds rowBounds);

	List<Map<String, Object>> queryAssAlterationMainPrint(
			Map<String, Object> entityMap);

}
