package com.chd.hrp.hr.service.nursingtraining;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hr.entity.nursingtraining.HrPerformanceIntegral;

/**
 * 员工年终绩效积分考核
 * @author Administrator
 *
 */
public interface HrPerformanceIntegralService {
    /**
     * 增加员工年终绩效积分考核
     * @param mapVo
     * @return
     */
	String addPerformanceIntegral(Map<String, Object> mapVo)throws DataAccessException;
	/**
	 * 删除员工年终绩效积分考核
	 * @param listVo
	 */
	String  deletePerformanceIntegral(List<HrPerformanceIntegral> listVo)throws DataAccessException;
	/**
	 * 查询员工年终绩效积分考核
	 * @param page
	 * @return
	 */
	String queryPerformanceIntegral(Map<String, Object> page)throws DataAccessException;

}
