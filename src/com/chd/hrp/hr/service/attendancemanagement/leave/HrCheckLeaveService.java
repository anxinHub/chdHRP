package com.chd.hrp.hr.service.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.leave.HrApplyingLeaves;


/**
 * 职工补休登记
 * @author Administrator
 *
 */
public interface HrCheckLeaveService {
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryCheckLeave(Map<String, Object> page)throws DataAccessException;
	/**
	 * 审核
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String auditHrApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 销审
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String unAuditHrApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 审核未通过
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String backHrApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 核定
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String checkHrApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 取消核定
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String uncheckHrHrApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 作废
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String cancelFXJApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;

}
