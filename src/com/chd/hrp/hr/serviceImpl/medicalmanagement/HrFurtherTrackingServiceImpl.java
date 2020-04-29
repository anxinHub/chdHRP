package com.chd.hrp.hr.serviceImpl.medicalmanagement;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.medicalmanagement.HrFurtherTrackingMapper;
import com.chd.hrp.hr.service.medicalmanagement.HrFurtherTrackingService;
import com.github.pagehelper.PageInfo;

/**
 * 进修统计表
 * 
 * @author Administrator
 *
 */
@Service("hrFurtherTrackingService")
public class HrFurtherTrackingServiceImpl implements HrFurtherTrackingService {
	private static Logger logger = Logger
			.getLogger(HrFurtherTrackingServiceImpl.class);

	@Resource(name = "hrFurtherTrackingMapper")
	private final HrFurtherTrackingMapper hrFurtherTrackingMapper = null;


	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);




	/**
	 * 查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String query(Map<String, Object> entityMap)
			throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrFurtherTrackingMapper
					.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(),
					sysPage.getPagesize());

			List<Map<String, Object>> list = (List<Map<String, Object>>) hrFurtherTrackingMapper
					.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}




	@Override
	public List<Map<String, Object>> queryFurtherTrackingByPrint(
			Map<String, Object> entityMap) throws DataAccessException {
		 List<Map<String,Object>> list = ChdJson.toListLower(hrFurtherTrackingMapper.queryFurtherTrackingByPrint(entityMap));
		 return list;
	}
}
