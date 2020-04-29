package com.chd.hrp.hr.dao.salarymanagement.empWage;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrWageQueryMapper extends SqlMapper{
    //打印
	List<Map<String, Object>> queryWageQueryByPrint(Map<String, Object> entityMap);

}
