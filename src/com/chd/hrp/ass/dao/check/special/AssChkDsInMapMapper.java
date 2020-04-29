package com.chd.hrp.ass.dao.check.special;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.special.AssChkDsInMap;

public interface AssChkDsInMapMapper extends SqlMapper{

	List<AssChkDsInMap> queryy(Map<String, Object> mapVo4);

}
