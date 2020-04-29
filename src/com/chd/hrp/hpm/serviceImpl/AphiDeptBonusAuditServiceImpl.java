package com.chd.hrp.hpm.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.hrp.hpm.dao.AphiDeptBonusAuditMapper;
import com.chd.hrp.hpm.dao.AphiDeptBonusDataMapper;
import com.chd.hrp.hpm.dao.AphiDeptBonusGrantMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.dao.AphiDeptHipMapper;
import com.chd.hrp.hpm.dao.AphiDeptMapper;
import com.chd.hrp.hpm.dao.AphiItemMapper;
import com.chd.hrp.hpm.entity.AphiDept;
import com.chd.hrp.hpm.entity.AphiDeptBonusAudit;
import com.chd.hrp.hpm.entity.AphiDeptBonusData;
import com.chd.hrp.hpm.entity.AphiDeptBonusGrant;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.entity.AphiDeptHip;
import com.chd.hrp.hpm.entity.AphiItem;
import com.chd.hrp.hpm.service.AphiDeptBonusAuditService;
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

@Service("aphiDeptBonusAuditService")
public class AphiDeptBonusAuditServiceImpl implements AphiDeptBonusAuditService {

	private static Logger logger = Logger.getLogger(AphiDeptBonusAuditServiceImpl.class);

	@Resource(name = "aphiDeptBonusAuditMapper")
	private final AphiDeptBonusAuditMapper aphiDeptBonusAuditMapper = null;

	@Resource(name = "aphiDeptBonusDataMapper")
	private final AphiDeptBonusDataMapper aphiDeptBonusDataMapper = null;

	@Resource(name = "aphiItemMapper")
	private final AphiItemMapper aphiItemMapper = null;

	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;

	@Resource(name = "aphiDeptMapper")
	private final AphiDeptMapper aphiDeptMapper = null;

	@Resource(name = "aphiDeptHipMapper")
	private final AphiDeptHipMapper aphiDeptHipMapper = null;

	@Resource(name = "aphiDeptBonusGrantMapper")
	private final AphiDeptBonusGrantMapper aphiDeptBonusGrantMapper = null;

	/*
	 * @Resource(name = "aphiDeptBonusGrantItemMapper") private final
	 * AphiDeptBonusGrantItemMapper aphiDeptBonusGrantItemMapper = null;
	 */
	/**
	 * 
	 */
	@Override
	public String queryHpmDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException {

		List<AphiItem> itemList = getGridTitleMap(entityMap);

		StringBuffer sql = new StringBuffer();

		StringBuffer sql_sum = new StringBuffer();

		if (itemList.size() == 0) {

			return "{\"error\":\"没有查询到核算的项目.\",\"state\":\"false\"}";

		}

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("sum(nvl((case when adbd.item_code = '" + item.getItem_code()
					+ "' then bonus_money end),0)) as  item_code" + item.getItem_code() + ",");

			if ("1".equals(String.valueOf(item.getIs_sum()))) {

				sql_sum.append(
						"nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
			}
		}

		entityMap.put("sql", sql.toString());

		if (sql_sum.length() > 0) {

			entityMap.put("sql_sum",
					"sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,");
		}

		if (!"".equals(entityMap.get("item_code"))) {

			entityMap.put("sql_item",
					",case when adbd.is_audit = 0 then '新建' when adbd.is_audit = 1 then '审核' else '' end is_audit,"
							+ " adbd.audit_date," + " adbd.user_code," + " su.user_name," + "adbd.note");

			entityMap.put("sql_group_by",
					",adbd.is_audit,su.user_name," + "adbd.audit_date," + "adbd.user_code," + "adbd.note");

		}

		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoney(entityMap);

			return ChdJson.toJsonLower(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoney(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJsonLower(list, page.getTotal());

		}
	}

	public List<AphiItem> getGridTitleMap(Map<String, Object> map) throws DataAccessException {

		List<AphiItem> itemList = aphiItemMapper.qeuryItemData(map);

		if (itemList.size() > 0) {

			return itemList;

		}
		return aphiItemMapper.qeuryItemMapGrid(map);
	}

	@Override
	public String querydataAudita(Map<String, Object> entityMap) throws DataAccessException {

		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));

		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		// AphiDeptBonusAudit adba =
		// aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);

		AphiDeptBonusData adba = aphiDeptBonusDataMapper.queryHpmDeptBonusDataByCode_Audit(entityMap);

		AphiDeptBonusGrant adba1 = aphiDeptBonusGrantMapper.queryDeptBonusGrantByCode_Grant(entityMap);

