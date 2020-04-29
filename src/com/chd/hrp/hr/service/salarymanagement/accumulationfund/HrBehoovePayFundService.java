package com.chd.hrp.hr.service.salarymanagement.accumulationfund;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-公积金等】：应缴公积金查询
 * @author yang
 *
 */
public interface HrBehoovePayFundService {

	/**
	 * 查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrFund(Map<String, Object> paramMap) throws DataAccessException;

}
