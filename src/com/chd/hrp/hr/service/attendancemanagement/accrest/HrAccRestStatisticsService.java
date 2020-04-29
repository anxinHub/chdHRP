package com.chd.hrp.hr.service.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAccRestStatisticsService {
    /**
     * 查询积休统计
     * @param page
     * @return
     * @throws DataAccessException
     */
	String queryAccRestStatistics(Map<String, Object> page) throws DataAccessException;
    /**
     * 查询积休增加
     * @param page
     * @return
     * @throws DataAccessException
     */
	String overtimeQuery(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询积休减少
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String applyingLeavesQuery(Map<String, Object> page)throws DataAccessException;
	/**
	 * 保存积休统计结果
	 * @param entityMap
	 * @return
	 */
	String updateAccRestStatistics(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAccRestStatisticsPrint(Map<String, Object> entityMap) throws DataAccessException; 

}
