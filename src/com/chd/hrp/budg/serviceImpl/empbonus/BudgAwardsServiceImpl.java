/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.budg.serviceImpl.empbonus;

import java.math.BigDecimal;
import java.util.*;
import javax.annotation.Resource;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.budg.dao.base.budgawardsitem.BudgAwardsItemDictMapper;
import com.chd.hrp.budg.dao.base.budgdept.BudgDeptSetMapper;
import com.chd.hrp.budg.dao.empbonus.BudgAwardsMapper;
import com.chd.hrp.budg.entity.BudgAwardsItemDict;
import com.chd.hrp.budg.entity.BudgWageChange;
import com.chd.hrp.budg.service.empbonus.BudgAwardsService;
import com.chd.hrp.hr.entity.sysstruc.HrTabType;
import com.chd.hrp.hr.util.RegExpValidatorUtils;
import com.github.pagehelper.PageInfo;

/**
 * 
 * @Description:
 * 
 * @Table: BUDG_WAGE
 * @Author: bell
 * @email: bell@e-tonggroup.com
 * @Version: 1.0
 */

@Service("budgAwardsService")
public class BudgAwardsServiceImpl implements BudgAwardsService {

	private static Logger logger = Logger.getLogger(BudgAwardsServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "budgAwardsMapper")
	private final BudgAwardsMapper budgAwardsMapper = null;

	@Resource(name = "budgAwardsItemDictMapper")
	private final BudgAwardsItemDictMapper budgAwardsItemDictMapper = null;

	@Resource(name = "budgDeptSetMapper")
	private final BudgDeptSetMapper budgDeptSetMapper = null;

