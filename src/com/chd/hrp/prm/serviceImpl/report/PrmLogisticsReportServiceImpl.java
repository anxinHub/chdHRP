package com.chd.hrp.prm.serviceImpl.report;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.util.ChdJson;
import com.chd.hrp.prm.dao.report.PrmLogisticsReportMapper;
import com.chd.hrp.prm.service.report.PrmLogisticsReportService;


/**
 * 
 * @Description: 行政后勤质量奖
 * @Table: 
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("prmLogisticsReportService")
public class PrmLogisticsReportServiceImpl implements PrmLogisticsReportService {
	
	private static Logger logger = Logger.getLogger(PrmLogisticsReportServiceImpl.class);

	@Resource(name = "prmLogisticsReportMapper")
	private final PrmLogisticsReportMapper prmLogisticsReportMapper = null;

	@Override
	public String queryPrmLogisticsReport(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		List<Map<String, Object>> list = prmLogisticsReportMapper.queryPrmLogisticsReport(entityMap);

		return ChdJson.toJsonLower(list);
	}

}
