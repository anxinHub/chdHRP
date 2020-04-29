package com.chd.hrp.ass.dao.assremould.general;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssRemouldRGeneralMapper extends SqlMapper{

	void updateConfirmAssRemouldRspecial(List<Map<String, Object>> listVo);

	void collectAssRemoildRGeneral(Map<String, Object> entityMap);

	void addAssPlanDeptImportBid(Map<String, Object> planApplyMapvo);

	void deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
