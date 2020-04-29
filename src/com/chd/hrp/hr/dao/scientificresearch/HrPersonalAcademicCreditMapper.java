package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicCredit;

/**
 * 个人学术荣誉积分
 * @author Administrator
 *
 */
public interface HrPersonalAcademicCreditMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrPersonalAcademicCredit> queryPersonalAcademicCreditById(Map<String, Object> entityMap);
    /**
     * 删除个人学术荣誉积分
     * @param entityList
     */
	void deletePersonalAcademicCredit(List<HrPersonalAcademicCredit> entityList);
	/**
	 * 查询学术荣誉
	 * @param entityMap 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHonorsName(Map<String, Object> entityMap);
	/**
	 * 查询是否有数据（保存学术荣誉满分数值时）
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryAcadeHonor(Map<String, Object> entityMap);
	/**
	 * 更新学术荣誉满分数值
	 * @param entityMap
	 */
	void updateAcadeHonor (Map<String, Object> entityMap);
	/**
	 * 添加学术荣誉满分数值
	 * @param entityMap
	 */
	void addAcadeHonor (Map<String, Object> entityMap);
	/**
	 * 继承时先删除
	 * @param entityMap
	 */
	void deleteHonorCode(Map<String, Object> entityMap);
	/**
	 * 继承时后插入
	 * @param entityMap
	 * @return
	 */
	int inheritAcademicCredit(Map<String, Object> entityMap);
	/**
	 * 继承时先查去年有没有数据
	 * @param entityMap
	 * @return int
	 * @author yangyunfei
	 */
	int queryHonorByLastYear(Map<String, Object> entityMap);

}
