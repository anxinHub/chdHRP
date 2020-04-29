package com.chd.hrp.med.service.medpayquery;

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
public interface MedAccountReportBillArrNonPayService {
	
	/**
	 * @Description 
	 * 查询报表<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountReportBillArrNonPay(Map<String,Object> entityMap) throws DataAccessException;
	
	public String queryMedAccountReportBillArrNonPayMain(Map<String,Object> entityMap) throws DataAccessException;
	/**
	 * 发票统计表
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	public String queryMedPayBillDetail(Map<String, Object> entityMap) throws DataAccessException;
}
