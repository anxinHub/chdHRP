package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentStatistical;
/**
 * 考核统计
 * @author Administrator
 *
 */
public interface HrAssessmentStatisticalMapper  extends SqlMapper{
	/**
	 * 查询考核名称
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryEvalCode(Map<String, Object> entityMap);
    /**
     * 打印
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryAssessmentByPrint(Map<String, Object> entityMap);
	

}
