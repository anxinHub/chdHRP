package com.chd.hrp.htcg.serviceImpl.analysis;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.JsonListBeanUtil;
import com.chd.hrp.htcg.dao.analysis.PatientDrgsAnalysisMapper;
import com.chd.hrp.htcg.service.analysis.PatientDrgsAnalysisService;
@Service("patientDrgsAnalysisService")
public class PatientDrgsAnalysisServiceImpl implements PatientDrgsAnalysisService {

	private static Logger logger = Logger.getLogger(PatientDrgsAnalysisServiceImpl.class);
	
	@Resource(name="patientDrgsAnalysisMapper")
	private final PatientDrgsAnalysisMapper patientDrgsAnalysisMapper = null;

	@Override
	public String queryPatientDrgsAnalysisCostDetail(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(patientDrgsAnalysisMapper.queryPatientDrgsAnalysisCostDetail(entityMap, rowBounds), sysPage.getTotal());
	}

	@Override
	public String queryPatientDrgsAnalysisCost(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) entityMap.get("sysPage");
		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
		return JsonListBeanUtil.listToJson(patientDrgsAnalysisMapper.queryPatientDrgsAnalysisCost(entityMap, rowBounds), sysPage.getTotal());
	}
	

}
