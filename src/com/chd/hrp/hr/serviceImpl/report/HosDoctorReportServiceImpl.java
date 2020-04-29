package com.chd.hrp.hr.serviceImpl.report;

import java.util.*;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.report.HosDoctorReportMapper;
import com.chd.hrp.hr.service.report.HosDoctorReportService;
import com.github.pagehelper.PageInfo;

@Service("hosDoctorReportService")
public class HosDoctorReportServiceImpl implements HosDoctorReportService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(HosDoctorReportServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "hosDoctorReportMapper")
	private final HosDoctorReportMapper hosDoctorReportMapper = null;


	
	

	/**
	 * @Description 查询结果集<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String queryHrDoctorReport(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) hosDoctorReportMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) hosDoctorReportMapper.query(entityMap, rowBounds);

		
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}





}
