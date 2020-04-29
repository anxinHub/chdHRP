package com.chd.hrp.hr.service.salarymanagement.socialSecurityManage;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-社保管理】：应缴社保查询
 * @author yang
 *
 */
public interface HrBehoovePayInsurService {
	
	/**
	 * 查询表头
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public String queryBehoovePayInsurHead(Map<String, Object> paramMap) throws DataAccessException;

	/**
	 * 主查询
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryBehoovePayInsur(Map<String, Object> page) throws DataAccessException;

}
