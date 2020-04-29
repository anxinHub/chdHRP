package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicStatusInformation;
/**
 * 个人学术地位信息
 * @author Administrator
 *
 */
public interface HrAcademicStatusInformationMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicStatusInformation> queryAcademicStatusInformationById(Map<String, Object> entityMap);
    /**
     * 删除个人学术地位信息
     * @param entityList
     */
	void deleteAcademicStatusInformation(List<HrAcademicStatusInformation> entityList);
	

}
