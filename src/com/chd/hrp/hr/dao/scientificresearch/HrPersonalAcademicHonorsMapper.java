package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicHonors;
/**
 * 个人学术荣誉信息
 * @author Administrator
 *
 */
public interface HrPersonalAcademicHonorsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrPersonalAcademicHonors> queryPersonalAcademicHonorsById(Map<String, Object> entityMap);
    /**
     * 删除个人学术荣誉信息
     * @param entityList
     */
	void deletePersonalAcademicHonors(List<HrPersonalAcademicHonors> entityList);
	

}
