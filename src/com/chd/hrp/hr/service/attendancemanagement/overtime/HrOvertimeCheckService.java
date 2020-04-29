package com.chd.hrp.hr.service.attendancemanagement.overtime;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.overtime.HrOvertimeCheck;


/**
 * 加班审核
 * @author Administrator
 *
 */
public interface HrOvertimeCheckService {
	/**
     * 审核
     * @param saveList
     * @return
     * @throws DataAccessException
     */
	String auditOvertimeCheck(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
     * 审核
     * @param saveList
     * @return
     * @throws DataAccessException
     */
	String unAuditOvertimeCheck(Map<String, Object> entityMap)throws DataAccessException;
    
    /**
     * 销审
     * @param saveList
     * @return
     * @throws DataAccessException
     */
	String reAuditOvertimeCheck(Map<String, Object> entityMap)throws DataAccessException;
    /**
     * 查询
     * @param page
     * @return
     * @throws DataAccessException
     */
	String queryCheckLeave(Map<String, Object> page)throws DataAccessException;
	
}
