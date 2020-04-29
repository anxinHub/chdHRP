package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDistributionBonusComparative;

public interface AphiDistributionBonusComparativeMapper extends SqlMapper {

	/**
	 * 
	 */
	public List<AphiDistributionBonusComparative> queryDistributionBonusComparativeMapperByKind(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

	/**
	 * 
	 */
	public List<AphiDistributionBonusComparative> queryDistributionBonusComparativeMapperByNature(Map<String, Object> entityMap, RowBounds rowBounds)
			throws DataAccessException;

}
