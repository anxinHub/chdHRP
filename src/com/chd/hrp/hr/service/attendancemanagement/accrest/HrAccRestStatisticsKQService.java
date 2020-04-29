package com.chd.hrp.hr.service.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrAccRestStatisticsKQService {
    /**
     * 查询积休统计
     * @param page
     * @return
     * @throws DataAccessException
     */
	String queryAccRestStatisticsKQ(Map<String, Object> page) throws DataAccessException;
    /**
     * 查询积休增加
     * @param page
     * @return
     * @throws DataAccessException
     */
	//String overtimeQuery(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询积休减少
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	//String applyingLeavesQuery(Map<String, Object> page)throws DataAccessException;
	/**
	 * 保存积休统计结果
	 * @param entityMap
	 * @return
	 */
	String updateAccRestStatisticsKQ(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAccRestStatisticsKQPrint(Map<String, Object> entityMap) throws DataAccessException; 

}
