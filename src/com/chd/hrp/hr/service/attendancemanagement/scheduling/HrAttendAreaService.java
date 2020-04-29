package com.chd.hrp.hr.service.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;

public interface HrAttendAreaService {
   /**
    * 添加排班区域
    * @param mapVo
    * @return
    * @throws DataAccessException
    */
	String addAttendArea(Map<String, Object> mapVo)throws DataAccessException;
   /**
    * 删除排班区域
    * @param listVo
    * @return
    * @throws DataAccessException
    */
	String deleteAttendArea(List<HrAttendArea> listVo)throws DataAccessException;
   /**
    * 查询排班区域
    * @param page
    * @return
    * @throws DataAccessException
    */
	String queryAttendArea(Map<String, Object> page)throws DataAccessException;
	/**
	 * 查询明细科室
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
    String queryAreaDept(Map<String, Object> mapVo)throws DataAccessException;
	HrAttendArea queryByCode(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改数据
	 * @param mapVo
	 * @return
	 */
	String updateAttendArea(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 查询是否被引用
	 * @param hrAttendArea
	 * @return
	 * @throws DataAccessException
	 */
	int queryAreacode(HrAttendArea hrAttendArea)throws DataAccessException;
	String queryAreaDeptCheck(Map<String, Object> mapVo)throws DataAccessException;
	 /**
		 * 打印
		 * 
		 * @param page
		 * @return
		 * @throws DataAccessException
		 */
	List<Map<String,Object>> queryAttendAreaByPrint(Map<String, Object> page)throws DataAccessException;
}
