package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicPaperIntegration;
/**
 * 学术论文积分
 * @author Administrator
 *
 */
public interface HrAcademicPaperIntegrationMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicPaperIntegration> queryAcademicPaperIntegrationById(Map<String, Object> entityMap);
    /**
     * 删除学术论文积分
     * @param entityList
     */
	void deleteAcademicPaperIntegration(List<HrAcademicPaperIntegration> entityList);
	/**
	 * 添加时先删除
	 * @param entityMap
	 */
	void deletePaper(Map<String, Object> entityMap);
	/**
	 * 查询论文类别下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryPaperType(Map<String, Object> entityMap);
	/**
	 * 查询影响因子下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAffectPara(Map<String, Object> entityMap);

	/**
	 * 继承
	 * @param entityMap
	 * @return
	 */
	int copyAcademicPaper(Map<String, Object> entityMap);
	/**
	 * 查询满分表中是否有数据
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryTot(Map<String, Object> entityMap);
	/**
	 * 更新满分表
	 * @param entityMap
	 */
	void updateTot(Map<String, Object> entityMap);
	/**
	 * 添加满分表中数据
	 * @param entityMap
	 */
	void addTot(Map<String, Object> entityMap);
	/**
	 * 主页跳转查询满分
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryTotMain(Map<String, Object> entityMap);
	/**
	 * 根据年份查询满分标准
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryTotMainByYear(Map<String, Object> entityMap);
	/**
	 * 查询上一年数据
	 * @param entityMap
	 * @return int
	 * @author yangyunfei
	 */
	int queryAcademicPaperByLastYear(Map<String, Object> entityMap);
	

}
