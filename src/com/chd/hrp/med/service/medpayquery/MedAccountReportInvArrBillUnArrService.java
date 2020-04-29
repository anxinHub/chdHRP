package com.chd.hrp.med.service.medpayquery;

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
public interface MedAccountReportInvArrBillUnArrService {
	
	/**
	 * @Description 
	 * 货到票未到 查询<BR> 
	 * @param  entityMap
	 * @return String
	 * @throws DataAccessException
	*/
	public String queryMedAccountReportInvArrBillUnArr(Map<String,Object> entityMap) throws DataAccessException;
}
