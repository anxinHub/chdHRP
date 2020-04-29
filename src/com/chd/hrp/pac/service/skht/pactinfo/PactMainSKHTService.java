package com.chd.hrp.pac.service.skht.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.pac.entity.skht.pactinfo.PactMainSKHTEntity;

public interface PactMainSKHTService {

	void addPactMainSKHT(Map<String, Object> page);
	
	String queryPactMainSKHT(Map<String, Object> page);

	String queryHosProjDict(Map<String, Object> mapVo);

	PactMainSKHTEntity queryPactMainSKHTByCode(Map<String, Object> mapVo);

	String deletePactMainSKHT(List<PactMainSKHTEntity> listVo);

	String updatePactMainSKHT(Map<String, Object> mapVo);

	String updatePactMainSKHTState(List<String> listVo, String check, String is_init);

	String queryPactMainSKHTByStateCode(Map<String, Object> page);

	String queryPactMainSKHTForWarning(Map<String, Object> page);

	String queryPactMainSKHTForRetWarning(Map<String, Object> page);

	String queryPactMainSKHTForPayWarning(Map<String, Object> page);

	List<Map<String, Object>> queryPactMainSKHTForPayWarningPrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainSKHTForWarningPrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainSKHTForRetWarningPrint(Map<String, Object> entityMap);
	
	/**
	   * 添加时根据合同类型和名称查询该条数据是否存在，同一合同类型名称不可重复
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactSKHTMainByTypeAndName(Map<String, Object> entityMap) throws DataAccessException;

}
