package com.chd.hrp.hr.serviceImpl.organize;

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
import com.chd.hrp.hr.dao.organize.HrStationSalaryMapper;
import com.chd.hrp.hr.entity.orangnize.HosStation;
import com.chd.hrp.hr.entity.orangnize.HrStationSalary;
import com.chd.hrp.hr.service.organize.HrStationSalaryService;
import com.github.pagehelper.PageInfo;

/**
 * 岗位设立
 * @author Administrator
 *
 */
@Service("hrStationSalaryService")
public class HrStationSalaryServiceImpl implements HrStationSalaryService{
	private static Logger logger = Logger.getLogger(HrStationSalaryServiceImpl.class);

	@Resource(name = "hrStationSalaryMapper")
	private final HrStationSalaryMapper hrStationSalaryMapper = null;

	// 默认显示20条数据
	RowBounds rowBoundsALL = new RowBounds(0, 20);

	/**
	 * 岗位定义增加
	 */
	@Override
	public String addStationSalary(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象
		List<HrStationSalary> list = hrStationSalaryMapper.queryStationSalaryById(entityMap);

		if (list.size() > 0) {

			for (HrStationSalary hrStationSalary : list) {
				/*
				 * if (hrStationSalary.getKind_code().equals(entityMap.get("kind_code"))) {
				 * return "{\"error\":\"编码：" + hrDutyLevel.getKind_code().toString() +
				 * "已存在.\"}"; } if
				 * (hrStationSalary.getKind_name().equals(entityMap.get("kind_name"))) { return
				 * "{\"error\":\"名称：" + hrDutyLevel.getKind_name().toString() + "已存在.\"}"; }
				 */
			}
		}
		try {

			@SuppressWarnings("unused")
			int state = hrStationSalaryMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 岗位定义修改
	 */
	@Override
	public String updateStationSalary(Map<String, Object> entityMap) throws DataAccessException {

		try {

			@SuppressWarnings("unused")
			int state = hrStationSalaryMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}

	/**
	 * 岗位定义删除
	 */
	@Override
	public String deleteBatchStationSalary(List<HrStationSalary> entityList) throws DataAccessException {

		try {

			hrStationSalaryMapper.deleteBatchStationSalary(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}

	/**
	 * 岗位定义查询
	 */
	@Override
	public String queryHrStationSalary(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrStationSalary> list = (List<HrStationSalary>) hrStationSalaryMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrStationSalary> list = (List<HrStationSalary>) hrStationSalaryMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * 跳转修改页面
	 */
	@Override
	public HrStationSalary queryByCodeStationSalary(Map<String, Object> entityMap) throws DataAccessException {

		return hrStationSalaryMapper.queryByCode(entityMap);
	}



	@Override
	public String queryStationSalaryDefTree(Map<String, Object>entityMap) throws DataAccessException {

		StringBuilder treeJson = new StringBuilder();

		treeJson.append("[");
		List<HrStationSalary> storeTypeList = (List<HrStationSalary>) hrStationSalaryMapper.query(entityMap);
		for (HrStationSalary storeType : storeTypeList) {
		/*	treeJson.append(
					"{'id':'" + storeType.getKind_code() + "', 'pId':'0', 'name':'" + storeType.getKind_name() + "'},");

		*/}
		treeJson.append("]");
		return treeJson.toString();

	}


	

}
