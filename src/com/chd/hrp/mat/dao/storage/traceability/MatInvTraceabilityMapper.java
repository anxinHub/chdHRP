package com.chd.hrp.mat.dao.storage.traceability;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatInvTraceabilityMapper  extends SqlMapper{

	/**
	 * 材料追溯左边数据查询
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, String>> queryMatInvTraceability(
			Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;  

	/**
	 * 查询右边数据
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, String>> queryMatInvTraceabilityBar_code(Map<String, Object> entityMap)throws DataAccessException;

}
