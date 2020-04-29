package com.chd.hrp.hr.service.nursing;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursing.HrTeachingStatistics;

/**
 * 教学能力统计
 * 
 * @author Administrator
 *
 */
public interface HrTeachingStatisticsService {
	/**
	 * 添加教学能力统计
	 * 
	 * @param mapVo
	 * @return
	 */
	String addTeachingStatistics(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 跳转修改页面教学能力统计
	 * 
	 * @param mapVo
	 * @return
	 */
	HrTeachingStatistics queryByCodeTeachingStatistics(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 修改教学能力统计
	 * 
	 * @param mapVo
	 * @return
	 */
	String updateTeachingStatistics(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 删除教学能力统计
     * @param listVo
     */
	String deleteTeachingStatistics(List<HrTeachingStatistics> listVo)throws DataAccessException;
    /**
     * 查询教学能力统计
     * @param page
     * @return
     */
	String queryTeachingStatistics(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryTeachingStatisticsByPrint(Map<String, Object> entityMap)throws DataAccessException;
}
