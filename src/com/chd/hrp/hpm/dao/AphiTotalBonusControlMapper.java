package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiTotalBonusControl;

public interface AphiTotalBonusControlMapper extends SqlMapper {
	
	/**
	 * 
	 */
	public List<AphiTotalBonusControl> queryTotalBonusControlMapperByCode(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
}