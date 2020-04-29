package com.chd.hrp.acc.service;

import com.chd.hrp.acc.entity.AccYear;
import java.util.List;
import java.util.Map;
import org.springframework.dao.DataAccessException;

public abstract interface AccYearService
{
  public abstract String addAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String addBatchAccYear(List<Map<String, Object>> paramList)
    throws DataAccessException;

  public abstract String queryAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract AccYear queryAccYearByCode(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String deleteAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String deleteBatchAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String updateAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract String updateBatchAccYear(List<Map<String, Object>> paramList)
    throws DataAccessException;

  public abstract String importAccYear(Map<String, Object> paramMap)
    throws DataAccessException;
}