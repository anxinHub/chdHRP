package com.chd.hrp.acc.service;

import com.chd.hrp.acc.entity.AccYearMonth;
import com.chd.hrp.acc.entity.AccYearMonthMy97;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public abstract interface AccYearMonthService
{
  public abstract String addAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String addBatchAccYearMonth(List<Map<String, Object>> paramList)
    throws DataAccessException;

  public abstract String queryAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String queryYearMonthBySelect(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract AccYearMonthMy97 queryYearMonthByMy97(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String queryMonthBySelect(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String queryAccYearMonthByMenu(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract AccYearMonth queryAccYearMonthByCode(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String deleteAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String deleteBatchAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String updateAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String updateBatchAccYearMonth(List<Map<String, Object>> paramList)
    throws DataAccessException;

  public abstract String importAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract List<AccYearMonth> queryAccTellPeriod(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String queryAccYearMonthNow(Map<String, Object> paramMap)
    throws DataAccessException;
  
  //根据flag标识,取所有系统模块的会计期间
  public abstract String queryAccYearMonthAllNow(String modCode)
		    throws DataAccessException;
  
  //根据年度查找月份
  public abstract String queryYearMonthByAccYearSelect(Map<String, Object> entityMap)
		    throws DataAccessException;
  
}