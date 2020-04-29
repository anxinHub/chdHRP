package com.chd.hrp.mat.service.warning;

import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface MatOrderArriveDateWarningService {
	/**
	 * 查询材料效期预警
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderArriveDateWarning(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询订单信息
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatOrderInvInfo(Map<String, Object> entityMap) throws DataAccessException;

}
