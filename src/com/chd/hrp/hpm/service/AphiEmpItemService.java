/**
 * 2015-2-2 SysUserService.java author:alfred
 */
package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpItem;

public interface AphiEmpItemService {

	/**
	 * 
	 *
	 */
	public String addEmpItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * @return
	 */
	public String queryEmpItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * @return
	 */
	public AphiEmpItem queryEmpItemByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * @param id
	 */
	public String deleteEmpItem(Map<String, Object> mapVo, String item_codes) throws DataAccessException;

}
