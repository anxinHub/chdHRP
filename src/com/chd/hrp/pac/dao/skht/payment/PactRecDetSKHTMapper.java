package com.chd.hrp.pac.dao.skht.payment;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.payment.PactRecDetSKHTEntity;

public interface PactRecDetSKHTMapper extends SqlMapper {

	void deleteByRecCode(List<PactRecDetSKHTEntity> list);

	void deleteAllBatch(List<PactRecDetSKHTEntity> listVo);
	
	Integer queryMaxDetailId(Map<String, Object> map);

}
