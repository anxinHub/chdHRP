package com.chd.hrp.med.service.order.audit;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MedOrderAuditService extends SqlService{
	
	/**
	 * 订单审核页面--查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryOrderAudit(Map<String, Object> entityMap)  throws DataAccessException;	
	
	/**
	 * 订单审核页面--查看明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryDetail(Map<String, Object> entityMap) throws DataAccessException;	
	
	/**
	 * 订单审核页面--审核
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String auditOrderMain(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 订单审核页面--取消审核
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String unAuditOrderMain(List<Map<String, Object>> entityList) throws DataAccessException;
	
	/**
	 * 订单审核页面--发送
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public String sendOutOrderMain(List<Map<String, Object>> entityList) throws DataAccessException;

	public String queryMedOrderAuditByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	

}
