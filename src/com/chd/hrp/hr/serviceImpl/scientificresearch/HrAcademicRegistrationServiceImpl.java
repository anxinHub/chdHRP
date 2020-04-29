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
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicRegistrationMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicRegistration;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicRegistrationService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术荣誉登记
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicRegistrationService")
public class HrAcademicRegistrationServiceImpl implements HrAcademicRegistrationService {
	private static Logger logger = Logger.getLogger(HrAcademicRegistrationServiceImpl.class);

	@Resource(name = "hrAcademicRegistrationMapper")
	private final HrAcademicRegistrationMapper hrAcademicRegistrationMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术荣誉登记增加
	 */
	@Override
	public String addAcademicRegistration(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrAcademicRegistration> list = hrAcademicRegistrationMapper.queryAcademicRegistrationById(entityMap);

		if (list.size() > 0) {

			for (HrAcademicRegistration hrAcademicRegistration : list) {
				/*
				 * if (hrAcademicRegistration.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrAcademicRegistration.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrAcademicRegistrationMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉登记修改
	 */
	@Override
	public String updateAcademicRegistration(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrAcademicRegistrationMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉登记删除
	 */
	@Override
	public String deleteAcademicRegistration(List<HrAcademicRegistration> entityList) throws DataAccessException {

		try {

			hrAcademicRegistrationMapper.deleteAcademicRegistration(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 个人学术荣誉登记查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicRegistration(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicRegistration> list = (List<HrAcademicRegistration>) hrAcademicRegistrationMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicRegistration> list = (List<HrAcademicRegistration>) hrAcademicRegistrationMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrAcademicRegistration queryByCodeAcademicRegistration(Map<String, Object> entityMap) throws DataAccessException {
		return hrAcademicRegistrationMapper.queryByCode(entityMap);
	}

	@Override
	public String queryAcademicRegistrationTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrAcademicRegistration> storeTypeList = (List<HrAcademicRegistration>) hrAcademicRegistrationMapper.query(entityMap);
		for (HrAcademicRegistration storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
