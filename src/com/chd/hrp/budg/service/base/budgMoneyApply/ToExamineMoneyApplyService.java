package com.chd.hrp.budg.service.base.budgMoneyApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface ToExamineMoneyApplyService{

	public String queryToExamineMoneyApply(Map<String, Object> page);

	public String updateToExamineMoneyApplyState(HashMap<String, Object> map);

	public String updateToExamineMoneyApplyStateRevoke(List<Map<String, Object>> list);


	public Map<String, Object> ToExamineMoneyApplyDetail(Map<String, Object> mapVo);

	public String queryToExamineMoneyApplyDet(Map<String, Object> mapVo);



}
