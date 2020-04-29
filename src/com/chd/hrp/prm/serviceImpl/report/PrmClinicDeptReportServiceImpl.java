package com.chd.hrp.prm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.report.PrmClinicDeptReportMapper;
import com.chd.hrp.prm.service.report.PrmClinicDeptReportService;


/**
 * 
 * @Description: 临床科室质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmClinicDeptReportService")
public class PrmClinicDeptReportServiceImpl implements PrmClinicDeptReportService {
	
	private static Logger logger = Logger.getLogger(PrmClinicDeptReportServiceImpl.class);

	@Resource(name = "prmClinicDeptReportMapper")
	private final PrmClinicDeptReportMapper prmClinicDeptReportMapper = null;

	@Override
	public String queryPrmClinicDeptReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = prmClinicDeptReportMapper.queryPrmClinicDeptReport(entityMap);

		return ChdJson.toJsonLower(list);
	}

}
