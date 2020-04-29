package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

public interface PactFileSKHTService {

	String queryPactExecDoc(Map<String, Object> mapVo);

	String deletePactExecSKHTFile(List<Map<String, Object>> list);

	String queryPactFileTypeSKHT(Map<String, Object> mapVo);

	String saveFileDocSKHT(List<Map<String, Object>> newList);

}
