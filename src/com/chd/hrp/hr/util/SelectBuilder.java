package com.chd.hrp.hr.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.ibatis.jdbc.SQL;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.entity.sc.HrTableColumnFormConfig;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumn;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnCondition;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnGroup;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnSort;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnTable;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnTableFunc;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnTableJoin;
import com.chd.hrp.hr.entity.sc.HrTableStruc;

/**
 * SQL构建
 * 
 * @author zhaonan
 *
 */
public class SelectBuilder {
	// 设计器
	private HrTableDesign design;
	private List<HrTableDesignQueryColumnTable> dataTableList;
	private List<HrTableDesignQueryColumnCondition> conditionList;
	private List<HrTableDesignQueryColumnGroup> groupList;
	private List<HrTableDesignQueryColumnSort> sortList;

	private SQL sql;

	private Map<String, String> aliasRegister;

	// 表结构
	private List<HrTableStruc> tableStrucList;
	private Map<String, HrTableStruc> tableStrucRegister;

	// 代码表
	private List<HrFiledTabStruc> filedTabStrucList;
	private Map<String, HrFiledTabStruc> filedTableRegister;

	private boolean autoGenOnCond;// 是否自动生成表连接ON条件
	
	private List<String> wherePrefixList = Arrays.asList("AND ", "OR ", "AND\n", "OR\n", "AND\r", "OR\r", "AND\t", "OR\t");

	public SelectBuilder(HrTableDesign design, List<HrTableStruc> tableStrucList,
			List<HrFiledTabStruc> filedTabStrucList) {
		this(design, tableStrucList, filedTabStrucList, false);
	}

	public SelectBuilder(HrTableDesign design, List<HrTableStruc> tableStrucList,
			List<HrFiledTabStruc> filedTabStrucList, boolean autoGenOnCond) {

		this.design = design;
		this.tableStrucList = tableStrucList;
		this.filedTabStrucList = filedTabStrucList;
		this.autoGenOnCond = autoGenOnCond;

		this.aliasRegister = new HashMap<String, String>();
		this.sql = new SQL();

		HrTableDesignQueryColumn designQueryCol = JSONObject.parseObject(this.design.getDesign_query_col(),
				HrTableDesignQueryColumn.class);
		this.dataTableList = designQueryCol.getTableData();
		this.conditionList = designQueryCol.getConditionData();
		this.groupList = designQueryCol.getGroupData();
		this.sortList = designQueryCol.getSortData();

		this.tableStrucRegister = new HashMap<>();
		for (HrTableStruc tabStruc : this.tableStrucList) {
			this.tableStrucRegister.put(tabStruc.getTab_code(), tabStruc);
		}

		this.filedTableRegister = new HashMap<>();
		for (HrFiledTabStruc filedTabStruc : this.filedTabStrucList) {
			this.filedTableRegister.put(filedTabStruc.getField_tab_code(), filedTabStruc);
		}
	}

