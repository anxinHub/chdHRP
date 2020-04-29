package com.chd.hrp.acc.serviceImpl.autovouch.accpubCost;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.autovouch.accpubCost.AccPubCostRegMapper;
import com.chd.hrp.acc.service.autovouch.accpubCost.AccPubCostRegService;
import com.chd.hrp.acc.serviceImpl.autovouch.accamorttize.AccAmortizeAutoVouchServiceImpl;
import com.github.pagehelper.PageInfo;

@Service("accPubCostRegService")
public class AccPubCostRegServiceImpl implements AccPubCostRegService {

	private static Logger logger = Logger.getLogger(AccAmortizeAutoVouchServiceImpl.class);

	@Resource(name = "accPubCostRegMapper")
	private AccPubCostRegMapper accPubCostRegMapper;

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String queryAccPubCostReg(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) accPubCostRegMapper.query(mapVo);
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) accPubCostRegMapper.query(mapVo, rowBounds);
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public Map<String, Object> queryAccPubCostRegByCode(Map<String, Object> mapVo) {
		Map<String, Object> queryByCode = accPubCostRegMapper.queryByCode(mapVo);
		return queryByCode;
	}

	@Override
	public String queryAccPubCostRegDept(Map<String, Object> mapVo) {
		SysPage sysPage = new SysPage();
		sysPage = (SysPage) mapVo.get("sysPage");
		String ft_para = mapVo.get("ft_para").toString();
		if ("01".equals(ft_para)) {
			mapVo.put("main_table", "ACC_COST_EMP");
		} else if ("02".equals(ft_para)) {
			mapVo.put("main_table", "ACC_COST_AREA");
		}

		if (sysPage.getTotal() == -1) {
			List<Map<String, Object>> list = (List<Map<String, Object>>) accPubCostRegMapper
					.queryAccPubCostRegDept(mapVo);
			if (!"1".equals(mapVo.get("times").toString())) {
				if (list.isEmpty() && !"".equals(ft_para) && !"99".equals(ft_para)) {
					list = (List<Map<String, Object>>) accPubCostRegMapper.queryAccPubCostRegOtherDept(mapVo);
				}
			}
			return ChdJson.toJson(list);
		} else {
			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
			List<Map<String, Object>> list = (List<Map<String, Object>>) accPubCostRegMapper
					.queryAccPubCostRegDept(mapVo, rowBounds);
			if (!"1".equals(mapVo.get("times").toString())) {
				if (list.isEmpty() && !"".equals(ft_para) && !"99".equals(ft_para)) {
					list = accPubCostRegMapper.queryAccPubCostRegOtherDept(mapVo, rowBounds);
				}
			}
			PageInfo page = new PageInfo(list);
			return ChdJson.toJson(list, page.getTotal());
		}

	}

	@Override
	public String saveAccPubCostReg(Map<String, Object> mapVo) {
		try {
			Map<String, Object> queryByCode = accPubCostRegMapper.queryByCode(mapVo);
			if (queryByCode != null) {
				if (queryByCode.get("state") != null && "2".equals(queryByCode.get("state").toString())) {
					return "{\"error\":\"保存失败，本月此项目已审核\"}";
				}
				if (queryByCode.get("ft_para") != null
						&& !mapVo.get("ft_para").toString().equals(queryByCode.get("ft_para").toString())) {
					return "{\"error\":\"保存失败，本月已在其他分摊参数下存在数据，不能重复添加\"}";
				}
			}

			String note = mapVo.get("note").toString();
			if (note.length() > 100) {
				return "{\"error\":\"保存失败，备注长度超过限定最大长度\"}";
			}

			if (queryByCode == null) {
				mapVo.put("state", 1);
				accPubCostRegMapper.saveAccPubCostReg(mapVo);
			} else {
				accPubCostRegMapper.update(mapVo);
			}
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (DuplicateKeyException e) {
			throw new SysException("{\"error\":\"保存失败，本月已有该数据\"}", e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String saveAccPubCostRegDept(Map<String, Object> mapVo) {
		try {
			Map<String, Object> costreg = accPubCostRegMapper.queryByCode(mapVo);
			if (costreg != null && costreg.get("state") != null && "2".equals(costreg.get("state").toString())) {
				return "{\"error\":\"保存失败，本月此项目已审核\"}";
			}

			Map<String, Object> dept = accPubCostRegMapper.queryPubCostRegDeptByCode(mapVo);
			if (dept != null && dept.get("data_type") != null && "1".equals(dept.get("data_type").toString())) {
				return "{\"error\":\"保存失败，该科室已存在，不允许添加\"}";
			}

			if (dept == null) {
				if ("01".equals(mapVo.get("ft_para").toString())) {
					mapVo.put("main_table", "ACC_COST_EMP");
				} else if ("02".equals(mapVo.get("ft_para").toString())) {
					mapVo.put("main_table", "ACC_COST_AREA");
				}
				if (mapVo.get("main_table") != null) {
					// 验证科室是否是计算科室
					dept = accPubCostRegMapper.queryPubCostRegOtherDeptByCode(mapVo);
					if (dept != null) {
						return "{\"error\":\"保存失败，该科室数据类型为计算，不允许修改\"}";
					}
				}
			}

			BigDecimal res = new BigDecimal(0);
			BigDecimal this_ft_my = new BigDecimal(mapVo.get("ft_my").toString());
			if (costreg == null) {
				res = res.add(this_ft_my);
				mapVo.put("state", 1);
				accPubCostRegMapper.saveAccPubCostReg(mapVo);
			} else {
				if ("add".equals(mapVo.get("type").toString())) {
					BigDecimal ft_my = new BigDecimal(costreg.get("ft_my").toString());
					res = ft_my.add(this_ft_my);
				} else {
					BigDecimal ft_my = new BigDecimal(costreg.get("ft_my").toString());
					BigDecimal old_ft_my = new BigDecimal(dept.get("ft_my").toString());
					res = ft_my.subtract(old_ft_my).add(this_ft_my);
				}
				mapVo.put("ft_my", res.toString());
				accPubCostRegMapper.update(mapVo);
				mapVo.put("ft_my", this_ft_my.toString());
			}

			if (dept == null) {
				mapVo.put("data_type", 2);
				accPubCostRegMapper.saveAccPubCostRegDept(mapVo);
			} else {
				accPubCostRegMapper.updateAccPubCostRegDept(mapVo);
			}
			return "{\"ft_my\": \"" + res.toString() + "\",\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (DuplicateKeyException e) {
			throw new SysException("{\"error\":\"保存失败，本月已有该数据\"}", e);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String deleteAccPubCostReg(Map<String, Object> mapVo) {
		try {
			List<Map> list = JSON.parseArray(mapVo.get("deleteList").toString(), Map.class);
			List<Map<String, Object>> deleteList = new ArrayList<Map<String, Object>>();
			for (Map<String, Object> map : list) {
				if ("2".equals(map.get("state").toString())) {
					continue;
				}
				Map<String, Object> delMap = new HashMap<String, Object>();
				delMap.put("group_id", SessionManager.getGroupId());
				delMap.put("hos_id", SessionManager.getHosId());
				delMap.put("copy_code", SessionManager.getCopyCode());
				delMap.put("proj_code", map.get("proj_code"));
				delMap.put("year_month", map.get("year_month"));
				deleteList.add(delMap);
			}
			if (!deleteList.isEmpty()) {
				accPubCostRegMapper.deleteAccPubCostRegDept(deleteList);
				accPubCostRegMapper.deleteAccPubCostRegBatch(deleteList);
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryDeptAllInfoDict(Map<String, Object> mapVo) {
		RowBounds rowBounds = new RowBounds(0, 20);
		if (mapVo.get("pageSize") != null) {
			rowBounds = new RowBounds(0, (Integer) mapVo.get("pageSize"));
		}
		List<Map<String, Object>> list = accPubCostRegMapper.queryDeptAllInfoDict(mapVo, rowBounds);
		return JSON.toJSONString(list);
	}

	@Override
	public String deleteAccPubCostRegDept(Map<String, Object> mapVo) {
		try {
			List<Map> list = JSON.parseArray(mapVo.get("deleteList").toString(), Map.class);
			List<Map<String, Object>> deleteList = new ArrayList<Map<String, Object>>();

			Map<String, Object> updateMap = new HashMap<String, Object>();
			updateMap.put("group_id", SessionManager.getGroupId());
			updateMap.put("hos_id", SessionManager.getHosId());
			updateMap.put("copy_code", SessionManager.getCopyCode());
			updateMap.put("proj_code", mapVo.get("proj_code"));
			updateMap.put("year_month", mapVo.get("year_month"));

			Map<String, Object> pubCostReg = accPubCostRegMapper.queryByCode(updateMap);
			if (pubCostReg != null && pubCostReg.get("state") != null
					&& "2".equals(pubCostReg.get("state").toString())) {
				return "{\"error\":\"删除失败，本月此项目已审核\"}";
			}
			BigDecimal res = new BigDecimal(0);
			if (pubCostReg != null) {
				res = new BigDecimal(pubCostReg.get("ft_my").toString());
			}

			for (Map<String, Object> map : list) {
				if (map.get("dept_id") == null || "".equals(map.get("dept_id").toString())) {
					continue;
				}
				if (map.get("proj_code") == null || "".equals(map.get("proj_code").toString())) {
					continue;
				}
				if (map.get("year_month") == null || "".equals(map.get("year_month").toString())) {
					continue;
				}
				Map<String, Object> delMap = new HashMap<String, Object>();
				delMap.putAll(updateMap);
				BigDecimal this_ft_my = new BigDecimal(map.get("ft_my").toString());
				res = res.subtract(this_ft_my);
				delMap.put("dept_id", map.get("dept_id"));
				deleteList.add(delMap);
			}
			if (!deleteList.isEmpty()) {
				updateMap.put("ft_my", res.toString());
				accPubCostRegMapper.update(updateMap);

				accPubCostRegMapper.deleteAccPubCostRegDept(deleteList);
			}
			return "{\"ft_my\": \"" + res.toString() + "\",\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String auditAccPubCostReg(Map<String, Object> mapVo) {
		try {
			String returnJson;

			String type = mapVo.get("type").toString();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());

			if ("audit".equals(type)) {
				Map<String, Object> pubCostReg = accPubCostRegMapper.queryByCode(mapVo);
				if (pubCostReg == null || pubCostReg.isEmpty()) {
					return "{\"error\":\"操作失败，未保存数据不能审核\"}";
				}
				BigDecimal allMoney = new BigDecimal(pubCostReg.get("ft_my").toString());
				BigDecimal detMoney = new BigDecimal(0);

				List<Map<String, Object>> deptList = accPubCostRegMapper.queryAccPubCostRegDeptNoSum(mapVo);
				if (deptList.isEmpty()) {
					return "{\"error\":\"操作失败，部门数据未保存，不能进行审核\"}";
				}
				for (Map<String, Object> map : deptList) {
					BigDecimal this_my = new BigDecimal(map.get("ft_my").toString());
					detMoney = detMoney.add(this_my);
				}

				if (allMoney.compareTo(detMoney) != 0) {
					return "{\"error\":\"审核失败，公用费用金额不等于明细数据的总金额\"}";
				}

				mapVo.put("state", 2);
				returnJson = "{\"ft_my\": \"" + allMoney.toString() + "\",\"msg\":\"审核成功.\",\"state\":\"true\"}";
			} else if ("unaudit".equals(type)) {
				if (mapVo.get("year_month") == null || "".equals(mapVo.get("year_month").toString())) {
					return "{\"error\":\"取消审核失败，年月为空\"}";
				}
				if (mapVo.get("proj_code") == null || "".equals(mapVo.get("proj_code").toString())) {
					return "{\"error\":\"取消审核失败，项目为空\"}";
				}
				String year_month = mapVo.get("year_month").toString();
				String proj_code = mapVo.get("proj_code").toString();
				mapVo.put("year_month_proj_code", year_month + "-" + proj_code);
				int i = accPubCostRegMapper.queryIsCreateVouch(mapVo);
				if (i > 0) {
					return "{\"error\":\"已生成凭证，不能取消审核\"}";
				}

				mapVo.put("state", 1);
				returnJson = "{\"msg\":\"消审成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"操作失败，请检查数据类型是否正确\"}";
			}
			accPubCostRegMapper.updateAccPubCostRegState(mapVo);
			return returnJson;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public List<Map<String, Object>> queryAccPubCostRegDeptPrint(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) accPubCostRegMapper.queryAccPubCostRegDept(mapVo);
		return list;
	}

	@Override
	public List<Map<String, Object>> queryAccPubCostRegPrint(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) accPubCostRegMapper.query(mapVo);
		return list;
	}

	@Override
	public String importAccPubCostRegDept(Map<String, Object> mapVo) {
		try {
			List<Map<String, Object>> deptList = new ArrayList<Map<String, Object>>();
			Map<String, String> deptCacheMap = new HashMap<String, String>();
			Map<String, String> deptExistMap = new HashMap<String, String>();
			Map<String, String> deptExistMoneyMap = new HashMap<String, String>();

			// 1.判断表头是否为空
			String columns = mapVo.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			// 2.判断数据是否为空
			String content = mapVo.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			Set<String> set = liData.get(0).keySet();
			if (set.size() > 2) {
				return "{\"error\":\"模版错误！\",\"state\":\"false\"}";
			}

			Map<String, Object> saveMap = new HashMap<String, Object>();
			saveMap.put("group_id", SessionManager.getGroupId());
			saveMap.put("hos_id", SessionManager.getHosId());
			saveMap.put("copy_code", SessionManager.getCopyCode());
			saveMap.put("proj_code", mapVo.get("proj_code"));
			saveMap.put("year_month", mapVo.get("year_month"));
			Map<String, Object> costreg = accPubCostRegMapper.queryByCode(saveMap);
			if (costreg != null && costreg.get("state") != null && "2".equals(costreg.get("state").toString())) {
				return "{\"error\":\"保存失败，本月此项目已审核\"}";
			}

			saveMap.put("ft_para", mapVo.get("ft_para"));
			if ("01".equals(mapVo.get("ft_para").toString())) {
				saveMap.put("main_table", "ACC_COST_EMP");
			} else if ("02".equals(mapVo.get("ft_para").toString())) {
				saveMap.put("main_table", "ACC_COST_AREA");
			}

			if (saveMap.get("main_table") != null) {
				List<Map<String, Object>> otherDept = accPubCostRegMapper.queryAccPubCostRegOtherDept(saveMap);
				for (Map<String, Object> map : otherDept) {
					deptExistMap.put(map.get("dept_id").toString(), map.get("dept_name").toString());
				}
			}
			List<Map<String, Object>> list2 = accPubCostRegMapper.queryAccPubCostRegDeptNoSum(saveMap);
			for (Map<String, Object> map : list2) {
				deptExistMoneyMap.put(map.get("dept_id").toString(), map.get("ft_my").toString());
			}

			// 取出两个表头，在下方取数据用
			String my_head = null;
			String dept_head = null;
			for (String string : set) {
				if (string.contains("科室")) {
					dept_head = string;
				} else if (string.contains("金额")) {
					my_head = string;
				} else {
					return "{\"error\":\"导入模版错误，请下载正确的模版！\",\"state\":\"false\"}";
				}
			}
			// 查询出部门信息，如果仅有一条的话，直接查询出对应的信息。如果多条的话，就全部查询出
			if (liData.size() == 1) {
				mapVo.put("jtkey", liData.get(0).get(dept_head).get(1));
			}
			List<Map<String, Object>> list = accPubCostRegMapper.queryDeptAllInfoDict(mapVo);
			if (list == null || list.isEmpty()) {
				return "{\"error\":\"科室数据错误，请确定所输入的科室！\",\"state\":\"false\"}";
			}

			for (Map<String, Object> map : list) {
				deptCacheMap.put(map.get("dept_name").toString(),
						map.get("dept_id").toString() + "," + map.get("dept_no").toString());
				deptCacheMap.put(map.get("dept_code").toString(),
						map.get("dept_id").toString() + "," + map.get("dept_no").toString());
			}

			BigDecimal all_my = new BigDecimal(costreg.get("ft_my") == null ? "0" : costreg.get("ft_my").toString());
			// 拼装要保存的信息
			for (Map<String, List<String>> map : liData) {
				String dept = map.get(dept_head).get(1);
				String ft_my = map.get(my_head).get(1);
				if (dept == null || "".equals(dept)) {
					return "{\"warn\":\"科室为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get(dept_head).get(0)
							+ "\"}";
				}
				if (ft_my == null || "".equals(ft_my)) {
					return "{\"warn\":\"金额为空！\",\"state\":\"false\",\"row_cell\":\"" + map.get(my_head).get(0) + "\"}";
				}
				if (mapVo.get("type") != null && !"delete".equals(mapVo.get("type").toString())) {

				}

				Map<String, Object> deptSaveMap = new HashMap<String, Object>();
				deptSaveMap.put("group_id", SessionManager.getGroupId());
				deptSaveMap.put("hos_id", SessionManager.getHosId());
				deptSaveMap.put("copy_code", SessionManager.getCopyCode());
				deptSaveMap.put("proj_code", mapVo.get("proj_code"));
				deptSaveMap.put("year_month", mapVo.get("year_month"));
				deptSaveMap.put("data_type", 2);
				deptSaveMap.put("ft_my", ft_my);
				deptSaveMap.put("ft_bl", 0.00);
				if (deptCacheMap.get(dept) != null) {
					String depts = deptCacheMap.get(dept).toString();
					String[] split = depts.split(",");
					// 判断此科室是否在采集表中存在数据，如果存在的话，不允许导入
					String string = deptExistMap.get(split[0]);
					if (string != null && !"".equals(string)) {
						return "{\"warn\":\"科室数据在采集表中存在，不允许导入！\",\"state\":\"false\",\"row_cell\":\""
								+ map.get(dept_head).get(0) + "\"}";
					}
					deptSaveMap.put("dept_id", split[0]);
					deptSaveMap.put("dept_no", split[1]);

					String exist_my = deptExistMoneyMap.get(split[0]);
					if (exist_my == null) {
						exist_my = "0";
					}
					BigDecimal existMoney = new BigDecimal(exist_my);
					all_my = all_my.subtract(existMoney);
					all_my = all_my.add(new BigDecimal(ft_my));
				} else {
					return "{\"warn\":\"科室不存在或非末级科室！\",\"state\":\"false\",\"row_cell\":\"" + map.get(dept_head).get(0)
							+ "\"}";
				}

				deptList.add(deptSaveMap);
			}

			if (!deptList.isEmpty()) {
				saveMap.put("ft_my", all_my.toString());
				Map<String, Object> queryByCode = accPubCostRegMapper.queryByCode(saveMap);
				if (queryByCode == null) {
					saveMap.put("state", 1);
					accPubCostRegMapper.saveAccPubCostReg(saveMap);
				} else {
					if ("2".equals(queryByCode.get("state").toString())) {
						return "{\"error\":\"当前年月的此项目已审核，不能进行导入！\",\"state\":\"false\"}";
					} else {
						accPubCostRegMapper.update(saveMap);
					}
				}

				accPubCostRegMapper.deleteAccPubCostRegDept(deptList);
				accPubCostRegMapper.saveAccPubCostRegDeptBatch(deptList);
			}
			return "{\"ft_my\": \"" + all_my.toString() + "\",\"msg\":\"导入成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String collectAccPubCostReg(Map<String, Object> mapVo) {
		try {
			Map<String, Object> saveMap = new HashMap<String, Object>();
			saveMap.put("group_id", SessionManager.getGroupId());
			saveMap.put("hos_id", SessionManager.getHosId());
			saveMap.put("copy_code", SessionManager.getCopyCode());
			saveMap.put("proj_code", mapVo.get("proj_code"));
			saveMap.put("year_month", mapVo.get("year_month"));
			Map<String, Object> costreg = accPubCostRegMapper.queryByCode(saveMap);
			if (costreg != null && costreg.get("state") != null && "2".equals(costreg.get("state").toString())) {
				return "{\"error\":\"计算失败，本月此项目已审核\"}";
			}
			BigDecimal all_my;
			// 如果数据库不存在主表
			if (costreg == null) {
				saveMap.put("ft_my", mapVo.get("ft_my"));
				saveMap.put("ft_para", mapVo.get("ft_para"));
				saveMap.put("note", mapVo.get("note"));
				saveMap.put("state", 1);
				accPubCostRegMapper.saveAccPubCostReg(saveMap);
				all_my = new BigDecimal(mapVo.get("ft_my").toString());
			} else
			// 如果数据库存在主表，而且与页面上的金额一致
			if (costreg.get("ft_my").toString().equals(mapVo.get("ft_my").toString())) {
				all_my = new BigDecimal(costreg.get("ft_my").toString());
			} else {
				// 如果金额不相等的话，更新数据库，以页面为准
				saveMap.put("ft_my", mapVo.get("ft_my").toString());
				accPubCostRegMapper.update(saveMap);
				all_my = new BigDecimal(mapVo.get("ft_my").toString());
			}
			if (costreg != null && costreg.get("state") != null && "2".equals(costreg.get("state").toString())) {
				return "{\"error\":\"计算失败，本月已有该审核数据\"}";
			}

			if ("01".equals(mapVo.get("ft_para").toString())) {
				saveMap.put("main_table", "ACC_COST_EMP");
				accPubCostRegMapper.extendOtherDept(saveMap);
			} else if ("02".equals(mapVo.get("ft_para").toString())) {
				saveMap.put("main_table", "ACC_COST_AREA");
				accPubCostRegMapper.extendOtherDept(saveMap);
			}

			saveMap.put("ft_para", mapVo.get("ft_para"));

			List<Map<String, Object>> deptList = accPubCostRegMapper.queryAccPubCostRegDeptNoSum(saveMap);

			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> otherDeptList = new ArrayList<Map<String, Object>>();

			for (Map<String, Object> map : deptList) {
				if ("2".equals(map.get("data_type").toString())) {
					BigDecimal this_my = new BigDecimal(map.get("ft_my").toString());
					// BigDecimal divide = this_my.divide(all_my, 6,
					// BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
					map.put("ft_bl", 0.00);
					all_my = all_my.subtract(this_my);
					updateList.add(map);
				} else {
					otherDeptList.add(map);
				}
			}
			if (all_my.compareTo(BigDecimal.ZERO) == -1) {
				return "{\"error\":\"计算失败，摊销总金额小于导入金额\"}";
			}

			if (otherDeptList.size() > 0) {
				BigDecimal rest_my = new BigDecimal(0);
				for (int i = 0; i < otherDeptList.size() - 1; i++) {
					Map<String, Object> map = otherDeptList.get(i);
					BigDecimal this_bl = new BigDecimal(map.get("ft_bl").toString()).divide(new BigDecimal(100));
					BigDecimal this_my = all_my.multiply(this_bl).setScale(2, BigDecimal.ROUND_HALF_UP);
					rest_my = rest_my.add(this_my);
					map.put("ft_my", this_my.toString());
					updateList.add(map);
				}

				Map<String, Object> map = otherDeptList.get(otherDeptList.size() - 1);
				map.put("ft_my", all_my.subtract(rest_my).toString());
				updateList.add(map);
			}

			if (!updateList.isEmpty()) {
				accPubCostRegMapper.updateAccPubCostRegDeptBatch(updateList);
			}
			return "{\"msg\":\"计算完成.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage(), e);
		}
	}

	@Override
	public String queryAccPubCostRegDeptCount(Map<String, Object> mapVo) {
		List<Map<String, Object>> list = accPubCostRegMapper.queryAccPubCostRegDeptNoSum(mapVo);
		if (list.size() > 0) {
			return "{\"have_data\":\"true\",\"state\":\"true\"}";
		}
		return "{\"have_data\":\"false\",\"state\":\"true\"}";
	}

}
