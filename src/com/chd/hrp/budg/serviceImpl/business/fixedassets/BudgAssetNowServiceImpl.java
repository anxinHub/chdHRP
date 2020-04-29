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
import com.chd.hrp.budg.dao.business.fixedassets.BudgAssetNowMapper;
import com.chd.hrp.budg.entity.BudgAssetNow;
import com.chd.hrp.budg.service.business.fixedassets.BudgAssetNowService;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description: 现有固定资产折旧预算
 * @Table: BUDG_ASSET_NOW
 * @Author: silent
 * @email: silent@e-tonggroup.com
 * @Version: 1.0
 */
@Service("budgAssetNowService")
public class BudgAssetNowServiceImpl implements BudgAssetNowService {

	private static Logger logger = Logger.getLogger(BudgAssetNowServiceImpl.class);
	// 引入DAO操作
	@Resource(name = "budgAssetNowMapper")
	private final BudgAssetNowMapper budgAssetNowMapper = null;

	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		try {
			budgAssetNowMapper.add(entityMap);
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
			budgAssetNowMapper.delete(entityMap);
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
			budgAssetNowMapper.deleteBatch(entityMap);
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
			List<BudgAssetNow> list = (List<BudgAssetNow>) budgAssetNowMapper.query(entityMap);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<BudgAssetNow> list = (List<BudgAssetNow>) budgAssetNowMapper.query(entityMap, rowBounds);
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
	public List<Map<String, Object>> queryBudgAssCard(Map<String, Object> entityMap) throws DataAccessException {
		return budgAssetNowMapper.queryBudgAssCard(entityMap);
	}

	@Override
	public String collectBudgAssetNow(Map<String, Object> entityMap) throws DataAccessException {

		// 预算年度不能为空
		if (entityMap.get("budg_year") == null) {
			return "{\"error\":\"缺少预算年度参数\"}";
		}
		// 预算年
		String budg_year = (String) entityMap.get("budg_year");
		// 预算月
		String[] monthList = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12" };
		// 1、先删除所选预算年度的数据
		try {
			budgAssetNowMapper.delete(entityMap);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}

		List<Map<String, Object>> collectList = new ArrayList<Map<String, Object>>();
		// 2、获取所有卡片BUDG_ASS_CARD，遍历
		List<Map<String, Object>> entityList = null;
		try {
			entityList = budgAssetNowMapper.queryBudgAssCard(entityMap);
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		if (entityList != null && entityList.size() > 0) {
			for (Map<String, Object> entity : entityList) {
				if (entity.get("is_depr") != null) {
					// 是否折旧0否1是
					String is_depr = String.valueOf(entity.get("is_depr"));
					// 是否折旧？若否，结束；若是，继续；
					if ("0".equals(is_depr)) {
						continue;
					}
				}
				if (entity.get("use_state") != null) {
					// 若使用状态
					String use_state = String.valueOf(entity.get("use_state"));
					// 若使用状态为6待处置或已处置，则不计提两个月的折旧，结束；否则继续；
					if ("6".equals(use_state) || "7".equals(use_state)) {
						continue;
					}
				}
				for (String budg_month : monthList) {
					if (entity.get("add_depre_month") != null && entity.get("last_depr_year") != null
							&& entity.get("last_depr_month") != null && entity.get("acc_depre_amount") != null) {
						// 若 【 累计折旧月份+（预算年月-上次折旧年月）】>12*计提年数?若是，结束；若否，计算月折旧额
						int add_depre_month = Integer.parseInt(String.valueOf(entity.get("add_depre_month")));// 累计折旧月份
						String last_depr_year = String.valueOf(entity.get("last_depr_year"));// 上次折旧年
						String last_depr_month = String.valueOf(entity.get("last_depr_month"));// 上次折旧月
						int acc_depre_amount = Integer.parseInt(String.valueOf(entity.get("acc_depre_amount")));// 计提年数
						Date budg_year_month = DateUtil.stringToDate(budg_year + budg_month, "yyyyMM");// 预算年月
						Date last_depr_year_month = DateUtil.stringToDate(last_depr_year + last_depr_month, "yyyyMM");// 上次折扣年月

						if ((add_depre_month + (getMonthSpace(budg_year_month, last_depr_year_month))) > (12
								* acc_depre_amount)) {
							continue;
						}

					}
					if (entity.get("depr_method") != null) {
						// 若折旧方法=01平均年限法，月折旧额=（原值-残值-累计折旧）/(12*计提年数-累计折旧月份）；否则结束。
						String depr_method = (String) entity.get("depr_method");// 若折旧方法
						if (depr_method.equals("01")) {
							if (entity.get("price") != null && entity.get("fore_money") != null
									&& entity.get("depre_money") != null && entity.get("acc_depre_amount") != null
									&& entity.get("add_depre_month") != null) {
								double price = Double.parseDouble(String.valueOf(entity.get("price")));// 原值
								double fore_money = Double.parseDouble(String.valueOf(entity.get("fore_money")));// 残值
								double depre_money = Double.parseDouble(String.valueOf(entity.get("depre_money")));// 累计折旧
								int acc_depre_amount = Integer.parseInt(String.valueOf(entity.get("acc_depre_amount")));// 计提年数
								int add_depre_month = Integer.parseInt(String.valueOf(entity.get("add_depre_month")));// 累计折旧月份
								double depr_count = (price - fore_money - depre_money)
										/ (12 * acc_depre_amount - add_depre_month);
								// 插入
								// entity.put("source_id",
								// entity.get("source_id")==null?"":entity.get("source_id"));
								entity.put("budg_year", budg_year);
								entity.put("month", budg_month);
								entity.put("depr_count", depr_count);
								entity.put("depr_budg", depr_count);
								collectList.add(new HashMap<String, Object>(entity));
								// budgAssetNowMapper.add(entity);
							}
						}
					}
				}
			}
		} else {
			return "{\"error\":\"无资产卡片数据！\"}";
		}

		if (collectList != null && collectList.size() > 0) {
			try {
				budgAssetNowMapper.addBatch(collectList);
			} catch (Exception e) {
				throw new SysException(e.getMessage());
			}
		}

		return "{\"state\":\"true\"}";
	}

	private int getMonthSpace(Date date1, Date date2) {

		int result = 0;

		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();

		c1.setTime(date1);
		c2.setTime(date2);

		int year = c1.get(Calendar.YEAR);
		int month = c1.get(Calendar.MONTH);
		int year2 = c2.get(Calendar.YEAR);
		int month2 = c2.get(Calendar.MONTH);
		// result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		if (year == year2) {
			result = month - month2;// 两个日期相差几个月，即月份差
		} else {
			result = 12 * (year - year2) + month - month2;// 两个日期相差几个月，即月份差
		}
		return result == 0 ? 1 : Math.abs(result);

	}

}
