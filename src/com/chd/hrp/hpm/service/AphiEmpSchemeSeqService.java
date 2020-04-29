package com.chd.hrp.hpm.service;

import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.hrp.hpm.entity.AphiEmpSchemeSeq;

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

public interface AphiEmpSchemeSeqService {

	/**
	 * 
	 */
	public String addEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String queryEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpSchemeSeq queryEmpSchemeSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public String deleteEmpSchemeSeqById(String[] ids) throws DataAccessException;

	/**
	 * 
	 */
	public String updateEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
}
