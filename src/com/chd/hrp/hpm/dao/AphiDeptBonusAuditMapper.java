package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;

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

public interface AphiDeptBonusAuditMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusAudit> queryDeptBonusAudit(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusAudit> queryDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptBonusAudit queryDeptBonusAuditByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	//public int deleteDeptBonusAuditById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDeptBonusAudit> deptBonusIsAudit(Map<String, Object> entityMap) throws DataAccessException;

	public AphiDeptBonusAudit querydataAuditaj(Map<String, Object> entityMap);
}
