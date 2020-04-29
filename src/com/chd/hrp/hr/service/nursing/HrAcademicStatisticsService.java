package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrAcademicStatistics;

/**
 * 学术能力统计
 * 
 * @author Administrator
 *
 */
public interface HrAcademicStatisticsService {
	
    /**
     * 查询学术能力统计
     * @param page
     * @return
     */
	String queryAcademicStatistics(Map<String, Object> page)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAcademicStatisticsPrint(Map<String, Object> entityMap) throws DataAccessException;
 

}
