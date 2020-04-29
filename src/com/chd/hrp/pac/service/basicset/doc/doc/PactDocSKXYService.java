package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;

public interface PactDocSKXYService {

	String queryPactDocSKXY(Map<String, Object> page);

	String addPactDocSKXY(Map<String, Object> page);

	String deletePactDocSKXY(List<PactDocEntity> mapVo);

	String addBatchPactDocSKXY(List<PactDocEntity> mapVo);

	String updatePactDocSKXY(Map<String, Object> mapVo);

	String queryPactDocSKXYForExec(Map<String, Object> mapVo);

	String queryPactSKXYType(Map<String, Object> mapVo);

}
