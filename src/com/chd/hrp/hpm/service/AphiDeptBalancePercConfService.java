package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiDeptBalancePercConf;

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

public interface AphiDeptBalancePercConfService {

	/**
	 * 
	 */
	public String addDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String addBatchDeptBalancePercConf(List<Map<String, Object>> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiDeptBalancePercConf queryDeptBalancePercConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteDeptBalancePercConf(Map<String, Object> entityMap, String codes) throws DataAccessException;

	/**
	 * 
	 */
	public String updateDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastDeptBalancePercConf(Map<String, Object> entityMap) throws DataAccessException;

	//导入
	public String hpmdeptBalancePercConfImport(Map<String, Object> entityMap)throws DataAccessException;
}
