package com.chd.hrp.mat.dao.dura.check;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatDuraDeptDetailMapper extends SqlMapper{
 
	public Long queryMatDuraDeptDetailSeq() throws DataAccessException;

	public List<Map<String, Object>> queryDetailByCode(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询待确认的明细数据
	 * @param entityList
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatDuraDeptDetailListForConfirmIn(List<Map<String, Object>> entityList) throws DataAccessException;
	public List<Map<String, Object>> queryMatDuraDeptDetailListForConfirmOut(List<Map<String, Object>> entityList) throws DataAccessException;

}
