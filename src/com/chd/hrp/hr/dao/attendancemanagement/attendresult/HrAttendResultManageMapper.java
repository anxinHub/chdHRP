package com.chd.hrp.hr.dao.attendancemanagement.attendresult;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


/**
 * 考勤数据维护
 * @table HR_ATTDENT_RESULT_MANAGE
 *
 */
public interface HrAttendResultManageMapper extends SqlMapper{

	//考勤项目
	public List<Map<String, Object>> queryAttendItemCol(Map<String, Object> entityMap) throws DataAccessException;
	
	//查询
	public List<Map<String,Object>> queryResultManage(Map<String,Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	
	//休假查询
	public List<Map<String,Object>> queryResultManageXj(Map<String,Object> entityMap)throws DataAccessException;

	//加班查询
	public List<Map<String,Object>> queryResultManageJb(Map<String,Object> entityMap)throws DataAccessException;
	
	//获取考勤结果
	public List<Map<String, Object>> queryResultData(Map<String, Object> entityMap) throws DataAccessException;
	
	//批量添加
	public int addBatchResultManage(@Param(value="map") Map<String,Object> entityMap, @Param(value="list") List<Map<String, Object>> entityList)throws DataAccessException;

	//批量判断是否存在不为该状态的数据
	public int existsByState(@Param(value="map")  Map<String, Object> entityMap, @Param(value="list") List<Map<String, Object>> listVo) throws DataAccessException;
	
	//批量删除
	public int deleteResultManage(@Param(value="map") Map<String,Object> entityMap, @Param(value="list") List<Map<String, Object>> entityList)throws DataAccessException;
	
	//提交、取消提交
	public int updateStateBySubmit(@Param(value="map") Map<String,Object> entityMap, @Param(value="list") List<Map<String, Object>> entityList)throws DataAccessException;
	
	//审核、消审
	public int updateStateByCheck(@Param(value="map") Map<String,Object> entityMap, @Param(value="list") List<Map<String, Object>> entityList)throws DataAccessException;

	public List<Map<String, Object>> queryAttendResultManagePrint(Map<String, Object> entityMap);

	public Map<String, Object> queryEmp(Map<String, Object> dataMap)throws DataAccessException;

	public int queryCount(Map<String, Object> entityMap)throws DataAccessException;

	public void updateManage(Map<String, Object> entityMap)throws DataAccessException;
}
