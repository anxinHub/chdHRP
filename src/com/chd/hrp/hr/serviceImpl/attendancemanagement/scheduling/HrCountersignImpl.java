package com.chd.hrp.hr.serviceImpl.attendancemanagement.scheduling;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrCountersignMapper;
import com.chd.hrp.hr.dao.attendancemanagement.scheduling.HrSchedulingMapper;
import com.chd.hrp.hr.service.attendancemanagement.scheduling.HrCountersignService;
import com.github.pagehelper.PageInfo;

@Service("hrCountersignService")
public class HrCountersignImpl  implements HrCountersignService{

	@Resource(name="hrCountersignMapper")
	private final HrCountersignMapper hrCountersignMapper=null;
	@Resource(name = "hrSchedulingMapper")
	private final HrSchedulingMapper hrSchedulingMapper = null;
	@Override
	public String auditCountersign(List<Map<String,Object>> saveList)
			throws DataAccessException {
		//hrSchedulingMapper.auditCountersign(saveList);
		
		return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
	}

	@Override
	public String queryCountersign(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSchedulingMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrSchedulingMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

}

}
