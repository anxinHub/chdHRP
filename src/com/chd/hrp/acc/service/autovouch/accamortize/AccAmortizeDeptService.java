package com.chd.hrp.acc.service.autovouch.accamortize;

import java.util.Map;

public interface AccAmortizeDeptService {

	String saveAmortizeDeptList(Map<String, Object> mapVo);

	String queryAmortizeDeptList(Map<String, Object> page);

	String deleteAmortizeDeptList(Map<String, Object> mapVo);

}
