package com.chd.hrp.mat.dao.warning;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatOrderArriveDateWarningMapper  extends SqlMapper{
	/**
	 * 查询预警信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatOrderArriveDateWarning(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatOrderArriveDateWarning(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询订单信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatOrderInvInfo(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatOrderInvInfo(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}
