package com.chd.hrp.acc.serviceImpl.autovouch.acccoodeptcost;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSON;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.acc.dao.autovouch.acccoodeptcost.AccCoopCostDetailMapper;
import com.chd.hrp.acc.dao.autovouch.acccoodeptcost.AccCoopCostMapper;
import com.chd.hrp.acc.service.autovouch.acccoodeptcost.AccCoopCostService;

@Service("accCoopCostService")
public class AccCoopCostServiceImpl implements AccCoopCostService {

	private static Logger logger = Logger.getLogger(AccCoopCostServiceImpl.class);

	@Resource(name = "accCoopCostMapper")
	private AccCoopCostMapper accCoopCostMapper;

	@Resource(name = "accCoopCostDetailMapper")
	private AccCoopCostDetailMapper accCoopCostDetailMapper;

	@Override
	public String queryAccCoopCost(Map<String, Object> mapVo) {
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String, Object>>) accCoopCostMapper.query(mapVo);
		return ChdJson.toJson(list);
	}

	@Override
	public Map<String, Object> queryAccCoopCostByCode(Map<String, Object> mapVo) {
		return accCoopCostMapper.queryByCode(mapVo);
	}

	@Override
	public String queryAccCoopCostDetail(Map<String, Object> mapVo) {
		if (mapVo.get("ser_num") == null || "".equals(mapVo.get("ser_num").toString())) {
			mapVo.put("coop_obj", "0");
			List<Map<String, Object>> list = accCoopCostMapper.queryAccCccpProjDetail(mapVo);
			return ChdJson.toJson(list);
		} else {
			List<Map<String, Object>> list = accCoopCostDetailMapper.queryAccCoopCostDetail(mapVo);
			return ChdJson.toJson(list);
		}
	}

	@Override
	public String queryAccCoopCostDetailCount(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accCoopCostDetailMapper.queryAccCoopCostDetail(mapVo);
		if (list.size() > 0) {
			return "{\"have_data\":\"true\",\"state\":\"true\"}";
		}
		return "{\"have_data\":\"false\",\"state\":\"true\"}";
	}

	@Override
	public String queryAccCoopObj(Map<String, Object> mapVo) {
		if (mapVo.get("ser_num") == null || "".equals(mapVo.get("ser_num").toString())) {
			mapVo.put("coop_obj", "1");
			List<Map<String, Object>> list = accCoopCostMapper.queryAccCccpProjDetail(mapVo);
			return ChdJson.toJson(list);
		} else {
			List<Map<String, Object>> list = accCoopCostDetailMapper.queryAccCoopObj(mapVo);
			return ChdJson.toJson(list);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String saveAccCoopCost(Map<String, Object> mapVo) {
		try {
			String ser_num;
			if ("add".equals(mapVo.get("page_type").toString())
					&& (mapVo.get("ser_num") == null || "".equals(mapVo.get("ser_num").toString()))) {
				String create_date = mapVo.get("create_date").toString();
				String[] date = create_date.split("-");
				String year = date[0];
				String month = date[1];
				StringBuffer sernum = new StringBuffer();
				sernum.append(year);
				sernum.append(month);
				mapVo.put("begin_date", year + month + "01");

				Calendar a = Calendar.getInstance();
				a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
				a.roll(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
				int maxDate = a.get(Calendar.DATE);
				mapVo.put("end_date", year + month + maxDate);

				String max_ser_num = accCoopCostMapper.queryMaxNo(mapVo);
				if (max_ser_num == null) {
					sernum.append("0001");
				} else {
					int no = Integer.parseInt(max_ser_num.substring(max_ser_num.length() - 4, max_ser_num.length()));
					no++;
					if (no < 10) {
						sernum.append("000").append(no);
					} else if (no < 100) {
						sernum.append("00").append(no);
					} else {
						sernum.append("0").append(no);
					}
				}

				mapVo.put("ser_num", sernum.toString());
				mapVo.put("user_id", SessionManager.getUserId());
				mapVo.put("state", 1);
				ser_num = sernum.toString();
			} else {
				ser_num = mapVo.get("ser_num").toString();
			}

			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();

			BigDecimal countMoney = new BigDecimal(0);

			if (mapVo.get("leftRow") != null) {
				Set<String> exist = new HashSet<String>();
				int count = 0;
				List<Map> leftRows = JSON.parseArray(mapVo.get("leftRow").toString(), Map.class);
				for (Map<String, Object> map : leftRows) {
					if (map.get("dept_id") != null && !"".equals(map.get("dept_id").toString())) {
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("copy_code", SessionManager.getCopyCode());
						saveMap.put("dept_id", Integer.parseInt(map.get("dept_id").toString()));
						saveMap.put("dept_no", Integer.parseInt(map.get("dept_no").toString()));
						if (map.get("ft_my") == null || "".equals(map.get("ft_my").toString())
								|| 0 == Double.parseDouble(map.get("ft_my").toString())) {
							continue;
						} else {
							saveMap.put("ft_my", map.get("ft_my"));
							countMoney = countMoney.add(new BigDecimal(map.get("ft_my").toString()));
						}
						if (map.get("ft_bl") == null || "".equals(map.get("ft_bl").toString())) {
							saveMap.put("ft_bl", 0.00);
						} else {
							saveMap.put("ft_bl", map.get("ft_bl"));
						}
						saveMap.put("note", map.get("note"));
						saveMap.put("ser_num", ser_num.toString());
						saveMap.put("coop_obj", 0);
						saveMap.put("cus_id", 0);
						saveMap.put("cus_no", 0);
						saveMap.put("sup_id", 0);
						saveMap.put("sup_no", 0);
						detailList.add(saveMap);
						exist.add(map.get("dept_id").toString());
						count++;
					}
				}

				if (exist.size() != count) {
					return "{\"error\":\"添加失败，存在重复数据\"}";
				}
			}

			if (mapVo.get("rightRow") != null) {
				Set<String> exist = new HashSet<String>();
				int count = 0;
				List<Map> rightRows = JSON.parseArray(mapVo.get("rightRow").toString(), Map.class);

				for (Map<String, Object> map : rightRows) {
					if (map.get("coop_obj") != null && !"".equals(map.get("coop_obj").toString())) {
						Map<String, Object> saveMap = new HashMap<String, Object>();
						saveMap.put("group_id", SessionManager.getGroupId());
						saveMap.put("hos_id", SessionManager.getHosId());
						saveMap.put("copy_code", SessionManager.getCopyCode());
						saveMap.put("dept_id", 0);
						saveMap.put("dept_no", 0);
						saveMap.put("ser_num", ser_num.toString());
						if (map.get("ft_my") == null || "".equals(map.get("ft_my").toString())
								|| 0 == Double.parseDouble(map.get("ft_my").toString())) {
							continue;
						} else {
							saveMap.put("ft_my", map.get("ft_my"));
							countMoney = countMoney.add(new BigDecimal(map.get("ft_my").toString()));
						}
						if (map.get("ft_bl") == null || "".equals(map.get("ft_bl").toString())) {
							saveMap.put("ft_bl", 0.00);
						} else {
							saveMap.put("ft_bl", map.get("ft_bl"));
						}
						saveMap.put("coop_obj", map.get("coop_obj"));
						saveMap.put("note", map.get("note"));
						String coop_obj = map.get("coop_obj").toString();
						if ("1".equals(coop_obj)) {
							if (map.get("cus_id") == null || "".equals(map.get("cus_id").toString())) {
								continue;
							}
							saveMap.put("cus_id", Integer.parseInt(map.get("cus_id").toString()));
							saveMap.put("cus_no", Integer.parseInt(map.get("cus_no").toString()));
							saveMap.put("sup_id", 0);
							saveMap.put("sup_no", 0);
							exist.add(coop_obj + map.get("cus_id").toString());
						} else if ("2".equals(coop_obj)) {
							if (map.get("sup_id") == null || "".equals(map.get("sup_id").toString())) {
								continue;
							}
							saveMap.put("sup_id", Integer.parseInt(map.get("sup_id").toString()));
							saveMap.put("sup_no", Integer.parseInt(map.get("sup_no").toString()));
							saveMap.put("cus_id", 0);
							saveMap.put("cus_no", 0);
							exist.add(coop_obj + map.get("sup_id").toString());
						}
						detailList.add(saveMap);
						count++;
					}
				}

				if (exist.size() != count) {
					return "{\"error\":\"添加失败，存在重复数据\"}";
				}
			}

			BigDecimal sm_my = new BigDecimal(mapVo.get("sm_my").toString());
			if (sm_my.compareTo(countMoney) != 0) {
				return "{\"error\":\"添加失败，总金额不等于明细金额\"}";
			}

			if ("add".equals(mapVo.get("page_type").toString())) {
				accCoopCostMapper.addAccCoopCost(mapVo);
			} else {
				accCoopCostMapper.updateAccCoopCost(mapVo);
				accCoopCostDetailMapper.deleteAccCoopCostDetailBatch(mapVo);
			}
			if (!detailList.isEmpty()) {
				accCoopCostDetailMapper.addAccCoopCostDetail(detailList);
			}
			return "{\"ser_num\": \"" + ser_num.toString() + "\",\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String auditAccCoopCost(Map<String, Object> mapVo) {
		try {
			String type = mapVo.get("type").toString();
			if ("audit".equals(type)) {
				Map<String, Object> coopMap = accCoopCostMapper.queryByCode(mapVo);
				BigDecimal sm_my = new BigDecimal(coopMap.get("sm_my").toString());

				BigDecimal count = new BigDecimal(0);
				List<Map<String, Object>> coopDetailList = accCoopCostDetailMapper.queryAccCoopCostDetail(mapVo);
				for (Map<String, Object> map : coopDetailList) {
					BigDecimal this_my = new BigDecimal(map.get("ft_my").toString());
					count = count.add(this_my);
				}

				if ("2".equals(coopMap.get("coop_type").toString())) {
					List<Map<String, Object>> coopDetailList2 = accCoopCostDetailMapper.queryAccCoopObj(mapVo);
					for (Map<String, Object> map : coopDetailList2) {
						BigDecimal this_my = new BigDecimal(map.get("ft_my").toString());
						count = count.add(this_my);
					}
				}

				if (sm_my.compareTo(count) != 0) {
					return "{\"error\":\"审核失败，总费用不等于明细费用\"}";
				}

				mapVo.put("state", 2);
				accCoopCostMapper.updateAccCoopCost(mapVo);
				return "{\"msg\":\"审核成功.\",\"state\":\"true\"}";

			} else if ("unaudit".equals(type)) {
				Integer count = accCoopCostMapper.isCreateVouch(mapVo);
				if (count > 0) {
					return "{\"error\":\"已生成凭证单据不能取消审核\"}";
				}

				mapVo.put("state", 1);
				accCoopCostMapper.updateAccCoopCost(mapVo);

				return "{\"msg\":\"取消审核成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"审核失败\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String deleteAccCoopCost(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> delList = new ArrayList<Map<String, Object>>();
			List<String> deleteList = JSON.parseArray(mapVo.get("deleteList").toString(), String.class);
			for (String ser_num : deleteList) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("copy_code", SessionManager.getCopyCode());
				map.put("ser_num", ser_num);
				delList.add(map);
			}
			if (!delList.isEmpty()) {
				accCoopCostDetailMapper.deleteAccCoopCostDetail(delList);
				accCoopCostMapper.deleteAccCoopCostBatch(delList);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String collectAccCoopCost(Map<String, Object> mapVo) {
		try {
			Map<String, Object> returnMap = new HashMap<String, Object>();
			if (mapVo.get("sm_my") == null || "".equals(mapVo.get("sm_my").toString())) {
				return "{\"error\":\"计算失败，总金额为空\"}";
			}

			BigDecimal sm_my = new BigDecimal(mapVo.get("sm_my").toString());
			BigDecimal count = new BigDecimal(0);
			List<Map<String, Object>> detailList = accCoopCostMapper.queryAccCccpProjDetail(mapVo);
			if (detailList.isEmpty()) {
				return "{\"error\":\"计算失败，合作项目未设置明细数据\"}";
			}
			List<Map<String, Object>> leftList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> rightList = new ArrayList<Map<String, Object>>();

			if (!detailList.isEmpty()) {
				for (int i = 0; i < detailList.size() - 1; i++) {
					Map<String, Object> map = detailList.get(i);
					BigDecimal ft_bl = new BigDecimal(map.get("ft_bl").toString());
					BigDecimal ft_my = sm_my.multiply(ft_bl);
					ft_my = ft_my.divide(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP);
					map.put("ft_my", ft_my.doubleValue());
					count = count.add(ft_my);
					if (map.get("coop_obj") != null && !"".equals(map.get("coop_obj").toString())
							&& "0".equals(map.get("coop_obj").toString())) {
						leftList.add(map);
					} else {
						rightList.add(map);
					}
				}

				Map<String, Object> map = detailList.get(detailList.size() - 1);
				BigDecimal ft_my = sm_my.subtract(count);
				map.put("ft_my", ft_my.doubleValue());
				if (map.get("coop_obj") != null && !"".equals(map.get("coop_obj").toString())
						&& "0".equals(map.get("coop_obj").toString())) {
					leftList.add(map);
				} else {
					rightList.add(map);
				}

				returnMap.put("left", leftList);
				returnMap.put("right", rightList);
			}
			return ChdJson.toJson(returnMap);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryCoopCostMaker(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}
		List<Map<String, Object>> list = accCoopCostMapper.queryCoopCostMaker(mapVo, rowBounds);
		return JSON.toJSONString(list);
	}

	@Override
	public String queryAccCoopProjDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}
		List<Map<String, Object>> list = accCoopCostMapper.queryAccCoopProjDict(mapVo, rowBounds);
		return JSON.toJSONString(list);
	}

	@Override
	public Map<String, Object> queryAccCoopCostDetailPrint(Map<String, Object> entityMap) {
		try {
			Map<String, Object> reMap = new HashMap<String, Object>();
			WebApplicationContext context = ContextLoader.getCurrentWebApplicationContext();
			AccCoopCostDetailMapper accCoopCostDetailMapper = (AccCoopCostDetailMapper) context
					.getBean("accCoopCostDetailMapper");
			AccCoopCostMapper accCoopCostMapper = (AccCoopCostMapper) context.getBean("accCoopCostMapper");

			entityMap.put("hos_name", SessionManager.getHosName());
			if (entityMap.get("print_title") == null || "".equals(entityMap.get("print_title"))) {
				entityMap.put("print_title", "合作科室费用登记");
			}

			// 查询入库主表
			Map<String, Object> map = accCoopCostMapper.queryByCodePrint(entityMap);
			// 查询入库明细表
			List<Map<String, Object>> list = accCoopCostDetailMapper.queryAccCoopCostDetailPrint(entityMap);
			reMap.put("main", map);

			reMap.put("detail", list);

			return reMap;

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public List<Map<String, Object>> queryAccCoopCostPrint(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accCoopCostMapper.queryAccCoopCostPrint(mapVo);
		return list;
	}

}
