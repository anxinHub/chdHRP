package com.chd.hrp.acc.service.paper;

import java.util.Map;

import java.util.List;

public interface AccPaperIncomeService {

	/**
	 * 票据类型下拉框 service
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeType_code(Map<String, Object> mapVo);

	/**
	 * 币种下拉加载 service
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeMoney(Map<String, Object> mapVo);

	/**
	 * 汇率下拉加载 service
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeRatename(Map<String, Object> mapVo);

	/**
	 * 核算项下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeCheckItemNo(Map<String, Object> mapVo);

	/**
	 * 核算类下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeCheckTypeId(Map<String, Object> mapVo);

	/**
	 * 应收票据添加
	 * @param mapVo
	 * @return
	 */
	String addPaperIncome(Map<String, Object> mapVo);

	/**
	 * 应收票据查询
	 * @param mapVo
	 * @return
	 */
	String queryAccPaperIncome(Map<String, Object> mapVo);

	/**
	 * 应收票据修改回显
	 * @param mapVo
	 * @return
	 */
	Map<String, Object> accPaperIncomeUpdateQuery(Map<String, Object> mapVo);

	/**
	 * 应收票据修改
	 * @param mapVo
	 * @return
	 */
	String updatePaperIncome(Map<String, Object> mapVo);

	/**
	 * 背书人表格回显
	 * @param mapVo
	 * @return
	 */
	String queryAccPaperIncomeBook(Map<String, Object> mapVo);
	
	/**
	 * 删除票据信息
	 * @param list
	 * @param mapVo
	 * @return
	 */
	String deleteAccPaperIncome(List<Map> list, Map<String, Object> mapVo);

	/**
	 * 票据审核
	 * @param list
	 * @param mapVo
	 * @return
	 */
	String updateAuditAccPaperIncome(List<Map> list, Map<String, Object> mapVo);

	/**
	 * 票据取消审核
	 * @param list
	 * @param mapVo
	 * @return
	 */
	String updateCancelAccPaperIncome(List<Map> list, Map<String, Object> mapVo);

	/**
	 * 票据作废
	 * @param list
	 * @param mapVo
	 * @return
	 */
	String updateZuofeiAccPaperIncome(List<Map> list, Map<String, Object> mapVo);

	/**
	 * 退票
	 * @param mapVo
	 * @return
	 */
	String updateRefundAccPaperIncome(Map<String, Object> mapVo);

	/**
	 * 收款
	 * @param mapVo
	 * @return
	 */
	String updatePutAccPaperIncome(Map<String, Object> mapVo);

	/**
	 * 条件查询-制单人加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeOpCreateuser(Map<String, Object> mapVo);

	/**
	 * 条件查询-审核人下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeOpAudituser(Map<String, Object> mapVo);

	/**
	 * 删除背书
	 * @param mapVo
	 * @return
	 */
	String deletePaperIncomeBeishu(Map<String, Object> mapVo);

	/**
	 * 条件查询-核算项下拉加载
	 * @param mapVo
	 * @return
	 */
	List<Map<String,Object>> queryAccPaperIncomeCheckNo(Map<String, Object> mapVo);

}
