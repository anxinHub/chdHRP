package com.chd.hrp.hr.dao.medicalmanagement;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.chd.base.SqlMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrMeetDiagApplication;

public interface HrMeetDiagApplicationDetailMapper extends SqlMapper{

	void deleteDetailBatch(@Param(value="list") List<HrMeetDiagApplication> entityList,
			@Param(value="map") Map<String, Object> mapVo);
  

}
