package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiItemIncreaseData;
import com.chd.hrp.hpm.entity.AphiItemIncreasePercConf;
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

public interface AphiItemIncreaseDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	public int addBatchItemIncreaseData(List entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int initItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreaseData> queryItemIncreaseData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreaseData> queryItemIncreaseDataByDay(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreasePercConf> getItemIncreasePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItemIncreaseData queryItemIncreaseDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateItemIncreaseData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusData> queryDeptBonusData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int calculate(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiItemIncreasePercConf getItemIncreasePercConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSchemeConf getSchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreaseData> queryItemIncreaseDept(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiItemIncreaseData> queryItemIncreaseLastMoney(Map<String, Object> entityMap) throws DataAccessException;
}
