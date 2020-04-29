package com.chd.hrp.pac.dao.basicset.doc.doc;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;

public interface PactDocSKHTMapper extends SqlMapper{

	void deleteAllBatch(List<PactDocEntity> list);

	void deleteByPactCode(Map<String, Object> map);

	PactDocTypeEntity queryPactDocByCode(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	Integer queryMaxDocId(Map<String, Object> map);

	List<PactDocEntity> queryPactDocSKHTForExec(Map<String, Object> mapVo);

	Map<String, Object> queryPactSKHTType(Map<String, Object> mapVo);
}
