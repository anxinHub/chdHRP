package com.chd.hrp.hr.dao.attendancemanagement.leave;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface HrAttdentVacalBalanceMapper extends SqlMapper{
	//增加
	void addBatch(Map<String, Object> entityMap) throws DataAccessException;
	//查询
	List<Map<String,Object>> queryHrAttdentVacalBal(Map<String, Object> entityMap) throws DataAccessException;
	//根据休假项目批量修改
	public int updateLimitByItem(Map<String, Object> entityMap) throws DataAccessException;
	//修改
	void updateBatch(@Param(value="list") List<Map<String, Object>> entityList,@Param(value="map") Map<String,Object> map) throws DataAccessException;
	//批量插入
	void addBatchBalance(@Param(value="list") List<Map<String, Object>> entityList,@Param(value="map") Map<String,Object> map) throws DataAccessException;
}
