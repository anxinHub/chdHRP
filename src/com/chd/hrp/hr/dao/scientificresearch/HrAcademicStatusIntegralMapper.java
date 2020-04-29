package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicStatusIntegral;
/**
 * 个人学术地位积分
 * @author Administrator
 *
 */
public interface HrAcademicStatusIntegralMapper  extends SqlMapper{
    /**
     * 删除个人学术地位积分
     * @param entityList
     */
	void deleteAcademicStatusIntegral(List<HrAcademicStatusIntegral> entityList);
	/**
	 * 查询学术地位积分
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStatus(Map<String, Object> entityMap);
	/**
	 * 删除所有数据
	 * @param entityMap
	 */
	void deleteAcademicStatus(Map<String, Object> entityMap);
	/**
	 * 继承
	 * @param entityMap
	 * @return
	 */
	int copyStatus(Map<String, Object> entityMap);
	/**
	 * 查询满分表
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryStatusTot(Map<String, Object> entityMap);
	/**
	 * 更新满分表
	 * @param entityMap
	 */
	void updateStatusTot(Map<String, Object> entityMap);
	/**
	 * 新增满分表
	 * @param entityMap
	 */
	void addStatusTot(Map<String, Object> entityMap);
	/**
	 * 查询满分标准
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryTotMain(Map<String, Object> entityMap);
	Map<String, Object> queryAcadeStatus(Map<String, Object> entityMap);
	/**
	 * 查询上一年数据
	 * @param entityMap
	 * @return int
	 * @author yangyunfei
	 */
	int queryAcademicStatusByLastYear(Map<String, Object> entityMap);
	

}
