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
import com.chd.hrp.hr.dao.nursingtraining.HrHospitalOSMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrHospitalOS;
import com.chd.hrp.hr.service.nursingtraining.HrHospitalOSService;
import com.github.pagehelper.PageInfo;

/**
 * 院外学习记录
 * 
 * @author Administrator
 *
 */
@Service("hrHospitalOSService")
public class HrHospitalOSServiceImpl implements HrHospitalOSService {
	private static Logger logger = Logger.getLogger(HrHospitalOSServiceImpl.class);

	@Resource(name = "hrHospitalOSMapper")
	private final HrHospitalOSMapper hrHospitalOSMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 院外学习记录增加
	 */
	@Override
	public String addHospitalOS(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrHospitalOS> list = hrHospitalOSMapper.queryHospitalOSById(entityMap);

		if (list.size() > 0) {

			for (HrHospitalOS hrHospitalOS : list) {
				/*
				 * if (hrHospitalOS.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrHospitalOS.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrHospitalOSMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 院外学习记录修改
	 */
	@Override
	public String updateHospitalOS(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrHospitalOSMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 院外学习记录删除
	 */
	@Override
	public String deleteHospitalOS(List<HrHospitalOS> entityList) throws DataAccessException {

		try {

			hrHospitalOSMapper.deleteHospitalOS(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 院外学习记录查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryHospitalOS(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrHospitalOS> list = (List<HrHospitalOS>) hrHospitalOSMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrHospitalOS> list = (List<HrHospitalOS>) hrHospitalOSMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrHospitalOS queryByCodeHospitalOS(Map<String, Object> entityMap) throws DataAccessException {
		return hrHospitalOSMapper.queryByCode(entityMap);
	}

	@Override
	public String queryHospitalOSTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrHospitalOS> storeTypeList = (List<HrHospitalOS>) hrHospitalOSMapper.query(entityMap);
		for (HrHospitalOS storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
