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
import com.chd.hrp.hr.dao.nursingtraining.HrHospitalTSMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrHospitalTS;
import com.chd.hrp.hr.service.nursingtraining.HrHospitalTSService;
import com.github.pagehelper.PageInfo;

/**
 * 院内学习记录
 * 
 * @author Administrator
 *
 */
@Service("hrHospitalTSService")
public class HrHospitalTSServiceImpl implements HrHospitalTSService {
	private static Logger logger = Logger.getLogger(HrHospitalTSServiceImpl.class);

	@Resource(name = "hrHospitalTSMapper")
	private final HrHospitalTSMapper hrHospitalTSMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 院内学习记录增加
	 */
	@Override
	public String addHospitalTS(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrHospitalTS> list = hrHospitalTSMapper.queryHospitalTSById(entityMap);

		if (list.size() > 0) {

			for (HrHospitalTS hrHospitalTS : list) {
				/*
				 * if (hrHospitalTS.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrHospitalTS.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrHospitalTSMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 院内学习记录修改
	 */
	@Override
	public String updateHospitalTS(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrHospitalTSMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 院内学习记录删除
	 */
	@Override
	public String deleteHospitalTS(List<HrHospitalTS> entityList) throws DataAccessException {

		try {

			hrHospitalTSMapper.deleteHospitalTS(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 院内学习记录查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryHospitalTS(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrHospitalTS> list = (List<HrHospitalTS>) hrHospitalTSMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrHospitalTS> list = (List<HrHospitalTS>) hrHospitalTSMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrHospitalTS queryByCodeHospitalTS(Map<String, Object> entityMap) throws DataAccessException {
		return hrHospitalTSMapper.queryByCode(entityMap);
	}

	@Override
	public String queryHospitalTSTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrHospitalTS> storeTypeList = (List<HrHospitalTS>) hrHospitalTSMapper.query(entityMap);
		for (HrHospitalTS storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
