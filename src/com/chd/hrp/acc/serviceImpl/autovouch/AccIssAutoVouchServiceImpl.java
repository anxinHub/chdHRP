package com.chd.hrp.acc.serviceImpl.autovouch;

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

import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.acc.dao.autovouch.AccBusiTemplateDetailMapper;
import com.chd.hrp.acc.dao.autovouch.AccIssAutoVouchMapper;
import com.chd.hrp.acc.dao.commonbuilder.AccCashItemMapper;
import com.chd.hrp.acc.dao.vouch.SuperVouchMapper;
import com.chd.hrp.acc.entity.AccCashItem;
import com.chd.hrp.acc.service.autovouch.AccIssAutoVouchService;
import com.chd.hrp.acc.service.autovouch.AccMatAutoVouchService;
import com.chd.hrp.acc.service.vouch.SuperVouchService;
import com.chd.hrp.sys.entity.BusiQuery;
import com.github.pagehelper.PageInfo;

@Service("accIssAutoVouchService")
public class AccIssAutoVouchServiceImpl implements AccIssAutoVouchService {

	private static Logger logger = Logger.getLogger(AccIssAutoVouchServiceImpl.class);

	@Resource(name = "accBusiTemplateDetailMapper")
	private final AccBusiTemplateDetailMapper accBusiTemplateDetailMapper = null;

	@Resource(name = "superVouchService")
	private final SuperVouchService superVouchService = null;

	@Resource(name = "accMatAutoVouchService")
	private final AccMatAutoVouchService accMatAutoVouchService = null;

	@Resource(name = "accIssAutoVouchMapper")
	private final AccIssAutoVouchMapper accIssAutoVouchMapper = null;

	@Resource(name = "superVouchMapper")
	private final SuperVouchMapper superVouchMapper = null;

	@Resource(name = "accCashItemMapper")
	private final AccCashItemMapper accCashItemMapper = null;

	@Override
	public String queryIssAutoVouchHead(Map<String, Object> entityMap) throws DataAccessException {
		List<BusiQuery> list = accIssAutoVouchMapper.queryAssAutoVouchHead(entityMap);

		return ChdJson.toJson(list);
	}