		if (adba != null) {

			return "{\"is_audit\":\"" + adba.getIs_audit() + "\",\"is_grant\":\"" + adba1.getIs_grant() + "\"}";

		} else {

			return "{\"is_audit\":\"0\",\"is_grant\":\"0\"}";

		}

	}

	/**
	 * 改 添加审核成功生成发放数据
	 */
	@Override
	public String updateHpmDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		String acct_yearm = (String) entityMap.get("acct_yearm");

		entityMap.put("acct_year", acct_yearm.substring(0, 4));
		entityMap.put("acct_month", acct_yearm.substring(4, 6));

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		Map<String, Object> mapVo1 = new HashMap<String, Object>();

		// 根据checkIds来判断是不是单个审批
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
		}

		entityMap.put("list", listVo);

		List<AphiDeptBonusGrant> adba = aphiDeptBonusGrantMapper.queryListDeptBonusGrant(entityMap);

		List<AphiDeptBonusData> data = aphiDeptBonusDataMapper.queryListDeptBonusData(entityMap);

		int audit_state = Integer.parseInt(entityMap.get("audit_state").toString());

		String reStr = null;

		if (audit_state == 1) {
			reStr = "审核";
		} else {
			reStr = "反审核";
		}

		if (adba.size() > 0) {
			return "{\"error\":\"当前状态已发放或者审核,不能" + reStr + ".\",\"state\":\"false\"}";
		}

		if (data.size() == 0) {

			return "{\"error\":\"已经审核过得数据,不能" + reStr + ".\",\"state\":\"false\"}";
		}

		try {

			if (audit_state == 1) {

				for (AphiDeptBonusData ids : data) {

					List<Map<String, Object>> listVo1 = new ArrayList<Map<String, Object>>();

					if ("1".equals(ids.getIs_audit())) {

						return "{\"error\":\"当前部分数据" + ids.getDept_name() + "已经审核,不能重复" + reStr
								+ ".\",\"state\":\"false\"}";
					}

					mapVo1.put("group_id", ids.getGroup_id());

					mapVo1.put("hos_id", ids.getHos_id());

					mapVo1.put("copy_code", ids.getCopy_code());

					mapVo1.put("acct_year", ids.getAcct_year());

					mapVo1.put("acct_month", ids.getAcct_month());

					mapVo1.put("dept_id", ids.getDept_id());

					mapVo1.put("dept_no", ids.getDept_no());

					mapVo1.put("item_code", ids.getItem_code());

					mapVo1.put("is_audit", 1);

					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

					mapVo1.put("audit_date", DateUtil.stringToDate(sdf.format(new Date()), "yyyy-MM-dd"));

					mapVo1.put("user_code", SessionManager.getUserId());

					listVo1.add(mapVo1);

					aphiDeptBonusDataMapper.updateBatch(listVo1);

				}

				// 生成发放
				for (AphiDeptBonusData ids : data) {

					mapVo1.put("group_id", ids.getGroup_id());

					mapVo1.put("hos_id", ids.getHos_id());

					mapVo1.put("copy_code", ids.getCopy_code());

					mapVo1.put("acct_year", ids.getAcct_year());

					mapVo1.put("acct_month", ids.getAcct_month());

					mapVo1.put("dept_id", ids.getDept_id());

					mapVo1.put("dept_no", ids.getDept_no());

					mapVo1.put("item_code", ids.getItem_code());

					mapVo1.put("bonus_money", ids.getBonus_money());

					mapVo1.put("grant_money", ids.getBonus_money());

					mapVo1.put("activity_money", 0);

					mapVo1.put("activity_percent", 0);

					mapVo1.put("is_audit", 0);

					mapVo1.put("is_grant", 0);

					mapVo1.put("note", ids.getNote());

					aphiDeptBonusGrantMapper.add(mapVo1);

				}

			} else {

				for (AphiDeptBonusData ids : data) {
					List<Map<String, Object>> listVo1 = new ArrayList<Map<String, Object>>();

					mapVo1.put("group_id", ids.getGroup_id());

					mapVo1.put("hos_id", ids.getHos_id());

					mapVo1.put("copy_code", ids.getCopy_code());

					mapVo1.put("acct_year", ids.getAcct_year());

					mapVo1.put("acct_month", ids.getAcct_month());

					mapVo1.put("dept_id", ids.getDept_id());

					mapVo1.put("dept_no", ids.getDept_no());

					mapVo1.put("item_code", ids.getItem_code());

					mapVo1.put("is_audit", "0");

					mapVo1.put("audit_date", null);

					mapVo1.put("user_code", null);

					listVo1.add(mapVo1);

					aphiDeptBonusDataMapper.updateBatchBack(listVo1);

				}

				// 删除发放

				List<AphiDeptBonusGrant> adba1 = aphiDeptBonusGrantMapper.queryListDeptBonusGrant_Audit(entityMap);

				if (adba1.size() > 0) {

					return "{\"error\":\"当前发放页面审核,不能" + reStr + ".\",\"state\":\"false\"}";

				}

				for (AphiDeptBonusData ids : data) {

					mapVo1.put("group_id", ids.getGroup_id());

					mapVo1.put("hos_id", ids.getHos_id());

					mapVo1.put("copy_code", ids.getCopy_code());

					mapVo1.put("acct_year", ids.getAcct_year());

					mapVo1.put("acct_month", ids.getAcct_month());

					mapVo1.put("dept_id", ids.getDept_id());

					mapVo1.put("dept_no", ids.getDept_no());

					mapVo1.put("item_code", ids.getItem_code());

					mapVo1.put("bonus_money", ids.getBonus_money());

					mapVo1.put("is_audit", 0);

					aphiDeptBonusGrantMapper.deleteDeptBonusGrant(mapVo1);

				}

			}

			return "{\"msg\":\"" + reStr + "成功." + "\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"msg\":\"操作失败.\",\"state\":\"false\"}";

		}

		/*
		 * AphiDeptBonusAudit adba =
		 * aphiDeptBonusAuditMapper.queryDeptBonusAuditByCode(entityMap);
		 * 
		 * int audit_state = Integer.parseInt(entityMap.get("audit_state").toString());
		 * 
		 * String reStr = null;
		 * 
		 * if (audit_state == 1) { reStr = "审核"; } else { reStr = "反审核"; }
		 * 
		 * if (adba != null && adba.getIs_grant() == 1) {
		 * 
		 * return "{\"error\":\"当前状态已发放,不能" + reStr + ".\",\"state\":\"false\"}";
		 * 
		 * }
		 */

		/*
		 * try {
		 * 
		 * if (adba != null) {
		 * 
		 * entityMap.put("group_id", adba.getGroup_id());
		 * 
		 * entityMap.put("hos_id", adba.getHos_id());
		 * 
		 * entityMap.put("copy_code", adba.getCopy_code());
		 * 
		 * entityMap.put("acct_year", adba.getAcct_year());
		 * 
		 * entityMap.put("acct_month", adba.getAcct_month());
		 * 
		 * entityMap.put("is_audit", audit_state);
		 * 
		 * //entityMap.put("is_grant", adba.getIs_grant());
		 * 
		 * aphiDeptBonusAuditMapper.updateDeptBonusAudit(entityMap);
		 * 
		 * } else {
		 * 
		 * entityMap.put("group_id", entityMap.get("group_id"));
		 * 
		 * entityMap.put("hos_id", entityMap.get("hos_id"));
		 * 
		 * entityMap.put("copy_code", entityMap.get("copy_code"));
		 * 
		 * entityMap.put("acct_year", entityMap.get("acct_year"));
		 * 
		 * entityMap.put("acct_month", entityMap.get("acct_month"));
		 * 
		 * entityMap.put("is_audit", audit_state);
		 * 
		 * entityMap.put("is_grant", 0);
		 * 
		 * aphiDeptBonusAuditMapper.addDeptBonusAudit(entityMap);
		 * 
		 * }
		 * 
		 * // 工资发放生成功能 String grantmsg = ""; if (audit_state == 1) { Map<String, Object>
		 * para = SessionManager.getHpmParaMap(); if (para != null && para.get("09002")
		 * != null && Integer.parseInt(para.get("09002").toString()) == 1)
		 * {//审核下达时是否同时生成发放的数据
		 * 
		 * entityMap.remove("is_audit"); if(para != null && para.get("09003") != null &&
		 * Integer.parseInt(para.get("09003").toString()) == 1){//绩效工资发放方式:0按总额发放/1按项目发放
		 * //aphiDeptBonusGrantItemMapper.deleteDeptBonusGrantItem(entityMap);
		 * 
		 * entityMap.put("is_avg", "1");
		 * 
		 * List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);
		 * 
		 * if (itemList.size() == 0) { logger.debug("工资发放生成失败 ,没有查询到参与人均奖核算的奖金项目.");
		 * grantmsg = "工资发放生成失败 ,没有查询到参与人均奖核算的奖金项目."; }else{
		 * 
		 * List<Map<String, Object>> deptBonusDataList = aphiDeptBonusDataMapper
		 * .queryDeptBonusByItemForBonusMoney(entityMap);
		 * 
		 * if (deptBonusDataList.size() == 0) { logger.debug("工资发放生成失败 ,没有查询到奖金核算数据.");
		 * grantmsg = "工资发放生成失败 ,没有查询到奖金核算数据.";
		 * 
		 * }else{ List<Map<String, Object>> lowerList = new ArrayList<Map<String,
		 * Object>>();
		 * 
		 * for (Map<String, Object> map : deptBonusDataList) {
		 * 
		 * Map lowerMap = new HashMap();
		 * 
		 * for (String key : map.keySet()) {
		 * 
		 * lowerMap.put(key.toLowerCase(), map.get(key));
		 * 
		 * }
		 * 
		 * lowerList.add(lowerMap);
		 * 
		 * }
		 * 
		 * for (int i = 0; i < lowerList.size(); i++) {
		 * 
		 * Map<String, Object> map = (Map<String, Object>) lowerList.get(i);
		 * 
		 * Map<String, Object> mapGrant = new HashMap<String, Object>();
		 * 
		 * mapGrant.put("group_id", entityMap.get("group_id"));
		 * 
		 * mapGrant.put("hos_id", entityMap.get("hos_id"));
		 * 
		 * mapGrant.put("copy_code", entityMap.get("copy_code"));
		 * 
		 * mapGrant.put("acct_year", entityMap.get("acct_year"));
		 * 
		 * mapGrant.put("acct_month", entityMap.get("acct_month"));
		 * 
		 * mapGrant.put("dept_id", map.get("dept_id"));
		 * 
		 * mapGrant.put("dept_no", map.get("dept_no"));
		 * 
		 * mapGrant.put("item_code", map.get("item_code"));
		 * 
		 * mapGrant.put("bonus_money", map.get("sum_money"));
		 * 
		 * mapGrant.put("is_audit", 0);
		 * 
		 * mapGrant.put("activity_money", 0);
		 * 
		 * mapGrant.put("activity_percent", 0);
		 * 
		 * mapGrant.put("grant_money", map.get("sum_money"));
		 * 
		 * //mapGrant.put("item_code", value);
		 * //aphiDeptBonusGrantItemMapper.addDeptBonusGrantItem(mapGrant);
		 * 
		 * } } }
		 * 
		 * }else{ aphiDeptBonusGrantMapper.deleteDeptBonusGrant(entityMap);
		 * entityMap.put("is_avg", "1");
		 * 
		 * List<AphiItem> itemList = aphiItemMapper.qeuryItemData(entityMap);
		 * 
		 * if (itemList.size() == 0) { logger.debug("工资发放生成失败 ,没有查询到参与人均奖核算的奖金项目.");
		 * grantmsg = "工资发放生成失败 ,没有查询到参与人均奖核算的奖金项目."; }else{ StringBuffer sql_sum = new
		 * StringBuffer();
		 * 
		 * for (int i = 0; i < itemList.size(); i++) {
		 * 
		 * AphiItem item = (AphiItem) itemList.get(i);
		 * 
		 * sql_sum.append("nvl((case when adbd.item_code = '" + item.getItem_code() +
		 * "' then bonus_money end),0)+"); }
		 * 
		 * entityMap.put("sql_sum", "sum(" + sql_sum.substring(0, sql_sum.length() -
		 * 1).toString() + ") as sum_money,");
		 * 
		 * List<Map<String, Object>> deptBonusDataList = aphiDeptBonusDataMapper
		 * .queryDeptBonusForBonusMoney(entityMap);
		 * 
		 * if (deptBonusDataList.size() == 0) { logger.debug("工资发放生成失败 ,没有查询到奖金核算数据.");
		 * grantmsg = "工资发放生成失败 ,没有查询到奖金核算数据.";
		 * 
		 * }else{ List<Map<String, Object>> lowerList = new ArrayList<Map<String,
		 * Object>>();
		 * 
		 * for (Map<String, Object> map : deptBonusDataList) {
		 * 
		 * Map lowerMap = new HashMap();
		 * 
		 * for (String key : map.keySet()) {
		 * 
		 * lowerMap.put(key.toLowerCase(), map.get(key));
		 * 
		 * }
		 * 
		 * lowerList.add(lowerMap);
		 * 
		 * }
		 * 
		 * for (int i = 0; i < lowerList.size(); i++) {
		 * 
		 * Map<String, Object> map = (Map<String, Object>) lowerList.get(i);
		 * 
		 * Map<String, Object> mapGrant = new HashMap<String, Object>();
		 * 
		 * mapGrant.put("group_id", entityMap.get("group_id"));
		 * 
		 * mapGrant.put("hos_id", entityMap.get("hos_id"));
		 * 
		 * mapGrant.put("copy_code", entityMap.get("copy_code"));
		 * 
		 * mapGrant.put("acct_year", entityMap.get("acct_year"));
		 * 
		 * mapGrant.put("acct_month", entityMap.get("acct_month"));
		 * 
		 * mapGrant.put("dept_id", map.get("dept_id"));
		 * 
		 * mapGrant.put("dept_no", map.get("dept_no"));
		 * 
		 * mapGrant.put("bonus_money", map.get("sum_money"));
		 * 
		 * mapGrant.put("is_audit", 0);
		 * 
		 * mapGrant.put("activity_money", 0);
		 * 
		 * mapGrant.put("activity_percent", 0);
		 * 
		 * mapGrant.put("grant_money", map.get("sum_money"));
		 * 
		 * aphiDeptBonusGrantMapper.addDeptBonusGrant(mapGrant); } } } }
		 * 
		 * 
		 * } }
		 * 
		 * return "{\"msg\":\"" + reStr + "成功."+ grantmsg +"\",\"state\":\"true\"}";
		 * 
		 * } catch (Exception e) {
		 * 
		 * logger.error(e.getMessage(), e);
		 * 
		 * return "{\"msg\":\"操作失败.\",\"state\":\"false\"}";
		 * 
		 * }
		 */

	}

	@Override
	public String queryDeptBounsAuditGrid(Map<String, Object> entityMap) throws DataAccessException {

		List<AphiItem> queryItemDict = getGridTitleMap(entityMap);

		StringBuffer columns = new StringBuffer();

		columns.append("[");

		columns.append("{ display: \'核算年月\', name: \'acct_year\', align: \'left\',width:120,"
				+ "render: function (rowdata , rowindex , value){" + "return 	rowdata.acct_year+rowdata.acct_month;"
				+ "}" + "},");

		columns.append("{ display: \'科室编码\', name: \'dept_code\', align: \'left\',width:120,"
				+ "render: function (rowdata , rowindex , value){" + "return \"<a href=javascript:openUpdate('"
				+ entityMap.get("group_id") + "'|'" + entityMap.get("hos_id") + "'|'" + entityMap.get("copy_code")
				+ "'|" + entityMap.get("dept_id") + "')>\"+value+\"</a>\";}},");

		columns.append("{ display: \'科室名称\', name: \'dept_name\', align: \'left\',width:180},");

		for (int i = 0; i < queryItemDict.size(); i++) {

			AphiItem item = queryItemDict.get(i);

			columns.append("{display : \'" + item.getItem_name() + "\',name : \'item_code"
					+ item.getItem_code().toLowerCase()
					+ "\',align : \'right\',formatter:\'###,##0.00\',width:160,totalSummary:{type: \'sum\',"
					+ "render: function (suminf, column, cell){ "
					+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';" + "}"
					+ "}," + "render: function (rowdata, rowindex,value,col){"
					+ "return formatNumber(rowdata[col.name] ==null ? 0 : rowdata[col.name],2,1);" + "}" + "},");

		}

		// 只有选择项目下拉框的时候才能显示

		if (!"".equals(entityMap.get("item_code"))) {

			columns.append("{ display: \'是否审核\', name: \'is_audit\', align: \'left\',width:180},");

			columns.append("{ display: \'审核人\', name: \'user_name\', align: \'left\',width:180},");

			columns.append("{ display: \'审核时间\', name: \'audit_date\', align: \'left\',width:180},");

			columns.append("{ display: \'备注\', name: \'note\', align: \'left\',width:180},");
		}

		columns.append(
				"{ display: \'合计\', name: \'sum_money\', align: \'right\',formatter:\'###,##0.00\',width:160,totalSummary:{type: \'sum\',"
						+ "render: function (suminf, column, cell){ "
						+ "return \'<div> 合计:\' +formatNumber(suminf.sum ==null ? 0 :suminf.sum,2,1);+ \'</div>\';"
						+ "}" + "}," + "render: function (rowdata , rowindex , value){"
						+ "return formatNumber(rowdata.sum_money ==null ? 0 : rowdata.sum_money,2,1);" + "}" + "}");

		columns.append("]");

		return columns.toString();
	}

	@Override
	public String importHpmDeptBonusAudit(Map<String, Object> entityMap) throws DataAccessException {

		try {
			if (entityMap.get("user_id") == null) {
				entityMap.put("user_id", SessionManager.getUserId());
			}
			// 1.判断表头是否为空
			String columns = entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);
			if (jsonColumns == null || jsonColumns.size() == 0) {
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}

			// 2.判断数据是否为空
			String content = entityMap.get("content").toString();
			List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
			if (liData == null || liData.size() == 0) {
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}

			/*
			 * Map<String, Object> queryMap =new HashMap<String,Object>();
			 * queryMap.put("group_id", SessionManager.getGroupId()); queryMap.put("hos_id",
			 * SessionManager.getHosId()); queryMap.put("copy_code",
			 * SessionManager.getCopyCode());
			 */

			// 3.查询 工资项目字典 List
			entityMap.put("is_stop", 0);
			List<AphiItem> itemList = aphiItemMapper.queryItem(entityMap);
			// 用于存储查询itemList中的AphiItem对象,以键值对的形式存储,用于判断指标是否存在
			Map<String, AphiItem> itemMap = new HashMap<String, AphiItem>();
			// 将指标List存入Map 键:item_code 值:AphiItem
			for (AphiItem item : itemList) {
				itemMap.put(item.getItem_code(), item);
				itemMap.put(item.getItem_name(), item);
			}

			// 4.判断表头中指标是否存在
			StringBuffer sb = new StringBuffer();// 提示信息:用于存储表头中不存在的指标
			Map<String, String> itemColumnMap = new HashMap<String, String>();// 用于存储表头中的指标,作为遍历数据时取指标值

			for (Map<String, List<String>> item : liData) {
				for (Map.Entry<String, List<String>> entry : item.entrySet()) {
					String key = entry.getKey();
					if ("年度".equals(key) || "月份".equals(key) || "科室".equals(key) || "备注".equals(key)) {
						continue;
					}

					itemColumnMap.put(key, key);
					if (itemMap.get(key) == null) {
						sb.append("工资项目" + key + "不存在,");
					}
				}
				break;// 判断指标表头是否存在,只遍历一次
			}

			if (itemColumnMap == null || itemColumnMap.size() == 0) {
				return "{\"error\":\"表头中未存在工资项目或未填写任何工资项目\",\"state\":\"false\"}";
			}

			// 表头含有不存在指标 返回提示
			if (sb.length() > 0) {
				return "{\"error\":\"" + sb.deleteCharAt(sb.length() - 1).toString() + "\",\"state\":\"false\"}";
			}

			List<AphiDeptBonusData> deptBonusList = aphiDeptBonusDataMapper.queryDeptBonus(entityMap);
			// 5.以年、月、科室id、科室变更no、工资项目编码作为键,adbd对象作为值,判断数据是否存在
			Map<String, AphiDeptBonusData> deptBonusMap = new HashMap<String, AphiDeptBonusData>();
			for (AphiDeptBonusData adbd : deptBonusList) {

				String key = String.valueOf(adbd.getAcct_year()) + String.valueOf(adbd.getAcct_month())
						+ String.valueOf(adbd.getDept_id()) + String.valueOf(adbd.getDept_no())
						+ String.valueOf(adbd.getItem_code());

				deptBonusMap.put(key, adbd);
			}

			// 查询所有绩效科室(未停用)
			List<AphiDeptDict> deptDictList = aphiDeptDictMapper.queryPrmDeptDict(entityMap);
			// 用于存储查询deptList中的AphiDeptDict对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDeptDict> deptDictMap = new HashMap<String, AphiDeptDict>();
			// 将科室List存入Map 键:dept_name 值:AphiDeptDict
			for (AphiDeptDict deptDict : deptDictList) {
				deptDictMap.put(deptDict.getDept_name(), deptDict);
				deptDictMap.put(deptDict.getDept_code(), deptDict);
			}

			// 按科室与系统平台对应关系查询科室 List
			List<AphiDept> deptList = aphiDeptMapper.queryAphiDeptRelaByMaping(entityMap);
			// 用于存储查询deptList中的AphiDept对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDept> deptMap = new HashMap<String, AphiDept>();
			// 将科室List存入Map 键:dept_name 值:AphiDept
			for (AphiDept dept : deptList) {
				deptMap.put(dept.getDept_name(), dept);
				deptMap.put(dept.getDept_code(), dept);
			}

			// 按科室与其它平台对应关系查询科室 List
			List<AphiDeptHip> deptHipList = aphiDeptHipMapper.queryAphiDeptRelaByMapingHip(entityMap);
			// 用于存储查询deptHipList中的AphiDeptHip对象,以键值对的形式存储,用于判断科室是否存在
			Map<String, AphiDeptHip> deptHipMap = new HashMap<String, AphiDeptHip>();
			// 将科室List存入Map 键:dept_name 值:AphiDeptHip
			for (AphiDeptHip deptHip : deptHipList) {
				deptHipMap.put(deptHip.getDept_name(), deptHip);
				deptHipMap.put(deptHip.getDept_code(), deptHip);
			}

			// 5.2查询审核、发放记录
			List<AphiDeptBonusAudit> auditList = aphiDeptBonusAuditMapper.queryDeptBonusAudit(entityMap);
			Map<String, AphiDeptBonusAudit> auditMap = new HashMap<String, AphiDeptBonusAudit>();
			for (AphiDeptBonusAudit adba : auditList) {
				String auditKey = String.valueOf(adba.getGroup_id()) + String.valueOf(adba.getHos_id())
						+ String.valueOf(adba.getCopy_code()) + String.valueOf(adba.getAcct_year())
						+ String.valueOf(adba.getAcct_month());
				auditMap.put(auditKey, adba);
			}

			// 6.组装数据

			// 用于存储传的数据值,判断数据是否重复
			Map<String, String> exitMap = new HashMap<String, String>();
			// 存储添加数据List
			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();

			// 存储修改数据List
			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();
			// 用于记录重复数据
			StringBuffer err_sb = new StringBuffer();

			for (Map.Entry<String, String> entry : itemColumnMap.entrySet()) {// 遍历指标

				// 遍历导入数据
				for (Map<String, List<String>> item : liData) {

					List<String> acct_year = item.get("年度");
					List<String> acct_month = item.get("月份");
					List<String> dept_name = item.get("科室");
					List<String> item_code = item.get(entry.getKey());// 指标
					List<String> note = item.get("备注");// 备注

					if (acct_year.get(1) == null) {
						return "{\"warn\":\"年度为空！\",\"state\":\"false\",\"row_cell\":" + acct_year.get(0) + "\"\"}";
					}

					if (acct_month.get(1) == null) {
						return "{\"warn\":\"月份为空！\",\"state\":\"false\",\"row_cell\":\"" + acct_month.get(0) + "\"}";
					}

					String is_audit_grant_key = String.valueOf(entityMap.get("group_id"))
							+ String.valueOf(entityMap.get("hos_id")) + String.valueOf(entityMap.get("copy_code"))
							+ String.valueOf(acct_year.get(1)) + String.valueOf(acct_month.get(1));
					if (auditMap.get(is_audit_grant_key) != null) { // 判断当前年月是否审核、发放

						AphiDeptBonusAudit bonus = auditMap.get(is_audit_grant_key);
						if (bonus.getIs_audit() == 1) {
							return "{\"warn\":\"当前月奖金已经审核,不能导入\",\"state\":\"false\",\"row_cell\":\""
									+ acct_month.get(0) + "\"}";
						}

						if (bonus.getIs_grant() == 1) {
							return "{\"warn\":\"当前月奖金已经发放,不能导入\",\"state\":\"false\",\"row_cell\":\""
									+ acct_month.get(0) + "\"}";
						}
					}

					if (dept_name.get(1) == null) {
						return "{\"warn\":\"科室为空！\",\"state\":\"false\",\"row_cell\":\"\"}";
					} else {
						if (deptDictMap.get(dept_name.get(1)) == null && deptHipMap.get(dept_name.get(1)) == null
								&& deptDictMap.get(dept_name.get(1)) == null) {
							return "{\"warn\":\"" + dept_name.get(1) + ",科室不存在！\",\"state\":\"false\",\"row_cell\":\""
									+ dept_name.get(0) + "\"}";
						}
					}

					if (item_code.get(1) == null) {
						return "{\"warn\":\"工资项目值为空！\",\"state\":\"false\",\"row_cell\":\"" + item_code.get(0) + "\"}";
					} else {
						try {
							Double.parseDouble((item_code.get(1)));// 校验是否为数值
						} catch (NumberFormatException e) {
							return "{\"warn\":\"" + item_code.get(1)
									+ ",工资项目值输入不合法,只能输入数字！\",\"state\":\"false\",\"row_cell\":\"" + item_code.get(0)
									+ "\"}";
						}
					}

					// 以年度|月份|工资项目编码|科室名称为键值,判断导入数据是否重复
					String key = acct_year.get(1) + "|" + acct_month.get(1) + "|"
							+ itemMap.get(entry.getKey()).getItem_code() + "|" + dept_name.get(1);
					if (exitMap.get(key) != null) {
						err_sb.append(acct_year.get(1) + "年度," + acct_month.get(1) + "月份," + dept_name.get(1) + "科室,"
								+ entry.getKey() + "工资项目<br/>");
					} else {
						exitMap.put(key, key);
					}

					// 添加数据Map
					Map<String, Object> addMap = new HashMap<String, Object>();
					addMap.put("group_id", SessionManager.getGroupId());
					addMap.put("hos_id", SessionManager.getHosId());
					addMap.put("copy_code", SessionManager.getCopyCode());
					addMap.put("acct_year", acct_year.get(1));
					addMap.put("acct_month", acct_month.get(1));
					addMap.put("item_code", itemMap.get(entry.getKey()).getItem_code());
					if (note == null || note.get(1) == null) {
						addMap.put("note", "");
					} else {
						addMap.put("note", note.get(1));
					}

					// 导入状态一定是新建的
					addMap.put("is_audit", 0);

					// 系统平台科室
					if (deptMap.get(dept_name.get(1)) != null) {
						addMap.put("dept_id", deptMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no", deptMap.get(dept_name.get(1)).getDept_no());
					}

					// 其它平台科室
					if (deptHipMap.get(dept_name.get(1)) != null) {
						addMap.put("dept_id", deptHipMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no", deptHipMap.get(dept_name.get(1)).getDept_no());
					}

					// 绩效科室
					if (deptDictMap.get(dept_name.get(1)) != null) {
						addMap.put("dept_id", deptDictMap.get(dept_name.get(1)).getDept_id());
						addMap.put("dept_no", deptDictMap.get(dept_name.get(1)).getDept_no());
					}

					// addMap.put("target_name",
					// dictMap.get(target_code.get(1)).getTarget_name());
					addMap.put("bonus_money", item_code.get(1));

					// 根据年+月+科室id+科室变更no+指标编码 作为键 判断数据库中是否存在数据
					String is_exit_key = String.valueOf(addMap.get("acct_year"))
							+ String.valueOf(addMap.get("acct_month")) + String.valueOf(addMap.get("dept_id"))
							+ String.valueOf(addMap.get("dept_no")) + String.valueOf(addMap.get("item_code"));

					AphiDeptBonusData adbd = deptBonusMap.get(is_exit_key);
					if (adbd == null) {// 不存在,添加
						addList.add(addMap);
					} else {
						// updateList.add(addMap);
						return "{\"warn\":\"数据重复！\",\"state\":\"false\"}";
					}
				}
			}

			if (err_sb.length() > 0) {// 重复数据是否存在
				return "{\"warn\":\"以下数据【" + err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}

			if (addList.size() > 0) {

				aphiDeptBonusDataMapper.addBatchDeptBonusDataByListMap(addList);
			}

			if (updateList.size() > 0) {

				aphiDeptBonusDataMapper.updateBatchDeptBonusData(updateList);
			}

			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"导入失败.\"}");
		}
	}

	@Override
	public List<Map<String, Object>> queryHpmDeptBonusAuditPrint(Map<String, Object> entityMap)
			throws DataAccessException {
		if (entityMap.get("user_id") == null) {
			entityMap.put("user_id", SessionManager.getUserId());
		}
		if (entityMap.get("acct_yearm") != null && !"".equals(entityMap.get("acct_yearm"))) {
			entityMap.put("acct_year", entityMap.get("acct_yearm").toString().substring(0, 4));
			entityMap.put("acct_month", entityMap.get("acct_yearm").toString().substring(4, 6));
		}

		List<AphiItem> itemList = getGridTitleMap(entityMap);

		StringBuffer sql = new StringBuffer();
		StringBuffer sql_sum = new StringBuffer();

		for (int i = 0; i < itemList.size(); i++) {

			AphiItem item = (AphiItem) itemList.get(i);

			sql.append("sum(nvl((case when adbd.item_code = '" + item.getItem_code()
					+ "' then bonus_money end),0)) as  item_code" + item.getItem_code() + ",");

			if (item.getIs_sum().intValue() == 1) {

				sql_sum.append(
						"nvl((case when adbd.item_code = '" + item.getItem_code() + "' then bonus_money end),0)+");
			}
		}

		entityMap.put("sql", sql.toString());

		if (sql_sum.length() > 0) {

			entityMap.put("sql_sum",
					"sum(" + sql_sum.substring(0, sql_sum.length() - 1).toString() + ") as sum_money,");
		}

		List<Map<String, Object>> list = aphiDeptBonusDataMapper.queryDeptBonusForBonusMoneyPrint(entityMap);

		return list;

	}

	// 保存
	@Override
	public String savedeptbonusauditAdd(Map<String, Object> mapVo) throws DataAccessException {
		try {
			if (mapVo.get("user_id") == null) {
				mapVo.put("user_id", SessionManager.getUserId());
			}
			List<AphiDeptBonusData> list = aphiDeptBonusDataMapper.queryDeptBonusDataForGrant(mapVo);

			if (list.size() > 0) {

				if (list.get(0).getIs_audit() == 1) {
					return "{\"error\":\"审核过的数据不能修改 \"}";
				}

				aphiDeptBonusDataMapper.updateDeptBonusData(mapVo);

				return "{\"msg\":\"修改成功.\",\"state\":\"true\"}";
			}

			aphiDeptBonusDataMapper.add(mapVo);

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"添加失败 数据库异常 请联系管理员! \"}";
		}
	}

	// 删除
	@Override
	public String deleteHpmDeptItem(List<Map<String, Object>> listVo) throws DataAccessException {
		try {
			Map<String, Object> entityMap = new HashMap<String, Object>();

			entityMap.put("list", listVo);

			entityMap.put("group_id", SessionManager.getGroupId());

			entityMap.put("hos_id", SessionManager.getHosId());

			entityMap.put("copy_code", SessionManager.getCopyCode());

			entityMap.put("user_id", SessionManager.getUserId());

			List<AphiDeptBonusData> data = aphiDeptBonusDataMapper.queryListDeptBonusData(entityMap);

			for (AphiDeptBonusData ids : data) {
				if (ids.getIs_audit() == 1) {
					return "{\"msg\":\"审核过的数据不能删除.\",\"state\":\"true\"}";
				}
			}

			aphiDeptBonusDataMapper.deleteBatch(listVo);

			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			return "{\"error\":\"删除失败 数据库异常 请联系管理员! 错误编码  deleteIncomeItem\"}";
		}
	}

	@Override
	public AphiDeptBonusData queryDeptName(Map<String, Object> mapVo) throws DataAccessException {
		if (mapVo.get("user_id") == null) {
			mapVo.put("user_id", SessionManager.getUserId());
		}
		return aphiDeptBonusDataMapper.queryDeptName(mapVo);
	}
}
