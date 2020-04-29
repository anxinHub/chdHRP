package com.chd.hrp.hpm.service.performance;

import java.util.Map;

public interface AphiPerformanceService {

	public String queryHpmEmpBonusDataForReportGrid(Map<String, Object> mapVo);

	public String queryHpmPerformance(Map<String, Object> page); 

}
