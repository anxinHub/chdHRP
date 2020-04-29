package com.chd.hrp.hr.service.salarymanagement.accumulationfund;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 【薪资管理-公积金等】：公积金缴费基数
 * @author yang
 *
 */
public interface HrFundBaseService {

	/**
	 * 查询
	 * @param paramMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryHrFundBase(Map<String, Object> paramMap) throws DataAccessException;

}
