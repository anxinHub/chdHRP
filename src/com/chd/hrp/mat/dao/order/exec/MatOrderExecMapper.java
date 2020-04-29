package com.chd.hrp.mat.dao.order.exec;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatOrderExecMapper extends SqlMapper{

	/**
	 * 订单执行查询--主查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<?> queryMatOrderExec(Map<String, Object> entityMap) throws DataAccessException;

	public List<?> queryMatOrderExec(Map<String, Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
}
