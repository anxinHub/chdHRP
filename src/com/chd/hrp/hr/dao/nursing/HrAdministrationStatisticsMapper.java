package com.chd.hrp.hr.dao.nursing;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursing.HrAdministrationStatistics;
/**
 * 行政能力统计
 * @author Administrator
 *
 */
public interface HrAdministrationStatisticsMapper  extends SqlMapper{
    /**
     * 添加查询重复
     * @param entityMap
     * @return
     */
	List<HrAdministrationStatistics> queryAdministrationStatisticsById(Map<String, Object> entityMap);
    /**
     * 删除行政能力统计
     * @param entityList
     */
	void deleteAdministrationStatistics(List<HrAdministrationStatistics> entityList);
	/**
	 * 打印
	 * @param entityMap
	 * @return
	 */
	List<Map<String, Object>> queryAdministrationStatisticsByPrint(Map<String, Object> entityMap);
	

}
