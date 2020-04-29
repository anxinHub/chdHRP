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
import com.chd.hrp.hr.dao.organize.HrCompetencyModelMapper;
import com.chd.hrp.hr.entity.orangnize.HrCompetencyModel;
import com.chd.hrp.hr.service.organize.HrCompetencyModelService;
import com.github.pagehelper.PageInfo;

/**
 * 能力素质模型
 * 
 * @author Administrator
 *
 */
@Service("hrCompetencyModelService")
public class HrCompetencyModelServiceImpl implements HrCompetencyModelService {
	private static Logger logger = Logger.getLogger(HrCompetencyModelServiceImpl.class);

	@Resource(name = "hrCompetencyModelMapper")
	private final HrCompetencyModelMapper hrCompetencyModelMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 能力素质模型增加
	 */
	@Override
	public String CompetencyModelAdd(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrCompetencyModel> list = hrCompetencyModelMapper.queryCompetencyModelById(entityMap);

		if (list.size() > 0) {

			for (HrCompetencyModel hrCompetencyModel : list) {
				/*
				 * if (hrCompetencyModel.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrCompetencyModel.getKind_name().equals(entityMap.get("kind_name"))) {
				 * return "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() +
				 * "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrCompetencyModelMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 能力素质模型修改
	 */
	@Override
	public String updateCompetencyModel(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrCompetencyModelMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 能力素质模型删除
	 */
	@Override
	public String deleteBatchCompetencyModel(List<HrCompetencyModel> entityList) throws DataAccessException {

		try {

			hrCompetencyModelMapper.deleteBatchCompetencyModel(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 能力素质模型查询
	 */
	@Override
	public String queryHrCompetencyModel(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrCompetencyModel> list = (List<HrCompetencyModel>) hrCompetencyModelMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrCompetencyModel> list = (List<HrCompetencyModel>) hrCompetencyModelMapper.query(entityMap,
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
	public HrCompetencyModel queryByCode(Map<String, Object> entityMap) throws DataAccessException {

		return hrCompetencyModelMapper.queryByCode(entityMap);
	}

	@Override
	public String queryCompetencyModelTree(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return null;
	}

}
