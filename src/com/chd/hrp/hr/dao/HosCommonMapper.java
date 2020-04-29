package com.chd.hrp.hr.dao;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HosCommonMapper extends SqlMapper{

	List<Map<String, Object>> queryHosUserPermByUserId(Map<String, Object> entityMap);

	/**
	 * 查询档案库人员限定
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrStoreCondition(Map<String, Object> entityMap);

}
