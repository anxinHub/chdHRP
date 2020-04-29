package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentRecord;
/**
 * 考核记录
 * @author Administrator
 *
 */
public interface HrAssessmentRecordMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAssessmentRecord> queryAssessmentRecordById(Map<String, Object> entityMap);
    /**
     * 删除考核记录
     * @param entityList
     */
	void deleteAssessmentRecord(List<HrAssessmentRecord> entityList);
	

}
