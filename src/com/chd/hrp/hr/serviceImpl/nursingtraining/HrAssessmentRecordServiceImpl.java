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
import com.chd.hrp.hr.dao.nursingtraining.HrAssessmentRecordMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrAssessmentRecord;
import com.chd.hrp.hr.service.nursingtraining.HrAssessmentRecordService;
import com.github.pagehelper.PageInfo;

/**
 * 考核记录
 * 
 * @author Administrator
 *
 */
@Service("hrAssessmentRecordService")
public class HrAssessmentRecordServiceImpl implements HrAssessmentRecordService {
	private static Logger logger = Logger.getLogger(HrAssessmentRecordServiceImpl.class);

	@Resource(name = "hrAssessmentRecordMapper")
	private final HrAssessmentRecordMapper hrAssessmentRecordMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 考核记录增加
	 */
	@Override
	public String addAssessmentRecord(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrAssessmentRecord> list = hrAssessmentRecordMapper.queryAssessmentRecordById(entityMap);

		if (list.size() > 0) {

			for (HrAssessmentRecord hrAssessmentRecord : list) {
				/*
				 * if (hrAssessmentRecord.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrAssessmentRecord.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrAssessmentRecordMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 考核记录修改
	 */
	@Override
	public String updateAssessmentRecord(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrAssessmentRecordMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 考核记录删除
	 */
	@Override
	public String deleteAssessmentRecord(List<HrAssessmentRecord> entityList) throws DataAccessException {

		try {

			hrAssessmentRecordMapper.deleteAssessmentRecord(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 考核记录查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryAssessmentRecord(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrAssessmentRecord> list = (List<HrAssessmentRecord>) hrAssessmentRecordMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrAssessmentRecord> list = (List<HrAssessmentRecord>) hrAssessmentRecordMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrAssessmentRecord queryByCodeAssessmentRecord(Map<String, Object> entityMap) throws DataAccessException {
		return hrAssessmentRecordMapper.queryByCode(entityMap);
	}

	@Override
	public String queryAssessmentRecordTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrAssessmentRecord> storeTypeList = (List<HrAssessmentRecord>) hrAssessmentRecordMapper.query(entityMap);
		for (HrAssessmentRecord storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
