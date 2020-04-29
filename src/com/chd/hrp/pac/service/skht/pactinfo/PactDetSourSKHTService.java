package com.chd.hrp.pac.service.skht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSourSKHTEntity;

public interface PactDetSourSKHTService {

	String addPactDetResourceSKHT(List<PactDetSourSKHTEntity> listVo);

	String deletePactDetResourceSKHT(List<PactDetSourSKHTEntity> list);

	String queryPactDetResourceSKHT(Map<String, Object> mapVo);

}
