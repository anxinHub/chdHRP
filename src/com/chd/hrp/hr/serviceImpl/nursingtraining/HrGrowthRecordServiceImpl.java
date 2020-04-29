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
import com.chd.hrp.hr.dao.nursingtraining.HrGrowthRecordMapper;
import com.chd.hrp.hr.entity.nursingtraining.HrGrowthRecord;
import com.chd.hrp.hr.service.nursingtraining.HrGrowthRecordService;
import com.github.pagehelper.PageInfo;

/**
 * 成长记录
 * 
 * @author Administrator
 *
 */
@Service("hrGrowthRecordService")
public class HrGrowthRecordServiceImpl implements HrGrowthRecordService {
	private static Logger logger = Logger.getLogger(HrGrowthRecordServiceImpl.class);

	@Resource(name = "hrGrowthRecordMapper")
	private final HrGrowthRecordMapper hrGrowthRecordMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 成长记录增加
	 */
	@Override
	public String addGrowthRecord(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrGrowthRecord> list = hrGrowthRecordMapper.queryGrowthRecordById(entityMap);

		if (list.size() > 0) {

			for (HrGrowthRecord hrGrowthRecord : list) {
				/*
				 * if (hrGrowthRecord.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrGrowthRecord.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrGrowthRecordMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 成长记录修改
	 */
	@Override
	public String updateGrowthRecord(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrGrowthRecordMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 成长记录删除
	 */
	@Override
	public String deleteGrowthRecord(List<HrGrowthRecord> entityList) throws DataAccessException {

		try {

			hrGrowthRecordMapper.deleteGrowthRecord(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 成长记录查询
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String queryGrowthRecord(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrGrowthRecord> list = (List<HrGrowthRecord>) hrGrowthRecordMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrGrowthRecord> list = (List<HrGrowthRecord>) hrGrowthRecordMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrGrowthRecord queryByCodeGrowthRecord(Map<String, Object> entityMap) throws DataAccessException {
		return hrGrowthRecordMapper.queryByCode(entityMap);
	}

	@Override
	public String queryGrowthRecordTree(Map<String, Object> entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrGrowthRecord> storeTypeList = (List<HrGrowthRecord>) hrGrowthRecordMapper.query(entityMap);
		for (HrGrowthRecord storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");
*/
		}
		treeJson.append("]");
		return treeJson.toString();
	}
    

}
