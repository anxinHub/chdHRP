package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiEmpDutyData;

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

public interface AphiEmpDutyDataMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int addBatchEmpDutyData(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 打印(查询)
	 */
	public List<Map<String, Object>> queryEmpDutyDataPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiEmpDutyData> queryEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpDutyData> queryEmpDutyData(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpDutyData queryEmpDutyDataByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpDutyData(Map<String, Object> entityMap) throws DataAccessException;

}
