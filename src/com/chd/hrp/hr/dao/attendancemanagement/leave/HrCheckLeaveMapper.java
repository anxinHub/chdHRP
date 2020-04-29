package com.chd.hrp.hr.dao.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrApplyingLeaves;

public interface HrCheckLeaveMapper  extends SqlMapper{
	/**
	 * 审核销审
	 * @param entityList
	 * @return
	 */
	int auditHrCheckLeaves(List<Map> entityList);
	/**
	 * 审核查询
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryCompensatoryLeave(Map<String, Object> entityMap);
	List<Map<String, Object>> queryCompensatoryLeave(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 核定、撤销核定
	 * @param entityList
	 * @return
	 */
	int checkHrApplyingLeaves(List<Map> entityList);
	void deleteBatchCheck(List<Map> entityList);
	/**
	 * 添加销假记录
	 * @param saveList1
	 */
	void addBatchTerminate(List<Map> saveList);
	//插入记录
	int addBatchCheck(List<Map> entityList);
	//更新记录
	void updateBatchCheck(List<Map> saveList);
	//核定表增加销假记录
	void addBatchCheckTer(List<Map> saveList);
	//从审核表删除数据
	void deleteCheck(List<Map> entityList);

}
