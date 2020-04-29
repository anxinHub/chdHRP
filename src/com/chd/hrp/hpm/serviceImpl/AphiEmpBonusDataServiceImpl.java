package com.chd.hrp.hpm.serviceImpl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.collections.ListUtils;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.acc.dao.wage.AccWageEmpMapper;
import com.chd.hrp.acc.entity.AccWageEmp;
import com.chd.hrp.hpm.dao.AphiDeptBonusGrantMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapingMapper;
import com.chd.hrp.hpm.dao.AphiEmpBonusAuditMapper;
import com.chd.hrp.hpm.dao.AphiEmpBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiEmpDictMapper;
import com.chd.hrp.hpm.dao.AphiEmpMapper;
import com.chd.hrp.hpm.dao.AphiEmpSchemeMapper;
import com.chd.hrp.hpm.dao.AphiEmpSchemeSeqMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.dao.AphiSubSchemeConfMapper;
import com.chd.hrp.hpm.dao.AphiTemplateWageConfMapper;
import com.chd.hrp.hpm.entity.AphiDeptBonusGrant;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptMaping;
import com.chd.hrp.hpm.entity.AphiEmp;
import com.chd.hrp.hpm.entity.AphiEmpBonusData;
import com.chd.hrp.hpm.entity.AphiEmpDict;
import com.chd.hrp.hpm.entity.AphiEmpDuty;
import com.chd.hrp.hpm.entity.AphiEmpScheme;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.entity.AphiSubSchemeConf;
import com.chd.hrp.hpm.service.AphiEmpBonusDataService;
import com.chd.hrp.sys.entity.Emp;
import com.github.pagehelper.PageInfo;

