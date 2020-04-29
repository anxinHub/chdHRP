package com.chd.hrp.pac.dao.jbrps;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
public interface PactMainJBRMapper extends SqlMapper {
	/**
	 * 固定资产降本报表
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryPactAlteration(Map<String, Object> entityMap) throws DataAccessException;
}