	public SelectBuilder select() {

		Map<String, Map<String, String>> filedTable = filedTableHandle();

		if (dataTableList == null || dataTableList.size() == 0) {
			throw new SysException("查询设计器数据表未维护");
		}

		if (dataTableList.size() > 0) {
			for (int i = 0; i < dataTableList.size(); i++) {
				String tableName = dataTableList.get(i).getTab_code();// 表名
				String alias = RandomUtil.randomString(6);// 随机别名
				String tableColumn = dataTableList.get(i).getCol_code();// 查询列

				aliasRegister.put(tableName, alias);// 注册表别名

				// 函数注册
				Map<String, HrTableDesignQueryColumnTableFunc> funcRegister = new HashMap<String, HrTableDesignQueryColumnTableFunc>();
				List<HrTableDesignQueryColumnTableFunc> columnFuncList = dataTableList.get(i).getFunc();
				if (columnFuncList != null && columnFuncList.size() > 0) {
					for (HrTableDesignQueryColumnTableFunc func : columnFuncList) {
						funcRegister.put(func.getCol_code(), func);
					}
				}

				// 第一个为主表
				if (i == 0) {
					sql.FROM(tableName + " " + alias);
				} else {
					String joinMode = dataTableList.get(i).getJoin_mode();// 连接方式
					String joinTable = tableName + " " + alias;

					// 拼接ON条件
					StringBuilder joinCondition = new StringBuilder();
					List<HrTableDesignQueryColumnTableJoin> joinConditionList = dataTableList.get(i)
							.getJoin_condition_grid();
					if (autoGenOnCond) {
						String mainAlias = aliasRegister.get(dataTableList.get(0).getTab_code());
						joinCondition.append(" ON ");
						joinCondition.append(mainAlias).append(".").append("GROUP_ID = ").append(alias).append(".")
								.append("GROUP_ID").append(" AND ");
						joinCondition.append(mainAlias).append(".").append("HOS_ID = ").append(alias).append(".")
								.append("HOS_ID").append(" AND ");
						joinCondition.append(mainAlias).append(".").append("EMP_ID = ").append(alias).append(".")
								.append("EMP_ID");
					} else {
						if (joinConditionList == null || joinConditionList.size() == 0) {
							throw new SysException("查询设计器数据表表连接条件未维护");
						}
						for (HrTableDesignQueryColumnTableJoin join : joinConditionList) {
							joinCondition.append(" ").append(join.getConn_mode()).append(" ");
							String leftField = join.getLeft_field().toUpperCase();
							String rightField = join.getRight_field().toUpperCase();
							for (String table : aliasRegister.keySet()) {
								leftField = leftField.replaceAll(table, aliasRegister.get(table));
								rightField = rightField.replaceAll(table, aliasRegister.get(table));
							}
							joinCondition.append(leftField).append(" ").append(join.getCondition()).append(" ")
									.append(rightField);
						}
					}

					switch (joinMode) {
					case "inner join":
						sql.INNER_JOIN(joinTable + joinCondition.toString());
						break;
					case "left join":
						sql.LEFT_OUTER_JOIN(joinTable + joinCondition.toString());
						break;
					case "right join":
						sql.RIGHT_OUTER_JOIN(joinTable + joinCondition.toString());
						break;
					default:
						sql.JOIN(joinTable + joinCondition.toString());
						break;
					}
				}

				if (tableColumn != null && StringUtils.isNotEmpty(tableColumn)) {
					Set<String> columnList = new HashSet<String>(Arrays.asList(tableColumn.split(";")));
					for (String column : columnList) {
						String selectColumn = alias + "." + column;
						// 处理列函数
						if (funcRegister.containsKey(column)) {
							HrTableDesignQueryColumnTableFunc func = funcRegister.get(column);
							selectColumn = columnFuncHandle(func, selectColumn);
							selectColumn += " AS " + column;
						}
						sql.SELECT(selectColumn);

						// 处理代码表
						if (filedTable.containsKey(tableName + column)) {
							Map<String, String> filedTab = filedTable.get(tableName + column);
							StringBuilder filedTabSql = new StringBuilder();
							String filedTabAlias = RandomUtil.randomString(6);
							String col = alias + "." + column;
							if (filedTab.containsKey("noCol")) {
								col += "||'@'||" + alias + "." + filedTab.get("noCol");
							}
							filedTabSql.append("(").append(filedTab.get("sql")).append(") ").append(filedTabAlias)
									.append(" ON ").append(col).append(" = ").append(filedTabAlias + ".id");
							sql.SELECT(filedTabAlias + ".text AS " + column + "_NAME");
							sql.LEFT_OUTER_JOIN(filedTabSql.toString());
						}
					}
				} else {
					// sql.SELECT(alias + ".*");
				}

			}
		}

		return this;
	}

	public SelectBuilder where() {
		// 条件
		if (conditionList != null && conditionList.size() > 0) {
			StringBuilder where = new StringBuilder();
			for (HrTableDesignQueryColumnCondition obj : conditionList) {
				if (obj.getTab_code() != null) {
					if (obj.getConn_mode() != null && "OR".equalsIgnoreCase(obj.getConn_mode())) {
						where.append(" OR ");
					}
					if (obj.getConn_mode() != null && "AND".equalsIgnoreCase(obj.getConn_mode())) {
						where.append(" AND ");
					}
					if (obj.getLeft_bracket() != null && StringUtils.isNotEmpty(obj.getLeft_bracket())) {
						where.append("(");
					}
					where.append(aliasRegister.get(obj.getTab_code())).append(".").append(obj.getCol_code())
						.append(" ").append(obj.getCondition()).append(" ");
					String value = obj.getItem_value();
					String valueMode = obj.getValue_mode_code();
					switch (valueMode) {
					case "01":
						if(value == null || StringUtils.isEmpty(value)){
							value = "[" + obj.getCol_code() + "]";
						}
						break;
					case "02":
						value = "[" + obj.getCol_code() + "]";
						break;
					case "03":
						value = "[" + obj.getCol_code() + "]";
						break;
					}
					
					where.append(value);
					if (obj.getRight_bracket() != null && StringUtils.isNotEmpty(obj.getRight_bracket())) {
						where.append(")");
					}
				}
			}
			
			if (where.length() > 0) {
				where = new StringBuilder(where.toString().trim());
				String trimmedUppercaseSql = where.toString().toUpperCase();
				for (String toRemove : wherePrefixList) {
					if (trimmedUppercaseSql.startsWith(toRemove)) {
						where.delete(0, toRemove.trim().length());
						break;
					}
				}
				sql.WHERE(where.toString());
			}
		}

		return this;
	}

