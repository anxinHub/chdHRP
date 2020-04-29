/** 
 * @Description:
 * @Copyright: Copyright (c) 2017-6-5 下午13:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.budg.serviceImpl.business.fixedassets;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
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
import com.chd.base.util.DateUtil;
import com.chd.hrp.budg.dao.business.fixedassets.BudgAssetPurMapper;
import com.chd.hrp.budg.entity.BudgAssetNow;
import com.chd.hrp.budg.service.business.fixedassets.BudgAssetPurService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 预购固定资产折旧预算
 * @Table: BUDG_ASSET_PUR
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Service("budgAssetPurService")
public class BudgAssetPurServiceImpl implements BudgAssetPurService {

	private static Logger logger = Logger.getLogger(BudgAssetPurServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgAssetPurMapper")
	private final BudgAssetPurMapper budgAssetPurMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgAssetPurMapper.add(entityMap);
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
			budgAssetPurMapper.delete(entityMap);
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
			budgAssetPurMapper.deleteBatch(entityMap);
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

			List<BudgAssetNow> list = (List<BudgAssetNow>) budgAssetPurMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgAssetNow> list = (List<BudgAssetNow>) budgAssetPurMapper.query(entityMap, rowBounds);

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
	public String collectBudgAssetPur(Map<String, Object> entityMap) throws DataAccessException {
		/*
		 * 计算： 1、预算时间范围由查询条件中预算年度决定。 2、只针对该预算年度的购置计划明细中资产性质为05无形资产之外的资产
		 * 3、计算之前，先删除所选预算年度的数据，再插入
		 * 4、对购置计划每一条符合条件的明细（预算年度、资产性质为05）根据希望到货日期，生成折旧预算数据
		 * 到货当月不计提折旧，从下月开始计提。譬如，预算年度为2017年，希望到货日期为2017年3月，则从4月份开始生成折旧，一直到12月份；
		 * 若希望到货日期为2017年12月份，则2017年没有该购置计划明细中资产的折旧。 6)计算月折旧额
		 * 若对应资产字典折旧方法为01，则月折旧额=金额/12*计提年数；否则结束。 其中计提年数同样取自资产字典
		 */
		if (entityMap.get("budg_year") == null) {
			return "{\"error\":\"缺少预算年度参数\"}";
		}
		// 预算年
		String budg_year = (String) entityMap.get("budg_year");
		// 预算月
		String[] monthList = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		// 1、先删除所选预算年度的数据
		try {
			budgAssetPurMapper.delete(entityMap);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		List<Map<String, Object>> collectList = new ArrayList<Map<String, Object>>();
		// 2、获取购置计划，遍历
		List<Map<String, Object>> entityList = null;
		try {
			// 资产性质为05无形资产之外的资产,对应资产字典折旧方法为01
			entityList = budgAssetPurMapper.queryCollectData(entityMap);
		} catch (Exception e) {
			throw new SysException(e);
		}
		if (entityList != null && entityList.size() > 0) {
			for (Map<String, Object> entity : entityList) {
				// 希望到货日期
				Date need_date = DateUtil.stringToDate(entity.get("need_date").toString());
				// 计划金额
				Double price = Double.parseDouble(entity.get("price").toString());
				// 计提年数(折旧年限)
				Integer depre_years = Integer.parseInt(entity.get("depre_years").toString());
				Calendar cal = Calendar.getInstance();
				cal.setTime(need_date);
				Integer need_month = cal.get(Calendar.MONTH) + 1;
				for (int i = 0; i < monthList.length; i++) {
					if (need_month != 12 && i >= need_month) {
						// 月折旧额=金额/12*计提年数
						Double depr_count = price / (12 * depre_years);
						entity.put("depr_count", depr_count);
						entity.put("depr_budg", depr_count);
						entity.put("budg_year", budg_year);
						entity.put("month", monthList[i]);
						// budgAssetPurMapper.add(entity);
						collectList.add(new HashMap<String, Object>(entity));
					}
				}
			}
		} else {
			return "{\"error\":\"无购置计划数据！\"}";
		}
		if (collectList != null && collectList.size() > 0) {
			try {
				budgAssetPurMapper.addBatch(collectList);
			} catch (Exception e) {
				throw new SysException(e.getMessage());
			}
		}
		return "{\"state\":\"true\"}";
	}

}
