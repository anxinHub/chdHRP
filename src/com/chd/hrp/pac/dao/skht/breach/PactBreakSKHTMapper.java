package com.chd.hrp.pac.dao.skht.breach;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.breach.PactBreakSKHTEntity;

public interface PactBreakSKHTMapper extends SqlMapper {

	void deleteAllbatch(List<PactBreakSKHTEntity> listVo);

	void updateState(Map<String, Object> map);

	List<Map<String, Object>> queryForPrint(Map<String, Object> entityMap);


}
