package com.chd.hrp.hr.serviceImpl.nursingtraining;

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
import com.chd.hrp.hr.dao.nursingtraining.HrDissertationMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrDissertation;
import com.chd.hrp.hr.service.nursingtraining.HrDissertationService;
import com.github.pagehelper.PageInfo;

/**
 * 论文发表
 * 
 * @author Administrator
 *
 */
@Service("hrDissertationService")
public class HrDissertationServiceImpl implements HrDissertationService {
	private static Logger logger = Logger.getLogger(HrDissertationServiceImpl.class);

	@Resource(name = "hrDissertationMapper")
	private final HrDissertationMapper hrDissertationMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 论文发表增加
	 */
	@Override
	public String addDissertation(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrDissertation> list = hrDissertationMapper.queryDissertationById(entityMap);

		if (list.size() > 0) {

			for (HrDissertation hrDissertation : list) {
				/*
				 * if (hrDissertation.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrDissertation.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrDissertationMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 论文发表修改
	 */
	@Override
	public String updateDissertation(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrDissertationMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 论文发表删除
	 */
	@Override
	public String deleteDissertation(List<HrDissertation> entityList) throws DataAccessException {

		try {

			hrDissertationMapper.deleteDissertation(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 论文发表查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryDissertation(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrDissertation> list = (List<HrDissertation>) hrDissertationMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrDissertation> list = (List<HrDissertation>) hrDissertationMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrDissertation queryByCodeDissertation(Map<String, Object> entityMap) throws DataAccessException {
		return hrDissertationMapper.queryByCode(entityMap);
	}

	@Override
	public String queryDissertationTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrDissertation> storeTypeList = (List<HrDissertation>) hrDissertationMapper.query(entityMap);
		for (HrDissertation storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
