package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClass;

public interface HrAttendClassService {
    /**
     * 添加班次设置
     * @param mapVo
     * @return
     */
	String addAttendClass(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除班次设置
    * @param listVo
    * @return
    */
	String deleteAttendClass(List<HrAttendClass> listVo)throws DataAccessException;
   /**
    * 查询班次设置
    * @param page
    * @return
    */
	String queryAttendClass(Map<String, Object> page)throws DataAccessException;
	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
    String queryAttendAreacode(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改调转
     * @param mapVo
     * @return
     * @throws DataAccessException
     */
	HrAttendClass queryByCode(Map<String, Object> mapVo)throws DataAccessException;
	
	/**
	/**
	 * 修改班次
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String updateAttendClass(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询是否被引用
	 * @param hrAttendClass
	 * @return
	 */
	int queryAttendScheduling(HrAttendClass hrAttendClass)throws DataAccessException;
	 /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryAttendClassByPrint(Map<String, Object> page)throws DataAccessException;

}
