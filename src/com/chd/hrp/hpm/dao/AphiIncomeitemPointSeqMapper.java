package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemPointSeq;

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

public interface AphiIncomeitemPointSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemPointSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemPointSeq> queryIncomeitemPointSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiIncomeitemPointSeq> queryIncomeitemPointSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemPointSeq queryIncomeitemPointSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemPointSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemPointSeq(Map<String, Object> entityMap) throws DataAccessException;
}
