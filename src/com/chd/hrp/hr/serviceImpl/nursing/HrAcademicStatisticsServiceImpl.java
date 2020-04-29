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
import com.chd.hrp.hr.dao.nursing.HrAcademicStatisticsMapper;
import com.chd.hrp.hr.entity.nursing.HrAcademicStatistics;
import com.chd.hrp.hr.service.nursing.HrAcademicStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * 学术能力统计
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicStatisticsService")
public class HrAcademicStatisticsServiceImpl implements HrAcademicStatisticsService {
	private static Logger logger = Logger.getLogger(HrAcademicStatisticsServiceImpl.class);

	@Resource(name = "hrAcademicStatisticsMapper")
	private final HrAcademicStatisticsMapper hrAcademicStatisticsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);


	/**
	 * 学术能力统计查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicStatistics(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicStatistics> list = (List<HrAcademicStatistics>) hrAcademicStatisticsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicStatistics> list = (List<HrAcademicStatistics>) hrAcademicStatisticsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	/**
	 * 打印
	 */
	@Override
	public List<Map<String,Object>> queryAcademicStatisticsPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		List<Map<String,Object>> list = ChdJson.toListLower(hrAcademicStatisticsMapper.queryByPrint(entityMap));
		return list;

	}
}
