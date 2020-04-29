package com.chd.hrp.prm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.report.PrmMedicalReportMapper;
import com.chd.hrp.prm.service.report.PrmMedicalReportService;


/**
 * 
 * @Description: 医技科室岗位质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmMedicalReportService")
public class PrmMedicalReportServiceImpl implements PrmMedicalReportService {
	
	private static Logger logger = Logger.getLogger(PrmMedicalReportServiceImpl.class);

	@Resource(name = "prmMedicalReportMapper")
	private final PrmMedicalReportMapper prmMedicalReportMapper = null;

	@Override
	public String queryPrmMedicalReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = prmMedicalReportMapper.queryPrmMedicalReport(entityMap);

		return ChdJson.toJsonLower(list);
	}

}
