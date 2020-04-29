package com.chd.hrp.acc.dao.vouch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccVouchDifferMapper extends SqlMapper{

	List<Map<String, Object>> queryDataByVouch(Map<String, Object> entityMap);

	List<Map<String, Object>> queryDataByDiffer(Map<String, Object> entityMap);

}
