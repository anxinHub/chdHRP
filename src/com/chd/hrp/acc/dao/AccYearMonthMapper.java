package com.chd.hrp.acc.dao;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccYearMonth;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

public abstract interface AccYearMonthMapper extends SqlMapper
{
  public abstract int addAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int addBatchAccYearMonth(List<Map<String, Object>> paramList)
    throws DataAccessException;

  public abstract List<AccYearMonth> queryAccYearMonth(Map<String, Object> paramMap, RowBounds paramRowBounds)
    throws DataAccessException;

  public abstract List<AccYearMonth> queryYearMonthBySelect(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract List<AccYearMonth> queryMonthBySelect(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract List<AccYearMonth> queryAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract AccYearMonth queryAccYearMonthByCode(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int deleteAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int deleteBatchAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int updateAccYearMonth(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int updateBatchAccYearMonth(List<Map<String, Object>> paramList)
    throws DataAccessException;
  
  
  public abstract int updateModAccYearMonth(Map<String, Object> paramMap)
   throws DataAccessException;

  public abstract List<String> queryYearMonthByMy97(Map<String, Object> paramMap)
    throws DataAccessException;
 
  public abstract String queryAccYearMonthNow(Map<String, Object> paramMap)
    throws DataAccessException;
  
  public abstract String queryAccYearMonthAllNow(Map<String, String> paramMap)
		    throws DataAccessException;
  
  public abstract List<Map<String,Object>> queryYearMonthByInit(Map<String, Object> paramMap)throws DataAccessException;
  
  
  //根据年度查找月份
  public abstract List<AccYearMonth> queryYearMonthByAccYearSelect(Map<String, Object> paramMap)
		    throws DataAccessException;
}