	public SelectBuilder where(Map<String, Object> reqParam) {
		// 条件
		if (conditionList != null && conditionList.size() > 0) {
			StringBuilder where = new StringBuilder();
			for (HrTableDesignQueryColumnCondition obj : conditionList) {
				HrTableStruc struc = tableStrucRegister.get(obj.getTab_code());
				List<HrTableColumnFormConfig> configList = struc.getTableColumnConfig().getSearchSetData();

				if (obj.getTab_code() != null && (reqParam.containsKey(obj.getCol_code().toLowerCase())
						|| reqParam.containsKey(obj.getCol_code().toLowerCase() + "_beg")
						|| reqParam.containsKey(obj.getCol_code().toLowerCase() + "_end"))) {
					if (obj.getConn_mode() != null && "OR".equalsIgnoreCase(obj.getConn_mode())) {
						where.append(" OR ");
					}
					if (obj.getConn_mode() != null && "AND".equalsIgnoreCase(obj.getConn_mode())) {
						where.append(" AND ");
					}
					if (obj.getLeft_bracket() != null && StringUtils.isNotEmpty(obj.getLeft_bracket())) {
						where.append("(");
					}

					//处理区间
					for (HrTableColumnFormConfig config : configList) {
						if (config.getCol_code().equals(obj.getCol_code())) {
							if (config.getIs_section() == 1) {
								where.append("(");
								if ("03".equals(config.getCom_type_code())) {
									where.append(aliasRegister.get(obj.getTab_code())).append(".")
											.append(obj.getCol_code()).append(" ").append(" >= ");
									if (reqParam.get(obj.getCol_code().toLowerCase()).toString().contains(",")) {
										where.append("to_date('"
												+ reqParam.get(obj.getCol_code().toLowerCase()).toString().split(",")[0]
												+ "', 'yyyy-mm-dd')");
										where.append(" AND ");
										where.append(aliasRegister.get(obj.getTab_code())).append(".")
												.append(obj.getCol_code()).append(" ").append(" <= ");
										where.append("to_date('"
												+ reqParam.get(obj.getCol_code().toLowerCase()).toString().split(",")[1]
												+ "', 'yyyy-mm-dd')");
									} else {
										where.append(
												"to_date('" + reqParam.get(obj.getCol_code().toLowerCase()).toString()
														+ "', 'yyyy-mm-dd')");
									}
								} else {
									if (reqParam.containsKey(obj.getCol_code().toLowerCase() + "_beg")) {
										where.append(aliasRegister.get(obj.getTab_code())).append(".")
												.append(obj.getCol_code()).append(" ").append(" >= ");
										where.append("#{" + obj.getCol_code().toLowerCase() + "_beg}");
									}
									if (reqParam.containsKey(obj.getCol_code().toLowerCase() + "_beg")
											&& reqParam.containsKey(obj.getCol_code().toLowerCase() + "_end")) {
										where.append(" AND ");
									}
									if (reqParam.containsKey(obj.getCol_code().toLowerCase() + "_end")) {
										where.append(aliasRegister.get(obj.getTab_code())).append(".")
												.append(obj.getCol_code()).append(" ").append(" <= ");
										where.append("#{" + obj.getCol_code().toLowerCase() + "_end}");
									}
								}
								where.append(")");
							} else {
								where.append(aliasRegister.get(obj.getTab_code())).append(".").append(obj.getCol_code())
										.append(" ").append(obj.getCondition()).append(" ");
								
								switch (obj.getCondition()) {
								case "like":
									where.append("'%' || #{" + obj.getCol_code().toLowerCase() + "} || '%'");
									break;
								case "not like":
									where.append("'%' || #{" + obj.getCol_code().toLowerCase() + "} || '%'");
									break;
								case "in":
									where.append("( #{" + obj.getCol_code().toLowerCase() + "} )");
									break;
								case "not in":
									where.append("( #{" + obj.getCol_code().toLowerCase() + "} )");
									break;
								default:
									if("03".equals(config.getCom_type_code())){
										where.append("to_date(");
									}
									where.append("#{" + obj.getCol_code().toLowerCase() + "}");
									if("03".equals(config.getCom_type_code())){
										where.append(", 'yyyy-mm-dd')");
									}
									break;
								}
								
							}
						}
					}

					if (obj.getRight_bracket() != null && StringUtils.isNotEmpty(obj.getRight_bracket())) {
						where.append(")");
					}
				}
			}
			if (where.length() > 0) {
				where = new StringBuilder(where.toString().trim());
				String trimmedUppercaseSql = where.toString().toUpperCase();
				for (String toRemove : wherePrefixList) {
					if (trimmedUppercaseSql.startsWith(toRemove)) {
						where.delete(0, toRemove.trim().length());
						break;
					}
				}
				sql.WHERE(where.toString());
			}
		}

		return this;
	}

