package com.chd.hrp.acc.serviceImpl.autovouch.accamorttize;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeDeptMapper;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeInfoMapper;
import com.chd.hrp.acc.dao.autovouch.accamortize.AccAmortizeSourceMapper;
import com.chd.hrp.acc.service.autovouch.accamortize.AccAmortizeInfoService;
import com.github.pagehelper.PageInfo;

@Service("accAmortizeInfoService")
public class AccAmortizeInfoServiceImpl<E> implements AccAmortizeInfoService {

	private static Logger logger = Logger.getLogger(AccAmortizeInfoServiceImpl.class);

	@Resource(name = "accAmortizeInfoMapper")
	private AccAmortizeInfoMapper accAmortizeInfoMapper;

	@Resource(name = "accAmortizeSourceMapper")
	private AccAmortizeSourceMapper accAmortizeSourceMapper;

	@Resource(name = "accAmortizeDeptMapper")
	private AccAmortizeDeptMapper accAmortizeDeptMapper;

	@Override
	public String saveAmortizeInfo(Map<String, Object> mapVo) {
		try {
			Calendar instance = Calendar.getInstance();
			int year = instance.get(Calendar.YEAR);
			int month = instance.get(Calendar.MONTH) + 1;
			int day = instance.get(Calendar.DATE);
			StringBuffer apply_code = new StringBuffer();
			apply_code.append(year);
			if (month < 10) {
				apply_code.append(0).append(month);
			} else {
				apply_code.append(month);
			}
			if (day < 10) {
				apply_code.append(0).append(day);
			} else {
				apply_code.append(day);
			}

			mapVo.put("apply_code", apply_code.toString());
			String maxNo = accAmortizeInfoMapper.queryMaxNoByTypeCode(mapVo);
			if (maxNo == null || "".equals(maxNo.toString())) {
				maxNo = "001";
			} else {
				maxNo = maxNo.substring(maxNo.length() - 3, maxNo.length());
				int max = Integer.parseInt(maxNo) + 1;
				if (max < 10) {
					maxNo = "00" + max;
				} else if (max < 100) {
					maxNo = "0" + max;
				} else {
					maxNo = String.valueOf(max);
				}
			}
			apply_code.append(maxNo);

			mapVo.put("apply_code", apply_code.toString());
			// 摊销状态 初始为0
			mapVo.put("amortize_state", 0);
			// 审核状态
			mapVo.put("state", 0);

			BigDecimal origin_value = new BigDecimal(mapVo.get("origin_value").toString());
			mapVo.put("net_value", origin_value.doubleValue());
			mapVo.put("user_id", SessionManager.getUserId());
			mapVo.put("apply_date", new Date());

			if (mapVo.get("amortized") == null || "".equals(mapVo.get("amortized").toString())) {
				// 已摊销期间，默认0
				mapVo.put("amortized", 0);
			}
			if (mapVo.get("amortized_value") == null || "".equals(mapVo.get("amortized_value").toString())) {
				// 已摊销期间，默认0
				mapVo.put("amortized_value", 0.00);
			}

			accAmortizeInfoMapper.saveAmortizeInfo(mapVo);
			return "{\"apply_code\": \"" + apply_code + "\",\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public String queryAmortizeInfo(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) accAmortizeInfoMapper.query(mapVo);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) accAmortizeInfoMapper.query(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryAmortizeInfoPrint(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accAmortizeInfoMapper.queryPrint(mapVo);
		return list;
	}

	@Override
	public String queryAmortizeHistoryList(Map<String, Object> mapVo) {
		if (mapVo.get("apply_code") == null || "".equals(mapVo.get("apply_code").toString())
				|| "编码由系统生成".equals(mapVo.get("apply_code").toString())) {
			return ChdJson.toJson(new ArrayList<Map<String, Object>>());
		}
		ArrayList<String> list = new ArrayList<String>();
		list.add(mapVo.get("apply_code").toString());
		mapVo.put("list", list);
		List<Map<String, Object>> query = accAmortizeInfoMapper.queryAmortizeHistoryList(mapVo);
		return ChdJson.toJson(query);
	}

	@Override
	public String queryAmortizeHistoryCount(Map<String, Object> mapVo) {
		List<String> list = JSON.parseArray(mapVo.get("apply_codes").toString(), String.class);
		mapVo.put("list", list);
		List<Map<String, Object>> query = accAmortizeInfoMapper.queryAmortizeHistoryList(mapVo);
		if (query.isEmpty()) {
			return "{\"have_data\": \"false\",\"state\":\"true\"}";
		} else {
			return "{\"have_data\": \"true\",\"state\":\"true\"}";

		}
	}

	@Override
	public String updateAmortizeInfo(Map<String, Object> mapVo) {
		try {
			if (mapVo.get("apply_code") == null || "".equals(mapVo.get("apply_code").toString())) {
				return "{\"error\":\"保存失败，单据编码为空\"}";
			}

			accAmortizeInfoMapper.updateAmortizeInfo(mapVo);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public Map<String, Object> queryAmortizeByCode(Map<String, Object> mapVo) {
		Map<String, Object> query = accAmortizeInfoMapper.queryByCode(mapVo);
		return query;
	}

	@Override
	public String deleteAmortizeList(Map<String, Object> mapVo) {
		try {
			List<String> list = JSON.parseArray(mapVo.get("deleteList").toString(), String.class);
			List<Map<String, Object>> delList = new ArrayList<Map<String, Object>>();
			for (String apply_code : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("apply_code", apply_code);

				delList.add(map);
			}

			accAmortizeDeptMapper.deleteBatch(delList);
			accAmortizeSourceMapper.deleteBatch(delList);
			accAmortizeInfoMapper.deleteBatch(delList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public String setAmortize(Map<String, Object> mapVo) {
		try {
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			List<Map<String, Object>> list = (List<Map<String, Object>>) accAmortizeInfoMapper.queryForAmortize(mapVo);
			List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

			List<Map<String, Object>> historyList = new ArrayList<Map<String, Object>>();
			Calendar instance = Calendar.getInstance();
			List<String> amortize_state = new ArrayList<String>();
			for (Map<String, Object> map : list) {
				Boolean is_last = false;
				// 本次摊销期间
				int amortized = Integer.parseInt(map.get("amortized").toString()) + 1;
				map.put("amortized", amortized);

				// 摊销年限
				int amortize_month = Integer.parseInt(map.get("amortize_year").toString()) * 12;
				if (amortize_month == amortized) {
					// 最后一次摊销
					is_last = true;
				}

				// 根据资金来源来分析明细数据
				List<Map<String, Object>> sourceList = (List<Map<String, Object>>) accAmortizeSourceMapper.query(map);
				List<Map<String, Object>> deptList = (List<Map<String, Object>>) accAmortizeDeptMapper.query(map);

				// 原值
				BigDecimal origin_value = new BigDecimal(map.get("origin_value").toString());

				// 本次摊销金额
				BigDecimal this_amortize_value = new BigDecimal(0);

				List<Map<String, Object>> cacheList = new ArrayList<Map<String, Object>>();
				for (Map<String, Object> map2 : sourceList) {
					BigDecimal money = new BigDecimal(map2.get("money").toString());
					BigDecimal ave_money = money.divide(new BigDecimal(amortize_month), 2, BigDecimal.ROUND_HALF_DOWN);

					for (Map<String, Object> map3 : deptList) {
						Map<String, Object> historyMap = new HashMap<String, Object>();
						historyMap.put("group_id", SessionManager.getGroupId());
						historyMap.put("hos_id", SessionManager.getHosId());
						historyMap.put("copy_code", SessionManager.getCopyCode());
						historyMap.put("year", instance.get(Calendar.YEAR));
						historyMap.put("user_id", SessionManager.getUserId());
						historyMap.put("amortized_date", new Date());
						historyMap.put("month", instance.get(Calendar.MONTH) + 1);
						historyMap.put("apply_code", map.get("apply_code"));
						historyMap.put("history_code", map.get("apply_code").toString() + amortized);
						historyMap.put("amortized", amortized);
						historyMap.put("source_id", map2.get("source_id"));
						historyMap.put("dept_id", map3.get("dept_id"));

						// 获取比例
						BigDecimal amortize_coefficient = new BigDecimal(map3.get("amortize_coefficient").toString());
						BigDecimal this_dept_amortize = ave_money.multiply(amortize_coefficient);

						// 本期摊销
						this_amortize_value = this_amortize_value.add(this_dept_amortize);
						historyMap.put("this_amortized", this_dept_amortize.doubleValue());
						// 累计摊销
						BigDecimal all_amortized = ave_money.multiply(new BigDecimal(amortized))
								.multiply(amortize_coefficient);
						historyMap.put("all_amortized", all_amortized.doubleValue());
						if (is_last) {
							origin_value = origin_value.subtract(all_amortized);
							cacheList.add(historyMap);
						} else {
							historyList.add(historyMap);
						}
					}
				}

				if (is_last) {
					if (origin_value.compareTo(BigDecimal.ZERO) != 0) {
						Map<String, Object> map2 = cacheList.get(0);
						BigDecimal this_amortized = new BigDecimal(map2.get("this_amortized").toString());
						this_amortized = this_amortized.add(origin_value);
						map2.put("this_amortized", this_amortized.doubleValue());
						BigDecimal all_amortized = new BigDecimal(map2.get("all_amortized").toString());
						all_amortized = all_amortized.add(origin_value);
						map2.put("all_amortized", all_amortized.doubleValue());
					}
					historyList.addAll(cacheList);

					// 完成摊销
					amortize_state.add(map.get("apply_code").toString());
					map.put("amortize_state", 2);
					map.put("amortized_value", map.get("origin_value"));
					map.put("net_value", 0.00);
				} else {
					BigDecimal amortized_value = new BigDecimal(map.get("amortized_value").toString());
					BigDecimal net_value = new BigDecimal(map.get("net_value").toString());

					map.put("amortize_state", 1);
					// 累计摊销
					amortized_value = amortized_value.add(this_amortize_value);
					map.put("amortized_value", amortized_value.doubleValue());
					// 净值
					net_value = net_value.subtract(this_amortize_value);
					map.put("net_value", net_value.doubleValue());
				}

				if (!historyList.isEmpty()) {
					saveList.add(map);
				}
			}

			if (!saveList.isEmpty()) {
				accAmortizeInfoMapper.addHistoryBatch(historyList);
				accAmortizeInfoMapper.updateBatch(saveList);
			}

			return "{\"msg\":\"摊销成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String queryAmortizeReport(Map<String, Object> mapVo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String checkAmortizeState(Map<String, Object> mapVo) {
		try {
			List<String> list = JSON.parseArray(mapVo.get("apply_codes").toString(), String.class);
			String state = mapVo.get("state").toString();

			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
			for (String apply_code : list) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("apply_code", apply_code);
				map.put("state", state);
				if ("0".equals(state)) {
					map.put("amortize_state", 0);
					map.put("amortized_value", 0);
					map.put("amortized", 0);
					map.put("net_value", 0);
				}
				updateList.add(map);
			}

			if (!updateList.isEmpty()) {
				accAmortizeInfoMapper.deleteAmortizeHistoryList(updateList);
				accAmortizeInfoMapper.updateStateBatch(updateList);
			}
			if ("1".equals(state)) {
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";
			} else {
				return "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
