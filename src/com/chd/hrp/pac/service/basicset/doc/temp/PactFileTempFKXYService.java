package com.chd.hrp.pac.service.basicset.doc.temp;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.basicset.doc.PactFileTempEntity;

public interface PactFileTempFKXYService {

	String queryPactFileTempFKXY(Map<String, Object> page);

	String addPactFileTempFKXY(List<PactFileTempEntity> listVo);

	String queryPactTypeFKXYTree(Map<String, Object> page);
	
	String queryPactFileTempFKXYfile (Map<String, Object> page);
}
