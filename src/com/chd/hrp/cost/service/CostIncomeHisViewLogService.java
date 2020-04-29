package com.chd.hrp.cost.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

 
public interface CostIncomeHisViewLogService {

   public String queryCostHisViewLog(Map<String, Object> mapVo) throws DataAccessException;
   
   public String queryCostIncomeHisViewSetting(Map<String, Object> mapVo) throws DataAccessException;
   
   public String deleteBatchDetailCostHisViewLog(List<Map<String, Object>> list) throws DataAccessException;
   
   public String updateOrAddCostIncomeHisViewSetting(Map<String, Object> mapVo) throws DataAccessException;
   
   public String getDateCostIncomeHisView(Map<String, Object> mapVo) throws DataAccessException;
   
   public Map<String, Object> queryCostHisViewInitByCode(Map<String, Object> mapVo)throws DataAccessException;
   
   public Map<String,Object> costRunJob(String etlPath,String jobPath,String viewCode)throws Exception;
}
