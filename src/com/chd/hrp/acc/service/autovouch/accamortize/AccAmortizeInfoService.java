package com.chd.hrp.acc.service.autovouch.accamortize;

import java.util.List;
import java.util.Map;

public interface AccAmortizeInfoService {

	String saveAmortizeInfo(Map<String, Object> mapVo);

	String queryAmortizeInfo(Map<String, Object> page);

	String queryAmortizeHistoryList(Map<String, Object> mapVo);

	String updateAmortizeInfo(Map<String, Object> mapVo);

	Map<String, Object> queryAmortizeByCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAmortizeInfoPrint(Map<String, Object> mapVo);

	String deleteAmortizeList(Map<String, Object> mapVo);

	String setAmortize(Map<String, Object> mapVo);

	String queryAmortizeReport(Map<String, Object> mapVo);

	String checkAmortizeState(Map<String, Object> mapVo);

	String queryAmortizeHistoryCount(Map<String, Object> mapVo);

}
