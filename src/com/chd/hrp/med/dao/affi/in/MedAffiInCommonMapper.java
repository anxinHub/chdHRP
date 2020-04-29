/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.med.dao.affi.in;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.med.entity.MedRequireDetail;
/**
 * 
 * @Description:
 * 代销材料期初入库
 * @Table:
 * MED_IN
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
 
public interface MedAffiInCommonMapper extends SqlMapper{
	
	/**
	 * 审核 入库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int auditMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 销审 入库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public int unAuditMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 查询 代销入库 配套导入信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedAffiInMatchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单导入  查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单导入  查看明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedOrderDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单导入  选中订单的明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryOrderDetailImp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 协议导入  查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMedAffiInPro(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 入库确认
	 * @param inMap
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmAffiIn(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 冲账  查询被冲账的主表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryByCodeOffSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 冲账 查询被冲账的明细表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDetailByCodeOffSet(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 冲账  插入到对应关系表 med_affi_back_rela
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int addMedAffiBackRela(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除订单关系表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int deleteOrderRela(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 明细数据是否已经全部维护发票信息 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public int existsMedAffiInDetailByInvoice(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 维护发票页面跳转
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 上一张
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiUpOutId(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 下一张
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiNextOutId(Map<String, Object> entityMap) throws DataAccessException;
	
	
	
	//代销入库主表模板打印
	public Map<String, Object> queryMedAffiInPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
		
	//代销入库主表模板打印
	public List<Map<String, Object>> queryMedAffiInPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
	//代销入库明细表模板打印
    public List<Map<String, Object>> queryMedAffiInPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
	//代销入库明细表模板汇总打印
    public List<Map<String, Object>> queryMedAffiInPrintTemlateByDetailCollect(Map<String,Object> entityMap) throws DataAccessException;
    
    /**
     * 历史引入查询明细数据
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public List<Map<String, Object>> queryMedAffiInHistoryDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 订单导入明细查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiInOrderDetailN(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAffiInOrderDetailN(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 更新订单状态
	 * @param entityMap
	 * @throws DataAccessException
	 */
	public void updateAffiOrderStates(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询订单状态
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiOrderIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 获得删除的Id
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAffiDeleteOldIds(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除时获得iD
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiOrderId(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 批量添加选择材料
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiInInvBatch(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMedAffiInInvBatch(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 批量选择材料生成入库单
	 * @param invList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMedAffiInInvBatchDetail(List<Map<String, Object>> invList)  throws DataAccessException;
	/**
	 * 显示明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryDetails(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String,Object>> queryDetails(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryAffiOrderRelaExists(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 *  先查询选中的供应商有无入库
	 */
	public int queryMedAffiInCommonInBySup(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取业务单据中该材料该供应商 的入库数量 
	 */
	public Map<String, Object> queryMedAffiInCommonInAmount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取业务单据中该材料该供应商 的出库数量 
	 */
	public int queryMedAffiInCommonOutAmount(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取业务单据中该材料该供应商 的入库数量来判断是否是统一供应商  
	 */
	public Map<String, Object> queryMedAffiInCommonInAmountBySup(Map<String, Object> entityMap) throws DataAccessException;
	
		
}
