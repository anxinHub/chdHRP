package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmpTargetData;
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

public interface AphiEmpTargetDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchEmpTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpTargetData> queryEmpTargetData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpTargetData> queryEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpTargetData queryEmpTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int shenhe(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * add by alfred
	 */
	public List<AphiEmpTargetData> getEmpTargetValue(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpTargetData> queryTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpTargetData> queryEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryEmpTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;
}
