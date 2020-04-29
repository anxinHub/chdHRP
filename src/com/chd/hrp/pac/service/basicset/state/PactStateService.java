package com.chd.hrp.pac.service.basicset.state;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.state.PactStateEntity;

public interface PactStateService {

	String queryPactState(Map<String, Object> page);

	String addPactState(Map<String, Object> page);

	PactStateEntity queryPactStateByCode(Map<String, Object> mapVo);

	String deletePactState(List<PactStateEntity> mapVo);

	String updatePactState(Map<String, Object> mapVo);

}
