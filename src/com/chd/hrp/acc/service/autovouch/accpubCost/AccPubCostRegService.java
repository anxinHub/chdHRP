package com.chd.hrp.acc.service.autovouch.accpubCost;

import java.util.List;
import java.util.Map;

public interface AccPubCostRegService {

	public String queryAccPubCostReg(Map<String, Object> page);

	public Map<String, Object> queryAccPubCostRegByCode(Map<String, Object> mapVo);

	public String queryAccPubCostRegDept(Map<String, Object> page);

	public String queryDeptAllInfoDict(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAccPubCostRegPrint(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryAccPubCostRegDeptPrint(Map<String, Object> mapVo);

	public String saveAccPubCostReg(Map<String, Object> mapVo);

	public String saveAccPubCostRegDept(Map<String, Object> mapVo);

	public String deleteAccPubCostReg(Map<String, Object> mapVo);

	public String deleteAccPubCostRegDept(Map<String, Object> mapVo);

	public String auditAccPubCostReg(Map<String, Object> mapVo);

	public String importAccPubCostRegDept(Map<String, Object> mapVo);

	public String collectAccPubCostReg(Map<String, Object> mapVo);

	public String queryAccPubCostRegDeptCount(Map<String, Object> mapVo);

}
