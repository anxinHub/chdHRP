/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.mat.service.storage.in;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.mat.entity.MatInResource;
/** 
 *  
 * @Description:
 * 04105 物资材料表
 * @Table: 
 * MAT_INV 
 * @Author: bell 
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */ 
 
public interface MatStorageInService extends SqlService {       
	
	/**
	 * @Description 
	 * 根据主表ID查询明细数据<BR> 
	 * @param  entityMap<BR>
	 *  参数为主键
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryDetailByCode(Map<String,Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量复制<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String copyMatStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量冲账<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String offsetMatStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatStorageIn(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatStorageIn(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMatStorageIn(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMatStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMatStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMatStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 订单结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryOrder(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 订单明细结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryOrderDetail(List<Map<String,Object>> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套表结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatch(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 协议结果集<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryProtocol(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 更新发票号<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String updateMatStorageInInvoice(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取下一张或上一张入库单
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMatStorageInBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询资金来源
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public MatInResource queryMatInResource(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询资金来源列表
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public List<MatInResource> queryMatInResourceList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 明细数据是否已经全部维护发票信息
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String existsMatInDetailByInvoice(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成发票
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String addMatBillByInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 维护发票明细列表
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMatInMainByInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库模板打印（包含主从表）
	public String queryMatInByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库模板打印（包含主从表）新版打印
	public  Map<String, Object> queryMatInByPrintTemlateNewPrint(Map<String,Object> entityMap) throws DataAccessException;
	//入库模板打印（合并打印）新版打印
	public  Map<String, Object> queryMatInByNewCombinePrint(Map<String,Object> entityMap) throws DataAccessException;
		
	//合并打印
	public String querymergePrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

	public String queryOrderDetailNew(Map<String, Object> entityMap) throws DataAccessException;
	
	public String addInByOrder(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException;

	public String mergeOffsetMatInMain(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryMatInOffsetMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	public void updateMatOrderState(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获得系统启用时间
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryModeStartDate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 入库明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

	public String queryAllStorageMatbySupId(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 关闭材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatStorageInCloseInv(List<Map<String, Object>> entityMap)  throws DataAccessException;
	/**
	 * 取消关闭材料
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMatStorageInCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;

	public String verifyMatClosingDate(List<Map<String, Object>> listVo);

	public String queryInvInDetail(Map<String, Object> mapVo);
	
	/**
	 * 根据供应商查询材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatChoiceInvBySup(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 组装材料信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryChoiceInvBySupData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 批量生成发票
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateBatchMatStorageInInvoice(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 根据送货单更新订单状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateMatDeliveryOrderState(Map<String, Object> entityMap) throws DataAccessException;


	public String queryMatStorageMegerBackDetail(Map<String, Object> entityMap) throws DataAccessException;

	public String addInByOrderIsGood(Map<String, Object> mapVo)  throws DataAccessException;

	/**
	 * 查询入库单ID 在发票mat_bill_detail表中是否有明细数据
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Integer queryBillCountByInId(Map<String, Object> mapVo) throws DataAccessException;
}
