package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptKindBonusData;

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

public interface AphiDeptKindBonusDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptKindBonusData> queryDeptKindBonusData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptKindBonusData> queryTarget(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptKindBonusData queryDeptKindBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteDeptKindBonusDataById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptKindBonusData(Map<String, Object> entityMap) throws DataAccessException;

}