/**
 * @Title. @Description.
 * 
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Service("aphiEmpBonusDataService")
public class AphiEmpBonusDataServiceImpl implements AphiEmpBonusDataService {

	private static Logger logger = Logger.getLogger(AphiEmpBonusDataServiceImpl.class);

	@Resource(name = "aphiEmpBonusDataMapper")
	private final AphiEmpBonusDataMapper aphiEmpBonusDataMapper = null;

	@Resource(name = "aphiEmpMapper")
	private final AphiEmpMapper aphiEmpMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Resource(name = "aphiEmpBonusAuditMapper")
	private final AphiEmpBonusAuditMapper aphiEmpBonusAuditMapper = null;

	@Resource(name = "aphiDeptBonusGrantMapper")
	private final AphiDeptBonusGrantMapper aphiDeptBonusGrantMapper = null;

	@Resource(name = "aphiEmpSchemeMapper")
	private final AphiEmpSchemeMapper aphiEmpSchemeMapper = null;

	@Resource(name = "aphiSubSchemeConfMapper")
	private final AphiSubSchemeConfMapper aphiSubSchemeConfMapper = null;

	@Resource(name = "aphiEmpSchemeSeqMapper")
	private final AphiEmpSchemeSeqMapper aphiEmpSchemeSeqMapper = null;

	@Resource(name = "aphiEmpDictMapper")
	private final AphiEmpDictMapper aphiEmpDictMapper = null;

	@Resource(name = "aphiTemplateWageConfMapper")
	private final AphiTemplateWageConfMapper aphiTemplateWageConfMapper = null;

	@Resource(name = "aphiDeptMapingMapper")
	private final AphiDeptMapingMapper aphiDeptMapingMapper = null;
	
	@Resource(name = "accWageEmpMapper")
	private final AccWageEmpMapper accWageEmpMapper = null;

	/**
	 * 
	 */
	@Override
	public String addEmpBonusData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		String dept_id = (String) entityMap.get("dept_id");

		entityMap.put("dept_id", dept_id.split(",")[0]);
		entityMap.put("dept_no", dept_id.split(",")[1]);

		List<AphiEmpBonusData> aeba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

		if (aeba != null) {

			for (AphiEmpBonusData ids : aeba) {

				if (ids.getIs_grant() == 1) {
					return "{\"error\":\"本月奖金已发放,不能引入数据\"}";
				}

			}

		}

		// List<AphiEmpItem> empItemList =
		// aphiEmpItemMapper.queryEmpItem(entityMap);//查询
		//
		// if(empItemList.size() == 0){
		//
		// return "{\"warn\":\"请维护职工奖金项\"}";
		//
		// }

		entityMap.put("is_avg", "1");

		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

		if (itemList.size() == 0) {

			return "{\"warn\":\"请维护参与核算的奖金项目\"}";

		}

		entityMap.put("is_audit", 1);

		List<Map<String, Object>> aebdListUp = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(entityMap);

		if (aebdListUp.size() > 0) {

			return "{\"warn\":\"当前科室已经审核无法引入数据\"}";
		}

		String paramVo = (String) entityMap.get("ParamVo");

		String[] empStr = paramVo.split(",");

		List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();
		// List<AphiDeptBonusGrant> adbg =
		// aphiDeptBonusGrantMapper.queryDeptBonusGrantByCode_NoNull(entityMap);
		try {

			for (int i = 0; i < empStr.length; i++) {

				for (AphiItem aei : itemList) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));

					map.put("copy_code", entityMap.get("copy_code"));
					map.put("acct_year", entityMap.get("acct_year"));

					map.put("acct_month", entityMap.get("acct_month"));

					map.put("emp_id", empStr[i].split(";")[0]);
					map.put("emp_no", empStr[i].split(";")[1]);

					map.put("bonus_money", 0);
					map.put("is_audit", 0);
					map.put("item_code", aei.getItem_code());
					map.put("is_grant", 0);
					map.put("is_two_audit", 0);

					map.put("dept_id", entityMap.get("dept_id"));
					map.put("dept_no", entityMap.get("dept_no"));

					AphiEmpBonusData aebd = aphiEmpBonusDataMapper.queryEmpBonusDataByCode(map);

					if (aebd == null) {
						addList.add(map);
					}

				}
			}

			if (addList.size() > 0) {

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < addList.size(); i++) {

					batchList.add(addList.get(i));

					if (i != 0 && i % 500 == 0) {

						aphiEmpBonusDataMapper.addBatchEmpBonusData(batchList);

						batchList.removeAll(batchList);

					}

				}

				aphiEmpBonusDataMapper.addBatchEmpBonusData(batchList);
			}

			return "{\"msg\":\"引入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}

	}

	/**
	 * 
	 */
	@Override
	public String queryEmpBonusData(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiEmpBonusData> list = aphiEmpBonusDataMapper.queryEmpBonusData(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiEmpBonusData> list = aphiEmpBonusDataMapper.queryEmpBonusData(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, sysPage.getTotal());
		}

	}

	/**
	 * 
	 */
	@Override
	public AphiEmpBonusData queryEmpBonusDataByCode(Map<String, Object> entityMap) throws DataAccessException {
		return aphiEmpBonusDataMapper.queryEmpBonusDataByCode(entityMap);
	}

	/**
	 * 
	 */

	public String deleteEmpBonusData(List<Map<String, Object>> entityList) throws DataAccessException {
		String request = "";
		for (Map<String, Object> entityMap : entityList) {
			int state = aphiEmpBonusDataMapper.deleteEmpBonusData(entityMap);
			if (state > 0) {
				request = "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			} else {
				request = "{\"msg\":\"没有要删除的数据.\",\"state\":\"false\"}";
			}
		}

		return request;
	}

	/**
	 * 
	 */
	@Override
	public String updateEmpBonusData(Map<String, Object> entityMap) throws DataAccessException {

		try {

			String year_month = (String) entityMap.get("acct_yearm");

			entityMap.put("acct_month", year_month.substring(4, 6));

			entityMap.put("acct_year", year_month.substring(0, 4));

			entityMap.put("item_code", entityMap.get("item_code").toString().toUpperCase());

			List<AphiEmpBonusData> aeba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			if (aeba != null) {
				for (AphiEmpBonusData ids : aeba) {
					if (ids.getIs_grant() == 1) {
						return "{\"error\":\"本月奖金已发放,不能生成更改数据\"}";
					}
				}

			}

			if (aeba.size() == 0) {

				return "{\"error\":\"该职工没有生成该项目,不能生成更改数据\"}";

			}

			entityMap.put("item_code", entityMap.get("item_code").toString().toUpperCase());

			aphiEmpBonusDataMapper.updateEmpBonusData(entityMap);

			return "{\"msg1\":\"修改成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"msg\":\"操作失败 \"}");
		}
	}

	@Override
	public String initEmpBonusData(Map<String, Object> entityMap) throws DataAccessException {
		try {

			String year_month = (String) entityMap.get("acct_yearm");

			String acct_year = year_month.substring(0, 4);

			String acct_month = year_month.substring(4, 6);

			entityMap.put("acct_month", acct_month);

			entityMap.put("acct_year", acct_year);

			aphiEmpBonusDataMapper.deleteEmpBonusData(entityMap);

			// List list = aphiEmpMapper.queryEmpForEmpBonusData(entityMap);
			List list = null;

			if (list.size() > 0) {

				for (int i = 0; i < list.size(); i++) {

					Emp e = (Emp) list.get(i);

					entityMap.put("acct_month", acct_month);

					entityMap.put("acct_year", acct_year);

					entityMap.put("emp_code", e.getEmp_code());

					aphiEmpBonusDataMapper.addEmpBonusData(entityMap);

				}

				return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

			} else {

				return "{\"msg\":\"没有要生成的数据.\",\"state\":\"false\"}";

			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"生成失败 数据库异常 请联系管理员! 错误编码  initEmpBonusData\"}";

		}
	}

	@Override
	public String createEmpBonusData(Map<String, Object> entityMap) throws DataAccessException {

		try {

			if (MyConfig.getSysPara("09001") == null) {
				entityMap.put("para_value", 0);
			} else {
				entityMap.put("para_value", MyConfig.getSysPara("09001"));
			}
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			String year_month = (String) entityMap.get("year_month");

			String acct_year = year_month.substring(0, 4);
			String acct_month = year_month.substring(4, 6);

			entityMap.put("acct_month", acct_month);
			entityMap.put("acct_year", acct_year);

			// 1.查询方案是否配置
			List<AphiEmpScheme> empSchemeList = aphiEmpSchemeMapper.queryEmpScheme(entityMap);
			if (empSchemeList.size() == 0) {

				return "{\"error\":\"当前年月未制定方案,不能生成\"}";
			}

			// 2.查询方案审核序号
			AphiSubSchemeConf assc = aphiSubSchemeConfMapper.querySubSchemeConfByCode(entityMap);
			if (assc == null) {
				return "{\"error\":\"当前年月方案未审核,不能生成\"}";
			}

			// 3.查询当前核算年月数据是否审核、发放
			List<AphiEmpBonusData> aeba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			if (aeba != null) {
				for (AphiEmpBonusData ids : aeba) {
					if (ids.getIs_audit() == 1) {
						return "{\"error\":\"当前年月奖金已审核,不能生成\"}";
					}

					if (ids.getIs_grant() == 1) {
						return "{\"error\":\"当前年月奖金已发放,不能生成\"}";
					}
				}

			}

			entityMap.put("sub_scheme_seq_no", assc.getSub_scheme_seq_no());
			// 4.根据已审核方案中的科室、职务 查找职工
			List<AphiEmpDict> empList = aphiEmpDictMapper.queryAphiEmpDictList(entityMap);
			if (empList.size() == 0) {
				return "{\"error\":\"当前配置方案科室、职务,未找到职工,不能生成\"}";
			}

			// 5.删除当前年月数据
			aphiEmpBonusDataMapper.deleteEmpBonusData(entityMap);

			// 6.生成数据
			for (AphiEmpDict empDict : empList) {

				Map<String, Object> mapVo = new HashMap<String, Object>();
				mapVo.put("group_id", empDict.getGroup_id());
				mapVo.put("hos_id", empDict.getHos_id());
				mapVo.put("copy_code", empDict.getCopy_code());
				mapVo.put("dept_id", empDict.getDept_id());
				mapVo.put("dept_no", empDict.getDept_no());
				mapVo.put("emp_id", empDict.getEmp_id());
				mapVo.put("emp_no", empDict.getEmp_no());
				mapVo.put("acct_month", acct_month);
				mapVo.put("acct_year", acct_year);
				mapVo.put("bonus_money", 0);
				mapVo.put("is_audit", 0);

				aphiEmpBonusDataMapper.addEmpBonusData(mapVo);
			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"生成失败 数据库异常 请联系管理员!\"}");

		}
	}

	@Override
	public String calculationEmpBonusData(Map<String, Object> entityMap) throws DataAccessException {

		return null;
	}

	@Override
	public String getEmpBonusDataValue(Map<String, Object> entityMap) throws DataAccessException {

		String year_month = (String) entityMap.get("year_month");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_year", acct_year);

		entityMap.put("acct_month", acct_month);

		List<AphiEmpBonusData> list = null;

		if (list.size() > 0) {

			return "{\"state\":\"true\"}";

		} else {

			/*
			 * entityMap .put("sql",
			 * "SELECT emp_code FROM aphi_emp_bonus_data where comp_code =#{comp_code} and copy_code = #{copy_code} and acct_year= #{acct_year} and acct_month = #{acct_month}"
			 * );
			 * 
			 * //List empList = aphiEmpMapper.queryEmpForEmpBonusData(entityMap); List
			 * empList = null; if (empList.size() > 0) {
			 * 
			 * return "{\"state\":\"checked\"}";
			 * 
			 * }
			 */

			return "{\"state\":\"false\"}";

		}

	}

	@Override
	public String createHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException {

		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			String year_month = (String) entityMap.get("acct_yearm");

			entityMap.put("acct_year", year_month.substring(0, 4));
			entityMap.put("acct_month", year_month.substring(4, 6));
			entityMap.put("is_stop", "0");

			/*
			 * List<AphiEmpBonusData> aeba =
			 * aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);
			 * 
			 * if (aeba != null) { for(AphiEmpBonusData ids : aeba){ if (ids.getIs_grant()
			 * == 1) {return "{\"error\":\"本月奖金已发放,不能生成本月数据\"}";} } }
			 */

			entityMap.put("is_audit", 1);

			String dept_id_no = entityMap.get("dept_id_no").toString();

			entityMap.put("dept_id", dept_id_no.split(",")[0]);
			entityMap.put("dept_no", dept_id_no.split(",")[1]);

			List<Map<String, Object>> aebdListUp = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(entityMap);

			if (aebdListUp.size() > 0) {

				return "{\"warn\":\"当前已有存在审核数据无法生成数据\"}";
			}

			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();// 添加List

			String rbtnl = (String) entityMap.get("rbtnl");

			if ("all".equals(rbtnl)) {// 覆盖生成

				entityMap.put("from_para", check_from_para());
				List<AphiEmp> aeList = aphiEmpMapper.queryAphiEmpDict(entityMap);

				if (aeList.size() == 0) {
					return "{\"warn\":\"请维护职工\"}";
				}

				entityMap.put("is_avg", "1");

				List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

				if (itemList.size() == 0) {
					return "{\"warn\":\"请维护参与核算的奖金项目\"}";
				}

				aphiEmpBonusDataMapper.deleteEmpBonusDataByPerm(entityMap);// 删除所有数据

				for (AphiEmp ae : aeList) {

					for (AphiItem aei : itemList) {

						Map<String, Object> map = new HashMap<String, Object>();

						map.put("group_id", entityMap.get("group_id"));
						map.put("hos_id", entityMap.get("hos_id"));
						map.put("copy_code", entityMap.get("copy_code"));

						map.put("acct_year", entityMap.get("acct_year"));
						map.put("acct_month", entityMap.get("acct_month"));

						map.put("emp_id", ae.getEmp_id());
						map.put("emp_no", ae.getEmp_no());

						map.put("dept_id", ae.getDept_id());
						map.put("dept_no", ae.getDept_no());

						map.put("item_code", aei.getItem_code());
						map.put("bonus_money", 0);
						map.put("is_audit", 0);
						map.put("is_grant", 0);
						map.put("is_two_audit", 0);

						addList.add(map);
					}
				}

			} else if ("check".equals(rbtnl)) {// 增量生成

				entityMap.put("from_para", check_from_para());
				List<AphiEmp> aeList = aphiEmpMapper.queryAphiEmpDict(entityMap);

				if (aeList.size() == 0) {
					return "{\"warn\":\"请维护职工\"}";
				}

				entityMap.put("is_avg", "1");

				List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

				if (itemList.size() == 0) {
					return "{\"warn\":\"请维护参与核算的奖金项目\"}";
				}

				entityMap.put("sql", "aebd.item_code,");

				List<Map<String, Object>> list = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(entityMap);// 查询

				Map<String, String> objMap = new HashMap<String, String>();// 用于判断职工map

				for (Map<String, Object> aebd : list) {

					String key = String.valueOf(aebd.get("EMP_ID")) + String.valueOf(aebd.get("EMP_NO"))
							+ String.valueOf(aebd.get("ITEM_CODE"));

					objMap.put(key, key);
				}

				for (AphiEmp ae : aeList) {

					for (AphiItem aei : itemList) {

						String key = String.valueOf(ae.getEmp_id()) + String.valueOf(ae.getEmp_no())
								+ aei.getItem_code();

						if (objMap.get(key) == null) {// 配置表中的职工在AphiEmpBonusData表中不存在

							Map<String, Object> map = new HashMap<String, Object>();

							map.put("group_id", entityMap.get("group_id"));
							map.put("hos_id", entityMap.get("hos_id"));
							map.put("copy_code", entityMap.get("copy_code"));

							map.put("acct_year", entityMap.get("acct_year"));
							map.put("acct_month", entityMap.get("acct_month"));

							map.put("emp_id", ae.getEmp_id());
							map.put("emp_no", ae.getEmp_no());

							map.put("dept_id", ae.getDept_id());
							map.put("dept_no", ae.getDept_no());

							map.put("item_code", aei.getItem_code());
							map.put("bonus_money", 0);
							map.put("is_audit", 0);
							map.put("is_grant", 0);
							map.put("is_two_audit", 0);

							addList.add(map);
						}
						// aphiEmpBonusDataMapper.addEmpBonusData(map);
					}
				}

			} else if ("inherit_value".equals(rbtnl)) {

				Map<String, Object> inheritMap = new HashMap<String, Object>();

				inheritMap.put("group_id", entityMap.get("group_id"));
				inheritMap.put("hos_id", entityMap.get("hos_id"));

				inheritMap.put("copy_code", entityMap.get("copy_code"));
				inheritMap.put("user_id", entityMap.get("user_id"));

				inheritMap.put("dept_id", entityMap.get("dept_id"));
				inheritMap.put("dept_no", entityMap.get("dept_no"));

				if (entityMap.get("item_code") != null && !"".equals(entityMap.get("item_code"))) {

					inheritMap.put("item_code", entityMap.get("item_code"));
				}

				inheritMap.put("para_value", entityMap.get("para_value"));

				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));// 获取上月

				inheritMap.put("acct_year", preYearMonth.substring(0, 4));
				inheritMap.put("acct_month", preYearMonth.substring(4, 6));

				inheritMap.put("sql", "aebd.item_code,aebd.bonus_money,");

				List<Map<String, Object>> listUp = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(inheritMap);// 查询上月数据

				List<Map<String, Object>> list = ChdJson.toListLower(listUp);

				if (list.size() > 0) {
					aphiEmpBonusDataMapper.deleteEmpBonusDataByPerm(entityMap);
				}

				else {
					return "{\"msg\":\"上月无数据.\",\"state\":\"true\"}";
				}

				for (Map<String, Object> aebd : list) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));

					map.put("acct_year", entityMap.get("acct_year"));
					map.put("acct_month", entityMap.get("acct_month"));

					map.put("emp_id", aebd.get("emp_id"));
					map.put("emp_no", aebd.get("emp_no"));

					map.put("dept_id", aebd.get("dept_id"));
					map.put("dept_no", aebd.get("dept_no"));

					map.put("item_code", aebd.get("item_code"));
					map.put("bonus_money", aebd.get("bonus_money"));

					map.put("is_audit", 0);
					map.put("is_grant", 0);
					map.put("is_two_audit", 0);

					addList.add(map);
				}

			} else {

				Map<String, Object> inheritMap = new HashMap<String, Object>();

				inheritMap.put("group_id", entityMap.get("group_id"));
				inheritMap.put("hos_id", entityMap.get("hos_id"));

				inheritMap.put("copy_code", entityMap.get("copy_code"));
				inheritMap.put("user_id", entityMap.get("user_id"));

				inheritMap.put("dept_id", entityMap.get("dept_id"));
				inheritMap.put("dept_no", entityMap.get("dept_no"));

				if (entityMap.get("item_code") != null && !"".equals(entityMap.get("item_code"))) {

					inheritMap.put("item_code", entityMap.get("item_code"));
				}

				inheritMap.put("para_value", entityMap.get("para_value"));

				String preYearMonth = DateUtil.getPreData((String) entityMap.get("acct_yearm"));// 获取上月

				inheritMap.put("acct_year", preYearMonth.substring(0, 4));
				inheritMap.put("acct_month", preYearMonth.substring(4, 6));

				inheritMap.put("sql", "aebd.item_code,aebd.bonus_money,");

				List<Map<String, Object>> listUp = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(inheritMap);// 查询上月数据

				List<Map<String, Object>> list = ChdJson.toListLower(listUp);

				if (list.size() > 0) {
					aphiEmpBonusDataMapper.deleteEmpBonusDataByPerm(entityMap);
				}

				else {
					return "{\"msg\":\"上月无数据.\",\"state\":\"true\"}";
				}

				for (Map<String, Object> aebd : list) {

					Map<String, Object> map = new HashMap<String, Object>();

					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));

					map.put("acct_year", entityMap.get("acct_year"));
					map.put("acct_month", entityMap.get("acct_month"));

					map.put("emp_id", aebd.get("emp_id"));
					map.put("emp_no", aebd.get("emp_no"));

					map.put("dept_id", aebd.get("dept_id"));
					map.put("dept_no", aebd.get("dept_no"));

					map.put("item_code", aebd.get("item_code"));
					map.put("bonus_money", 0);

					map.put("is_audit", 0);
					map.put("is_grant", 0);
					map.put("is_two_audit", 0);

					addList.add(map);
				}

			}

			if (addList.size() > 0) {

				List<Map<String, Object>> batchList = new ArrayList<Map<String, Object>>();

				for (int i = 0; i < addList.size(); i++) {

					batchList.add(addList.get(i));

					if (i != 0 && i % 500 == 0) {

						aphiEmpBonusDataMapper.addBatchEmpBonusData(batchList);
						batchList.removeAll(batchList);

					}
				}

				aphiEmpBonusDataMapper.addBatchEmpBonusData(batchList);

			}

			return "{\"msg\":\"生成成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}

	}

	@Override
	public String grantHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException {

		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			
			String year_month = (String) entityMap.get("acct_yearm");

			entityMap.put("acct_year", year_month.substring(0, 4));
			entityMap.put("acct_month", year_month.substring(4, 6));

			entityMap.put("is_grant", entityMap.get("is_grant"));
			entityMap.put("is_audit", entityMap.get("is_audit"));

			if ("".equals(entityMap.get("item_code")) && "".equals(entityMap.get("dept_id"))) {

				return "{\"error\":\"请选择科室或者是项目来提交\"}";
			}

			// 根据checkIds来判断是不是单个审批
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			if (entityMap.get("checkIds") != null) {

				// 把科室id和no循环出来
				for (String id : entityMap.get("checkIds").toString().split(",")) {

					Map<String, Object> mapVo = new HashMap<String, Object>();

					String[] ids = id.split("@");

					mapVo.put("dept_id", ids[0]);
					mapVo.put("dept_no", ids[1]);

					// 有一种情况,就是不选下拉框,也选择复选框的时候
					if (ids.length > 2) {
						mapVo.put("item_codes", ids[2]);
					}

					listVo.add(mapVo);

				}
				entityMap.put("list", listVo);
			}

			// 判断是否在发放页面点击发放按钮
			List<AphiDeptBonusGrant> grantList = aphiDeptBonusGrantMapper.queryListDeptBonusGrant(entityMap);

			if (grantList.size() == 0) {

				return "{\"error\":\"部分科室没有在发放页面做数据\"}";
			}

			for (AphiDeptBonusGrant grant : grantList) {

				if (grant.getIs_grant() != 1) {

					return "{\"error\":\"" + grant.getDept_name() + "没有选择在发放页面选择发放\"}";
				}
			}

			// 判断是不是有已经发放的数据
			List<AphiEmpBonusData> aeba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			if ("1".equals(entityMap.get("is_audit").toString())) {
				if (aeba != null) {

					for (AphiEmpBonusData ids : aeba) {

						if (ids.getIs_grant() == 1) {
							return "{\"error\":\"本月奖金已发放\"}";
						}

					}

				}

			}

			/*
			 * // 当选择复选框的时候 一共有多少个科室被选上,从来按科室删选一下 List<AphiEmpBonusData> aebas =
			 * aphiEmpBonusDataMapper.queryEmpBonusAuditByCode_dept(entityMap);
			 * 
			 * for(AphiEmpBonusData idss : aebas){
			 * 
			 * //判断科室是下拉框还是表单的科室 if( entityMap.get("dept_id") != null &&
			 * "".equals(entityMap.get("dept_id")) && entityMap.get("dept_no") != null &&
			 * "".equals(entityMap.get("dept_no"))){
			 * 
			 * entityMap.put("dept_id", entityMap.get("dept_id")); entityMap.put("dept_no",
			 * entityMap.get("dept_no"));
			 * 
			 * } else {
			 * 
			 * entityMap.put("dept_id", idss.getDept_id()); entityMap.put("dept_no",
			 * idss.getDept_no()); }
			 * 
			 * if( entityMap.get("item_code") != null && "".equals(entityMap.get("dept_id"))
			 * ){
			 * 
			 * entityMap.put("item_code", entityMap.get("item_code"));
			 * 
			 * } else {
			 * 
			 * entityMap.put("item_code", idss.getItem_code()); }
			 * 
			 * }
			 */

			// map 用于查询当前核算年月数据
			Map<String, Object> map = new HashMap<String, Object>();

			map.put("group_id", entityMap.get("group_id"));
			map.put("hos_id", entityMap.get("hos_id"));
			map.put("copy_code", entityMap.get("copy_code"));

			map.put("acct_year", entityMap.get("acct_year"));
			map.put("acct_month", entityMap.get("acct_month"));

			map.put("user_id", entityMap.get("user_id"));

			map.put("para_value", entityMap.get("para_value"));

			map.put("item_code", entityMap.get("item_code"));

			map.put("dept_id", entityMap.get("dept_id"));

			map.put("dept_no", entityMap.get("dept_no"));
			map.put("is_grant", entityMap.get("is_grant"));
			map.put("is_audit", entityMap.get("is_audit"));

			map.put("list", listVo);

			List<Map<String, Object>> aebdListUp = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(map);

			List<Map<String, Object>> aebdList = ChdJson.toListLower(aebdListUp);

			StringBuffer msg = new StringBuffer();// 未审核数据提示

			Map<String, Object> exitMap = new HashMap<String, Object>();

			for (Map<String, Object> aebd : aebdList) {

				if ("1".equals(aebd.get("is_audit").toString())) {
					continue;
				}

				String key = aebd.get("dept_code") + "|" + aebd.get("dept_name");

				if (exitMap.get(key) != null) {
					continue;
				}

				msg.append("当前核算年月 " + aebd.get("dept_name") + " 科室存在未审核数据<br>");

				exitMap.put(key, key);
			}

			if (msg.length() > 0) {
				return "{\"error\":\"" + msg.toString() + "\"}";
			}

			List<AphiEmpBonusData> list = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			// 奖金数据发放到工资发放表acc_wage_pay

			// 1.根据发放项目查询对应关系表 查出来wage_code item_code
			List<Map<String, Object>> wageList = aphiTemplateWageConfMapper
					.queryAphiTemplateWageConfForGrant(entityMap);

			if (wageList.size() == 0) {
				return "{\"warn\":\"未维护职工奖金项与工资项目对应关系.\",\"state\":\"true\"}";
			}

			// 把wage_code 和item_code装到map里

			StringBuffer wage_code_string = new StringBuffer();

			StringBuffer item_code_string = new StringBuffer();

			Map<String, Object> wageMapString = new HashMap<String, Object>();

			for (Map<String, Object> ids : wageList) {

				wage_code_string.append("'" + ids.get("wage_code") + "'");

				wage_code_string.append(",");

				item_code_string.append("'" + ids.get("emp_item") + "'");

				item_code_string.append(",");

			}

			wageMapString.put("wage_code", wage_code_string.deleteCharAt(wage_code_string.length() - 1));

			if ("".equals(entityMap.get("item_code"))) {

				wageMapString.put("item_code", item_code_string.deleteCharAt(item_code_string.length() - 1));

			} else {
				if (entityMap.get("checkIds") != null) {

					wageMapString.put("list", listVo);

				} else {

					wageMapString.put("item_codes", entityMap.get("item_code"));

				}

			}

			wageMapString.put("group_id", entityMap.get("group_id"));

			wageMapString.put("hos_id", entityMap.get("hos_id"));

			wageMapString.put("copy_code", entityMap.get("copy_code"));

			// 2.用二次分配的上报表 联合查询职工工资套对应关系表 where 工资套IN（第一步查出来的工资套）

			List<Map<String, Object>> wageEmpList = aphiTemplateWageConfMapper.queryWageEmp(wageMapString);

			// 第三步 用第二步查出来的数据 工资套使用的是第二步查询的 关系表的工资套

			for (Map<String, Object> wageids : wageEmpList) {

				entityMap.put("wage_code", wageids.get("wage_code"));

				List<Map<String, Object>> wageItemList = aphiTemplateWageConfMapper.queryWageItem(entityMap);

				Map<String, String> wageItemMap = new HashMap<String, String>();

				for (Map<String, Object> itemMap : ChdJson.toListLower(wageItemList)) {

					String wage_item_code = itemMap.get("wage_item_code").toString().toLowerCase();

					wageItemMap.put(wage_item_code, wage_item_code);

				}

				Map<String, Object> wageMap = new HashMap<String, Object>();

				for (Map<String, Object> mapList : wageList) {

					String wage_item = mapList.get("wage_item").toString().toLowerCase();

					wageMap.put(mapList.get("emp_item").toString(), wage_item);

					wageItemMap.remove(wage_item);
				}

				entityMap.put("is_avg", "1");

				List<AphiItem> itemList = null;

				if ("".equals(entityMap.get("dept_id"))) {

					itemList = aphiItemMapper.qeuryItemData(entityMap);

				} else {

					itemList = aphiItemMapper.qeuryItemData_wage(entityMap);
				}

				if (itemList.size() == 0) {
					return "{\"warn\":\"请维护参与核算的奖金项目\"}";
				}

/*				for (AphiItem aei : itemList) {

					if (aei.getIs_two_audit() == 1) {

						// entityMap.put("is_two_audit", 1);

						List<AphiEmpBonusData> dataList = aphiEmpBonusDataMapper.queryEmpBonusDataByKey(entityMap);

						for (AphiEmpBonusData grant_state : dataList) {

							if (grant_state.getIs_two_audit() == 0) {

								return "{\"warn\":\"请先领导审核才能提交\"}";
							}

						}

					}
				}*/

				StringBuffer sqlCase = new StringBuffer();

				StringBuffer sql = new StringBuffer();

				StringBuffer sqlValue = new StringBuffer();

				StringBuffer sqlWage = new StringBuffer();

				StringBuffer sqlWageSize = new StringBuffer();

				for (AphiItem aei : itemList) {

					sqlCase.append("nvl(sum(case when aebd.item_code='" + aei.getItem_code()
							+ "' then aebd.bonus_money end),0) as " + wageMap.get(aei.getItem_code()) + ",");

					if ("0".equals(entityMap.get("is_grant"))) {

						//sqlWage.append(wageMap.get(aei.getItem_code()) + "=0,");

					} else {

						sqlWageSize.append(wageMap.get(aei.getItem_code()) + ",");
					}

					sql.append(wageMap.get(aei.getItem_code()) + ",");

					sqlValue.append("#{item." + wageMap.get(aei.getItem_code()) + "},");
				}

				for (String key : wageItemMap.keySet()) {

					sql.append(key + ",");

					sqlValue.append("#{item." + key + "},");

				}

				entityMap.put("sqlCase", sqlCase);

				List<Map<String, Object>> empBonusDataListUp = aphiEmpBonusAuditMapper
						.queryEmpBonusDataForWagePay(entityMap);

				if (empBonusDataListUp.size() == 0) {
					return "{\"warn\":\"未查询到奖金数据.\",\"state\":\"true\"}";
				}

				List<Map<String, Object>> empBonusDataList = ChdJson.toListLower(empBonusDataListUp);

				List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

				for (Map<String, Object> wMap : empBonusDataList) {
					Map<String, Object> addMap = new HashMap<String, Object>();

					addMap.put("group_id", wMap.get("group_id"));
					addMap.put("hos_id", wMap.get("hos_id"));
					addMap.put("copy_code", wMap.get("copy_code"));

					addMap.put("acc_year", wMap.get("acct_year"));
					addMap.put("acc_month", wMap.get("acct_month"));

					addMap.put("emp_id", wMap.get("emp_id"));
					addMap.put("emp_no", wMap.get("emp_no"));

					addMap.put("dept_id", wMap.get("dept_id").toString());
					addMap.put("dept_no", wMap.get("dept_no").toString());

					// 查询奖金科室与系统平台 的对应关系表,没有给出提示
					List<AphiDeptMaping> deptList = aphiDeptMapingMapper.queryPrmDeptMaping(addMap);

					if (deptList.size() == 0) {

						return "{\"warn\":\"没有找到跟系统科室对应的关系\"}";

					}

					for (AphiDeptMaping deptMap : deptList) {

						addMap.put("dept_id", deptMap.getSys_dept_id().toString());

						addMap.put("dept_no", deptMap.getSys_dept_no().toString());

					}

					addMap.put("wage_code", wageids.get("wage_code"));
					
					List<AccWageEmp> emp = accWageEmpMapper.queryAccWageEmp(addMap);
					if (emp == null || emp.isEmpty()) {
						continue;
					}else {
						
						addMap.put("dept_id", emp.get(0).getDept_id());

						addMap.put("dept_no", emp.get(0).getDept_no());
					}
					
					addMap.put("user_id", SessionManager.getUserId());
					addMap.put("create_date", new Date());

					addMap.put("kind_code", (wMap.get("kind_code") != null ? wMap.get("kind_code") : ""));

					addMap.put("kind_name", (wMap.get("kind_name") != null ? wMap.get("kind_name") : ""));

					addMap.put("pay_type_code", (wMap.get("pay_type_code") != null ? wMap.get("pay_type_code") : ""));

					addMap.put("pay_type_name", (wMap.get("pay_type_name") != null ? wMap.get("pay_type_name") : ""));

					addMap.put("station_code", (wMap.get("station_code") != null ? wMap.get("station_code") : ""));

					addMap.put("station_name", (wMap.get("station_name") != null ? wMap.get("station_name") : ""));

					addMap.put("duty_code", (wMap.get("duty_code") != null ? wMap.get("duty_code") : ""));

					addMap.put("duty_name", (wMap.get("duty_name") != null ? wMap.get("duty_name") : ""));

					addMap.put("sex", (wMap.get("sex") != null ? wMap.get("sex") : ""));

					addMap.put("id_number", (wMap.get("id_number") != null ? wMap.get("id_number") : ""));

					addMap.put("state", wMap.get("state"));

					addMap.put("pay_state", wMap.get("pay_state"));

					for (String key : wageMap.keySet()) {

						addMap.put(wageMap.get(key).toString(), wMap.get(wageMap.get(key).toString()));

					}

					for (String key : wageItemMap.keySet()) {

						addMap.put(key, 0);
					}
					
					addList.add(addMap);
				}

				/********************************* 二次分配上报修改提交状态 ***************************/
				if (list.size() > 0) {

					if ("1".equals(entityMap.get("is_audit").toString())) {

						entityMap.put("grant_date", new Date());

						entityMap.put("grant_code", SessionManager.getUserId());

					} else {

						entityMap.put("grant_date", "");

						entityMap.put("grant_code", "");
					}

					aphiEmpBonusDataMapper.updateEmpBonusData_grant(entityMap);

				}

				//此map用来在往财务系统中插入数据时，去除同一职工的重复数据
				Map<String,Map<String,Object>> removeDuplicateMap = new HashMap<>();

				//同一个员工可能在多个部门中有奖金金额，在此合并金额，并录入财务系统
				for (Map<String, Object> addlist : addList) {
					String emp_id = addlist.get("emp_id").toString();
					Map<String, Object> otherEmpMap = removeDuplicateMap.get(emp_id);
					if (otherEmpMap == null ||otherEmpMap.isEmpty()) {
						removeDuplicateMap.put(emp_id , addlist);
					}else {
						String[] sqlWageSizes = sqlWageSize.toString().split(",");
						for (String sqls : sqlWageSizes) {
							BigDecimal thisone = new BigDecimal(0);
							if (addlist.get(sqls) != null) {
								thisone = new BigDecimal(addlist.get(sqls).toString());
							}
							BigDecimal otherone = new BigDecimal(0);
							if (otherEmpMap.get(sqls) != null) {
								otherone = new BigDecimal(otherEmpMap.get(sqls).toString());
							}
							BigDecimal result = thisone.add(otherone);
							addlist.put(sqls, result.toString());
						}
						removeDuplicateMap.put(emp_id, addlist);
					}
				}
				
				addList = new ArrayList<Map<String, Object>>(removeDuplicateMap.values());
				
				/***************************************
				 * 发到财务工资表
				 *********************************/
				if ("1".equals(entityMap.get("is_grant"))) {
					List<Map<String, Object>> listVo1 = new ArrayList<Map<String, Object>>();

					// if(entityMap.get("checkIds") != null){

					List<AphiDeptMaping> deptList1 = aphiDeptMapingMapper.queryPrmDeptMaping(entityMap);

					for (AphiDeptMaping deptMap : deptList1) {
						Map<String, Object> map1 = new HashMap<String, Object>();

						map1.put("dept_ids", deptMap.getSys_dept_id());

						map1.put("dept_nos", deptMap.getSys_dept_no());

						listVo1.add(map1);

					}

					entityMap.put("list1", listVo1);
					// }

					//此部门用于在同时提交时
					// 修改的list
					List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
					// 增加的list
					List<Map<String, Object>> addList2 = new ArrayList<Map<String, Object>>();
					for (Map<String, Object> addlist : addList) {

						// 把每个项目的数据都单独拿出来查询有没有数据有数据就修改,没有数据就增加
						Map<String, Object> addMap1 = new HashMap<String, Object>();

						addMap1.put("group_id", addlist.get("group_id"));
						addMap1.put("hos_id", addlist.get("hos_id"));
						addMap1.put("copy_code", addlist.get("copy_code"));
						addMap1.put("acct_year", addlist.get("acc_year"));
						addMap1.put("acct_month", addlist.get("acc_month"));
						addMap1.put("emp_id", addlist.get("emp_id"));
						addMap1.put("emp_no", addlist.get("emp_no"));
						addMap1.put("dept_ids", addlist.get("dept_id"));
						addMap1.put("dept_nos", addlist.get("dept_no"));
						addMap1.put("wage_code", addlist.get("wage_code"));
						addMap1.put("user_id", SessionManager.getUserId());
						addMap1.put("create_date", new Date());

						addMap1.put("kind_code", (addlist.get("kind_code") != null ? addlist.get("kind_code") : ""));

						addMap1.put("kind_name", (addlist.get("kind_name") != null ? addlist.get("kind_name") : ""));

						addMap1.put("pay_type_code",
								(addlist.get("pay_type_code") != null ? addlist.get("pay_type_code") : ""));

						addMap1.put("pay_type_name",
								(addlist.get("pay_type_name") != null ? addlist.get("pay_type_name") : ""));

						addMap1.put("station_code",
								(addlist.get("station_code") != null ? addlist.get("station_code") : ""));

						addMap1.put("station_name",
								(addlist.get("station_name") != null ? addlist.get("station_name") : ""));

						addMap1.put("duty_code", (addlist.get("duty_code") != null ? addlist.get("duty_code") : ""));

						addMap1.put("duty_name", (addlist.get("duty_name") != null ? addlist.get("duty_name") : ""));

						addMap1.put("sex", (addlist.get("sex") != null ? addlist.get("sex") : ""));

						addMap1.put("id_number", (addlist.get("id_number") != null ? addlist.get("id_number") : ""));

						addMap1.put("state", addlist.get("state"));

						addMap1.put("pay_state", addlist.get("pay_state"));

						for (String key : wageMap.keySet()) {

							addMap1.put(wageMap.get(key).toString(), addlist.get(wageMap.get(key).toString()));

						}

						for (String key : wageItemMap.keySet()) {

							addMap1.put(key, 0);

						}

						// 判断工资表是否有重复数据
						// 如果有重复数据，则从数据库中查出该数据，并进行金额合并操作。此操作应对同一个人在多个部门中有奖金分配的问题；避免在分别提交时，数据覆盖的问题
						Map<String,Object> listWagePay = aphiEmpBonusAuditMapper.queryWagePay(addMap1);

						if (listWagePay != null && !listWagePay.isEmpty()) {

							String[] sqlWageSizes = sqlWageSize.toString().split(",");

							for (String sqls : sqlWageSizes) {
								BigDecimal thisone = new BigDecimal(addlist.get(sqls).toString());
								
								if (listWagePay.get(sqls.toUpperCase()) != null) {
									BigDecimal otherone = new BigDecimal(listWagePay.get(sqls.toUpperCase()).toString());
									BigDecimal result = thisone.add(otherone);
									sqlWage.append(sqls + "=" + result + ",");
								}else {
									sqlWage.append(sqls + "=" + thisone + ",");
								}
							}

							addMap1.put("sqlWage", sqlWage.toString());

							// 初始化变量
							sqlWage = new StringBuffer();

							updateList.add(addMap1);

							// aphiEmpBonusAuditMapper.updateBatchWagePay(addMapWagePay);

							/*
							 * Map<String,Object> addMapWagePay = new HashMap<String,Object>();
							 * 
							 * for( Map<String, Object> ids : empBonusDataList){
							 * 
							 * entityMap.put("dept_id", ids.get("dept_id"));entityMap.put("dept_no",
							 * ids.get("dept_no"));
							 * 
							 * List<AphiDeptMaping> deptList =
							 * aphiDeptMapingMapper.queryPrmDeptMaping(entityMap);
							 * 
							 * for(AphiDeptMaping deptMap : deptList){
							 * 
							 * 
							 * entityMap.put("dept_ids", deptMap.getSys_dept_id());
							 * 
							 * entityMap.put("dept_nos", deptMap.getSys_dept_no());
							 * 
							 * 
							 * }
							 * 
							 * String[] sqlWageSizes = sqlWageSize.toString().split(",");
							 * 
							 * for(String sqls : sqlWageSizes){
							 * 
							 * sqlWage.append(sqls+"="+ids.get(sqls).toString()+",");
							 * 
							 * }
							 * 
							 * sqlWage.deleteCharAt(sqlWage.length()-1);
							 * 
							 * addMapWagePay.put("group_id",
							 * ids.get("group_id"));addMapWagePay.put("hos_id",
							 * ids.get("hos_id"));addMapWagePay.put("copy_code", ids.get("copy_code"));
							 * 
							 * addMapWagePay.put("acc_year",
							 * ids.get("acct_year"));addMapWagePay.put("acc_month", ids.get("acct_month"));
							 * 
							 * addMapWagePay.put("emp_id", ids.get("emp_id"));addMapWagePay.put("emp_no",
							 * ids.get("emp_no"));
							 * 
							 * addMapWagePay.put("dept_id", entityMap.get("dept_ids").toString());
							 * 
							 * addMapWagePay.put("dept_no", entityMap.get("dept_nos").toString());
							 * 
							 * addMapWagePay.put("wage_code", wageids.get("wage_code"));
							 * 
							 * addMapWagePay.put("sqlWage", sqlWage.toString());
							 * 
							 * //工资表有数据就修改 aphiEmpBonusAuditMapper.updateBatchWagePay(addMapWagePay);
							 * 
							 * 
							 * //初始化变量 sqlWage = new StringBuffer();
							 * 
							 * entityMap.put("dept_id", "");entityMap.put("dept_no", "");
							 * 
							 * }
							 */

						} else {

							addList2.add(addMap1);

						}
					}

					if (addList2.size() > 0) {

						// 工资表没有这个数据就增加
						aphiEmpBonusAuditMapper.addEmpBonusDataForWagePay(sql.toString(), sqlValue.toString(),
								addList2);

					}

					if (updateList.size() > 0) {

						aphiEmpBonusAuditMapper.updateBatchWagePayList(updateList);

					}

					// 判断工资表是否有重复数据
					// int listWagePay = aphiEmpBonusAuditMapper.queryWagePay(entityMap);

					/*
					 * if(listWagePay > 0){
					 * 
					 * Map<String,Object> addMapWagePay = new HashMap<String,Object>();
					 * 
					 * for( Map<String, Object> ids : empBonusDataList){
					 * 
					 * entityMap.put("dept_id", ids.get("dept_id"));entityMap.put("dept_no",
					 * ids.get("dept_no"));
					 * 
					 * List<AphiDeptMaping> deptList =
					 * aphiDeptMapingMapper.queryPrmDeptMaping(entityMap);
					 * 
					 * for(AphiDeptMaping deptMap : deptList){
					 * 
					 * 
					 * entityMap.put("dept_ids", deptMap.getSys_dept_id());
					 * 
					 * entityMap.put("dept_nos", deptMap.getSys_dept_no());
					 * 
					 * 
					 * }
					 * 
					 * String[] sqlWageSizes = sqlWageSize.toString().split(",");
					 * 
					 * for(String sqls : sqlWageSizes){
					 * 
					 * sqlWage.append(sqls+"="+ids.get(sqls).toString()+",");
					 * 
					 * }
					 * 
					 * sqlWage.deleteCharAt(sqlWage.length()-1);
					 * 
					 * addMapWagePay.put("group_id",
					 * ids.get("group_id"));addMapWagePay.put("hos_id",
					 * ids.get("hos_id"));addMapWagePay.put("copy_code", ids.get("copy_code"));
					 * 
					 * addMapWagePay.put("acc_year",
					 * ids.get("acct_year"));addMapWagePay.put("acc_month", ids.get("acct_month"));
					 * 
					 * addMapWagePay.put("emp_id", ids.get("emp_id"));addMapWagePay.put("emp_no",
					 * ids.get("emp_no"));
					 * 
					 * addMapWagePay.put("dept_id", entityMap.get("dept_ids").toString());
					 * 
					 * addMapWagePay.put("dept_no", entityMap.get("dept_nos").toString());
					 * 
					 * addMapWagePay.put("wage_code", wageids.get("wage_code"));
					 * 
					 * addMapWagePay.put("sqlWage", sqlWage.toString());
					 * 
					 * //工资表有数据就修改 //aphiEmpBonusAuditMapper.updateBatchWagePay(addMapWagePay);
					 * 
					 * 
					 * //初始化变量 sqlWage = new StringBuffer();
					 * 
					 * entityMap.put("dept_id", "");entityMap.put("dept_no", "");
					 * 
					 * }
					 */
					// } else{

					// 工资表没有这个数据就增加
					// aphiEmpBonusAuditMapper.addEmpBonusDataForWagePay(sql.toString(),
					// sqlValue.toString(), addList);

					// }

				} else {

					// 取消发放
					if (!addList.isEmpty()) {
						for (Map<String, Object> addlist : addList) {
							Map<String, Object> addMap1=new HashMap<>();
							addMap1.put("group_id", addlist.get("group_id"));
							addMap1.put("hos_id", addlist.get("hos_id"));
							addMap1.put("copy_code", addlist.get("copy_code"));
							addMap1.put("acct_year", addlist.get("acc_year"));
							addMap1.put("acct_month", addlist.get("acc_month"));
							addMap1.put("emp_id", addlist.get("emp_id"));
							addMap1.put("emp_no", addlist.get("emp_no"));
							addMap1.put("dept_ids", addlist.get("dept_id"));
							addMap1.put("dept_nos", addlist.get("dept_no"));
							addMap1.put("wage_code", addlist.get("wage_code"));
							// 如果有重复数据，则从数据库中查出该数据，并进行金额合并操作。此操作应对同一个人在多个部门中有奖金分配的问题；避免在分别提交时，数据覆盖的问题
							Map<String,Object> listWagePay = aphiEmpBonusAuditMapper.queryWagePay(addMap1);
	
							if (listWagePay != null && !listWagePay.isEmpty()) {
	
								String[] sqlWageSizes = sqlWageSize.toString().split(",");
	
								for (String sqls : sqlWageSizes) {
									BigDecimal thisone = new BigDecimal(addlist.get(sqls).toString());
									if (listWagePay.get(sqls.toUpperCase()) != null) {
										BigDecimal otherone = new BigDecimal(listWagePay.get(sqls.toUpperCase()).toString());
										BigDecimal result = otherone.subtract(thisone);
										if (result.compareTo(BigDecimal.ZERO) >= 0) {
											sqlWage.append(sqls + "=" + result + ",");
										}else {
											sqlWage.append(sqls + "=0,");
										}
									}else {
										sqlWage.append(sqls + "=" + new BigDecimal(listWagePay.get(sqls.toUpperCase()).toString()) + ",");
									}
								}
							}
							sqlWage.deleteCharAt(sqlWage.length() - 1);
							addMap1.put("sqlWage", sqlWage.toString());
						}
						aphiEmpBonusAuditMapper.updateBatchWage(addList);
					}

				}

			}

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败\"}";

		}
	}

	@Override
	public String updateEmpBonus(Map<String, Object> entityMap) throws DataAccessException {

		String year_month = (String) entityMap.get("acct_yearm");

		String acct_year = year_month.substring(0, 4);

		String acct_month = year_month.substring(4, 6);

		entityMap.put("acct_month", acct_month);

		entityMap.put("acct_year", acct_year);

		try {

			entityMap.put("is_grant", "1");

			aphiEmpBonusAuditMapper.updateEmpBonusAuditData(entityMap);

			return "{\"msg\":\"发放成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"发放失败 数据库异常 请联系管理员! 错误编码  updateEmpBonus\"}";
		}

	}

	@Override
	public String deleteHpmEmpBonusDataForReport(List<Map<String, Object>> entityList) throws DataAccessException {

		for (Map<String, Object> entityMap : entityList) {

			List<AphiEmpBonusData> eba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			/*
			 * if (eba != null) { if (eba.getIs_audit() == 1) { return
			 * "{\"error\":\"本月奖金已审核,不能删除本月数据\"}"; }
			 */
			if (eba != null) {
				for (AphiEmpBonusData ids : eba) {
					if (ids.getIs_grant() == 1) {
						return "{\"error\":\"此项目奖金已发放,不能删除本月数据\"}";
					}
				}

			}
		}

		try {

			for (Map<String, Object> entityMap : entityList) {

				List<AphiEmpBonusData> aebdList = aphiEmpBonusDataMapper.queryEmpBonusDataByKey(entityMap);

				if (aebdList.size() > 0) {

					for (AphiEmpBonusData aebd : aebdList) {

						if (aebd.getIs_audit() == 1) {

							return "{\"msg\":\"存在审核数据 无法完成删除数据.\",\"state\":\"true\"}";
						}

					}

				}

			}

			for (Map<String, Object> entityMap : entityList) {

				aphiEmpBonusDataMapper.deleteEmpBonusData(entityMap);

			}

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败\"}";
		}

	}

	private static Map<String, String> dept_name = new HashMap<>();
	static {
		dept_name.put("1057", "1057,1058");// 耳鼻喉病房
		dept_name.put("1058", "1057,1058");// 耳鼻喉门诊

		dept_name.put("1059", "1059,1060,1065");// 眼科病房
		dept_name.put("1060", "1059,1060,1065");// 眼科门诊
		dept_name.put("1065", "1059,1060,1065");// 神经外科--本部

		dept_name.put("1062", "1062,1063");// 口腔门诊
		dept_name.put("1063", "1062,1063");// 口腔病房

		dept_name.put("1076", "1076,1099");// 肾内科
		dept_name.put("1099", "1076,1099");// 透析中心

		dept_name.put("1084", "1084,1085");// 儿科
		dept_name.put("1085", "1084,1085");// 儿科门诊

		dept_name.put("1125", "1125,1088");// 放射科
		dept_name.put("1088", "1125,1088");// 介入科

		dept_name.put("1053", "1053,1090");// 心脏外科
		dept_name.put("1090", "1053,1090");// 生物治疗科

		dept_name.put("1185", "1185,1154");// 经营办
		dept_name.put("1154", "1185,1154");// 咖啡厅
	}

	@Override
	public String auditHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			String year_month = (String) entityMap.get("acct_yearm");
			entityMap.put("acct_year", year_month.substring(0, 4));

			entityMap.put("acct_month", year_month.substring(4, 6));

			String dept_id = String.valueOf(entityMap.get("dept_id"));

			if (dept_id != null && !"null".equals(dept_id) && !"".equals(dept_id)) {

				entityMap.put("dept_id", dept_id.split(",")[0]);

				entityMap.put("dept_no", dept_id.split(",")[1]);

			}

			// 根据checkIds来判断是不是单个审批
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
			if (entityMap.get("checkIds") != null) {
				// 把科室id和no循环出来
				for (String id : entityMap.get("checkIds").toString().split(",")) {
					Map<String, Object> mapVo = new HashMap<String, Object>();
					String[] ids = id.split("@");
					mapVo.put("dept_id", ids[0]);
					mapVo.put("dept_no", ids[1]);
					// mapVo.put("emp_id", ids[2]);mapVo.put("emp_no", ids[3]);

					// 有一种情况,就是不选下拉框,也选择复选框的时候
					if (ids.length > 2) {
						mapVo.put("item_codes", ids[2]);
					}

					listVo.add(mapVo);
				}
			} else {
				// 验证该部门是否有其他关联部门
				String dept_ids = dept_name.get(entityMap.get("dept_id"));
				// 如果存在其他关联部门
				if (dept_ids != null) {
					String[] depts = dept_ids.split(",");
					for (String ids : depts) {
						Map<String, Object> map = new HashMap<>();
						map.put("dept_id", ids);
						listVo.add(map);
					}
				} else {
					// 不存在关联部门，则直接
					Map<String, Object> map = new HashMap<>();
					map.put("dept_id", entityMap.get("dept_id"));
					listVo.add(map);
				}
			}
			entityMap.put("list", listVo);

			// 判断是否在发放页面点击发放按钮
			List<AphiDeptBonusGrant> grantList1 = aphiDeptBonusGrantMapper.queryListDeptBonusGrant(entityMap);

			if (grantList1.size() == 0) {

				return "{\"error\":\"部分科室没有在发放页面做数据\"}";
			}

			for (AphiDeptBonusGrant grant : grantList1) {

				if (grant.getIs_grant() != 1) {

					return "{\"error\":\"" + grant.getDept_name() + "没有选择在发放页面选择发放\"}";
				}
			}

			List<AphiEmpBonusData> aeba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			if (aeba != null) {
				for (AphiEmpBonusData ids : aeba) {
					if (ids.getIs_grant() == 1) {
						return "{\"error\":\"本月奖金已发放,不能操作数据\"}";
					}

					if (ids.getIs_two_audit() == 1) {
						return "{\"error\":\"本月奖金领导审核,不能操作数据\"}";
					}
				}
			}
			
			if ("1".equals(entityMap.get("is_audit").toString())) {
				// 验证项目金额与数据中的总金额是否一致
				// 获取项目金额
				String grant_money = aphiDeptBonusGrantMapper.querySumDeptBonusGrant(entityMap);
				if (grant_money != null) {
					// 获取本部门所有职工的金额和
					String sum_money = aphiEmpBonusDataMapper.querySumHpmEmpBonusData(entityMap);
					BigDecimal grant = new BigDecimal(grant_money);
					BigDecimal sum = new BigDecimal(sum_money);
					if (grant.compareTo(sum) != 0) {
						String dept_ids = dept_name.get(entityMap.get("dept_id"));
						// 如果存在其他关联部门
						if (dept_ids != null) {
							return "{\"error\":\"项目金额与职工的奖金总和不一致，差额为 " + grant.subtract(sum).doubleValue() + "，请确定数据与其关联科室数据。\"}";
						}
						return "{\"error\":\"项目金额与职工的奖金总和不一致，差额为 " + grant.subtract(sum).doubleValue() + "，请确定数据。\"}";
					}
				}
			}

			for (AphiEmpBonusData ids : aeba) {

				entityMap.put("is_audit", entityMap.get("is_audit"));

				if (entityMap.get("dept_id") != null && "".equals(entityMap.get("dept_id"))
						&& entityMap.get("dept_no") != null && "".equals(entityMap.get("dept_no"))) {

					entityMap.put("dept_id", entityMap.get("dept_id"));
					entityMap.put("dept_no", entityMap.get("dept_no"));

				} else {

					entityMap.put("dept_id", ids.getDept_id());
					entityMap.put("dept_no", ids.getDept_no());
				}

				if (entityMap.get("item_code") != null && "".equals(entityMap.get("dept_id"))) {

					entityMap.put("item_code", entityMap.get("item_code"));

				} else {

					entityMap.put("item_code", ids.getItem_code());
				}

				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("group_id", ids.getGroup_id());
				queryMap.put("hos_id", ids.getHos_id());
				queryMap.put("copy_code", ids.getCopy_code());
				queryMap.put("acct_year", ids.getAcct_year());
				queryMap.put("acct_month", ids.getAcct_month());
				queryMap.put("dept_id", ids.getDept_id());
				queryMap.put("dept_no", ids.getDept_no());
				queryMap.put("user_id", SessionManager.getUserId());
				queryMap.put("item_code", ids.getItem_code());

				StringBuffer msg = new StringBuffer();

				if ("1".equals(entityMap.get("is_audit").toString())) {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					entityMap.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

					entityMap.put("user_code", SessionManager.getUserId());

					List<AphiDeptBonusGrant> grantList = aphiDeptBonusGrantMapper.queryDeptBonusGrant(queryMap);// 科室发放奖金
					Map<String, AphiDeptBonusGrant> grantMap = new HashMap<String, AphiDeptBonusGrant>();
					for (int x = 0; x < grantList.size(); x++) {
						AphiDeptBonusGrant adbg = grantList.get(x);
						String key = String.valueOf(adbg.getDept_id())
								+ String.valueOf(adbg.getDept_no() + adbg.getItem_code());
						grantMap.put(key, adbg);
					}

					List<AphiEmpBonusData> empBounsList = aphiEmpBonusDataMapper
							.queryEmpBonusDataByDeptSumMoney(queryMap);
					for (AphiEmpBonusData aebd : empBounsList) {
						String key = String.valueOf(aebd.getDept_id()) + String.valueOf(aebd.getDept_no())
								+ aebd.getItem_code();
						AphiDeptBonusGrant deptGrant = grantMap.get(key);

						if (deptGrant == null) {
							msg.append("科室【" + aebd.getDept_name() + "】在绩效工资发放中不存在<br/>");
						} else {
							String dept_ids = dept_name.get(String.valueOf(aebd.getDept_id()));
							// 如果存在其他关联部门
							if (dept_ids == null) {
								if (!String.valueOf(deptGrant.getGrant_money())
										.equals(String.valueOf(aebd.getBonus_money()))) {
									msg.append("科室【" + aebd.getDept_name() + "】上报金额与下发金额不一致<br/>");
								}
							}
						}
					}
				} else {

					entityMap.put("audit_date", "");

					entityMap.put("user_code", "");

				}

				if (msg.length() > 0) {

					return "{\"warn\":\"" + msg.toString() + "\",\"state\":\"false\"}";
				}

				aphiEmpBonusDataMapper.updateEmpBonusData(entityMap);

			}

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败\"}";

		}

	}

	@Override
	public String twoAuditHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException {

		try {

			String year_month = (String) entityMap.get("acct_yearm");

			entityMap.put("acct_year", year_month.substring(0, 4));

			entityMap.put("acct_month", year_month.substring(4, 6));

			String dept_id = String.valueOf(entityMap.get("dept_id"));

			if (dept_id != null && !"null".equals(dept_id) && !"".equals(dept_id)) {

				entityMap.put("dept_id", dept_id.split(",")[0]);

				entityMap.put("dept_no", dept_id.split(",")[1]);

			}

			// 根据checkIds来判断是不是单个审批
			List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

			if (entityMap.get("checkIds") != null) {

				// 把科室id和no循环出来
				for (String id : entityMap.get("checkIds").toString().split(",")) {

					Map<String, Object> mapVo = new HashMap<String, Object>();

					String[] ids = id.split("@");

					mapVo.put("dept_id", ids[0]);
					mapVo.put("dept_no", ids[1]);

					// mapVo.put("emp_id", ids[2]);mapVo.put("emp_no", ids[3]);

					// 有一种情况,就是不选下拉框,也选择复选框的时候
					if (ids.length > 2) {
						mapVo.put("item_codes", ids[2]);
					}

					listVo.add(mapVo);

				}
			}

			entityMap.put("list", listVo);

			List<AphiEmpBonusData> aeba = aphiEmpBonusDataMapper.queryEmpBonusAuditByCode(entityMap);

			if (aeba != null) {
				for (AphiEmpBonusData ids : aeba) {
					if (ids.getIs_grant() == 1) {
						return "{\"error\":\"本月奖金已发放,不能审核数据\"}";
					}
				}

			}

			for (AphiEmpBonusData ids : aeba) {

				// map 用于查询当前核算年月数据
				Map<String, Object> map = new HashMap<String, Object>();

				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				map.put("acct_year", entityMap.get("acct_year"));
				map.put("acct_month", entityMap.get("acct_month"));
				map.put("user_id", entityMap.get("user_id"));
				map.put("para_value", entityMap.get("para_value"));

				if (entityMap.get("item_code") != null) {

					map.put("item_code", entityMap.get("item_code"));
				}
				if (entityMap.get("dept_id") != null) {

					map.put("dept_id", entityMap.get("dept_id"));
				}
				if (entityMap.get("dept_no") != null) {

					map.put("dept_no", entityMap.get("dept_no"));
				}

				map.put("list", listVo);

				List<Map<String, Object>> aebdListUp = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForReport(map);

				List<Map<String, Object>> aebdList = ChdJson.toListLower(aebdListUp);

				StringBuffer msg = new StringBuffer();// 未审核数据提示

				Map<String, Object> exitMap = new HashMap<String, Object>();

				for (Map<String, Object> aebd : aebdList) {

					if ("1".equals(aebd.get("is_audit").toString())) {
						continue;
					}

					String key = aebd.get("dept_code") + "|" + aebd.get("dept_name");

					if (exitMap.get(key) != null) {
						continue;
					}

					msg.append("当前核算年月 " + aebd.get("dept_name") + " 科室存在未审核数据<br>");

					exitMap.put(key, key);
				}

				if (msg.length() > 0) {
					return "{\"error\":\"" + msg.toString() + "\"}";
				}

				entityMap.put("is_two_audit", entityMap.get("is_two_audit"));

				if (entityMap.get("dept_id") != null && "".equals(entityMap.get("dept_id"))
						&& entityMap.get("dept_no") != null && "".equals(entityMap.get("dept_no"))) {

					entityMap.put("dept_id", entityMap.get("dept_id"));
					entityMap.put("dept_no", entityMap.get("dept_no"));

				} else {

					entityMap.put("dept_id", ids.getDept_id());
					entityMap.put("dept_no", ids.getDept_no());
				}

				if (entityMap.get("item_code") != null && "".equals(entityMap.get("dept_id"))) {

					entityMap.put("item_code", entityMap.get("item_code"));

				} else {

					entityMap.put("item_code", ids.getItem_code());
				}

				Map<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("group_id", ids.getGroup_id());
				queryMap.put("hos_id", ids.getHos_id());
				queryMap.put("copy_code", ids.getCopy_code());
				queryMap.put("acct_year", ids.getAcct_year());
				queryMap.put("acct_month", ids.getAcct_month());
				queryMap.put("dept_id", ids.getDept_id());
				queryMap.put("dept_no", ids.getDept_no());
				queryMap.put("user_id", SessionManager.getUserId());

				// StringBuffer msg = new StringBuffer();

				if ("1".equals(entityMap.get("is_two_audit").toString())) {

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					entityMap.put("two_audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

					entityMap.put("two_user_code", SessionManager.getUserId());

					List<AphiDeptBonusGrant> grantList = aphiDeptBonusGrantMapper.queryDeptBonusGrant(queryMap);// 科室发放奖金

					Map<String, AphiDeptBonusGrant> grantMap = new HashMap<String, AphiDeptBonusGrant>();
					for (int x = 0; x < grantList.size(); x++) {
						AphiDeptBonusGrant adbg = grantList.get(x);
						String key = String.valueOf(adbg.getDept_id())
								+ String.valueOf(adbg.getDept_no() + adbg.getItem_code());
						grantMap.put(key, adbg);
					}

					List<AphiEmpBonusData> empBounsList = aphiEmpBonusDataMapper
							.queryEmpBonusDataByDeptSumMoney(queryMap);

					for (AphiEmpBonusData aebd : empBounsList) {
						String key = String.valueOf(aebd.getDept_id())
								+ String.valueOf(aebd.getDept_no() + aebd.getItem_code());
						AphiDeptBonusGrant deptGrant = grantMap.get(key);

						if (deptGrant == null) {
							msg.append("科室【" + aebd.getDept_name() + "】在绩效工资发放中不存在<br/>");
						} else {

							if (!String.valueOf(deptGrant.getGrant_money())
									.equals(String.valueOf(aebd.getBonus_money()))) {
								msg.append("科室【" + aebd.getDept_name() + "】上报金额与下发金额不一致<br/>");
							}
						}
					}

				} else {

					entityMap.put("two_audit_date", "");

					entityMap.put("two_user_code", "");

				}

				if (msg.length() > 0) {

					return "{\"warn\":\"" + msg.toString() + "\",\"state\":\"false\"}";
				}

				aphiEmpBonusDataMapper.updateEmpBonusData(entityMap);

			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"操作失败\"}";

		}

	}

	@Override
	public String queryHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_month", year_month.substring(4, 6));
		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("is_avg", "1");

		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

		if (itemList.size() == 0) {
			return "{\"warn\":\"请维护参与核算的奖金项目\"}";
		}

		StringBuffer sql = new StringBuffer();
		StringBuffer sql_sum = new StringBuffer();
		StringBuffer sql_columns = new StringBuffer();
		StringBuffer sql_null = new StringBuffer();

		if (itemList.size() > 0) {

			for (int x = 0; x < itemList.size(); x++) {

				AphiItem aebd = itemList.get(x);

				sql.append("sum(case when aebd.item_code='" + aebd.getItem_code()
						+ "' then aebd.bonus_money end) as item_code_" + aebd.getItem_code() + ",");
				sql_sum.append(
						"nvl((case when aebd.item_code = '" + aebd.getItem_code() + "' then aebd.bonus_money end),0)+");
				sql_columns.append("NULL AS item_code_" + aebd.getItem_code() + ",");
				sql_null.append(" aebd.bonus_money as item_code_" + aebd.getItem_code() + ",");
			}

		}

		entityMap.put("sql_columns", String.valueOf(sql_columns));

		if (sql.length() > 0) {

			String sumSql = "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,";
			entityMap.put("sql", sql.toString() + sumSql);
			String nullSql = sql_sum.substring(0, sql_sum.length() - 1).toString() + "as sum_money,";
			entityMap.put("nullsql", sql_null.toString() + nullSql);
			entityMap.put("is_group", "1");

		}

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForUpReport(entityMap);

			return ChdJson.toJson(ChdJson.toJsonLower(list));

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForUpReport(entityMap,
					rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(ChdJson.toListLower(list), page.getTotal());
		}
	}

	@Override
	public String importHpmEmpBonusDataForReport(Map<String, Object> entityMap) throws DataAccessException {
		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			// 缺少审核验证

			if (MyConfig.getSysPara("09001") == null) {
				entityMap.put("para_value", 0);
			} else {
				entityMap.put("para_value", MyConfig.getSysPara("09001"));
			}

			String columns = entityMap.get("columns").toString();

			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			String content = entityMap.get("content").toString();
			List<Map<String, List<String>>> allDataList = SpreadTableJSUtil.toListMap(content, 1);
			if (allDataList == null || allDataList.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			entityMap.put("is_avg", "1");

			List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

			if (itemList.size() == 0) {
				return "{\"warn\":\"请维护参与核算的奖金项目\"}";
			}

			Map<String, AphiItem> targetMap = new HashMap<String, AphiItem>();
			for (AphiItem aei : itemList) {
				targetMap.put(aei.getItem_code(), aei);
				targetMap.put(aei.getItem_name(), aei);
			}

			// 判断表头中工资项目是否存在
			StringBuffer sb = new StringBuffer();// 提示信息:用于存储表头中不存在的指标
			Map<String, String> targetColumnMap = new HashMap<String, String>();// 用于存储表头中的指标,作为遍历数据时取指标值

			for (Map<String, List<String>> item : allDataList) {
				for (Map.Entry<String, List<String>> entry : item.entrySet()) {
					String key = entry.getKey();
					if ("核算年度".equals(key) || "核算月份".equals(key) || "科室名称".equals(key) || "职工名称".equals(key)) {
						continue;
					}

					targetColumnMap.put(key, key);
					if (targetMap.get(key) == null) {
						sb.append("工资项目" + key + "不存在,");
					}
				}
				break;// 判断指标表头是否存在,只遍历一次
			}

			if (targetColumnMap == null || targetColumnMap.size() == 0) {
				return "{\"error\":\"表头中未存在工资项目或未填写工资项目值\",\"state\":\"false\"}";
			}

			// Map<String, Object> entityMap = new HashMap<String, Object>();
			// entityMap.put("group_id", SessionManager.getGroupId());
			// entityMap.put("hos_id", SessionManager.getHosId());
			// entityMap.put("copy_code", SessionManager.getCopyCode());

			// 查询 职工字典 List
			entityMap.put("from_para", check_from_para());
			List<AphiEmp> empList = aphiEmpMapper.queryAphiEmpDict(entityMap);
			// 用于存储查询targetList中的AphiTarget对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, AphiEmp> empMap = new HashMap<String, AphiEmp>();
			// 将指标List存入Map 键:emp_code 值:AphiEmp
			for (AphiEmp emp : empList) {
				empMap.put(emp.getEmp_code(), emp);
				empMap.put(emp.getEmp_name(), emp);
			}

			// 查询科室字典 List
			List<AphiDeptDict> deptList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			// 用于存储查询deptList中的AphiDeptDict对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDeptDict> deptMap = new HashMap<String, AphiDeptDict>();
			// 将科室List存入Map 键:dept_name 值:AphiDeptDict
			for (AphiDeptDict dept : deptList) {
				deptMap.put(dept.getDept_code(), dept);
				deptMap.put(dept.getDept_name(), dept);
			}

			// 用于储存查询empDutyList中的AphiEmpDuty对象,以键值对的形式存储,用于判断职务是否存在
			Map<String, AphiEmpDuty> empDutyMap = new HashMap<String, AphiEmpDuty>();

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();

			// 存储保存数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();

			for (Map.Entry<String, String> entry : targetColumnMap.entrySet()) {// 遍历工资项目

				// 遍历导入数据
				for (Map<String, List<String>> item : allDataList) {

					List<String> acct_year = item.get("核算年度");
					List<String> acct_month = item.get("核算月份");
					List<String> dept_name = item.get("科室名称");
					List<String> emp_name = item.get("职工名称");
					List<String> item_code = item.get(entry.getKey());

					if (acct_year.get(1) == null) {
						return "{\"warn\":\"核算年度！\",\"state\":\"false\",\"row_cell\":\"" + acct_year.get(0) + "\"}";
					}
					if (acct_month.get(1) == null) {
						return "{\"warn\":\"核算年度！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}

					if (dept_name.get(1) == null) {
						return "{\"warn\":\"科室名称！\",\"state\":\"false\",\"row_cell\":\"" + dept_name.get(0) + "\"}";
					} else {
						if (deptMap.get(dept_name.get(1)) == null) {
							return "{\"warn\":\"" + dept_name.get(1) + ",科室名称不存在！\",\"state\":\"false\",\"row_cell\":\""
									+ dept_name.get(0) + "\"}";
						}
					}

					if (emp_name.get(1) == null) {
						return "{\"warn\":\"职工名称为空！\",\"state\":\"false\",\"row_cell\":\"" + emp_name.get(0) + "\"}";
					} else {
						if (empMap.get(emp_name.get(1)) == null) {
							return "{\"warn\":\"" + emp_name.get(1) + ",职工不存在！\",\"state\":\"false\",\"row_cell\":\""
									+ emp_name.get(0) + "\"}";
						}
					}

					if (item_code.get(1) == null) {
						return "{\"warn\":\"奖金额！\",\"state\":\"false\",\"row_cell\":\"" + item_code.get(0) + "\"}";
					}

					// 以职工编码+职工名称+科室名称+职务名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + acct_month.get(1) + dept_name.get(1) + emp_name.get(1)
							+ targetMap.get(entry.getKey()).getItem_code();
					if (exitMap.get(key) != null) {
						err_sb.append(acct_year.get(1) + "核算年度," + acct_month.get(1) + "核算月份");
						err_sb.append(
								dept_name.get(1) + "科室," + emp_name.get(1) + "职工," + entry.getKey() + "工资项目编码<br/>");
					} else {
						exitMap.put(key, key);
					}

					Map<String, Object> addMap = new HashMap<String, Object>();

					addMap.put("group_id", entityMap.get("group_id"));
					addMap.put("hos_id", entityMap.get("hos_id"));

					addMap.put("copy_code", entityMap.get("copy_code"));

					addMap.put("acct_year", acct_year.get(1));
					addMap.put("acct_month", acct_month.get(1));

					addMap.put("emp_id", empMap.get(emp_name.get(1)).getEmp_id());
					addMap.put("emp_no", empMap.get(emp_name.get(1)).getEmp_no());

					addMap.put("dept_id", deptMap.get(dept_name.get(1)).getDept_id());
					addMap.put("dept_no", deptMap.get(dept_name.get(1)).getDept_no());
					addMap.put("item_code", targetMap.get(entry.getKey()).getItem_code());
					addMap.put("bonus_money", item_code.get(1));
					addMap.put("is_audit", 0);
					addMap.put("is_grant", 0);
					addMap.put("is_two_audit", 0);

					addList.add(addMap);

				}

			}

			if (err_sb.length() > 0) {// 重复数据是否存在

				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";

			} else {

				aphiEmpBonusDataMapper.deleteBatchEmpBonusData(addList);

				aphiEmpBonusDataMapper.addBatchEmpBonusData(addList);

				return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String queryHpmEmpBonusDataForReportGrid(Map<String, Object> entityMap) throws DataAccessException {

		entityMap.put("is_stip", "0");
		entityMap.put("is_avg", "1");
		Integer is_two_audit = 0;

		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

		if (itemList.size() == 0) {
			return "{\"warn\":\"请维护参与核算的奖金项目\"}";
		}

		StringBuffer columns = new StringBuffer();

		columns.append("{Rows:[");

		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:80},");

		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:160},");

		columns.append("{ display: \'职工编码\', name: \'emp_code\', align: \'left\',width:80},");

		columns.append("{ display: \'职工姓名\', name: \'emp_name\', align: \'left\',width:120},");

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = itemList.get(i);

			is_two_audit = item.getIs_two_audit();

			columns.append("{display : \'" + item.getItem_name() + "\',name : \'item_code_"
					+ item.getItem_code().toLowerCase() + "\',align : \'right\',width:160,editor:{type:\'float\'},"
					+ "render : function(rowdata, rowindex,value,col){" + "var col = arguments[arguments.length - 1];"
					+ "if (rowdata.emp_id == null || rowdata.emp_id == '') {"
					+ "rowdata.notEidtColNames.push(col.columnname); return '';}"
					+ "if (rowdata.is_audit == 1) {rowdata.notEidtColNames.push(col.columnname);}"
					+ "return formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],2,1);}" + "},");
		}

		if (!"".equals(entityMap.get("item_code"))) {

			columns.append("{display: \'是否审核\', name: \'is_audit\', align: \'left\',width:80,render:function(rowdata){"
					+ "if(rowdata.is_audit == \'1\'){" + "	return \'是\';" + "}else if(rowdata.is_audit == \'0\'){"
					+ "return \'否\';" + "}else{return \'\';}}},");

			columns.append("{ display: \'审核人\', name: \'user_name\', align: \'left\',width:180},");

			columns.append("{ display: \'审核时间\', name: \'audit_date\', align: \'left\',width:180},");

			if (is_two_audit != null) {

				if (is_two_audit == 1) {

					columns.append(
							"{display: \'领导审核\', name: \'is_two_audit\', align: \'left\',width:80,render:function(rowdata){"
									+ "if(rowdata.is_two_audit == \'1\'){" + "	return \'是\';"
									+ "}else if(rowdata.is_two_audit == \'0\'){" + "return \'否\';"
									+ "}else{return \'\';}}},");

					columns.append("{ display: \'审核人\', name: \'two_user_name\', align: \'left\',width:180},");

					columns.append("{ display: \'审核时间\', name: \'two_audit_date\', align: \'left\',width:180},");
				}
			}

		}

		columns.append("{ display: \'合计\', name: \'sum_money\', align: \'right\',width:160,"
				+ "render: function (rowdata , rowindex , value){"
				+ "return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,2,1);}" + "}");

		columns.append("]}");

		return columns.toString();
	}

	/**
	 * 按照科室项目查询科室方法金额 分三种情况 1.当前月发放的金额 2.历史预留的金额 3.历史没有发放的金额
	 * 
	 * 用 union all 拼到一起 返回到程序
	 */
	@Override
	public String queryHpmEmpBonusDataDeptGrantSumMoney(Map<String, Object> entityMap) throws DataAccessException {

		AphiDeptBonusGrant adbg = aphiDeptBonusGrantMapper.queryDeptBonusGrantByCode(entityMap);

		String grant_money = "0";

		if (adbg == null) {

			return grant_money;
		}

		return String.valueOf(adbg.getGrant_money());
	}

	@Override
	public String queryAphiEmpBonusDataDeptState(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<AphiEmpBonusData> list = aphiEmpBonusDataMapper.queryAphiEmpBonusDataDeptState(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<AphiEmpBonusData> list = aphiEmpBonusDataMapper.queryAphiEmpBonusDataDeptState(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());
		}
	}

	@Override
	public List<Map<String, Object>> queryEmpBonusDataPrint(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}

		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {
			entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));
			entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4,
					entityMap.get("acct_yearm").toString().length()));
		}

		List<Map<String, Object>> list = aphiEmpBonusDataMapper.queryEmpBonusDataPrint(entityMap);

		return list;

	}

	@Override
	public List<Map<String, Object>> queryHpmEmpBonusDataForReportPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		// TODO Auto-generated method stub
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}

		if (MyConfig.getSysPara("09001") == null) {
			entityMap.put("para_value", 0);
		} else {
			entityMap.put("para_value", MyConfig.getSysPara("09001"));
		}

		String year_month = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_month", year_month.substring(4, 6));
		entityMap.put("acct_year", year_month.substring(0, 4));

		entityMap.put("is_avg", "1");

		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);

		StringBuffer sql = new StringBuffer();
		StringBuffer sql_sum = new StringBuffer();
		StringBuffer sql_columns = new StringBuffer();
		StringBuffer sql_null = new StringBuffer();

		if (itemList.size() > 0) {

			for (int x = 0; x < itemList.size(); x++) {

				AphiItem aebd = itemList.get(x);

				sql.append("sum(case when aebd.item_code='" + aebd.getItem_code()
						+ "' then aebd.bonus_money end) as item_code_" + aebd.getItem_code() + ",");
				sql_sum.append(
						"nvl((case when aebd.item_code = '" + aebd.getItem_code() + "' then aebd.bonus_money end),0)+");
				sql_columns.append("NULL AS item_code_" + aebd.getItem_code() + ",");
				sql_null.append(" aebd.bonus_money as item_code_" + aebd.getItem_code() + ",");
			}

		}

		entityMap.put("sql_columns", String.valueOf(sql_columns));

		if (sql.length() > 0) {

			String sumSql = "sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,";
			entityMap.put("sql", sql.toString() + sumSql);
			String nullSql = sql_sum.substring(0, sql_sum.length() - 1).toString() + "as sum_money,";
			entityMap.put("nullsql", sql_null.toString() + nullSql);
			entityMap.put("is_group", "1");

		}

		List<Map<String, Object>> list = aphiEmpBonusDataMapper.queryHpmEmpBonusDataForUpReportPrint(entityMap);

		return list;

	}

	@Override
	public String querydataAuditaBonus(Map<String, Object> entityMap) throws DataAccessException {
		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		AphiEmpBonusData adba = aphiEmpBonusDataMapper.queryEmpBonusDataAdult(entityMap);

		if (adba != null) {

			return "{\"is_audit\":\"" + adba.getIs_audit() + "\"}";

		} else {

			return "{\"is_audit\":\"0\"}";

		}
	}

	private String check_from_para() {
		String return_msg = null;
		String dept_para = null;
		String emp_para = null;
		if (MyConfig.getSysPara("09004") == null) {
			dept_para = "0";
		} else {
			dept_para = MyConfig.getSysPara("09004");
		}
		if (MyConfig.getSysPara("09001") == null) {
			emp_para = "0";
		} else {
			emp_para = MyConfig.getSysPara("09001");
		}

		if ("0".equals(dept_para)) {
			if ("0".equals(emp_para)) {
				return_msg = "temp_all_aphi";
			} else {
				return_msg = "temp_dept_aphi";
			}
		} else {
			if ("0".equals(emp_para)) {
				return_msg = "temp_emp_aphi";
			} else {
				return_msg = "temp_no_aphi";
			}
		}
		return return_msg;
	}
}
