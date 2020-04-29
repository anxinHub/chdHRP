package com.chd.hrp.hr.dao.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HrPeriodMapper extends SqlMapper{
	/**
	 * 批量增加期间表
	 * @param list
	 * @throws DataAccessException
	 */
	public void addBatchHrPeriod(List<Map<String, Object>> list) throws DataAccessException;

	public List<Map<String, Object>> queryHrPeriod(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHrPeriod(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 清除
     * @param entityMap
     * @throws DataAccessException
     */
	public void deleteCycle(Map<String, Object> entityMap) throws DataAccessException;
    /**
     * 查询启用年月
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	public Map<String, Object> queryStartData(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHrFlag(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHrResult(Map<String, Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryHrPeriodByYear(Map<String, Object> entityMap);
}
