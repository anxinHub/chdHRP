package com.chd.hrp.mat.service.matpayquery;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 货到票未到明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportInvArrBillUnArrService {
	
	/**
	 * @Description 
	 * 货到票未到 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/ 
	public String queryMatAccountReportInvArrBillUnArr(Map<String,Object> entityMap) throws DataAccessException;
	//
	public String queryMatAccountReportInvArrBillUnArrSup(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 货到票未到 打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */ 
	public List<Map<String,Object>> queryMatAccountReportInvArrBillUnArrPrint(Map<String,Object> entityMap) throws DataAccessException;
}
