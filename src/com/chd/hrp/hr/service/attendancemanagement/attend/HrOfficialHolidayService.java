package com.chd.hrp.hr.service.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.attend.HrOfficialHoliday;



/**
 * 法定节假日设置
 * @author Administrator
 *
 */
public interface HrOfficialHolidayService {
	/**
	 * 增加法定节假日设置设置
	 * @param mapVo
	 * @return
	 */

	String addOfficialHoliday(Map<String, Object> mapVo)throws DataAccessException;
    /**
     * 修改页面跳转查询法定节假日设置
     * @param mapVo
     * @return
     */
	HrOfficialHoliday queryByCodeOfficialHoliday(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 修改法定节假日设置
	 * @param mapVo
	 * @return
	 */
	String updateOfficialHoliday(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除法定节假日设置
	 * @param listVo
	 */
	String deleteOfficialHoliday(List<HrOfficialHoliday> listVo)throws DataAccessException;
	/**
	 * 查询法定节假日设置
	 * @param page
	 * @return
	 */
	String queryOfficialHoliday(Map<String, Object> page)throws DataAccessException;
	

}
