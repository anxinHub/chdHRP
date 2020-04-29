package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrFurtherStatisticsMapper extends SqlMapper{
   /**
    * 打印
    * @param entityMap
    * @return
    */
	List<Map<String, Object>> queryFurtherStatisticsByPrint(Map<String, Object> entityMap);

}
