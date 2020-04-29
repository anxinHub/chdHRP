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
import com.chd.hrp.hr.dao.scientificresearch.HrScientificRegisterMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrScientificRegister;
import com.chd.hrp.hr.service.scientificresearch.HrScientificRegisterService;
import com.github.pagehelper.PageInfo;

/**
 * 专利登记
 * 
 * @author Administrator
 *
 */
@Service("hrScientificRegisterService")
public class HrScientificRegisterServiceImpl implements HrScientificRegisterService {
	private static Logger logger = Logger.getLogger(HrScientificRegisterServiceImpl.class);

	@Resource(name = "hrScientificRegisterMapper")
	private final HrScientificRegisterMapper hrScientificRegisterMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 专利登记增加
	 */
	@Override
	public String addScientificRegister(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrScientificRegister> list = hrScientificRegisterMapper.queryScientificRegisterById(entityMap);

		if (list.size() > 0) {

			for (HrScientificRegister hrScientificRegister : list) {
				/*
				 * if (hrScientificRegister.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrScientificRegister.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrScientificRegisterMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 专利登记修改
	 */
	@Override
	public String updateScientificRegister(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrScientificRegisterMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 专利登记删除
	 */
	@Override
	public String deleteScientificRegister(List<HrScientificRegister> entityList) throws DataAccessException {

		try {

			hrScientificRegisterMapper.deleteScientificRegister(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 专利登记查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryScientificRegister(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrScientificRegister> list = (List<HrScientificRegister>) hrScientificRegisterMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrScientificRegister> list = (List<HrScientificRegister>) hrScientificRegisterMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrScientificRegister queryByCodeScientificRegister(Map<String, Object> entityMap) throws DataAccessException {
		return hrScientificRegisterMapper.queryByCode(entityMap);
	}

	@Override
	public String queryScientificRegisterTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrScientificRegister> storeTypeList = (List<HrScientificRegister>) hrScientificRegisterMapper.query(entityMap);
		for (HrScientificRegister storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
