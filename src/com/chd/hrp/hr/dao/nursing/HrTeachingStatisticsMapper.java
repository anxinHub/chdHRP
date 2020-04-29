package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrTeachingStatistics;
/**
 * 教学能力统计
 * @author Administrator
 *
 */
public interface HrTeachingStatisticsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrTeachingStatistics> queryTeachingStatisticsById(Map<String, Object> entityMap);
    /**
     * 删除教学能力统计
     * @param entityList
     */
	void deleteTeachingStatistics(List<HrTeachingStatistics> entityList);
	
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryTeachingStatisticsByPrint(Map<String, Object> entityMap);
	

}
