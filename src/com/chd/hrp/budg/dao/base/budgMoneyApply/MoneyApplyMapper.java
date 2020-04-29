package com.chd.hrp.budg.dao.base.budgMoneyApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;

import com.chd.base.SqlMapper;

public interface MoneyApplyMapper extends SqlMapper{

	public List<Map<String, Object>> queryProjDict(Map<String, Object> entityMap);

	public String queryMaxKey(Map<String, Object> mapVo);

	public void addMain(Map<String, Object> mapVo);

	public void addDetailed(List<Map<String, Object>> list);

	public Map<String, Object> queryChangeNo(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryUpdatePageMainData(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryUpdatePageDetiData(Map<String, Object> mapVo);

	public void deleteMain(HashMap<String, Object> map);

	public void deleteDetailed(HashMap<String, Object> map);

	public void deleteDetailedBatch(List<Map<String, Object>> list);

	public void deleteMainBatch(List<Map<String, Object>> list);

	public Float queryDetailedApply_amount(Map<String, Object> map);

	public void updatMainApply_amount(Map<String, Object> map);

	public void updateMoneyApplyState(List<Map<String, Object>> list);

	public void updateMoneyApplyStateRevoke(List<Map<String, Object>> list);

	public List<Map<String, Object>> queryUserApplyCode(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryProjDict(Map<String, Object> entityMap, RowBounds rowBounds);

	public HashMap<String, Object> queryApplyDataForCode(Map<String, Object> mapVo);

	public Map<String, Object> queryMoneyApplyByPrintTemlateMain(Map<String, Object> entityMap);

	public List<Map<String, Object>> queryMoneyApplyDetByPrintTemlate(Map<String, Object> entityMap);

	public HashMap<String, Object> queryBudgetQuota(Map<String, Object> mapVo);

}
