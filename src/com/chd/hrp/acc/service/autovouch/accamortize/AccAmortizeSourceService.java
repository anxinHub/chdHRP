package com.chd.hrp.acc.service.autovouch.accamortize;

import java.util.Map;

public interface AccAmortizeSourceService {


	String saveAmortizeSourceList(Map<String, Object> mapVo);

	String queryAmortizeSourceList(Map<String, Object> mapVo);

	String deleteAmortizeSourceList(Map<String, Object> mapVo);

}
