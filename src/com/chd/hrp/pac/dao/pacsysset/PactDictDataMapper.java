package com.chd.hrp.pac.dao.pacsysset;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactDictDataMapper extends SqlMapper{

	List<Map<String, Object>> querySelect(Map<String, Object> mapVo);

}
