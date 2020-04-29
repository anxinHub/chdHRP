package com.chd.hrp.pac.dao.basicset.state;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.state.PactStateEntity;

public interface PactStateMapper extends SqlMapper{

	void deleteAllBatch(List<PactStateEntity> list);

	PactStateEntity queryPactStateByCode(Map<String, Object> mapVo);

	PactStateEntity queryPactStatByName(Map<String, Object> mapVo);

}
