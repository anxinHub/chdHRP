package com.chd.hrp.ass.dao.assremould.general;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssRemouldRsourceGeneralMapper extends SqlMapper{

	List<Map<String, Object>> queryChangeSourceByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldSourceGeneral(Map<String, Object> mapVo);
	
}
