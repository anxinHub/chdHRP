package com.chd.hrp.hr.dao.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.leave.HrApplyingLeaves;

/**
 * 职工休假申请
 * @author Administrator
 *
 */
public interface HrApplyingLeavesMapper extends SqlMapper{
    /**
     * 添加查询数据是否重复
     * @param entityMap
     * @return
     */
	List<HrApplyingLeaves> queryApplyingLeavesById(Map<String, Object> entityMap);
	
	/**
	 * 提交/取消提交职工休假申请
	 * @param entityList
	 * @return
	 */
	int confirmApplyingLeaves(List<HrApplyingLeaves> entityList);
	
	/**
	 * 查询休假额度
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryAttendSum(Map<String, Object> entityMap);
	/**
	 * 查询休假历史记录
	 * @param entityMap
	 * @return
	 */
	List<HrApplyingLeaves> queryHistroy(Map<String, Object> entityMap);
	/**
	 * 查询休假历史记录带分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 */
	List<HrApplyingLeaves> queryHistroy(Map<String, Object> entityMap,RowBounds rowBounds);
	void auditHrApplyingLeaves(List<Map> entityList);
	
	/**
	 * 查询是否存在重复休假
	 * @param entityMap
	 * @return
	 */
	HrApplyingLeaves queryByCodeLeaves(Map<String, Object> entityMap);
	
	/**
	 * 修改状态
	 * @param updatList
	 * @param entityMap
	 */
	//void updateApplyState(@Param(value="list") List<Map<String, Object>> updatList, @Param(value="map") Map<String, Object> entityMap) throws DataAccessException;
	void updateApplyState(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 休假管理表增加休假申请数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	void insertApplyHrEmpHoliday(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 休假管理表删除休假申请数据
	 * @param entityMap
	 * @throws DataAccessException
	 */
	void deleteApplyHrEmpHoliday(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 删除
	 * @param entityMap
	 * @throws DataAccessException
	 */
	void deleteApplyingLeaves(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 打印
	 */
	public  List<Map<String, Object>> queryApplyingLeavesByPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 核定组装结存表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryXjInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 作废组装结存表数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryCancleXjInfo(Map<String, Object> entityMap) throws DataAccessException;
	List<Map<String, Object>> queryCancleXXjInfo(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 根据主键和状态查询休假申请表信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryXjApplication(Map<String, Object> entityMap) throws DataAccessException;
    /**
     * 查询是积休的休假
     * @param entityMap
     * @return
     * @throws DataAccessException
     */
	Map<String, Object> queryAttendSumByJX(Map<String, Object> entityMap)throws DataAccessException;

	

}
