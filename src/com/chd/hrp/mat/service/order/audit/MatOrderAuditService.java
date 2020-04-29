package com.chd.hrp.mat.service.order.audit;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MatOrderAuditService extends SqlService{
	
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
	public String sendOutOrderMain(List<Map<String, Object>> entityList,Map<String, Object> map) throws DataAccessException;

	public String queryMatOrderAuditByPrintTemlate(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 订单审核 查询- 新打印
	 * @param page
	 * @return
	 */
	public Map<String, Object>  queryMatOrderAuditByPrintTemlateNewPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 订单编制 查询- 显示明细 
	 * @param page
	 * @return
	 */
	public String queryShowDetailCheck(Map<String, Object> page);
	
	/**
	 * 订单撤销
	 * @param listVo
	 * @return
	 */
	public String revokeOutOrderMain(List<Map<String, Object>> listVo) throws DataAccessException;

}
