package com.chd.hrp.pac.dao.basicset.common;

import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactDeleteMapper extends SqlMapper{

	Integer isExistsDataByTable(Map<String, Object> map);
}
