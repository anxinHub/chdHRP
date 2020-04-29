/**
 * 2015-2-2 SysUserService.java author:alfred
 */
package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiItem;

public interface AphiItemService {

	/**
	 * 添加用户
	 * 
	 * @param Incomeitem
	 */
	public String addItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * @return
	 */
	public String queryItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * select
	 * 
	 * @return
	 */
	public AphiItem queryItemByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 修改用户
	 */
	public String updateItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * delete
	 * 
	 * @param id
	 */
	public String deleteItem(Map<String, Object> mapVo, String item_codes) throws DataAccessException;

}
