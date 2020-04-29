package com.chd.hrp.acc.dao.autovouch.accamortize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccAmortizeInfoMapper extends SqlMapper {

	int saveAmortizeInfo(Map<String, Object> mapVo);

	String queryMaxNoByTypeCode(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAmortizeHistoryList(Map<String, Object> mapVo);

	void updateAmortizeInfo(Map<String, Object> mapVo);

	void addHistoryBatch(List<Map<String, Object>> historyList);

	List<Map<String, Object>> queryForAmortize(Map<String, Object> mapVo);

	void updateStateBatch(List<Map<String, Object>> updateList);

	void deleteAmortizeHistoryList(List<Map<String, Object>> updateList);

	List<Map<String, Object>> queryPrint(Map<String, Object> mapVo);

}
