package com.chd.hrp.pac.dao.basicset.warning;


import java.util.List;

import com.chd.base.SqlMapper;
import com.chd.hrp.pac.entity.basicset.warning.PactWarnSetFKHTEntity;

public interface PactWarnSetFKHTMapper extends SqlMapper{

	void deleteAllBatch(List<PactWarnSetFKHTEntity> entityMap);


}
