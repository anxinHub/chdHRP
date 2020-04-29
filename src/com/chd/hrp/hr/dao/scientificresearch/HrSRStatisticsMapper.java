package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrPaperSet;
import com.chd.hrp.hr.entity.scientificresearch.HrResearchTotSet;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedData;

				
public interface HrSRStatisticsMapper extends SqlMapper{
	
	/**
	 * 查询 个人学术荣誉统计
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryHonorStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	List<Map<String, Object>> queryHonorStatistics(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询 个人学术地位统计
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryStatusStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	List<Map<String, Object>> queryStatusStatistics(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询 科研项目与成果统计
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryScientificStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	List<Map<String, Object>> queryScientificStatistics(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询 学术论文发表统计
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryPaperStatistics(Map<String,Object> entityMap) throws DataAccessException;
	
	List<Map<String, Object>> queryPaperStatistics(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 查询父级代码表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<HrFiiedData> queryFieldDataParent(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询动态表头
	 * @param entityMap
	 * @return
	 */
	List<Map<String,Object>> queryScoreSet(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据代码表名查询代码表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<HrFiiedData> queryFiiedDataByTab(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 根据论文类别查询论文积分标准
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<HrPaperSet> queryHrPaperSetByPaperType(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询科研满分标准
	 * @param entityMap
	 * @return
	 */
	HrResearchTotSet queryScoreStandard(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * 个人科研积分统计
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> querySRStatistics(Map<String,Object> entityMap) throws DataAccessException;

	List<Map<String, Object>> querySRStatistics(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
    /**
     * 打印科研积分统计
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> querySRStatisticsByPrint(Map<String, Object> entityMap);
    /**
     * 打印学术地位统计
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryStatusStatisticsByPrint(Map<String, Object> entityMap);
    /**
     * 打印学术论文发表统计
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryPaperDynamicTabHeadByPrint(Map<String, Object> entityMap);
	  /**
     * 打印学术荣誉统计
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryHonorStatisticsByPrint(Map<String, Object> entityMap);
	/**
	 * 科研项目与成果统计
	 * @param entityMap
     * @return
	 */
	List<Map<String, Object>> queryScientificStatisticsByPrint(Map<String, Object> entityMap);

}
