package com.chd.hrp.ass.dao.check.inassets;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.inassets.AssChkDsInInassetsMap;

public interface AssChkDsInInassetsMapMapper extends SqlMapper{

	List<AssChkDsInInassetsMap> queryy(Map<String, Object> mapVo4);

}
