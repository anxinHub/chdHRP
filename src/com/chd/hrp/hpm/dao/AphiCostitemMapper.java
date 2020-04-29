package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiCostitem;

/**
 * 
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiCostitemMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addCostitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchCostitem(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitem> queryCostitem(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitem> queryCostitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitem queryCostitemByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteCostitem(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateCostitem(Map<String, Object> entityMap) throws DataAccessException;

}
