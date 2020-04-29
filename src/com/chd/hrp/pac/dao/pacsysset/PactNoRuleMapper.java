package com.chd.hrp.pac.dao.pacsysset;


import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactNoRuleMapper extends SqlMapper{

	String queryMaxId(Map<String, Object> mapVo);

	Map<String, Object> queryPactTypeByCode(Map<String, Object> mapVo);

	String queryTypeCodeByPactCode(Map<String, Object> mapVo);

	int queryPreExists(Map<String, Object> mapVo);

	int queryPactNoRuleUsing(Map<String, Object> mapVo);


}
