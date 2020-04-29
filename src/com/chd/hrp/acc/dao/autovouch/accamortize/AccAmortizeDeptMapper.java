package com.chd.hrp.acc.dao.autovouch.accamortize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccAmortizeDeptMapper extends SqlMapper{

	List<Map<String, Object>> queryAmortizeHistoryList(Map<String, Object> mapVo);

	void deleteAmortizeDeptList(List<Map<String, Object>> rmlist);


}
