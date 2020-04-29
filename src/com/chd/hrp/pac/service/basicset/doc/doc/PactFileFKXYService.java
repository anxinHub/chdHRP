package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

public interface PactFileFKXYService {

	String queryPactExecDoc(Map<String, Object> mapVo);

	String deletePactExecFKXYFile(List<Map<String, Object>> list);

	String queryPactFileTypeFKXY(Map<String, Object> mapVo);

	String saveFileDocFKXY(List<Map<String, Object>> newList);


}
