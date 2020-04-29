package com.chd.hrp.ass.service.assremould.general;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.general.AssRemouldRdetailGeneral;

public interface AssRemouldRDetailGeneralService extends SqlService{

	List<AssRemouldRdetailGeneral> queryByDisANo(Map<String, Object> mapVo);

}
