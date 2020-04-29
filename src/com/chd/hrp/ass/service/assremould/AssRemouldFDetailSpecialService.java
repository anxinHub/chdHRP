package com.chd.hrp.ass.service.assremould;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlService;
import com.chd.hrp.ass.entity.assremould.AssRemouldFdetailSpecial;

public interface AssRemouldFDetailSpecialService  extends SqlService {

	List<AssRemouldFdetailSpecial> queryByDisANo(Map<String, Object> mapVo);

}
