package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
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

public interface AphiEmpSchemeSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiEmpSchemeSeq> queryEmpSchemeSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiEmpSchemeSeq queryEmpSchemeSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteEmpSchemeSeqById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<Map<String,Object>> queryEmpByEmpSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
	
}
