package com.chd.hrp.pac.dao.basicset.elsesub;

import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.elsesub.PactElseSubEntity;

public interface PactElseSubMapper extends SqlMapper {

	void deleteAllBatch(Map<String, Object> map);

	PactElseSubEntity queryPactElseSubByName(Map<String, Object> mapVo);

	Integer queryUsing(Map<String, Object> map);
}
