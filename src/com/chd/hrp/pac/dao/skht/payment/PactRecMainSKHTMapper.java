package com.chd.hrp.pac.dao.skht.payment;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.payment.PactRecMainSKHTEntity;

public interface PactRecMainSKHTMapper extends SqlMapper {

	void updateState(Map<String, Object> map);

	void deleteByRecCodeBatch(List<PactRecMainSKHTEntity> listVo);

	List<Map<String, Object>> queryForPrint(Map<String, Object> mapVo);

	List<Map<String,Object>> queryPactRecMainSKHTDetail(Map<String, Object> entityMap);

	List<Map<String,Object>> queryPactRecMainSKHTDetail(Map<String, Object> entityMap, RowBounds rowBounds);

}
