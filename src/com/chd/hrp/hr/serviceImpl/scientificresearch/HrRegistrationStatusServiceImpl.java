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
import com.chd.hrp.hr.dao.scientificresearch.HrRegistrationStatusMapper;
import com.chd.hrp.hr.entity.scientificresearch.HrRegistrationStatus;
import com.chd.hrp.hr.service.scientificresearch.HrRegistrationStatusService;
import com.github.pagehelper.PageInfo;

/**
 *个人学术地位登记
 * 
 * @author Administrator
 *
 */
@Service("hrRegistrationStatusService")
public class HrRegistrationStatusServiceImpl implements HrRegistrationStatusService {
	private static Logger logger = Logger.getLogger(HrRegistrationStatusServiceImpl.class);

	@Resource(name = "hrRegistrationStatusMapper")
	private final HrRegistrationStatusMapper hrRegistrationStatusMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 *个人学术地位登记增加
	 */
	@Override
	public String addRegistrationStatus(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrRegistrationStatus> list = hrRegistrationStatusMapper.queryRegistrationStatusById(entityMap);

		if (list.size() > 0) {

			for (HrRegistrationStatus hrRegistrationStatus : list) {
				/*
				 * if (hrRegistrationStatus.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrRegistrationStatus.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrRegistrationStatusMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 *个人学术地位登记修改
	 */
	@Override
	public String updateRegistrationStatus(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrRegistrationStatusMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 *个人学术地位登记删除
	 */
	@Override
	public String deleteRegistrationStatus(List<HrRegistrationStatus> entityList) throws DataAccessException {

		try {

			hrRegistrationStatusMapper.deleteRegistrationStatus(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 *个人学术地位登记查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryRegistrationStatus(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrRegistrationStatus> list = (List<HrRegistrationStatus>) hrRegistrationStatusMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrRegistrationStatus> list = (List<HrRegistrationStatus>) hrRegistrationStatusMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrRegistrationStatus queryByCodeRegistrationStatus(Map<String, Object> entityMap) throws DataAccessException {
		return hrRegistrationStatusMapper.queryByCode(entityMap);
	}

	@Override
	public String queryRegistrationStatusTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrRegistrationStatus> storeTypeList = (List<HrRegistrationStatus>) hrRegistrationStatusMapper.query(entityMap);
		for (HrRegistrationStatus storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