	/**
	 * @Description 添加工资变动<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {
		// 定义addList 接收最终数据 添加进数据库
		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

		// 定义countList 接收计算出的各科室 各职工类别 各工资项目 总支出 数据
		List<Map<String, Object>> countList = new ArrayList<Map<String, Object>>();

		// 定义list 接收重新封装数据的mapVo集合
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		// 定义map 封装条件查询上年12月份各科室人数
		Map<String, Object> map = new HashMap<String, Object>();

		map.put("group_id", entityMap.get("group_id"));
		map.put("hos_id", entityMap.get("hos_id"));
		map.put("copy_code", entityMap.get("copy_code"));
		map.put("year", Integer.parseInt(entityMap.get("budg_year").toString()) - 1);
		// map.put("month", 12);

		try {
			// 根据map中的条件 从科室职工工资总表中 查询上年最大月份 各科室 各工资类别 下 人数 作为人员数量 的 基数
			List<Map<String, Object>> baseList = budgAwardsMapper.queryLYEmpCountFromTotal(map);
			// 遍历数据 结合增员计划 和减员计划 确定预算年度下 各科室各月份人数
			for (Map<String, Object> mapVo : baseList) {

				// 添加参数 预算年度
				mapVo.put("budg_year", entityMap.get("budg_year"));

				// 添加参数上年年度 用于查询上年各科室 各工资项目平均工资使用
				mapVo.put("last_year", map.get("year"));

				// 获取每条数据中 该科室类别下 职工人数 作为基数
				int empCount = Integer.parseInt(mapVo.get("empCount").toString());
				// 根据根据科室 职工类别 查询该科室职工类别 该月份下是否有减员计划 并查询出减员人员id
				List<Map<String, Object>> cutEmpIdList = budgAwardsMapper.queryCutPlan(mapVo);
				// 根据科室 职工类别 查询该科室职工类别是否有增员计划
				Map<String, Object> addPlanMap = budgAwardsMapper.queryAddPlan(mapVo);
				for (int i = 1; i <= 12; i++) {
					// 定义newMap 封装条件
					Map<String, Object> newMap = new HashMap<String, Object>();

					newMap.putAll(mapVo);

					if (i < 10) {
						newMap.put("month", "0" + i);
					} else {
						newMap.put("month", String.valueOf(i));
					}

					if (addPlanMap != null) {
						// 获取增加人数
						int add = Integer.parseInt(addPlanMap.get("add_num").toString());
						// 如果当前月份与增员计划到岗日期月份相同 则职工人数加上增员人数
						if (newMap.get("month").toString().equals(addPlanMap.get("in_month").toString())) {
							empCount += add;
						}
					}
					// 遍历查询出的减员计划 匹配月份 如果离职月份与当前月份相同 职工人数减去减员人员
					for (Map<String, Object> cutMap : cutEmpIdList) {

						// 根据当前职工id 反查工资总表中各科室是否带有该员工 如果带有该员工 且当前月份与离职月份相同 减去
						map.put("emp_id", cutMap.get("emp_id"));
						map.put("dept_id", newMap.get("dept_id"));
						map.put("emp_type_code", newMap.get("emp_type_code"));

						if (newMap.get("month").toString().equals(cutMap.get("out_month").toString())) {
							int count = budgAwardsMapper.queryEmpExists(map);
							if (count > 0) {
								--empCount;
							}
						}

					}
					newMap.put("empCount", empCount);
					list.add(newMap);
				}
			}

			for (Map<String, Object> empCountMap : list) {
				// 查询各科室各职工类别下各工资项目的平均工资
				List<Map<String, Object>> typeAwardsList = budgAwardsMapper.queryEmpTypeAwards(empCountMap);

				// 获取每条数据中 该科室 该职工类别下 职工人数
				int empCount = Integer.parseInt(empCountMap.get("empCount").toString());

				// 遍历typeWageList 获取单独工资项目的平均工资
				for (Map<String, Object> typeAwardsMap : typeAwardsList) {
					typeAwardsMap.putAll(entityMap);

					// 定义amountMap 封装条件 获取各科室 各职工类别 每月总支出
					Map<String, Object> amountMap = new HashMap<String, Object>();
					amountMap.putAll(empCountMap);

					// 获取平均工资
					Double avgAmount = Double.parseDouble(typeAwardsMap.get("amount").toString());

					// 根据 职工类别 工资项目 查询调整数据
					Map<String, Object> typeAwardsAdjMap = budgAwardsMapper.queryTypeAwardsAdj(typeAwardsMap);
					// 如果查询出的typeWageAdjMap 不为空 则匹配月份数据 计算计划平均工资
					if (typeAwardsAdjMap != null) {
						// 获取调整比例
						int adj_rate = Integer.parseInt(typeAwardsAdjMap.get("adj_rate").toString());
						// 获取调整金额
						Double adj_amount = Double.parseDouble(typeAwardsAdjMap.get("adj_amount").toString());

						if (empCountMap.get("month").toString().equals(typeAwardsAdjMap.get("adj_month").toString())
								|| Integer.parseInt(empCountMap.get("month").toString()) > Integer
										.parseInt((typeAwardsAdjMap.get("adj_month").toString()))) {
							if (adj_rate > 0) {
								// 计算 计划平均工资(计划平均工资为平均工资*(1+分解比例))
								avgAmount = Double.parseDouble(String.valueOf(avgAmount)) * (100 + adj_rate) / 100;// 除以100
																													// 按百分比计算

								BigDecimal countValue = new BigDecimal(String.valueOf(avgAmount));
								avgAmount = countValue.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

							} else if (adj_amount > 0) {
								avgAmount = avgAmount + adj_amount;
							}
						}
					}

					// 计算当前月份 该科室 该职工类别 该工资项目 总支出
					Double value = empCount * avgAmount;
					BigDecimal countValue = new BigDecimal(String.valueOf(value));
					double amount = countValue.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
					// 将当前工资项目 科室工资项目总支出 放入mapVo 并存入list
					amountMap.put("awards_item_code", typeAwardsMap.get("awards_item_code"));
					amountMap.put("amount", amount);

					countList.add(amountMap);
				}
			}

			// 根据月份,科室,工资项目 汇总数据

			Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();

			for (Map<String, Object> countMap : countList) {
				String monthOne = countMap.get("month").toString();
				String deptId = countMap.get("dept_id").toString();
				String awardsItemCode = countMap.get("awards_item_code").toString();

				Double count_value = Double.parseDouble(countMap.get("amount").toString());

				String key = monthOne + deptId + awardsItemCode;

				if (resultMap.get(key) != null) {
					// 如果key存在 则更新计算值
					count_value += Double.parseDouble(resultMap.get(key).get("count_value").toString());

					resultMap.get(key).put("count_value", count_value);

				} else {
					// 如果不存在 则将key 对应数据 放入resultMap 之中 并将计算值字段放入resultMap 中
					resultMap.put(key, countMap);
					resultMap.get(key).put("count_value", count_value);
				}
			}

			for (String dataKey : resultMap.keySet()) {

				// 表中其余字段处理
				resultMap.get(dataKey).put("cost_budg", resultMap.get(dataKey).get("count_value"));// 支出预算
				resultMap.get(dataKey).put("adj_rate", "");// 调整比例
				resultMap.get(dataKey).put("remark", "");// 说明

				addList.add(resultMap.get(dataKey));
			}

			budgAwardsMapper.delete(entityMap);
			budgAwardsMapper.addBatch(addList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败  方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加工资变动<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgAwardsMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"添加失败! 方法 addBatch\"}");

		}

	}

	/**
	 * @Description 更新工资变动<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = budgAwardsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败  方法 update\"}");

		}

	}

	/**
	 * @Description 批量更新工资变动<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgAwardsMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"更新失败! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除工资变动<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			int state = budgAwardsMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除工资变动<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			budgAwardsMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"删除失败! 方法 deleteBatch\"}");

		}
	}

	/**
	 * @Description 添加工资变动<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象工资变动
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<BudgWageChange> list = (List<BudgWageChange>) budgAwardsMapper.queryExists(mapVo);

		if (list.size() > 0) {

			int state = budgAwardsMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			int state = budgAwardsMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集工资变动<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<BudgWageChange> list = (List<BudgWageChange>) budgAwardsMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<BudgWageChange> list = (List<BudgWageChange>) budgAwardsMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象工资变动<BR>
	 * @param entityMap<BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return budgAwardsMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取工资变动<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return BudgWageChange
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return budgAwardsMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取工资变动<BR>
	 * @param entityMap<BR>
	 *            参数为要检索的字段
	 * @return List<BudgWageChange>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return budgAwardsMapper.queryExists(entityMap);
	}

	@Override
	public String importExcel(Map<String, Object> entityMap) {
		int successNum = 0;
		int failureNum = 0;
		StringBuilder failureMsg = new StringBuilder();
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();

		Map<String, Object> whereMap = new HashMap<String, Object>(3);
		whereMap.put("group_id", SessionManager.getGroupId());
		whereMap.put("hos_id", SessionManager.getHosId());
		whereMap.put("copy_code", SessionManager.getCopyCode());

		// 资金项目
		Map<String, Object> awardsItemMap = new HashMap<String, Object>();
		List<Map<String, Object>> awardsItemList =  (List<Map<String, Object>>) budgAwardsItemDictMapper.query(whereMap);
		for (Map<String, Object> map : awardsItemList) {
			awardsItemMap.put(map.get("awards_item_code").toString(), map.get("awards_item_code"));
			awardsItemMap.put(map.get("awards_item_name").toString(), map.get("awards_item_code"));
		}

		// 预算科室
		Map<String, Object> budgDeptMap = new HashMap<String, Object>();
		List<Map<String, Object>> budgDeptList = (List<Map<String, Object>>) budgDeptSetMapper.query(whereMap);
		for (Map<String, Object> map : budgDeptList) {
			budgDeptMap.put(map.get("dept_id").toString(), map.get("dept_id"));
			budgDeptMap.put(map.get("dept_name").toString(), map.get("dept_id"));
		}

		try {
			List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
			if (list != null && list.size() > 0) {
				for (Map<String, List<String>> map : list) {
					Map<String, Object> saveMap = new HashMap<String, Object>();
					saveMap.put("group_id", SessionManager.getGroupId());
					saveMap.put("hos_id", SessionManager.getHosId());
					saveMap.put("copy_code", SessionManager.getCopyCode());
					saveMap.put("budg_year", map.get("budg_year").get(1));
					saveMap.put("month", formatMonth(map.get("month").get(1)));
					if (budgDeptMap.get(map.get("dept_id").get(1)) == null) {
						failureMsg.append("<br/>科室名称:" + map.get("dept_id") + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("dept_id", budgDeptMap.get(map.get("dept_id").get(1)));
					if (awardsItemMap.get(map.get("awards_item_code").get(1)) == null) {
						failureMsg.append("<br/>资金项目:" + map.get("awards_item_code") + " 不存在; ");
						failureNum++;
						continue;
					}
					saveMap.put("awards_item_code", awardsItemMap.get(map.get("awards_item_code").get(1)));
					if (!RegExpValidatorUtils.IsNumber(map.get("cost_budg").get(1))) {
						failureMsg.append("<br/>支出预算:" + map.get("cost_budg") + " 非法数字; ");
						failureNum++;
						continue;
					}
					saveMap.put("cost_budg", map.get("cost_budg").get(1));
					saveMap.put("count_value", "");
					saveMap.put("adj_rate", "");
					saveMap.put("remark", map.get("remark").get(1));

					Map<String, Object> data = budgAwardsMapper.queryByCode(saveMap);

					if (data != null) {
						failureMsg.append("<br/>预算年度:" + map.get("budg_year").get(1) + " 月份:" + map.get("month").get(1) + " 科室名称:"
								+ map.get("dept_id").get(1) + " 资金项目:" + map.get("awards_item_code").get(1) + " 已存在; ");
						failureNum++;
						continue;
					}
					successNum++;
					saveList.add(saveMap);
				}
				if (saveList.size() > 0) {
					budgAwardsMapper.addBatch(saveList);
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条，导入信息如下：");
				}
			}
			return "{\"msg\":\"已成功导入 " + successNum + "条" + failureMsg + "\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			return "{\"error\":\"导入失败！\"}";
		}
	}

	public static String formatMonth(String month) {
		if (month.length() < 2) {
			return "0" + month;
		}
		return month;
	}

}
