package com.chd.hrp.ass.service.assremould.land;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.house.AssRemouldRdetailHouse;
import com.chd.hrp.ass.entity.assremould.land.AssRemouldRdetailLand;

public interface AssRemouldRDetailLandService extends SqlService{

	List<AssRemouldRdetailLand> queryByDisANo(Map<String, Object> mapVo);

}
