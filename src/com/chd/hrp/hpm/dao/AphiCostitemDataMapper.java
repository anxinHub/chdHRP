package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiCostitemConf;
import com.chd.hrp.hpm.entity.AphiCostitemData;
import com.chd.hrp.hpm.entity.AphiSchemeConf;

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

public interface AphiCostitemDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	public int addBatchCostitemData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemData> queryCostitemData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiCostitemData> queryCostitemData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemConf> getCostItemConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemData queryCostitemDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateCostitemData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCostitemConf getCostItemConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemData> queryCostitemDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemData> queryCostitemIncreaseDept(Map<String, Object> entityMap) throws DataAccessException;

}
