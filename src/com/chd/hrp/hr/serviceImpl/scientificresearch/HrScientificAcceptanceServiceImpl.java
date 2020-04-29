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
import com.chd.hrp.hr.dao.scientificresearch.HrScientificAcceptanceMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificAcceptance;
import com.chd.hrp.hr.service.scientificresearch.HrScientificAcceptanceService;
import com.github.pagehelper.PageInfo;

/**
 * 科研项目验收
 * 
 * @author Administrator
 *
 */
@Service("hrScientificAcceptanceService")
public class HrScientificAcceptanceServiceImpl implements HrScientificAcceptanceService {
	private static Logger logger = Logger.getLogger(HrScientificAcceptanceServiceImpl.class);

	@Resource(name = "hrScientificAcceptanceMapper")
	private final HrScientificAcceptanceMapper hrScientificAcceptanceMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 科研项目验收增加
	 */
	@Override
	public String addScientificAcceptance(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrScientificAcceptance> list = hrScientificAcceptanceMapper.queryScientificAcceptanceById(entityMap);

		if (list.size() > 0) {

			for (HrScientificAcceptance hrScientificAcceptance : list) {
				/*
				 * if (hrScientificAcceptance.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrScientificAcceptance.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrScientificAcceptanceMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研项目验收修改
	 */
	@Override
	public String updateScientificAcceptance(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrScientificAcceptanceMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研项目验收删除
	 */
	@Override
	public String deleteScientificAcceptance(List<HrScientificAcceptance> entityList) throws DataAccessException {

		try {

			hrScientificAcceptanceMapper.deleteScientificAcceptance(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科研项目验收查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryScientificAcceptance(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrScientificAcceptance> list = (List<HrScientificAcceptance>) hrScientificAcceptanceMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrScientificAcceptance> list = (List<HrScientificAcceptance>) hrScientificAcceptanceMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrScientificAcceptance queryByCodeScientificAcceptance(Map<String, Object> entityMap) throws DataAccessException {
		return hrScientificAcceptanceMapper.queryByCode(entityMap);
	}

	@Override
	public String queryScientificAcceptanceTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrScientificAcceptance> storeTypeList = (List<HrScientificAcceptance>) hrScientificAcceptanceMapper.query(entityMap);
		for (HrScientificAcceptance storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