	@Override
	public String queryIssAutoVouch(Map<String, Object> entityMap) throws DataAccessException {
		List<BusiQuery> busiQueryList = accIssAutoVouchMapper.queryAssAutoVouchHead(entityMap);

		if (busiQueryList == null || busiQueryList.size() == 0) {
			throw new SysException("SYS_BUSI_QUERY表没有配置！");
		}

		BusiQuery busiQuery = busiQueryList.get(0);

		String view_name = busiQuery.getMain_table();

		String busi_type_code = busiQuery.getBusi_type_code();

		String detail_view_name = busiQuery.getDetail_table();

		String group_sql = busiQuery.getGroup_by();

		String where_sql = busiQuery.getWhere_sql();

		String business_no = busiQuery.getBusiness_no();

		String log_name = busiQuery.getLog_name();

		String main_key_field = busiQuery.getMain_key_field();

		// String left_sql = accMatAutoVouchService.getLeftJoinSql(view_name,
		// main_key_field, detail_view_name, where_sql);

		if (busiQuery.getHead_name().indexOf("BUSI_DATE") == -1) {
			throw new SysException("SYS_BUSI_QUERY表没有配置BUSI_DATE字段！");
		}

		String[] head_names = busiQuery.getHead_name().split(",");
		StringBuilder field = new StringBuilder();
		StringBuilder fieldTotal = new StringBuilder();

		for (int i = 0; i < head_names.length; i++) {

			if (head_names[i].split("\\|")[2].equalsIgnoreCase("VOUCH_NO")) {
				continue;
			}

			/*
			 * if(head_names[i].split("\\|")[2].equalsIgnoreCase(
			 * "V_MAT_IN_MAIN.BUSINESS_NO")){
			 * 
			 * //head_names[i].split("\\|")[2].replace(
			 * "V_MAT_IN_MAIN.BUSINESS_NO",
			 * "to_char(V_MAT_IN_MAIN.BUSINESS_NO) as BUSINESS_NO");
			 * 
			 * }
			 */
			if (head_names[i].split("\\|")[2].indexOf("SUM(") != -1) {

				fieldTotal.append(head_names[i].split("\\|")[2] + ",");

			} else if (head_names[i].split("\\|")[0].equalsIgnoreCase("BUSINESS_NO")) {

				fieldTotal.append("cast('合计' as varchar2(20)) BUSINESS_NO,");

			} else if (head_names[i].split("\\|")[0].equalsIgnoreCase("AMOUNT_MONEY")) {

				fieldTotal.append(
						"sum(" + head_names[i].split("\\|")[2].split("\\.")[0] + ".AMOUNT_MONEY) AMOUNT_MONEY,");

			} else {

				fieldTotal.append("null " + head_names[i].split("\\|")[0] + ",");

			}

			if ((i + 1) == head_names.length) {

				field.append(head_names[i].split("\\|")[2]);

			} else {

				if (head_names[i].split("\\|")[2].split("\\.").length > 1
						&& "BUSINESS_NO".equals(head_names[i].split("\\|")[2].split("\\.")[1])) {

					field.append("to_char(" + head_names[i].split("\\|")[2].split("\\.")[0]
							+ ".BUSINESS_NO) as BUSINESS_NO ,");

				} else {

					field.append(head_names[i].split("\\|")[2] + ",");
				}

			}

		}
		fieldTotal.append("null VOUCH_NO,0 VOUCH_ID");

		entityMap.put("view_name", view_name);

		entityMap.put("detail_view_name", detail_view_name);

		entityMap.put("log_table_name", log_name);

		entityMap.put("business_no", business_no);

		entityMap.put("main_key_field", main_key_field);

		entityMap.put("field", field.toString());

		entityMap.put("fieldTotal", fieldTotal.toString());

		entityMap.put("busi_type_code", busi_type_code);

		// if (left_sql != null && !left_sql.equals("")) {
		// entityMap.put("left_sql", left_sql);
		// }
		
		if("140601".equals(busi_type_code)){
			//采购发票只查询暂估凭证
			if(!"".equals(where_sql) && where_sql != null){
				where_sql = " and ";
			}
			if(where_sql == null || "".equals(where_sql)){
				where_sql = "";
			}
			where_sql+=" v_asset_bill_main.ASS_IN_NO in(select business_no from ACC_BUSI_LOG_140101 where v_asset_bill_main.group_id=group_id and v_asset_bill_main.hos_id=hos_id and v_asset_bill_main.copy_code=copy_code and template_code='002' union select business_no from ACC_BUSI_LOG_140201 where v_asset_bill_main.group_id=group_id and v_asset_bill_main.hos_id=hos_id and v_asset_bill_main.copy_code=copy_code and template_code='002')";
		}

		if (where_sql != null && !where_sql.equals("")) {
			entityMap.put("where_sql", where_sql);
		}

		if (group_sql != null && !group_sql.equals("")) {
			entityMap.put("group_sql", "group by " + group_sql);
		}

		if (business_no != null && !business_no.equals("")) {
			entityMap.put("order_sql", "order by " + business_no);
		}

		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

		List<Map<String, Object>> list = accIssAutoVouchMapper.queryAssAutoVouch(entityMap, rowBounds);

		PageInfo page = new PageInfo(list);

		return ChdJson.toJson(list, page.getTotal());
	}

