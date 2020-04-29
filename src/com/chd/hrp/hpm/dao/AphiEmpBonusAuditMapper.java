package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmpBonusAudit;

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

public interface AphiEmpBonusAuditMapper extends SqlMapper {  

	/**
	 * 
	 */
	public int addEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpBonusAudit> queryEmpBonusAudit(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpBonusAudit queryEmpBonusAuditByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteEmpBonusAuditById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpBonusAuditData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpBonusAudit> queryEmpBonusAudit(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpBonusAudit> queryEmpBonusAuditData(Map<String, Object> entityMap) throws DataAccessException;
	
	
	//发放到工资表
	public List<Map<String,Object>> queryEmpBonusDataForWagePay(Map<String, Object> entityMap) throws DataAccessException;
	
	//根据年、月、wage_code 删除工资发放表中的数据
	public int deleteAccWagePayByYearMonth(Map<String, Object> entityMap) throws DataAccessException;
	
	//根据数据
	public int addEmpBonusDataForWagePay(@Param("sql") String sql,@Param("sqlValue") String sqlValue,@Param("addList2") List<Map<String, Object>> entityMap) throws DataAccessException;

	public void updateBatchWage(@Param("sqlWage") String sqlWage,@Param("addList") List<Map<String, Object>> addList) throws DataAccessException;

	public Map<String, Object> queryWagePay(Map<String, Object> entityMap) throws DataAccessException;

	public void updateBatchWagePay(Map<String, Object> addMapWagePay) throws DataAccessException;

	public int updateBatchWagePayList(List<Map<String, Object>> updateList) throws DataAccessException;

	public void updateBatchWage(@Param("addList") List<Map<String, Object>> addList);


	//public int updateWageItem(Map<String, Object> addWageMap) throws DataAccessException;

}
