package com.chd.hrp.acc.service.autovouch.accamortize;

import java.util.List;
import java.util.Map;

public interface AccAmortizeTypeService {

	String saveAmortizeType(Map<String, Object> mapVo);

	String queryAmortizeType(Map<String, Object> mapVo);

	Map<String,Object> queryAmortizeTypeByCode(Map<String, Object> mapVo);

	String deleteAmortizeType(Map<String, Object> mapVo);

	String updateAmortizeType(Map<String, Object> mapVo);

	List<Map<String, Object>> amortizeTypeSelect(Map<String, Object> mapVo);

}
