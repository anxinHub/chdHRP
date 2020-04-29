package com.chd.hrp.budg.service.base.budgMoneyApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.chd.base.SqlService;

public interface MoneyApplyService{

	public String queryMoneyApply(Map<String, Object> mapVo);

	public String queryProjDict(Map<String, Object> mapVo);

	public String addMoneyApply(List<Map<String, Object>> list, Map<String, Object> mapVo);

	public String queryMaxKey(Map<String, Object> mapVo);

	public Map<String, Object> queryUpdatePageData(Map<String, Object> mapVo);

	public String queryMoneyApplyDet(Map<String, Object> mapVo);

	public void deleteFromId(HashMap<String, Object> entMap);

	public String deleteFromBatch(List<Map<String, Object>> list);

	public String deleteFromBatchDetailed(List<Map<String, Object>> list);

	public void updatMainApply_amount(List<Map<String, Object>> list);

	public String updateMoneyApplyState(List<Map<String, Object>> list);

	public String updateMoneyApplyStateRevoke(List<Map<String, Object>> list);

	public String queryUserApplyCode(Map<String, Object> entityMap);

	public HashMap<String, Object> queryApplyDataForCode(Map<String, Object> mapVo);
	
	public Map<String, Object> queryMoneyApplyPrintTemlate(Map<String,Object> entityMap) throws DataAccessException;

	public HashMap<String, Object> queryBudgetQuota(Map<String, Object> mapVo);

}
