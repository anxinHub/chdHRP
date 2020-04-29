package com.chd.hrp.hr.service.attendancemanagement.termend;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 结账
 * 
 * @author Administrator
 *
 */
public interface HrAttendTermendService {
	
	/**
	 * 获取当前期间
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAttendTermendYearMonth(Map<String, Object> entityMap)throws DataAccessException;

	/**
	 * 查询清除余额
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAttendXjedDel(Map<String, Object> entityMap) throws DataAccessException; 
	
	/**
	 * 保存清除余额
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> addAttendXjedDel(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> confirmAttendTermend(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 反结账
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> unConfirmAttendTermend(Map<String, Object> entityMap) throws DataAccessException;
}
