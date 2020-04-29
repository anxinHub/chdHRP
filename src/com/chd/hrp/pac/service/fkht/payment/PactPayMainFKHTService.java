package com.chd.hrp.pac.service.fkht.payment;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.fkht.payment.PactPayMainFKHTEntity;

public interface PactPayMainFKHTService {

	/**
	 * 付款单查询
	 * 
	 * @param page
	 * @return
	 */
	String queryPactPayMainFKHT(Map<String, Object> page);

	/**
	 * 审核/确认
	 * 
	 * @param listVo
	 * @param check
	 * @return
	 */
	String checkPactPayMainFKHTState(List<PactPayMainFKHTEntity> listVo, String check);

	/**
	 * 删除付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	String deletePactPayMainFKHT(String mapVo);

	/**
	 * 根据编码查询付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	PactPayMainFKHTEntity queryPactPayMainFKHTByCode(Map<String, Object> mapVo);

	/**
	 * 添加付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	String addPactPayMainFKHT(Map<String, Object> mapVo);

	/**
	 * 查询付款详情
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryPactPayDetFKHT(Map<String, Object> mapVo);

	/**
	 * 查询已付款的付款计划
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryPactPayPlanFKHT(Map<String, Object> mapVo);

	/**
	 * 更新付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	String updatePactPayMainFKHT(Map<String, Object> mapVo);

	/**
	 * 打印查询
	 * 
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryPactPayMainFKHTPrint(Map<String, Object> mapVo);

	String queryPactPayMainFKHTDetail(Map<String, Object> page);

	String queryPactFKHTForPayMent(Map<String, Object> mapVo);

}
