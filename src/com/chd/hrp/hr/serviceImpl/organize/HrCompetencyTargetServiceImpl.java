package com.chd.hrp.hr.serviceImpl.organize;

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
import com.chd.hrp.hr.dao.organize.HrCompetencyTargetMapper;
import com.chd.hrp.hr.entity.orangnize.HrCompetencyTarget;
import com.chd.hrp.hr.service.organize.HrCompetencyTargetService;
import com.github.pagehelper.PageInfo;

/**
 * 能力素质指标
 * 
 * @author Administrator
 *
 */
@Service("hrCompetencyTargetService")
public class HrCompetencyTargetServiceImpl implements HrCompetencyTargetService {
	private static Logger logger = Logger.getLogger(HrCompetencyTargetServiceImpl.class);

	@Resource(name = "hrCompetencyTargetMapper")
	private final HrCompetencyTargetMapper hrCompetencyTargetMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 能力素质指标增加
	 */
	@Override
	public String CompetencyTargetAdd(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrCompetencyTarget> list = hrCompetencyTargetMapper.queryCompetencyTargetById(entityMap);

		if (list.size() > 0) {

			for (HrCompetencyTarget hrCompetencyTarget : list) {
				
				  if (hrCompetencyTarget.getIndicator_code().equals(entityMap.get("indicator_code"))) {
				  return "{\"error\":\"编码：" + hrCompetencyTarget.getIndicator_code().toString() +
				  "已存在.\"}"; } if
				  (hrCompetencyTarget.getIndicator_name().equals(entityMap.get("indicator_name"))) {
				  return "{\"error\":\"名称：" + hrCompetencyTarget.getIndicator_name().toString() +
				 "已存在.\"}"; }
				 
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrCompetencyTargetMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 能力素质指标修改
	 */
	@Override
	public String updateCompetencyTarget(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrCompetencyTargetMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 能力素质指标删除
	 */
	@Override
	public String deleteBatchCompetencyTarget(List<HrCompetencyTarget> entityList) throws DataAccessException {

		try {

			hrCompetencyTargetMapper.deleteBatchCompetencyTarget(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 能力素质指标查询
	 */
	@Override
	public String queryHrCompetencyTarget(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrCompetencyTarget> list = (List<HrCompetencyTarget>) hrCompetencyTargetMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrCompetencyTarget> list = (List<HrCompetencyTarget>) hrCompetencyTargetMapper.query(entityMap,
					rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrCompetencyTarget queryByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hrCompetencyTargetMapper.queryByCode(entityMap);
	}

}
