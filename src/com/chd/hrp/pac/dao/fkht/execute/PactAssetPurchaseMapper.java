package com.chd.hrp.pac.dao.fkht.execute;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface PactAssetPurchaseMapper extends SqlMapper{

	List<Map<String, Object>> queryPactAssetPurchaseFKHT(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactAssetPurchaseDetailFKHT(Map<String, Object> mapVo);
	
	/**
	 * 付款记录 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssetPurchasePayPay(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 发票记录 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssetPurchasePayBill(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产采购应付款 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssetPurchasePay(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产采购应付款 查询   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssetPurchasePay(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	/**
	 * 资产采购应付款明细 查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssetPurchasePayMD(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产采购应付款明细 查询   分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssetPurchasePayMD(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	
	
	
	/**
	 * 资产采购执行查询  不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTDet(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产采购执行查询  分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTDet(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;

	
	/**
	 * 资产采购执行查询  入库链接查询不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTDetIn(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产采购执行查询  入库链接查询分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTDetIn(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 资产采购执行查询  退货链接查询不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTDetBack(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 资产采购执行查询  退货链接查询分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTDetBack(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	/**
	 * 资产采购执行汇总查询  安装链接查询不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainIns(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产采购执行汇总查询  安装链接查询分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainIns(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 资产采购执行汇总查询  验收链接查询不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainAccept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产采购执行汇总查询  验收链接查询分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainAccept(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 资产采购执行汇总查询  入库链接查询不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainIn(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产采购执行汇总查询  入库链接查询分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainIn(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 资产采购执行汇总查询  退货链接查询不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainBack(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 资产采购执行汇总查询  退货链接查询分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAssPurchaseFKHTMainBack(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
