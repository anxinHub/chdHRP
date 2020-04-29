package com.chd.hrp.hr.dao.attendancemanagement.termend;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;


/**
 * 结账
 */
public interface HrAttendTermendMapper extends SqlMapper{
	
	//获取当前期间
	public Map<String, Object> queryYearMonth(Map<String, Object> map) throws DataAccessException;
	
	//查询历史清除余额
	public List<Map<String, Object>> queryAttendXjedDel(Map<String, Object> map, RowBounds rowBounds) throws DataAccessException;
	
	//保存清除余额
	public int addAttendXjedDel(Map<String, Object> map) throws DataAccessException;
	
	//修改清除余额
	public int updateDelAmtByAttendCode(Map<String, Object> map) throws DataAccessException;
 
	//修改结账状态
	public int updateHrPeriodFlag(Map<String, Object> map) throws DataAccessException;
	
	//批量添加明细表
	public int insertBatchDetail(@Param(value="map") Map<String, Object> map, @Param(value="list")List<Map<String, Object>> list) throws DataAccessException;
}
