package com.chd.hrp.pac.service.fkht.execute;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface PactAssetPurchaseService {

	String queryPactAssetPurchaseFKHT(Map<String, Object> mapVo);
	List<Map<String,Object>> queryPactAssetPurchaseFKHTPrint(Map<String, Object> mapVo);

	String queryPactAssetPurchaseDetailFKHT(Map<String, Object> mapVo);
	List<Map<String,Object>> queryPactAssetPurchaseDetailFKHTPrint(Map<String, Object> mapVo);
	
	/**
	 * 付款记录 查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssetPurchasePayPay(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 发票记录 查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssetPurchasePayBill(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 资产采购应付款 查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssetPurchasePay(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 资产采购应付款明细 查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssetPurchasePayMD(Map<String, Object> mapVo) throws DataAccessException;
	
	
	/**
	 * 资产采购执行查询
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTDet(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资产采购执行查询 入库链接查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTDetIn(Map<String, Object> mapVo) throws DataAccessException;
	/**
	 * 资产采购执行查询  退货链接查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTDetBack(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 资产采购执行汇总查询  安装链接查询
	 * @param page 
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTMainIns(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 资产采购执行汇总查询  验收链接查询
	 * @param page 
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTMainAccept(Map<String, Object> page) throws DataAccessException;

	/**
	 * 资产采购执行汇总查询  入库链接查询
	 * @param page 
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTMainIn(Map<String, Object> page) throws DataAccessException;

	/**
	 * 资产采购执行汇总查询  退货链接查询
	 * @param page 
	 * @return
	 * @throws DataAccessException
	 */
	public String queryPactAssPurchaseFKHTMainBack(Map<String, Object> page) throws DataAccessException;

}
