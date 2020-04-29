package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicStatistics;
/**
 * 年度学术能力统计
 * @author Administrator
 *
 */
public interface HrAcademicStatisticsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAcademicStatistics> queryAcademicStatisticsById(Map<String, Object> entityMap);
    /**
     * 删除年度学术能力统计
     * @param entityList
     */
	void deleteAcademicStatistics(List<HrAcademicStatistics> entityList);
	
	List<Map<String,Object>> queryByPrint(Map<String, Object> entityMap);
}
