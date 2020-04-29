package com.chd.hrp.pac.dao.basicset.doc.temp;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactFileTempSKXYMapper extends SqlMapper{

	List<Map<String, Object>> queryTree(Map<String, Object> page);
	
	List<Map<String ,Object>> queryPactFileTempSKXYfile(Map<String ,Object>page);

}
