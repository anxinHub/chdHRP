package com.chd.hrp.hr.service.scientificresearch;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.scientificresearch.HrResearchTotSet;

public interface HrSRStatisticsService {
	/**
	 * 查询 个人学术荣誉统计
	 * @param entityMap
	 * @return
	 */
	String queryHonorStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 个人学术地位统计
	 * @param entityMap
	 * @return
	 */
	String queryStatusStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 科研项目与成果统计
	 * @param entityMap
	 * @return
	 */
	String queryScientificStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询 学术论文发表统计
	 * @param entityMap
	 * @return
	 */
	String queryPaperStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询动态表头
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryDynamicTabHead(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 科研项目与成果统计 动态表头
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryScientificDynamicTabHead(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 学术论文发表统计
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryPaperDynamicTabHead(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科研满分标准
	 * @param entityMap
	 * @return
	 */
	HrResearchTotSet queryScoreStandard(Map<String,Object> entityMap) throws DataAccessException;

	/**
	 * 查询 个人科研积分统计
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String querySRStatistics(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印个人学术荣誉统计
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryHonorStatisticsByPrint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 打印个人学术地位统计
	 * 
	 */
	List<Map<String,Object>> queryStatusStatisticsByPrint(Map<String, Object> page)throws DataAccessException;
	
	/**
	 * 科研项目与成果统计
	 * 
	 */
	List<Map<String,Object>> queryScientificStatisticsByPrint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 打印学术论文发表统计
	 */
	List<Map<String,Object>> queryPaperDynamicTabHeadByPrint(Map<String, Object> page)throws DataAccessException;
	/**
	 * 个人科研积分统计
	 */
	List<Map<String,Object>> querySRStatisticsByPrint(Map<String, Object> page)throws DataAccessException;
}
