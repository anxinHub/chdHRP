package com.chd.hrp.hpm.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.hpm.entity.AphiPostBonusRatio;

public interface AphiPostBonusRatioMapper extends SqlMapper {
	
	/**
	 * 
	 */
	public List<AphiPostBonusRatio> queryPostBonusRatioByCode(Map<String, Object> entityMap, RowBounds rowbounds) throws DataAccessException;
}
