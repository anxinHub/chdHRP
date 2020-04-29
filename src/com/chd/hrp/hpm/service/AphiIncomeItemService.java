/**
 * 2015-2-2 author:alfred
 */
package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiIncomeItem;

public interface AphiIncomeItemService {

	/**
	 * add
	 * 
	 * @param Incomeitem
	 */
	public String addIncomeitem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchIncomeitem(List<Map<String, Object>> mapVo) throws DataAccessException;

	/**
	 * select
	 * 
	 * @return
	 */
	public String queryIncomeitem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * select
	 * 
	 * @return
	 */
	public AphiIncomeItem queryIncomeItemByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * delete
	 * 
	 * @param id
	 */
	public String deleteIncomeItem(Map<String, Object> mapVo, String income_item_codes) throws DataAccessException;

	/**
	 * 修改用户
	 */
	public String updateIncomeItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 导入
	 * @param mapVo
	 * @return
	 * @throws DataAccessException
	 */
	public String hpmIncomeItemImport(Map<String, Object> mapVo)throws DataAccessException;
}
