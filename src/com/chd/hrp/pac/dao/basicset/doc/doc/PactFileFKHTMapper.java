package com.chd.hrp.pac.dao.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactFileFKHTMapper extends SqlMapper {

	List<Map<String, Object>> queryPactExecDoc(Map<String, Object> mapVo);

	void addExecSelectDoc(List<Map<String, Object>> listVo);

	void addPactExecFKHTFileBatch(List<Map<String, Object>> listVo);

	void deletePactExecFKHTFile(List<Map<String, Object>> list);

	void deletePactExecFKHTFileDoc(List<Map<String, Object>> list2);

	List<Map<String, Object>> queryPactFileTypeFKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactExecFile(Map<String, Object> mapVo);

	int queryExistsFile(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteDocFileByPactCodeList(List<Map<String, Object>> listMap);

}
