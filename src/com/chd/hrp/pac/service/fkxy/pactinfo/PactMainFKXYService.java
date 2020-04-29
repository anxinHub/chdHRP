package com.chd.hrp.pac.service.fkxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkxy.pactinfo.PactMainFKXYEntity;

public interface PactMainFKXYService {

	String queryPactFKXY(Map<String, Object> mapVo);

	String addPactFKXY(Map<String, Object> mapVo);

	String deletePactFKXY(List<PactMainFKXYEntity> listVo);

	PactMainFKXYEntity queryPactFKXYByPactCode(Map<String, Object> mapVo);

	String updatePactFKXY(Map<String, Object> mapVo);

	String queryPactFKXYForDeadline(Map<String, Object> page);

	String queryPactMainFKXYByStateCode(Map<String, Object> page);

	String checkPactMainFKXY(List<String> listVo, String check, String is_init);
	
	/*
	 * 协议执行总额预警查询
	 */
	String queryPactMoneyProgress(Map<String, Object> page);
}
