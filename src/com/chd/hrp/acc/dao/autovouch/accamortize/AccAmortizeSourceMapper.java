package com.chd.hrp.acc.dao.autovouch.accamortize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccAmortizeSourceMapper extends SqlMapper{

	void deleteAmortizeSourceList(List<Map<String,Object>> list);


}
