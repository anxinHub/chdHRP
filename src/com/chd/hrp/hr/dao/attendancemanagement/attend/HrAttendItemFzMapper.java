package com.chd.hrp.hr.dao.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItemFz;

public interface HrAttendItemFzMapper extends SqlMapper{
	/**
	 * 查询是否重复
	 * 
	 * @param entityMap
	 * @return
	 */
	List<HrAttendItemFz> queryByCodeItem(Map<String, Object> entityMap);

	/**
	 * 添加
	 * 
	 * @param entityMap
	 * @return
	 */
	int addAttendItemFz(Map<String, Object> entityMap);

	/**
	 * 
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendResult(Map<String, Object> entityMap);

	/**
	 * 更新
	 * 
	 * @param entityMap
	 */
	void updateAttendItemFz(Map<String, Object> entityMap);

	/**
	 * 删除考勤项目分组
	 * 
	 * @param entityList
	 */
	void deleteAttendItemFz(List<HrAttendItemFz> entityList);
    

}
