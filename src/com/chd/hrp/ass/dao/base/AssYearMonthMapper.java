package com.chd.hrp.ass.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.base.AssYearMonth;

public interface AssYearMonthMapper extends SqlMapper{
	
	int addAssYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int addBatchAssYearMonth(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	int updateModAssYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int updateAssYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	int updateAssDelYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int updateBatchAssYearMonth(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	int deleteAssYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int deleteBatchAssYearMonth(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	AssYearMonth queryAssYearMonthByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	
	List<AssYearMonth> queryAssYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	List<AssYearMonth> queryAssYearMonth(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	public Map<String, Object> queryAssCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;


	public Map<String, Object> queryAssSysYearMonth(Map<String, Object> mapVo)throws DataAccessException;
	
}
