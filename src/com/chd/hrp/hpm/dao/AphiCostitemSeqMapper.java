package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiCostitemSeq;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

public interface AphiCostitemSeqMapper extends SqlMapper {

	/**
	 * 
	 */
	public int addCostitemSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiCostitemSeq> queryCostitemSeq(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 不带分页查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<AphiCostitemSeq> queryCostitemSeq(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 
	 */
	public AphiCostitemSeq queryCostitemSeqByCode(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int deleteCostitemSeq(Map<String, Object> entityMap) throws DataAccessException;

	/**
	 * 
	 */
	public int updateCostitemSeq(Map<String, Object> entityMap) throws DataAccessException;
}
