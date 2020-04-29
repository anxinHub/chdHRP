package com.chd.hrp.hr.service.record;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrStatisticConditionService {
	/**
	 * 查询简单统计表设置条件
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrStatisticSetCondition(Map<String, Object> entityMap) throws DataAccessException;
}
