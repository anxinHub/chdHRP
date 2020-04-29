package com.chd.hrp.pac.service.basicset.doc.temp;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactFileTempEntity;

public interface PactFileTempSKHTService {

	String queryPactFileTempSKHT(Map<String, Object> page);

	String addPactFileTempSKHT(List<PactFileTempEntity> listVo);

	String queryPactTypeSKHTTree(Map<String, Object> page);
	
	String queryPactFileTempSKHTfile(Map<String ,Object>page);

}
