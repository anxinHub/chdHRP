package com.chd.hrp.pac.dao.skht.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.skht.pactinfo.PactPlanSKHTEntity;

public interface PactPlanSKHTMapper extends SqlMapper{


	void deleteAllBatch(List<PactPlanSKHTEntity> listVo);

	void deleteByPactCode(Map<String, Object> entityMap);

	void deleteByPactCodeList(List<Map<String, Object>> listMap);

	List<PactPlanSKHTEntity> queryForPay(Map<String, Object> mapVo);

	List<PactPlanSKHTEntity> queryForEdit(Map<String, Object> mapVo);

	Integer queryMaxDetailId(Map<String, Object> entityMap);
	
	List<Map<String, Object>> queryPactPlanList(Map<String,Object> entityMap) throws DataAccessException;

}
