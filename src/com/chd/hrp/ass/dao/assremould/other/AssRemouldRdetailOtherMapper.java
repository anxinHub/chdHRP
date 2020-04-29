package com.chd.hrp.ass.dao.assremould.other;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.other.AssRemouldRdetailOther;

public interface AssRemouldRdetailOtherMapper extends SqlMapper{

	List<Map<String, Object>> queryByRecordNoMap(Map<String, Object> mapVo);

	int updatePriceBySource(Map<String, Object> entityMap);

	List<AssRemouldRdetailOther> queryByDisANo(Map<String, Object> mapVo);
	


}
