package com.chd.hrp.pac.dao.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactFileSKHTMapper extends SqlMapper {

	List<Map<String, Object>> queryPactExecFile(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactFileTypeSKHT(Map<String, Object> mapVo);

	void deletePactExecSKHTFileDoc(List<Map<String, Object>> list);

	void deletePactExecSKHTFile(List<Map<String, Object>> list);

	void addPactExecSKHTFileBatch(List<Map<String, Object>> listVo);

	void addExecSelectDoc(List<Map<String, Object>> listVo);

	int queryExistsFile(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactExecDoc(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteDocFileByPactCodeList(List<Map<String, Object>> listMap);

}
