package com.chd.hrp.hr.dao.scientificresearch;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrRegistrationStatus;
/**
 *个人学术地位登记
 * @author Administrator
 *
 */
public interface HrRegistrationStatusMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrRegistrationStatus> queryRegistrationStatusById(Map<String, Object> entityMap);
    /**
     * 删除个人学术荣誉申请
     * @param entityList
     */
	void deleteRegistrationStatus(List<HrRegistrationStatus> entityList);
	

}
