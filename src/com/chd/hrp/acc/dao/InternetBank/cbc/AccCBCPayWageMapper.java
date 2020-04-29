package com.chd.hrp.acc.dao.InternetBank.cbc;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccCBCPayWageMapper extends SqlMapper{

	List<Map<String, Object>> queryAccWagePay(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAccCBCPayWage(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAccWagePayTree(Map<String, Object> entityMap);

	double sumTotalAmtByDay(Map<String, Object> entityMap);

	double sumWage(Map<String, Object> entityMap);

	double totalAmtByFSeqNo(Map<String, Object> entityMap);

	double sumTotalAmtByISeqNo(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAccCBCPayWageRdBySeqNo(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAccCBCPayWageRd(Map<String, Object> entityMap);

	List<Map<String, Object>> queryAccBankForInternet(Map<String, Object> entityMap);

	int addAccCBCWagRd(List<Map<String, Object>> responseList);
	
	int addAccCBCWag(Map<String, Object> mapVo);

	int updateAccCBCWagRd(List<Map<String, Object>> responseList);

	int updateAccCBCWag(Map<String, Object> rspMap);

}
