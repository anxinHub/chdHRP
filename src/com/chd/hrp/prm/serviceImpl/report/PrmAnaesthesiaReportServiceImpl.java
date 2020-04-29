package com.chd.hrp.prm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.report.PrmAnaesthesiaReportMapper;
import com.chd.hrp.prm.service.report.PrmAnaesthesiaReportService;


/**
 * 
 * @Description: 麻醉科质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmAnaesthesiaReportService")
public class PrmAnaesthesiaReportServiceImpl implements PrmAnaesthesiaReportService {
	
	private static Logger logger = Logger.getLogger(PrmAnaesthesiaReportServiceImpl.class);

	@Resource(name = "prmAnaesthesiaReportMapper")
	private final PrmAnaesthesiaReportMapper prmAnaesthesiaReportMapper = null;

	@Override
	public String queryPrmAnaesthesiaReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = prmAnaesthesiaReportMapper.queryPrmAnaesthesiaReport(entityMap);

		return ChdJson.toJsonLower(list);
	}

}
