package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import com.chd.base.SqlMapper;

public interface HrFurtherTrackingMapper extends SqlMapper{
   /**
    * 打印
    * @param entityMap
    * @return
    */
	List<Map<String, Object>> queryFurtherTrackingByPrint(Map<String, Object> entityMap);

}
