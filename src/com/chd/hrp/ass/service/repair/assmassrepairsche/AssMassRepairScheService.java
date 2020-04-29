package com.chd.hrp.ass.service.repair.assmassrepairsche;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;

public interface AssMassRepairScheService extends SqlService {

	public String queryAssrRepTeamTree(Map<String, Object> mapVo);

	public Map<String, Object> queryAssRepairUserByCode(Map<String, Object> mapVo);

	public String updateAssRepairUser(Map<String, Object> mapVo);

	public String queryAssRepUser(Map<String, Object> mapVo);

	public String updateWorkDa(Map<String, Object> mapVo);

	public String addRepUser(Map<String, Object> mapVo);

	public String deleteRepUser(List<Map> listVo);

	public String updateWorkDay(Map<String, Object> mapVo);

	public String updateUserSort(List<Map> listVo);

}
