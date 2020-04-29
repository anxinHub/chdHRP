package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptTargetData;
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

public interface AphiDeptTargetDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchDeptTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiHospTargetData> getTargetCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryDeptTargetData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryDeptTargetDataPrint(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryDeptTargetViewGrid(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<Map<String,Object>> queryDeptTargetView(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryDeptTargetView(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptTargetData queryDeptTargetDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int deleteBatchDeptTargetData(List<Map<String, Object>> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateDeptTargetData(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int shenhe(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTargetData> getDeptTargetValue(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptTargetData> getDeptTargetValueByTarget(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryTargetData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryDept(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryDeptBySchemeSeqNo(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptTargetData> queryAphiDeptTargetDataAll(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateBatchDeptTargetData(List<Map<String, Object>> entityList) throws DataAccessException;
	
}