	public SelectBuilder groupBy() {
		// 分组
		if (groupList != null && groupList.size() > 0) {
			for (HrTableDesignQueryColumnGroup obj : groupList) {
				if (obj.getTab_code() != null) {
					sql.GROUP_BY(aliasRegister.get(obj.getTab_code()) + "." + obj.getCol_code());
				}
			}
		}
		return this;
	}

	public SelectBuilder orderBy() {
		// 排序
		if (sortList != null && sortList.size() > 0) {
			Collections.sort(sortList);
			for (HrTableDesignQueryColumnSort obj : sortList) {
				if (obj.getTab_code() != null && obj.getSort_mode() != null) {
					sql.ORDER_BY(
							aliasRegister.get(obj.getTab_code()) + "." + obj.getCol_code() + " " + obj.getSort_mode());
				} else if (obj.getTab_code() != null) {
					sql.ORDER_BY(aliasRegister.get(obj.getTab_code()) + "." + obj.getCol_code());
				}
			}
		}
		return this;
	}

	public String getSql() {
		return sql.toString();
	}

	/**
	 * 列函数处理
	 * 
	 * @param func
	 * @param selectColumn
	 * @return
	 */
	private String columnFuncHandle(HrTableDesignQueryColumnTableFunc func, String selectColumn) {
		String funcStr = func.getFunc();
		String param = func.getParam();
		selectColumn = String.format(funcStr, selectColumn, param);
		if (param == null || StringUtils.isEmpty(param.toString())) {
			selectColumn = selectColumn.replaceAll(",null", "");
		}
		return selectColumn;
	}

	/**
	 * 代码表处理
	 * 
	 * @param entityMap
	 * @param dataTableList
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Map<String, Map<String, String>> filedTableHandle() {
		HrTableDesignQueryColumn queryColumn = JSONObject.parseObject(design.getDesign_query_col(),
				HrTableDesignQueryColumn.class);
		List<HrTableDesignQueryColumnTable> dataTableList = queryColumn.getTableData();
		Map<String, Map<String, String>> fieldTable = new LinkedHashMap<String, Map<String, String>>();
		for (HrTableDesignQueryColumnTable table : dataTableList) {
			HrTableStruc struc = tableStrucRegister.get(table.getTab_code());
			if(struc == null){
				continue;
			}
			List<HrTableColumn> cols = struc.getTableColumn();
			Map<String, String> fieldTabs = new HashMap<String, String>();// 用来判断是否存在变更id
			for (HrTableColumn col : cols) {
				if ("02".equals(col.getValue_mode_code()) && col.getField_tab_code() != null) {
					HrFiledTabStruc hrFiledTabStruc = filedTableRegister.get(col.getField_tab_code());
					if(hrFiledTabStruc != null && StringUtils.isNotEmpty(hrFiledTabStruc.getRelated_sql())){
						Map<String, String> filedTabMap = new HashMap<String, String>();
						filedTabMap.put("sql", hrFiledTabStruc.getRelated_sql());
						
						//使用代码表field_col_code字段的个数判断是否存在变更
						int fieldColCodeCount = 0;
						if(hrFiledTabStruc.getCite_json() != null && StringUtils.isNotEmpty(hrFiledTabStruc.getCite_json())){
							List<Map> citeJsonList = JSONArray.parseArray(hrFiledTabStruc.getCite_json(), Map.class);
							for (Map map : citeJsonList) {
								if("field_col_code".equals(map.get("codeItem"))){
									fieldColCodeCount ++;
								}
							}
							
						}
						
						if (fieldTabs.containsKey(col.getField_tab_code()) && fieldColCodeCount == 2) {
							filedTabMap.put("noCol", col.getCol_code());
							fieldTable.put(table.getTab_code() + fieldTabs.get(col.getField_tab_code()), filedTabMap);
							//解决同一表结构出现多个相同带有变更ID的代码表出现错误
							fieldTabs.remove(col.getField_tab_code());
							continue;
						} else {
							fieldTable.put(table.getTab_code() + col.getCol_code(), filedTabMap);
						}

						fieldTabs.put(col.getField_tab_code(), col.getCol_code());
					}else{
						throw new SysException("代码表：“" + col.getField_tab_name() + "” 未维护。");
					}
				}
			}
		}

		return fieldTable;

	}

}
