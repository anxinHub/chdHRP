package com.chd.hrp.pac.service.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;


public interface PactDetSKXYService {

	String queryPactDetSKXY(Map<String, Object> mapVo);

	String updatePactDetSKXY(List<PactDetSKXYEntity> list);

	String deletePactDetSKXY(List<PactDetSKXYEntity> listVo);

	String deletePactDetSKXYByPactCode(List<Map<String, Object>> listVo);

}
