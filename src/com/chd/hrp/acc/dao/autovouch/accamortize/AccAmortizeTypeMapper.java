package com.chd.hrp.acc.dao.autovouch.accamortize;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface AccAmortizeTypeMapper extends SqlMapper{

	int saveAmoirtizeCardInfo(Map<String, Object> mapVo);

	Map<String,Object> queryTypeCodeExist(Map<String, Object> mapVo);

	List<Map<String, Object>> queryAmortizeTypeSelect(Map<String, Object> mapVo);

}
