package com.chd.hrp.pac.dao.fkht.payment;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.payment.PactPayDetFKHTEntity;

public interface PactPayDetFKHTMapper extends SqlMapper {

	void deleteByPayCode(List<PactPayDetFKHTEntity> list);

	void deleteAllBatch(List<PactPayDetFKHTEntity> listVo);
	
	Integer queryMaxDetailId(Map<String, Object> map);

}
