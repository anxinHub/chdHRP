package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrQuarterlyAssessment;
/**
 * 季度护士长考核
 * @author Administrator
 *
 */
public interface HrQuarterlyAssessmentMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrQuarterlyAssessment> queryQuarterlyAssessmentById(Map<String, Object> entityMap);
    /**
     * 删除季度护士长考核
     * @param entityList
     */
	void deleteQuarterlyAssessment(List<HrQuarterlyAssessment> entityList);
	

}
