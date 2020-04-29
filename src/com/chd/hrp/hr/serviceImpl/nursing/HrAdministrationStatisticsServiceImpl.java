package com.chd.hrp.hr.serviceImpl.nursing;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.nursing.HrAdministrationStatisticsMapper;
import com.chd.hrp.hr.entity.nursing.HrAdministrationStatistics;
import com.chd.hrp.hr.service.nursing.HrAdministrationStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * 行政能力统计
 * 
 * @author Administrator
 *
 */
@Service("hrAdministrationStatisticsService")
public class HrAdministrationStatisticsServiceImpl implements HrAdministrationStatisticsService {
	private static Logger logger = Logger.getLogger(HrAdministrationStatisticsServiceImpl.class);

	@Resource(name = "hrAdministrationStatisticsMapper")
	private final HrAdministrationStatisticsMapper hrAdministrationStatisticsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);


	/**
	 * 行政能力统计查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAdministrationStatistics(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAdministrationStatistics> list = (List<HrAdministrationStatistics>) hrAdministrationStatisticsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAdministrationStatistics> list = (List<HrAdministrationStatistics>) hrAdministrationStatisticsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	
	@Override
	public List<Map<String, Object>> queryAdministrationStatisticsByPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list=ChdJson.toListLower(hrAdministrationStatisticsMapper.queryAdministrationStatisticsByPrint(entityMap));
		return list;
	}
    

}
