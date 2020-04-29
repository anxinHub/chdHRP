package com.chd.hrp.ass.dao.assremould.general;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRdetailGeneral;

public interface AssRemouldRdetailGeneralMapper extends SqlMapper{

	List<Map<String, Object>> queryByRecordNoMap(Map<String, Object> mapVo);

	int updatePriceBySource(Map<String, Object> entityMap);

	List<AssRemouldRdetailGeneral> queryByDisANo(Map<String, Object> mapVo);
	


}
