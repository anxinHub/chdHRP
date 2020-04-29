package com.chd.hrp.hr.service.record;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrStatisticTabStrucService {
	/**
	 * 查询简单统计表设置表列
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrStatisticSetTab(Map<String, Object> entityMap) throws DataAccessException;
}
