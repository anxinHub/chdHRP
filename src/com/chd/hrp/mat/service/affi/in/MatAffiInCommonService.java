package com.chd.hrp.mat.service.affi.in;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SqlService;


public interface MatAffiInCommonService extends SqlService{
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
	public String auditMatAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 取消审核 代销入库单
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String unAuditMatAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 配套入库查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiInMatchDetail(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 复制入库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String copyMatAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 冲销入库单据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String offsetMatAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 订单导入  查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrder(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单导入  查看明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderDetail(Map<String, Object> entityMap) throws DataAccessException;
	
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
	public String confirmMatAffiInCommon(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 协议导入查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiInPro(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 获取上一张，下一张ID
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiInBeforeOrNext(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 维护发票页面跳转查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAffiInMainByInvoice(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 细数据是否已经全部维护发票信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String existsMatAffiInDetailByInvoice(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryMatAffiInIds(Map<String, Object> entityMap) throws DataAccessException;
	

	
	//入库模板打印（包含主从表）
		public String queryMatAffiInByPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

		public String queryAllMatbySupId(Map<String, Object> mapVo) throws DataAccessException;
		/**
		 * 历史引入查询明细数据
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMatAffiInHistoryDetail(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 历史引入生成入库单
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMatAffiInHisDetail(Map<String, Object> entityMap)  throws DataAccessException;
		/**
		 * 订单导入明细查询
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMatAffiInOrderDetailN(Map<String, Object> entityMap) throws DataAccessException;
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
		public void updateMatAffiOrderState(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 批量添加查询材料
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMatAffiInInvBatch(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 批量生成入库单
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryMatAffiInInvBatchDetail(Map<String, Object> entityMap) throws DataAccessException;
		/**
		 * 显示明细
		 * @param entityMap
		 * @return
		 * @throws DataAccessException
		 */
		public String queryDetails(Map<String, Object> entityMap) throws DataAccessException;

		public List<Map<String, Object>> queryOrderRelaExists(Map<String, Object> mapVo) throws DataAccessException;

		public Map<String,Object> queryMatAffiInByPrintTemlate1(Map<String, Object> map)throws DataAccessException;
		
	
}
