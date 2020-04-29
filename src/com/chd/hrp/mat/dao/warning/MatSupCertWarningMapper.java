package com.chd.hrp.mat.dao.warning;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface MatSupCertWarningMapper  extends SqlMapper{
	
	/**
	 * 查询材料效期预警 不分页
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatSupCertWarning(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 查询材料效期预警 分页
	 * @param entityMap
	 * @param rowBounds
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String, Object>> queryMatSupCertWarning(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatSupCertWarnByDays(Map<String, Object> entityMap) throws DataAccessException;
	
	public  List<Map<String, Object>> queryWarnType(Map<String, Object> entityMap) throws DataAccessException;
}
