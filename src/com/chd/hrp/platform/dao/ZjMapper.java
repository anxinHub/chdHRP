package com.chd.hrp.platform.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface ZjMapper extends SqlMapper{
 
	/**
	 * 添加采购目录
	 * @param procureCatalogList
	 */
	public int insertProcureCatalog(List<Map> procureCatalogList);        
	/**
	 * 保存省平台与hrp订单明细对应关系
	 * @param orderRelaList
	 * @return
	 */
	public int insertOrderRela(List<Map<String, Object>> orderRelaList);      
	/**
	 * 通过hrp中的order_id获取对应的省平台台订单明细数据
	 * @param map
	 * @return
	 */
	public List<Map<String, String>> getPlatformOrderDetailsByHrpOrderId(Map<String, Object> map);
	/**
	 * 根据hrp订单id获取省平台明细id
	 * @param mapVo
	 * @return
	 */
	public List<String> getPlatformOrderDetailId(Map<String, Object> mapVo);

	/**
	 * begin
	 *  省平台材料对应关系 查询 、添加、修改、删除
	 */
	public List<Map<String, String>> queryMatInvRela(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, String>> queryMatInvRela(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public int addMatInvRela(Map<String,Object> entityMap)throws DataAccessException;
	public int updateMatInvRela(Map<String,Object> entityMap)throws DataAccessException;
	public int deleteBatchMatInvRela(List<Map<String,Object>> entityList)throws DataAccessException;
	public Map<String, Object> queryMatInvRelaByCode(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 省平台材料对应关系    通过goodsid查询所需要的省平台提供的值，用于保存
	 */
	public Map<String, Object> queryMatInvRelaSptByGoodsId(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 省平台材料对应关系    HRP材料 下拉框查询数据
	 */
	public List<Map<String, String>> queryMatInvHrpBySelect(Map<String, Object> mapVo, RowBounds rowBounds);
	/**
	 * 省平台材料对应关系    省平台 材料 下拉框查询数据
	 */
	public List<Map<String, String>> queryMatInvSptBySelect(Map<String, Object> mapVo, RowBounds rowBounds);
	/**
	 * 修改省平台对应关系表,添加上对应的
	 * @param successList
	 * @return
	 */
	public int updateDeliveryMsg(List<Map<String, Object>> successList);
	/**
	 * 通过hrp订单id查询订单的配送明细
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryPlatformDelivery(Map<String, Object> mapVo);
 
	/**
	 * 获取退货信息
	 * 通过zj_order_map中获取所有的配送单明细，
	 * @param mapVo
	 * @return
	 */ 
	public List<String> getPlatformDistributionByAll(Map<String, Object> mapVo);
 
	/**
	 * 查询订单明细用于生成入库单
	 * @param mapVo
	 * @return
	 */
	public List<Map<String, Object>> queryOrderDetailForIn(Map<String, Object> mapVo);
	/**
	 * 添加入库明细和省平台配送明细对应关系
	 * @param detailList
	 */
	public void insertInDeliveryRela(List<Map<String, Object>> detailList);
 
	/**
	 * 将返回的退货信息 添加到表中ZJ_BACK_ORDER_MAP
	 *  
	 */
	public int insertMatBackResult(List<Map> totalList);
	
 
	/**
	 * 通过hrp退货ID查询退货单据信息
	 * @param mapVo
	 * @return
	 */
    public List<Map<String, Object>> queryMatStorageBackDetailById(Map<String, Object> mapVo);
	/**
	 * 省平台支付主表信息查询
	 * @param mapVo
	 * @return
	 */
    public List<Map<String, Object>> queryMatPayMainByCodeP(Map<String, Object> mapVo);
	/**
	 * 省平台支付明细表信息查询
	 * @param mapVo
	 * @return
	 */
    public List<Map<String, Object>> queryMatPaydetailByCodeP(Map<String, Object> mapVo);
    /**
     * 删除传递的材料信息
     * @param totalList
     */
	public void deleteProcureCatalog(List<Map> totalList);
	/**
	 * 通过hrp材料查询此材料与省平台材料的对应关系
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> queryExistsInvRelaByGoodsId(Map<String, Object> entityMap);
	public String queryOrderDetail(Map<String, Object> lineOrderMap);
	public List<Map<String, Object>> queryInOrder(Map<String, Object> mapVo);
	public int queryMatInvRelaByCodes(Map<String, Object> mapVo);
	public List<Map<String, Object>> queryInvCode(Map<String, Object> entityMap);
	public List<Map<String, Object>> queryInvCode(
			Map<String, Object> entityMap, RowBounds rowBounds);
	public List<Map<String, Object>> queryGoodsid(
			Map<String, Object> entityMap, RowBounds rowBounds);
	public List<Map<String, Object>> queryGoodsid(Map<String, Object> entityMap);
	public List<Map<String, Object>> queryExistsInvRelaByGoodsIdBatch(
			List<Map<String, Object>> dataAddedBatch);
	public List<Map<String, Object>> queryMatInvRelaSptByGoodsIdBatch(
			List<Map<String, Object>> dataAddedBatch);
	public int insertDistribution(List<Map<String, Object>> list);
	public List<Map<String, Object>> queryPlatformDeliveryDis(
			Map<String, Object> mapVo);
	public int deleteDistribution(List<Map<String, Object>> list);
 
}
