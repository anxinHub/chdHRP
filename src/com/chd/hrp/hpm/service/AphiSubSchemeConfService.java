package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiSubSchemeConf;

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

public interface AphiSubSchemeConfService {

	/**
	 * 
	 */
	public String addSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String querySubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String createSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSubSchemeConf querySubSchemeConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteSubSchemeConfById(String[] ids) throws DataAccessException;

	/**
	 * 
	 */
	public String updateSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public String queryHpmSubSchemeConfForEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHpmSubSchemeConfForEmpPrint(Map<String, Object> entityMap) throws DataAccessException;
}
