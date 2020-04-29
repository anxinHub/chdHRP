package com.chd.hrp.acc.service.InternetBank.cbc;

import java.util.List;
import java.util.Map;

public interface AccCBCPayWageService {

	String queryAccWagePay(Map<String, Object> mapVo);

	String queryAccCBCPayWageTree(Map<String, Object> mapVo);

	String accNetPayWageToCBC(Map<String, Object> mapVo);

	String queryAccBankForInternet(Map<String, Object> mapVo);

	String queryAccCBCWageRd(Map<String, Object> mapVo);

	String updateAccCBCPayWage(Map<String, Object> mapVo);

}
