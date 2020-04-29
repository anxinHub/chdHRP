package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrAttendArea;

public interface HrAttendAreaMapper extends SqlMapper{
    /**
     * 删除排班区域明细
     * @param alllistVo
     * @param entityMap 
     */
	void deleteAttendArea(List<HrAttendArea> alllistVo);

	void deleteBatchArea(List<HrAttendArea> listVo);
    //添加科室
	void addBatchDateil(@Param(value="list")List<HrAttendArea> listVo, @Param(value="map")Map<String, Object> entityMap);
   /**
    * 查询科室
    * @param entityMap
    * @return
    */
	List<Map<String, Object>> queryAreaDept(Map<String, Object> entityMap);

void deleteBatchAttendArea(@Param(value="list")List<HrAttendArea> alllistVo, @Param(value="map")Map<String, Object> entityMap);
/**
 * 查询是否被引用
 * @param hrAttendArea
 * @return
 */
int queryAreacode(HrAttendArea hrAttendArea);
/**
 * 查询重复
 * @param entityMap
 * @return
 */
List<HrAttendArea> queryByCodeArea(Map<String, Object> entityMap);

List<String> queryAttendArea(@Param(value="list")List<HrAttendArea> listVo, @Param(value="map")Map<String, Object> entityMap);

List<Map<String, Object>> queryAreaDeptCheck(Map<String, Object> entityMap);

List<HrAttendArea> queryByCodeAreaByName(Map<String, Object> entityMap);

List<Map<String, Object>> queryAttendAreaByPrint(Map<String, Object> entityMap);

}
