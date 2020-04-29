package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusGrant;

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

public interface AphiDeptBonusGrantMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 打印-查询
	 */
	public List<Map<String, Object>> queryDeptBonusGrantPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiDeptBonusGrant> queryDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;
	

	/**
	 * 
	 */
	public List<AphiDeptBonusGrant> queryDeptBonusGrant(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptBonusGrant queryDeptBonusGrantByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteDeptBonusGrantById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptBonusGrant(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public int updateBatchDeptBonusGrant(List<Map<String, Object>> entityList) throws DataAccessException;

	public List<AphiDeptBonusGrant> queryListDeptBonusGrant(
			Map<String, Object> listVo)  throws DataAccessException;

	public List<AphiDeptBonusGrant> queryListDeptBonusGrant_Audit(
			Map<String, Object> entityMap) throws DataAccessException;

	public AphiDeptBonusGrant queryDeptBonusGrantByCode_Grant(
			Map<String, Object> entityMap)  throws DataAccessException;

	public void updateDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiDeptBonusGrant> queryDeptBonusGrantByCode_GrantList(
			Map<String, Object> entityMap) throws DataAccessException;

	public List<AphiDeptBonusGrant> queryDeptBonusGrantState(
			Map<String, Object> entityMap) throws DataAccessException;

	public String querySumDeptBonusGrant(Map<String, Object> entityMap);
	
	

}
