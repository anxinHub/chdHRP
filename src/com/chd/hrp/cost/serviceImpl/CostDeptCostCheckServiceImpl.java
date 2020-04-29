/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.cost.serviceImpl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.dao.Dao;
import org.nutz.mvc.Mvcs;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.startup.LoadSystemInfo;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ExcelWriter;
import com.chd.base.util.FileUtil;
import com.chd.base.util.PreciseCompute;
import com.chd.hrp.cost.dao.CostDeptCostMapper;
import com.chd.hrp.cost.dao.CostParaTypeMapper;
import com.chd.hrp.cost.entity.CostDeptCost;
import com.chd.hrp.cost.entity.CostDeptCostCheck;
import com.chd.hrp.cost.entity.CostDeptDriData;
import com.chd.hrp.cost.entity.CostParaType;
import com.chd.hrp.cost.service.CostDeptCostCheckService;
import com.github.pagehelper.PageInfo;

/**
 * @Description: 成本_科室成本核算总表
 * @Table: COST_DEPT_COST
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("costDeptCostCheckService")
public class CostDeptCostCheckServiceImpl implements CostDeptCostCheckService {

	private static Logger logger = Logger.getLogger(CostDeptCostCheckServiceImpl.class);

	private final static String configUrl = LoadSystemInfo.getProjectUrl();

	// 引入DAO操作
	@Resource(name = "costDeptCostMapper")
	private final CostDeptCostMapper costDeptCostMapper = null;

	@Resource(name = "costParaTypeMapper")
	private final CostParaTypeMapper costParaTypeMapper = null;

	/**
	 * @Description 添加成本_科室成本核算总表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String add(Map<String, Object> entityMap) throws DataAccessException {

		// 获取对象成本_科室成本核算总表
		CostDeptCost costDeptCost = queryByCode(entityMap);

		if (costDeptCost != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {

			costDeptCostMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 add\"}";

		}

	}

	/**
	 * @Description 批量添加成本_科室成本核算总表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String addBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptCostMapper.addBatch(entityList);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addBatch\"}";

		}

	}

	/**
	 * @Description 更新成本_科室成本核算总表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String update(Map<String, Object> entityMap) throws DataAccessException {

		try {

			costDeptCostMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 update\"}";

		}

	}

	/**
	 * @Description 批量更新成本_科室成本核算总表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String updateBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptCostMapper.updateBatch(entityList);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 updateBatch\"}";

		}

	}

	/**
	 * @Description 删除成本_科室成本核算总表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String delete(Map<String, Object> entityMap) throws DataAccessException {

		try {

			costDeptCostMapper.delete(entityMap);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 delete\"}";

		}

	}

	/**
	 * @Description 批量删除成本_科室成本核算总表<BR>
	 * @param entityList
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public String deleteBatch(List<Map<String, Object>> entityList) throws DataAccessException {

		try {

			costDeptCostMapper.deleteBatch(entityList);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 方法 deleteBatch\"}";

		}
	}

	/**
	 * @Description 添加成本_科室成本核算总表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String addOrUpdate(Map<String, Object> entityMap) throws DataAccessException {
		/**
		 * 备注 先判断是否存在 存在即更新不存在则添加<br>
		 * 在判断是否存在时可根据实际情况进行修改传递的参数进行判断<br>
		 * 判断是否名称相同 判断是否 编码相同 等一系列规则
		 */
		// 判断是否存在对象成本_科室成本核算总表
		Map<String, Object> mapVo = new HashMap<String, Object>();
		mapVo.put("group_id", entityMap.get("group_id"));
		mapVo.put("hos_id", entityMap.get("hos_id"));
		mapVo.put("copy_code", entityMap.get("copy_code"));
		mapVo.put("acct_year", entityMap.get("acct_year"));

		List<CostDeptCost> list = (List<CostDeptCost>) costDeptCostMapper.queryExists(mapVo);

		if (list.size() > 0) {

			costDeptCostMapper.update(entityMap);

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		}

		try {

			costDeptCostMapper.add(entityMap);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! 方法 addOrUpdate\"}";

		}

	}

	/**
	 * @Description 查询结果集成本_科室成本核算总表<BR>
	 * @param entityMap
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<CostDeptCost> list = (List<CostDeptCost>) costDeptCostMapper.query(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<CostDeptCost> list = (List<CostDeptCost>) costDeptCostMapper.query(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}

	}

	/**
	 * @Description 获取对象成本_科室成本核算总表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为主键
	 * @return String JSON
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return costDeptCostMapper.queryByCode(entityMap);
	}

	/**
	 * @Description 获取成本_科室成本核算总表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return CostDeptCost
	 * @throws DataAccessException
	 */
	@Override
	public <T> T queryByUniqueness(Map<String, Object> entityMap) throws DataAccessException {
		return costDeptCostMapper.queryByUniqueness(entityMap);
	}

	/**
	 * @Description 获取成本_科室成本核算总表<BR>
	 * @param entityMap
	 * <BR>
	 *            参数为要检索的字段
	 * @return List<CostDeptCost>
	 * @throws DataAccessException
	 */
	@Override
	public List<?> queryExists(Map<String, Object> entityMap) throws DataAccessException {
		return costDeptCostMapper.queryExists(entityMap);
	}

	@SuppressWarnings("unchecked")
	private void paraData(Map<String, Object> para_map, Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap,
	        Map<String, Object> deptParaSumNumMap, Map<String, Object> deptNumMap_1, Map<String, Object> deptNumMap_2) {

		// 管理服务科室分摊配置集合
		List<Map<String, Object>> deptParaData = (List<Map<String, Object>>) costDeptCostMapper.queryDeptParaData(para_map);

		// 管理受益科室分摊配置集合
		List<Map<String, Object>> serverDeptParaData = (List<Map<String, Object>>) costDeptCostMapper.queryServerDeptParaData(para_map);

		// 判断服务科室与受益科室是否有当量 有则为true 否则为false
		Map<String, Boolean> charge_flag = new HashMap<String, Boolean>();
		double sum_amount = 0.0;
		for (Map<String, Object> dept : deptParaData) {
			if (sum_amount != 0.0) {
				charge_flag.put(dept.get("dept_id").toString() + dept.get("para_code"), true);
				sum_amount = 0.0;
				break;
			}
			for (Map<String, Object> map : serverDeptParaData) {

				sum_amount = (Double) (null == deptNumMap_2.get(dept.get("dept_id").toString() + dept.get("dept_no") + map.get("server_dept_id")
				        + map.get("server_dept_no") + dept.get("para_code")) ? 0.0 : deptNumMap_2.get(dept.get("dept_id").toString()
				        + dept.get("dept_no") + map.get("server_dept_id") + map.get("server_dept_no") + dept.get("para_code")));
				if (sum_amount != 0.0) {
					charge_flag.put(dept.get("dept_id").toString() + dept.get("para_code"), true);
					break;
				}
			}
			charge_flag.put(dept.get("dept_id").toString() + dept.get("para_code"), false);

		}

		// 管理 服务科室与受益科室进行笛卡尔乘积
		for (Map<String, Object> dept : deptParaData) {

			Map<String, Object> deptPara = (Map<String, Object>) paraDataMap.get(dept.get("para_type_code").toString());

			List<Map<String, Object>> data = (List<Map<String, Object>>) deptPara.get(dept.get("dept_id").toString());

			data = (null == data) ? new ArrayList<Map<String, Object>>() : data;

			boolean v_amount = (null == charge_flag.get(dept.get("dept_id").toString() + dept.get("para_code")) ? false : charge_flag.get(dept
			        .get("dept_id").toString() + dept.get("para_code")));
			double num_amount = 0;
			for (Map<String, Object> serverDept : serverDeptParaData) {

				if (dept.get("bill_code").toString().equals(serverDept.get("bill_code").toString())) {

					Map<String, Object> para = new HashMap<String, Object>();

					para.putAll(dept);

					para.putAll(serverDept);

					data.add(para);

					deptPara.put(dept.get("dept_id").toString(), data);

					paraDataMap.put(dept.get("para_type_code").toString(), deptPara);

					Double d_amount = 0.0;

					// 有匹配的关系的取比例关系
					if (null != deptNumMap_2.get(dept.get("dept_id").toString() + dept.get("dept_no") + serverDept.get("server_dept_id")
					        + serverDept.get("server_dept_no") + dept.get("para_code"))) {
						d_amount = (Double) deptNumMap_2.get(dept.get("dept_id").toString() + dept.get("dept_no") + serverDept.get("server_dept_id")
						        + serverDept.get("server_dept_no") + dept.get("para_code"));

					} else {
						// 自定义 人员 面积 参数
						if (null != deptNumMap_1.get(serverDept.get("server_dept_id").toString() + serverDept.get("server_dept_no")
						        + dept.get("para_code"))) {
							d_amount = (Double) (deptNumMap_1.get(serverDept.get("server_dept_id").toString() + serverDept.get("server_dept_no")
							        + dept.get("para_code")));

						} else {

							/*
							 * if (!v_amount) { // 全院也统计不到的 取按人员取数 d_amount =
							 * (Double) (null ==
							 * deptNumMap_1.get(serverDept.get(
							 * "server_dept_id").toString() +
							 * serverDept.get("server_dept_no") + "01") ? 0.0 :
							 * deptNumMap_1.get(serverDept.get("server_dept_id")
							 * .toString() + serverDept.get("server_dept_no") +
							 * "01")); }
							 */

						}

						/*
						 * if (dept.get("para_code").toString().equals("01") ||
						 * dept.get("para_code").toString().equals("02") ||
						 * dept.get("para_code").toString().equals("03")) { //
						 * 没有对应关系的 取 全院总数 分摊参数不变 d_amount = (Double) (null ==
						 * deptNumMap_1
						 * .get(serverDept.get("server_dept_id").toString() +
						 * serverDept.get("server_dept_no") +
						 * dept.get("para_code")) ? 0.0 :
						 * deptNumMap_1.get(serverDept
						 * .get("server_dept_id").toString() +
						 * serverDept.get("server_dept_no") +
						 * dept.get("para_code"))); }
						 */
					}

					deptParaNumMap.put(dept.get("dept_id").toString() + dept.get("dept_no") + serverDept.get("server_dept_id")
					                           + serverDept.get("server_dept_no") + dept.get("para_code") + dept.get("para_type_code"),
					                   (d_amount.isNaN() ? 0.0 : d_amount.doubleValue()));

					num_amount = num_amount + (d_amount.isNaN() ? 0.0 : d_amount.doubleValue());

				}

			}
			deptParaSumNumMap.put(dept.get("dept_id").toString() + dept.get("dept_no") + dept.get("para_code") + dept.get("para_type_code"),
			                      num_amount);

		}
		charge_flag = null;
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public String check(Map<String, Object> entityMap) throws DataAccessException {
		// 开始时间
		long allstartTime = System.currentTimeMillis() / 1000; // 获取开始时间

		// 管理直接成本
		Set<CostDeptDriData> manDirData = new HashSet<CostDeptDriData>();

		// 医辅直接成本
		Set<CostDeptDriData> assDirData = new HashSet<CostDeptDriData>();

		// 医技直接成本
		Set<CostDeptDriData> medDirData = new HashSet<CostDeptDriData>();

		// 管理直接成本_平级分摊
		Set<CostDeptDriData> peerManDirData = new HashSet<CostDeptDriData>();

		// 医辅直接成本_平级分摊
		Set<CostDeptDriData> peerAssDirData = new HashSet<CostDeptDriData>();

		// 医技直接成本_平级分摊
		Set<CostDeptDriData> peerMedDirData = new HashSet<CostDeptDriData>();

		// 核算结果集集合
		Map<String, Object> costDeptCostMap = new HashMap<String, Object>();

		// 直接成本键值对集合
		Map<String, Object> dirMapData = new HashMap<String, Object>();

		// 各受益科室分摊比例数
		Map<String, Object> deptParaNumMap = new HashMap<String, Object>();

		// 各服务科室分摊比例数
		Map<String, Object> deptParaSumNumMap = new HashMap<String, Object>();

		// 平级分摊科室集合
		Map<String, Object> peerDeptMap = new HashMap<String, Object>();

		List<CostDeptCostCheck> checkData_man = new ArrayList<CostDeptCostCheck>();
		List<CostDeptCostCheck> checkData_ass = new ArrayList<CostDeptCostCheck>();
		List<CostDeptCostCheck> checkData_med = new ArrayList<CostDeptCostCheck>();

		/**
		 * 获取核算年月各科室直接成本
		 * 
		 * @param entityMap
		 *            页面传递参数
		 * @param dirMapData
		 *            各科室项目直接成本MAP形式存储
		 * @param peerDeptMap
		 *            平级分摊科室集合
		 * @param costDeptCostMap
		 *            成本分摊结果集
		 * @param manDirData
		 *            普通管理直接成本
		 * @param assDirData
		 *            普通医辅直接成本
		 * @param medDirData
		 *            普通医技直接成本
		 * @param peerManDirData
		 *            平级管理直接成本
		 * @param peerAssDirData
		 *            平级医辅直接成本
		 * @param peerMedDirData
		 *            平级医技直接成本
		 */
		dirDeptData(entityMap, dirMapData, peerDeptMap, costDeptCostMap, manDirData, assDirData, medDirData, peerManDirData, peerAssDirData,
		            peerMedDirData);

		// 各受益科室分摊参数比例数
		List<Map<String, Object>> deptNumAmount = (List<Map<String, Object>>) costDeptCostMapper.queryDeptNumAmount(entityMap);

		// 人员 面积
		Map<String, Object> deptNumMap_1 = new HashMap<String, Object>();

		// 服务量 工作量 收入 自定义
		Map<String, Object> deptNumMap_2 = new HashMap<String, Object>();

		long t1 = System.currentTimeMillis() / 1000; // 获取开始时间
		StringBuilder servKey=null;
		for (Map<String, Object> sermap : deptNumAmount) {
			if (sermap.get("dept_id").toString().equals(sermap.get("server_dept_id").toString())) {

				 servKey = new StringBuilder(100);

				servKey.append("");

				servKey.append(sermap.get("dept_id"));

				servKey.append(sermap.get("dept_no"));

				servKey.append(sermap.get("para_code").toString());

				deptNumMap_1.put(servKey.toString(), Double.valueOf(sermap.get("num_amount").toString()));
			} else {
				servKey = new StringBuilder(100);

				servKey.append("");

				servKey.append(sermap.get("dept_id"));

				servKey.append(sermap.get("dept_no"));

				servKey.append(sermap.get("server_dept_id"));

				servKey.append(sermap.get("server_dept_no"));

				servKey.append(sermap.get("para_code").toString());

				deptNumMap_2.put(servKey.toString(), Double.valueOf(sermap.get("num_amount").toString()));
			}

		}
		long t2 = System.currentTimeMillis() / 1000; // 获取开始时间

		logger.debug("受益科室分摊比例数交互时间：" + (t2 - t1) + "s");

		// 各级分摊类型集合
		Map<String, Object> paraDataMap = new HashMap<String, Object>();

		// 分摊类型字典
		List<CostParaType> cpt = (List<CostParaType>) costParaTypeMapper.query(entityMap);
		Map<String, Object> map=null;
		List<Map<String, Object>> data=null;
		for (CostParaType type : cpt) {

			 map = new HashMap<String, Object>();

			 data = new ArrayList<Map<String, Object>>();

			map.put("cost", data);

			paraDataMap.put(type.getType_code(), map);

		}

		Map<String, Object> manPara = new HashMap<String, Object>();

		manPara = entityMap;

		manPara.put("cost_type_code", "01");

		manPara.put("bill_type", "01");

		paraData(manPara, paraDataMap, deptParaNumMap, deptParaSumNumMap, deptNumMap_1, deptNumMap_2);

		Map<String, Object> assPara = new HashMap<String, Object>();

		assPara = entityMap;

		assPara.put("cost_type_code", "02");

		assPara.put("bill_type", "02");

		paraData(assPara, paraDataMap, deptParaNumMap, deptParaSumNumMap, deptNumMap_1, deptNumMap_2);

		Map<String, Object> medPara = new HashMap<String, Object>();

		medPara = entityMap;

		medPara.put("cost_type_code", "03");

		medPara.put("bill_type", "03");

		paraData(medPara, paraDataMap, deptParaNumMap, deptParaSumNumMap, deptNumMap_1, deptNumMap_2);

		logger.debug("|*******************************************************管理分摊开始*****************************************|");

		// 开始时间
		long startTime = System.currentTimeMillis() / 1000; // 获取开始时间
		if (peerManDirData.size() > 0) {
			// 管理平级分摊
			peerManParaCost(entityMap, peerManDirData, dirMapData, paraDataMap, deptParaNumMap, deptParaSumNumMap, costDeptCostMap, peerDeptMap,
			                manDirData, assDirData, medDirData, peerAssDirData, peerMedDirData, checkData_man);
		}
		if (manDirData.size() > 0) {
			// 管理普通分摊
			manParaCost(entityMap, manDirData, dirMapData, paraDataMap, deptParaNumMap, deptParaSumNumMap, costDeptCostMap, peerDeptMap, assDirData,
			            medDirData, peerAssDirData, peerMedDirData, checkData_man);
		}
		long endTime = System.currentTimeMillis() / 1000; // 获取结束时间

		String manTime = "管理分摊时间： " + (endTime - startTime) + "s";

		manDirData = null;

		logger.debug("|*******************************************************管理分摊结束*****************************************|");

		// 清除文件
		createFile(entityMap);

		if (checkData_man.size() > 0) {
			// 生成校验文件
			saveCheckData(entityMap, checkData_man);
		}
		checkData_man = null;

		// 医辅分摊
		logger.debug("|*******************************************************医辅分摊开始*****************************************|");

		startTime = System.currentTimeMillis() / 1000; // 获取开始时间
		if (peerAssDirData.size() > 0) {
			// 医辅平级分摊
			peerAssParaCost(entityMap, peerAssDirData, dirMapData, paraDataMap, deptParaNumMap, deptParaSumNumMap, costDeptCostMap, peerDeptMap,
			                assDirData, medDirData, peerMedDirData, checkData_ass);
		}
		if (assDirData.size() > 0) {
			// 医辅普通分摊
			assParaCost(entityMap, assDirData, dirMapData, paraDataMap, deptParaNumMap, deptParaSumNumMap, costDeptCostMap, peerDeptMap, medDirData,
			            peerMedDirData, checkData_ass);
		}

		endTime = System.currentTimeMillis() / 1000; // 获取结束时间

		String assTime = "医辅分摊时间： " + (endTime - startTime) + "s";

		assDirData = null;

		logger.debug("|*******************************************************医辅分摊结束*****************************************|");

		if (checkData_ass.size() > 0) {
			// 生成校验文件
			saveCheckData(entityMap, checkData_ass);
		}

		checkData_ass = null;

		// 医技分摊
		logger.debug("|*******************************************************医技分摊开始*****************************************|");

		startTime = System.currentTimeMillis() / 1000; // 获取开始时间
		if (peerMedDirData.size() > 0) {
			// 医技平级分摊
			peerMedParaCost(entityMap, peerMedDirData, medDirData, dirMapData, paraDataMap, deptParaNumMap, deptParaSumNumMap, costDeptCostMap,
			                peerDeptMap, checkData_med);
		}
		if (medDirData.size() > 0) {
			// 医技普通分摊
			medParaCost(entityMap, medDirData, dirMapData, paraDataMap, deptParaNumMap, deptParaSumNumMap, costDeptCostMap, peerDeptMap,
			            checkData_med);

		}

		endTime = System.currentTimeMillis() / 1000; // 获取结束时间

		String medTime = "医技分摊时间： " + (endTime - startTime) + "s";

		medDirData = null;

		logger.debug("|*******************************************************医技分摊结束*****************************************|");

		if (checkData_med.size() > 0) {
			// 生成校验文件
			saveCheckData(entityMap, checkData_med);
		}

		checkData_med = null;

		logger.debug("|***********************************************************************************************************|");

		try {

			
			long allendTime = System.currentTimeMillis() / 1000; // 获取结束时间
			logger.debug("|************************************************************************************************|");
			logger.debug("|" + manTime);
			logger.debug("|");
			logger.debug("|" + assTime);
			logger.debug("|");
			logger.debug("|" + medTime);
			logger.debug("|");
			logger.debug("|数据库交互时间： " + (endTime - startTime) + "s");
			logger.debug("|");
			logger.debug("|分摊总时间： " + (allendTime - allstartTime) + "s");
			logger.debug("|************************************************************************************************|");

			return "{\"msg\":\"校验成功.\",\"state\":\"true\"}";

		}
		catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"校验失败\"}");

		}
	}

	@SuppressWarnings({ "unused" })
	private void saveData(Map<String, Object> entityMap, Map<String, Object> costDeptCostMap) {

		logger.debug("|**************************************************清除原有数据开始***************************************|");

		delete(entityMap);

		logger.debug("|**************************************************清除原有数据结束***************************************|");

		int count = 500;

		List<CostDeptCost> costList = new ArrayList<CostDeptCost>(count);

		logger.debug("|**************************************************与数据库进行交互***************************************|");

		for (String key : costDeptCostMap.keySet()) {

			costList.add((CostDeptCost) costDeptCostMap.get(key));

			if (costList.size() == count) {

				logger.debug("|**************************************************批量插入**************************************|");

				costDeptCostMapper.addBatch(costList);

				costList = new ArrayList<CostDeptCost>(count);

			}
		}
		if (costList.size() > 0) {
			costDeptCostMapper.addBatch(costList);
		}

		logger.debug("|**************************************************交互结束***************************************|");

	}

	private void createFile(Map<String, Object> entityMap) {
		// 删除已存在的目录
		FileUtil.deleteFolder(configUrl + "costfile\\服务科室分摊过程\\" + entityMap.get("group_id") + "\\" + entityMap.get("hos_id") + "\\"
		        + entityMap.get("copy_code") + "\\" + entityMap.get("acc_year") + "\\" + entityMap.get("acc_month"));

	}

	@SuppressWarnings({ "rawtypes", "unchecked", "unused" })
	private void saveCheckData(Map<String, Object> entityMap, List<CostDeptCostCheck> checkData) {
		List<List> excel = new ArrayList<List>();
		List top = new ArrayList();
		top.add("服务科室名称");
		top.add("受益科室名称");
		top.add("成本项目名称");
		top.add("资金来源名称");
		top.add("分摊性质");
		top.add("分摊类型");
		top.add("服务科室直接成本");
		top.add("分摊管理直接成本");
		top.add("比例");
		top.add("总比例");
		top.add("分摊医辅直接成本");
		top.add("比例");
		top.add("总比例");
		top.add("分摊医技直接成本");
		top.add("比例");
		top.add("总比例");
		top.add("间接分摊医辅管理成本");
		top.add("比例");
		top.add("总比例");
		top.add("间接分摊医技管理成本");
		top.add("比例");
		top.add("总比例");
		top.add("间接分摊医技医辅管理成本");
		top.add("比例");
		top.add("总比例");
		top.add("间接分摊医技医辅成本");
		top.add("比例");
		top.add("总比例");

		Map<String, Object> dept_para_data = new HashMap<String, Object>();
		Map<String, Object> serverdept_para_data = new HashMap<String, Object>();

		for (CostDeptCostCheck check : checkData) { // 按照服务科室存储文件 //
			List<List> dept_data = (List<List>) dept_para_data.get(check.getDept_name());
			List<List> server_data = (List<List>) serverdept_para_data.get(check.getServer_dept_name());
			if (null == dept_data) {
				dept_data = new ArrayList<List>();
			}
			if (null == server_data) {
				server_data = new ArrayList<List>();
			}
			List server_list = new ArrayList();
			server_list.add(check.getDept_name());
			server_list.add(check.getServer_dept_name());
			server_list.add(check.getCost_item_name());
			server_list.add(check.getSource_name());
			server_list.add(check.getPara_name());
			server_list.add(check.getPara_type_name());
			server_list.add(check.getDir_amount());
			server_list.add(check.getDir_man_amount());
			server_list.add(check.getDir_man_amount_bl());
			server_list.add(check.getDir_man_amount_bl_sum());
			server_list.add(check.getDir_ass_amount());
			server_list.add(check.getDir_ass_amount_bl());
			server_list.add(check.getDir_ass_amount_bl_sum());
			server_list.add(check.getDir_med_amount());
			server_list.add(check.getDir_med_amount_bl());
			server_list.add(check.getDir_med_amount_bl_sum());
			server_list.add(check.getIndir_ass_man_amount());
			server_list.add(check.getIndir_ass_man_amount_bl());
			server_list.add(check.getIndir_ass_man_amount_bl_sum());
			server_list.add(check.getIndir_med_man_amount());
			server_list.add(check.getIndir_med_man_amount_bl());
			server_list.add(check.getIndir_med_man_amount_bl_sum());
			server_list.add(check.getIndir_ass_med_man_amount());
			server_list.add(check.getIndir_ass_med_man_amount_bl());
			server_list.add(check.getIndir_ass_med_man_amount_bl_sum());
			server_list.add(check.getIndir_med_ass_amount());
			server_list.add(check.getIndir_med_ass_amount_bl());
			server_list.add(check.getIndir_med_ass_amount_bl_sum());

			dept_data.add(server_list);
			server_data.add(server_list);

			serverdept_para_data.put(check.getServer_dept_name(), server_data);
			dept_para_data.put(check.getDept_name(), dept_data);

		}
		List<List> excel_dept =null;
		for (String v_key : dept_para_data.keySet()) {
			// 生成各服务科室分摊过程
			List<List> data = (List<List>) dept_para_data.get(v_key);
			excel_dept = new ArrayList<List>();
			excel_dept.add(top);
			excel_dept.addAll(data);

			ExcelWriter.exportExcel(new File(configUrl + "costfile\\服务科室分摊过程\\" + entityMap.get("group_id") + "\\" + entityMap.get("hos_id") + "\\"
			                                + entityMap.get("copy_code") + "\\" + entityMap.get("acc_year") + "\\" + entityMap.get("acc_month")
			                                + "\\" + v_key + ".xls"),
			                        excel_dept);

		}
		for (String v_key : serverdept_para_data.keySet()) {
			// 生成各服务科室分摊过程
			List<List> data = (List<List>) serverdept_para_data.get(v_key);
			excel_dept = new ArrayList<List>();
			excel_dept.add(top);
			excel_dept.addAll(data);
			// 生成各受益科室被分摊过程

			ExcelWriter.exportExcel(new File(configUrl + "costfile\\受益科室分摊过程\\" + entityMap.get("group_id") + "\\" + entityMap.get("hos_id") + "\\"
			                                + entityMap.get("copy_code") + "\\" + entityMap.get("acc_year") + "\\" + entityMap.get("acc_month")
			                                + "\\" + v_key + ".xls"),
			                        excel_dept);
		}

	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param peerDeptMap
	 *            平级分摊科室集合
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 * @param manDirData
	 *            普通管理直接成本
	 * @param assDirData
	 *            普通医辅直接成本
	 * @param medDirData
	 *            普通医技直接成本
	 * @param peerManDirData
	 *            平级管理直接成本
	 * @param peerAssDirData
	 *            平级医辅直接成本
	 * @param peerMedDirData
	 *            平级医技直接成本
	 */
	@SuppressWarnings("unchecked")
	private void dirDeptData(Map<String, Object> entityMap, Map<String, Object> dirMapData, Map<String, Object> peerDeptMap,
	        Map<String, Object> costDeptCostMap, Set<CostDeptDriData> manDirData, Set<CostDeptDriData> assDirData, Set<CostDeptDriData> medDirData,
	        Set<CostDeptDriData> peerManDirData, Set<CostDeptDriData> peerAssDirData, Set<CostDeptDriData> peerMedDirData) {

		// 取各科室直接成本数
		List<CostDeptDriData> allDeptDirData = (List<CostDeptDriData>) costDeptCostMapper.queryDeptDirData(entityMap);
		CostDeptCost data =null;
		StringBuilder dirKey=null;
		for (CostDeptDriData dir : allDeptDirData) {

			 data = new CostDeptCost();

			data.setGroup_id(dir.getGroup_id());

			data.setHos_id(dir.getHos_id());

			data.setCopy_code(dir.getCopy_code());

			data.setAcc_year(dir.getAcc_year());

			data.setAcc_month(dir.getAcc_month());

			data.setDept_id(dir.getDept_id());

			data.setDept_no(dir.getDept_no());

			data.setCost_item_id(dir.getCost_item_id());

			data.setCost_item_no(dir.getCost_item_no());

			data.setSource_id(dir.getSource_id());

			data.setDir_amount(dir.getDir_amount());

			data.setDir_ass_amount(0.0);

			data.setDir_man_amount(0.0);

			data.setDir_med_amount(0.0);

			data.setIndir_ass_man_amount(0.0);

			data.setIndir_med_man_amount(0.0);

			data.setIndir_ass_med_man_amount(0.0);

			data.setIndir_med_ass_amount(0.0);

			dirKey = new StringBuilder(100);

			dirKey.append("");

			dirKey.append(dir.getDept_id());

			dirKey.append(dir.getDept_no());

			dirKey.append(dir.getCost_item_id());

			dirKey.append(dir.getCost_item_no());

			dirKey.append(dir.getSource_id());

			dirMapData.put(dirKey.toString(), dir.getDir_amount());

			// 各科室直接成本数据
			costDeptCostMap.put(dirKey.toString(), data);

			if (dir.getType_code().equals("04")) {

				// 管理
				if (dir.getPeer_flag() == 0) {

					manDirData.add(dir);

				} else {

					peerDeptMap.put(dir.getDept_code(), dir.getDept_id());

					peerManDirData.add(dir);

				}

			} else if (dir.getType_code().equals("03")) {

				// 医辅
				if (dir.getPeer_flag() == 0) {

					assDirData.add(dir);

				} else {

					peerDeptMap.put(dir.getDept_code(), dir.getDept_id());

					peerAssDirData.add(dir);

				}

			} else if (dir.getType_code().equals("02")) {

				// 医技
				if (dir.getPeer_flag() == 0) {

					medDirData.add(dir);

				} else {

					peerDeptMap.put(dir.getDept_code(), dir.getDept_id());

					peerMedDirData.add(dir);

				}
			}
		}

		
	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param peerManDirData
	 *            管理科室直接成本结果集LIST形式存储
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param manData
	 *            管理分摊科室结果集 科室对应关系
	 * @param deptParaNumMap
	 *            受益科室分摊比例数
	 * @param deptParaSumNumMap
	 *            服务科室分摊比例总数
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 * @param peerDeptMap
	 *            平级分摊科室集合
	 * @param manDirData
	 *            普通管理直接成本扩充
	 * @param assDirData
	 *            普通医辅直接成本扩充
	 * @param medDirData
	 *            普通医技直接成本扩充
	 * @param peerAssDirData
	 *            平级医辅直接成本扩充
	 * @param peerMedDirData
	 *            平级医技直接成本扩充
	 */
	@SuppressWarnings("unchecked")
	private void peerManParaCost(Map<String, Object> entityMap, Set<CostDeptDriData> peerManDirData, Map<String, Object> dirMapData,
	        Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap, Map<String, Object> deptParaSumNumMap,
	        Map<String, Object> costDeptCostMap, Map<String, Object> peerDeptMap, Set<CostDeptDriData> manDirData, Set<CostDeptDriData> assDirData,
	        Set<CostDeptDriData> medDirData, Set<CostDeptDriData> peerAssDirData, Set<CostDeptDriData> peerMedDirData,
	        List<CostDeptCostCheck> checkData) {
		StringBuilder manCostKey=null;
		CostDeptCost oldCost =null;
		StringBuilder manServerCostKey=null;
		StringBuilder v_deptParaNumMap=null;
		StringBuilder v_deptParaSumNumMap=null;
		CostDeptCost data =null;
		CostDeptDriData newData=null;
		CostDeptDriData medDataDir=null;
		CostDeptCostCheck check=null;
		for (CostDeptDriData manDir : peerManDirData) {

			manCostKey = new StringBuilder();

			manCostKey.append(manDir.getDept_id().toString());

			manCostKey.append(manDir.getDept_no().toString());

			manCostKey.append(manDir.getCost_item_id().toString());

			manCostKey.append(manDir.getCost_item_no().toString());

			manCostKey.append(manDir.getSource_id().toString());

			Map<String, Object> typeData = (Map<String, Object>) paraDataMap.get(manDir.getPara_type_code());
			if (null == typeData) {
				logger.debug("分摊类型：" + manDir.getPara_type_code() + " 为找到相对应的服务科室");
				continue;
			}

			List<Map<String, Object>> deptData = (List<Map<String, Object>>) typeData.get(manDir.getDept_id().toString());
			if (null == deptData) {
				logger.debug("当前科室：" + manDir.getDept_name() + " 对应的分摊类型：" + manDir.getPara_type_code() + "为找到相对应的受益科室");
				continue;
			}
			
			for (Map<String, Object> man : deptData) {

				manServerCostKey = new StringBuilder(100);

				manServerCostKey.append(man.get("server_dept_id").toString());

				manServerCostKey.append(man.get("server_dept_no").toString());

				manServerCostKey.append(manDir.getCost_item_id().toString());

				manServerCostKey.append(manDir.getCost_item_no().toString());

				manServerCostKey.append(manDir.getSource_id().toString());

				v_deptParaNumMap = new StringBuilder(100);
				v_deptParaNumMap.append(man.get("dept_id").toString());
				v_deptParaNumMap.append(man.get("dept_no"));
				v_deptParaNumMap.append(man.get("server_dept_id"));
				v_deptParaNumMap.append(man.get("server_dept_no"));
				v_deptParaNumMap.append(man.get("para_code"));
				v_deptParaNumMap.append(man.get("para_type_code"));

				v_deptParaSumNumMap = new StringBuilder(100);
				v_deptParaSumNumMap.append(man.get("dept_id").toString());
				v_deptParaSumNumMap.append(man.get("dept_no"));
				v_deptParaSumNumMap.append(man.get("para_code"));
				v_deptParaSumNumMap.append(man.get("para_type_code"));

				Object obj = deptParaNumMap.get(v_deptParaNumMap.toString());

				Object sum = deptParaSumNumMap.get(v_deptParaSumNumMap.toString());

				obj = (null == obj) ? 0 : obj;

				sum = (null == sum) ? 0 : sum;

				// 受益科室分摊比例数
				double server_num = Double.parseDouble(obj.toString());

				// 服务科室分摊比例数
				double dept_num = Double.parseDouble(sum.toString());

				if (dept_num == 0 || server_num == 0) {

					continue;

				}

				if (null != costDeptCostMap.get(manServerCostKey.toString())) {

					// 取受益科室本次分摊前各成本数据
					oldCost = new CostDeptCost();

					oldCost = (CostDeptCost) costDeptCostMap.get(manServerCostKey.toString());

					// 分摊前管理成本
					double old_dir_man_amount = oldCost.getDir_man_amount().isNaN() ? 0.0 : oldCost.getDir_man_amount();

					data = new CostDeptCost();

					data.setGroup_id(manDir.getGroup_id());

					data.setHos_id(manDir.getHos_id());

					data.setCopy_code(manDir.getCopy_code());

					data.setAcc_year(manDir.getAcc_year());

					data.setAcc_month(manDir.getAcc_month());

					data.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

					data.setCost_item_id(manDir.getCost_item_id());

					data.setCost_item_no(manDir.getCost_item_no());

					data.setSource_id(manDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(manServerCostKey.toString()) ? dirMapData.get(manServerCostKey.toString())
					        : 0.0);

					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					data.setDir_ass_amount(0.0);

					// 直接分摊管理成本
					Double d_dir_man_amount = manDir.getDir_amount().isNaN() ? 0.0 : PreciseCompute.div(manDir.getDir_amount().doubleValue()
					        * server_num, dept_num, 18);

					data.setDir_man_amount(old_dir_man_amount + (d_dir_man_amount.isNaN() ? 0.0 : d_dir_man_amount));

					data.setDir_med_amount(0.0);

					data.setIndir_ass_man_amount(0.0);

					data.setIndir_med_man_amount(0.0);

					data.setIndir_ass_med_man_amount(0.0);

					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(manServerCostKey.toString(), data);

				} else {

					data = new CostDeptCost();

					data.setGroup_id(manDir.getGroup_id());

					data.setHos_id(manDir.getHos_id());

					data.setCopy_code(manDir.getCopy_code());

					data.setAcc_year(manDir.getAcc_year());

					data.setAcc_month(manDir.getAcc_month());

					data.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

					data.setCost_item_id(manDir.getCost_item_id());

					data.setCost_item_no(manDir.getCost_item_no());

					data.setSource_id(manDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(manServerCostKey.toString()) ? dirMapData.get(manServerCostKey.toString())
					        : 0.0);

					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					data.setDir_ass_amount(0.0);

					// 直接分摊管理成本
					Double peerdir_man_amount = manDir.getDir_amount().isNaN() ? 0.0 : PreciseCompute.div(manDir.getDir_amount().doubleValue()
					        * server_num, dept_num, 18);

					data.setDir_man_amount(peerdir_man_amount.isNaN() ? 0.0 : peerdir_man_amount);

					data.setDir_med_amount(0.0);

					data.setIndir_ass_man_amount(0.0);

					data.setIndir_med_man_amount(0.0);

					data.setIndir_ass_med_man_amount(0.0);

					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(manServerCostKey.toString(), data);

					if (man.get("server_type_code").toString().equals("04")) {

						newData = new CostDeptDriData();

						newData.setGroup_id(manDir.getGroup_id());

						newData.setHos_id(manDir.getHos_id());

						newData.setCopy_code(manDir.getCopy_code());

						newData.setAcc_year(manDir.getAcc_year());

						newData.setAcc_month(manDir.getAcc_month());

						newData.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

						newData.setDept_code(man.get("server_dept_code").toString());

						newData.setDept_name(man.get("server_dept_name").toString());

						newData.setCost_item_id(manDir.getCost_item_id());

						newData.setCost_item_no(manDir.getCost_item_no());

						newData.setCost_item_code(manDir.getCost_item_code());

						newData.setCost_item_name(manDir.getCost_item_name());

						newData.setSource_id(manDir.getSource_id());

						newData.setSource_code(manDir.getSource_code());

						newData.setSource_name(manDir.getSource_name());

						newData.setType_code(man.get("server_type_code").toString());

						newData.setNatur_code(man.get("server_natur_code").toString());

						newData.setDir_amount(0.0);

						newData.setPara_type_code(manDir.getPara_type_code());

						if (peerDeptMap.containsKey(man.get("server_dept_code").toString())) {

							if (!peerManDirData.contains(newData)) {

								peerManDirData.add(newData);

							}

						} else {

							if (!manDirData.contains(newData)) {

								manDirData.add(newData);

							}

						}

					}
					if (man.get("server_type_code").toString().equals("03")) {

						newData = new CostDeptDriData();

						newData.setGroup_id(manDir.getGroup_id());

						newData.setHos_id(manDir.getHos_id());

						newData.setCopy_code(manDir.getCopy_code());

						newData.setAcc_year(manDir.getAcc_year());

						newData.setAcc_month(manDir.getAcc_month());

						newData.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

						newData.setDept_code(man.get("server_dept_code").toString());

						newData.setDept_name(man.get("server_dept_name").toString());

						newData.setCost_item_id(manDir.getCost_item_id());

						newData.setCost_item_no(manDir.getCost_item_no());

						newData.setCost_item_code(manDir.getCost_item_code());

						newData.setCost_item_name(manDir.getCost_item_name());

						newData.setSource_id(manDir.getSource_id());

						newData.setSource_code(manDir.getSource_code());

						newData.setSource_name(manDir.getSource_name());

						newData.setType_code(man.get("server_type_code").toString());

						newData.setNatur_code(man.get("server_natur_code").toString());

						newData.setDir_amount(0.0);

						newData.setPara_type_code(manDir.getPara_type_code());

						if (peerDeptMap.containsKey(man.get("server_dept_code").toString())) {

							if (!peerAssDirData.contains(newData)) {

								peerAssDirData.add(newData);

							}

						} else {

							if (!assDirData.contains(newData)) {

								assDirData.add(newData);

							}

						}

					}

					if (man.get("server_type_code").toString().equals("02")) {

						medDataDir = new CostDeptDriData();

						medDataDir.setGroup_id(manDir.getGroup_id());

						medDataDir.setHos_id(manDir.getHos_id());

						medDataDir.setCopy_code(manDir.getCopy_code());

						medDataDir.setAcc_year(manDir.getAcc_year());

						medDataDir.setAcc_month(manDir.getAcc_month());

						medDataDir.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

						medDataDir.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

						medDataDir.setDept_code(man.get("server_dept_code").toString());

						medDataDir.setDept_name(man.get("server_dept_name").toString());

						medDataDir.setCost_item_id(manDir.getCost_item_id());

						medDataDir.setCost_item_no(manDir.getCost_item_no());

						medDataDir.setCost_item_code(manDir.getCost_item_code());

						medDataDir.setCost_item_name(manDir.getCost_item_name());

						medDataDir.setSource_id(manDir.getSource_id());

						medDataDir.setSource_code(manDir.getSource_code());

						medDataDir.setSource_name(manDir.getSource_name());

						medDataDir.setType_code(man.get("server_type_code").toString());

						medDataDir.setNatur_code(man.get("server_natur_code").toString());

						medDataDir.setDir_amount(0.0);

						medDataDir.setPara_type_code(manDir.getPara_type_code());

						if (peerDeptMap.containsKey(man.get("server_dept_code").toString())) {

							if (!peerMedDirData.contains(medDataDir)) {

								peerMedDirData.add(medDataDir);

							}

						} else {

							if (!medDirData.contains(medDataDir)) {

								medDirData.add(medDataDir);

							}

						}

					}

				}
				check = new CostDeptCostCheck();

				check.setGroup_id(manDir.getGroup_id());

				check.setHos_id(manDir.getHos_id());

				check.setCopy_code(manDir.getCopy_code());

				check.setAcc_year(manDir.getAcc_year());

				check.setAcc_month(manDir.getAcc_month());

				check.setDept_id(manDir.getDept_id());

				check.setDept_no(manDir.getDept_no());

				check.setDept_code(manDir.getDept_code());

				check.setDept_name(manDir.getDept_name());

				check.setType_code(manDir.getType_code());
				check.setType_name(manDir.getType_name());
				check.setNatur_code(manDir.getNatur_code());
				check.setNatur_name(manDir.getNatur_name());

				check.setServer_dept_id(Long.valueOf(man.get("server_dept_id").toString()));

				check.setServer_dept_no(Long.valueOf(man.get("server_dept_no").toString()));

				check.setServer_dept_code(man.get("server_dept_code").toString());

				check.setServer_dept_name(man.get("server_dept_name").toString());

				check.setCost_item_id(manDir.getCost_item_id());

				check.setCost_item_no(manDir.getCost_item_no());

				check.setCost_item_code(manDir.getCost_item_code());

				check.setCost_item_name(manDir.getCost_item_name());

				check.setSource_id(manDir.getSource_id());

				check.setSource_code(manDir.getSource_code());

				check.setSource_name(manDir.getSource_name());

				// 直接分摊管理成本
				Double d_dir_man_amount = PreciseCompute.div((manDir.getDir_amount().doubleValue() * server_num), dept_num, 18);

				// 服务科室直接成本
				check.setDir_amount(manDir.getDir_amount().isNaN() ? 0.0 : manDir.getDir_amount().doubleValue());

				check.setDir_man_amount((d_dir_man_amount.isNaN() ? 0.0 : d_dir_man_amount));
				check.setDir_man_amount_bl(server_num);
				check.setDir_man_amount_bl_sum(dept_num);
				check.setPara_type_code(manDir.getPara_type_code());
				check.setPara_type_name(manDir.getPara_type_name());
				check.setPara_code(manDir.getPara_code());
				check.setPara_name(manDir.getPara_name());
				checkData.add(check);
				manServerCostKey = null;

			}

		}

	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param manDirData
	 *            管理科室直接成本结果集LIST形式存储
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param manData
	 *            管理分摊科室结果集 科室对应关系
	 * @param deptParaNumMap
	 *            受益科室分摊比例数
	 * @param deptParaSumNumMap
	 *            服务科室分摊比例总数
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 * @param peerDeptMap
	 *            平级分摊科室集合
	 * @param assDirData
	 *            普通医辅直接成本扩充
	 * @param medDirData
	 *            普通医技直接成本扩充
	 * @param peerAssDirData
	 *            平级医辅直接成本扩充
	 * @param peerMedDirData
	 *            平级医技直接成本扩充
	 */
	@SuppressWarnings("unchecked")
	private void manParaCost(Map<String, Object> entityMap, Set<CostDeptDriData> manDirData, Map<String, Object> dirMapData,
	        Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap, Map<String, Object> deptParaSumNumMap,
	        Map<String, Object> costDeptCostMap, Map<String, Object> peerDeptMap, Set<CostDeptDriData> assDirData, Set<CostDeptDriData> medDirData,
	        Set<CostDeptDriData> peerAssDirData, Set<CostDeptDriData> peerMedDirData, List<CostDeptCostCheck> checkData) {
		StringBuilder manCostKey=null;
		StringBuilder manServerCostKey=null;
		StringBuilder v_deptParaNumMap=null;
		
		for (CostDeptDriData manDir : manDirData) {

			double dir_man_amount = 0;

			manCostKey = new StringBuilder(100);

			manCostKey.append(manDir.getDept_id().toString());

			manCostKey.append(manDir.getDept_no().toString());

			manCostKey.append(manDir.getCost_item_id().toString());

			manCostKey.append(manDir.getCost_item_no().toString());

			manCostKey.append(manDir.getSource_id().toString());

			// 取服务科室相对应的成本
			CostDeptCost deptCost = (CostDeptCost) costDeptCostMap.get(manCostKey.toString());

			// 分摊的管理成本
			dir_man_amount = deptCost.getDir_man_amount().isNaN() ? 0.0 : deptCost.getDir_man_amount();

			Map<String, Object> typeData = (Map<String, Object>) paraDataMap.get(manDir.getPara_type_code());
			if (null == typeData) {
				logger.debug("分摊类型：" + manDir.getPara_type_code() + " 为找到相对应的服务科室");
				continue;
			}

			List<Map<String, Object>> deptData = (List<Map<String, Object>>) typeData.get(manDir.getDept_id().toString());
			if (null == deptData) {
				logger.debug("当前科室：" + manDir.getDept_name() + " 对应的分摊类型：" + manDir.getPara_type_code() + "为找到相对应的受益科室");
				continue;
			}
			
			StringBuilder v_deptParaSumNumMap=null;
			CostDeptCost oldCost=null;
			CostDeptCost data =null;
			CostDeptDriData medDataDir=null;
			CostDeptCostCheck check=null;
			for (Map<String, Object> man : deptData) {

				manServerCostKey = new StringBuilder(100);

				manServerCostKey.append(man.get("server_dept_id").toString());

				manServerCostKey.append(man.get("server_dept_no").toString());

				manServerCostKey.append(manDir.getCost_item_id().toString());

				manServerCostKey.append(manDir.getCost_item_no().toString());

				manServerCostKey.append(manDir.getSource_id().toString());

				v_deptParaNumMap = new StringBuilder(100);
				v_deptParaNumMap.append(man.get("dept_id").toString());
				v_deptParaNumMap.append(man.get("dept_no"));
				v_deptParaNumMap.append(man.get("server_dept_id"));
				v_deptParaNumMap.append(man.get("server_dept_no"));
				v_deptParaNumMap.append(man.get("para_code"));
				v_deptParaNumMap.append(man.get("para_type_code"));

				v_deptParaSumNumMap = new StringBuilder(100);
				v_deptParaSumNumMap.append(man.get("dept_id").toString());
				v_deptParaSumNumMap.append(man.get("dept_no"));
				v_deptParaSumNumMap.append(man.get("para_code"));
				v_deptParaSumNumMap.append(man.get("para_type_code"));

				Object obj = deptParaNumMap.get(v_deptParaNumMap.toString());

				Object sum = deptParaSumNumMap.get(v_deptParaSumNumMap.toString());

				obj = (null == obj) ? 0 : obj;

				sum = (null == sum) ? 0 : sum;

				// 受益科室分摊比例数
				double server_num = Double.parseDouble(obj.toString());

				// 服务科室分摊比例数
				double dept_num = Double.parseDouble(sum.toString());

				if (dept_num == 0 || server_num == 0) {

					continue;

				}

				if (null != costDeptCostMap.get(manServerCostKey.toString())) {

					// 取受益科室本次分摊前各成本数据
					oldCost= new CostDeptCost();

					oldCost = (CostDeptCost) costDeptCostMap.get(manServerCostKey.toString());

					// 分摊前管理成本
					double old_dir_man_amount = oldCost.getDir_man_amount().isNaN() ? 0.0 : oldCost.getDir_man_amount();

					data = new CostDeptCost();

					data.setGroup_id(manDir.getGroup_id());

					data.setHos_id(manDir.getHos_id());

					data.setCopy_code(manDir.getCopy_code());

					data.setAcc_year(manDir.getAcc_year());

					data.setAcc_month(manDir.getAcc_month());

					data.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

					data.setCost_item_id(manDir.getCost_item_id());

					data.setCost_item_no(manDir.getCost_item_no());

					data.setSource_id(manDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(manServerCostKey.toString()) ? dirMapData.get(manServerCostKey.toString())
					        : 0.0);

					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					data.setDir_ass_amount(0.0);

					// 直接分摊管理成本+平级分摊成本
					Double d_dir_man_amount = PreciseCompute
					        .div(((manDir.getDir_amount().doubleValue() + dir_man_amount) * server_num), dept_num, 18);

					data.setDir_man_amount(old_dir_man_amount + (d_dir_man_amount.isNaN() ? 0.0 : d_dir_man_amount));

					data.setDir_med_amount(0.0);

					data.setIndir_ass_man_amount(0.0);

					data.setIndir_med_man_amount(0.0);

					data.setIndir_ass_med_man_amount(0.0);

					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(manServerCostKey.toString(), data);

				} else {

					data = new CostDeptCost();

					data.setGroup_id(manDir.getGroup_id());

					data.setHos_id(manDir.getHos_id());

					data.setCopy_code(manDir.getCopy_code());

					data.setAcc_year(manDir.getAcc_year());

					data.setAcc_month(manDir.getAcc_month());

					data.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

					data.setCost_item_id(manDir.getCost_item_id());

					data.setCost_item_no(manDir.getCost_item_no());

					data.setSource_id(manDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(manServerCostKey.toString()) ? dirMapData.get(manServerCostKey.toString())
					        : 0.0);

					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					data.setDir_ass_amount(0.0);

					// 直接分摊管理成本
					Double peerdir_man_amount = PreciseCompute
					        .div((manDir.getDir_amount().doubleValue() + dir_man_amount) * server_num, dept_num, 18);

					data.setDir_man_amount(peerdir_man_amount.isNaN() ? 0.0 : peerdir_man_amount);

					data.setDir_med_amount(0.0);

					data.setIndir_ass_man_amount(0.0);

					data.setIndir_med_man_amount(0.0);

					data.setIndir_ass_med_man_amount(0.0);

					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(manServerCostKey.toString(), data);

					if (man.get("server_type_code").toString().equals("03")) {

						CostDeptDriData newData = new CostDeptDriData();

						newData.setGroup_id(manDir.getGroup_id());

						newData.setHos_id(manDir.getHos_id());

						newData.setCopy_code(manDir.getCopy_code());

						newData.setAcc_year(manDir.getAcc_year());

						newData.setAcc_month(manDir.getAcc_month());

						newData.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

						newData.setDept_code(man.get("server_dept_code").toString());

						newData.setDept_name(man.get("server_dept_name").toString());

						newData.setCost_item_id(manDir.getCost_item_id());

						newData.setCost_item_no(manDir.getCost_item_no());

						newData.setCost_item_code(manDir.getCost_item_code());

						newData.setCost_item_name(manDir.getCost_item_name());

						newData.setSource_id(manDir.getSource_id());

						newData.setSource_code(manDir.getSource_code());

						newData.setSource_name(manDir.getSource_name());

						newData.setType_code(man.get("server_type_code").toString());

						newData.setNatur_code(man.get("server_natur_code").toString());

						newData.setDir_amount(0.0);

						newData.setPara_type_code(manDir.getPara_type_code());

						if (peerDeptMap.containsKey(man.get("server_dept_code").toString())) {

							if (!peerAssDirData.contains(newData)) {

								peerAssDirData.add(newData);

							}

						} else {

							if (!assDirData.contains(newData)) {

								assDirData.add(newData);

							}

						}

					}

					if (man.get("server_type_code").toString().equals("02")) {

						medDataDir = new CostDeptDriData();

						medDataDir.setGroup_id(manDir.getGroup_id());

						medDataDir.setHos_id(manDir.getHos_id());

						medDataDir.setCopy_code(manDir.getCopy_code());

						medDataDir.setAcc_year(manDir.getAcc_year());

						medDataDir.setAcc_month(manDir.getAcc_month());

						medDataDir.setDept_id(Long.valueOf(man.get("server_dept_id").toString()));

						medDataDir.setDept_no(Long.valueOf(man.get("server_dept_no").toString()));

						medDataDir.setDept_code(man.get("server_dept_code").toString());

						medDataDir.setDept_name(man.get("server_dept_name").toString());

						medDataDir.setCost_item_id(manDir.getCost_item_id());

						medDataDir.setCost_item_no(manDir.getCost_item_no());

						medDataDir.setCost_item_code(manDir.getCost_item_code());

						medDataDir.setCost_item_name(manDir.getCost_item_name());

						medDataDir.setSource_id(manDir.getSource_id());

						medDataDir.setSource_code(manDir.getSource_code());

						medDataDir.setSource_name(manDir.getSource_name());

						medDataDir.setType_code(man.get("server_type_code").toString());

						medDataDir.setNatur_code(man.get("server_natur_code").toString());

						medDataDir.setDir_amount(0.0);

						medDataDir.setPara_type_code(manDir.getPara_type_code());

						if (peerDeptMap.containsKey(man.get("server_dept_code").toString())) {

							if (!peerMedDirData.contains(medDataDir)) {

								peerMedDirData.add(medDataDir);

							}

						} else {

							if (!medDirData.contains(medDataDir)) {

								medDirData.add(medDataDir);

							}

						}

					}

				}

				check= new CostDeptCostCheck();

				check.setGroup_id(manDir.getGroup_id());

				check.setHos_id(manDir.getHos_id());

				check.setCopy_code(manDir.getCopy_code());

				check.setAcc_year(manDir.getAcc_year());

				check.setAcc_month(manDir.getAcc_month());

				check.setDept_id(manDir.getDept_id());

				check.setDept_no(manDir.getDept_no());

				check.setDept_code(manDir.getDept_code());

				check.setDept_name(manDir.getDept_name());

				check.setType_code(manDir.getType_code());
				check.setType_name(manDir.getType_name());
				check.setNatur_code(manDir.getNatur_code());
				check.setNatur_name(manDir.getNatur_name());

				check.setServer_dept_id(Long.valueOf(man.get("server_dept_id").toString()));

				check.setServer_dept_no(Long.valueOf(man.get("server_dept_no").toString()));

				check.setServer_dept_code(man.get("server_dept_code").toString());

				check.setServer_dept_name(man.get("server_dept_name").toString());

				check.setCost_item_id(manDir.getCost_item_id());

				check.setCost_item_no(manDir.getCost_item_no());

				check.setCost_item_code(manDir.getCost_item_code());

				check.setCost_item_name(manDir.getCost_item_name());

				check.setSource_id(manDir.getSource_id());

				check.setSource_code(manDir.getSource_code());

				check.setSource_name(manDir.getSource_name());

				// 直接分摊管理成本+平级分摊成本
				Double d_dir_man_amount = PreciseCompute.div(((manDir.getDir_amount().doubleValue() + dir_man_amount) * server_num), dept_num);

				// 服务科室直接成本
				check.setDir_amount(manDir.getDir_amount().isNaN() ? 0.0 : manDir.getDir_amount().doubleValue());

				check.setDir_man_amount((d_dir_man_amount.isNaN() ? 0.0 : d_dir_man_amount));
				check.setDir_man_amount_bl(server_num);
				check.setDir_man_amount_bl_sum(dept_num);
				check.setPara_type_code(manDir.getPara_type_code());
				check.setPara_type_name(manDir.getPara_type_name());
				check.setPara_code(manDir.getPara_code());
				check.setPara_name(manDir.getPara_name());
				checkData.add(check);
				manServerCostKey = null;

			}

		}
		
	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param peerAssDirData
	 *            医辅科室平级分摊
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param manData
	 *            医辅分摊科室结果集 科室对应关系
	 * @param deptParaNumMap
	 *            受益科室分摊比例数
	 * @param deptParaSumNumMap
	 *            服务科室分摊比例总数
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 * @param peerDeptMap
	 *            平级分摊科室集合
	 * @param assDirData
	 *            医辅直接成本扩充
	 * @param medDirData
	 *            医技直接成本扩充
	 * @param peerMedDirData
	 *            医技平级分摊直接成本扩充
	 * @param checkData
	 *            分摊过程集合
	 */
	@SuppressWarnings("unchecked")
	private void peerAssParaCost(Map<String, Object> entityMap, Set<CostDeptDriData> peerAssDirData, Map<String, Object> dirMapData,
	        Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap, Map<String, Object> deptParaSumNumMap,
	        Map<String, Object> costDeptCostMap, Map<String, Object> peerDeptMap, Set<CostDeptDriData> assDirData, Set<CostDeptDriData> medDirData,
	        Set<CostDeptDriData> peerMedDirData, List<CostDeptCostCheck> checkData) {
		StringBuilder assCostKey=null;
		StringBuilder assServerCostKey=null;
		StringBuilder v_deptParaNumMap=null;
		StringBuilder v_deptParaSumNumMap=null;
		CostDeptCost data=null;
		CostDeptDriData newData =null;
		CostDeptCostCheck check=null;
		for (CostDeptDriData assDir : peerAssDirData) {

			// 分摊管理成本
			double dir_man_amount = 0;

			assCostKey = new StringBuilder(100);

			assCostKey.append(assDir.getDept_id().toString());

			assCostKey.append(assDir.getDept_no().toString());

			assCostKey.append(assDir.getCost_item_id());

			assCostKey.append(assDir.getCost_item_no());

			assCostKey.append(assDir.getSource_id());

			// 服务科室各成本数
			CostDeptCost deptCost = (CostDeptCost) costDeptCostMap.get(assCostKey.toString());

			if (null != deptCost) {

				// 管理成本
				dir_man_amount = deptCost.getDir_man_amount().isNaN() ? 0.0 : deptCost.getDir_man_amount();

			}

			Map<String, Object> typeData = (Map<String, Object>) paraDataMap.get(assDir.getPara_type_code());

			if (null == typeData) {
				logger.debug("分摊类型：" + assDir.getPara_type_code() + " 为找到相对应的服务科室");
				continue;
			}

			List<Map<String, Object>> deptData = (List<Map<String, Object>>) typeData.get(assDir.getDept_id().toString());
			if (null == deptData) {
				logger.debug("当前科室：" + assDir.getDept_name() + " 对应的分摊类型：" + assDir.getPara_type_code() + "为找到相对应的受益科室");
				continue;
			}
			
			for (Map<String, Object> ass : deptData) {

			    assServerCostKey = new StringBuilder(100);

				assServerCostKey.append(ass.get("server_dept_id").toString());

				assServerCostKey.append(ass.get("server_dept_no").toString());

				assServerCostKey.append(assDir.getCost_item_id());

				assServerCostKey.append(assDir.getCost_item_no());

				assServerCostKey.append(assDir.getSource_id());

			    v_deptParaNumMap = new StringBuilder(100);
				v_deptParaNumMap.append(ass.get("dept_id").toString());
				v_deptParaNumMap.append(ass.get("dept_no"));
				v_deptParaNumMap.append(ass.get("server_dept_id"));
				v_deptParaNumMap.append(ass.get("server_dept_no"));
				v_deptParaNumMap.append(ass.get("para_code"));
				v_deptParaNumMap.append(ass.get("para_type_code"));

				v_deptParaSumNumMap = new StringBuilder();
				v_deptParaSumNumMap.append(ass.get("dept_id").toString());
				v_deptParaSumNumMap.append(ass.get("dept_no"));
				v_deptParaSumNumMap.append(ass.get("para_code"));
				v_deptParaSumNumMap.append(ass.get("para_type_code"));

				Object obj = deptParaNumMap.get(v_deptParaNumMap.toString());

				Object sum = deptParaSumNumMap.get(v_deptParaSumNumMap.toString());

				obj = (null == obj) ? 0 : obj;

				sum = (null == sum) ? 0 : sum;

				// 受益科室分摊比例数
				double server_num = Double.parseDouble(obj.toString());

				// 服务科室分摊比例数
				double dept_num = Double.parseDouble(sum.toString());

				if (dept_num == 0 || server_num == 0) {

					continue;

				}

				if (null != costDeptCostMap.get(assServerCostKey.toString())) {

					// 受益科室本次分摊前各成本数
					CostDeptCost oldServerCost = (CostDeptCost) costDeptCostMap.get(assServerCostKey.toString());

					double old_dir_man_amount = oldServerCost.getDir_man_amount().isNaN() ? 0.0 : oldServerCost.getDir_man_amount();

					double old_dir_ass_amount = oldServerCost.getDir_ass_amount().isNaN() ? 0.0 : oldServerCost.getDir_ass_amount();

					double old_indir_ass_man_amount = oldServerCost.getIndir_ass_man_amount().isNaN() ? 0.0 : oldServerCost.getIndir_ass_man_amount();

					data = new CostDeptCost();

					data.setGroup_id(assDir.getGroup_id());

					data.setHos_id(assDir.getHos_id());

					data.setCopy_code(assDir.getCopy_code());

					data.setAcc_year(assDir.getAcc_year());

					data.setAcc_month(assDir.getAcc_month());

					data.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

					data.setCost_item_id(assDir.getCost_item_id());

					data.setCost_item_no(assDir.getCost_item_no());

					data.setSource_id(assDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(assServerCostKey.toString()) ? dirMapData.get(assServerCostKey.toString())
					        : 0.0);

					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(old_dir_ass_amount
					        + (assDir.getDir_amount().isNaN() ? 0.0 : PreciseCompute.div(assDir.getDir_amount().doubleValue() * server_num, dept_num,
					                                                                     18)));

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(old_indir_ass_man_amount + PreciseCompute.div((dir_man_amount * server_num), dept_num, 18));

					// 直接分摊管理成本
					data.setDir_man_amount(old_dir_man_amount);

					// 直接分摊医技成本
					data.setDir_med_amount(0.0);

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(0.0);

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(0.0);

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(assServerCostKey.toString(), data);

				} else {

					data = new CostDeptCost();

					data.setGroup_id(assDir.getGroup_id());

					data.setHos_id(assDir.getHos_id());

					data.setCopy_code(assDir.getCopy_code());

					data.setAcc_year(assDir.getAcc_year());

					data.setAcc_month(assDir.getAcc_month());

					data.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

					data.setCost_item_id(assDir.getCost_item_id());

					data.setCost_item_no(assDir.getCost_item_no());

					data.setSource_id(assDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(assServerCostKey.toString()) ? dirMapData.get(assServerCostKey.toString())
					        : 0.0);

					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(assDir.getDir_amount().isNaN() ? 0.0 : PreciseCompute.div(assDir.getDir_amount().doubleValue()
					        * server_num, dept_num, 18));

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

					// 直接分摊管理成本
					data.setDir_man_amount(0.0);

					// 直接分摊管理成本
					data.setDir_med_amount(0.0);

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(0.0);

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(0.0);

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(assServerCostKey.toString(), data);

					if (ass.get("server_type_code").toString().equals("03")) {

						newData = new CostDeptDriData();

						newData.setGroup_id(assDir.getGroup_id());

						newData.setHos_id(assDir.getHos_id());

						newData.setCopy_code(assDir.getCopy_code());

						newData.setAcc_year(assDir.getAcc_year());

						newData.setAcc_month(assDir.getAcc_month());

						newData.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

						newData.setDept_code(ass.get("server_dept_code").toString());

						newData.setDept_name(ass.get("server_dept_name").toString());

						newData.setCost_item_id(assDir.getCost_item_id());

						newData.setCost_item_no(assDir.getCost_item_no());

						newData.setCost_item_code(assDir.getCost_item_code());

						newData.setCost_item_name(assDir.getCost_item_name());

						newData.setSource_id(assDir.getSource_id());

						newData.setSource_code(assDir.getSource_code());

						newData.setSource_name(assDir.getSource_name());

						newData.setType_code(ass.get("server_type_code").toString());

						newData.setNatur_code(ass.get("server_natur_code").toString());

						newData.setPara_type_code(assDir.getPara_type_code());

						newData.setDir_amount(0.0);

						if (peerDeptMap.containsKey(ass.get("server_dept_code").toString())) {

							if (!peerAssDirData.contains(newData)) {

								peerAssDirData.add(newData);

							}

						} else {

							if (!assDirData.contains(newData)) {

								assDirData.add(newData);

							}

						}

					}

					if (ass.get("server_type_code").toString().equals("02")) {

						newData = new CostDeptDriData();

						newData.setGroup_id(assDir.getGroup_id());

						newData.setHos_id(assDir.getHos_id());

						newData.setCopy_code(assDir.getCopy_code());

						newData.setAcc_year(assDir.getAcc_year());

						newData.setAcc_month(assDir.getAcc_month());

						newData.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

						newData.setDept_code(ass.get("server_dept_code").toString());

						newData.setDept_name(ass.get("server_dept_name").toString());

						newData.setCost_item_id(assDir.getCost_item_id());

						newData.setCost_item_no(assDir.getCost_item_no());

						newData.setCost_item_code(assDir.getCost_item_code());

						newData.setCost_item_name(assDir.getCost_item_name());

						newData.setSource_id(assDir.getSource_id());

						newData.setSource_code(assDir.getSource_code());

						newData.setSource_name(assDir.getSource_name());

						newData.setType_code(ass.get("server_type_code").toString());

						newData.setNatur_code(ass.get("server_natur_code").toString());

						newData.setPara_type_code(assDir.getPara_type_code());

						newData.setDir_amount(0.0);

						if (peerDeptMap.containsKey(ass.get("server_dept_code").toString())) {

							if (!peerMedDirData.contains(newData)) {

								peerMedDirData.add(newData);

							}

						} else {

							if (!medDirData.contains(newData)) {

								medDirData.add(newData);

							}

						}

					}

				}
				check = new CostDeptCostCheck();

				check.setGroup_id(assDir.getGroup_id());

				check.setHos_id(assDir.getHos_id());

				check.setCopy_code(assDir.getCopy_code());

				check.setAcc_year(assDir.getAcc_year());

				check.setAcc_month(assDir.getAcc_month());

				check.setDept_id(assDir.getDept_id());

				check.setDept_no(assDir.getDept_no());

				check.setDept_code(assDir.getDept_code());

				check.setDept_name(assDir.getDept_name());

				check.setType_code(assDir.getType_code());
				check.setType_name(assDir.getType_name());
				check.setNatur_code(assDir.getNatur_code());
				check.setNatur_name(assDir.getNatur_name());

				check.setServer_dept_id(Long.valueOf(ass.get("server_dept_id").toString()));

				check.setServer_dept_no(Long.valueOf(ass.get("server_dept_no").toString()));

				check.setServer_dept_code(ass.get("server_dept_code").toString());

				check.setServer_dept_name(ass.get("server_dept_name").toString());

				check.setCost_item_id(assDir.getCost_item_id());

				check.setCost_item_no(assDir.getCost_item_no());

				check.setCost_item_code(assDir.getCost_item_code());

				check.setCost_item_name(assDir.getCost_item_name());

				check.setSource_id(assDir.getSource_id());

				check.setSource_code(assDir.getSource_code());

				check.setSource_name(assDir.getSource_name());

				// 服务科室直接成本
				check.setDir_amount(assDir.getDir_amount().isNaN() ? 0.0 : assDir.getDir_amount().doubleValue());

				check.setDir_ass_amount(PreciseCompute.div(assDir.getDir_amount().doubleValue() * server_num, dept_num, 18));
				check.setDir_ass_amount_bl(server_num);
				check.setDir_ass_amount_bl_sum(dept_num);

				check.setIndir_ass_man_amount(PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

				check.setIndir_ass_man_amount_bl(server_num);

				check.setIndir_ass_man_amount_bl_sum(dept_num);
				check.setPara_type_code(assDir.getPara_type_code());
				check.setPara_type_name(assDir.getPara_type_name());
				check.setPara_code(assDir.getPara_code());
				check.setPara_name(assDir.getPara_name());
				checkData.add(check);

				assServerCostKey = null;

			}

		}
		
	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param manDirData
	 *            医辅科室直接成本结果集LIST形式存储
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param manData
	 *            医辅分摊科室结果集 科室对应关系
	 * @param deptParaNumMap
	 *            受益科室分摊比例数
	 * @param deptParaSumNumMap
	 *            服务科室分摊比例总数
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 * @param peerDeptMap
	 *            平级分摊科室集合
	 * @param medDirData
	 *            普通医技分摊直接成本扩充
	 * @param peerMedDirData
	 *            平级医技分摊直接成本扩充
	 */
	@SuppressWarnings("unchecked")
	private void assParaCost(Map<String, Object> entityMap, Set<CostDeptDriData> assDirData, Map<String, Object> dirMapData,
	        Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap, Map<String, Object> deptParaSumNumMap,
	        Map<String, Object> costDeptCostMap, Map<String, Object> peerDeptMap, Set<CostDeptDriData> medDirData,
	        Set<CostDeptDriData> peerMedDirData, List<CostDeptCostCheck> checkData) {
		StringBuilder assServerCostKey=null;
		StringBuilder v_deptParaNumMap=null;
		StringBuilder v_deptParaSumNumMap=null;
		CostDeptCost data=null;
		CostDeptDriData newData =null;
		CostDeptCostCheck check=null;
		StringBuilder assCostKey =null;
		for (CostDeptDriData assDir : assDirData) {

			// 分摊管理成本
			double dir_man_amount = 0;

			// 分摊医技成本
			double peers_dir_ass_amount = 0;

			assCostKey = new StringBuilder(100);

			assCostKey.append(assDir.getDept_id().toString());

			assCostKey.append(assDir.getDept_no().toString());

			assCostKey.append(assDir.getCost_item_id());

			assCostKey.append(assDir.getCost_item_no());

			assCostKey.append(assDir.getSource_id());

			// 服务科室各成本数
			CostDeptCost deptCost = (CostDeptCost) costDeptCostMap.get(assCostKey.toString());

			if (null != deptCost) {

				// 管理成本
				dir_man_amount = deptCost.getDir_man_amount().isNaN() ? 0.0 : deptCost.getDir_man_amount();

				// 平级分摊成本
				peers_dir_ass_amount = deptCost.getDir_ass_amount().isNaN() ? 0.0 : deptCost.getDir_ass_amount();

			}

			Map<String, Object> typeData = (Map<String, Object>) paraDataMap.get(assDir.getPara_type_code());
			if (null == typeData) {
				logger.debug("分摊类型：" + assDir.getPara_type_code() + " 为找到相对应的服务科室");
				continue;
			}

			List<Map<String, Object>> deptData = (List<Map<String, Object>>) typeData.get(assDir.getDept_id().toString());
			if (null == deptData) {
				logger.debug("当前科室：" + assDir.getDept_name() + " 对应的分摊类型：" + assDir.getPara_type_code() + "为找到相对应的受益科室");
				continue;
			}

			for (Map<String, Object> ass : deptData) {

				assServerCostKey = new StringBuilder(100);

				assServerCostKey.append(ass.get("server_dept_id").toString());

				assServerCostKey.append(ass.get("server_dept_no").toString());

				assServerCostKey.append(assDir.getCost_item_id());

				assServerCostKey.append(assDir.getCost_item_no());

				assServerCostKey.append(assDir.getSource_id());

				v_deptParaNumMap = new StringBuilder(100);
				v_deptParaNumMap.append(ass.get("dept_id").toString());
				v_deptParaNumMap.append(ass.get("dept_no"));
				v_deptParaNumMap.append(ass.get("server_dept_id"));
				v_deptParaNumMap.append(ass.get("server_dept_no"));
				v_deptParaNumMap.append(ass.get("para_code"));
				v_deptParaNumMap.append(ass.get("para_type_code"));

				v_deptParaSumNumMap = new StringBuilder(100);
				v_deptParaSumNumMap.append(ass.get("dept_id").toString());
				v_deptParaSumNumMap.append(ass.get("dept_no"));
				v_deptParaSumNumMap.append(ass.get("para_code"));
				v_deptParaSumNumMap.append(ass.get("para_type_code"));

				Object obj = deptParaNumMap.get(v_deptParaNumMap.toString());

				Object sum = deptParaSumNumMap.get(v_deptParaSumNumMap.toString());

				obj = (null == obj) ? 0 : obj;

				sum = (null == sum) ? 0 : sum;

				// 受益科室分摊比例数
				double server_num = Double.parseDouble(obj.toString());

				// 服务科室分摊比例数
				double dept_num = Double.parseDouble(sum.toString());

				if (dept_num == 0 || server_num == 0) {

					continue;

				}

				if (null != costDeptCostMap.get(assServerCostKey.toString())) {

					// 受益科室本次分摊前各成本数
					CostDeptCost oldServerCost = (CostDeptCost) costDeptCostMap.get(assServerCostKey.toString());

					double old_dir_man_amount = oldServerCost.getDir_man_amount().isNaN() ? 0.0 : oldServerCost.getDir_man_amount();

					double old_dir_ass_amount = oldServerCost.getDir_ass_amount().isNaN() ? 0.0 : oldServerCost.getDir_ass_amount();

					double old_indir_ass_man_amount = oldServerCost.getIndir_ass_man_amount().isNaN() ? 0.0 : oldServerCost.getIndir_ass_man_amount();

					data = new CostDeptCost();

					data.setGroup_id(assDir.getGroup_id());

					data.setHos_id(assDir.getHos_id());

					data.setCopy_code(assDir.getCopy_code());

					data.setAcc_year(assDir.getAcc_year());

					data.setAcc_month(assDir.getAcc_month());

					data.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

					data.setCost_item_id(assDir.getCost_item_id());

					data.setCost_item_no(assDir.getCost_item_no());

					data.setSource_id(assDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(assServerCostKey.toString()) ? dirMapData.get(assServerCostKey.toString())
					        : 0.0);

					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(old_dir_ass_amount
					        + PreciseCompute.div((assDir.getDir_amount().doubleValue() + peers_dir_ass_amount) * server_num, dept_num, 18));

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(old_indir_ass_man_amount + PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

					// 直接分摊管理成本
					data.setDir_man_amount(old_dir_man_amount);

					// 直接分摊医技成本
					data.setDir_med_amount(0.0);

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(0.0);

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(0.0);

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(assServerCostKey.toString(), data);

				} else {

				    data = new CostDeptCost();

					data.setGroup_id(assDir.getGroup_id());

					data.setHos_id(assDir.getHos_id());

					data.setCopy_code(assDir.getCopy_code());

					data.setAcc_year(assDir.getAcc_year());

					data.setAcc_month(assDir.getAcc_month());

					data.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

					data.setCost_item_id(assDir.getCost_item_id());

					data.setCost_item_no(assDir.getCost_item_no());

					data.setSource_id(assDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(assServerCostKey.toString()) ? dirMapData.get(assServerCostKey.toString())
					        : 0.0);

					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(PreciseCompute.div((assDir.getDir_amount().doubleValue() + peers_dir_ass_amount) * server_num, dept_num,
					                                          18));

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

					// 直接分摊管理成本
					data.setDir_man_amount(0.0);

					// 直接分摊管理成本
					data.setDir_med_amount(0.0);

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(0.0);

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(0.0);

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(0.0);

					costDeptCostMap.put(assServerCostKey.toString(), data);

					if (ass.get("server_type_code").toString().equals("02")) {

						newData = new CostDeptDriData();

						newData.setGroup_id(assDir.getGroup_id());

						newData.setHos_id(assDir.getHos_id());

						newData.setCopy_code(assDir.getCopy_code());

						newData.setAcc_year(assDir.getAcc_year());

						newData.setAcc_month(assDir.getAcc_month());

						newData.setDept_id(Long.valueOf(ass.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(ass.get("server_dept_no").toString()));

						newData.setDept_code(ass.get("server_dept_code").toString());

						newData.setDept_name(ass.get("server_dept_name").toString());

						newData.setCost_item_id(assDir.getCost_item_id());

						newData.setCost_item_no(assDir.getCost_item_no());

						newData.setCost_item_code(assDir.getCost_item_code());

						newData.setCost_item_name(assDir.getCost_item_name());

						newData.setSource_id(assDir.getSource_id());

						newData.setSource_code(assDir.getSource_code());

						newData.setSource_name(assDir.getSource_name());

						newData.setType_code(ass.get("server_type_code").toString());

						newData.setNatur_code(ass.get("server_natur_code").toString());

						newData.setPara_type_code(assDir.getPara_type_code());

						newData.setDir_amount(0.0);

						if (peerDeptMap.containsKey(ass.get("server_dept_code").toString())) {

							if (!peerMedDirData.contains(newData)) {

								peerMedDirData.add(newData);

							}

						} else {

							if (!medDirData.contains(newData)) {

								medDirData.add(newData);

							}

						}

					}

				}

				check = new CostDeptCostCheck();

				check.setGroup_id(assDir.getGroup_id());

				check.setHos_id(assDir.getHos_id());

				check.setCopy_code(assDir.getCopy_code());

				check.setAcc_year(assDir.getAcc_year());

				check.setAcc_month(assDir.getAcc_month());

				check.setDept_id(assDir.getDept_id());

				check.setDept_no(assDir.getDept_no());

				check.setDept_code(assDir.getDept_code());

				check.setDept_name(assDir.getDept_name());

				check.setType_code(assDir.getType_code());
				check.setType_name(assDir.getType_name());
				check.setNatur_code(assDir.getNatur_code());
				check.setNatur_name(assDir.getNatur_name());

				check.setServer_dept_id(Long.valueOf(ass.get("server_dept_id").toString()));

				check.setServer_dept_no(Long.valueOf(ass.get("server_dept_no").toString()));

				check.setServer_dept_code(ass.get("server_dept_code").toString());

				check.setServer_dept_name(ass.get("server_dept_name").toString());

				check.setCost_item_id(assDir.getCost_item_id());

				check.setCost_item_no(assDir.getCost_item_no());

				check.setCost_item_code(assDir.getCost_item_code());

				check.setCost_item_name(assDir.getCost_item_name());

				check.setSource_id(assDir.getSource_id());

				check.setSource_code(assDir.getSource_code());

				check.setSource_name(assDir.getSource_name());

				// 服务科室直接成本
				check.setDir_amount(assDir.getDir_amount().isNaN() ? 0.0 : assDir.getDir_amount().doubleValue());

				check.setDir_ass_amount(PreciseCompute.div((assDir.getDir_amount().doubleValue() + peers_dir_ass_amount) * server_num, dept_num, 18));
				check.setDir_ass_amount_bl(server_num);
				check.setDir_ass_amount_bl_sum(dept_num);

				check.setIndir_ass_man_amount(PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

				check.setIndir_ass_man_amount_bl(server_num);

				check.setIndir_ass_man_amount_bl_sum(dept_num);
				check.setPara_type_code(assDir.getPara_type_code());
				check.setPara_type_name(assDir.getPara_type_name());
				check.setPara_code(assDir.getPara_code());
				check.setPara_name(assDir.getPara_name());
				checkData.add(check);

				assServerCostKey = null;

			}

		}
		
	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param manDirData
	 *            医技科室直接成本结果集LIST形式存储
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param manData
	 *            医技分摊科室结果集 科室对应关系
	 * @param deptParaNumMap
	 *            受益科室分摊比例数
	 * @param deptParaSumNumMap
	 *            服务科室分摊比例总数
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 */
	@SuppressWarnings("unchecked")
	private void peerMedParaCost(Map<String, Object> entityMap, Set<CostDeptDriData> peerMedDirData, Set<CostDeptDriData> medDirData,
	        Map<String, Object> dirMapData, Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap,
	        Map<String, Object> deptParaSumNumMap, Map<String, Object> costDeptCostMap, Map<String, Object> peerDeptMap,
	        List<CostDeptCostCheck> checkData) {
		StringBuilder medServerCostKey=null;
		StringBuilder v_deptParaNumMap=null;
		StringBuilder v_deptParaSumNumMap=null;
		CostDeptCost data=null;
		CostDeptDriData newData =null;
		CostDeptCostCheck check=null;
		StringBuilder medCostKey =null;
		CostDeptCost oldServerCost=null;
		// 医技分摊
		for (CostDeptDriData medDir : medDirData) {

			// 间接医辅管理成本
			double indir_ass_man_amount = 0;

			// 分摊管理成本
			double dir_man_amount = 0;

			// 分摊医辅成本
			double dir_ass_amount = 0;

			 medCostKey = new StringBuilder(100);

			medCostKey.append(medDir.getDept_id().toString());

			medCostKey.append(medDir.getDept_no().toString());

			medCostKey.append(medDir.getCost_item_id());

			medCostKey.append(medDir.getCost_item_no());

			medCostKey.append(medDir.getSource_id());

			CostDeptCost oldDeptCost = new CostDeptCost();

			oldDeptCost = (CostDeptCost) costDeptCostMap.get(medCostKey.toString());

			if (null != oldDeptCost) {

				indir_ass_man_amount = oldDeptCost.getIndir_ass_man_amount().isNaN() ? 0.0 : oldDeptCost.getIndir_ass_man_amount();

				dir_man_amount = oldDeptCost.getDir_man_amount().isNaN() ? 0.0 : oldDeptCost.getDir_man_amount();

				dir_ass_amount = oldDeptCost.getDir_ass_amount().isNaN() ? 0.0 : oldDeptCost.getDir_ass_amount();
			}

			Map<String, Object> typeData = (Map<String, Object>) paraDataMap.get(medDir.getPara_type_code());

			if (null == typeData) {
				logger.debug("分摊类型：" + medDir.getPara_type_code() + " 为找到相对应的服务科室");
				continue;
			}

			List<Map<String, Object>> deptData = (List<Map<String, Object>>) typeData.get(medDir.getDept_id().toString());
			if (null == deptData) {
				logger.debug("当前科室：" + medDir.getDept_name() + " 对应的分摊类型：" + medDir.getPara_type_code() + "为找到相对应的受益科室");
				continue;
			}
			for (Map<String, Object> med : deptData) {

				 medServerCostKey = new StringBuilder(100);

				medServerCostKey.append(med.get("server_dept_id").toString());

				medServerCostKey.append(med.get("server_dept_no").toString());

				medServerCostKey.append(medDir.getCost_item_id());

				medServerCostKey.append(medDir.getCost_item_no());

				medServerCostKey.append(medDir.getSource_id());

				v_deptParaNumMap = new StringBuilder(100);
				v_deptParaNumMap.append(med.get("dept_id").toString());
				v_deptParaNumMap.append(med.get("dept_no"));
				v_deptParaNumMap.append(med.get("server_dept_id"));
				v_deptParaNumMap.append(med.get("server_dept_no"));
				v_deptParaNumMap.append(med.get("para_code"));
				v_deptParaNumMap.append(med.get("para_type_code"));

				v_deptParaSumNumMap = new StringBuilder(100);
				v_deptParaSumNumMap.append(med.get("dept_id").toString());
				v_deptParaSumNumMap.append(med.get("dept_no"));
				v_deptParaSumNumMap.append(med.get("para_code"));
				v_deptParaSumNumMap.append(med.get("para_type_code"));

				Object obj = deptParaNumMap.get(v_deptParaNumMap.toString());

				Object sum = deptParaSumNumMap.get(v_deptParaSumNumMap.toString());

				obj = (null == obj) ? 0 : obj;

				sum = (null == sum) ? 0 : sum;

				// 受益科室分摊比例数
				double server_num = Double.parseDouble(obj.toString());

				// 服务科室分摊比例数
				double dept_num = Double.parseDouble(sum.toString());

				if (dept_num == 0 || server_num == 0) {

					continue;

				}

				if (null != costDeptCostMap.get(medServerCostKey.toString())) {

					oldServerCost = new CostDeptCost();

					oldServerCost = (CostDeptCost) costDeptCostMap.get(medServerCostKey.toString());

					double old_dir_man_amount = oldServerCost.getDir_man_amount().isNaN() ? 0.0 : oldServerCost.getDir_man_amount();

					double old_dir_ass_amount = oldServerCost.getDir_ass_amount().isNaN() ? 0.0 : oldServerCost.getDir_ass_amount();

					double old_dir_med_amount = oldServerCost.getDir_med_amount().isNaN() ? 0.0 : oldServerCost.getDir_med_amount();

					double old_indir_ass_man_amount = oldServerCost.getIndir_ass_man_amount().isNaN() ? 0.0 : oldServerCost.getIndir_ass_man_amount();

					double old_indir_med_man_amount = oldServerCost.getIndir_med_man_amount().isNaN() ? 0.0 : oldServerCost.getIndir_med_man_amount();

					double old_indir_ass_med_man_amount = oldServerCost.getIndir_ass_med_man_amount().isNaN() ? 0.0 : oldServerCost
					        .getIndir_ass_med_man_amount();

					double old_indir_med_ass_amount = oldServerCost.getIndir_med_ass_amount().isNaN() ? 0.0 : oldServerCost.getIndir_med_ass_amount();

					data = new CostDeptCost();

					data.setGroup_id(medDir.getGroup_id());

					data.setHos_id(medDir.getHos_id());

					data.setCopy_code(medDir.getCopy_code());

					data.setAcc_year(medDir.getAcc_year());

					data.setAcc_month(medDir.getAcc_month());

					data.setDept_id(Long.valueOf(med.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(med.get("server_dept_no").toString()));

					data.setCost_item_id(medDir.getCost_item_id());

					data.setCost_item_no(medDir.getCost_item_no());

					data.setSource_id(medDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(medServerCostKey.toString()) ? dirMapData.get(medServerCostKey.toString())
					        : 0.0);
					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医技成本
					data.setDir_med_amount(old_dir_med_amount
					        + PreciseCompute.div((medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue()) * server_num,
					                             dept_num, 18));

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(old_indir_med_man_amount + PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(old_indir_ass_med_man_amount
					        + PreciseCompute.div(indir_ass_man_amount * server_num, dept_num, 18));

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(old_indir_med_ass_amount + PreciseCompute.div(dir_ass_amount * server_num, dept_num, 18));

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(old_indir_ass_man_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(old_dir_ass_amount);

					// 直接分摊管理成本
					data.setDir_man_amount(old_dir_man_amount);

					costDeptCostMap.put(medServerCostKey.toString(), data);

				} else {

					data = new CostDeptCost();

					data.setGroup_id(medDir.getGroup_id());

					data.setHos_id(medDir.getHos_id());

					data.setCopy_code(medDir.getCopy_code());

					data.setAcc_year(medDir.getAcc_year());

					data.setAcc_month(medDir.getAcc_month());

					data.setDept_id(Long.valueOf(med.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(med.get("server_dept_no").toString()));

					data.setCost_item_id(medDir.getCost_item_id());

					data.setCost_item_no(medDir.getCost_item_no());

					data.setSource_id(medDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(medServerCostKey.toString()) ? dirMapData.get(medServerCostKey.toString())
					        : 0.0);
					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(0.0);

					// 直接分摊管理成本
					data.setDir_man_amount(0.0);

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(0.0);

					// 直接分摊医技成本
					data.setDir_med_amount((medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue()) * server_num / dept_num);

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(PreciseCompute.div(indir_ass_man_amount * server_num, dept_num, 18));

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(PreciseCompute.div(dir_ass_amount * server_num, dept_num, 18));

					costDeptCostMap.put(medServerCostKey.toString(), data);

					if (med.get("server_type_code").toString().equals("02")) {

						newData = new CostDeptDriData();

						newData.setGroup_id(medDir.getGroup_id());

						newData.setHos_id(medDir.getHos_id());

						newData.setCopy_code(medDir.getCopy_code());

						newData.setAcc_year(medDir.getAcc_year());

						newData.setAcc_month(medDir.getAcc_month());

						newData.setDept_id(Long.valueOf(med.get("server_dept_id").toString()));

						newData.setDept_no(Long.valueOf(med.get("server_dept_no").toString()));

						newData.setDept_code(med.get("server_dept_code").toString());

						newData.setDept_name(med.get("server_dept_name").toString());

						newData.setCost_item_id(medDir.getCost_item_id());

						newData.setCost_item_no(medDir.getCost_item_no());

						newData.setCost_item_code(medDir.getCost_item_code());

						newData.setCost_item_name(medDir.getCost_item_name());

						newData.setSource_id(medDir.getSource_id());

						newData.setSource_code(medDir.getSource_code());

						newData.setSource_name(medDir.getSource_name());

						newData.setType_code(med.get("server_type_code").toString());

						newData.setNatur_code(med.get("server_natur_code").toString());

						newData.setPara_type_code(medDir.getPara_type_code());

						newData.setDir_amount(0.0);

						if (peerDeptMap.containsKey(med.get("server_dept_code").toString())) {

							if (!peerMedDirData.contains(newData)) {

								peerMedDirData.add(newData);

							}

						} else {

							if (!medDirData.contains(newData)) {

								medDirData.add(newData);

							}

						}

					}

				}

				check = new CostDeptCostCheck();

				check.setGroup_id(medDir.getGroup_id());

				check.setHos_id(medDir.getHos_id());

				check.setCopy_code(medDir.getCopy_code());

				check.setAcc_year(medDir.getAcc_year());

				check.setAcc_month(medDir.getAcc_month());

				check.setDept_id(medDir.getDept_id());

				check.setDept_no(medDir.getDept_no());

				check.setDept_code(medDir.getDept_code());

				check.setDept_name(medDir.getDept_name());

				check.setType_code(medDir.getType_code());
				check.setType_name(medDir.getType_name());
				check.setNatur_code(medDir.getNatur_code());
				check.setNatur_name(medDir.getNatur_name());

				check.setServer_dept_id(Long.valueOf(med.get("server_dept_id").toString()));

				check.setServer_dept_no(Long.valueOf(med.get("server_dept_no").toString()));

				check.setServer_dept_code(med.get("server_dept_code").toString());

				check.setServer_dept_name(med.get("server_dept_name").toString());

				check.setCost_item_id(medDir.getCost_item_id());

				check.setCost_item_no(medDir.getCost_item_no());

				check.setCost_item_code(medDir.getCost_item_code());

				check.setCost_item_name(medDir.getCost_item_name());

				check.setSource_id(medDir.getSource_id());

				check.setSource_code(medDir.getSource_code());

				check.setSource_name(medDir.getSource_name());

				// 服务科室直接成本
				check.setDir_amount(medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue());

				check.setDir_ass_amount(0.0);

				check.setDir_man_amount(0.0);

				check.setDir_med_amount((PreciseCompute.div((medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue())
				        * server_num, dept_num, 18)));

				check.setDir_med_amount_bl(server_num);

				check.setDir_med_amount_bl_sum(dept_num);

				check.setIndir_ass_man_amount(0.0);

				check.setIndir_med_man_amount(PreciseCompute.div(dir_man_amount * server_num, dept_num, 18));

				check.setIndir_med_man_amount_bl(server_num);

				check.setIndir_med_man_amount_bl_sum(dept_num);

				check.setIndir_ass_med_man_amount(PreciseCompute.div(indir_ass_man_amount * server_num, dept_num, 18));
				check.setIndir_ass_med_man_amount_bl(server_num);
				check.setIndir_ass_med_man_amount_bl_sum(dept_num);

				check.setIndir_med_ass_amount(PreciseCompute.div(dir_ass_amount * server_num, dept_num, 18));

				check.setIndir_med_ass_amount_bl(server_num);
				check.setIndir_med_ass_amount_bl_sum(dept_num);
				check.setPara_type_code(medDir.getPara_type_code());
				check.setPara_type_name(medDir.getPara_type_name());
				check.setPara_code(medDir.getPara_code());
				check.setPara_name(medDir.getPara_name());
				checkData.add(check);
				medServerCostKey = null;
			}
		}
		
	}

	/**
	 * @param entityMap
	 *            页面传递参数
	 * @param manDirData
	 *            医技科室直接成本结果集LIST形式存储
	 * @param dirMapData
	 *            各科室项目直接成本MAP形式存储
	 * @param manData
	 *            医技分摊科室结果集 科室对应关系
	 * @param deptParaNumMap
	 *            受益科室分摊比例数
	 * @param deptParaSumNumMap
	 *            服务科室分摊比例总数
	 * @param costDeptCostMap
	 *            成本分摊结果集
	 * @param peerDeptMap
	 *            平级分摊科室集合
	 */
	@SuppressWarnings("unchecked")
	private void medParaCost(Map<String, Object> entityMap, Set<CostDeptDriData> medDirData, Map<String, Object> dirMapData,
	        Map<String, Object> paraDataMap, Map<String, Object> deptParaNumMap, Map<String, Object> deptParaSumNumMap,
	        Map<String, Object> costDeptCostMap, Map<String, Object> peerDeptMap, List<CostDeptCostCheck> checkData) {
		StringBuilder medServerCostKey=null;
		StringBuilder v_deptParaNumMap=null;
		StringBuilder v_deptParaSumNumMap=null;
		CostDeptCost data=null;
		CostDeptDriData newData =null;
		CostDeptCostCheck check=null;
		StringBuilder medCostKey =null;
		CostDeptCost oldServerCost=null;
		CostDeptCost oldDeptCost=null;
		for (CostDeptDriData medDir : medDirData) {

			// 间接医辅管理成本
			double indir_ass_man_amount = 0;

			// 分摊管理成本
			double dir_man_amount = 0;

			// 分摊医辅成本
			double dir_ass_amount = 0;

			// 分摊医技成本
			double dir_med_amount = 0;

			// 分摊间接医技管理成本
			double indir_med_man_amount = 0;

			// 分摊间接医技医辅管理成本
			double indir_ass_med_man_amount = 0;

			// 分摊间接医辅医技成本
			double indir_med_ass_amount = 0;

			medCostKey = new StringBuilder(100);

			medCostKey.append(medDir.getDept_id().toString());

			medCostKey.append(medDir.getDept_no().toString());

			medCostKey.append(medDir.getCost_item_id());

			medCostKey.append(medDir.getCost_item_no());

			medCostKey.append(medDir.getSource_id());

			 oldDeptCost = new CostDeptCost();

			oldDeptCost = (CostDeptCost) costDeptCostMap.get(medCostKey.toString());

			if (null != oldDeptCost) {

				indir_ass_man_amount = oldDeptCost.getIndir_ass_man_amount().isNaN() ? 0.0 : oldDeptCost.getIndir_ass_man_amount();

				dir_man_amount = oldDeptCost.getDir_man_amount().isNaN() ? 0.0 : oldDeptCost.getDir_man_amount();

				dir_ass_amount = oldDeptCost.getDir_ass_amount().isNaN() ? 0.0 : oldDeptCost.getDir_ass_amount();

				dir_med_amount = oldDeptCost.getDir_med_amount().isNaN() ? 0.0 : oldDeptCost.getDir_med_amount();

				indir_med_man_amount = oldDeptCost.getIndir_med_man_amount().isNaN() ? 0.0 : oldDeptCost.getIndir_med_man_amount();

				indir_ass_med_man_amount = oldDeptCost.getIndir_ass_med_man_amount().isNaN() ? 0.0 : oldDeptCost.getIndir_ass_med_man_amount();

				indir_med_ass_amount = oldDeptCost.getIndir_med_ass_amount().isNaN() ? 0.0 : oldDeptCost.getIndir_med_ass_amount();
			}

			Map<String, Object> typeData = (Map<String, Object>) paraDataMap.get(medDir.getPara_type_code());
			if (null == typeData) {
				logger.debug("分摊类型：" + medDir.getPara_type_code() + " 为找到相对应的服务科室");
				continue;
			}

			List<Map<String, Object>> deptData = (List<Map<String, Object>>) typeData.get(medDir.getDept_id().toString());
			if (null == deptData) {
				logger.debug("当前科室：" + medDir.getDept_name() + " 对应的分摊类型：" + medDir.getPara_type_code() + "为找到相对应的受益科室");
				continue;
			}

			for (Map<String, Object> med : deptData) {

				medServerCostKey = new StringBuilder(100);

				medServerCostKey.append(med.get("server_dept_id").toString());

				medServerCostKey.append(med.get("server_dept_no").toString());

				medServerCostKey.append(medDir.getCost_item_id());

				medServerCostKey.append(medDir.getCost_item_no());

				medServerCostKey.append(medDir.getSource_id());

				v_deptParaNumMap = new StringBuilder(100);
				v_deptParaNumMap.append(med.get("dept_id").toString());
				v_deptParaNumMap.append(med.get("dept_no"));
				v_deptParaNumMap.append(med.get("server_dept_id"));
				v_deptParaNumMap.append(med.get("server_dept_no"));
				v_deptParaNumMap.append(med.get("para_code"));
				v_deptParaNumMap.append(med.get("para_type_code"));

				v_deptParaSumNumMap = new StringBuilder(100);
				v_deptParaSumNumMap.append(med.get("dept_id").toString());
				v_deptParaSumNumMap.append(med.get("dept_no"));
				v_deptParaSumNumMap.append(med.get("para_code"));
				v_deptParaSumNumMap.append(med.get("para_type_code"));

				Object obj = deptParaNumMap.get(v_deptParaNumMap.toString());

				Object sum = deptParaSumNumMap.get(v_deptParaSumNumMap.toString());

				obj = (null == obj) ? 0 : obj;

				sum = (null == sum) ? 0 : sum;

				// 受益科室分摊比例数
				double server_num = Double.parseDouble(obj.toString());

				// 服务科室分摊比例数
				double dept_num = Double.parseDouble(sum.toString());

				if (dept_num == 0 || server_num == 0) {

					continue;

				}

				if (null != costDeptCostMap.get(medServerCostKey.toString())) {

					oldServerCost = new CostDeptCost();

					oldServerCost = (CostDeptCost) costDeptCostMap.get(medServerCostKey.toString());

					double old_dir_man_amount = oldServerCost.getDir_man_amount().isNaN() ? 0.0 : oldServerCost.getDir_man_amount();

					double old_dir_ass_amount = oldServerCost.getDir_ass_amount().isNaN() ? 0.0 : oldServerCost.getDir_ass_amount();

					double old_dir_med_amount = oldServerCost.getDir_med_amount().isNaN() ? 0.0 : oldServerCost.getDir_med_amount();

					double old_indir_ass_man_amount = oldServerCost.getIndir_ass_man_amount().isNaN() ? 0.0 : oldServerCost.getIndir_ass_man_amount();

					double old_indir_med_man_amount = oldServerCost.getIndir_med_man_amount().isNaN() ? 0.0 : oldServerCost.getIndir_med_man_amount();

					double old_indir_ass_med_man_amount = oldServerCost.getIndir_ass_med_man_amount().isNaN() ? 0.0 : oldServerCost
					        .getIndir_ass_med_man_amount();

					double old_indir_med_ass_amount = oldServerCost.getIndir_med_ass_amount().isNaN() ? 0.0 : oldServerCost.getIndir_med_ass_amount();

					data = new CostDeptCost();

					data.setGroup_id(medDir.getGroup_id());

					data.setHos_id(medDir.getHos_id());

					data.setCopy_code(medDir.getCopy_code());

					data.setAcc_year(medDir.getAcc_year());

					data.setAcc_month(medDir.getAcc_month());

					data.setDept_id(Long.valueOf(med.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(med.get("server_dept_no").toString()));

					data.setCost_item_id(medDir.getCost_item_id());

					data.setCost_item_no(medDir.getCost_item_no());

					data.setSource_id(medDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(medServerCostKey.toString()) ? dirMapData.get(medServerCostKey.toString())
					        : 0.0);

					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医技成本
					data.setDir_med_amount(old_dir_med_amount
					        + PreciseCompute
					                .div((((medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue()) + dir_med_amount) * server_num),
					                     dept_num, 18));

					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(old_indir_med_man_amount
					        + PreciseCompute.div((dir_man_amount + indir_med_man_amount) * server_num, dept_num, 18));

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(old_indir_ass_med_man_amount
					        + PreciseCompute.div((indir_ass_man_amount + indir_ass_med_man_amount) * server_num, dept_num, 18));

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(old_indir_med_ass_amount
					        + PreciseCompute.div((dir_ass_amount + indir_med_ass_amount) * server_num, dept_num, 18));

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(old_indir_ass_man_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(old_dir_ass_amount);

					// 直接分摊管理成本
					data.setDir_man_amount(old_dir_man_amount);

					costDeptCostMap.put(medServerCostKey.toString(), data);

				} else {

					data = new CostDeptCost();

					data.setGroup_id(medDir.getGroup_id());

					data.setHos_id(medDir.getHos_id());

					data.setCopy_code(medDir.getCopy_code());

					data.setAcc_year(medDir.getAcc_year());

					data.setAcc_month(medDir.getAcc_month());

					data.setDept_id(Long.valueOf(med.get("server_dept_id").toString()));

					data.setDept_no(Long.valueOf(med.get("server_dept_no").toString()));

					data.setCost_item_id(medDir.getCost_item_id());

					data.setCost_item_no(medDir.getCost_item_no());

					data.setSource_id(medDir.getSource_id());

					Double dir_amount = (Double) (null != dirMapData.get(medServerCostKey.toString()) ? dirMapData.get(medServerCostKey.toString())
					        : 0.0);

					// 直接成本
					data.setDir_amount(dir_amount.isNaN() ? 0.0 : dir_amount);

					// 直接分摊医辅成本
					data.setDir_ass_amount(0.0);

					// 直接分摊管理成本
					data.setDir_man_amount(0.0);

					// 间接分摊医辅管理成本
					data.setIndir_ass_man_amount(0.0);

					// 直接分摊医技成本
					data.setDir_med_amount(PreciseCompute
					        .div(((medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue()) + dir_med_amount) * server_num,
					             dept_num, 18));
 
					// 间接分摊医技管理成本
					data.setIndir_med_man_amount(PreciseCompute.div((dir_man_amount + indir_med_man_amount) * server_num, dept_num, 18));

					// 间接分摊医技医辅管理成本
					data.setIndir_ass_med_man_amount(PreciseCompute.div((indir_ass_man_amount + indir_ass_med_man_amount) * server_num, dept_num, 18));

					// 间接分摊医技医辅成本
					data.setIndir_med_ass_amount(PreciseCompute.div((dir_ass_amount + indir_med_ass_amount) * server_num, dept_num, 18));

					costDeptCostMap.put(medServerCostKey.toString(), data);

				}

				check = new CostDeptCostCheck();

				check.setGroup_id(medDir.getGroup_id());

				check.setHos_id(medDir.getHos_id());

				check.setCopy_code(medDir.getCopy_code());

				check.setAcc_year(medDir.getAcc_year());

				check.setAcc_month(medDir.getAcc_month());

				check.setDept_id(medDir.getDept_id());

				check.setDept_no(medDir.getDept_no());

				check.setDept_code(medDir.getDept_code());

				check.setDept_name(medDir.getDept_name());

				check.setType_code(medDir.getType_code());
				check.setType_name(medDir.getType_name());
				check.setNatur_code(medDir.getNatur_code());
				check.setNatur_name(medDir.getNatur_name());

				check.setServer_dept_id(Long.valueOf(med.get("server_dept_id").toString()));

				check.setServer_dept_no(Long.valueOf(med.get("server_dept_no").toString()));

				check.setServer_dept_code(med.get("server_dept_code").toString());

				check.setServer_dept_name(med.get("server_dept_name").toString());

				check.setCost_item_id(medDir.getCost_item_id());

				check.setCost_item_no(medDir.getCost_item_no());

				check.setCost_item_code(medDir.getCost_item_code());

				check.setCost_item_name(medDir.getCost_item_name());

				check.setSource_id(medDir.getSource_id());

				check.setSource_code(medDir.getSource_code());

				check.setSource_name(medDir.getSource_name());

				// 服务科室直接成本
				check.setDir_amount(medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue());

				check.setDir_ass_amount(0.0);

				check.setDir_man_amount(0.0);

				check.setDir_med_amount((PreciseCompute
				        .div(((medDir.getDir_amount().isNaN() ? 0.0 : medDir.getDir_amount().doubleValue()) + dir_med_amount) * server_num, dept_num,
				             18)));

				check.setDir_med_amount_bl(server_num);

				check.setDir_med_amount_bl_sum(dept_num);

				check.setIndir_ass_man_amount(0.0);

				check.setIndir_med_man_amount(PreciseCompute.div((dir_man_amount + indir_med_man_amount) * server_num, dept_num, 18));

				check.setIndir_med_man_amount_bl(server_num);

				check.setIndir_med_man_amount_bl_sum(dept_num);

				check.setIndir_ass_med_man_amount(PreciseCompute.div((indir_ass_man_amount + indir_ass_med_man_amount) * server_num, dept_num, 18));
				check.setIndir_ass_med_man_amount_bl(server_num);
				check.setIndir_ass_med_man_amount_bl_sum(dept_num);

				check.setIndir_med_ass_amount(PreciseCompute.div((dir_ass_amount + indir_med_ass_amount) * server_num, dept_num, 18));

				check.setIndir_med_ass_amount_bl(server_num);
				check.setIndir_med_ass_amount_bl_sum(dept_num);
				check.setPara_type_code(medDir.getPara_type_code());
				check.setPara_type_name(medDir.getPara_type_name());
				check.setPara_code(medDir.getPara_code());
				check.setPara_name(medDir.getPara_name());
				checkData.add(check);
				medServerCostKey = null;

			}

		}
		
	}

	@Override
	public String checkH2Db(Map<String, Object> entityMap) throws DataAccessException {
		Dao daoH2 = Mvcs.ctx().getDefaultIoc().get(Dao.class, "daoH2");
		return null;
	}

}
