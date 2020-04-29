package com.chd.hrp.pac.service.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skxy.pactinfo.PactMainSKXYEntity;

public interface PactMainSKXYService {

	String queryPactSKXY(Map<String, Object> mapVo);

	String addPactSKXY(Map<String, Object> mapVo);

	String deletePactSKXY(List<PactMainSKXYEntity> listVo);

	PactMainSKXYEntity queryPactSKXYByPactCode(Map<String, Object> mapVo);

	String updatePactSKXY(Map<String, Object> mapVo);

	String queryPactSKXYForDeadline(Map<String, Object> page);

	String queryPactMainSKXYByStateCode(Map<String, Object> page);

	String checkPactMainSKXY(List<String> listVo, String check, String is_init);

}
