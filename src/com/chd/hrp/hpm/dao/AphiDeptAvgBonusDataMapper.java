package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptAvgBonusData;

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

public interface AphiDeptAvgBonusDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptAvgBonusData> queryDeptAvgBonusData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptAvgBonusData> queryDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptAvgBonusData> queryDeptAvgBonusDataByCollect(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptAvgBonusData queryDeptAvgBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteDeptAvgBonusDataById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptAvgBonusData(Map<String, Object> entityMap) throws DataAccessException;

}
