package com.chd.hrp.acc.dao.single;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.AccWageScheme;

/**
 * 单页-个人工资条-mapper
 * @author yang
 *
 */
public interface AccGztMapper extends SqlMapper {

	/**
	 * 查询指定职工的工资条方案
	 */
	public AccWageScheme queryGztSchemeByEmpId(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询职工指定的月，有没有发放工资
	 */
	public int queryAccWagePayByMonth(Map<String, Object> paraMap) throws DataAccessException;

	/**
	 * 查询工资条明细
	 */
	public List<Map<String, Object>> queryGztDetail(Map<String, Object> paraMap) throws DataAccessException;
	
	public Map<String, Object> queryGztEmpById(Map<String, Object> paraMap) throws DataAccessException;
	
	//查询工资套
	public List<Map<String, Object>> queryGztWage(Map<String, Object> paraMap) throws DataAccessException;
	
}
