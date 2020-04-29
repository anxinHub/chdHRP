package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrSchedulingService {


    /**
     * 删除处理
     * @param listVo
     * @return
     * @throws DataAccessException
     */
	String deleteScheduling(Map<String, Object> mapVo)throws DataAccessException;


	/**
	 * 复制上周
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String copyWeek(Map<String, Object> page)throws DataAccessException;
	
	String queryPB(Map<String, Object> page)throws DataAccessException;

	/**
	 * 修改排班
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String savePB(Map<String, Object> mapVo)throws DataAccessException;



	/**
	 * 删除人员
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deleteSchedulingEmp(Map<String, Object> map)throws DataAccessException;
	   /**
	    * 审签
	    * @param saveList
	    * @return
	    */
		String auditCountersign(Map<String,Object> map) throws DataAccessException;
	String queryDeptTreeByArea(Map<String, Object> entityMap)
			throws DataAccessException;
	String queryEmpByArea(Map<String, Object> entityMap)
			throws DataAccessException;


	List<Map<String,Object>> queryPBprint(Map<String, Object> entityMap)
			throws DataAccessException;


	String queryPbByDept(Map<String, Object> entityMap)
			throws DataAccessException;


	List<Map<String, Object>> queryPbByDeptPrint(Map<String, Object> entityMap)
			throws DataAccessException;

    /**
     * 继承上周或者上月
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	String extend(Map<String, Object> mapVo)throws DataAccessException;

	/**
	 * 查询排班统计
	 */
	public String queryPBStatistics(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 排班统计打印
	 */
	public List<Map<String, Object>> queryPBStatisticsPrint(Map<String, Object> mapVo) throws DataAccessException;
}
