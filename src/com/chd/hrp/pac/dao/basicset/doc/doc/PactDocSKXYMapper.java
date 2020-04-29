package com.chd.hrp.pac.dao.basicset.doc.doc;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;

public interface PactDocSKXYMapper extends SqlMapper{

	void deleteAllBatch(List<PactDocEntity> list);

	PactDocTypeEntity queryPactDocByCode(Map<String, Object> mapVo);
	
	Integer queryMaxDocId(Map<String, Object> map);

	void deleteByPactCode(Map<String, Object> map);

	List<PactDocEntity> queryPactDocSKXYForExec(Map<String, Object> mapVo);

	Map<String, Object> queryPactSKXYType(Map<String, Object> mapVo);
}
