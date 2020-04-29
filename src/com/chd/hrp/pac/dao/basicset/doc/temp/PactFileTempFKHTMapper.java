package com.chd.hrp.pac.dao.basicset.doc.temp;


import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactFileTempFKHTMapper extends SqlMapper{

	List<Map<String, Object>> queryTree(Map<String, Object> page);
	List<Map<String ,Object>> queryPactFileTempFKHTfile(Map<String, Object> page);;

}
