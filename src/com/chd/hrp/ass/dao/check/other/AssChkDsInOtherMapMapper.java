package com.chd.hrp.ass.dao.check.other;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.other.AssChkDsInOtherMap;

public interface AssChkDsInOtherMapMapper extends SqlMapper{

	List<AssChkDsInOtherMap> queryy(Map<String, Object> mapVo4);

}
