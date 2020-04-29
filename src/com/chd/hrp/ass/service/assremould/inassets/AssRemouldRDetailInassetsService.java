package com.chd.hrp.ass.service.assremould.inassets;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldRdetailHouse;
import com.chd.hrp.ass.entity.assremould.inassets.AssRemouldRdetailInassets;

public interface AssRemouldRDetailInassetsService extends SqlService{

	List<AssRemouldRdetailInassets> queryByDisANo(Map<String, Object> mapVo);

}
