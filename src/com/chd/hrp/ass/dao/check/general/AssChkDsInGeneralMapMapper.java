package com.chd.hrp.ass.dao.check.general;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.check.general.AssChkDsInGeneralMap;

public interface AssChkDsInGeneralMapMapper extends SqlMapper {

	List<AssChkDsInGeneralMap> queryy(Map<String, Object> mapVo4);

}
