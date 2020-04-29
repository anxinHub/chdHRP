package com.chd.hrp.acc.dao;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccYear;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

public abstract interface AccYearMapper extends SqlMapper
{
  public abstract int addAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int addBatchAccYear(List<Map<String, Object>> paramList)
    throws DataAccessException;

  public abstract List<AccYear> queryAccYear(Map<String, Object> paramMap, RowBounds paramRowBounds)
    throws DataAccessException;

  public abstract List<AccYear> queryAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract List<AccYear> queryAccYearList(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract AccYear queryAccYearByCode(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int deleteAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int deleteBatchAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int updateAccYear(Map<String, Object> paramMap)
    throws DataAccessException;

  public abstract int updateBatchAccYear(List<Map<String, Object>> paramList)
    throws DataAccessException;
}