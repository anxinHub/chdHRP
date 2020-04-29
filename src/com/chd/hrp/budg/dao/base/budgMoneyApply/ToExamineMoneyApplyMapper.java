package com.chd.hrp.budg.dao.base.budgMoneyApply;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface ToExamineMoneyApplyMapper extends SqlMapper{

	public void updateToExamineMoneyApplyState(HashMap<String, Object> map);

	public void updateToExamineMoneyApplyStateRevoke(List<Map<String, Object>> list);

	public List<Map<String, Object>> queryUpdatePageMainData(Map<String, Object> mapVo);

	public List<Map<String, Object>> queryUpdatePageDetiData(Map<String, Object> mapVo);



}
