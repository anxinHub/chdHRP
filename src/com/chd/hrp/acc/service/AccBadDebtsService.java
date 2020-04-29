package com.chd.hrp.acc.service;

import java.util.List;
import java.util.Map;

public interface AccBadDebtsService {

	//查询
	String queryBadDebts(Map<String, Object> mapVo);

	//删除
	String addBadDebts(Map<String, Object> mapVo);

	//查询科目
	List<Map<String, Object>> queryAccSubjSelect(Map<String, Object> mapVo);

	String importBadDebts(Map<String, Object> mapVo);

	List<Map<String, Object>> queryBadDebtsPrint(Map<String, Object> mapVo);
	
}
