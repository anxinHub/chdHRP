package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;

public interface PactDocSKHTService {

	String queryPactDocSKHT(Map<String, Object> page);

	String addPactDocSKHT(Map<String, Object> page);

	String deletePactDocSKHT(List<PactDocEntity> mapVo);

	String addBatchPactDocSKHT(List<PactDocEntity> mapVo);

	String updatePactDocSKHT(Map<String, Object> mapVo);

	String queryPactDocSKHTForExec(Map<String, Object> mapVo);

	String queryPactSKHTType(Map<String, Object> mapVo);

}
