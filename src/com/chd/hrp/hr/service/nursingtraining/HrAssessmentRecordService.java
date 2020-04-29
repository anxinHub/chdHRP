package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentRecord;

/**
 * 考核记录
 * @author Administrator
 *
 */
public interface HrAssessmentRecordService {
    /**
     * 增加考核记录
     * @param mapVo
     * @return
     */
	String addAssessmentRecord(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 跳转修改页面
     * @param mapVo
     * @return
     */
	HrAssessmentRecord queryByCodeAssessmentRecord(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改考核记录
	 * @param mapVo
	 * @return
	 */
	String updateAssessmentRecord(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除考核记录
	 * @param listVo
	 */
	String  deleteAssessmentRecord(List<HrAssessmentRecord> listVo)throws DataAccessException;
	/**
	 * 查询考核记录
	 * @param page
	 * @return
	 */
	String queryAssessmentRecord(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询左侧菜单
	 * @param mapVo
	 * @return
	 */
	String queryAssessmentRecordTree(Map<String, Object> mapVo)throws DataAccessException;

}
