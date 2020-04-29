package com.chd.hrp.pac.dao.skht.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactDetSKHTEntity;

public interface PactDetSKHTMapper extends SqlMapper{


	void deleteAllBatch(List<PactDetSKHTEntity> listVo);

	void deleteByPactCode(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	Integer queryMaxDetailId(Map<String, Object> entityMap);

	List<PactDetSKHTEntity> queryByPactCodeList(Map<String, Object> map);

}
