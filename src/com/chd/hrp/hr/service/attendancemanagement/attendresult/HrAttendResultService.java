package com.chd.hrp.hr.service.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.attendresult.HrAttendResult;

/**
 * 考勤数据维护
 * 
 * @author Administrator
 *
 */
public interface HrAttendResultService {

	/**
	 * 查询表头
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAttendResultHead(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAttendResult(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 查询明细
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryAttendResultDetail(Map<String, Object> page) throws DataAccessException;
	
	/**
	 * 保存
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> addAttendResult(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 保存明细
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> saveAttendResultDetail(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 导入
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> importAttendResult(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAttendResultPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 生成
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> addBatchAttendResult(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 添加考勤人员
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> addAttendResultEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	
	/**
	 * 删除
	 * @param listVo
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> deleteAttendResult(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 导入排班
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> importPb(Map<String, Object> map) throws DataAccessException;

	/**
	 * 导入加班
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> importJb(Map<String, Object> map) throws DataAccessException;

	/**
	 * 导入休假
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> importXj(Map<String, Object> map) throws DataAccessException;
    /**
     * 保存快捷设置明细数据
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	public Map<String, Object> saveAttendResultShortCut(Map<String, Object> mapVo) throws DataAccessException;

	public Map<String, Object> insertBatchDetailResult(Map<String, Object> mapVo)throws DataAccessException;

	public Map<String, Object> saveAttendResultShortPL(Map<String, Object> mapVo)throws DataAccessException;

	public String queryEmpSelectResult(Map<String, Object> mapVo)throws DataAccessException;

	public String queryAttendEmp(Map<String, Object> mapVo)throws DataAccessException;
}
