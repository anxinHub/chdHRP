package com.chd.hrp.pac.service.basicset.doc.temp;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactFileTempEntity;

public interface PactFileTempSKXYService {

	String queryPactFileTempSKXY(Map<String, Object> page);

	String addPactFileTempSKXY(List<PactFileTempEntity> listVo);

	String queryPactTypeSKXYTree(Map<String, Object> page);
	
	String queryPactFileTempSKXYfile(Map<String ,Object>page);

}
