package com.chd.hrp.hpm.dao;

/** 
 * 2015-2-2  
 * author:alfred
 */

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeItem;

public interface AphiIncomeItemMapper extends SqlMapper {
	/**
	 * add
	 * 
	 * @param IncomeItem
	 */
	public void addIncomeItem(Map<String, Object> incomeItemMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchIncomeItem(List<Map<String, Object>> incomeItemMap) throws DataAccessException;

	/**
	 * select
	 * 
	 * @return list
	 */
	public List<AphiIncomeItem> queryIncomeItem(Map<String, Object> incomeItemMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * select
	 * 
	 * @return list
	 */
	public List<AphiIncomeItem> queryIncomeItem(Map<String, Object> incomeItemMap) throws DataAccessException;

	/**
	 * select by income_item_code
	 * 
	 * @return IncomeItem
	 */
	public AphiIncomeItem queryIncomeItemByCode(Map<String, Object> incomeItemMap) throws DataAccessException;

	/**
	 * delete
	 * 
	 * @param income_item_code
	 */
	public int deleteIncomeItem(Map<String, Object> incomeItemMap) throws DataAccessException;

	/**
	 * update
	 */
	public int updateIncomeItem(Map<String, Object> IncomeItemMap) throws DataAccessException;

}
