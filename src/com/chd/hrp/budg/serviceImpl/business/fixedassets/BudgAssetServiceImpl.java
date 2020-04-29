/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.serviceImpl.business.fixedassets;

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
import com.chd.hrp.budg.dao.business.fixedassets.BudgAssetMapper;
import com.chd.hrp.budg.entity.BudgAsset;
import com.chd.hrp.budg.service.business.fixedassets.BudgAssetService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 预购固定资产折旧预算
 * @Table: BUDG_ASSET
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Service("budgAssetService")
public class BudgAssetServiceImpl implements BudgAssetService {

	private static Logger logger = Logger.getLogger(BudgAssetServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgAssetMapper")
	private final BudgAssetMapper budgAssetMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgAssetMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";
		}
	}

	@Override
	public String addBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String updateBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgAssetMapper.delete(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";
		}
	}

	@Override
	public String deleteBatch(List<Map<String, Object>> entityMap) throws DataAccessException {
		try {
			budgAssetMapper.deleteBatch(entityMap);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
			// return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";
		}
	}

	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgAsset> list = (List<BudgAsset>) budgAssetMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgAsset> list = (List<BudgAsset>) budgAssetMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String collectBudgAsset(Map<String, Object> entityMap) throws DataAccessException {
		// 1、清空表数据
		try {
			budgAssetMapper.delete(entityMap);
		} catch (Exception e) {
			e.printStackTrace();
			throw new SysException(e.getMessage());
		}

		// 2、分别汇总现有资产折旧预算和预购资产折旧预算，按资产名称对应的资产分类。资金来源、月份、科室分组汇总。
		List<Map<String, Object>> entityList = null;
		try {
			entityList = budgAssetMapper.queryCollectData(entityMap);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		if (entityList != null && entityList.size() > 0) {
			/*
			 * for (Map<String, Object> entity : entityList) {
			 * budgAssetMapper.add(entity); }
			 */
			budgAssetMapper.addBatch(entityList);
		}
		return "{\"state\":\"true\"}";
	}

}
