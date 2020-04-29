package com.chd.hrp.pac.service.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkxy.pactinfo.PactDetFKXYEntity;

public interface PactDetFKXYService {

	String queryPactDetFKXY(Map<String, Object> mapVo);

	String updatePactDetFKXY(List<PactDetFKXYEntity> list);

	String deletePactDetFKXY(List<PactDetFKXYEntity> listVo);

	String deletePactDetFKXYByPactCode(List<Map<String, Object>> listVo);
	
	String queryPactFKXYMaterial(Map<String, Object> mapVo);

}
