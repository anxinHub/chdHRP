package com.chd.hrp.hr.dao.attendancemanagement.attend;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;
/**
 * 考勤项目设置
 * @author Administrator
 *
 */
public interface HrAttendItemMapper  extends SqlMapper{
    /**
     * 删除考勤项目设置
     * @param entityList
     */
	void deleteAttendItem(List<HrAttendItem> entityList);
	/**
	 * 单位信息下拉框
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHosName(Map<String, Object> entityMap);
	//增加考勤项目
	int addAttendItem(Map<String, Object> entityMap);
	/**
	 * 修改考勤项目
	 * @param entityMap
	 * @return
	 */
	int updateAttendItem(Map<String, Object> entityMap);
	/**
	 * 查询重复
	 * @param entityMap
	 * @return
	 */
	List<HrAttendItem> queryByCodeItem(Map<String, Object> entityMap);
	/**
	 * 查询是否有数据是否在考勤表显示
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAttendResult(Map<String, Object> entityMap);
	/**
	 * 查询是否显示
	 * @param entityMap
	 * @return
	 */
	String queryByCodeItemFz(Map<String, Object> entityMap);
	List<Map<String, Object>> queryAttendItemByPrint(Map<String, Object> entityMap);
	/**
	 * 是否存在积休的考勤项目
	 * @param entityMap
	 * @return
	 */
	int queryAttendItemIsJx(Map<String, Object> entityMap);
	List<HrAttendItem> queryByCodeItemName(Map<String, Object> entityMap);
	int queryAttendItemIsDefault(Map<String, Object> entityMap);

}
