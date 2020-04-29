package com.chd.hrp.ass.service.assremould.other;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssRemouldROtherService extends SqlService{

	String queryAssRemouldSourceOther(Map<String, Object> mapVo);

	String queryAssRemouldRdetailOther(Map<String, Object> mapVo);

	String saveAssRemouldRSourceOther(Map<String, Object> mapVo);

	String updateAssRemouldAOtherConfirmState(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	String deleteAssSourceOther(List<Map<String, Object>> listVo);


	String initAssCheckother(Map<String, Object> entityMap);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