	@Override
	public List<Map<String, Object>> queryIssAutoVouchPrint(Map<String, Object> entityMap) throws DataAccessException {
		List<BusiQuery> busiQueryList = accIssAutoVouchMapper.queryAssAutoVouchHead(entityMap);

		if (busiQueryList == null || busiQueryList.size() == 0) {
			throw new SysException("SYS_BUSI_QUERY表没有配置！");
		}

		BusiQuery busiQuery = busiQueryList.get(0);

		String view_name = busiQuery.getMain_table();

		String busi_type_code = busiQuery.getBusi_type_code();

		String detail_view_name = busiQuery.getDetail_table();

		String group_sql = busiQuery.getGroup_by();

		String where_sql = busiQuery.getWhere_sql();

		String business_no = busiQuery.getBusiness_no();

		String log_name = busiQuery.getLog_name();

		String main_key_field = busiQuery.getMain_key_field();

		String left_sql = accMatAutoVouchService.getLeftJoinSql(view_name, main_key_field, detail_view_name, where_sql);

		if (busiQuery.getHead_name().indexOf("BUSI_DATE") == -1) {
			throw new SysException("SYS_BUSI_QUERY表没有配置BUSI_DATE字段！");
		}

		String[] head_names = busiQuery.getHead_name().split(",");
		StringBuilder field = new StringBuilder();
		StringBuilder fieldTotal = new StringBuilder();

		for (int i = 0; i < head_names.length; i++) {

			if (head_names[i].split("\\|")[2].equalsIgnoreCase("VOUCH_NO")) {
				continue;
			}

			if (head_names[i].split("\\|")[2].indexOf("SUM(") != -1) {

				fieldTotal.append(head_names[i].split("\\|")[2] + ",");

			} else if (head_names[i].split("\\|")[0].equalsIgnoreCase("BUSINESS_NO")) {

				fieldTotal.append("cast('合计' as varchar2(20)) BUSINESS_NO,");

			} else {

				fieldTotal.append("null " + head_names[i].split("\\|")[0] + ",");

			}

			if ((i + 1) == head_names.length) {

				field.append(head_names[i].split("\\|")[2]);

			} else {

				if (head_names[i].split("\\|")[2].split("\\.").length > 1
						&& "BUSINESS_NO".equals(head_names[i].split("\\|")[2].split("\\.")[1])) {

					field.append("to_char(" + head_names[i].split("\\|")[2].split("\\.")[0]
							+ ".BUSINESS_NO) as BUSINESS_NO ,");

				} else {

					field.append(head_names[i].split("\\|")[2] + ",");
				}

			}

		}
		fieldTotal.append("null VOUCH_NO,0 VOUCH_ID");

		entityMap.put("view_name", view_name);

		entityMap.put("detail_view_name", detail_view_name);

		entityMap.put("log_table_name", log_name);

		entityMap.put("business_no", business_no);

		entityMap.put("main_key_field", main_key_field);

		entityMap.put("field", field.toString());

		entityMap.put("fieldTotal", fieldTotal.toString());

		entityMap.put("busi_type_code", busi_type_code);

		if (left_sql != null && !left_sql.equals("")) {
			entityMap.put("left_sql", left_sql);
		}

		if (where_sql != null && !where_sql.equals("")) {
			entityMap.put("where_sql", where_sql);
		}

		if (group_sql != null && !group_sql.equals("")) {
			entityMap.put("group_sql", "group by " + group_sql);
		}

		if (business_no != null && !business_no.equals("")) {
			entityMap.put("order_sql", "order by " + business_no);
		}

		List<Map<String, Object>> list = accIssAutoVouchMapper.queryAssAutoVouch(entityMap);

		return list;
	}

