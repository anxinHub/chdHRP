package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemConfSeq;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiIncomeitemConfSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemConfSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemConfSeq> queryIncomeitemConfSeq(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 
	 */
	public List<AphiIncomeitemConfSeq> queryIncomeitemConfSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public AphiIncomeitemConfSeq queryIncomeitemConfSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemConfSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemConfSeq(Map<String, Object> entityMap) throws DataAccessException;
}
