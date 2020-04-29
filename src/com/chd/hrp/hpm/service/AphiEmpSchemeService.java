package com.chd.hrp.hpm.service;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpScheme;

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

public interface AphiEmpSchemeService {

	/**
	 * 
	 */
	public String addEmpScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryEmpScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpScheme queryEmpSchemeByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpScheme(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public String deleteBatchEmpScheme(List<Map<String, Object>> entityList) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpSchemeById(String[] ids) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpScheme(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String fastEmpScheme(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 打印-查询
	 */
	public List<Map<String, Object>> queryEmpSchemePrint(Map<String, Object> entityMap) throws DataAccessException;
}
