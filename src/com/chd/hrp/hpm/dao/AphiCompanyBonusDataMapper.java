package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiCompanyBonusData;

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

public interface AphiCompanyBonusDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCompanyBonusData> queryCompanyBonusData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiCompanyBonusData queryCompanyBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteCompanyBonusDataById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateCompanyBonusData(Map<String, Object> entityMap) throws DataAccessException;
}
