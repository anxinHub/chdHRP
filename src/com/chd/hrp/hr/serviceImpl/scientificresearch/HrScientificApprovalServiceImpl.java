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
import com.chd.hrp.hr.dao.scientificresearch.HrScientificApprovalMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificApproval;
import com.chd.hrp.hr.service.scientificresearch.HrScientificApprovalService;
import com.github.pagehelper.PageInfo;

/**
 * 科研项目立项
 * 
 * @author Administrator
 *
 */
@Service("hrScientificApprovalService")
public class HrScientificApprovalServiceImpl implements HrScientificApprovalService {
	private static Logger logger = Logger.getLogger(HrScientificApprovalServiceImpl.class);

	@Resource(name = "hrScientificApprovalMapper")
	private final HrScientificApprovalMapper hrScientificApprovalMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 科研项目立项增加
	 */
	@Override
	public String addScientificApproval(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrScientificApproval> list = hrScientificApprovalMapper.queryScientificApprovalById(entityMap);

		if (list.size() > 0) {

			for (HrScientificApproval hrScientificApproval : list) {
				/*
				 * if (hrScientificApproval.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrScientificApproval.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrScientificApprovalMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研项目立项修改
	 */
	@Override
	public String updateScientificApproval(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrScientificApprovalMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 科研项目立项删除
	 */
	@Override
	public String deleteScientificApproval(List<HrScientificApproval> entityList) throws DataAccessException {

		try {

			hrScientificApprovalMapper.deleteScientificApproval(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 科研项目立项查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryScientificApproval(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrScientificApproval> list = (List<HrScientificApproval>) hrScientificApprovalMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrScientificApproval> list = (List<HrScientificApproval>) hrScientificApprovalMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrScientificApproval queryByCodeScientificApproval(Map<String, Object> entityMap) throws DataAccessException {
		return hrScientificApprovalMapper.queryByCode(entityMap);
	}

	@Override
	public String queryScientificApprovalTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrScientificApproval> storeTypeList = (List<HrScientificApproval>) hrScientificApprovalMapper.query(entityMap);
		for (HrScientificApproval storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
