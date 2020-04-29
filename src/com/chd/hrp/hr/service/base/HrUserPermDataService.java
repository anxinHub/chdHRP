package com.chd.hrp.hr.service.base;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;
import com.chd.hrp.hr.entity.base.HrUserPermData;

public interface HrUserPermDataService {
	/**
	 * 增加角色权限
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String addRolePermData(List<HrUserPermData> listVo) throws DataAccessException;

	/**
	 * 删除角色权限
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deleteRolePermData(List<HrUserPermData> listVo) throws DataAccessException;

	/**
	 * 增加用户权限
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String addUserPermData(List<HrUserPermData> listVo) throws DataAccessException;

	/**
	 * 删除用户权限
	 * 
	 * @param listVo
	 * @return
	 * @throws DataAccessException
	 */
	String deleteUserPermData(List<HrUserPermData> listVo) throws DataAccessException;

	/**
	 * 查询权限
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryPermData(Map<String, Object> page) throws DataAccessException;

	/**
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String queryColumnIdByTableCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 批量添加用户权限
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String addBatchUserPermData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 批量增加角色权限
	 * 
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	String addBatchRolePermData(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询用户
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryUser(Map<String, Object> page) throws DataAccessException;

	/**
	 * 查询角色
	 * 
	 * @param page
	 * @return
	 * @throws DataAccessException
	 */
	String queryRole(Map<String, Object> page) throws DataAccessException;

}
