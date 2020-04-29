package com.chd.hrp.pac.service.fkht.pactinfo;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.pac.entity.fkht.pactinfo.PactMainFKHTEntity;

public interface PactMainFKHTService {

	String addPactMainFKHT(Map<String, Object> page);
	
	String queryPactMainFKHT(Map<String, Object> page);

	PactMainFKHTEntity queryPactMainFKHTByCode(Map<String, Object> mapVo);

	String deletePactMainFKHT(List<PactMainFKHTEntity> listVo);

	String updatePactMainFKHT(Map<String, Object> mapVo);

	String updatePactMainFKHTState(List<String> listVo, String check, String is_init);

	String queryPactMainFKHTByStateCode(Map<String, Object> page);

	String queryPactMainFKHTForWarning(Map<String, Object> page);

	String queryPactMainFKHTForRetWarning(Map<String, Object> page);

	String queryPactMainFKHTForPayWarning(Map<String, Object> page);

	String queryPactMainFKHTForNearRepairWarning(Map<String, Object> page);

	List<Map<String, Object>> queryPactMainFKHTForNearRepairWarningPrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForPayWarningPrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForWarningPrint(Map<String, Object> entityMap);

	List<Map<String, Object>> queryPactMainFKHTForRetWarningPrint(Map<String, Object> entityMap);
	
	/**
	 * 资产购置申请信息查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssApplyFKHT(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 定标信息查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactBidFKHT(Map<String, Object> entityMap) throws DataAccessException;

	public String queryPactSourceFKHT(Map<String, Object> mapVo)  throws DataAccessException;
	
	/**
	 * 变更页面 链接合同查看 数据查询（查询备份表数据)
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public PactMainFKHTEntity queryPactMainFKHTByCodeCopy(Map<String, Object> mapVo)  throws DataAccessException;

}
