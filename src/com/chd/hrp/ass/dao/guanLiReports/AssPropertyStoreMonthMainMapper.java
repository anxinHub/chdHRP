package com.chd.hrp.ass.dao.guanLiReports;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.guanLiReports.AssPropertyStoreMonthMain;

public interface AssPropertyStoreMonthMainMapper extends SqlMapper{
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (整合查询)  不带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/                            
	public List<AssPropertyStoreMonthMain> queryAssPropertyStoreMonthMain(Map<String, Object> entityMap)throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询结果集051202  (查询报表<资产月报表  财务制度>) (整合查询)  带分页
	 * @param  entityMap
	 * @param  rowBounds
	 * @return List
	 * @throws DataAccessException
	*/   
	public List<AssPropertyStoreMonthMain> queryAssPropertyStoreMonthMain(Map<String, Object> entityMap, RowBounds rowBounds)throws DataAccessException;
	 
	
	public List<Map<String, Object>> queryAssPropertyStoreMonthMainPrint(
			Map<String, Object> entityMap);
}
