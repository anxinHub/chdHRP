package com.chd.hrp.hr.service.salarymanagement.socialSecurityManage;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-社保管理】：社保缴费基数
 * @author yang
 *
 */
public interface HrInsurBaseService {

	/**
	 * 主查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryHrInsurBase(Map<String, Object> page) throws DataAccessException;

}
