package com.chd.hrp.hr.serviceImpl.nursing;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.nursing.HrInserviceStatisticsMapper;
import com.chd.hrp.hr.entity.nursing.HrEducationStudent;
import com.chd.hrp.hr.service.nursing.HrInserviceStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * 年度在职教育培训学时统计
 * 
 * @author Administrator
 *
 */
@Service("hrInserviceStatisticsService")
public class HrInserviceStatisticsServiceImpl implements HrInserviceStatisticsService {
	private static Logger logger = Logger.getLogger(HrInserviceStatisticsServiceImpl.class);

	@Resource(name = "hrInserviceStatisticsMapper")
	private final HrInserviceStatisticsMapper hrInserviceStatisticsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);


	/**
	 * 年度在职教育培训学时统计查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryInserviceStatistics(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceStatisticsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrEducationStudent> list = (List<HrEducationStudent>) hrInserviceStatisticsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryByPrint(Map<String, Object> entityMap) throws DataAccessException {
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		List<Map<String, Object>> list = ChdJson.toListLower(hrInserviceStatisticsMapper.queryByPrint(entityMap));
		return list;
	}

}
