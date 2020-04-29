package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;

public interface PactDocFKHTService {

	String queryPactDocFKHT(Map<String, Object> page);

	String addPactDocFKHT(Map<String, Object> page);

	String deletePactDocFKHT(List<PactDocEntity> mapVo);

	String addBatchPactDocFKHT(List<PactDocEntity> mapVo);

	//String updatePactDocFKHT(Map<String, Object> mapVo);
	
	String updatePactDocFKHT(List<PactDocEntity> mapVo);	//add by yh

	String queryPactDocFKHTForExec(Map<String, Object> mapVo);

	String queryPactFKHTType(Map<String, Object> mapVo);

}
