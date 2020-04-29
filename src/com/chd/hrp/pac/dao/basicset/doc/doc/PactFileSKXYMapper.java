package com.chd.hrp.pac.dao.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactFileSKXYMapper extends SqlMapper {

	List<Map<String, Object>> queryPactExecDoc(Map<String, Object> mapVo);

	void addExecSelectDoc(List<Map<String, Object>> listVo);

	void addPactExecSKXYFileBatch(List<Map<String, Object>> listVo);

	void deletePactExecSKXYFile(List<Map<String, Object>> list);

	void deletePactExecSKXYFileDoc(List<Map<String, Object>> list);

	List<Map<String, Object>> queryPactFileTypeSKXY(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactExecFile(Map<String, Object> mapVo);

	int queryExistsFile(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	void deleteDocFileByPactCodeList(List<Map<String, Object>> listMap);

}
