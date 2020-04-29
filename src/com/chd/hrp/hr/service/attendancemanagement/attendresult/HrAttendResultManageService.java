package com.chd.hrp.hr.service.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 考勤数据上报
 * 
 * @author Administrator
 *
 */
public interface HrAttendResultManageService { 
	
	//查询表头
	public Map<String, Object> queryResultManageHead(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询
	public String queryResultManage(Map<String, Object> entityMap) throws DataAccessException;
	
	//休假查询
	public String queryResultManageXj(Map<String, Object> entityMap) throws DataAccessException;
	
	//加班查询
	public String queryResultManageJb(Map<String, Object> entityMap) throws DataAccessException;
	
	//计算
	public Map<String, Object> addBatchAttendResult(Map<String, Object> entityMap)throws DataAccessException;
	
	//删除
	public Map<String, Object> deleteResultManage(Map<String, Object> entityMap)throws DataAccessException;
	
	//提交、取消提交
	public Map<String, Object> submitOrUnSubmitResultManage(Map<String, Object> entityMap)throws DataAccessException;
	
	//审核、消审
	public Map<String, Object> checkOrUnCheckResultManage(Map<String, Object> entityMap)throws DataAccessException;
	
	//导入
	public Map<String, Object> importResultManage(Map<String, Object> entityMap)throws DataAccessException;
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAttendResultManagePrint(Map<String, Object> entityMap) throws DataAccessException;
}
