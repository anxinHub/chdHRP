package com.chd.hrp.med.service.affi.in;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SqlService;


public interface MedAffiInCommonService extends SqlService{
	/**
	 * 根据主键查询明细表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 审核 代销入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String auditMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 取消审核 代销入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String unAuditMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 配套入库查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInMatchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 复制入库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 冲销入库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String offsetMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 订单导入  查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单导入  查看明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedOrderDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单导入 查询导入的订单明细
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String queryOrderDetailImp(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 入库确认
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String confirmMedAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 协议导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInPro(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取上一张，下一张ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInBeforeOrNext(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 维护发票页面跳转查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedAffiInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 细数据是否已经全部维护发票信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMedAffiInDetailByInvoice(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMedAffiInIds(Map<String, Object> entityMap) throws DataAccessException;
	

	
	//入库模板打印（包含主从表）
		public String queryMedAffiInByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

		public String queryAllMedbySupId(Map<String, Object> mapVo) throws DataAccessException;
		/**
		 * 历史引入查询明细数据
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedAffiInHistoryDetail(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 历史引入生成入库单
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedAffiInHisDetail(Map<String, Object> entityMap)  throws DataAccessException;
		/**
		 * 订单导入明细查询
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedAffiInOrderDetailN(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 订单导入保存
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String addAffiInByOrder(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 更新订单状态
		 * @param entityMap
		 * @throws DataAccessException
		 */
		public void updateMedAffiOrderState(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 批量添加查询材料
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedAffiInInvBatch(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 批量生成入库单
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMedAffiInInvBatchDetail(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 显示明细
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

		public List<Map<String, Object>> queryOrderRelaExists(Map<String, Object> mapVo) throws DataAccessException;

		public Map<String,Object> queryMedAffiInByPrintTemlate1(Map<String, Object> map)throws DataAccessException;
		
	
}
