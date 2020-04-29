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
import com.chd.hrp.hr.dao.scientificresearch.HrScientificRewardsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificRewards;
import com.chd.hrp.hr.service.scientificresearch.HrScientificRewardsService;
import com.github.pagehelper.PageInfo;

/**
 * 科研成果奖励
 * 
 * @author Administrator
 *
 */
@Service("hrScientificRewardsService")
public class HrScientificRewardsServiceImpl implements HrScientificRewardsService {
	private static Logger logger = Logger.getLogger(HrScientificRewardsServiceImpl.class);

	@Resource(name = "hrScientificRewardsMapper")
	private final HrScientificRewardsMapper hrScientificRewardsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 科研成果奖励增加
	 */
	@Override
	public String addScientificRewards(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrScientificRewards> list = hrScientificRewardsMapper.queryScientificRewardsById(entityMap);

		if (list.size() > 0) {

			for (HrScientificRewards hrScientificRewards : list) {
				/*
				 * if (hrScientificRewards.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrScientificRewards.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrScientificRewardsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研成果奖励修改
	 */
	@Override
	public String updateScientificRewards(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrScientificRewardsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研成果奖励删除
	 */
	@Override
	public String deleteScientificRewards(List<HrScientificRewards> entityList) throws DataAccessException {

		try {

			hrScientificRewardsMapper.deleteScientificRewards(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科研成果奖励查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryScientificRewards(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrScientificRewards> list = (List<HrScientificRewards>) hrScientificRewardsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrScientificRewards> list = (List<HrScientificRewards>) hrScientificRewardsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrScientificRewards queryByCodeScientificRewards(Map<String, Object> entityMap) throws DataAccessException {
		return hrScientificRewardsMapper.queryByCode(entityMap);
	}

	@Override
	public String queryScientificRewardsTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrScientificRewards> storeTypeList = (List<HrScientificRewards>) hrScientificRewardsMapper.query(entityMap);
		for (HrScientificRewards storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
