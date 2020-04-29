package com.chd.hrp.ass.dao.base;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.base.AssYearMonth;

public interface InassYearMonthMapper extends SqlMapper{
	
	int addInassYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int addBatchInassYearMonth(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	int updateModInassYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int updateInassYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	int updateInassDelYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int updateBatchInassYearMonth(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	int deleteInassYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	
	int deleteBatchInassYearMonth(List<Map<String, Object>> entityMap)throws DataAccessException;
	
	
	AssYearMonth queryInassYearMonthByCode(Map<String, Object> entityMap)throws DataAccessException;
	
	
	List<AssYearMonth> queryInassYearMonth(Map<String, Object> entityMap)throws DataAccessException;
	
	List<AssYearMonth> queryInassYearMonth(Map<String, Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	public Map<String, Object> queryInassCurYearMonth(Map<String,Object> entityMap) throws DataAccessException;


	public Map<String, Object> queryInassSysYearMonth(Map<String, Object> mapVo)throws DataAccessException;
	
}
