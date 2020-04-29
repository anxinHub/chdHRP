package com.chd.hrp.mat.dao.matpayquery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * 
 * @Description:
 * 货到票未到明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportInvArrBillUnArrMapper extends SqlMapper {
	
	/** 
	 * @Description 
	 * 货到票未到 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatAccountReportInvArrBillUnArr(Map<String,Object> entityMap) throws DataAccessException;
	
	/**
	 * @Description 
	 * 货到票未到 查询<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatAccountReportInvArrBillUnArr(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	//
	public List<Map<String, Object>> queryMatAccountReportInvArrBillUnArrSup(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAccountReportInvArrBillUnArrSup(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	
}
