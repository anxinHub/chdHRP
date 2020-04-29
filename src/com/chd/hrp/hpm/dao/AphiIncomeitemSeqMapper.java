package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiIncomeitemSeq;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiIncomeitemSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addIncomeitemSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiIncomeitemSeq> queryIncomeitemSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 查询收入项目不带分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AphiIncomeitemSeq> queryIncomeitemSeq(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public AphiIncomeitemSeq queryIncomeitemSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteIncomeitemSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateIncomeitemSeq(Map<String, Object> entityMap) throws DataAccessException;
}
