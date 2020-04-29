package com.chd.hrp.cost.dao.analysis.c10;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface C10Mapper extends SqlMapper{
	/**
	 * @Description 
	 *  查询不带分页
	 * @param  entityMap RowBounds
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC1001(Map<String,Object> entityMap, RowBounds rowBounds) throws DataAccessException;

	public int deleteAnalysisC1001(Map<String,Object> entityMap)throws DataAccessException;
	
	
	public int addAnalysisC1001(Map<String,Object> entityMap)throws DataAccessException;
	/**
	 * @Description 
	 * 盘亏分析数据访问类<BR> 查询不带分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<?> queryAnalysisC1001(Map<String,Object> entityMap) throws DataAccessException;

	public List<Map<String, Object>> queryC1001Print(
			Map<String, Object> entityMap) throws DataAccessException;
	
}
