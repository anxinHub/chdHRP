package com.chd.hrp.acc.service.single;

import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * 单独页-个人工资条查询-service
 * @author yang
 *
 */
public interface AccGztService {

	/**
	 * 查询工资条信息
	 */
	public String getGztEmpBaseInfo(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询指定年份工资条的工资项
	 */
	public String queryGztWageItem(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 指定的月是否有工资数据
	 */
	public boolean queryGzIsGrantByMonth(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询工资条明细
	 */
	public String queryGztDetail(Map<String, Object> paraMap) throws DataAccessException;
	
}
