package com.chd.hrp.hr.service.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItemFz;

public interface HrAttendItemFzService {
    /**
     * 添加考勤项目分组
     * @param mapVo
     * @return
     */
	String addAttendItemFz(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 
     * @param mapVo
     * @return
     */
	HrAttendItemFz queryByCodeAttendItemFz(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 *修改考勤项目分组
	 * @param mapVo
	 * @return
	 */
	String updateAttendItemFz(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除
	 * @param listVo
	 * @return
	 */
	String deleteAttendItemFz(List<HrAttendItemFz> listVo)throws DataAccessException;
	/**
	 * 查询考勤项目分组
	 * @param page
	 * @return
	 */
	String queryAttendItemFz(Map<String, Object> page)throws DataAccessException;

}
