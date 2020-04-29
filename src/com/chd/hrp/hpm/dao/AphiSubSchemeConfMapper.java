package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface AphiSubSchemeConfMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiSubSchemeConf> querySubSchemeConf(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSubSchemeConf querySubSchemeConfByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteSubSchemeConfById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateSubSchemeConf(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHpmSubSchemeConfForEmp(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String, Object>> queryHpmSubSchemeConfForEmp(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 
	 */
	public AphiSubSchemeConf querySubSchemeConfSeq(Map<String, Object> entityMap) throws DataAccessException;
}
