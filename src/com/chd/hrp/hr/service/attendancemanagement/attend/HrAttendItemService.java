package com.chd.hrp.hr.service.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;

/**
 * 考勤项目设置
 * 
 * @author Administrator
 *
 */
public interface HrAttendItemService {
	/**
	 * 增加考勤项目设置
	 * 
	 * @param mapVo
	 * @return
	 */

	String addAttendItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 删除考勤项目
	 * 
	 * @param listVo
	 */
	String deleteAttendItem(List<HrAttendItem> listVo) throws DataAccessException;

	/**
	 * 查询考勤项目
	 * 
	 * @param page
	 * @return
	 */
	String queryAttendItem(Map<String, Object> page) throws DataAccessException;

	/**
	 * 单位信息下拉框
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHosName(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改跳转
	 * 
	 * @param mapVo
	 * @return
	 */
	HrAttendItem queryByCodeAttendItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询动态数据
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrFiiedData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改数据
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String updateAttendItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 打印
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryAttendItemByPrint(Map<String, Object> page) throws DataAccessException;
	/**
	 * 删除
	 * @param mapVo
	 * @return
	 */
	String deleteAttendItem(Map<String, Object> mapVo);
}
