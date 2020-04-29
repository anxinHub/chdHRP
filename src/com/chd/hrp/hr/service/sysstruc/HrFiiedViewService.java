package com.chd.hrp.hr.service.sysstruc;

import java.util.Map;

import com.chd.base.SqlService;

public interface HrFiiedViewService extends SqlService{

	String saveHrFiiedView(Map<String, Object> entityMap);
}
