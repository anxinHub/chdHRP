package com.chd.hrp.ass.dao.assremould.other;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssRemouldROtherMapper extends SqlMapper{


	void updateConfirmAssRemouldROther(List<Map<String, Object>> listVo);

	void collectAssRemoildRother(Map<String, Object> entityMap);

	void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo);

	void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
