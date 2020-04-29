package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiSubSchemeSeq;

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

public interface AphiSubSchemeSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addSubSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiSubSchemeSeq> querySubSchemeSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查询返回最大ID
	 */
	public int queryMaxSubSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public AphiSubSchemeSeq querySubSchemeSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteSubSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
     * 
     */
	public int deleteSubSchemeSeqById(String id) throws DataAccessException;

	/**
	 * 
	 */
	public int updateSubSchemeSeq(Map<String, Object> entityMap) throws DataAccessException;
}
