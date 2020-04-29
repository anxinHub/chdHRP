package com.chd.hrp.ass.service.assremould.general;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssRemouldRGeneralService extends SqlService{

	String queryAssRemouldSourceGeneral(Map<String, Object> mapVo);

	String queryAssRemouldRdetailGeneral(Map<String, Object> mapVo);

	String saveAssRemouldRSourceGeneral(Map<String, Object> mapVo);

	String updateAssRemouldAgeneralConfirmState(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	String deleteAssSourceGeneral(List<Map<String, Object>> listVo);


	String initAssCheckGeneral(Map<String, Object> mapVo);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
