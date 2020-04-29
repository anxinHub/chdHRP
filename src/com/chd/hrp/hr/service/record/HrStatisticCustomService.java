package com.chd.hrp.hr.service.record;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface HrStatisticCustomService {
	/**
	 * 简单统计表设置菜单查询
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String queryHrStatisticCustomSetMenu(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 简单统计表设置查询
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	Map<String, Object> queryHrStatisticCustomSetByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除简单统计表设置
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String deleteHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 保存简单统计设置
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	String saveHrStatisticCustomSet(Map<String, Object> entityMap) throws DataAccessException;
}
