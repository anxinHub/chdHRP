package com.chd.hrp.pac.service.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.pactinfo.PactPlanFKHTEntity;

public interface PactPlanFKHTService {

	String queryPactPlanFKHT(Map<String, Object> mapVo);

	String deletePactPlanFKHT(List<PactPlanFKHTEntity> listVo);

	String queryPactPlanFKHTForEdit(Map<String, Object> page);

}
