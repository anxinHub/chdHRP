package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiDept;

public interface AphiSearchSchemeMapper extends SqlMapper {

	/**
	 * 
	 */
	public List<AphiDept> querySchemeOfCondition(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	/**
	 * 
	 */
	public int addScheme(Map<String, Object> entityMap) throws DataAccessException;

}
