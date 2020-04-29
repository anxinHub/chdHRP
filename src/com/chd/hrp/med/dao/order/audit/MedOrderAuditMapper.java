package com.chd.hrp.med.dao.order.audit;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MedOrderAuditMapper extends SqlMapper{

  
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
  public Map<String, Object> queryMedOrderAuditPrintTemlateByMain(Map<String,Object> entityMap) throws DataAccessException;
  public List<Map<String, Object>> queryMedOrderAuditPrintTemlateByMainBatch(Map<String,Object> entityMap) throws DataAccessException;
  //入库明细表模板打印
  public List<Map<String, Object>> queryMedOrderAuditPrintTemlateByDetail(Map<String,Object> entityMap) throws DataAccessException;
  

}
