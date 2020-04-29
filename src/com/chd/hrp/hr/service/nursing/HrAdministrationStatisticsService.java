package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrAdministrationStatistics;

/**
 * 行政能力统计
 * 
 * @author Administrator
 *
 */
public interface HrAdministrationStatisticsService {
    /**
     * 查询行政能力统计
     * @param page
     * @return
     */
	String queryAdministrationStatistics(Map<String, Object> page)throws DataAccessException;
	/**
	 * 打印
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAdministrationStatisticsByPrint(Map<String, Object> mapVo) throws DataAccessException;
}
