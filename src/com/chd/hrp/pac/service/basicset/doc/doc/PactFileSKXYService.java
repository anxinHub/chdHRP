package com.chd.hrp.pac.service.basicset.doc.doc;

import java.util.List;
import java.util.Map;

public interface PactFileSKXYService {

	String queryPactExecDoc(Map<String, Object> mapVo);

	String deletePactExecSKXYFile(List<Map<String, Object>> list);

	String queryPactFileTypeSKXY(Map<String, Object> mapVo);

	String saveFileDocSKXY(List<Map<String, Object>> newList);

}
