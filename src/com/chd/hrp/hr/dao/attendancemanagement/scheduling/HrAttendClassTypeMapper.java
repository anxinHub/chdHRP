package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendClassType;

public interface HrAttendClassTypeMapper extends SqlMapper{
  
	/**
	 * 删除
	 * @param listVo
	 * @param mapVo 
	 */
	void deleteAttendClassType(@Param(value="list")List<HrAttendClassType> listVo,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 查询是否被引用
	 * @param listVo
	 * @param mapVo 
	 * @return
	 */
	int queryArea(@Param(value="list")List<HrAttendClassType> listVo,@Param(value="map") Map<String, Object> mapVo);
	/**
	 * 查询是否存在
	 * @param entityMap
	 * @return
	 */
	List<HrAttendClassType> queryByCodeClassType(Map<String, Object> entityMap);
	
	/**
	 * 查询是否被班次设置占用
	 * @param listVo
	 * @param mapVo
	 * @return
	 */
	int queryAttendClass(@Param(value="list")List<HrAttendClassType> listVo,@Param(value="map") Map<String, Object> mapVo);
	List<HrAttendClassType> queryByCodeClassTypeUpdate(Map<String, Object> entityMap);
	List<Map<String, Object>> queryAttendClassTypeByPrint(Map<String, Object> entityMap);
}
