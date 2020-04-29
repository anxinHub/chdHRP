package com.chd.hrp.hr.dao.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.accrest.HrAccRestInit;
import com.chd.hrp.hr.entity.attendancemanagement.attend.HrAttendItem;

public interface HrAccRestInitMapper extends SqlMapper{

	int deleteAccRest(List<HrAccRestInit> alllistVo);
	public int existsTermendByYear(Map<String, Object> map) throws DataAccessException;
	/**
	 * 查询积休额度上限
	 * @param map
	 * @return
	 */
	Map<String, Object> queryAttendCodeByPK(Map<String, Object> map);
	/**
	 * 查询单个具体额度
	 * @param map
	 * @return
	 */
	int queryinit(Map<String, Object> map);
	/**
	 * 查询人员加班
	 * @param entityMap
	 * @return
	 */
	int queryOverTime(Map<String, Object> entityMap);
	public int queryInitByCode(Map<String,Object> entityMap)throws DataAccessException;
	Map<String, Object> queryEmp(Map<String, Object> map);
}
