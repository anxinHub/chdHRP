/**
 * 2014-12-31 LoginService.java author:pengjin
 */
package com.chd.hrp.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

public interface LoginService {

	public String login(Map<String, Object> mapVo) throws DataAccessException;

	public String loadSystemModTree(Map<String, Object> mapVo) throws DataAccessException;

	public String userUnLock(Map<String, Object> mapVo) throws DataAccessException;

	String singleLogin(Map<String, Object> mapVo) throws DataAccessException;

	Map<String, Object> dhcHrpLogin(Map<String, Object> mapVo)
			throws DataAccessException;

}
