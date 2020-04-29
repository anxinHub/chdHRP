package com.chd.hrp.hr.dao.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface HrAccRestStatisticsKQMapper  extends SqlMapper{
   /**
    * 查询积休增加
    * @param entityMap
    * @return
    */
	//List<Map<String, Object>> overtimeQuery(Map<String, Object> entityMap);
    /**
     * 查询积休增加带分页
     * @param entityMap
     * @param rowBounds
     * @return
     */
	//List<Map<String, Object>> overtimeQuery(Map<String, Object> entityMap,RowBounds rowBounds);
     /**
      * 查收积休减少
      * @param entityMap
      * @return
      */
	//List<Map<String, Object>> applyingLeavesQuery(Map<String, Object> entityMap);
     /**
      * 查询积休减少带分页
      * @param entityMap
      * @param rowBounds
      * @return
      */
	//List<Map<String, Object>> applyingLeavesQuery(Map<String, Object> entityMap, RowBounds rowBounds);
	/**
	 * 查询考勤周期设置
	 * @param entityMap
	 * @return
	 */
	Map<String, Object> queryHrCycDateKQ(Map<String, Object> entityMap);
	/**
	 * 积休统计表里面是否有数据
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryHrAttdentAccstatKQIsUse(Map<String, Object> entityMap);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryKQByPrint(Map<String, Object> entityMap);
	/**
	 * 查询当月的人员数据
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAccYearMonthExistsKQ(Map<String, Object> entityMap);

}
