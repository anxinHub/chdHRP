package com.chd.hrp.hr.dao.nursingtraining;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrPerformanceIntegral;
/**
 * 员工年终绩效积分考核
 * @author Administrator
 *
 */
public interface HrPerformanceIntegralMapper  extends SqlMapper{
    /**
     * 删除员工年终绩效积分考核
     * @param entityList
     */
	void deletePerformanceIntegral(List<HrPerformanceIntegral> entityList);
	/**
	 * 删除所有的数据
	 * @param alllistVo
	 */
	void deleteAll(List<HrPerformanceIntegral> alllistVo);
	

}
