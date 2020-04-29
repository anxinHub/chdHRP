package com.chd.hrp.hr.service.salarymanagement.accumulationfund;

import java.util.Map;

public interface HrProvidentfundratioService {

	//添加
	String saveProvidentfundratio(Map<String, Object> mapVo);

	//查询
	Map<String, Object> queryProvidentfundratio(Map<String, Object> mapVo);

}
