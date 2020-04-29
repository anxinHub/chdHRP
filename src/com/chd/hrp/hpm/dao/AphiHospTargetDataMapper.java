package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiHospTargetData;

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

public interface AphiHospTargetDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchHospTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> queryHospTargetData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> queryHospTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHospTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * add by alfred
	 */
	public List<AphiHospTargetData> getHospTargetValue(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * add by alfred
	 */
	public List<AphiHospTargetData> getHospTargetValueByTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> queryHospTargetDataByDay(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> queryTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiHospTargetData queryHospTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateHospTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int shenhe(Map<String, Object> entityMap) throws DataAccessException;
}
