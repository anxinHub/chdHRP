package com.chd.hrp.pac.service.skht.payment;

import java.util.List;
import java.util.Map;

import com.chd.hrp.pac.entity.skht.payment.PactRecMainSKHTEntity;

public interface PactRecMainSKHTService {

	/**
	 * 付款单查询
	 * 
	 * @param page
	 * @return
	 */
	String queryPactRecMainSKHT(Map<String, Object> page);

	/**
	 * 审核/确认
	 * 
	 * @param listVo
	 * @param check
	 * @return
	 */
	String checkPactRecMainSKHTState(List<PactRecMainSKHTEntity> listVo, String check);

	/**
	 * 删除付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	String deletePactRecMainSKHT(String mapVo);

	/**
	 * 根据编码查询付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	PactRecMainSKHTEntity queryPactRecMainSKHTByCode(Map<String, Object> mapVo);

	/**
	 * 添加付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	String addPactRecMainSKHT(Map<String, Object> mapVo);

	/**
	 * 查询付款详情
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryPactRecDetSKHT(Map<String, Object> mapVo);

	/**
	 * 查询已付款的付款计划
	 * 
	 * @param mapVo
	 * @return
	 */
	String queryPactRecPlanSKHT(Map<String, Object> mapVo);

	/**
	 * 更新付款单
	 * 
	 * @param mapVo
	 * @return
	 */
	String updatePactRecMainSKHT(Map<String, Object> mapVo);

	/**
	 * 打印查询
	 * 
	 * @param mapVo
	 * @return
	 */
	List<Map<String, Object>> queryPactRecMainSKHTPrint(Map<String, Object> mapVo);

	String queryPactRecMainSKHTDetail(Map<String, Object> page);

	String queryPactTypesSKHTByCode(Map<String, Object> mapVo);

}
