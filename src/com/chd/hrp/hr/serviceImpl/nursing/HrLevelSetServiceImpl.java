/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
 
package com.chd.hrp.hr.serviceImpl.nursing;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.nursing.HrLevelSetMapper;
import com.chd.hrp.hr.entity.nursing.HrLevelSet;
import com.chd.hrp.hr.entity.nursing.HrLevelSet;
import com.chd.hrp.hr.service.nursing.HrLevelSetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table:
 * HR_LEVEL_SET
 * @Author: bell
 * @email:  bell@e-tonggroup.com
 * @Version: 1.0
 */
 


@Service("hrLevelSetService")
public class HrLevelSetServiceImpl implements HrLevelSetService {

	private static Logger logger = Logger.getLogger(HrLevelSetServiceImpl.class);
	//引入DAO操作
	@Resource(name = "hrLevelSetMapper")
	private final HrLevelSetMapper hrLevelSetMapper = null;
	@Override
	public String addLevelSet(Map<String, Object> entityMap) throws DataAccessException {

		try {
	
			List<HrLevelSet> alllistVo = JSONArray.parseArray(String.valueOf(entityMap.get("paramVo")),
					HrLevelSet.class);

			/**
			 * 增加
			 */
			if (alllistVo != null && alllistVo.size() > 0) {
				for (HrLevelSet hrAcademicAbility : alllistVo) {
					hrAcademicAbility.setGroup_id(Integer.parseInt(entityMap.get("group_id").toString()));
					hrAcademicAbility.setHos_id(Integer.parseInt(entityMap.get("hos_id").toString()));
				}
			}
			hrLevelSetMapper.deleteLevelSet(alllistVo);
			int state = hrLevelSetMapper.addBatch(alllistVo);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}

	}
	@Override
	public String deleteLevelSet(List<HrLevelSet> entityList) throws DataAccessException {

		try {
			if (entityList != null) {
				hrLevelSetMapper.deleteLevelSet(entityList);
			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException(e.getMessage());

		}
	}
	@Override
	public String queryLevelSet(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrLevelSet> list = (List<HrLevelSet>) hrLevelSetMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrLevelSet> list = (List<HrLevelSet>) hrLevelSetMapper.query(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	@Override
	public String generateLevelSet(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<HrLevelSet> list = (List<HrLevelSet>) hrLevelSetMapper.generateLevelSet(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<HrLevelSet> list = (List<HrLevelSet>) hrLevelSetMapper.generateLevelSet(entityMap, rowBounds);

			@SuppressWarnings("rawtypes")
			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}
	@Override
	public String copyLevelSet(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap != null) {
				entityMap.put("year", SessionManager.getAcctYear());
			}

			List<HrLevelSet> hrLevelSet = hrLevelSetMapper.queryByCodeLevelSet(entityMap);
			if (hrLevelSet.size() > 0) {
				// 先删除
				hrLevelSetMapper.deleteHLevelSet(entityMap);
				int state = hrLevelSetMapper.copyLevelSet(entityMap);
				return "{\"msg\":\"继承成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"上一年度没有数据.\",\"state\":\"false\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
}
