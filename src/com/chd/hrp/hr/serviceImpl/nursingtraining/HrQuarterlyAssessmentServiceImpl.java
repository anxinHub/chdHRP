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
import com.chd.hrp.hr.dao.nursingtraining.HrQuarterlyAssessmentMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrQuarterlyAssessment;
import com.chd.hrp.hr.service.nursingtraining.HrQuarterlyAssessmentService;
import com.github.pagehelper.PageInfo;

/**
 * 季度护士长考核
 * 
 * @author Administrator
 *
 */
@Service("hrQuarterlyAssessmentService")
public class HrQuarterlyAssessmentServiceImpl implements HrQuarterlyAssessmentService {
	private static Logger logger = Logger.getLogger(HrQuarterlyAssessmentServiceImpl.class);

	@Resource(name = "hrQuarterlyAssessmentMapper")
	private final HrQuarterlyAssessmentMapper hrQuarterlyAssessmentMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 季度护士长考核增加
	 */
	@Override
	public String addQuarterlyAssessment(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrQuarterlyAssessment> list = hrQuarterlyAssessmentMapper.queryQuarterlyAssessmentById(entityMap);

		if (list.size() > 0) {

			for (HrQuarterlyAssessment hrQuarterlyAssessment : list) {
				/*
				 * if (hrQuarterlyAssessment.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrQuarterlyAssessment.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrQuarterlyAssessmentMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 季度护士长考核修改
	 */
	@Override
	public String updateQuarterlyAssessment(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrQuarterlyAssessmentMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 季度护士长考核删除
	 */
	@Override
	public String deleteQuarterlyAssessment(List<HrQuarterlyAssessment> entityList) throws DataAccessException {

		try {

			hrQuarterlyAssessmentMapper.deleteQuarterlyAssessment(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 季度护士长考核查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryQuarterlyAssessment(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrQuarterlyAssessment> list = (List<HrQuarterlyAssessment>) hrQuarterlyAssessmentMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrQuarterlyAssessment> list = (List<HrQuarterlyAssessment>) hrQuarterlyAssessmentMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrQuarterlyAssessment queryByCodeQuarterlyAssessment(Map<String, Object> entityMap) throws DataAccessException {
		return hrQuarterlyAssessmentMapper.queryByCode(entityMap);
	}

	@Override
	public String queryQuarterlyAssessmentTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrQuarterlyAssessment> storeTypeList = (List<HrQuarterlyAssessment>) hrQuarterlyAssessmentMapper.query(entityMap);
		for (HrQuarterlyAssessment storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
