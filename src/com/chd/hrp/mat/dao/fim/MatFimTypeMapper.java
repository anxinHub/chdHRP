package com.chd.hrp.mat.dao.fim;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface MatFimTypeMapper extends SqlMapper {


	List<Map<String, Object>> queryMatFimTypelMain(Map<String, Object> entityMap);

	int queryMatFimTypeIsExists(Map<String, Object> map);
	
}
