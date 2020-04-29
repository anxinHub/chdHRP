package com.chd.hrp.ass.service.assremould.other;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldRdetailHouse;
import com.chd.hrp.ass.entity.assremould.other.AssRemouldRdetailOther;

public interface AssRemouldRDetailOtherService extends SqlService{

	List<AssRemouldRdetailOther> queryByDisANo(Map<String, Object> mapVo);

}
