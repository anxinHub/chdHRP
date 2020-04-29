package com.chd.hrp.pac.dao.basicset.doc.doc;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactFileFKXYMapper extends SqlMapper{

	List<Map<String, Object>> queryPactExecDoc(Map<String, Object> mapVo);

	void addExecSelectDoc(List<Map<String, Object>> list2);

	void addPactExecFKXYFileBatch(List<Map<String, Object>> list1);

	void deletePactExecFKXYFile(List<Map<String, Object>> list);
	
	void deletePactExecFKXYFileDoc(List<Map<String, Object>> list);

	List<Map<String, Object>> queryPactFileTypeFKXY(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactExecFile(Map<String, Object> mapVo);

	int queryExistsFile(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteDocFileByPactCodeList(List<Map<String, Object>> listMap);


}
