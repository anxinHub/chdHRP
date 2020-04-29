package com.chd.hrp.pac.dao.basicset.warning;


import java.util.List;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.warning.BaseWarnEntity;

public interface PactWarnSetSKXYMapper extends SqlMapper{

	void deleteAllBatch(List<BaseWarnEntity> entityMap);


}
