/**
 * @Copyright: Copyright (c) 2015-2-14 
 * @Company: 智慧云康（北京）数据科技有限公司
 */
package com.chd.hrp.acc.dao.autovouch;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.acc.entity.autovouch.AccPatientType;

public interface AccPatientTypeMapper extends SqlMapper {

	/**
	 * 添加患者类别 
	 * 
	 * */
	public int addAccPatientType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 删除患者类别 
	 * 
	 * */
	public int deleteAccPatientType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 修改患者类别 
	 * 
	 * */
	public int updateAccPatientType(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 *根据编码查询患者类别
	 * 
	 * */
	public AccPatientType queryAccPatientTypeByCode(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 插叙患者类别不分页
	 * 
	 * */
	public List<AccPatientType> queryAccPatientType(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 查询患者类别分页
	 * 
	 * */
	public List<AccPatientType> queryAccPatientType(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

}
