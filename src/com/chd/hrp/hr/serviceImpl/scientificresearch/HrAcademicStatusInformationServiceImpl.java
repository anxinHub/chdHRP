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
import com.chd.hrp.hr.dao.scientificresearch.HrAcademicStatusInformationMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrAcademicStatusInformation;
import com.chd.hrp.hr.service.scientificresearch.HrAcademicStatusInformationService;
import com.github.pagehelper.PageInfo;

/**
 * 个人学术地位信息
 * 
 * @author Administrator
 *
 */
@Service("hrAcademicStatusInformationService")
public class HrAcademicStatusInformationServiceImpl implements HrAcademicStatusInformationService {
	private static Logger logger = Logger.getLogger(HrAcademicStatusInformationServiceImpl.class);

	@Resource(name = "hrAcademicStatusInformationMapper")
	private final HrAcademicStatusInformationMapper hrAcademicStatusInformationMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 个人学术地位信息增加
	 */
	@Override
	public String addAcademicStatusInformation(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrAcademicStatusInformation> list = hrAcademicStatusInformationMapper.queryAcademicStatusInformationById(entityMap);

		if (list.size() > 0) {

			for (HrAcademicStatusInformation hrAcademicStatusInformation : list) {
				/*
				 * if (hrAcademicStatusInformation.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrAcademicStatusInformation.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrAcademicStatusInformationMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术地位信息修改
	 */
	@Override
	public String updateAcademicStatusInformation(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrAcademicStatusInformationMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 个人学术地位信息删除
	 */
	@Override
	public String deleteAcademicStatusInformation(List<HrAcademicStatusInformation> entityList) throws DataAccessException {

		try {

			hrAcademicStatusInformationMapper.deleteAcademicStatusInformation(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 个人学术地位信息查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAcademicStatusInformation(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAcademicStatusInformation> list = (List<HrAcademicStatusInformation>) hrAcademicStatusInformationMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAcademicStatusInformation> list = (List<HrAcademicStatusInformation>) hrAcademicStatusInformationMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrAcademicStatusInformation queryByCodeAcademicStatusInformation(Map<String, Object> entityMap) throws DataAccessException {
		return hrAcademicStatusInformationMapper.queryByCode(entityMap);
	}

	@Override
	public String queryAcademicStatusInformationTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrAcademicStatusInformation> storeTypeList = (List<HrAcademicStatusInformation>) hrAcademicStatusInformationMapper.query(entityMap);
		for (HrAcademicStatusInformation storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
