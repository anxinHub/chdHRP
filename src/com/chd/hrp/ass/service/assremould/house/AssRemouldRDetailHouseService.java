package com.chd.hrp.ass.service.assremould.house;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldRdetailHouse;

public interface AssRemouldRDetailHouseService extends SqlService{

	List<AssRemouldRdetailHouse> queryByDisANo(Map<String, Object> mapVo);

}