	@Override
	public String queryVouchJsonByBusi(Map<String, Object> map) throws DataAccessException {
		try {

			int initType = Integer.parseInt(map.get("init_type").toString());
			String groupId = map.get("group_id").toString();
			String hosId = map.get("hos_id").toString();
			String copyCode = map.get("copy_code").toString();
			String modCode = map.get("mod_code").toString();
			String busiDateB = map.get("busi_date_b").toString();
			String busiDateE = map.get("busi_date_e").toString();
			String vouch_date = map.get("vouch_date").toString();
			String accYear = vouch_date.substring(0, 4);
			if (Integer.parseInt(accYear) < 2019) {
				return "{\"error\":\"只能生成2019年以后的凭证！\"}";
			}
			map.put("acc_year", accYear);
			String accMonth = vouch_date.substring(5, 7);
			String busi_log_table = map.get("busi_log_table").toString();
			String busiTypeCode = map.get("busi_type_code").toString();

			List<Map<String, Object>> tempList = accBusiTemplateDetailMapper.queryAccBusiTemplateByCode(map);
			if (tempList == null || tempList.size() == 0) {
				return "{\"error\":\"没有配置凭证模板！\"}";
			}

			String vouchTypeCode = tempList.get(0).get("VOUCH_TYPE_CODE").toString();
			// 查询凭证视图字段
			List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
			viewList = accIssAutoVouchMapper.queryAutoVouchView(map);
			if (viewList == null || viewList.size() == 0) {
				return "{\"error\":\"没有配置内置数据：SYS_BUSI_VIEW！\"}";
			}

			String summary = "";
			String vouchId = UUIDLong.absStringUUID();
			map.put("vouch_id", vouchId);
			Date createDate = DateUtil.getCurrenDate();
			map.put("create_date", createDate);// 这个时间很重要是验证是否生成凭证的重要的条件
			int reCount = 0;
			if (initType == 1) {
				// 按单据生成

				// 保存自动凭证日志临时表
				if (map.get("busi_no") != null && !map.get("busi_no").equals("")) {
					String[] busiNoArray = map.get("busi_no").toString().split(",");

					reCount = accIssAutoVouchMapper.saveAutoVouchLogTempByNo(map, busiNoArray);
				}
				if (reCount == 0) {
					return "{\"error\":\"请先查询数据！\"}";
				}

			} else {
				// 按日期生成、按汇总生成
				// 拼日期摘要
				if (map.get("busi_date_b").toString().equalsIgnoreCase(map.get("busi_date_e").toString())) {
					if (null != map.get("busi_date_b") && !"".equals(map.get("busi_date_b").toString())) {
						summary = map.get("busi_date_b").toString() + "日";
					}
				} else {
					summary = Integer.parseInt(map.get("busi_date_b").toString().substring(8, 10)) + "-"
							+ Integer.parseInt(map.get("busi_date_e").toString().substring(8, 10)) + "日";
				}
				// 折旧单独处理
				if (busiTypeCode.equals("140301")) {
					List<BusiQuery> busiQueryList = accIssAutoVouchMapper.queryAssAutoVouchHead(map);
					if (busiQueryList == null || busiQueryList.size() == 0) {
						return "{\"error\":\"没有配置内置数据：SYS_BUSI_QUERY！\"}";
					}

					reCount = accIssAutoVouchMapper.saveAutoVouchDepreLogTemp(map);
					if (reCount == 0) {
						return "{\"error\":\"请先查询数据！\"}";
					}

				} else {
					List<BusiQuery> busiQueryList = accIssAutoVouchMapper.queryAssAutoVouchHead(map);
					if (busiQueryList == null || busiQueryList.size() == 0) {
						return "{\"error\":\"没有配置内置数据：SYS_BUSI_QUERY！\"}";
					}
					BusiQuery busiQuery = busiQueryList.get(0);
					map.put("query_view_name", busiQuery.getMain_table());
					map.put("huizong_sql",
							map.get("huizong_sql").toString().replace("{m_table}", busiQuery.getMain_table()));
					map.put("query_main_key_fielde", busiQuery.getMain_key_field());
					map.put("query_where_sql", busiQuery.getWhere_sql());
					map.put("left_sql", accMatAutoVouchService.getLeftJoinSql(busiQuery.getMain_table(),
							busiQuery.getMain_key_field(), busiQuery.getDetail_table(), busiQuery.getWhere_sql()));
					// 保存自动凭证日志临时表

					reCount = accIssAutoVouchMapper.saveAutoVouchLogTemp(map);
					if (reCount == 0) {
						return "{\"error\":\"请先查询数据！\"}";
					}
				}

			}

			// 判断是否生成凭证
			reCount = superVouchMapper.queryAutoVouchIsCreate(map);
			if (reCount > 0) {
				return "{\"error\":\"有单据已经生成凭证！\"}";
			}

			/* 添加自动凭证主表 */
			map.put("vouch_att_num", 0);// 凭证附件
			map.put("vouch_type_code", vouchTypeCode);// 凭证类型
			Date vouchDate = DateUtil.stringToDate(vouch_date, "yyyy-MM-dd");
			map.put("vouch_date", vouchDate);
			superVouchMapper.saveAutoVouch(map);

			List<Map<String, Object>> detailList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> cashList = new ArrayList<Map<String, Object>>();
			List<Map<String, Object>> metaList = null;
			int isDetailSummary = Integer.parseInt(tempList.get(0).get("IS_DETAIL_SUMMARY").toString());

			int vouchRow = 0;
			int direction = 0;
			Map<String, String> checkEl = null;
			Map<String, Object> cashMap = null;
			AccCashItem accCashItem = null;
			for (Map<String, Object> tempMap : tempList) {

				checkEl = new HashMap<String, String>();// 检索辅助核算规则
				checkEl.put("busi_type_code", map.get("busi_type_code").toString());
				String metaCode = tempMap.get("META_CODE").toString();
				direction = Integer.parseInt(tempMap.get("DIRECTION").toString());
				map.put("direction", direction);
				map.put("vouch_row", tempMap.get("VOUCH_ROW"));

				// 0不按明细摘要，1按明细摘要
				if (isDetailSummary == 0) {
					map.put("summary", tempMap.get("M_SUMMARY"));
				} else {
					map.put("summary", tempMap.get("D_SUMMARY") + summary);
				}

				String main_table = "";
				String detail_table = "";
				String item_table = "";
				String source_table = "";
				String amount_money = "";
				String sub_type_id = "";
				String while_sql = "";
				String busi_date = "";
				String main_key_filed = "";

				String detail_key_filed = "";
				String item_key_filed = "";
				String source_key_filed = "";
				StringBuilder inner_sql1 = new StringBuilder();
				StringBuilder inner_sql2 = new StringBuilder();
				StringBuilder inner_sql3 = new StringBuilder();
				StringBuilder inner_sql4 = new StringBuilder();
				StringBuilder vouch_sql = new StringBuilder();
				StringBuilder where_sql = new StringBuilder();
				StringBuilder group_sql = new StringBuilder();
				group_sql.append(" group by b.subj_code");

				for (Map<String, Object> view : viewList) {
					// 取当前元素的对应关系
					if (!view.get("META_CODE").equals(metaCode)) {
						continue;
					}

					String FIELD_ALIAS = view.get("FIELD_ALIAS").toString();
					// 存业务字段与辅助核算字段的关系，CHECK4_ID:V_MAT_IN_MAIN.STORE_ID
					if (FIELD_ALIAS.indexOf("CHECK") != -1 && checkEl.get(FIELD_ALIAS) == null) {
						checkEl.put(FIELD_ALIAS, view.get("VIEW_FIELD").toString());
					} else if (isDetailSummary == 1 && FIELD_ALIAS.equalsIgnoreCase("CON_NO")
							&& checkEl.get("CON_NO") == null) {
						// 合同编号，付款单业务辅助核算合同编号字段写入库单号
						checkEl.put(FIELD_ALIAS.toUpperCase(), view.get("VIEW_FIELD").toString());
					}

					// 取主表、明细表、第三层表、第四层
					if (main_table.equals("") && detail_table.equals("") && item_table.equals("")) {
						main_table = view.get("MAIN_TABLE") == null ? "" : view.get("MAIN_TABLE").toString();
						detail_table = view.get("DETAIL_TABLE") == null ? "" : view.get("DETAIL_TABLE").toString();
						item_table = view.get("ITEM_TABLE") == null ? "" : view.get("ITEM_TABLE").toString();
						source_table = view.get("SOURCE_DETAIL_TABLE") == null ? ""
								: view.get("SOURCE_DETAIL_TABLE").toString();
					}

					// 取科目与对应关系的关联字段
					if (view.get("FIELD_CODE").toString().equalsIgnoreCase("SUB_TYPE_ID")) {
						sub_type_id = view.get("VIEW_FIELD") == null ? "" : view.get("VIEW_FIELD").toString();
						while_sql = view.get("WHILE_SQL") == null ? "" : view.get("WHILE_SQL").toString();
					}

					// 取汇总金额
					if (FIELD_ALIAS.equalsIgnoreCase("AMT_MONEY")) {
						amount_money = view.get("VIEW_FIELD") == null ? "" : view.get("VIEW_FIELD").toString();
					}

					// 取单据日期
					if (FIELD_ALIAS.equalsIgnoreCase("OCCUR_DATE")) {
						busi_date = view.get("VIEW_FIELD") == null ? "" : view.get("VIEW_FIELD").toString();
					}

					// 取主从表关联字段
					if (main_key_filed.equals("") && view.get("MAIN_KEY_FIELD") != null
							&& !view.get("MAIN_KEY_FIELD").equals("")) {
						main_key_filed = view.get("MAIN_KEY_FIELD").toString();

					}

					// 取从表关联字段
					if (detail_key_filed.equals("") && view.get("DETAIL_KEY_FIELD") != null
							&& !view.get("DETAIL_KEY_FIELD").equals("")) {
						detail_key_filed = view.get("DETAIL_KEY_FIELD").toString();
					}
					// 取从表第三层表关联字段
					if (item_key_filed.equals("") && view.get("ITEM_KEY_FIELD") != null
							&& !view.get("ITEM_KEY_FIELD").equals("")) {
						item_key_filed = view.get("ITEM_KEY_FIELD").toString();
					}

					// 取从表第四层表关联字段
					if (source_key_filed.equals("") && view.get("SOURCE_KEY_FIELD") != null
							&& !view.get("SOURCE_KEY_FIELD").equals("")) {
						source_key_filed = view.get("SOURCE_KEY_FIELD").toString();
					}
				}

				if (main_table.equals("") || amount_money.equals("") || sub_type_id.equals("")) {
					throw new SysException(
							metaCode + "缺少内置数据：字段MAIN_TABLE，字段FIELD_ALIAS【amount_money】，字段FIELD_CODE【SUB_TYPE_ID】！");
				}

				// 关联主从表
				if (!main_key_filed.equals("") && !detail_table.equals("")) {

					inner_sql1.append(" inner join " + detail_table + " on ");
					inner_sql1.append(main_table + ".group_id=" + detail_table + ".group_id and " + main_table
							+ ".hos_id=" + detail_table + ".hos_id and " + main_table + ".copy_code=" + detail_table
							+ ".copy_code ");
					inner_sql1.append(
							" and " + main_table + "." + main_key_filed + "=" + detail_table + "." + main_key_filed);

				}

				// 关联从表第三层表
				if (!detail_key_filed.equals("") && !detail_table.equals("") && !item_table.equals("")) {
					inner_sql2.append(" inner join " + item_table + " on ");
					inner_sql2.append(item_table + ".group_id=" + detail_table + ".group_id and " + item_table
							+ ".hos_id=" + detail_table + ".hos_id and " + item_table + ".copy_code=" + detail_table
							+ ".copy_code ");
					inner_sql2.append(
							" and " + item_table + "." + item_key_filed + "=" + detail_table + "." + detail_key_filed);
				}

				// 关联从表第四层表
				if (!detail_key_filed.equals("") && !detail_table.equals("") && !source_table.equals("")) {
					// 当第三层为空时，匹配第二层关联关系
					if (!item_table.equals("")) {
						inner_sql3.append(" inner join " + source_table + " on ");
						inner_sql3.append(source_table + ".group_id=" + item_table + ".group_id and " + source_table
								+ ".hos_id=" + item_table + ".hos_id and " + source_table + ".copy_code=" + item_table
								+ ".copy_code ");
						inner_sql3.append(" and " + source_table + "." + source_key_filed + "=" + item_table + "."
								+ item_key_filed);

					} else {
						inner_sql3.append(" inner join " + source_table + " on ");
						inner_sql3.append(source_table + ".group_id=" + detail_table + ".group_id and " + source_table
								+ ".hos_id=" + detail_table + ".hos_id and " + source_table + ".copy_code="
								+ detail_table + ".copy_code ");
						inner_sql3.append(" and " + source_table + "." + source_key_filed + "=" + detail_table + "."
								+ detail_key_filed);
					}
				}

				// 开始组装查询自动凭证SQL
				if (inner_sql1 != null && !inner_sql1.toString().equals("")) {
					vouch_sql.append(inner_sql1.toString());
				}

				if (inner_sql2 != null && !inner_sql2.toString().equals("")) {
					vouch_sql.append(inner_sql2.toString());
				}

				if (inner_sql3 != null && !inner_sql3.toString().equals("")) {
					vouch_sql.append(inner_sql3.toString());
				}

				// 关联科目关系
				vouch_sql.append(" inner join acc_busi_map b on b.group_id=" + main_table + ".group_id and b.hos_id="
						+ main_table + ".hos_id and b.copy_code=" + main_table + ".copy_code ");
				
				vouch_sql.append(" and b.acc_year='" + accYear + "' and b.mod_code='" + modCode + "' and b.meta_code='"
						+ metaCode + "' and (b.sub_type_id	=" + sub_type_id + " or b.sub_type_id is null) ");
				
				if(busiTypeCode.equals("140101") || busiTypeCode.equals("140201")){
					vouch_sql.append("  and  (b.store_id="+main_table+".store_id or b.store_id is  null) "); 	
				}
				// where条件
				where_sql.append(" where b.group_id=" + groupId + " and b.hos_id=" + hosId + " and b.copy_code='"
						+ copyCode + "' ");

				if (map.get("store_id") != null && !map.get("store_id").equals("")) {
					where_sql.append(" and " + main_table + ".store_id = " + map.get("store_id") + "");
				}

				if (map.get("ven_id") != null && !map.get("ven_id").equals("")) {
					where_sql.append(" and " + main_table + ".sup_id = " + map.get("ven_id") + "");
				}

				if (map.get("dept_id") != null && !map.get("dept_id").equals("")) {
					where_sql.append(" and " + main_table + ".dept_id = " + map.get("dept_id") + "");
				}

				if (initType == 1) {
					// 按单据生成
					if (main_key_filed == null || main_key_filed.equals("")) {
						throw new SysException("没有配置单据ID字段：main_key_filed！");
					}

					where_sql.append(" and " + main_table + "." + main_key_filed + " in('"
							+ map.get("busi_no").toString().toString().replace(",", "','") + "') ");
				} else {

					// 按日期生成、按汇总生成
					if (busi_date == null || busi_date.equals("")) {
						throw new SysException("没有配置单据日期字段：busi_date！");
					}
					if (busiTypeCode.equals("140301")) {
						where_sql.append(" and year_month = '" + map.get("year_month").toString() + "'");
						where_sql.append(" and not exists(select 1 from " + busi_log_table + " where " + busi_log_table
								+ ".group_id=" + main_table + ".group_id ");
						where_sql.append(" and " + busi_log_table + ".hos_id=" + main_table + ".hos_id and "
								+ busi_log_table + ".copy_code=" + main_table + ".copy_code and " + busi_log_table
								+ ".business_no=" + main_table + ".year_month)");
					} else {
						where_sql.append(" and to_char(" + busi_date + ",'yyyy-MM-dd') between '" + busiDateB
								+ "' and '" + busiDateE + "' ");
						// if(map.get("huizong_sql")!=null &&
						// !map.get("huizong_sql").equals("")){
						// where_sql.append(map.get("huizong_sql"));
						// }
						where_sql.append(" and not exists(select 1 from " + busi_log_table + " where " + busi_log_table
								+ ".group_id=" + main_table + ".group_id ");
						where_sql.append(" and " + busi_log_table + ".hos_id=" + main_table + ".hos_id and "
								+ busi_log_table + ".copy_code=" + main_table + ".copy_code and " + busi_log_table
								+ ".business_no=" + main_table + "." + main_key_filed + ")");
					}
					
					if("140601".equals(busiTypeCode)){
						//采购发票只查询暂估凭证
						where_sql.append(" and v_asset_bill_main.ASS_IN_NO in(select business_no from ACC_BUSI_LOG_140101 where v_asset_bill_main.group_id=group_id and v_asset_bill_main.hos_id=hos_id and v_asset_bill_main.copy_code=copy_code and template_code='002' union select business_no from ACC_BUSI_LOG_140201 where v_asset_bill_main.group_id=group_id and v_asset_bill_main.hos_id=hos_id and v_asset_bill_main.copy_code=copy_code and template_code='002')");
					}

					
					if("140101".equals(busiTypeCode) || "140201".equals(busiTypeCode)){
						if(map.get("bill_state")!=null && !map.get("bill_state").toString().equals("")){
							where_sql.append(" and "+main_table+".bill_state="+map.get("bill_state").toString());
						}
					}

				}

				// 过滤业务类型
				if (!while_sql.equals("")) {
					where_sql.append(" and " + while_sql);
				}
				String selSql = "";
				if (busiTypeCode.equals("140301")) {
					selSql = "select b.subj_code,ASS_NATURS,sum(" + amount_money + ") amount_money from " + main_table
							+ " ";
				} else {
					selSql = "select b.subj_code,sum(" + amount_money + ") amount_money from " + main_table + " ";
				}
				map.put("meta_code", metaCode);
				map.put("vouch_sql", selSql + vouch_sql.toString());
				map.put("where_sql", where_sql.toString());
				map.put("group_sql", group_sql.toString());
				metaList = accIssAutoVouchMapper.queryVouchJsonByBusi(map);

				if (metaList != null && metaList.size() > 0) {

					// 分录
					for (Map<String, Object> detailMap : metaList) {

						if (busiTypeCode.equals("140301")) {

							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);// 凭证ID
							detailMap.put("vouch_row", vouchRow);// 分录行号
							detailMap.put("direction", direction);// 方向

							if (detailMap.get("is_check") != null && detailMap.get("is_check").toString().equals("1")) {
								// 辅助核算
								detailMap.put("vouch_date", vouch_date);// 凭证日期
								detailMap.put("amount_money", amount_money);// 金额字段
								detailMap.put("detail_table", detail_table);// 业务表名
								detailMap.put("from_table", main_table);// 子查询表名
								detailMap.put("busi_type_code", busiTypeCode);// 业务类型
								superVouchService.saveAccAutoCheck(detailMap, checkEl, vouch_sql.toString(),
										where_sql.toString(), group_sql.toString());

							}
							if (detailMap.get("is_cash") != null && detailMap.get("is_cash").toString().equals("1")) {
								cashMap = new HashMap<String, Object>();
								cashMap.put("group_id", detailMap.get("group_id"));
								cashMap.put("hos_id", detailMap.get("hos_id"));
								cashMap.put("copy_code", detailMap.get("copy_code"));
								if (accCashItem == null) {
									// 只查一次，只有付款业务才有现金银行科目，才挂现金流量，现金流量项目都是一样的
									cashMap.put("cash_item_code", "201");
									accCashItem = accCashItemMapper.queryAccCashItemCodeByAutoCash(cashMap);
								}

								cashMap.put("cash_id", UUIDLong.absStringUUID());
								cashMap.put("cash_item_id", accCashItem.getCash_item_id());
								cashMap.put("summary", detailMap.get("summary"));
								cashMap.put("cash_money", Float.parseFloat(detailMap.get("credit").toString()) == 0
										? detailMap.get("debit") : detailMap.get("credit"));
								cashMap.put("vouch_id", detailMap.get("vouch_id"));// 凭证ID
								cashMap.put("vouch_row", vouchRow);// 分录行号
								// cashMap.put("pay_code",detailMap.get("pay_code")
								// );
								cashList.add(cashMap);
							}
							detailList.add(detailMap);

						} else {

							vouchRow++;
							detailMap.put("group_id", groupId);
							detailMap.put("hos_id", hosId);
							detailMap.put("copy_code", copyCode);
							detailMap.put("vouch_id", vouchId);// 凭证ID
							detailMap.put("vouch_row", vouchRow);// 分录行号
							detailMap.put("direction", direction);// 方向

							if (detailMap.get("is_check") != null && detailMap.get("is_check").toString().equals("1")) {
								// 辅助核算
								detailMap.put("vouch_date", vouch_date);// 凭证日期
								detailMap.put("amount_money", amount_money);// 金额字段
								detailMap.put("detail_table", detail_table);// 业务表名
								detailMap.put("from_table", main_table);// 子查询表名
								detailMap.put("busi_type_code", busiTypeCode);// 业务类型
								superVouchService.saveAccAutoCheck(detailMap, checkEl, vouch_sql.toString(),
										where_sql.toString(), group_sql.toString());

							}
							if (detailMap.get("is_cash") != null && detailMap.get("is_cash").toString().equals("1")) {
								cashMap = new HashMap<String, Object>();
								cashMap.put("group_id", detailMap.get("group_id"));
								cashMap.put("hos_id", detailMap.get("hos_id"));
								cashMap.put("copy_code", detailMap.get("copy_code"));
								if (accCashItem == null) {
									// 只查一次，只有付款业务才有现金银行科目，才挂现金流量，现金流量项目都是一样的
									cashMap.put("cash_item_code", "201");
									accCashItem = accCashItemMapper.queryAccCashItemCodeByAutoCash(cashMap);
								}

								cashMap.put("cash_id", UUIDLong.absStringUUID());
								cashMap.put("cash_item_id", accCashItem.getCash_item_id());
								cashMap.put("summary", detailMap.get("summary"));
								cashMap.put("cash_money", Float.parseFloat(detailMap.get("credit").toString()) == 0
										? detailMap.get("debit") : detailMap.get("credit"));
								cashMap.put("vouch_id", detailMap.get("vouch_id"));// 凭证ID
								cashMap.put("vouch_row", vouchRow);// 分录行号
								// cashMap.put("pay_code",detailMap.get("pay_code")
								// );
								cashList.add(cashMap);
							}
							detailList.add(detailMap);

						}
					}
				}

			}

			if (detailList != null && detailList.size() > 0) {
				// 保存自动凭证明细表
				superVouchMapper.saveAutoVouchDetail(detailList);
				
				//根据自动凭证的模板的处理凭证分录的摘要,如：模板明细表摘要，[供应商]摘要=》[某某公司]摘要
				superVouchMapper.updateAutoVouchDetailSummary(map);
				if(cashList!=null && cashList.size()>0){
					superVouchMapper.saveAccAutoCash(cashList);
				}
			} else {
				throw new SysException("没有明细表数据或者没有对应关系！");
			}

			return "{\"auto_id\": \"" + vouchId + "\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

}
