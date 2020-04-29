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
import com.chd.hrp.hpm.entity.AphiEmpItem;
import com.chd.hrp.hpm.entity.AphiItem;

public interface AphiEmpItemMapper extends SqlMapper {

	/**
	 * 
	 * @param user
	 */
	public int addEmpItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * @return
	 */
	public List<AphiEmpItem> queryEmpItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * @return
	 */
	public List<AphiEmpItem> queryEmpItem(Map<String, Object> mapVo, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 * @return Item
	 */
	public AphiEmpItem queryEmpItemByCode(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * 
	 * update
	 */
	public int updateEmpItem(Map<String, Object> mapVo) throws DataAccessException;

	/**
	 * delete
	 * 
	 * @param income_item_code
	 */
	public int deleteEmpItem(Map<String, Object> mapVo) throws DataAccessException;

}
