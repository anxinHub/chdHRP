package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;

public interface PactDocFKXYService {

	String queryPactDocFKXY(Map<String, Object> page);

	String addPactDocFKXY(Map<String, Object> page);

	String deletePactDocFKXY(List<PactDocEntity> mapVo);

	String addBatchPactDocFKXY(List<PactDocEntity> mapVo);

	String updatePactDocFKXY(Map<String, Object> mapVo);

	String queryPactDocFKXYForExec(Map<String, Object> mapVo);

	String queryPactFKXYType(Map<String, Object> mapVo);

}
