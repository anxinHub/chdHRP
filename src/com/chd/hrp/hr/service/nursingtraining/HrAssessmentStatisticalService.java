package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentStatistical;

/**
 * 考核统计
 * @author Administrator
 *
 */
public interface HrAssessmentStatisticalService {
	/**
	 * 查询考核统计
	 * @param page
	 * @return
	 */
	String queryAssessmentStatistical(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询考核名称
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryEvalCode(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 打印
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String,Object>> queryAssessmentByPrint(Map<String, Object> page)throws DataAccessException;
}
