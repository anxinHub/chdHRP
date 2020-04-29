package com.chd.hrp.ass.service.assremould.house;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssRemouldRHouseService extends SqlService{

	String queryAssRemouldSourceHouse(Map<String, Object> mapVo);

	String queryAssRemouldRdetailHouse(Map<String, Object> mapVo);

	String saveAssRemouldRSourceHouse(Map<String, Object> mapVo);


	String updateAssRemouldAHouseConfirmState(List<Map<String, Object>> listVo, List<Map<String, Object>> listCardVo);

	String deleteAssSourceHouse(List<Map<String, Object>> listVo);


	String initAssCheckHouse(Map<String, Object> mapVo);

	String deleteBatchAssApplyPlanMap(List<Map<String, Object>> listVo);

}
