package com.chd.hrp.hr.serviceImpl.scientificresearch;

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
import com.chd.hrp.hr.dao.scientificresearch.HrResearchProjectsResultsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrResearchProjectsResults;
import com.chd.hrp.hr.service.scientificresearch.HrResearchProjectsResultsService;
import com.github.pagehelper.PageInfo;

/**
 * 科研项目与成果信息
 * 
 * @author Administrator
 *
 */
@Service("hrResearchProjectsResultsService")
public class HrResearchProjectsResultsServiceImpl implements HrResearchProjectsResultsService {
	private static Logger logger = Logger.getLogger(HrResearchProjectsResultsServiceImpl.class);

	@Resource(name = "hrResearchProjectsResultsMapper")
	private final HrResearchProjectsResultsMapper hrResearchProjectsResultsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 科研项目与成果信息增加
	 */
	@Override
	public String addResearchProjectsResults(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrResearchProjectsResults> list = hrResearchProjectsResultsMapper.queryResearchProjectsResultsById(entityMap);

		if (list.size() > 0) {

			for (HrResearchProjectsResults hrResearchProjectsResults : list) {
				/*
				 * if (hrResearchProjectsResults.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrResearchProjectsResults.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrResearchProjectsResultsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研项目与成果信息修改
	 */
	@Override
	public String updateResearchProjectsResults(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrResearchProjectsResultsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研项目与成果信息删除
	 */
	@Override
	public String deleteResearchProjectsResults(List<HrResearchProjectsResults> entityList) throws DataAccessException {

		try {

			hrResearchProjectsResultsMapper.deleteResearchProjectsResults(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科研项目与成果信息查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryResearchProjectsResults(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrResearchProjectsResults> list = (List<HrResearchProjectsResults>) hrResearchProjectsResultsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrResearchProjectsResults> list = (List<HrResearchProjectsResults>) hrResearchProjectsResultsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrResearchProjectsResults queryByCodeResearchProjectsResults(Map<String, Object> entityMap) throws DataAccessException {
		return hrResearchProjectsResultsMapper.queryByCode(entityMap);
	}

	@Override
	public String queryResearchProjectsResultsTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrResearchProjectsResults> storeTypeList = (List<HrResearchProjectsResults>) hrResearchProjectsResultsMapper.query(entityMap);
		for (HrResearchProjectsResults storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
