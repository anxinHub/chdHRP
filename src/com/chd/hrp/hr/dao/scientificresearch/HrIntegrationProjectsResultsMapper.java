package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrIntegrationProjectsResults;
/**
 * 科研项目与成果积分
 * @author Administrator
 *
 */
public interface HrIntegrationProjectsResultsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrIntegrationProjectsResults> queryIntegrationProjectsResultsById(Map<String, Object> entityMap);
    /**
     * 删除科研项目与成果积分
     * @param entityList
     */
	void deleteIntegrationProjectsResults(List<HrIntegrationProjectsResults> entityList);
	/**
	 * 删除所有数据
	 * @param entityMap
	 */
	void deleteIntegration(Map<String, Object> entityMap);
	/**
	 * 继承
	 * @param entityMap
	 * @return
	 */
	int copyIntegration(Map<String, Object> entityMap);
	/**
	 * 查询满分表
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryProjectsTot(Map<String, Object> entityMap);
	/**
	 * 更新满分表
	 * @param entityMap
	 */
	void updateProjectsTot(Map<String, Object> entityMap);
	/**
	 * 增加满分表
	 * @param entityMap
	 */
	void addProjectsTot(Map<String, Object> entityMap);
	/**
	 * 科研项目满分标准
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryTotMain(Map<String, Object> entityMap);
	/**
	 * 查询满分按年份切换
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryProjByYear(Map<String, Object> entityMap);
	/**
	 * 查询上一年数据
	 * @param entityMap
	 * @return int
	 * @author yangyunfei
	 */
	int queryIntegrationByLastYear(Map<String, Object> entityMap);
	

}
