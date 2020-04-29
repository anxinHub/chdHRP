package com.chd.hrp.pac.service.basicset.doc;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;

public interface PactDocTypeService {

	String queryPactDocType(Map<String, Object> page);

	String addPactDocType(Map<String, Object> page);

	PactDocTypeEntity queryPactDocTypeByCode(Map<String, Object> mapVo);

	String deletePactDocType(List<PactDocTypeEntity> mapVo);

	String updatePactDocType(Map<String, Object> mapVo);

	String queryPactDocTypeSelect(Map<String, Object> mapVo);



}
