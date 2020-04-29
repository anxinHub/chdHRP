package com.chd.hrp.ass.dao.assremould.other;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AssRemouldRsourceOtherMapper extends SqlMapper{

	List<Map<String, Object>> queryChangeSourceByAssCardNo(Map<String, Object> detailVo);

	List<Map<String, Object>> queryAssRemouldSourceOther(Map<String, Object> mapVo);
	
}
