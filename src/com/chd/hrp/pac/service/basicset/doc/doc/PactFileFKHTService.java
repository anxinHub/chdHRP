package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

public interface PactFileFKHTService {

	String queryPactExecDoc(Map<String, Object> mapVo);

	String deletePactExecFKHTFile(List<Map<String, Object>> list);

	String queryPactFileTypeFKHT(Map<String, Object> mapVo);

	String saveFileDocFKHT(List<Map<String, Object>> newList);

}
