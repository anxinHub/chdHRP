package com.chd.hrp.pac.service.skht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.pactinfo.PactPlanSKHTEntity;

public interface PactPlanSKHTService {

	String queryPactPlanSKHT(Map<String, Object> mapVo);

	String deletePactPlanSKHT(List<PactPlanSKHTEntity> listVo);

	String queryPactPlanSKHTForPay(Map<String, Object> page);

	String queryPactPlanSKHTForEdit(Map<String, Object> page);

}
