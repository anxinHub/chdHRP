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
import com.chd.hrp.hr.dao.scientificresearch.HrPersonalAcademicHonorsMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrPersonalAcademicHonors;
import com.chd.hrp.hr.service.scientificresearch.HrPersonalAcademicHonorsService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术荣誉信息
 * 
 * @author Administrator
 *
 */
@Service("hrPersonalAcademicHonorsService")
public class HrPersonalAcademicHonorsServiceImpl implements HrPersonalAcademicHonorsService {
	private static Logger logger = Logger.getLogger(HrPersonalAcademicHonorsServiceImpl.class);

	@Resource(name = "hrPersonalAcademicHonorsMapper")
	private final HrPersonalAcademicHonorsMapper hrPersonalAcademicHonorsMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术荣誉信息增加
	 */
	@Override
	public String addPersonalAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrPersonalAcademicHonors> list = hrPersonalAcademicHonorsMapper.queryPersonalAcademicHonorsById(entityMap);

		if (list.size() > 0) {

			for (HrPersonalAcademicHonors hrPersonalAcademicHonors : list) {
				/*
				 * if (hrPersonalAcademicHonors.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrPersonalAcademicHonors.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrPersonalAcademicHonorsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉信息修改
	 */
	@Override
	public String updatePersonalAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrPersonalAcademicHonorsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术荣誉信息删除
	 */
	@Override
	public String deletePersonalAcademicHonors(List<HrPersonalAcademicHonors> entityList) throws DataAccessException {

		try {

			hrPersonalAcademicHonorsMapper.deletePersonalAcademicHonors(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 个人学术荣誉信息查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryPersonalAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrPersonalAcademicHonors> list = (List<HrPersonalAcademicHonors>) hrPersonalAcademicHonorsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrPersonalAcademicHonors> list = (List<HrPersonalAcademicHonors>) hrPersonalAcademicHonorsMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrPersonalAcademicHonors queryByCodePersonalAcademicHonors(Map<String, Object> entityMap) throws DataAccessException {
		return hrPersonalAcademicHonorsMapper.queryByCode(entityMap);
	}

	@Override
	public String queryPersonalAcademicHonorsTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrPersonalAcademicHonors> storeTypeList = (List<HrPersonalAcademicHonors>) hrPersonalAcademicHonorsMapper.query(entityMap);
		for (HrPersonalAcademicHonors storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
