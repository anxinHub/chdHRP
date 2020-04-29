package com.chd.hrp.hpm.dao;

/** 
 * 2015-2-2 
 * SysUserService.java 
 * author:alfred
 */
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiItem;

public interface AphiItemMapper extends SqlMapper {
	/**
	 * 添加
	 * 
	 * @param user
	 */
	public int addItem(Map<String, Object> itemMap) throws DataAccessException;
	
	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<AphiItem> queryItem(Map<String, Object> ItemMap) throws DataAccessException;

	/**
	 * 查询
	 * 
	 * @return
	 */
	public List<AphiItem> queryItem(Map<String, Object> ItemMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * select by item_code
	 * 
	 * @return Item
	 */
	public AphiItem queryItemByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * update
	 */
	public int updateItem(Map<String, Object> ItemMap) throws DataAccessException;

	/**
	 * delete
	 */
	public int deleteItem(Map<String, Object> itemMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItem queryItemByItemCode(String item_code) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItem> qeuryItemMap(Map<String, Object> map) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItem> qeuryItemData(Map<String, Object> map) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItem> qeuryItemMapGrid(Map<String, Object> map) throws DataAccessException;

	public List<AphiItem> qeuryItemData_wage(Map<String, Object> entityMap) throws DataAccessException;

}
