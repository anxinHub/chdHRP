package com.chd.hrp.pac.dao.basicset.doc.doc;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.doc.PactDocEntity;
import com.chd.hrp.pac.entity.basicset.doc.PactDocTypeEntity;
import com.chd.hrp.pac.entity.cmitype.PACTInterfaceType;

public interface PactDocFKHTMapper extends SqlMapper {

	void deleteAllBatch(List<PactDocEntity> list);

	void deleteByPactCode(Map<String, Object> map);

	PactDocTypeEntity queryPactDocByCode(Map<String, Object> mapVo);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	List<PactDocEntity> queryPactDocFKHTForExec(Map<String, Object> mapVo);
	
	Integer queryMaxDocId(Map<String, Object> map);

	Map<String, Object> queryPactFKHTType(Map<String, Object> mapVo);
	
	public int updatePactDocEntity(List<PactDocEntity> list);
}
