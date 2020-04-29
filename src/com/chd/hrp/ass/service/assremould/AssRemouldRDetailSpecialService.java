package com.chd.hrp.ass.service.assremould;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.AssRemouldRdetailSpecial;

public interface AssRemouldRDetailSpecialService extends SqlService {

	List<AssRemouldRdetailSpecial> queryByDisANo(Map<String, Object> mapVo);

}
