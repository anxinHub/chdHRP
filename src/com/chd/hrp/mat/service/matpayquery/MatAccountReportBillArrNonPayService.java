package com.chd.hrp.mat.service.matpayquery;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

/**
 * @Description:
 * 票到款未付明细表
 * @Table:
 * @Author: bell
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */
public interface MatAccountReportBillArrNonPayService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMatAccountReportBillArrNonPay(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * @Description 
	 * 未汇总报表打印<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAccountReportBillArrNonPayPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 报表查询未汇总
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAccountReportBillArrNonPayMain(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 报表打印汇总
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAccountReportBillArrNonPayMainPrint(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 发票统计表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatPayBillDetail(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 发票统计表打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatPayBillDetailPrint(Map<String, Object> entityMap) throws DataAccessException;
	
	/**
	 * 票到款未付总表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMatAccountReportBillArrNonPaySum(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 票到款未付总表打印
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public List<Map<String,Object>> queryMatAccountReportBillArrNonPaySumPrint(Map<String, Object> entityMap) throws DataAccessException;
	/**
	 * 查询供应商应付款,已付款,未付款总额
	 * @param page
	 * @return 
	 */
	public String querySupInPayUnpaidAmountMoney(Map<String, Object> page);
	/**
	 * 查询供应商应付款,已付款,未付款总额-打印
	 * @param page
	 * @return
	 */
	public List<Map<String, Object>> querySupInPayUnpaidAmountMoneyPrint(Map<String, Object> entityMap) throws DataAccessException;
}
