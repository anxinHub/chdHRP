package com.chd.hrp.mat.dao.order.audit;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatOrderAuditMapper extends SqlMapper{   

  
  /**
   * 审核订单 
   * @param entityList
   * @return
   * @throws DataAccessException
   */
  public int auditOrderMain(List<Map<String, Object>> entityList) throws DataAccessException;  
  /**
   * 取消审核
   * @param entityList
   * @return
   * @throws DataAccessException
   */
  public int unAuditOrderMain(List<Map<String, Object>> entityList) throws DataAccessException;
  
  /**
   * 发送
   * @param entityList
   * @return
   * @throws DataAccessException
   */
  public int sendOutOrderMain(List<Map<String, Object>> entityList) throws DataAccessException;
  
  
  //入库主表模板打印
  public Map<String, Object> queryMatOrderAuditPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
  public List<Map<String, Object>> queryMatOrderAuditPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
  //入库明细表模板打印
  public List<Map<String, Object>> queryMatOrderAuditPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
  
  /**
	 * 订单编制 显示明细查询
	 * @param entityMap
	 * @return
	 */ 
	public List<?> queryShowDetailCheck(Map<String, Object> entityMap) throws DataAccessException;
	public List<?> queryShowDetailCheck(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	public int updateState(List<Map<String, Object>> list) throws DataAccessException;
	
	
	/**
	 * 查询订单 是否已被生成 供应商出货单
	 */
	public int querySupOrderCount(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 撤销订单
	 */
	public void revokeOutOrderMain(List<Map<String, Object>> listVo) throws DataAccessException;
}
