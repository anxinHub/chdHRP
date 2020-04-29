package com.chd.hrp.hr.service.report;

import java.util.Map;
/**
 * 人员职称统计表（医生）
 * @author Administrator
 *
 */
public interface HosDoctorReportService {

	String queryHrDoctorReport(Map<String, Object> page);

}
