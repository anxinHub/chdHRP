/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.med.service.storage.in;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.med.entity.MedInResource;
/**
 * 
 * @Description:
 * 08105 药品药品表
 * @Table:
 * MED_INV
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedStorageInService extends SqlService {
	
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
	public String copyMedStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量冲账<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String offsetMedStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedStorageIn(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedStorageIn(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMedStorageIn(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量审核<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String auditMedStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量消审<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String unAuditMedStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

	/**
	 * @Description 
	 * 批量入库确认<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String confirmMedStorageInBatch(List<Map<String, Object>> entityMap)throws DataAccessException;

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
	public String updateMedStorageInInvoice(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 获取下一张或上一张入库单
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMedStorageInBeforeOrNext(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询资金来源
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public MedInResource queryMedInResource(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 查询资金来源列表
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public List<MedInResource> queryMedInResourceList(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 明细数据是否已经全部维护发票信息
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String existsMedInDetailByInvoice(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 生成发票
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String addMedBillByInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 维护发票明细列表
	 * @param entityMap
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	public String queryMedInMainByInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库模板打印（包含主从表）
	public String queryMedInByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;
	
	//入库模板打印（包含主从表）新版打印
	public  Map<String, Object> queryMedInByPrintTemlateNewPrint(Map<String,Object> entityMap) throws DataAccessException;
	//入库模板打印（合并打印）新版打印
	public  Map<String, Object> queryMedInByNewCombinePrint(Map<String,Object> entityMap) throws DataAccessException;
		
	//合并打印
	public String querymergePrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

	public String queryOrderDetailNew(Map<String, Object> entityMap) throws DataAccessException;
	
	public String addInByOrder(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException;

	public String mergeOffsetMedInMain(Map<String, Object> entityMap) throws DataAccessException;

	public Map<String, Object> queryMedInOffsetMainByCode(Map<String, Object> entityMap) throws DataAccessException;

	public void updateMedOrderState(Map<String, Object> entityMap) throws DataAccessException;
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

	public String queryAllStorageMedbySupId(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 关闭药品
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedStorageInCloseInv(List<Map<String, Object>> entityMap)  throws DataAccessException;
	/**
	 * 取消关闭药品
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String updateMedStorageInCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	
	public String verifyMedClosingDate(List<Map<String, Object>> listVo);
	
	public String queryInvInDetail(Map<String, Object> mapVo);


	
}
