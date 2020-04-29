package com.chd.hrp.pac.dao.fkht.payment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.fkht.payment.PactPayMainFKHTEntity;

public interface PactPayMainFKHTMapper extends SqlMapper {

	void updateState(Map<String, Object> map);

	void deleteByPayCodeBatch(List<PactPayMainFKHTEntity> listVo);

	List<Map<String, Object>> queryForPrint(Map<String, Object> mapVo);

	List<Map<String,Object>> queryPactPayMainFKHTDetail(Map<String, Object> entityMap);

	List<Map<String,Object>> queryPactPayMainFKHTDetail(Map<String, Object> entityMap, RowBounds rowBounds);
}
