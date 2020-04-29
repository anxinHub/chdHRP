package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiWorkitemSeq;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiWorkitemSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addWorkitemSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiWorkitemSeq> queryWorkitemSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查询不带分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AphiWorkitemSeq> queryWorkitemSeq(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public AphiWorkitemSeq queryWorkitemSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteWorkitemSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateWorkitemSeq(Map<String, Object> entityMap) throws DataAccessException;
}
