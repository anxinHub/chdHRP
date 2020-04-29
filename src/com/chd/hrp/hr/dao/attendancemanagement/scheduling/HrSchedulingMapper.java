package com.chd.hrp.hr.dao.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.attendancemanagement.scheduling.HrSchedulingEmp;

public interface HrSchedulingMapper extends SqlMapper{

	void deletePB(Map<String,Object> map);
	
	void insertPBCheck(Map<String, Object> map);

	void insertPB(@Param("map") Map<String, Object> map,@Param("list") List<Map<String, Object>> list);
	

   
    /**
     * 查询封存设置
     * @param entityMap
     * @return
     */
	Map<String, Object> queryset(Map<String, Object> entityMap);

    //查询排班详细 带分页
	List<HrSchedulingEmp> queryPB(Map<String, Object> entityMap,RowBounds rowBounds);
   //查询排班不带分页
	List<Map<String, Object>> queryPB(Map<String, Object> entityMap);
	
	//查询排班审核数据
	Map<String, Object> queryPBCheck(Map<String, Object> entityMap);
	


	List<String> queryPbExisits(Map<String, Object> entityMap);
    
	int queryPbIsCheck(Map<String,Object> map);
	/**
     * 删除人员
     * @param listVo
     */
	void deleteSchedulingEmp(Map<String,Object> map);
   
	
    /**
     * 审签
     * @param saveList
     */
	void auditCountersign(Map<String,Object> map);
    /**
     * 查询上月或者上周排班
     * @param entityMap
     * @return
     */
	List<Map<String, Object>> queryPBCopy(Map<String, Object> entityMap);
	
	List<Map<String, Object>> queryDeptTreeByArea(Map<String, Object> entityMap);

	List<Map<String, Object>> queryEmpByArea(Map<String, Object> entityMap, RowBounds rowBounds);
	
	List<Map<String, Object>> queryPbByDept(Map<String, Object> entityMap);
    /**
     * 包含上周或者上月数据
     * @param entityMap
     * @param pb_dateList 
     * @param sel_dateList 
     */
	void extendBh(@Param("map") Map<String, Object> entityMap,@Param("list") List<Map<String, Object>> pb_dateList);
    /**
     * 不包含上周或者上月数据
     * @param entityMap
     * @param pb_dateList 
     */
	void extendNotBh(@Param("map")Map<String, Object> entityMap,@Param("list") List<Map<String, Object>> pb_dateList);
    /**
     * 查询是否有排班
     * @param entityMap
     * @return
     */
	List<String> queryCount(Map<String, Object> entityMap);
    /**
     * 查询排班审核表是否有数据
     * @param entityMap
     * @return
     */
	List<String> queryinsertPBCheck(Map<String, Object> entityMap);

	/**
	 * 查询职工的排班
	 * @deprecated 暂时废弃
	 * @author yang
	 */
	List<Map<String, Object>> queryPBStatisticsEmpPB(Map<String, Object> mapVo);
	
	/**
	 * 查询排班统计
	 */
	List<Map<String, Object>> queryPBStatistics(Map<String, Object> mapVo);

	/**
	 * 查询排班统计（分页）
	 */
	List<Map<String, Object>> queryPBStatistics(Map<String, Object> mapVo, RowBounds rowBounds);
}
