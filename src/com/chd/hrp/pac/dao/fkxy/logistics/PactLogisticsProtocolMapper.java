package com.chd.hrp.pac.dao.fkxy.logistics;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface PactLogisticsProtocolMapper extends SqlMapper{

	List<Map<String, Object>> queryPactProtocalSummaryFKXY(Map<String, Object> mapVo);

	List<Map<String, Object>> queryPactProtocolDetailFKXY(Map<String, Object> mapVo);

}
