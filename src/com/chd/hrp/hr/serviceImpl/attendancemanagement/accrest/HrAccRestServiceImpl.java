package com.chd.hrp.hr.serviceImpl.attendancemanagement.accrest;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.accrest.HrAccRestMapper;
import com.chd.hrp.hr.service.attendancemanagement.accrest.HrAccRestService;
import com.chd.hrp.hr.serviceImpl.scientificresearch.HrAcademicHonorsServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("hrAccRestService")
public class HrAccRestServiceImpl  implements HrAccRestService{
	private static Logger logger = Logger.getLogger(HrAccRestServiceImpl.class);
	@Resource(name = "hrAccRestMapper")
	private final HrAccRestMapper hrAccRestMapper = null;



	@Override
	public String queryAccRest(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAccRestMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrAccRestMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}



	

}
