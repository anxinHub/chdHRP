package com.chd.hrp.pac.service.basicset.doc;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactFileTypeEntity;

public interface PactFileTypeService {

	String queryPactFileType(Map<String, Object> page);

	String addPactFileType(Map<String, Object> page);

	PactFileTypeEntity queryPactFileTypeByCode(Map<String, Object> mapVo);

	String deletePactFileType(List<PactFileTypeEntity> mapVo);

	String updatePactFileType(Map<String, Object> mapVo);

	String queryPactFileTypeSimple(Map<String, Object> page);

}
