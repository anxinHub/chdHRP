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
import com.chd.hrp.hr.dao.nursing.HrTeachingStatisticsMapper;
import com.chd.hrp.hr.entity.nursing.HrTeachingStatistics;
import com.chd.hrp.hr.service.nursing.HrTeachingStatisticsService;
import com.github.pagehelper.PageInfo;

/**
 * 教学能力统计
 * 
 * @author Administrator
 *
 */
@Service("hrTeachingStatisticsService")
public class HrTeachingStatisticsServiceImpl implements HrTeachingStatisticsService {
	private static Logger logger = Logger.getLogger(HrTeachingStatisticsServiceImpl.class);

	@Resource(name = "hrTeachingStatisticsMapper")
	private final HrTeachingStatisticsMapper hrTeachingStatisticsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 教学能力统计增加
	 */
	@Override
	public String addTeachingStatistics(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrTeachingStatistics> list = hrTeachingStatisticsMapper.queryTeachingStatisticsById(entityMap);

		if (list.size() > 0) {

			for (HrTeachingStatistics hrTeachingStatistics : list) {
				/*
				 * if (hrTeachingStatistics.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrTeachingStatistics.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrTeachingStatisticsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 教学能力统计修改
	 */
	@Override
	public String updateTeachingStatistics(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrTeachingStatisticsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 教学能力统计删除
	 */
	@Override
	public String deleteTeachingStatistics(List<HrTeachingStatistics> entityList) throws DataAccessException {

		try {

			hrTeachingStatisticsMapper.deleteTeachingStatistics(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 教学能力统计查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryTeachingStatistics(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrTeachingStatistics> list = (List<HrTeachingStatistics>) hrTeachingStatisticsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrTeachingStatistics> list = (List<HrTeachingStatistics>) hrTeachingStatisticsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrTeachingStatistics queryByCodeTeachingStatistics(Map<String, Object> entityMap) throws DataAccessException {
		return hrTeachingStatisticsMapper.queryByCode(entityMap);
	}
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryTeachingStatisticsByPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = ChdJson.toListLower(hrTeachingStatisticsMapper.queryTeachingStatisticsByPrint(entityMap));
		return list;
	}



}
