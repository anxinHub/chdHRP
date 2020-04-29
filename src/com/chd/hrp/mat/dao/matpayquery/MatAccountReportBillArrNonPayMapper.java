package com.chd.hrp.mat.dao.matpayquery;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;

import com.chd.base.SqlMapper;

/**
 * @Description:
 * 票到款未付明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportBillArrNonPayMapper extends SqlMapper {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPay(Map<String,Object> entityMap) throws DataAccessException;
	
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPayMain(Map<String,Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPayMain(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * @Description 
	 * 查询报表<BR> 分页
	 * @param  entityMap
	 * @return List
	 * @throws DataAccessException
	*/
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPay(Map<String,Object> entityMap,RowBounds rowBounds) throws DataAccessException;

	public List<Map<String, Object>> queryMatPayBillDetail(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatPayBillDetail(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	
	/**
	 * 票到款未付总表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException 
	 */
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPaySum(Map<String, Object> entityMap) throws DataAccessException;
	public List<Map<String, Object>> queryMatAccountReportBillArrNonPaySum(Map<String, Object> entityMap, RowBounds rowBounds) throws DataAccessException;
	/**
	 * 查询供应商入库金额,已付金额,未付金额
	 * @param entityMap
	 * @return
	 */
	public List<Map<String, Object>> querySupInPayUnpaidAmountMoney(Map<String, Object> entityMap);
	
	public List<Map<String, Object>> querySupInPayUnpaidAmountMoney(Map<String, Object> entityMap, RowBounds rowBounds);

	
}
