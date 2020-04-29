package com.chd.hrp.ass.dao.payablequery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

public interface AssPayableQueryMapper extends SqlMapper{
	/**
	 * 货到票未到
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryGoodsArrivedInvoiceNot(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryGoodsArrivedInvoiceNot(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * 应付款查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryPaySearch(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryPaySearch(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	/**
	 * 应付款卡片查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryPayCardSearch(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryPayCardSearch(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
	
	
	/**
	 * 应付款供应商查询
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	List<Map<String, Object>> queryPayVenSearch(Map<String,Object> entityMap)throws DataAccessException;
	
	List<Map<String, Object>> queryPayVenSearch(Map<String,Object> entityMap,RowBounds rowBounds)throws DataAccessException;
	
}
