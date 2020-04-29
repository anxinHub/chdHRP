package com.chd.hrp.hr.serviceImpl.medicalmanagement;


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
import com.chd.hrp.hr.dao.medicalmanagement.HrTechniqueResultsMapper;
import com.chd.hrp.hr.entity.medicalmanagement.HrTechniqueResults;
import com.chd.hrp.hr.service.medicalmanagement.HrTechniqueResultsService;
import com.github.pagehelper.PageInfo;




/**
 * 技术管理(结果查询)
 * 
 * @author Administrator
 *
 */
@Service("hrTechniqueResultsService")
public class HrTechniqueResultsServiceImpl implements HrTechniqueResultsService {
	private static Logger logger = Logger.getLogger(HrTechniqueResultsServiceImpl.class);

	@Resource(name = "hrTechniqueResultsMapper")
	private final HrTechniqueResultsMapper hrTechniqueResultsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 技术管理(结果查询)增加
	 */
	@Override
	public String addTechniqueResults(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrTechniqueResults> list = hrTechniqueResultsMapper.queryTechniqueResultsById(entityMap);

		if (list.size() > 0) {

			for (HrTechniqueResults hrTechniqueResults : list) {
				/*
				 * if (hrTechniqueResults.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrTechniqueResults.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrTechniqueResultsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 技术管理(结果查询)修改
	 */
	@Override
	public String updateTechniqueResults(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrTechniqueResultsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 技术管理(结果查询)删除
	 */
	@Override
	public String deleteTechniqueResults(List<HrTechniqueResults> entityList) throws DataAccessException {

		try {

			hrTechniqueResultsMapper.deleteTechniqueResults(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 技术管理(结果查询)查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryTechniqueResults(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrTechniqueResults> list = (List<HrTechniqueResults>) hrTechniqueResultsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrTechniqueResults> list = (List<HrTechniqueResults>) hrTechniqueResultsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrTechniqueResults queryByCodeTechniqueResults(Map<String, Object> entityMap) throws DataAccessException {
		return hrTechniqueResultsMapper.queryByCode(entityMap);
	}

	@Override
	public String queryTechniqueResultsTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrTechniqueResults> storeTypeList = (List<HrTechniqueResults>) hrTechniqueResultsMapper.query(entityMap);
		for (HrTechniqueResults storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
