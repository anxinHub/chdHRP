/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.storage.in;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
/**
 * 
 * @Description:
 * 常备药品入库
 * @Table:
 * MED_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedStorageInMapper extends SqlMapper{

	 /**
	 * @Description 
	 * 插入入库单订单关系表<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertOrderRela(List<Map<String, Object>> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 删除入库单订单关系表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteOrderRela(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 批量删除入库单订单关系表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int deleteOrderRelaBatch(List<Map<String, Object>> entityList)throws DataAccessException;
	
	 /**
	 * @Description 
	 * 复制主表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertCopyMain(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 复制明细<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertCopyDetail(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 生成冲账主表<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertOffsetMain(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 生成冲账明细<BR> 
	 * @param  Map<String, Object>
	 * @return int
	 * @throws DataAccessException
	*/
	public int insertOffsetDetail(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 审核或消审<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAudit(Map<String, Object> entityMap)throws DataAccessException;

	 /**
	 * @Description 
	 * 批量审核或消审<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int auditOrUnAuditBatch(List<Map<String, Object>> entityList)throws DataAccessException;

	 /**
	 * @Description 
	 * 确认前单据状态判断<BR> 
	 * @param  List<Map<String, Object>>
	 * @return int
	 * @throws DataAccessException
	*/
	public int existsMedInStateForConfirm(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 订单结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryOrder(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 订单结果集<BR>带分页 
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryOrder(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * @Description 
	 * 订单药品结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String,Object>> queryOrderDetail(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 配套结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryMatch(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 协议结果集<BR>全部 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryProtocol(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 更新发票号
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int updateMedStorageInInvoice(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 明细数据是否已经全部维护发票信息
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int existsMedInDetailByInvoice(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 判断发票号是否已被占用
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int existsMedBillByInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 发票补登
	 * @param  entityMap
	 * @return  int
	 * @throws DataAccessException
	*/
	public int updateMedInMainInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 获取生成发票主表信息
	 * @param  entityMap
	 * @return  Map<String, Object>
	 * @throws DataAccessException
	*/
	public Map<String, Object> queryMedInMainForBill(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 获取没有维护发票的明细数据
	 * @param  entityMap
	 * @return  List<Map<String, Object>>
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMedInDetailByNotBill(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * @Description 
	 * 维护发票明细列表
	 * @param  entityMap
	 * @return  List<?>
	 * @throws DataAccessException
	*/
	public List<?> queryMedInMainByInvoice(Map<String,Object> entityMap) throws DataAccessException;
	
	
	//入库主表模板打印
	public Map<String, Object> queryMedInPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
	public  List<Map<String, Object>>  querymergePrint(Map<String,Object> entityMap) throws DataAccessException;
	//合并打印
	public List<Map<String,Object>> querymergePrint(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	public List<Map<String, Object>> queryMedInPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;

	//入库明细表模板打印
	public List<Map<String, Object>> queryMedInPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryOrderDetailNew(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryOrderDetailNew(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedInOffsetMainByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> mergeOffsetMedInMain(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询执行完的订单号
	public String queryOrderIds(Map<String, Object> entityMap) throws DataAccessException;
	//更新订单状态
	public void updateOrderStates(Map<String, Object> map) throws DataAccessException;
	
	//删除入库单时查询订单ID
	public String queryMedOrderId(List<Map<String, Object>> entityList) throws DataAccessException;

	public String queryOrderIdById(Map<String, Object> entityMap) throws DataAccessException;

	public String queryDeleteOldIds(Map<String, Object> entityMap) throws DataAccessException;
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
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryDetails(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 关闭药品
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateMedStorageInCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;
	/**
	 * 取消关闭药品
	 * @param entityList
	 * @throws DataAccessException
	 */
	public void updateMedStorageInCancleCloseInv(List<Map<String, Object>> entityList) throws DataAccessException;

	public List<Map<String, Object>> queryMedInByNewCombinePrintByMain(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryMedInByNewCombinePrintByDetail(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryMedInBarcodeByPrintTemlateNewPrint(
			Map<String, Object> entityMap); 
	
	public Map<String, Object> queryAccYearMonthByYearMonth(Map<String, Object> inMap);

	public int queryMedTermendStoreByYearMonth(Map<String, Object> inMap);

	
	
}
