package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrQuarterlyAssessment;

/**
 * 季度护士长考核
 * @author Administrator
 *
 */
public interface HrQuarterlyAssessmentService {
    /**
     * 增加季度护士长考核
     * @param mapVo
     * @return
     */
	String addQuarterlyAssessment(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrQuarterlyAssessment queryByCodeQuarterlyAssessment(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改季度护士长考核
	 * @param mapVo
	 * @return
	 */
	String updateQuarterlyAssessment(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除季度护士长考核
	 * @param listVo
	 */
	String  deleteQuarterlyAssessment(List<HrQuarterlyAssessment> listVo)throws DataAccessException;
	/**
	 * 查询季度护士长考核
	 * @param page
	 * @return
	 */
	String queryQuarterlyAssessment(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryQuarterlyAssessmentTree(Map<String, Object> mapVo)throws DataAccessException;

}
