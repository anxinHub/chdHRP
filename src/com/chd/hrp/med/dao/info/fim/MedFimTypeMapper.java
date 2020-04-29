package com.chd.hrp.med.dao.info.fim;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface MedFimTypeMapper extends SqlMapper {


	List<Map<String, Object>> queryMedFimTypeMain(Map<String, Object> entityMap);

	int queryMedFimTypeIsExists(Map<String, Object> map);
	
}
