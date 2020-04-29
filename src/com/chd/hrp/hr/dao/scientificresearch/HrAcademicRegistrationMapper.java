package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicRegistration;
/**
 * 个人学术荣誉登记
 * @author Administrator
 *
 */
public interface HrAcademicRegistrationMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicRegistration> queryAcademicRegistrationById(Map<String, Object> entityMap);
    /**
     * 删除个人学术荣誉登记
     * @param entityList
     */
	void deleteAcademicRegistration(List<HrAcademicRegistration> entityList);
	

}
