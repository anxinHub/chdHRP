package com.chd.hrp.acc.service.payable.otherpay;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.acc.entity.AccEmpAccount;

public interface AccElsePayService extends SqlService {
	
	/**
	 * 模板打印
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public Map<String, Object> queryAccElsePayPrint(Map<String, Object> mapVo) throws DataAccessException;
	
	/**
	 * 根据 职工 查询职工账号
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public AccEmpAccount queryAccEmpAccountByEmp(Map<String, Object> mapVo) throws DataAccessException;
	
}
