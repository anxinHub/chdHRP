package com.chd.hrp.hr.dao.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.attendresult.HrAttendResult;
import com.chd.hrp.hr.entity.medicalmanagement.HrDrugPerm;


/**
 * 考勤数据维护
 * @table HR_ATTDENT_RESULT_DATA
 *
 */
public interface HrAttendResultMapper extends SqlMapper{
	
	//初始化人员
	public int addEmpDateByDept(Map<String, Object> map) throws DataAccessException;
	
	//添加考勤人员
	public int existsByAddEmp(Map<String, Object> map) throws DataAccessException;
	
	//添加考勤人员
	public int addEmp(Map<String, Object> map) throws DataAccessException;
	
	//添加考勤人员
	public int addEmpByImp(@Param(value="map")  Map<String, Object> map, @Param(value="list") List<Map<String, Object>> listVo) throws DataAccessException;
	
	//主查询
	public List<Map<String, Object>> queryAttendResult(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	//明细查询
	public List<Map<String, Object>> queryAttendResultDetail(Map<String, Object> map) throws DataAccessException;
	
	//批量删除
	public int deleteAttendResult(@Param(value="map")  Map<String, Object> map, @Param(value="list") List<Map<String, Object>> listVo) throws DataAccessException;
	
	//批量判断是否存在不为该状态的数据
	public int existsByState(@Param(value="map")  Map<String, Object> map, @Param(value="list") List<Map<String, Object>> listVo) throws DataAccessException;

	//查询考勤周期
	public Map<String, Object> queryAttendCycle(Map<String, Object> map) throws DataAccessException;
	
	//按期间查询考勤周期
	public Map<String, Object> queryHrPeriod(Map<String, Object> map) throws DataAccessException;

	//更新主表
	public int updateMainByCode(Map<String, Object> map) throws DataAccessException;
	
	//批量更新主表
	public int updateBatchMain(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;

	//删除明细表
	public int deleteDetailByCode(Map<String, Object> map) throws DataAccessException;
	
	//批量删除明细表
	public int deleteBatchDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	
	//批量添加明细表
	public int insertBatchDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
	/**
	 * 打印
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAttendResultPrint(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 查询科室月份信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptInfoByAccMonth(Map<String, Object> map) throws DataAccessException;

	/**
	 * 是否存在科室员工信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAttendResultIsExists(Map<String, Object> map)  throws DataAccessException;
	
	/**
	 * 查询部门信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryDeptInfo(Map<String, Object> map) throws DataAccessException;
	/**
	 * 查询职工信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryEmpInfo(Map<String, Object> map) throws DataAccessException;
	/**
	 * 查询月份信息
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMonthInfo(Map<String, Object> map) throws DataAccessException;
	
	/**
	 * 考勤项目
	 * @param map
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryAttendItemInfo(Map<String, Object> map) throws DataAccessException;

	//获取排班数据
	public List<Map<String, Object>> queryPbData(Map<String, Object> map) throws DataAccessException;
	
	//获取加班数据
	public List<Map<String, Object>> queryJbData(Map<String, Object> map) throws DataAccessException;

	//获取休假数据
	public List<Map<String, Object>> queryXjData(Map<String, Object> map) throws DataAccessException;
    //删除快捷设置数据
	public void deleteDetailByCodeShortCut(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list);

	public List<Map<String, Object>> queryEmpInfoByDept(Map<String, Object> entityMap);

	public void deleteBatchDetailResult(@Param(value="map") Map<String, Object> map,@Param(value="list") List<Map<String, Object>> detailList);

	public void insertBatchDetailResult(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list);

	public void updateMainByCodePL(Map<String, Object> map);

	public void deleteDetailByCodeShortCutPL(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryHrItem(Map<String, Object> entityMap);

	public int queryState(Map<String, Object> entityMap);

	public int queryStateDeptId(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryEmpSelectResult(
			Map<String, Object> entityMap);

	public List<Map<String, Object>> queryAttendEmp(
			Map<String, Object> entityMap);

	public void deleteBatchResult(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list);

	public List<Map<String, Object>> queryDetail(Map<String, Object> entityMap)throws DataAccessException;

	public void updateManage(Map<String, Object> entityMap)throws DataAccessException;

	public List<Map<String, Object>> queryHrNotWork(Map<String, Object> entityMap)throws DataAccessException;
}
