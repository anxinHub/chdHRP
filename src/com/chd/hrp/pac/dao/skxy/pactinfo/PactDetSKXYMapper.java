package com.chd.hrp.pac.dao.skxy.pactinfo;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skxy.pactinfo.PactDetSKXYEntity;

public interface PactDetSKXYMapper extends SqlMapper{

	
	void deletePactDetSKXYByPactCode(List<Map<String,Object>> listMap);

	void deleteAllBatch(List<PactDetSKXYEntity> list);

	Integer queryMaxDetailId(Map<String, Object> map);

	List<PactDetSKXYEntity> queryByPactCodeList(Map<String, Object> map);

}
