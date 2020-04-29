package com.chd.hrp.pac.service.basicset.doc.temp;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactFileTempEntity;

public interface PactFileTempFKHTService {

	String queryPactFileTempFKHT(Map<String, Object> page);

	String addPactFileTempFKHT(List<PactFileTempEntity> listVo);

	String queryPactTypeFKHTTree(Map<String, Object> page);
	String queryPactFileTempFKHTfile (Map<String, Object> page);

}
