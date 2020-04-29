package com.chd.hrp.hr.serviceImpl.base;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import oracle.jdbc.OracleTypes;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.dao.ConnCallback;
import org.nutz.dao.Dao;
import org.nutz.dao.entity.Record;
import org.nutz.dao.pager.ResultSetLooping;
import org.nutz.dao.sql.SqlContext;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.lang.util.NutMap;
import org.nutz.mvc.Mvcs;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.Parameter;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.DateUtil;
import com.chd.base.util.JsonListMapUtil;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.SpreadTableJSUtil;
import com.chd.base.util.StringTool;
import com.chd.hrp.acc.dao.wagedata.AccWageCarryOverMapper;
import com.chd.hrp.acc.dao.wagedata.AccWagePayMapper;
import com.chd.hrp.hr.dao.base.BaseCRUDMapper;
import com.chd.hrp.hr.dao.base.CreateTablesMapper;
import com.chd.hrp.hr.dao.base.HrCommonMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sysstruc.HrColStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedTabStrucMapper;
import com.chd.hrp.hr.dao.sysstruc.HrFiiedViewMapper;
import com.chd.hrp.hr.dao.sysstruc.HrStoreColSetMapper;
import com.chd.hrp.hr.entity.base.HrColumn;
import com.chd.hrp.hr.entity.sysstruc.HrColStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedTabStruc;
import com.chd.hrp.hr.entity.sysstruc.HrFiiedView;
import com.chd.hrp.hr.entity.sysstruc.HrStoreColSet;
import com.chd.hrp.hr.entity.sysstruc.HrTabStruc;
import com.chd.hrp.hr.service.base.HrCommonService;
import com.chd.hrp.hr.util.Condition;
import com.chd.hrp.hr.util.ParameterHandler;
import com.chd.hrp.hr.util.RegExpValidatorUtils;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.github.pagehelper.PageInfo;
/**
 * 
 * @ClassName: HrCommonService
 * @Description: hr动态表格、表单增删改查
 * @author zn
 * @date 2017年11月5日 上午10:53:57
 * 
 *
 */

@Service("hrCommonService")
public class HrCommonServiceImpl implements HrCommonService {
	
	private static Logger logger = Logger.getLogger(HrCommonServiceImpl.class);
	
	// 组件类型
	private static final String COM_TYPE_SELECT = "01";// 下拉框
	// private static final String COM_TYPE_TEXT = "02";//文本
	private static final String COM_TYPE_DATE = "03";// 日期
	
	private static final String COM_TYPE_FILE = "04";// 文件上传
	
	private static final String KEYFIELDMAP = "keyFieldMap";

	
	// 引入Service服务
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;

	
	// 引入DAO操作
	
	@Resource(name = "hrColStrucMapper")
	private final HrColStrucMapper hrColStrucMapper = null;
	
	@Resource(name = "hrFiiedTabStrucMapper")
	private final HrFiiedTabStrucMapper hrFiiedTabStrucMapper = null;
	
	@Resource(name = "hrFiiedViewMapper")
	private final HrFiiedViewMapper hrFiiedViewMapper = null;
	
	@Resource(name = "hrStoreColSetMapper")
	private final HrStoreColSetMapper hrStoreColSetMapper = null;
	
	@Resource(name = "createTablesMapper")
	private final CreateTablesMapper createTablesMapper = null;
	
	@Resource(name = "baseCRUDMapper")
	private final BaseCRUDMapper baseCRUDMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	@Resource(name = "hrCommonMapper")
	private final HrCommonMapper hrCommonMapper = null;
	
	@Resource(name = "hrCommonService")
	private final HrCommonService hrCommonService = null;
	
	@Resource(name = "accWagePayMapper")
	private final AccWagePayMapper accWagePayMapper = null;
	
	@Resource(name = "accWageCarryOverMapper")
	private final AccWageCarryOverMapper accWageCarryOverMapper = null;
	
	private static final String FILE_UPLOAD_URL = "fileUpload.do?isCheck=false";// 附件上传URL
			
	private static final String MAIN_TABLE_NAME = "HOS_EMP";// 主表表名
	

	
	/**
	 * 查询字典列表
	 * 
	 * @param entityMap
	 * @return
	 * @throws DataAccessException
	 */
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> queryHrFiiedData(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
		
	/*	hrSelectMapper.queryHrFiiedDataDicByTabCol(entityMap);
		List<Map<String, Object>> fiiedDataList = (List<Map<String, Object>>) entityMap.get("resultlist");
		for (Map<String, Object> map : fiiedDataList) {
			Map<String, Object> option = new HashMap<String, Object>();
			option.put("id", map.get("field_col_code"));
			option.put("text", map.get("field_col_name"));
			option.put("label", map.get("field_col_name"));
			options.add(option);
		}*/
		
		return options;
	}
	
	// 列表表格头
	@Override
	public List<Map<String, Object>> queryListGridHeader(Map<String, Object> entityMap) throws DataAccessException {
		
		// 表格默认列宽
		int defColWidth = MyConfig.getSysPara("06003") == null ? 120 : Integer.parseInt(MyConfig.getSysPara("06003"));
		
		entityMap.put("is_view_tab", 1);// 是否列表显示
		
		List<HrStoreColSet> cols = hrStoreColSetMapper.queryHrStoreColSet(entityMap);
		
		// 表格列
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		
		if (cols != null && cols.size() > 0) {
			if (cols != null && cols.size() > 0) {
				// 如果不是主表需加上职工名称字段
				if (!MAIN_TABLE_NAME.equalsIgnoreCase(entityMap.get("tab_code").toString())) {
					Map<String, Object> fieldItem = new HashMap<String, Object>();
					
					fieldItem.put("name", "EMP_NAME");
					fieldItem.put("display", "职工名称");
					fieldItem.put("width", defColWidth);// 表格列宽度
					fieldItem.put("align", "left");// 对齐方式
					
					fieldItems.add(fieldItem);
				}
				
				// 拼接表头
				for (HrStoreColSet col : cols) {
					Map<String, Object> fieldItem = new HashMap<String, Object>();
					
					String name = col.getCol_code().toUpperCase();
					
					// 字典显示文本信息
					if (col.getCom_type_code() != null && COM_TYPE_SELECT.equals(col.getCom_type_code())) {
						name += "_TEXT";
					}
					
					fieldItem.put("name", name);
					fieldItem.put("display", col.getCol_name_show());
					fieldItem.put("width", col.getCol_width() == null ? defColWidth : col.getCol_width());// 表格列宽度
					fieldItem.put("align", col.getText_align() == null ? "left" : col.getText_align());// 对齐方式
					
					fieldItems.add(fieldItem);
				}
			}
			
		}
		
		return fieldItems;
	}
	
	// 可编辑表格头
	@Override
	public List<Map<String, Object>> queryEditGridHeader(Map<String, Object> entityMap) throws DataAccessException {
		
		int defColWidth = Integer.parseInt(MyConfig.getSysPara("06003"));
		
		entityMap.put("is_view", 1);// 是否显示
		List<HrStoreColSet> cols = hrStoreColSetMapper.queryHrStoreColSet(entityMap);
		
		// 表格列
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		
		for (HrStoreColSet col : cols) {
			Map<String, Object> fieldItem = new HashMap<String, Object>();
			
			fieldItem.put("name", col.getCol_code().toUpperCase());
			fieldItem.put("display", col.getCol_name_show());
			fieldItem.put("width", col.getCol_width() == null ? defColWidth : col.getCol_width());// 表格列宽度
			fieldItem.put("align", col.getText_align() == null ? "left" : col.getText_align());// 对齐方式
			
			if (col.getIs_pk() != null && col.getIs_pk() == 1) {
				fieldItem.put("disabled", 1);// 是否为主键列 修改时主键列不允许编辑
			}
			
			// 默认值
			if (col.getIs_default() != null && col.getIs_default() == 1) {
				fieldItem.put("is_default", true);
				fieldItem.put("default_value", col.getDefault_value());
				fieldItem.put("default_text", col.getDefault_text());
			}
			
			// 处理下拉框类型字段
			Map<String, Object> optionsMap = new HashMap<String, Object>();
			if (col.getCom_type_code() != null && COM_TYPE_SELECT.equals(col.getCom_type_code()) && StringUtils.isNotBlank(col.getField_tab_code())) {
				// entityMap.put("field_tab_code", col.getField_tab_code());
				entityMap.put("col_code", col.getCol_code());
				List<Map<String, Object>> options = queryHrFiiedData(entityMap);
				optionsMap.put("source", options);
				optionsMap.put("type", "select");
				fieldItem.put("name", col.getCol_code().toUpperCase() + "_TEXT");
				optionsMap.put("keyField", col.getCol_code().toUpperCase());
			}
			
			// 处理日期类型字段
			if (col.getCom_type_code() != null && COM_TYPE_DATE.equals(col.getCom_type_code())) {
				optionsMap.put("type", "date");
			}
			
			// 处理文件上传类型字段
			if (col.getCom_type_code() != null && COM_TYPE_FILE.equals(col.getCom_type_code())) {
				Map<String, String> fileOption = new HashMap<String, String>();
				fileOption.put("url", FILE_UPLOAD_URL);
				fileOption.put("keyField", "file");
				fileOption.put("type", "file");
				fieldItem.put("fileModel", fileOption);
			}
			
			fieldItem.put("editor", optionsMap);
			
			fieldItems.add(fieldItem);
		}
		
		return fieldItems;
	}
	
	// 表格数据
	@Override
	public Map<String, Object> queryGridData(Map<String, Object> entityMap) {
		
		try {
			final Map<String, Object> datas = new HashMap<String, Object>();
			
			final HrTabStruc tab = hrCommonMapper.queryTableInfoByCode(entityMap);
			if (tab == null) {
				datas.put("state", false);
				datas.put("error", "没有主集数据");
				return datas;
			}
			final String store_type_code = entityMap.get("store_type_code").toString();
			
			final String condition = assembleConditions(entityMap, tab);
			
			final String customCondition = entityMap.get("param") != null ? " AND " + entityMap.get("param").toString() : "";
			
			Dao nutDao = Mvcs.ctx().getDefaultIoc().get(Dao.class, "dao");
			final String sql = "{CALL PKG_HR_APP.PROC_DYNAMIC_QUERY(?, ?, ?, ?, ?, ?, ?, ?,?)}";
			nutDao.run(new ConnCallback() {
				@Override
				public void invoke(Connection conn) throws Exception {
					try {
						CallableStatement cs = conn.prepareCall(sql);// 通过它来执行sql
						cs.setInt(1, Integer.parseInt(SessionManager.getGroupId())); // 设置入参
						cs.setInt(2, Integer.parseInt(SessionManager.getHosId()));
						cs.setString(3, SessionManager.getCopyCode());
						cs.setString(4, tab.getTab_code());
						cs.setString(5, store_type_code);
						cs.setString(6, condition + customCondition);
						cs.registerOutParameter(7, OracleTypes.CURSOR);// 注册输出参数
						cs.registerOutParameter(8, OracleTypes.INTEGER);
						cs.registerOutParameter(9, OracleTypes.VARCHAR);
						
						System.out.println("(" + Integer.parseInt(SessionManager.getGroupId()) + "," + Integer.parseInt(SessionManager.getHosId()) + ","
								+ SessionManager.getCopyCode() + "," + tab.getTab_code() + "," + store_type_code + "," + condition + customCondition + ")");
						cs.execute();// 执行
						ResultSet rs = (ResultSet) cs.getObject(7);// 返回值
						int appCode = cs.getInt(8);
						if (appCode != 0) {
							logger.debug(cs.getString(9));
							throw new SysException(cs.getString(9));
						}
						final ResultSetMetaData rmd = rs.getMetaData();
						
						ResultSetLooping ing = new ResultSetLooping() {
							protected boolean createObject(int index, ResultSet rs, SqlContext context, int rowCout) {
								NutMap re = new NutMap();
								Record.create(re, rs, rmd);
								list.add(re);
								return true;
							}
						};
						
						ing.doLoop(rs, new SqlContext());
						
						List<NutMap> dataList = Lang.collection2list(ing.getList(), NutMap.class);
						
						// 处理日期类型转换
						for (Map<String, Object> map : dataList) {
							for (Entry<String, Object> entry : map.entrySet()) {
								if (entry.getValue() instanceof Date) {
									entry.setValue(DateUtil.dateFormat(entry.getValue(), "yyyy-MM-dd"));
								}
							}
						}
						
						datas.put("Rows", dataList);
						datas.put("Total", dataList.size());
					} catch (Exception e) {
						
						logger.error(e.getMessage(), e);
						
						throw new SysException(e.getMessage(), e);
					}
					
				}
				
			});
			return datas;
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException(e.getMessage(), e);
		}
	}
	
	// 表格数据
	@Override
	public List<Map<String, Object>> queryGridDataPrint(Map<String, Object> entityMap) {
		
		entityMap.put("tmpQuerySql", entityMap.get("tmpSQL"));

		List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap);
		
		return list;
	}
	
	@Override
	public Map<String, Object> queryGridDataByCustom(Map<String, Object> entityMap) throws DataAccessException {
		
		String tab_code = entityMap.get("tab_code").toString();
		
		// 条件连接符
		Map<String, String> conSignMap = new HashMap<String, String>();
		List<Map<String, String>> conSignList = hrCommonMapper.queryHrConSignByCode();
		for (Map<String, String> map : conSignList) {
			conSignMap.put(map.get("con_sign_code"), map.get("con_sign_name"));
		}
		
		// 变更列
		Map<String, String> changeColMap = new HashMap<String, String>();
		List<HrStoreColSet> cols = hrStoreColSetMapper.queryHrStoreColSet(entityMap);
		for (HrStoreColSet col : cols) {
			if (col.getIs_change() != null && col.getIs_change() == 1) {
				changeColMap.put(col.getCol_code(), col.getChange_col_code());
			}
		}
		
		StringBuilder whereStr = new StringBuilder();
		if (entityMap.get("param") != null && StringUtils.isNotBlank(entityMap.get("param").toString())) {
			List<Parameter> params = JSONArray.parseArray(entityMap.get("param").toString(), Parameter.class);
			if (params != null && !params.isEmpty()) {
				for (int i = 0; i < params.size(); i++) {
					String l_bracket = params.get(i).get("l_bracket") == null ? "(" : params.get(i).get("l_bracket").toString();// 左括号
					String col_code = params.get(i).get("col_code") == null ? null : params.get(i).get("col_code").toString();// 列名
					if (changeColMap.containsKey(col_code) && changeColMap.get(col_code) != null) {
						col_code = tab_code + "." + col_code + "||'@'||" + tab_code + "." + changeColMap.get(col_code);
					} else {
						col_code = tab_code + "." + col_code;
					}
					String con_sign_code = conSignMap.get(String.valueOf(params.get(i).get("con_sign_code")));// 条件连接符
					String field_col_code = params.get(i).get("field_col_code") != null
							&& StringUtils.isNotBlank(params.get(i).get("field_col_code").toString()) ? String.valueOf(params.get(i).get("field_col_code"))
							: String.valueOf(params.get(i).get("field_col_name"));// 值
					String r_bracket = params.get(i).get("r_bracket") == null ? ")" : params.get(i).get("r_bracket").toString();// 右括号
					String join_sign_code = params.get(i).get("join_sign_code") == null ? "AND" : params.get(i).get("join_sign_code").toString();// 连接符
					whereStr.append(l_bracket);
					whereStr.append(col_code);
					whereStr.append(" " + con_sign_code + " ");
					if ("Like".equalsIgnoreCase(con_sign_code) || "NO LIKE".equalsIgnoreCase(con_sign_code)) {
						whereStr.append("'%" + field_col_code + "%'");
					} else if ("IN".equalsIgnoreCase(con_sign_code) || "NO IN".equalsIgnoreCase(con_sign_code)) {
						if (field_col_code.contains(",")) {
							String[] vals = field_col_code.split(",");
							whereStr.append("(");
							for (int j = 0; j < vals.length; j++) {
								whereStr.append("'" + vals[j] + "'");
								if (j < vals.length - 1) {
									whereStr.append(",");
								}
							}
							whereStr.append(")");
						} else if (field_col_code.contains("，")) {
							String[] vals = field_col_code.split("，");
							whereStr.append("(");
							for (int j = 0; j < vals.length; j++) {
								whereStr.append("'" + vals[j] + "'");
								if (j < vals.length - 1) {
									whereStr.append(",");
								}
							}
							whereStr.append(")");
						} else {
							whereStr.append("('" + field_col_code + "')");
						}
					} else {
						whereStr.append("'" + field_col_code + "'");
					}
					
					whereStr.append(r_bracket);
					if (params.size() > 1 && i < params.size() - 1) {
						if (join_sign_code != null) {
							whereStr.append(" " + join_sign_code + " ");
						} else {
							whereStr.append(" AND ");
						}
					}
				}
			}
		}
		entityMap.put("param", whereStr);
		Map<String, Object> data = queryGridData(entityMap);
		return data;
	}
	
	private List<Map<String, Object>> buildQueryGridDataSQL(Map<String, Object> entityMap) {
		
		SQL sql = new SQL();

		String mainTabName = MAIN_TABLE_NAME;// 主表表名
		
		StringBuilder columns = new StringBuilder();// 列名
		
		HrTabStruc tab = hrCommonMapper.queryTableInfoByCode(entityMap);
		
		if (tab != null) {
			
			if (!mainTabName.equals(tab.getTab_code())) {
				columns.append(mainTabName + ".EMP_NAME,");
				StringBuilder join = new StringBuilder();
				join.append(mainTabName + " ON " + mainTabName + ".EMP_ID = " + tab.getTab_code() + ".EMP_ID ");
				
				List<String> columnList = createTablesMapper.findTableColumnByTableName(mainTabName);
				
				if (columnList.contains("GROUP_ID")) {
					join.append("AND " + mainTabName + ".GROUP_ID= " + SessionManager.getGroupId() + " ");
				}
				if (columnList.contains("HOS_ID")) {
					join.append("AND " + mainTabName + ".HOS_ID= " + SessionManager.getHosId() + " ");
				}

				sql.LEFT_OUTER_JOIN(join.toString());
			}
			
			int idx = 1;
			
			for (HrColStruc col : tab.getHrColStrucList()) {
				
				// 拼接列
				if (col.getHrStoreColSet().getIs_change() != null && col.getHrStoreColSet().getIs_change() == 1) {
					String c = col.getHrStoreColSet().getChange_col_code();
					columns.append(tab.getTab_code() + "." + col.getCol_code().toUpperCase() + "||'@'||" + tab.getTab_code() + "." + c.toUpperCase() + " AS "
							+ col.getCol_code().toUpperCase());
				} else {
					columns.append(tab.getTab_code() + "." + col.getCol_code().toUpperCase());
				}
				columns.append(",");
				
				// 拼列JOIN
				if (col.getHrStoreColSet().getCom_type_code() != null && COM_TYPE_SELECT.equals(col.getHrStoreColSet().getCom_type_code())
						&& StringUtils.isNotBlank(col.getField_tab_code())) {
					entityMap.put("field_tab_code", col.getField_tab_code());
					HrFiiedTabStruc hrFiiedTabStruc = hrFiiedTabStrucMapper.queryByCode(entityMap);
					
					if (hrFiiedTabStruc != null) {
						
						if (hrFiiedTabStruc.getIs_cite() != null && hrFiiedTabStruc.getIs_cite() == 1) {
							
							HrFiiedView hrFiiedView = hrFiiedViewMapper.queryByCode(entityMap);
							
							if (hrFiiedView != null) {
								
								columns.append("FD" + idx + ".FIELD_COL_NAME " + col.getCol_code().toUpperCase() + "_TEXT");
								columns.append(",");
								
								String fiiedTabSql = hrFiiedView.getCite_sql();
								ParameterHandler parameterHandler = new ParameterHandler();
								fiiedTabSql = parameterHandler.setParameters(fiiedTabSql);
								
								if (col.getHrStoreColSet().getIs_change() != null && col.getHrStoreColSet().getIs_change() == 1) {
									String c = col.getHrStoreColSet().getChange_col_code();
									sql.LEFT_OUTER_JOIN("(" + fiiedTabSql + ") FD" + idx + " ON FD" + idx + ".FIELD_COL_CODE = to_char(" + tab.getTab_code()
											+ "." + col.getCol_code().toUpperCase() + "||'@'||" + tab.getTab_code() + "." + c.toUpperCase() + ")");
								} else {
									sql.LEFT_OUTER_JOIN("(" + fiiedTabSql + ") FD" + idx + " ON FD" + idx + ".FIELD_COL_CODE = to_char(" + tab.getTab_code()
											+ "." + col.getCol_code().toUpperCase() + ")");
								}
							}
							
						} else {
							
							columns.append("FD" + idx + ".FIELD_COL_NAME " + col.getCol_code().toUpperCase() + "_TEXT");
							columns.append(",");
							
							sql.LEFT_OUTER_JOIN("HR_FIIED_DATA FD" + idx + " ON FD" + idx + ".GROUP_ID = " + SessionManager.getGroupId() + " AND FD" + idx
									+ ".HOS_ID = " + SessionManager.getHosId() + " AND FD" + idx + ".FIELD_TAB_CODE = '" + col.getField_tab_code() + "' AND "
									+ "FD" + idx + ".FIELD_COL_CODE = to_char(" + tab.getTab_code() + "." + col.getCol_code().toUpperCase() + ")");
						}
						
					}
					
				}
				
				idx++;
			}
			
			sql.SELECT(columns.deleteCharAt(columns.length() - 1).toString());
			sql.FROM(tab.getTab_code());
			
			String condition = assembleConditions(entityMap, tab);
			if (condition != null) {
				sql.WHERE(condition);
			}
			
			if (entityMap.get("param") != null && StringUtils.isNotBlank(entityMap.get("param").toString())) {
				sql.AND();
				sql.WHERE(entityMap.get("param").toString());
			}
			
			List<Map<String, Object>> dataList = baseCRUDMapper.queryGridData(sql.toString());
			
			return dataList;
		}
		
		return new ArrayList<Map<String, Object>>();
	}
	
	// 拼接条件
	private String assembleConditions(Map<String, Object> entityMap, HrTabStruc tab) {
		
		Condition condition = Condition.create();
		condition.isWhere(true);
		
		for (HrColStruc col : tab.getHrColStrucList()) {
			if ("GROUP_ID".equals(col.getCol_code().toUpperCase())) {
				condition.eq(tab.getTab_code() + "." + col.getCol_code(), SessionManager.getGroupId());
				continue;
			}
			
			if ("HOS_ID".equals(col.getCol_code().toUpperCase())) {
				condition.eq(tab.getTab_code() + "." + col.getCol_code(), SessionManager.getHosId());
				continue;
			}
			
			if ("COPY_CODE".equals(col.getCol_code().toUpperCase())) {
				condition.eq(tab.getTab_code() + "." + col.getCol_code(), SessionManager.getCopyCode());
				continue;
			}
			
			if ("EMP_ID".equals(col.getCol_code().toUpperCase()) && entityMap.get(col.getCol_code().toUpperCase()) != null
					&& StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
				condition.eq(tab.getTab_code() + "." + col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
				continue;
			}
			
			// 拼接条件
			if (col.getHrStoreQueSet().getIs_view() != null && col.getHrStoreQueSet().getIs_view() == 1) {
				
				String queKey = tab.getTab_code() + "." + col.getCol_code();
				if (col.getHrStoreColSet().getIs_change() != null && col.getHrStoreColSet().getIs_change() == 1) {
					queKey += " ||'@'|| " + tab.getTab_code() + "." + col.getHrStoreColSet().getChange_col_code();
				}
				
				Object value = entityMap.get(col.getCol_code().toUpperCase());
				
				// 区间
				if (col.getHrStoreQueSet().getIs_section() != null && col.getHrStoreQueSet().getIs_section() == 1) {
					if ("DATE".equals(col.getData_type())) {
						if (value != null && StringUtils.isNotBlank(value.toString()) && value.toString().contains(",")) {
							String[] valArr = value.toString().split(",");
							condition.ge(queKey, valArr[0], col.getData_type());
							condition.le(queKey, valArr[1], col.getData_type());
						}
					} else {
						if (entityMap.get(col.getCol_code().toUpperCase() + "_BEG") != null
								&& StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase() + "_BEG").toString())) {
							condition.ge(queKey, entityMap.get(col.getCol_code().toUpperCase() + "_BEG"));
						}
						if (entityMap.get(col.getCol_code().toUpperCase() + "_END") != null
								&& StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase() + "_END").toString())) {
							condition.le(queKey, entityMap.get(col.getCol_code().toUpperCase() + "_END"));
						}
					}
					
					continue;
					
				}
				if (value != null && StringUtils.isNotBlank(value.toString())) {
					if (col.getHrStoreQueSet().getCon_sign_code() != null && StringUtils.isNotBlank(col.getHrStoreQueSet().getCon_sign_code())) {
						// 11:= 12:> 13: >= 14:< 15:<= 16:<> 17:Like 18:NOT LIKE
						// 19:IN 20:NOT IN
						switch (Integer.parseInt(col.getHrStoreQueSet().getCon_sign_code())) {
							case 11:
								condition.eq(queKey, value, col.getData_type());
								break;
							case 12:
								condition.gt(queKey, value, col.getData_type());
								break;
							case 13:
								condition.ge(queKey, value, col.getData_type());
								break;
							case 14:
								condition.lt(queKey, value, col.getData_type());
								break;
							case 15:
								condition.le(queKey, value, col.getData_type());
								break;
							case 16:
								condition.ne(queKey, value, col.getData_type());
								break;
							case 17:
								condition.like(queKey, value.toString());
								break;
							case 18:
								condition.notLike(queKey, value.toString());
								break;
							case 19:
								condition.in(queKey, value.toString());
								break;
							case 20:
								condition.notIn(queKey, value.toString());
								break;
							default:
								condition.eq(queKey, value, col.getData_type());
								break;
						}
					} else {
						condition.eq(queKey, value, col.getData_type());
					}
				}
			}
		}
		
		if (condition.isNotEmptyOfWhere()) {
			String str = "";
			if ("HOS_EMP".equals(entityMap.get("tab_code")) && !"".equals(entityMap.get("DEPT_CODE")) && entityMap.get("DEPT_CODE") != null
					&& !"0".equals(entityMap.get("DEPT_CODE"))) {
				str = " and HOS_EMP.Dept_Id in ( select Dept_Id from hos_dept hd where hd.dept_code like '" + entityMap.get("DEPT_CODE")
						+ "%' and hd.dept_id = HOS_EMP.Dept_Id )  ";
				if (entityMap.get("DEPT_ID").toString().contains(",")) {
					
					return " 1=1 " + str;
				}
			}
			
			return condition.getSqlSegment() + str;
		}
		return null;
		
	}
	
	/**
	 * 执行SQL
	 * 
	 * @param entityMap
	 * @return
	 */
	public String selectGridData(String sql) {
		return sql;
	}
	
	@Override
	public String queryEditForm(Map<String, Object> entityMap, int colNum) throws DataAccessException {
		entityMap.put("is_view", 1);
		List<HrColumn> hrColStrucList = hrCommonMapper.queryColJoinSetList(entityMap);
		return queryForm(entityMap, colNum, hrColStrucList);
	}
	
	@Override
	public String queryQueForm(Map<String, Object> entityMap, int colNum) throws DataAccessException {
		entityMap.put("is_view", 1);
		List<HrColumn> hrColStrucList = hrCommonMapper.queryColJoinQueSetList(entityMap);
		return queryForm(entityMap, colNum, hrColStrucList);
	}/**
	 * 查询表单信息
	 * 
	 * @param entityMap
	 * @param colNum
	 * @param hrColStrucList
	 * @return
	 * @throws DataAccessException
	 */
	private String queryForm(Map<String, Object> entityMap, int colNum, List<HrColumn> hrColStrucList) throws DataAccessException {
		LinkedHashMap<String,Map<String,Object>> citeFiiedMap = new LinkedHashMap<String,Map<String,Object>>();
		int defFieldWidth = MyConfig.getSysPara("06004") == null ? 180 : Integer.parseInt(MyConfig.getSysPara("06004"));
		
		Map<String, Object> data = null;
		String child_code=null;
		String tab_code=entityMap.get("tab_code").toString();
		if(entityMap.get("child_code")!=null) {
		 child_code=entityMap.get("child_code").toString();
		}
		
		
		if (entityMap.get("EMP_ID") != null && StringUtils.isNotBlank(entityMap.get("EMP_ID").toString())) {
			// data = (Map<String, Object>) entityMap.get("data");
			List<Map<String, Object>> dataList = buildQueryGridDataSQL(entityMap);
			data = dataList.get(0);
		}
		
		// 组件（下拉框、文本框等）
		List<Map<String, Object>> comTypeList = hrColStrucMapper.queryHrComType(entityMap);
		Map<String, Object> comTypeMap = new HashMap<String, Object>();
		if (comTypeList != null && comTypeList.size() > 0) {
			for (Map<String, Object> map : comTypeList) {
				String key = String.valueOf(map.get("com_type_code"));
				String val = String.valueOf(map.get("com_type_nature"));
				comTypeMap.put(key, val);
			}
		}
		List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
		
		
		for (Map<String, Object> citeHrFiiedMap : hrFiiedList) {
						
			citeFiiedMap.put(citeHrFiiedMap.get("FIELD_TAB_CODE").toString(), citeHrFiiedMap);
						
		}
		Map<String, Object> formMap = new HashMap<String, Object>();
		formMap.put("colNum", colNum);// 页面form表单列的个数
		
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		
		for (HrColumn col : hrColStrucList) {
			if(col.getTab_code().equals(tab_code)) {
				
				// 字段类型
				String type = "text";
				if (comTypeMap != null && comTypeMap.size() > 0 && col.getCom_type_code() != null) {
					type = String.valueOf(comTypeMap.get(col.getCom_type_code()));
				}
				
				// 字段宽度
				int field_width = col.getField_width() == null ? 1 : col.getField_width();
				
				Map<String, Object> fieldItem = new HashMap<String, Object>();
				
				// 是否区间查询
				if (col.getIs_section() != null && col.getIs_section() == 1) {
					if ("DATE".equals(col.getData_type())) {
						fieldItem.put("id", col.getCol_code().toUpperCase());
						fieldItem.put("name", col.getCol_name_show());
						fieldItem.put("type", type);
						Map<String, Object> rangeMap = new LinkedHashMap<String, Object>();
						rangeMap.put("range", true);
						fieldItem.put("OPTIONS", rangeMap);
						fieldItem.put("required",false);
						fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
						fieldItem.put("place", field_width);// 占列数
					} else {
						fieldItem.put("id", col.getCol_code().toUpperCase());
						fieldItem.put("name", col.getCol_name_show());
						fieldItem.put("type", "range");
						Map<String, Object> rangeIdMap = new LinkedHashMap<String, Object>();
						String[] rangeIdArr = { col.getCol_code().toUpperCase() + "_BEG", col.getCol_code().toUpperCase() + "_END" };
						rangeIdMap.put("rangeId", rangeIdArr);
						fieldItem.put("OPTIONS", rangeIdMap);
						fieldItem.put("required",false);
						fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
						fieldItem.put("place", field_width);// 占列数
					}
				} else {
					
					fieldItem.put("id", col.getCol_code().toUpperCase());
					fieldItem.put("name", col.getCol_name_show());
					fieldItem.put("type", type);
					fieldItem.put("value", "");
					
					fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
					fieldItem.put("place", field_width);// 占列数
					
					// 只读
					if (col.getIs_read() != null && col.getIs_read() == 1) {
						fieldItem.put("disabled", true);
					}
					
					// 必填项
					fieldItem.put("required", col.getIs_verify() != null && col.getIs_verify() == 1 ? true : false);
					
					// 默认值
					if (col.getIs_default() != null && col.getIs_default() == 1) {
						fieldItem.put("value", col.getDefault_value());
					}
					
					// 如果是修改页面则传值
					if (data != null && data.size() > 0) {
						
						// 主键列不可编辑
						if (col.getIs_pk() != null && col.getIs_pk() == 1) {
							fieldItem.put("disabled", true);
						}
						
						if (data.get(col.getCol_code().toUpperCase()) != null) {
							fieldItem.put("value", data.get(col.getCol_code().toUpperCase()).toString());
							if ("PHOTO".equals(col.getCol_code())) {
								formMap.put("PHOTO", data.get(col.getCol_code().toUpperCase()));
							}
							
						}
					}
					
					// 过滤头像文本输入框
					if ("PHOTO".equals(col.getCol_code().toUpperCase())) {
						continue;
					}
					
					// 代码表数据
					Map<String, Object> optionsMap = new HashMap<String, Object>();
					if (StringUtils.isNotBlank(col.getField_tab_code())) {
						// entityMap.put("field_tab_code", col.getField_tab_code());
						entityMap.put("tab_code", col.getTab_code());
						entityMap.put("col_code", col.getCol_code());

						if("0".equals(citeFiiedMap.get(col.getField_tab_code()).get("IS_CITE").toString())){
							
							optionsMap.put("url", "queryHrHosSelect.do?isCheck=false&table_code="+col.getField_tab_code());
							optionsMap.put("defaultValue", "none");
						
						}else {
							
						if(MAIN_TABLE_NAME.equals(col.getField_tab_code().toString())) {
							
				           optionsMap.put("url", "queryHosEmpSelect.do?isCheck=false");
				           optionsMap.put("defaultValue", "none");
				          optionsMap.put("maxOptions", 10000);// 最大显示条数
				          
			                 }else {
							
							String sqlCite = String.valueOf(citeFiiedMap.get(col.getField_tab_code()).get("CITE_SQL"));
							
							sqlCite = sqlCite.replaceAll("\n", " ");
							
							sqlCite = sqlCite.replaceAll("\r", " ");
							
							sqlCite = sqlCite.replaceAll("\t", " ");

							optionsMap.put("url","queryHrHosSelectCite.do?isCheck=false&sql="+sqlCite);
					
						}
						/*List<Map<String, Object>> options = queryHrFiiedData(entityMap);
						optionsMap.put("options", options);*/
						optionsMap.put("defaultValue", "none");
						
						//optionsMap.put("backEndSearch", false); // backEndSearch:false
																// 关闭后台检索
						optionsMap.put("maxOptions", 10000);// 最大显示条数
					}
					}
					fieldItem.put("OPTIONS", optionsMap);
				}
				fieldItems.add(fieldItem);
	}
			
			
		
	}
				
			
		formMap.put("fieldItems", fieldItems);
		
		return JSONArray.toJSONString(formMap);
	}

	@Override
	public String queryFormSearchPage(Map<String, Object> entityMap, int colNum) throws DataAccessException {
		entityMap.put("is_view", 1);
		List<HrColumn> hrColStrucList = hrCommonMapper.queryColJoinQueSetList(entityMap);
		return queryFormSearchPage(entityMap, colNum, hrColStrucList);
	}
	
	/**
	 * 查询表单信息
	 * 
	 * @param entityMap
	 * @param colNum
	 * @param hrColStrucList
	 * @return
	 * @throws DataAccessException
	 */
	private String queryFormSearchPage(Map<String, Object> entityMap, int colNum, List<HrColumn> hrColStrucList) throws DataAccessException {
		LinkedHashMap<String,Map<String,Object>> citeFiiedMap = new LinkedHashMap<String,Map<String,Object>>();
		int defFieldWidth = MyConfig.getSysPara("06004") == null ? 180 : Integer.parseInt(MyConfig.getSysPara("06004"));
		
		Map<String, Object> data = null;
		String child_code=null;
		String tab_code=entityMap.get("tab_code").toString();
		if(entityMap.get("child_code")!=null) {
		 child_code=entityMap.get("child_code").toString();
		}
		
		
		if (entityMap.get("EMP_ID") != null && StringUtils.isNotBlank(entityMap.get("EMP_ID").toString())) {
			// data = (Map<String, Object>) entityMap.get("data");
			List<Map<String, Object>> dataList = buildQueryGridDataSQL(entityMap);
			data = dataList.get(0);
		}
		
		// 组件（下拉框、文本框等）
		List<Map<String, Object>> comTypeList = hrColStrucMapper.queryHrComType(entityMap);
		Map<String, Object> comTypeMap = new HashMap<String, Object>();
		if (comTypeList != null && comTypeList.size() > 0) {
			for (Map<String, Object> map : comTypeList) {
				String key = String.valueOf(map.get("com_type_code"));
				String val = String.valueOf(map.get("com_type_nature"));
				comTypeMap.put(key, val);
			}
		}
		List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
		
		
		for (Map<String, Object> citeHrFiiedMap : hrFiiedList) {
						
			citeFiiedMap.put(citeHrFiiedMap.get("FIELD_TAB_CODE").toString(), citeHrFiiedMap);
						
		}
		Map<String, Object> formMap = new HashMap<String, Object>();
		formMap.put("colNum", colNum);// 页面form表单列的个数
		
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		
		for (HrColumn col : hrColStrucList) {
			if(col.getTab_code().equals(tab_code)) {
				
				// 字段类型
				String type = "text";
				if (comTypeMap != null && comTypeMap.size() > 0 && col.getCom_type_code() != null) {
					type = String.valueOf(comTypeMap.get(col.getCom_type_code()));
				}
				
				// 字段宽度
				int field_width = col.getField_width() == null ? 1 : col.getField_width();
				
				Map<String, Object> fieldItem = new HashMap<String, Object>();
				
				// 是否区间查询
				if (col.getIs_section() != null && col.getIs_section() == 1) {
					if ("DATE".equals(col.getData_type())) {
						fieldItem.put("id", tab_code+"."+col.getCol_code().toUpperCase());
						fieldItem.put("name", col.getCol_name_show());
						fieldItem.put("type", type);
						Map<String, Object> rangeMap = new LinkedHashMap<String, Object>();
						rangeMap.put("range", true);
						fieldItem.put("OPTIONS", rangeMap);
						fieldItem.put("required",false);
						fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
						fieldItem.put("place", field_width);// 占列数
					} else {
						fieldItem.put("id", tab_code+"."+col.getCol_code().toUpperCase());
						fieldItem.put("name", col.getCol_name_show());
						fieldItem.put("type", "range");
						Map<String, Object> rangeIdMap = new LinkedHashMap<String, Object>();
						String[] rangeIdArr = { col.getCol_code().toUpperCase() + "_BEG", col.getCol_code().toUpperCase() + "_END" };
						rangeIdMap.put("rangeId", rangeIdArr);
						fieldItem.put("OPTIONS", rangeIdMap);
						fieldItem.put("required",false);
						fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
						fieldItem.put("place", field_width);// 占列数
					}
				} else {
					
					fieldItem.put("id", tab_code+"."+col.getCol_code().toUpperCase());
					fieldItem.put("name", col.getCol_name_show());
					fieldItem.put("type", type);
					fieldItem.put("value", "");
					
					fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
					fieldItem.put("place", field_width);// 占列数
					
					// 只读
					if (col.getIs_read() != null && col.getIs_read() == 1) {
						fieldItem.put("disabled", true);
					}
					
					// 必填项
					fieldItem.put("required", col.getIs_verify() != null && col.getIs_verify() == 1 ? true : false);
					
					// 默认值
					if (col.getIs_default() != null && col.getIs_default() == 1) {
						fieldItem.put("value", col.getDefault_value());
					}
					
					// 如果是修改页面则传值
					if (data != null && data.size() > 0) {
						
						// 主键列不可编辑
						if (col.getIs_pk() != null && col.getIs_pk() == 1) {
							fieldItem.put("disabled", true);
						}
						
						if (data.get(col.getCol_code().toUpperCase()) != null) {
							fieldItem.put("value", data.get(col.getCol_code().toUpperCase()).toString());
							if ("PHOTO".equals(col.getCol_code())) {
								formMap.put("PHOTO", data.get(col.getCol_code().toUpperCase()));
							}
							
						}
					}
					
					// 过滤头像文本输入框
					if ("PHOTO".equals(col.getCol_code().toUpperCase())) {
						continue;
					}
					
					// 代码表数据
					Map<String, Object> optionsMap = new HashMap<String, Object>();
					if (StringUtils.isNotBlank(col.getField_tab_code())) {
						// entityMap.put("field_tab_code", col.getField_tab_code());
						entityMap.put("tab_code", col.getTab_code());
						entityMap.put("col_code", col.getCol_code());

						if("0".equals(citeFiiedMap.get(col.getField_tab_code()).get("IS_CITE").toString())){
							
							optionsMap.put("url", "queryHrHosSelect.do?isCheck=false&table_code="+col.getField_tab_code());
							optionsMap.put("defaultValue", "none");
						
						}else {
							
						if(MAIN_TABLE_NAME.equals(col.getField_tab_code().toString())) {
							
				           optionsMap.put("url", "queryHosEmpSelect.do?isCheck=false");
				           optionsMap.put("defaultValue", "none");
				          optionsMap.put("maxOptions", 10000);// 最大显示条数
				          
			                 }else {
							
							String sqlCite = String.valueOf(citeFiiedMap.get(col.getField_tab_code()).get("CITE_SQL"));
							
							sqlCite = sqlCite.replaceAll("\n", " ");
							
							sqlCite = sqlCite.replaceAll("\r", " ");
							
							sqlCite = sqlCite.replaceAll("\t", " ");

							optionsMap.put("url","queryHrHosSelectCite.do?isCheck=false&sql="+sqlCite);
					
						}
						/*List<Map<String, Object>> options = queryHrFiiedData(entityMap);
						optionsMap.put("options", options);*/
						optionsMap.put("defaultValue", "none");
						
						//optionsMap.put("backEndSearch", false); // backEndSearch:false
																// 关闭后台检索
						optionsMap.put("maxOptions", 10000);// 最大显示条数
					}
					}
					fieldItem.put("OPTIONS", optionsMap);
				}
				fieldItems.add(fieldItem);
	}
			if(child_code!=null) {
	if(col.getTab_code().equals(child_code) && !col.getCol_code().equals("EMP_ID") && !tab_code.equals(child_code)) { {

		
					// 字段类型
					String type = "text";
					if (comTypeMap != null && comTypeMap.size() > 0 && col.getCom_type_code() != null) {
						type = String.valueOf(comTypeMap.get(col.getCom_type_code()));
					}
					
					// 字段宽度
					int field_width = col.getField_width() == null ? 1 : col.getField_width();
					
					Map<String, Object> fieldItem = new HashMap<String, Object>();
					
					// 是否区间查询
					if (col.getIs_section() != null && col.getIs_section() == 1) {
						if ("DATE".equals(col.getData_type())) {
							fieldItem.put("id", child_code+"."+col.getCol_code().toUpperCase());
							fieldItem.put("name", col.getCol_name_show());
							fieldItem.put("type", type);
							Map<String, Object> rangeMap = new LinkedHashMap<String, Object>();
							rangeMap.put("range", true);
							fieldItem.put("OPTIONS", rangeMap);
							fieldItem.put("required",false);
							fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
							fieldItem.put("place", field_width);// 占列数
						} else {
							fieldItem.put("id", child_code+"."+col.getCol_code().toUpperCase());
							fieldItem.put("name", col.getCol_name_show());
							fieldItem.put("type", "range");
							Map<String, Object> rangeIdMap = new LinkedHashMap<String, Object>();
							String[] rangeIdArr = { col.getCol_code().toUpperCase() + "_BEG", col.getCol_code().toUpperCase() + "_END" };
							rangeIdMap.put("rangeId", rangeIdArr);
							fieldItem.put("OPTIONS", rangeIdMap);
							fieldItem.put("required",false);
							fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
							fieldItem.put("place", field_width);// 占列数
						}
					} else {
						
						fieldItem.put("id", child_code+"."+col.getCol_code().toUpperCase());
						fieldItem.put("name", col.getCol_name_show());
						fieldItem.put("type", type);
						fieldItem.put("value", "");
						/*fieldItem.put("defaultValue", "none");*/
						fieldItem.put("width", defFieldWidth * field_width);// 字段宽度
						fieldItem.put("place", field_width);// 占列数
						
						// 只读
						if (col.getIs_read() != null && col.getIs_read() == 1) {
							fieldItem.put("disabled", true);
						}
						
						// 必填项
						fieldItem.put("required", col.getIs_verify() != null && col.getIs_verify() == 1 ? true : false);
						
						// 默认值
						if (col.getIs_default() != null && col.getIs_default() == 1) {
							fieldItem.put("value", col.getDefault_value());
						}
						
						// 如果是修改页面则传值
						if (data != null && data.size() > 0) {
							
							// 主键列不可编辑
							if (col.getIs_pk() != null && col.getIs_pk() == 1) {
								fieldItem.put("disabled", true);
							}
							
							if (data.get(col.getCol_code().toUpperCase()) != null) {
								fieldItem.put("value", data.get(col.getCol_code().toUpperCase()).toString());
								if ("PHOTO".equals(col.getCol_code())) {
									formMap.put("PHOTO", data.get(col.getCol_code().toUpperCase()));
								}
								
							}
						}
						
						// 过滤头像文本输入框
						if ("PHOTO".equals(col.getCol_code().toUpperCase())) {
							continue;
						}
						
						// 代码表数据
						Map<String, Object> optionsMap = new HashMap<String, Object>();
						if (StringUtils.isNotBlank(col.getField_tab_code())) {
							// entityMap.put("field_tab_code", col.getField_tab_code());
							entityMap.put("tab_code", col.getTab_code());
							entityMap.put("col_code", col.getCol_code());

							if("0".equals(citeFiiedMap.get(col.getField_tab_code()).get("IS_CITE").toString())){
								optionsMap.put("url", "queryHrHosSelect.do?isCheck=false&table_code="+col.getField_tab_code());
								optionsMap.put("defaultValue", "none");
							}else {
								if(MAIN_TABLE_NAME.equals(citeFiiedMap.get(col.getField_tab_code()).get("FIELD_TAB_CODE").toString())) {
								optionsMap.put("url", "queryHosEmpSelect.do?isCheck=false");
								optionsMap.put("defaultValue", "none");
								
								//optionsMap.put("backEndSearch", false); // backEndSearch:false
																		// 关闭后台检索
								optionsMap.put("maxOptions", 10000);// 最大显示条数
							}else {
												String sqlCite = String.valueOf(citeFiiedMap.get(col.getField_tab_code()).get("CITE_SQL"));
												
												sqlCite = sqlCite.replaceAll("\n", " ");
												
												sqlCite = sqlCite.replaceAll("\r", " ");
												
												sqlCite = sqlCite.replaceAll("\t", " ");
				
												optionsMap.put("url","queryHrHosSelectCite.do?isCheck=false&sql="+sqlCite);
											}
											/*List<Map<String, Object>> options = queryHrFiiedData(entityMap);
											optionsMap.put("options", options);*/
											optionsMap.put("defaultValue", "none");
											
										//	optionsMap.put("backEndSearch", false); // backEndSearch:false
																					// 关闭后台检索
											optionsMap.put("maxOptions", 10000);// 最大显示条数
										}
										}
						fieldItem.put("OPTIONS", optionsMap);
					}
					fieldItems.add(fieldItem);
					
				}
			
	}
			  }
			
		
	}
				
			
		formMap.put("fieldItems", fieldItems);
		
		return JSONArray.toJSONString(formMap);
	}
	
	@Override
	public List<Map<String, Object>> findList(String tab_code, Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Map<String, Object>> tableMap = new HashMap<String, Map<String, Object>>();
		tableMap.put(tab_code, entityMap);
		return baseCRUDMapper.queryInfo(tableMap);
	}
	
	@Override
	public String saveGrid(String tab_code, List<Parameter> addList, List<Parameter> modList) throws DataAccessException {
		
		try {

			String USER_CODE = SessionManager.getUserId();
			
			if (addList != null && addList.size() > 0) {
				Map<String, Map<String, Object>> tableMap = new HashMap<String, Map<String, Object>>();
				for (Parameter parameter : addList) {
					
					Map<String, Object> map = filterParameter(tab_code, parameter, false);
					
					tableMap.put(tab_code, map);
					
					baseCRUDMapper.saveInfo(tableMap);
					
//					if("HOS_EMP".equals(tab_code)){
//						
//						tableMap = new HashMap<String, Map<String, Object>>();
//						
//						map.put("USER_CODE", USER_CODE);
//						
//						tableMap.put("HOS_EMP_DICT", map);
//						
//						baseCRUDMapper.saveInfo(tableMap);
//						
//					}
					
				}
			}
			
			if (modList != null && modList.size() > 0) {
				Map<String, Map<String, Object>> tableMap = new HashMap<String, Map<String, Object>>();
				for (Parameter parameter : modList) {
					
					Map<String, Object> map = filterParameter(tab_code, parameter, true);
					
					tableMap.put(tab_code, map);
					
					baseCRUDMapper.updateInfo(tableMap);
					
//					if("HOS_EMP".equals(tab_code)){
//						
//						tableMap = new HashMap<String, Map<String, Object>>();
//						
//						map.put("USER_CODE", USER_CODE);
//						
//						tableMap.put("HOS_EMP_DICT", map);
//						
//						baseCRUDMapper.updateInfo(tableMap);
//						
//					}
				}
			}
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
		
	}
	
	@Override
	public String saveFrom(Map<String, Object> entityMap) throws DataAccessException {
		boolean isSave = true;
		String USER_CODE = SessionManager.getUserId();
		// 表名
		String tab_code = entityMap.get("tab_code").toString();
		// 员工ID
		String emp_id = entityMap.get("EMP_ID") == null ? null : entityMap.get("EMP_ID").toString();
		
		// 获取表列信息
		HrTabStruc tab = hrCommonMapper.queryTableInfoByCode(entityMap);
		
		Map<String, Map<String, Object>> tableMap = new HashMap<String, Map<String, Object>>();
		Map<String, Object> dataMap = new HashMap<String, Object>();
		Map<String, Object> keyFieldMap = new HashMap<String, Object>();
		
		for (HrColStruc col : tab.getHrColStrucList()) {
			System.out.println(col.getCol_code());
			if ("GROUP_ID".equals(col.getCol_code().toUpperCase())) {
				dataMap.put(col.getCol_code(), SessionManager.getGroupId());
				keyFieldMap.put(col.getCol_code(), SessionManager.getGroupId());
				continue;
			}
			
			if ("HOS_ID".equals(col.getCol_code().toUpperCase())) {
				dataMap.put(col.getCol_code(), SessionManager.getHosId());
				keyFieldMap.put(col.getCol_code(), SessionManager.getHosId());
				continue;
			}
			
			if ("COPY_CODE".equals(col.getCol_code().toUpperCase())) {
				dataMap.put(col.getCol_code(), SessionManager.getCopyCode());
				keyFieldMap.put(col.getCol_code(), SessionManager.getCopyCode());
				continue;
			}
			
			// 判断是否为更新操作
			if (StringUtils.isNotBlank(emp_id)) {
				isSave = false;
				if (col.getHrStoreColSet().getIs_pk() != null && col.getHrStoreColSet().getIs_pk() == 1) {
					keyFieldMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
				}
			}
			
			// 如果是自增,并且是保存
			if(isSave && "HOS_EMP".equals(col.getHrStoreColSet())){
				
				try {
					int id = baseCRUDMapper.querySeqByTabCode(col.getTab_code().toUpperCase());
					dataMap.put(col.getCol_code(), id);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new SysException("缺少序列" + col.getTab_code().toUpperCase() + "_SEQ");
				}
				
				continue;
				
			}
			if (isSave && !"HOS_EMP".equals(col.getHrStoreColSet()) && col.getHrStoreColSet().getIs_auto() != null && col.getHrStoreColSet().getIs_auto() == 1) {
				try {
					int id = baseCRUDMapper.querySeqByTabCode(col.getTab_code().toUpperCase());
					dataMap.put(col.getCol_code(), id);
				} catch (Exception e) {
					logger.error(e.getMessage());
					throw new SysException("缺少序列" + col.getTab_code().toUpperCase() + "_SEQ");
				}
				
				continue;
			}
			
			if (entityMap.get(col.getCol_code().toUpperCase()) != null) {
				
				if (col.getHrStoreColSet().getIs_change() != null && col.getHrStoreColSet().getIs_change() == 1) {
					String[] strArr = entityMap.get(col.getCol_code().toUpperCase()).toString().split("@");
					entityMap.put(col.getCol_code().toUpperCase(), strArr[0]);
					dataMap.put(col.getHrStoreColSet().getChange_col_code(), strArr[1]);
				}
				
				// 验证数据类型
				if ("NUMBER".equals(col.getData_type()) && StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())
						&& !RegExpValidatorUtils.IsNumber(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
					throw new SysException(col.getCol_name() + "必须是数字格式。");
				}
				
				if ("DATE".equals(col.getData_type()) && StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())
						&& !RegExpValidatorUtils.isDate(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
					throw new SysException(col.getCol_name() + "必须是日期格式(yyyy-MM-dd)。");
				}
				
				if (col.getData_type() != null && "DATE".equals(col.getData_type()) && entityMap.get(col.getCol_code().toUpperCase()) != null
						&& StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
					// 处理日期类型
					dataMap.put(col.getCol_code(), DateUtil.stringToDate(String.valueOf(entityMap.get(col.getCol_code().toUpperCase())), "yyyy-MM-dd"));
				} else {
					dataMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
				}
			}
			
			// 判断唯一性
			if (col.getIs_unique() != null && col.getIs_unique() == 1 && isSave) {
				Map<String, Map<String, Object>> tableMapB = new HashMap<String, Map<String, Object>>();
				Map<String, Object> map = new HashMap<String, Object>();
				// 添加编码规则判断
				map.put("group_id", SessionManager.getGroupId());
				map.put("hos_id", SessionManager.getHosId());
				map.put("proj_code", "HOS_EMP");
				map.put("mod_code", "00");
				Map<Object, Object> rules = sysBaseService.getHosRules(map);
				String proj_code = entityMap.get(col.getCol_code()).toString();
				if (null != entityMap.get(col.getCol_code().toUpperCase())) {
					
					Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
					
					Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
					
					int max_level = Integer.parseInt(rules.get("max_level").toString());
					
					if(max_level>0){
						
						String rules_v = rules.get("rules_view").toString();
						
						String s_view = Strings.removeFirst(rules_v);
						
						Object level = proj_code.length();
						int code=proj_code.length();
						if(rules_level_length!=null){
							//当第一级为0时 不验证规则
							int sLevel=Integer.parseInt(rules_level_length.get(1).toString());
							if(!rules_level_length.get(1).toString().equals("0")){
								
								if(code > sLevel){
									return "{\"error\":\"编码不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
								}
								String eCode="";
								for (int i = 0; i < sLevel-code; i++) {
									eCode+="0";
								}
								String emp_code=eCode+proj_code;
								entityMap.put("EMP_CODE", emp_code);
								dataMap.put("EMP_CODE", emp_code);
							}
						}
					}
					
				}
				//
				Map<String, Object> whereMap = new HashMap<String, Object>();
				
				whereMap.put("group_id", SessionManager.getGroupId());
				whereMap.put("hos_id", SessionManager.getHosId());
				
				whereMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
				
				tableMapB.put(tab_code, whereMap);
				
				List<Map<String, Object>> list = baseCRUDMapper.queryInfo(tableMapB);
				
				if (list.size() > 0) {
					return "{\"error\":\"" + col.getCol_name() + "已存在\"}";
				}
			}
		}
		if (dataMap.get("SPELL_CODE") == null || dataMap.get("SPELL_CODE").toString().equals("")) {
			dataMap.put("SPELL_CODE", StringTool.toPinyinShouZiMu(dataMap.get("EMP_NAME").toString()));
		}
		if (dataMap.get("WBX_CODE") == null || dataMap.get("WBX_CODE").toString().equals("")) {
			dataMap.put("WBX_CODE", StringTool.toWuBi(dataMap.get("EMP_NAME").toString()));
		}
		
		try {
			if (isSave) {
				// emp_id = IdGen.uuid();// ID自动生成
				// dataMap.put("emp_id", emp_id);
				tableMap.put(tab_code, dataMap);
				// 执行保存操作
				baseCRUDMapper.saveInfo(tableMap);
				baseCRUDMapper.saveHosEmpDict(USER_CODE, dataMap);
			} else {
				dataMap.put(KEYFIELDMAP, keyFieldMap);
				tableMap.put(tab_code, dataMap);
				// 执行更新操作根据主键
				baseCRUDMapper.updateInfo(tableMap);
				baseCRUDMapper.updateHosEmpDict(USER_CODE, dataMap);
				
				//修改职工的信息同步更新职工在当月工资表中的信息
				entityMap.put("copy_code", SessionManager.getCopyCode());
				
				entityMap.put("wage_flag", "1");
				
	 			String account_day = accWageCarryOverMapper.queryAccYearMonthNow(entityMap);
	 			
	 			Map<String, Object> map = new HashMap<String, Object>();
				
	 			entityMap.put("acc_year", account_day.substring(0,4));
				
	 			entityMap.put("acc_month", account_day.substring(5,7));
	 			
	 			entityMap.put("dept_no", dataMap.get("DEPT_NO"));
				
				accWagePayMapper.updateAccWagePayByEmpDict(JsonListMapUtil.toMapLower(entityMap));
			}
			
			return "{\"EMP_ID\":\"" + dataMap.get("EMP_ID") + "\",\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String deleteBatchItem(String tab_code, List<Parameter> paramList) throws DataAccessException {
		
		Map<String, Object> paramMap = new HashMap<String, Object>();
		String table_dict = null;
		paramMap.put("group_id", SessionManager.getGroupId());
		paramMap.put("hos_id", SessionManager.getHosId());
		paramMap.put("copy_code", SessionManager.getCopyCode());
		paramMap.put("tab_code", tab_code);
		
		if (tab_code.equals("HOS_EMP")) {
			table_dict = new String("HOS_EMP_DICT");
		}
		try {
			if (StringUtils.isNotBlank(tab_code) && paramList.size() > 0) {
				List<HrColStruc> cols = hrColStrucMapper.queryHrColStrucEntity(paramMap);
				for (Parameter param : paramList) {
					Map<String, Map<String, Object>> tableMap = new HashMap<String, Map<String, Object>>();
					Map<String, Map<String, Object>> tableMapDict = new HashMap<String, Map<String, Object>>();
					Map<String, Object> dataMap = new HashMap<String, Object>();
					
					for (HrColStruc col : cols) {
						if (col.getIs_pk() != null && col.getIs_pk() == 1 && param.get(col.getCol_code().toUpperCase()) != null
								&& StringUtils.isNotBlank(param.get(col.getCol_code().toUpperCase()).toString())) {
							if ("DATE".equals(col.getData_type())) {
								dataMap.put(col.getCol_code(), DateUtil.stringToDate(param.get(col.getCol_code().toUpperCase()).toString(), "yyyy-MM-dd"));
							} else {
								dataMap.put(col.getCol_code(), param.get(col.getCol_code().toUpperCase()));
							}
						}
					}
					
					if (dataMap != null && !dataMap.isEmpty()) {
						tableMap.put(tab_code, dataMap);
						tableMapDict.put(table_dict, dataMap);
						if (table_dict != null && !"".equals(table_dict)) {
							// 当删除人员档案信息时：查询所有其他子集信息，若有数据提示先删除子集在删除人员信息
							List<Map<String, Object>> listTab = baseCRUDMapper.queryAllTable(param);
							List<String> listT = new ArrayList<String>();
							for (Map<String, Object> map : listTab) {
								if (!"HOS_EMP".equals(map.get("tab_code").toString())) {
									listT.add(map.get("tab_code").toString());
								}
								
							}
							paramMap.put("list", listT);
							paramMap.put("EMP_ID", param.get("EMP_ID"));
							List<HashMap<String, Object>> listInfo = baseCRUDMapper.queryChildData(paramMap);
							if (listInfo.size() != 0) {
								String info = "";
								for (HashMap<String, Object> hashMap : listInfo) {
									info += hashMap.get("tab_name") + "、";
								}
								info = info.substring(0, info.length() - 1);
								info = "请先删除以下子集信息(" + info + ")再删除职工信息！";
								return "{\"error\":\"" + info + "\",\"state\":\"false\"}";
							}
							
							baseCRUDMapper.deleteInfo(tableMapDict);
						}
						
						baseCRUDMapper.deleteInfo(tableMap);
					}
					
				}
				
				return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			} else {
				return "{\"error\":\"参数不完整\"}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 过滤参数
	 * 
	 * @param entityMap
	 * @return
	 */
	private Map<String, Object> filterParameter(String tab_code, Map<String, Object> entityMap, boolean isUpdate) {
		
		try{

		Map<String, Object> reMap = new HashMap<String, Object>();
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		HrTabStruc tabInfo = hrCommonMapper.queryTableInfoByCode(entityMap);
		
		// 判断联合主键是否唯一
		Map<String, Object> whereMap = new HashMap<String, Object>();
		
		Map<String, Object> keyFieldMap = new HashMap<String, Object>();
		
		if (entityMap.size() > 0 && tabInfo.getHrColStrucList().size() > 0) {
			for (HrColStruc col : tabInfo.getHrColStrucList()) {

	
				if (col.getIs_pk() != null && col.getIs_pk() == 1 && isUpdate) {
					if ("DATE".equals(col.getData_type())) {
						keyFieldMap.put(col.getCol_code(), DateUtil.stringToDate(entityMap.get(col.getCol_code().toUpperCase()).toString(), "yyyy-MM-dd"));
					} else {
						if(col.getCol_code().equals("DEPT_ID")) {
						
							keyFieldMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()).toString().split("@")[0]);
						}else {
							
							keyFieldMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
						}
						//keyFieldMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
					}
					reMap.put(KEYFIELDMAP, keyFieldMap);
					continue;
				}
				
				if ("GROUP_ID".equals(col.getCol_code().toUpperCase())) {
					reMap.put(col.getCol_code(), SessionManager.getGroupId());
					keyFieldMap.put(col.getCol_code(), SessionManager.getGroupId());
					whereMap.put(col.getCol_code(), SessionManager.getGroupId());
					continue;
				}
				
				if ("HOS_ID".equals(col.getCol_code().toUpperCase())) {
					reMap.put(col.getCol_code(), SessionManager.getHosId());
					keyFieldMap.put(col.getCol_code(), SessionManager.getHosId());
					whereMap.put(col.getCol_code(), SessionManager.getHosId());
					continue;
				}
				
				if ("COPY_CODE".equals(col.getCol_code().toUpperCase())) {
					reMap.put(col.getCol_code(), SessionManager.getCopyCode());
					keyFieldMap.put(col.getCol_code(), SessionManager.getCopyCode());
					whereMap.put(col.getCol_code(), SessionManager.getCopyCode());
					continue;
				}
				
				// 处理自动生成字段
				if (col.getHrStoreColSet().getIs_auto() != null && col.getHrStoreColSet().getIs_auto() == 1) {
					// reMap.put(col.getCol_code(), IdGen.uuid());
					try {
						int id = baseCRUDMapper.querySeqByTabCode(col.getTab_code().toUpperCase());
						reMap.put(col.getCol_code(), id);
					} catch (Exception e) {
						logger.error(e.getMessage());
						throw new SysException("缺少序列" + col.getTab_code().toUpperCase() + "_SEQ");
					}
					
					continue;
				}
				
				is_change : if (col.getHrStoreColSet().getIs_change() != null && col.getHrStoreColSet().getIs_change() == 1) {
					
					if(entityMap.get(col.getCol_code().toUpperCase()) == null){break is_change;}
					
					String[] strArr = entityMap.get(col.getCol_code().toUpperCase()).toString().split("@");
					
					entityMap.put(col.getCol_code().toUpperCase(), strArr[0]);
					
					reMap.put(col.getCol_code().toUpperCase(), strArr[0]);
					
					reMap.put(col.getHrStoreColSet().getChange_col_code(), strArr[1]);
					
				}else{
					
					if(entityMap.get(col.getCol_code().toUpperCase()) == null){break is_change;}
						
					String col_value = entityMap.get(col.getCol_code().toUpperCase()).toString();
						
					if(col_value.indexOf("@") == -1){break is_change;}
					
					entityMap.put(col.getCol_code().toUpperCase(), col_value.split("@")[0]);

				}
				
				// 验证参数
				// 验证不可为空
				if ((col.getIs_pk() != null && col.getIs_pk() == 1) || (col.getIs_m() != null && col.getIs_m() == 1)) {
					if (entityMap.get(col.getCol_code().toUpperCase()) == null
							|| StringUtils.isBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
						throw new SysException(col.getCol_name() + "不能为空。");
					}
				}
				
				// 验证数据类型
				if (entityMap.get(col.getCol_code().toUpperCase()) != null && StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
					if ("NUMBER".equals(col.getData_type()) && !RegExpValidatorUtils.IsNumber(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
						throw new SysException(col.getCol_name() + "必须是数字格式。");
					}
					
					if ("DATE".equals(col.getData_type()) && !RegExpValidatorUtils.isDate(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
						throw new SysException(col.getCol_name() + "必须是日期格式(yyyy-MM-dd)。");
					}
				}
				
				// 验证是否唯一
				
				if (col.getIs_pk() != null && col.getIs_pk() == 1) {
					if ("DATE".equals(col.getData_type())) {
						whereMap.put(col.getCol_code(), DateUtil.stringToDate(entityMap.get(col.getCol_code().toUpperCase()).toString(), "yyyy-MM-dd"));
					} else {
						whereMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
					}
				}
				
				if (entityMap.containsKey(col.getCol_code().toUpperCase()) && entityMap.get(col.getCol_code().toUpperCase()) != null
						&& !"undefined".equals(entityMap.get(col.getCol_code().toUpperCase()).toString())) {

					if ("DATE".equalsIgnoreCase(col.getData_type())
							|| (col.getHrStoreColSet().getCom_type_code() != null && COM_TYPE_DATE.equals(col.getHrStoreColSet().getCom_type_code()))
							&& entityMap.get(col.getCol_code().toUpperCase()) != null
							&& StringUtils.isNotBlank(entityMap.get(col.getCol_code().toUpperCase()).toString())) {
						// 处理日期类型
						if (!"".equals(entityMap.get(col.getCol_code()))) {
							reMap.put(col.getCol_code(), DateUtil.stringToDate(String.valueOf(entityMap.get(col.getCol_code().toUpperCase())), "yyyy-MM-dd"));
						}
						
					} else {
						reMap.put(col.getCol_code(), entityMap.get(col.getCol_code().toUpperCase()));
					}
					
				}
			}
		}
		
		if (!isUpdate) {
			Map<String, Map<String, Object>> tableMapB = new HashMap<String, Map<String, Object>>();
			tableMapB.put(tab_code, whereMap);
			List<Map<String, Object>> list = baseCRUDMapper.queryInfo(tableMapB);
			if (list.size() > 0) {
				throw new SysException("数据已存在。");
			}
		}
		
		return reMap;
		
		}catch(Exception e){
			logger.error(e.getMessage(),e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String queryListByCustomSql(String tab_code, Map<String, Object> entityMap) throws DataAccessException {

		Map<String, String> tableMap = new HashMap<String, String>();
		tableMap.put(tab_code, entityMap.get("param").toString());
		
		List<Map<String, Object>> list = baseCRUDMapper.queryListByCustomSql(tableMap);
		return ChdJson.toJson(list);
	}
	
	@Override
	public String[] queryChildDeptById(Map<String, Object> entityMap) throws DataAccessException {
		return hrCommonMapper.queryChildDeptById(entityMap);
	}
	
	@Override
	public List<Map<String, Object>> queryHrStoreTabStrucByStoreType(Map<String, Object> entityMap) throws DataAccessException {
		return hrCommonMapper.queryHrStoreTabStrucByStoreType(entityMap);
	}
	
	
	
	
	

	
	@SuppressWarnings("unchecked")
	private List<Map<String, Object>> buildQueryHrStatisticCustomData(Map<String, Object> entityMap) {
		SQL sql = new SQL();
		
		List<HrColumn> tabColList = (List<HrColumn>) entityMap.get("tabColList");
		
		if (tabColList == null || tabColList.size() < 1) {
			return null;
		}
		
		// 主表表名
		String mainTabName = MAIN_TABLE_NAME;
		
		// 子表
		List<String> childTabList = new ArrayList<String>();
		
		for (HrColumn col : tabColList) {
			if (!childTabList.contains(col.getTab_code())) {
				childTabList.add(col.getTab_code());
			}
		}
		
		// 列名
		StringBuilder columns = new StringBuilder();
		
		columns.append(mainTabName + ".EMP_NAME,");
		
		// where条件
		Condition condition = Condition.create();
		condition.isWhere(true);
		
		
		// 拼接JOIN
		for (String tab : childTabList) {
			if (!mainTabName.equalsIgnoreCase(tab)) {
				
				StringBuilder join = new StringBuilder();
				join.append(tab + " ON " + mainTabName + ".EMP_ID = " + tab + ".EMP_ID ");
				
				List<String> columnList = createTablesMapper.findTableColumnByTableName(tab);
				
				if (columnList.contains("GROUP_ID")) {
					join.append("AND " + tab + ".GROUP_ID= " + SessionManager.getGroupId() + " ");
				}
				if (columnList.contains("HOS_ID")) {
					join.append("AND " + tab + ".HOS_ID= " + SessionManager.getHosId() + " ");
				}
				
				sql.LEFT_OUTER_JOIN(join.toString());
			}
			
		}
		
		if (tabColList != null && tabColList.size() > 0) {
			int idx = 1;
			for (HrColumn col : tabColList) {
				
				// 拼接列
				if (col.getIs_change() != null && col.getIs_change() == 1) {
					String c = col.getChange_col_code();
					columns.append(col.getTab_code() + "." + col.getCol_code().toUpperCase() + "||'@'||" + col.getTab_code() + "." + c.toUpperCase() + " AS "
							+ col.getCol_code().toUpperCase());
				} else {
					columns.append(col.getTab_code() + "." + col.getCol_code().toUpperCase());
				}
				columns.append(",");
				
				// 拼列JOIN
				if (col.getCom_type_code() != null && COM_TYPE_SELECT.equals(col.getCom_type_code()) && StringUtils.isNotBlank(col.getField_tab_code())) {
					entityMap.put("field_tab_code", col.getField_tab_code());
					HrFiiedTabStruc hrFiiedTabStruc = hrFiiedTabStrucMapper.queryByCode(entityMap);
					
					if (hrFiiedTabStruc != null) {
						if (hrFiiedTabStruc.getIs_cite() != null && hrFiiedTabStruc.getIs_cite() == 1) {
							
							columns.append("FD" + idx + ".FIELD_COL_NAME " + col.getCol_code().toUpperCase() + "_TEXT");
							columns.append(",");
							
							HrFiiedView hrFiiedView = hrFiiedViewMapper.queryByCode(entityMap);
							String fiiedTabSql = (String) hrFiiedView.getCite_sql();
							ParameterHandler parameterHandler = new ParameterHandler();
							fiiedTabSql = parameterHandler.setParameters(fiiedTabSql);
							
							if (col.getIs_change() != null && col.getIs_change() == 1) {
								String c = col.getChange_col_code();
								sql.LEFT_OUTER_JOIN("(" + fiiedTabSql + ") FD" + idx + " ON FD" + idx + ".FIELD_COL_CODE = to_char(" + col.getTab_code() + "."
										+ col.getCol_code().toUpperCase() + "||'@'||" + col.getTab_code() + "." + c.toUpperCase() + ")");
							} else {
								sql.LEFT_OUTER_JOIN("(" + fiiedTabSql + ") FD" + idx + " ON FD" + idx + ".FIELD_COL_CODE = to_char(" + col.getTab_code() + "."
										+ col.getCol_code().toUpperCase() + ")");
							}
							
						} else {
							columns.append("FD" + idx + ".FIELD_COL_NAME " + col.getCol_code().toUpperCase() + "_TEXT");
							columns.append(",");
							sql.LEFT_OUTER_JOIN("HR_FIIED_DATA FD" + idx + " ON FD" + idx + ".GROUP_ID = " + SessionManager.getGroupId() + " AND FD" + idx
									+ ".HOS_ID = " + SessionManager.getHosId() + /*
																				 * " AND FD"
																				 * +
																				 * idx
																				 * +
																				 * ".COPY_CODE = '"
																				 * +
																				 * SessionManager
																				 * .
																				 * getCopyCode
																				 * (
																				 * )
																				 * +
																				 */" AND FD" + idx + ".FIELD_TAB_CODE = '" + col.getField_tab_code() + "' AND "
									+ "FD" + idx + ".FIELD_COL_CODE = to_char(" + col.getTab_code() + "." + col.getCol_code().toUpperCase() + ")");
						}
						
					}
					
				}
				
				idx++;
			}
		}
		
		StringBuilder whereStr = new StringBuilder();
		if (entityMap.containsKey("conditionList")) {
			List<Map<String, Object>> conditionList = (List<Map<String, Object>>) entityMap.get("conditionList");
			for (int i = 0; i < conditionList.size(); i++) {
				String l_bracket = String.valueOf(conditionList.get(i).get("l_bracket"));// 左括号
				String tab_code = String.valueOf(conditionList.get(i).get("tab_code"));// 表名
				String col_code = String.valueOf(conditionList.get(i).get("col_code"));// 列名
				String con_sign_code = String.valueOf(conditionList.get(i).get("con_sign_code"));// 条件连接符
				String con_sign_name = String.valueOf(conditionList.get(i).get("con_sign_name"));// 条件连接符
				String col_value = String.valueOf(conditionList.get(i).get("col_value"));// 值
				String r_bracket = String.valueOf(conditionList.get(i).get("r_bracket"));// 右括号
				// String join_sign_code =
				// String.valueOf(conditionList.get(i).get("join_sign_code"));//
				// 连接符
				String join_sign_name = String.valueOf(conditionList.get(i).get("join_sign_name"));// 连接符
				whereStr.append(l_bracket);
				whereStr.append(tab_code + "." + col_code);
				whereStr.append(" " + con_sign_name + " ");
				if ("17".equalsIgnoreCase(con_sign_code) || "18".equalsIgnoreCase(con_sign_code)) {
					whereStr.append("'%" + col_value + "%'");
				} else if ("19".equalsIgnoreCase(con_sign_code) || "20".equalsIgnoreCase(con_sign_code)) {
					if (col_value.contains(",")) {
						String[] vals = col_value.split(",");
						whereStr.append("(");
						for (int j = 0; j < vals.length; j++) {
							whereStr.append("'" + vals[j] + "'");
							if (j < vals.length - 1) {
								whereStr.append(",");
							}
						}
						whereStr.append(")");
					} else if (col_value.contains("，")) {
						String[] vals = col_value.split("，");
						whereStr.append("(");
						for (int j = 0; j < vals.length; j++) {
							whereStr.append("'" + vals[j] + "'");
							if (j < vals.length - 1) {
								whereStr.append(",");
							}
						}
						whereStr.append(")");
					} else {
						whereStr.append("('" + col_value + "')");
					}
				} else {
					whereStr.append("'" + col_value + "'");
				}
				
				whereStr.append(r_bracket);
				if (conditionList.size() > 1 && i < conditionList.size() - 1) {
					whereStr.append(" " + join_sign_name + " ");
				}
			}
		}
		
		// 拼接Where
		List<HrColumn> queSetList = hrCommonMapper.queryColumnsByStatisticQueSet(entityMap);
		if (queSetList != null && queSetList.size() > 0 && entityMap.get("param") != null) {
			Parameter param = JSONObject.parseObject(entityMap.get("param").toString(), Parameter.class);
			for (HrColumn col : queSetList) {
				if (col.getIs_view() != null && col.getIs_view() == 1) {
					String queKey = col.getTab_code() + "." + col.getCol_code();
					if (col.getIs_change() != null && col.getIs_change() == 1) {
						queKey += " ||'@'|| " + col.getTab_code() + "." + col.getChange_col_code();
					}
					
					Object value = param.get(col.getCol_code().toUpperCase());
					
					// 区间
					if (col.getIs_section() != null && col.getIs_section() == 1) {
						if ("DATE".equals(col.getData_type())) {
							if (value != null && StringUtils.isNotBlank(value.toString()) && value.toString().contains(",")) {
								String[] valArr = value.toString().split(",");
								condition.ge(queKey, valArr[0], col.getData_type());
								condition.le(queKey, valArr[1], col.getData_type());
							}
						} else {
							if (param.get(col.getCol_code().toUpperCase() + "_BEG") != null
									&& StringUtils.isNotBlank(param.get(col.getCol_code().toUpperCase() + "_BEG").toString())) {
								condition.ge(queKey, param.get(col.getCol_code().toUpperCase() + "_BEG"));
							}
							if (param.get(col.getCol_code().toUpperCase() + "_END") != null
									&& StringUtils.isNotBlank(param.get(col.getCol_code().toUpperCase() + "_END").toString())) {
								condition.le(queKey, param.get(col.getCol_code().toUpperCase() + "_END"));
							}
						}
						
						continue;
						
					}
					if (value != null && StringUtils.isNotBlank(value.toString())) {
						if (col.getCon_sign_code() != null && StringUtils.isNotBlank(col.getCon_sign_code())) {
							// 11:= 12:> 13: >= 14:< 15:<= 16:<> 17:Like 18:NOT
							// LIKE 19:IN 20:NOT IN
							switch (Integer.parseInt(col.getCon_sign_code())) {
								case 11:
									condition.eq(queKey, value, col.getData_type());
									break;
								case 12:
									condition.gt(queKey, value, col.getData_type());
									break;
								case 13:
									condition.ge(queKey, value, col.getData_type());
									break;
								case 14:
									condition.lt(queKey, value, col.getData_type());
									break;
								case 15:
									condition.le(queKey, value, col.getData_type());
									break;
								case 16:
									condition.ne(queKey, value, col.getData_type());
									break;
								case 17:
									condition.like(queKey, value.toString());
									break;
								case 18:
									condition.notLike(queKey, value.toString());
									break;
								case 19:
									condition.in(queKey, value.toString());
									break;
								case 20:
									condition.notIn(queKey, value.toString());
									break;
								default:
									condition.eq(queKey, value, col.getData_type());
									break;
							}
						} else {
							condition.eq(queKey, value, col.getData_type());
						}
					}
					
				}
			}
		}
		
		sql.SELECT(columns.deleteCharAt(columns.length() - 1).toString());
		sql.FROM(mainTabName);
		
		if (condition.getSqlSegment() != null) {
			sql.WHERE(condition.getSqlSegment());
		}
		if (whereStr.length() > 0) {
			if (condition.getSqlSegment() != null) {
				sql.AND();
			}
			sql.WHERE(whereStr.toString());
		}
		
		return baseCRUDMapper.queryStatisticCustom(sql.toString());
	}
	
	public String selectStatisticCustom(String sql) {
		return sql;
	}
	
	@Override
	public List<Map<String, Object>> queryHrUserPermByUserId(Map<String, Object> entityMap) throws DataAccessException {
		return hrCommonMapper.queryHrUserPermByUserId(entityMap);
	}
	
	@Override
	public List<Map<String, Object>> queryHrRolePermByRoleId(Map<String, Object> entityMap) throws DataAccessException {
		return hrCommonMapper.queryHrRolePermByRoleId(entityMap);
	}
	
	@Override
	public String queryHrStatisticCustomQueForm(Map<String, Object> mapVo, int i) throws DataAccessException {
		mapVo.put("is_view", 1);
		List<HrColumn> hrColStrucList = hrCommonMapper.queryColumnsByStatisticQueSet(mapVo);
		return queryForm(mapVo, i, hrColStrucList);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String importFromDate(Map<String, Object> mapVo) throws DataAccessException {

//		try {
//			
//			String store_type_code = mapVo.get("store_type_code").toString();
//			
//			List<Map<String, Object>> addList = new ArrayList<Map<String, Object>>();//新增数据
//			
//			List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();//修改数据
//			
//			Map<String, Object> dataMap = (Map<String, Object>) JSONArray.parse(mapVo.get("para").toString());
//			
//			/*{"column":[
//				{"display":"职工编码(必填)","is_pk":0,"name":"EMP_CODE","required":"true","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"职工名称(必填)","name":"EMP_NAME","required":"true","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"change_col_code":"DEPT_NO","display":"部门名称(编码)(必填)","field_tab_code":"SYS_DEPT","is_change":1,"is_cite":1,"name":"DEPT_ID","required":"true","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"分类(编码)(必填)","field_tab_code":"DIC_EMP_TYPE","is_cite":1,"name":"KIND_CODE","required":"true","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"性别(编码)","field_tab_code":"DIC_SEX","is_cite":0,"name":"SEX_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"民族(编码)","field_tab_code":"DIC_NATION","is_cite":0,"name":"NATION_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"婚姻(编码)","field_tab_code":"DIC_MARRIAGE","is_cite":0,"name":"MARRIAGE_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"政治面貌(编码)","field_tab_code":"DIC_POLITICAL","is_cite":0,"name":"POLITICAL_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"健康状况(编码)","field_tab_code":"DIC_HEALTH","is_cite":0,"is_pk":0,"name":"HEALTH_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"身份证号","name":"ID_CARD","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"出生日期","name":"BIRTHDAY","required":"false","tab_code":"HOS_EMP","type":"03","width":"200"},
//				{"display":"年龄","name":"AGE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"工作电话","name":"PHONE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"手机","name":"MOBILE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"EMAIL","name":"EMAIL","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"从事专业","name":"PROFESSION","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"原单位","name":"ORI_HOS","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"户口性质(编码)","field_tab_code":"DIC_RESIDENCE","is_cite":0,"name":"RESIDENCE_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"虚拟网络","name":"VPN","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"出生地","name":"BIRTHPLACE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"住址","name":"ADDRESS","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"参加工作时间","name":"WORKTIME","required":"false","tab_code":"HOS_EMP","type":"03","width":"200"},
//				{"display":"工龄","name":"WORKAGE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"进院时间","name":"HOSTIME","required":"false","tab_code":"HOS_EMP","type":"03","width":"200"},
//				{"display":"院龄","name":"HOSKAGE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"个人身份","name":"PID_CODE","required":"false","tab_code":"HOS_EMP","type":"02","width":"200"},
//				{"display":"员工照片","name":"PHOTO","required":"false","tab_code":"HOS_EMP","type":"04","width":"200"},
//				{"display":"排序号","name":"SORT_CODE","required":"false","tab_code":"HOS_EMP","width":"200"},
//				{"display":"拼音码","name":"SPELL_CODE","required":"false","tab_code":"HOS_EMP","width":"200"},
//				{"display":"五笔码","name":"WBX_CODE","required":"false","tab_code":"HOS_EMP","width":"200"},
//				{"display":"是否停用(编码)(必填)","field_tab_code":"DIC_YN","is_cite":0,"name":"IS_STOP","required":"true","tab_code":"HOS_EMP","type":"01","width":"200"},
//				{"display":"备注","name":"NOTE","required":"false","tab_code":"HOS_EMP","width":"200"},
//				{"display":"医护属性(编码)","field_tab_code":"HOS_EMP_YH","is_cite":0,"name":"YH_CODE","required":"false","tab_code":"HOS_EMP","type":"01","width":"200"}
//				],"isUpdate":false}*/
//			@SuppressWarnings("rawtypes")
//			List<Map> dataList = ChdJson.fromJsonAsList(Map.class, dataMap.get("column").toString());
//			
//			// 获取所有下拉框的编码
//			for (Map<String,Object> map : dataList) {
//				if (map.get("type") != null && map.get("type").toString().equals("01")) {
//					if (map.get("is_cite") != null && map.get("is_cite").toString().equals("1")) {
//						view_code.append("'");
//						view_code.append(map.get("name").toString() + "',");
//					} else {
//						col_code.append("'");
//						col_code.append(map.get("name").toString() + "',");
//					}
//				}
//			}
//			
//			
//		}catch (Exception e) {
//			throw new SysException(e.getMessage());
//		}
	
	
	
	
		try {
			
			
		int countNum = 2;
		
		String userId = SessionManager.getUserId();
		
		LinkedHashMap<String,Map<String,Object>> hrFiiedMap = new LinkedHashMap<String,Map<String,Object>>();

		
		
		
		List<String> dataList= new ArrayList<String>();
		
		List<Map<String, Object>> updateList = new ArrayList<Map<String, Object>>();//修改数据
		
		List<Map<String, Object>> saveList = new ArrayList<Map<String, Object>>();//修改数据
		
		
		
		
		
		Map<String, Object> errMap = new HashMap<String, Object>();
	
		// 查询数据表条件查询出所有下拉框的名称及编码
		Map<String, Object> colStrucWhere = new HashMap<String, Object>();
		
		String store_type_code = mapVo.get("store_type_code").toString();
		
		String tab_code= mapVo.get("tab_code").toString();
		
	
		
		
		//new Map查询导入时对应数据信息
		Map<String, Object> entityMap=new HashMap<String,Object>();
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		//
		entityMap.put("tab_code", tab_code);
		entityMap.put("store_type_code", store_type_code);
		entityMap.put("user_id", SessionManager.getUserId());
		
		// 添加编码规则判断
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("proj_code", "HOS_EMP");
		entityMap.put("mod_code", "00");
		//查询编码规则
	    Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);
		
		
		
		Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
		
		entityMap.put("copy_code", SessionManager.getCopyCode());
		//List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMapOrderly(content,1);
	    // 提取前台数据
		List<Map<String, List<String>>> liData = ReadFiles.readFiles(mapVo);
		if(liData==null || liData.size()==0){
			return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
		}
		
		

		
	/**
	* 查询数据表
	*/
	List<Map<String, Object>> hrStoreColSet = hrCommonMapper.queryHrStoreColSet(entityMap);
		
		if (hrStoreColSet == null || hrStoreColSet.size() == 0) {
			
			throw new SysException("档案库数据表未构建 或者 档案库数据列未设置!");
			
		}
		
		
		
		/**
		 * 2、取代码表
		 */
		List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
		
		for (Map<String, Object> tmpHrFiiedMap : hrFiiedList) {
			
			hrFiiedMap.put(tmpHrFiiedMap.get("FIELD_TAB_CODE").toString(), tmpHrFiiedMap);
			
		}

		int emp_id = hrCommonMapper.queryHosEmpMaxId(mapVo);
	
		
		for (Map<String,List<String>> map : liData) {
			
			//查询SQL
			StringBuffer selectSql= new StringBuffer();
			
			selectSql.append(" select * from " + tab_code +" where ");
			
			//新增SQL
			StringBuffer insertSql= new StringBuffer();
			
			insertSql.append(" insert into " + tab_code + " ( ");
			
			//新增SQL字段
			
			StringBuffer codeSql= new StringBuffer();
			
			//修改SQL
			StringBuffer updateSql= new StringBuffer();
			
		
			updateSql.append("update " + tab_code+" set ");
			
		/*	//修改时候删除hos_emp_dict表SQL
			StringBuffer deleteSql= new StringBuffer();
			
		
			deleteSql.append("delete from hos_emp_dict where ");*/
			
			//主键Map
			Map<String, Object> keyMap= new  HashMap<String, Object>();
			//人员Map
			Map<String, Object> empMap= new  HashMap<String, Object>();
			//用于存储传的数据值
			Map<String,Object> saveMap = new HashMap<String,Object>();
			
			String colCodeString ="HOS_EMP";
			
			StringBuffer isPk = new StringBuffer();//验证重复数据
			
	    if (tab_code.equals("HOS_EMP")) {
				
				    if (null != map.get("EMP_CODE").get(1)) {
					
					int max_level = Integer.parseInt(rules.get("max_level").toString());
					String proj_code = map.get("EMP_CODE").get(1).toString();
					String empCode="";
					if(max_level>0){
						
						String rules_v = rules.get("rules_view").toString();
						
						String s_view = Strings.removeFirst(rules_v);
						
						
						int code=proj_code.length();
						if(rules_level_length!=null){
							//当第一级为0时 不验证规则
							int sLevel=Integer.parseInt(rules_level_length.get(1).toString());
							if(!rules_level_length.get(1).toString().equals("0")){
								
								if(code > sLevel){
									return "{\"error\":\"编码"+proj_code+"不符合要求,请重新添加.编码规则长度：" + s_view + "\"}";
								}
								String eCode="";
								for (int i = 0; i < sLevel-code; i++) {
									eCode+="0";
								}
								empCode=eCode+proj_code;
								//
								map.get("EMP_CODE").set(1, empCode);
							}
						}
					}
					
				}
				
				if (errMap.containsKey(map.get("EMP_CODE").get(1))) {
					return "{\"error\":\"导入失败！" + map.get("EMP_CODE").get(0) + "编码重复\",\"state\":\"false\"}";
				}
				if (map.get("EMP_CODE").get(1) == null || map.get("EMP_CODE").get(1).equals("")) {
					continue;
					//return "{\"error\":\"导入失败！ " + map.get("EMP_CODE").get(0) + "编码为空\",\"state\":\"false\"}";
				}
				if (map.get("DEPT_ID").get(1) == null || map.get("DEPT_ID").get(1).equals("")) {
				
					return "{\"error\":\"导入失败！ " + map.get("DEPT_ID").get(0) + "编码为空\",\"state\":\"false\"}";
				}
		
				}else{
					//过滤空行
					if("".equals(map.get("EMP_ID").get(1))||map.get("EMP_ID").get(1)==null){
						continue;
					}
					
				} 


		  for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSet) {
			  
			  Map<String, Map<String, Object>> selectMap = new HashMap<String, Map<String, Object>>();
			
			  Map<String, Object> dataTypeMap= new HashMap<String, Object>();
			  
			  dataTypeMap.put("DATA_TYPE", tmpHrColStrucSetMap.get("DATA_TYPE"));
			  
			 if(tmpHrColStrucSetMap.get("DATA_TYPE").equals("DATE")){
				 
				 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				 String regex  = "[\\/OADate\\()\\/]";
				 
	 			 String dateStr=null;
	 			 
	 			 if(map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1)!=null&&map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1).toString().indexOf("OADate")==1){
    				
	 				 String d=map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1).toString().replaceAll(regex , "");
	 				 
	 				 Date date=	fromDoubleToDateTime(Double.parseDouble(d));
		    			
    				 
	    			 dateStr = df.format(date);
					
	    			 map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).set(1, dateStr);
    			 }
	 			 
			 } 
			 
			  
			  if(tab_code.equals("HOS_EMP")&&tmpHrColStrucSetMap.get("COL_CODE").toString().equals("EMP_ID")){
					continue;
				}
			  
			  if (tmpHrColStrucSetMap.get("COL_CODE").toString().toString().equals("EMP_NAME")) {
				  
					saveMap.put("SPELL_CODE", StringTool.toPinyinShouZiMu(map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1)));
					saveMap.put("WBX_CODE", StringTool.toWuBi(map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1)));
					
					empMap.put("SPELL_CODE", StringTool.toPinyinShouZiMu(map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1)));
					empMap.put("WBX_CODE", StringTool.toWuBi(map.get(tmpHrColStrucSetMap.get("COL_CODE").toString()).get(1)));
			  }
			 
					if(!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID") &&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")&&tmpHrColStrucSetMap.get("FIELD_TAB_CODE")!=null){
						
						colCodeString=tmpHrColStrucSetMap.get("FIELD_TAB_CODE").toString();
					}
					
			
					if(!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")){
					
						//查询引用的数据
						
					if(hrFiiedMap.get(colCodeString).get("CITE_SQL")!=null){
						
						String tmpSQL= replaceConstant(hrFiiedMap.get(colCodeString).get("CITE_SQL").toString(),entityMap);
						
						List<Map<String, Object>> list = hrFiiedViewMapper.queryDictCustomSql(tmpSQL);
						
						Map<String, Object> Data = new HashMap<String, Object>();
						
						
						for (Map<String, Object> map1 : list) {
							
							if (selectMap.containsKey(map1.get("FIELD_COL_NAME"))) {
								
								Data.put(map1.get("FIELD_COL_NAME").toString(), "#");
								
							}
							if (map1.get("CODE") == null) {
								
								return "{\"error\":\"导入失败！请维护代码表【" + tmpHrColStrucSetMap.get("COL_NAME").toString() + "】的代码引用SQL并加入(code)编码列\",\"state\":\"false\"}";
							
							}
							
							Data.put(map1.get("FIELD_COL_NAME").toString(), map1.get("FIELD_COL_CODE"));
							
							Data.put(map1.get("CODE").toString(), map1.get("FIELD_COL_CODE"));
							
							selectMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), Data);
							
						}
						
					}else{
						
						//查询内置数据
						
						colStrucWhere.put("group_id", SessionManager.getGroupId());
						colStrucWhere.put("hos_id", SessionManager.getHosId());
						colStrucWhere.put("copy_code", SessionManager.getCopyCode());
						colStrucWhere.put("col_code", tmpHrColStrucSetMap.get("COL_CODE"));
						colStrucWhere.put("store_type_code", store_type_code);
						List<Map<String, Object>> selectDataList = hrCommonMapper.querySelectData(colStrucWhere);
						
						// 查询所有下拉框编码及名称
						Map<String, Object> Data = new HashMap<String, Object>();
						for (Map<String, Object> map2 : selectDataList) {
							Data.put(map2.get("FIELD_COL_NAME").toString(), map2.get("FIELD_COL_CODE"));
							Data.put(map2.get("FIELD_COL_CODE").toString(), map2.get("FIELD_COL_CODE"));
							selectMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), Data);
							
						}
					};
			
					}
				
			
				  //判断主键用来判断是否为修改          预留根据是否唯一IS_UNIQUE
				 if(tmpHrColStrucSetMap.get("IS_PK")!=null&&tmpHrColStrucSetMap.get("IS_PK").toString().equals("1")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID")
						 &&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")) {
					
					 if( map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)==null||map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1).equals("")){
						
						return "{\"error\":\"导入失败！" +map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(0)+" "+tmpHrColStrucSetMap.get("COL_NAME").toString()  + "不能为空\",\"state\":\"false\"}";
					
					 }else{
						 //有下拉框的数据
						 if(tmpHrColStrucSetMap.get("FIELD_TAB_CODE")!=null&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")){
					       
							 dataTypeMap.put("COL_VALUE", selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)));		
							
							 if(selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1))==null){
									
								 return "{\"error\":\"导入失败！请维护第" +countNum+"行"+tmpHrColStrucSetMap.get("COL_NAME")+" "+map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)+ "在代码表中的数据\",\"state\":\"false\"}";
 
							 }else{
								 
					         keyMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1))); 
							 saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), dataTypeStr(dataTypeMap)); 
							 empMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1))); 
							
							 }
						 }else{
							 //普通数据
							 if(!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")){
								    dataTypeMap.put("COL_VALUE", map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1));		

								 keyMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),dataTypeStr(dataTypeMap));
								 empMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),dataTypeStr(dataTypeMap));	

								 saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), dataTypeStr(dataTypeMap)); 

							 }
								
						
						 }
						 
						 
					 }
					
					 
				}else if(tmpHrColStrucSetMap.get("COL_CODE").toString().equals("EMP_CODE")){
					
					 dataTypeMap.put("COL_VALUE",map.get("EMP_CODE").get(1));
					 
					 saveMap.put("EMP_CODE", dataTypeStr(dataTypeMap));
					 
					 empMap.put("EMP_CODE", map.get("EMP_CODE").get(1));
					 //判断职工是否存在 
					 if(selectMap.size()>0&&selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1))!=null){
						 
						 //dataTypeMap.put("COL_VALUE", selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)));		
						
						 keyMap.put("EMP_ID",selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1))); 
						
						 empMap.put("EMP_ID",selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)));

						 saveMap.put("EMP_ID",selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)));  
					 }else{
						 
						 
						 keyMap.put("EMP_ID", emp_id); 
						
						 empMap.put("EMP_ID", emp_id); 
						
						 saveMap.put("EMP_ID", emp_id); 
					 }
					
				}
				else{
					
					 if(tmpHrColStrucSetMap.get("FIELD_TAB_CODE")!=null&&map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)!=null&&!map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1).equals("")){
						 
						 if(!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("")&&tmpHrColStrucSetMap.get("COL_CODE").toString()!=null){
						
							 if(selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1))==null){
								 saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), ""); 

								 return "{\"error\":\"导入失败！第	" +countNum+"行"+tmpHrColStrucSetMap.get("COL_NAME").toString()  + "在代码表中不存在,请维护数据！\",\"state\":\"false\"}";
							 
							 }else{
								 
								 dataTypeMap.put("COL_VALUE", selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)));
								 
								 empMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)));
								 
								 saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), dataTypeStr(dataTypeMap)); 
							 }
						 }
					 }else{
						 
						 if(!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("GROUP_ID")&&!tmpHrColStrucSetMap.get("COL_CODE").toString().equals("HOS_ID")){
						 
							 if(!tmpHrColStrucSetMap.get("COL_CODE").equals("DEPT_NO")){
								 
								 if ( map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)== null ||map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1).equals("")) {
										saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), "");
										continue;
									
								}else{
									
									 dataTypeMap.put("COL_VALUE", map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1));
									
									 empMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),  map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1));
									
									 saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(), dataTypeStr(dataTypeMap));
								} 
							 }
							
						 }
					 }
				}
					 if(tmpHrColStrucSetMap.get("COL_CODE").toString().equals("DEPT_ID")){
							
							if (selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)).toString().split("@").length < 2) {
								
								return "{\"error\":\"导入失败！请在代码构建功能中维护部门ID与变更ID数据,格式:【dept_id||'@'||dept_no as field_col_code 】\",\"state\":\"false\"}";
							
							}
							
							// keyMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)).toString().split("@")[0]);
							saveMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)).toString().split("@")[0]);
						   
							empMap.put(tmpHrColStrucSetMap.get("COL_CODE").toString(),selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)).toString().split("@")[0]);
		         
							if(tab_code.equals("HOS_EMP")){
		        	   
		        	   saveMap.put("DEPT_NO", selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)).toString().split("@")[1]);
					    
						empMap.put("DEPT_NO",selectMap.get(tmpHrColStrucSetMap.get("COL_CODE")).get(map.get(tmpHrColStrucSetMap.get("COL_CODE")).get(1)).toString().split("@")[1]);

		           }
							
					 } 
				 /*}*/
			
			
			 }
			keyMap.put("group_id", SessionManager.getGroupId());
			keyMap.put("hos_id", SessionManager.getHosId());
		   
		
			
		    saveMap.put("GROUP_ID", SessionManager.getGroupId());
			saveMap.put("HOS_ID", SessionManager.getHosId());
			
			empMap.put("GROUP_ID", SessionManager.getGroupId());
			empMap.put("HOS_ID", SessionManager.getHosId());
			empMap.put("USERID", SessionManager.getUserId());
		
			
			
			
			
			for (Entry<String, Object> entry : keyMap.entrySet()) {
			
				if(entry.getValue().toString()!=null&&!entry.getValue().toString().equals("")){
					isPk.append(entry.getValue().toString());
				selectSql.append(entry.getKey().toString()+" = "+entry.getValue().toString()+" and ");
				
				}
				
			}
			
			
			if (errMap.containsKey(isPk.toString())) {
				
				return "{\"error\":\"导入失败！第" +countNum+ "行数据重复\",\"state\":\"false\"}";
			}
			
			errMap.put(isPk.toString(), countNum);
			
			selectSql.delete(selectSql.length() - 4 , selectSql.length());
			
			keyMap.put("tmpQuerySql", selectSql);
			
			//查询是否存在数据用来判断是否修改
			List<Map<String, Object>> hrDataList=(List<Map<String, Object>>) hrCommonMapper.queryQuerySQL(keyMap);
			
			if(hrDataList.size()>0){
				keyMap.remove("tmpQuerySql");
				for (Entry<String, Object> entry : saveMap.entrySet()) {
					
					if(entry.getValue().toString()!=null&&!entry.getValue().toString().equals("")){
						
					updateSql.append(entry.getKey().toString()+" = "+entry.getValue().toString()+",");
					
					}
					
				}
				
				updateSql.deleteCharAt(updateSql.length() - 1);
				
				updateSql.append(" where ");
				
				for(Entry<String, Object> entry : keyMap.entrySet()){
					
					
						updateSql.append(entry.getKey().toString()+" = "+entry.getValue().toString()+" and ");
						//deleteSql.append(entry.getKey().toString()+" = "+entry.getValue().toString()+" and ");
					
				}
				//deleteSql.append("IS_STOP = 0");
				updateSql.delete(updateSql.length() - 4 , updateSql.length());
				
				 if(tab_code.equals("HOS_EMP")) {
					 
				//dataList.add(deleteSql.toString());
				
				if(map.get("IS_STOP").get(1) == null||map.get("IS_STOP").get(1).toString().equals("")){
					
					empMap.put("IS_STOP", 0);
					
				}
				   updateList.add(empMap);
				 }
				dataList.add(updateSql.toString());
		
        
				
			}else{
			    if(tab_code.equals("HOS_EMP")) {
					
				
					if (map.get("IS_STOP").get(1) == null||map.get("IS_STOP").get(1).toString().equals("")) {
						saveMap.put("IS_STOP", 0);
						empMap.put("IS_STOP", 0);
					}

					empMap.put("EMP_ID",emp_id);
					empMap.put("SORT_CODE", emp_id);
					
					saveMap.put("EMP_ID",emp_id);
					saveMap.put("SORT_CODE", emp_id);
				}
				for (Entry<String, Object> entry : saveMap.entrySet()) {
					
					if(entry.getValue().toString()!=null&&!entry.getValue().toString().equals("")){
						
						insertSql.append(entry.getKey().toString()+",");
						
						codeSql.append( entry.getValue().toString()+",");
					}
					
				}
				insertSql.deleteCharAt(insertSql.length() - 1);
				
				insertSql.append(" ) values (");
				
				codeSql.deleteCharAt(codeSql.length() - 1);
				
				codeSql.append("　)　");
				
				insertSql.append(codeSql);
				
				dataList.add(insertSql.toString());
   
				saveList.add(empMap);
				}
			countNum++;
			emp_id++;
		}
		

		//通用新增修改sql	
		if (dataList.size() > 0 ) {
					
					List<String> stringSqlList= new  ArrayList<String>();
					
					for (int i=0;  i<  dataList.size(); i++) {
						stringSqlList.add(dataList.get(i));
						
						if( i>0 && i==500 || i == dataList.size() - 1) {
							
							baseCRUDMapper.batchEmpUpate(stringSqlList);
							stringSqlList.clear();
						}
	                     
					}
					
				}
				//人员基本情况特殊处理
				if (saveList.size() > 0) {
					
					if (tab_code.equals("HOS_EMP")) {
						hrCommonMapper.addHosEmpDictBatch(userId, saveList);
					}
				}
				//人员基本情况特殊处理
				if (updateList.size() > 0) {
					
					if (tab_code.equals("HOS_EMP")) {
						
						
						hrCommonMapper.updateHosEmpDictBatch(userId, updateList);
					}
					/*for (Map<String, Object> map : updateList) {
						//修改职工的信息同步更新职工在当月工资表中的信息
						entityMap.put("copy_code", SessionManager.getCopyCode());
						
						entityMap.put("wage_flag", "1");
						
			 			String account_day = accWageCarryOverMapper.queryAccYearMonthNow(entityMap);
			 			
			 		
						
			 			entityMap.put("acc_year", account_day.substring(0,4));
						
			 			entityMap.put("acc_month", account_day.substring(5,7));
			 			
			 			entityMap.put("dept_no", map.get("DEPT_NO"));
			 			
			 			entityMap.put("dept_id", map.get("DEPT_ID"));
			 			
			 			entityMap.put("emp_id", map.get("EMP_ID"));
						
						accWagePayMapper.updateAccWagePayByEmpDict(JsonListMapUtil.toMapLower(entityMap));
					}*/
				}
					countNum--;
					int lastCountNum=countNum-1;
					return "{\"msg\":\"导入成功.成功条数:" + lastCountNum+ "\",\"state\":\"true\"}";


} catch (Exception e) {	
	logger.error(e.getMessage(), e);
    throw new SysException(e.getMessage());
    }
	}
	
	
	public Map<String, Object> verify(int countNum, Map<String, List<String>> map, List<Map> colums, Map<String, Object> empMap,
			Map<String, Map<String, Object>> selectMap) {
		Map<String, Object> mapVo = new HashMap<String, Object>();
		StringBuffer failureMsg = new StringBuffer();
		StringBuffer isPk = new StringBuffer();
		Map<String, Object> tablePkMap = new HashMap<String, Object>();
		
		Map<String, Object> repeatMap = new HashMap<String, Object>();
		try {
			String tab_code = colums.get(0).get("tab_code").toString();
			boolean falg = false;
			Map<String, Object> saveMap = new HashMap<String, Object>();
			Map<String, Object> dataType = new HashMap<String, Object>();
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("tab_code", tab_code);
			List<Map<String, Object>> hosDataType = hrCommonMapper.queryEmpDataMap(mapVo);
			
			for (Map<String, Object> dataMap : hosDataType) {
				dataType.put(dataMap.get("COL_CODE").toString(), dataMap.get("DATA_TYPE"));
			}
		
			for (Map<String, Object> colum : colums) {
				int columNum=1;
				if ((colum.get("required") != null && colum.get("required").toString().equals("true"))
						|| (colum.get("is_pk") != null && colum.get("is_pk").toString().equals("1"))) {
					if (map.get(colum.get("name")) == null || map.get(colum.get("name")).get(1) == null || map.get(colum.get("name")).get(1).equals("")) {
						saveMap.put("error", colum.get("display") + "为必填项");
						saveMap.put("rowno", map.get(colum.get("name")).get(0));
						return saveMap;
					}
				} else {
					if ( map.get(colum.get("name")).get(1) == null || map.get(colum.get("name")).get(1).equals("")) {
							saveMap.put(colum.get("name").toString(), "");
							continue;
						
					}
				}
				if (colum.get("type") != null && colum.get("type").toString().equals("01")) {
					
					if (selectMap.get(colum.get("name")) == null) {
						saveMap.put("error", "请维护代码表【" + colum.get("display") + "】数据");
						saveMap.put("rowno", map.get(colum.get("name")).get(0));
						return saveMap;
					}
					if (selectMap.get(colum.get("name")).containsKey(map.get(colum.get("name")).get(1))) {
						
						if (selectMap.get(colum.get("name")).get(map.get(colum.get("name")).get(1)).equals("#")) {
							falg = true;
							failureMsg.append("<br/> 第" + columNum + "行《" + colum.get("display") + "》列名称在系统中有重名，请使用编码导入");
						}
						if (colum.get("is_change") != null && colum.get("is_change").toString().equals("1")) {
							
							if (selectMap.get(colum.get("name")).get(map.get(colum.get("name")).get(1)).toString().split("@").length < 2) {
								falg = true;
								failureMsg.append("<br/> 请在代码构建功能中维护部门ID与变更ID数据,格式:【dept_id||'@'||dept_no as field_col_code 】");
								continue;
							}
							
							saveMap.put(colum.get("name").toString(),
									selectMap.get(colum.get("name")).get(map.get(colum.get("name")).get(1)).toString().split("@")[0]);
							saveMap.put(colum.get("change_col_code").toString(), selectMap.get(colum.get("name")).get(map.get(colum.get("name")).get(1))
									.toString().split("@")[1]);
						} else {
							saveMap.put(colum.get("name").toString(), selectMap.get(colum.get("name")).get(map.get(colum.get("name")).get(1)));
						}
					} else {
						falg = true;
						failureMsg.append("<br/> 《" + colum.get("display") + "》列，没有找到系统对应的数据");
						continue;
					}
					
				} else {
					if ((colum.get("required") != null && colum.get("required").toString().equals("true"))
							|| (colum.get("is_pk") != null && colum.get("is_pk").toString().equals("1"))) {
						if (map.get(colum.get("name")) == null || map.get(colum.get("name")).get(1) == null || map.get(colum.get("name")).get(1).equals("")) {
							saveMap.put("error", colum.get("display") + "为必填项");
							
							saveMap.put("rowno", map.get(colum.get("name")).get(0));
							return saveMap;
						}
					}
					if (map.get(colum.get("name")) != null && map.get(colum.get("name")).get(1) != null) {
						
					/*	if (dataType.get(colum.get("name").toString()).equals("DATE")) {
							if(!RegExpValidatorUtils.isDate(map.get(colum.get("name")).get(1))){
								System.out.println(map.get(colum.get("name")).get(1));
								falg = true;
								failureMsg.append("<br/> 《" + colum.get("display") + "》列为EXCEL日期格式 ，请修改为文本格式的日期(yyyy-MM-dd)");
								continue;
							}
							
							
						}*/
						if (!colum.get("name").toString().equals("EMP_ID")) {
							if (dataType.get(colum.get("name").toString()).equals("NUMBER")
									&& !RegExpValidatorUtils.IsNumber(map.get(colum.get("name")).get(1))) {
								falg = true;
								failureMsg.append("<br/> 《" + colum.get("display") + "》列，必须为数字格式");
							}
						}
						
						if (dataType.get(colum.get("name").toString()).equals("DATE") ) {
							
							
							 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			    			 boolean flag = false ;
			    			 String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
			    			 String dateStr=null;
			    			 /* Object obj = map.get(colum.get("name")).get(1);
			    			if (obj instanceof Integer) {
			 					
			 				}
			 				if (obj instanceof String) {
			 					 String reg1 = "[\\d]{4}[\\d]{2}[\\d]{2}";
			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg1);
			    				 dateStr=map.get(colum.get("name")).get(1).toString();
			 				}
			 				if (obj instanceof Date) {
			 				
			 				}*/
			    			 
			    			 if(map.get(colum.get("name")).get(1) .toString().indexOf("OADate")==1){
			    				 Date date=	fromDoubleToDateTime(Double.parseDouble(map.get(colum.get("name")).get(1) .toString().replace("OADate", "").replace("()", "").replace("/", "").replace("(", "").replace(")", "")));
					    			
			    				 
				    			 dateStr = df.format(date);
								
								flag = dateStr.matches(reg);
			    			 }else if (map.get(colum.get("name")).get(1).length()==8){
			    				//处理20190101类型
			    				 String reg1 = "[\\d]{4}[\\d]{2}[\\d]{2}";
			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg1);
			    				 dateStr=map.get(colum.get("name")).get(1).toString();
			    			 }

//			    			 else if (map.get(colum.get("name")).get(1).length()==7){
//			    				//处理2019101类型
//			    				 String reg1 = "[\\d]{4}[\\d]{1}[\\d]{2}";
//			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg1);
//			    				 dateStr=map.get(colum.get("name")).get(1).toString();
//			    			 }else if (map.get(colum.get("name")).get(1).length()==6){
//			    				//处理201911类型
//			    				 String reg1 = "[\\d]{4}[\\d]{1}[\\d]{1}";
//			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg1);
//			    				 dateStr=map.get(colum.get("name")).get(1).toString();
//			    			 }
			    			 else{
			    				 //处理文本格式2019-01-01
			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg);
			    				 dateStr=map.get(colum.get("name")).get(1).toString();
			    			 }
			    			 if(flag == false){//判断日期格式是否正确
									failureMsg.append("第"+columNum+"行"+"日期格式错误 ");
									continue;
								}else{
									// 处理日期类型
									saveMap.put(colum.get("name").toString(),  DateUtil.stringToDate(dateStr,"yyyy-MM-dd") );
						}
			    			 
							
						} else {
							saveMap.put(colum.get("name").toString(), map.get(colum.get("name")).get(1));
							if (colum.get("name").toString().equals("EMP_NAME")) {
								saveMap.put("SPELL_CODE", StringTool.toPinyinShouZiMu(map.get(colum.get("name")).get(1)));
								saveMap.put("WBX_CODE", StringTool.toWuBi(map.get(colum.get("name")).get(1)));
							}
							if (!tab_code.equals("HOS_EMP") && colum.get("name").equals("EMP_ID")) {
								if (empMap.get(map.get("EMP_ID").get(1)) == null) {
									falg = true;
									failureMsg.append("<br/> 第" + columNum + "行《" + colum.get("display") + "》列员工名称在系统中不存在");
									continue;
								}
								if (empMap.get(map.get("EMP_ID").get(1)).equals("#")) {
									falg = true;
									failureMsg.append("<br/> 第" + columNum + "行《" + colum.get("display") + "》列员工名称在系统中有重名，请使用编码导入");
								} else {
									saveMap.put("EMP_ID", empMap.get(map.get("EMP_ID").get(1)).toString().split("@")[0]);
								}
							}
						}
				}
				}
				if (colum.get("is_pk") != null && colum.get("is_pk").toString().equals("1")) {
					isPk.append(map.get(colum.get("name").toString()).get(1));
					if(colum.get("name").toString().equals("DEPT_ID")) {
						repeatMap.put(colum.get("name").toString(), saveMap.get(colum.get("name")).toString().split("@")[0]);
					}else {
						if (dataType.get(colum.get("name").toString()).equals("DATE") ){
							
							 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			    			 boolean flag = false ;
			    			 String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
			    			 String dateStr=null;
							
			    			 
			    			 if(map.get(colum.get("name")).get(1) .toString().indexOf("OADate")==1){
			    				 Date date=	fromDoubleToDateTime(Double.parseDouble(map.get(colum.get("name")).get(1) .toString().replace("OADate", "").replace("()", "").replace("/", "").replace("(", "").replace(")", "")));
					    			
				    			 dateStr = df.format(date);
								
								flag = dateStr.matches(reg);
			    			 }else if (map.get(colum.get("name")).get(1).length()==8){
			    				//处理20190101类型
			    				 String reg1 = "[\\d]{4}[\\d]{2}[\\d]{2}";
			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg1);
			    				 dateStr=map.get(colum.get("name")).get(1).toString();
			    			 }else{
			    				 //处理文本格式2019-01-01
			    				 flag = map.get(colum.get("name")).get(1).toString().matches(reg);
			    				 dateStr=map.get(colum.get("name")).get(1).toString();
			    			 }
			    			 if(flag == false){//判断日期格式是否正确
									failureMsg.append("第"+columNum+"行"+"日期格式错误 ");
									continue;
								}else{
									// 处理日期类型
									//saveMap.put(colum.get("name").toString(),  DateUtil.stringToDate(dateStr,"yyyy-MM-dd") );
									String str=" TO_DATE('" + dateStr + "','yyyy-MM-dd')";
									repeatMap.put(colum.get("name").toString(),str );
						}
							
							
						}else{
							repeatMap.put(colum.get("name").toString(), saveMap.get(colum.get("name")));
						}
					
					}
					
					saveMap.put(colum.get("name").toString()+"1", "is_pk");
					saveMap.put(colum.get("name").toString(), saveMap.get(colum.get("name")));
				}
			
				if(colum.get("name").toString().equals("DEPT_ID")) {
					saveMap.put(colum.get("name").toString(), saveMap.get(colum.get("name")).toString().split("@")[0]);
				}else {
					saveMap.put(colum.get("name").toString(), saveMap.get(colum.get("name")));
				}
			}
		/*	tablePkMap.put("GROUP_ID", SessionManager.getGroupId());
			tablePkMap.put("HOS_ID", SessionManager.getHosId());
			tablePkMap.put("EMP_ID", map.get("EMP_ID").get(1));
			int con1 = hrCommonMapper.queryTabExistsData(tab_code, repeatMap);
			
			  if(con1>0){ 
			  
			  return tablePkMap; }*/
			 
			if(colums.get(0).get("tab_code").toString().equals("HOS_EMP")) {
				int emp_id = hrCommonMapper.queryHosEmpMaxId(mapVo);
				repeatMap.put("emp_id",emp_id);
				repeatMap.put("group_id", SessionManager.getGroupId());
				repeatMap.put("hos_id", SessionManager.getHosId());
			}
			if(repeatMap.size()>0){
				StringBuffer selectSql= new StringBuffer();
				selectSql.append("select count(1) from "+tab_code+" where ");
				
				for (Map.Entry<String, Object> m :repeatMap.entrySet())  {
					selectSql.append(m.getKey()+" = "+m.getValue()+" and ");
					
				}
			
			String	cite_sql=selectSql.substring(0, selectSql.length() - 4);
				int con=hrCommonMapper.queryTabExistsData(cite_sql);
				if(con>0){ saveMap.put("update",0);
				
				
					};
			}
			
			saveMap.put("isPk", isPk);
			if (tab_code.equals("HOS_EMP")) {
				if (saveMap.get("IS_STOP") == null) {
					saveMap.put("IS_STOP", 0);
				}
			}
			if (falg) {
				saveMap.put("error", failureMsg);
				saveMap.put("rowno", "第" + countNum + "行");
			}
			return saveMap;
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryHrChildDataByPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("group_id", SessionManager.getGroupId());
		entityMap.put("hos_id", SessionManager.getHosId());
		entityMap.put("copy_code", SessionManager.getCopyCode());
		
		try {
			// 表格
			Map<String, Object> gridMap = new HashMap<String, Object>();
			
			// 表格头信息
			List<Map<String, Object>> fieldItems = hrCommonService.queryEditGridHeader(entityMap);
			gridMap.put("columns", fieldItems);
			
			// 表格数据
			Map<String, Object> datas = null;
			
			datas = hrCommonService.queryGridData(entityMap);
			List<Map<String, Object>> rowData = (List<Map<String, Object>>) datas.get("Rows");
			
			return rowData;
		} catch (Exception e) {
			throw new SysException(e.getMessage());
		}
		
		// List<Map<String, Object>> list =
		// hrTabStrucMapper.queryHrChildDataByPrint(entityMap);
		// return list ;
	}
	
	/**
	 * 打印
	 */
	@Override
	public List<Map<String, Object>> queryHrStatisticCustomPrint(Map<String, Object> entityMap) throws DataAccessException {
		
		entityMap.put("tmpQuerySql", entityMap.get("tmpSQL"));

		List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap);
		
		return list;
	}
	
	@Override
	public String queryExcelColumn(Map<String, Object> mapVo) {
		
		try {
			List<Map<String, Object>> column = hrCommonMapper.queryExcelColumn(mapVo);
			return ChdJson.toJsonLower(column);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
		
	}
	
	public static boolean checkDate(String date) {
		String eL = "^((//d{2}(([02468][048])|([13579][26]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|([1-2][0-9])))))|(//d{2}(([02468][1235679])|([13579][01345789]))[//-/////s]?((((0?[13578])|(1[02]))[//-/////s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[//-/////s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[//-/////s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(//s(((0?[0-9])|([1][0-9])|([2][0-3]))//:([0-5]?[0-9])((//s)|(//:([0-5]?[0-9])))))?$";
		Pattern p = Pattern.compile(eL);
		Matcher m = p.matcher(date);
		boolean b = m.matches();
		return b;
		
	}
	
	

	@Override
	public List<Map<String, Object>> totalDetail(Map<String, Object> entityMap) throws DataAccessException {
		
		LinkedHashMap<String,Map<String,Object>> hrFiiedMap = new LinkedHashMap<String,Map<String,Object>>();

		LinkedHashMap<String,Map<String,Object>> hrStoreTabColMap = new LinkedHashMap<String,Map<String,Object>>();
		
		LinkedHashMap<String,ArrayList<Map<String,Object>>> hrStoreTabMap = new LinkedHashMap<String,ArrayList<Map<String,Object>>>();
		
		LinkedHashMap<String,ArrayList<Map<String,Object>>> hrStoreCondition =  new LinkedHashMap<String,ArrayList<Map<String,Object>>>();
		
		/**
		 * 1、通过档案库分类编码store_type_code获取所有数据表 比填写字段
		 */
		
		List<Map<String, Object>> hrStoreColSet = hrCommonMapper.queryHrStoreColSet(entityMap);
		
		if (hrStoreColSet == null || hrStoreColSet.size() == 0) {
			
			throw new SysException("档案库数据表未构建 或者 档案库数据列未设置!");
			
		}
		
		/**
		 * 2、按照tab_code 把对应内容放到 ArrayList
		 */
		
		for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSet) {
			
			String tab_code = tmpHrColStrucSetMap.get("TAB_CODE").toString();
			
			hrStoreTabColMap.put(tab_code + tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);
			
			if (hrStoreTabMap.get(tab_code) != null) {
				
				ArrayList<Map<String, Object>> tmpHrTabStructList = hrStoreTabMap.get(tab_code);
				
				tmpHrTabStructList.add(tmpHrColStrucSetMap);
				
				hrStoreTabMap.put(tab_code, tmpHrTabStructList);
				
			} else {
				
				ArrayList<Map<String, Object>> tmpHrTabStructList = new ArrayList<Map<String, Object>>();
				
				tmpHrTabStructList.add(tmpHrColStrucSetMap);
				
				hrStoreTabMap.put(tab_code, tmpHrTabStructList);
				
			}
			
		}
		
		/**
		 * 3、取代码表
		 */
		List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
		
		for (Map<String, Object> tmpHrFiiedMap : hrFiiedList) {
			
			hrFiiedMap.put(tmpHrFiiedMap.get("FIELD_TAB_CODE").toString(), tmpHrFiiedMap);
			
		}

		List<Map<String, Object>> reDataList = new ArrayList<Map<String, Object>>();// 返回页面List
		
		Map<String, Object> reDataMap = null;
		
		List<Map<String, Object>> viewList = null;// 存放在reDataList中的List
		
		// 查询数据
		Map<String, String> querySQLMap = new LinkedHashMap<String, String>();
		
		// 主的SQL查询语句
		StringBuffer querySql = new StringBuffer();
		
		// hrFiiedInSQL 用来拼接is_cite=0 的内置字典包含表
		StringBuffer hrFiiedInSQL = new StringBuffer();
		
		// CITE_SQL 用来拼接is_cite=1的SQL
		StringBuffer citeSQL = new StringBuffer();
		
		// LEFT_SQL 用来拼接left join sql
		StringBuffer joinSQL = new StringBuffer();
		
		// withSQL 用来拼接is_cite=0的内置字典
		StringBuffer hrFiiedSQL = new StringBuffer();
		
		StringBuffer tmpSql = new StringBuffer();
		
		for (String tab_code : hrStoreTabMap.keySet()) {
			
			querySql.append("select ");
			
			ArrayList<Map<String, Object>> tmpHrTabStructList = hrStoreTabMap.get(tab_code);
			
			for (Map<String, Object> map : tmpHrTabStructList) {
				
				if("DATE".equals(map.get("DATA_TYPE"))){
					
					querySql.append("to_char("+tab_code + "." + map.get("COL_CODE")+",'yyyy-MM-dd') as " + map.get("COL_CODE")+ ", ");
					
				}else{
					
					querySql.append(tab_code + "." + map.get("COL_CODE") + ", ");
					
				}
				
//				if("04".equals(map.get("COM_TYPE_CODE"))){
//					
//					tmpSql.append("substr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",instr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",'/',-1,1)+1) as  SHORT_" + map.get("COL_CODE")+",");
//				
//				}

				if (map.get("FIELD_TAB_CODE") == null || "".equals(map.get("FIELD_TAB_CODE"))) {continue;}
				
				// 为了防止一个表重复使用 同一个字段 所以使用别名
				String alias = map.get("FIELD_TAB_CODE").toString() + "_" + map.get("COL_CODE");
				
				// 如果别名过长可能会引起oracle错误 所以判断截取
				if (alias.length() > 28) {alias = alias.substring(0, 28);}
				
				querySql.append(alias + ".field_col_name as " + map.get("COL_CODE") + "_TEXT, ");
				
				Map<String, Object> tmpHrFiiedMap = hrFiiedMap.get(map.get("FIELD_TAB_CODE"));
				
				Integer isCite = Integer.parseInt(tmpHrFiiedMap.get("IS_CITE").toString());
				
				if (isCite == 0) {
					
					if (hrFiiedInSQL.indexOf("'" + map.get("FIELD_TAB_CODE") + "',") == -1) {
						hrFiiedInSQL.append("'" + map.get("FIELD_TAB_CODE") + "',");
					}
					
					joinSQL.append("left join hr_fiied " + alias + " on to_char(");
					
					joinSQL.append(tab_code + "." + map.get("COL_CODE"));
					
					// 拼接变更字段
					if (map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1) {
						
						joinSQL.append("||'@'||" + tab_code + "." + map.get("CHANGE_COL_CODE"));
					}
					
					joinSQL.append(") = " + alias + ".field_col_code and ");
					
					joinSQL.append(alias + ".field_tab_code = '" + map.get("FIELD_TAB_CODE") + "' ");
					
				} else {
					
					citeSQL.append(alias + " as (").append(tmpHrFiiedMap.get("CITE_SQL") == null?"select null as FIELD_COL_CODE,null as FIELD_COL_NAME from dual":tmpHrFiiedMap.get("CITE_SQL")).append("),");
					
					joinSQL.append("left join " + alias + " " + alias + " on to_char(");
					
					joinSQL.append(tab_code + "." + map.get("COL_CODE"));
					
					// 拼接变更字段
					if (map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1) {
						joinSQL.append("||'@'||" + tab_code + "." + map.get("CHANGE_COL_CODE"));
					}
					
					joinSQL.append(") = " + alias + ".field_col_code ");
					
				}
				
			}
			
			querySql.delete(querySql.length() - 2, querySql.length() - 1);
			
			querySql.append("from " + tab_code + " " + tab_code + " ");
			
			querySql.append(joinSQL);
			
			querySql.append("where ");
			
			// 根据 拼接SQL中判断是否包含 默认的查询条件 group_id、hos_id、emp_id
			if (querySql.indexOf("GROUP_ID") != -1 && entityMap.get("group_id") != null) {
				
				querySql.append(tab_code + ".group_id = " + entityMap.get("group_id") + " AND ");
				
			}
			
			if (querySql.indexOf("HOS_ID") != -1 && entityMap.get("hos_id") != null) {
				
				querySql.append(tab_code + ".hos_id = " + entityMap.get("hos_id") + " AND ");
				
			}
			
			if (querySql.indexOf("EMP_ID") != -1 && entityMap.get("emp_id") != null) {
				
				querySql.append(tab_code + ".emp_id = " + entityMap.get("emp_id") + " AND ");
				
			}
			
			if (querySql.indexOf("DEPT_ID") != -1 && entityMap.get("dept_id") != null) {
				
				querySql.append(tab_code + ".DEPT_ID in (");
				
				querySql.append("select dept_id from hos_dept start with group_id = @group_id and hos_id = @hos_id and dept_code = '"
						+ entityMap.get("dept_code") + "' ");//之前写的dept_id是不是写错了
				
				querySql.append("connect by prior dept_code = super_code) AND ");
			}
			
			if (querySql.indexOf("AND") != -1) {// 如果不包含AND字符 说明 这个表没有where 条件
												// 则删除where字符
			
				querySql.delete(querySql.length() - 4, querySql.length());// 清除最后AND字符
				
			} else {
				
				querySql.delete(querySql.length() - 6, querySql.length());// 清除最后where字符
				
			}
			
			
			if (hrFiiedInSQL.length() > 0) {
				
				hrFiiedSQL.append("hr_fiied as (");
				
				hrFiiedSQL.append("select ");
				
				hrFiiedSQL.append("hr_fiied_data.field_tab_code,hr_fiied_data.field_col_code,hr_fiied_data.field_col_name from hr_fiied_data hr_fiied_data ");
				
				hrFiiedSQL.append("where hr_fiied_data.group_id = @group_id and hr_fiied_data.hos_id = @hos_id ");
				
				hrFiiedSQL.append("and hr_fiied_data.field_tab_code in (" + hrFiiedInSQL.deleteCharAt(hrFiiedInSQL.length() - 1) + ")");
				
				hrFiiedSQL.append("),");
				
			}
			
			if (hrFiiedSQL.length() > 0 || citeSQL.length() > 0) {
				tmpSql.append("with ");
			}
			
			if (hrFiiedSQL.length() > 0) {
				tmpSql.append(hrFiiedSQL);
			}
			
			if (citeSQL.length() > 0) {
				tmpSql.append(citeSQL);
			}
			
			if (tmpSql.length() > 0) {
				tmpSql.deleteCharAt(tmpSql.length() - 1);
			}
			
			tmpSql.append(querySql);
			
			String sql = tmpSql.toString();
			
			sql = replaceConstant(sql, entityMap);// 替换SQL里面的常量
			
			// 初始化变量
			querySql.setLength(0);
			
			citeSQL.setLength(0);
			
			joinSQL.setLength(0);
			
			hrFiiedSQL.setLength(0);
			
			hrFiiedInSQL.setLength(0);
			
			tmpSql.setLength(0);
			
			querySQLMap.put(tab_code, sql);
		}
		
		for (String tab_code : querySQLMap.keySet()) {
			
			entityMap.put("tmpQuerySql", querySQLMap.get(tab_code));
			
			List<Map<String, Object>> dataList = hrCommonMapper.queryQuerySQL(entityMap);
			
			if (dataList == null || dataList.size() == 0) {
				continue;
			}
			
			viewList = new ArrayList<Map<String, Object>>();
			
			Map<String, Object> viewValue = null;
			
			for (Map<String, Object> columnMap : dataList) {
				
				for (String key : columnMap.keySet()) {
					
					viewValue = new HashMap<String, Object>();
					
					Map<String, Object> tmpMap = hrStoreTabColMap.get(tab_code + key);
					
					if (key.indexOf("_TEXT") != -1) {continue;}
					
					if ("".equals(tmpMap.get("IS_VIEW_TAB")) || "0".equals(tmpMap.get("IS_VIEW_TAB").toString())) {continue;}
					
					viewValue.put("text", tmpMap.get("COL_NAME_SHOW"));
					
					viewValue.put("com_type", tmpMap.get("COM_TYPE_CODE"));
					
					if (tmpMap.get("FIELD_TAB_CODE") != null && !"".equals(tmpMap.get("FIELD_TAB_CODE"))) {
						
						viewValue.put("value", columnMap.get(key + "_TEXT"));
						
					} else {
						
						viewValue.put("value", columnMap.get(key));
						
					}
					
					viewList.add(viewValue);
					
				}
				
			}
			
			reDataMap = new HashMap<String, Object>();
			
			reDataMap.put("tab_code", tab_code);
			
			// 取统计表头
			ArrayList<Map<String, Object>> tmpList = hrStoreTabMap.get(tab_code);
			
			Map<String, Object> tmpMap = tmpList.get(0);
			
			reDataMap.put("tab_name", tmpMap.get("TAB_NAME"));
			
			reDataMap.put("data", viewList);
			
			reDataList.add(reDataMap);
			
		}
		
		// 初始化变量
		hrFiiedMap.clear();
		
		hrStoreTabColMap.clear();
		
		hrStoreTabMap.clear();
		
		hrStoreCondition.clear();
		
		return reDataList;
	}

	
	private String replaceConstant(String replaceStr,Map<String, Object> entityMap){
		
		replaceStr = replaceStr.replaceAll("@group_id", entityMap.get("group_id").toString());
		
		replaceStr = replaceStr.replaceAll("@hos_id", entityMap.get("hos_id").toString());
		
		replaceStr = replaceStr.replaceAll("@copy_code", entityMap.get("copy_code").toString());
		
		replaceStr = replaceStr.replaceAll("@user_id", entityMap.get("user_id").toString());
		
		return replaceStr;
		
	}
	
	private String dataTypeStr(Map<String, Object> map) {
		
		String str = null;
		// 根据字段数据类型 拼接语句
		if ("NUMBER".equals(map.get("DATA_TYPE"))) {
			
			str = " " + map.get("COL_VALUE");
			
		} else if ("VARCHAR2".equals(map.get("DATA_TYPE"))) {
			
			str = " '" + map.get("COL_VALUE") + "'";
			
		} else if ("DATE".equals(map.get("DATA_TYPE"))) {
			
			str = " TO_DATE('" + map.get("COL_VALUE") + "','yyyy-MM-dd')";
			
		} else {
			
			str = " '" + map.get("COL_VALUE") + "'";
		}
		
		return str;
		
	}
	
	private String hrFiiedTabStrucSql(Map<String, String> fieldTabCodeMap){
		
		if(fieldTabCodeMap.size() == 0){return "";}
		
		StringBuffer hrFiiedSQL = new StringBuffer(); 
		
		hrFiiedSQL.append("hr_fiied as (");
		
		hrFiiedSQL.append("select ");
		
		hrFiiedSQL.append("hr_fiied_data.field_tab_code,hr_fiied_data.field_col_code,hr_fiied_data.field_col_name from hr_fiied_data hr_fiied_data ");
		
		hrFiiedSQL.append("where hr_fiied_data.group_id = @group_id and hr_fiied_data.hos_id = @hos_id ");
		
		hrFiiedSQL.append("and hr_fiied_data.field_tab_code in (");
		
		for(String key : fieldTabCodeMap.keySet()){
			
			hrFiiedSQL.append("'"+key+"',");
			
		}
		hrFiiedSQL.deleteCharAt(hrFiiedSQL.length() - 1);
				
		hrFiiedSQL.append(")),");
		
		return hrFiiedSQL.toString();
		
	}

	private String joinCondition(Map<String,Object> map){
		
		StringBuffer conditionSql = new StringBuffer();
		
		conditionSql.append(" ");
		/*conditionSql.append("and  ");*/
		if(map.get("L_BRACKET") != null){conditionSql.append(map.get("L_BRACKET"));}
		
		conditionSql.append(map.get("TAB_CODE") + "."+ map.get("COL_CODE") + map.get("CON_SIGN_NAME"));
if(map.get("COL_CODE").equals("DEPT_ID")){
	conditionSql.append(dataTypeStr(map).split("@")[0]);//根据字段数据类型 拼接语句
	
}else{
	conditionSql.append(dataTypeStr(map));
}
		
		
		if(map.get("R_BRACKET") != null){conditionSql.append(map.get("R_BRACKET"));}
		
		if(map.get("JOIN_SIGN_NAME") != null){conditionSql.append(" "+ map.get("JOIN_SIGN_NAME"));}
		
		
		return conditionSql.toString();
		
	}
	
	private Integer joinSignIndex(String str){
		
		if(str.indexOf("AND") != -1){return 3;}
		
		if(str.indexOf("OR") != -1){return 2;}
		
		if(str.indexOf("NOT") != -1){return 3;}
		
		return 0;
		
	}
	
	private Map<String,String> getPageSearchMap(Map<String,Object> entityMap){
		
		Map<String,String> map = new HashMap<String,String>();
		
		for(String key : entityMap.keySet()){
			
			if(key.indexOf("SEARCH_") != -1){
				
				if(key.indexOf("DEPT_ID") != -1){
					map.put(key.replaceAll("SEARCH_", ""), entityMap.get(key).toString().split("@")[0]);
				}else{
				
				map.put(key.replaceAll("SEARCH_", ""), entityMap.get(key).toString());
				}	
			}
			
		}
		return map;

	}
	@Override
	public  String queryEmpHead(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			String main_code = entityMap.get("main_code").toString();
			
			String child_code = null;
			
			if(entityMap.get("child_code") != null && !"".equals(entityMap.get("child_code"))){
				
				child_code = entityMap.get("child_code").toString();
				
			}
			
			LinkedHashMap<String,Map<String,Object>> hrFiiedMap = new LinkedHashMap<String,Map<String,Object>>();

			//构建SQL和表头
			LinkedHashMap<String,Map<String,Object>> hrStoreTabColMap = new LinkedHashMap<String,Map<String,Object>>();
			
			//查询条件需要main_code、child_code 如果main_code不是hos_emp还需要加上HOS_EMP的全部字段
			LinkedHashMap<String,Map<String,Object>> hrSearchMap = new LinkedHashMap<String,Map<String,Object>>();
			
			/**主集查询 判断是不是 HOS_EMP 如果不是 需要把HOS_EMP表中 职工编码 职工姓名 科室名称自动带出来 */
			if(!MAIN_TABLE_NAME.equals(main_code)){
				
				entityMap.put("tab_code", "HOS_EMP");
				
				List<Map<String, Object>> hrHosEmpStoreColSet = hrCommonMapper.queryHrStoreColSet(entityMap);
				
				for (Map<String, Object> tmpHrHosEmpStoreColSet : hrHosEmpStoreColSet) {
					
					String tmpColCode = tmpHrHosEmpStoreColSet.get("COL_CODE").toString();

					if("EMP_CODE".equals(tmpColCode) || "EMP_NAME".equals(tmpColCode) || "DEPT_ID".equals(tmpColCode) || "DEPT_NO".equals(tmpColCode)){
						
						hrStoreTabColMap.put(tmpHrHosEmpStoreColSet.get("TAB_CODE").toString() + tmpColCode, tmpHrHosEmpStoreColSet);
						
					}
					
					hrSearchMap.put(tmpHrHosEmpStoreColSet.get("TAB_CODE") +"."+  tmpColCode, tmpHrHosEmpStoreColSet);	

				}
				
			}
			
			/**取main_code*/
			entityMap.put("tab_code", main_code);
			
			List<Map<String, Object>> hrStoreColSetMain = hrCommonMapper.queryHrStoreColSet(entityMap);
			
			if (hrStoreColSetMain == null || hrStoreColSetMain.size() == 0) {
				 
				return "{\"error\":\"档案库数据表未构建 或者 档案库数据列未设置!\"}";
				
			}
			
			for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSetMain) {

				//防止查重复字段 如果存在则删除该字段属性
				if(!MAIN_TABLE_NAME.equals(main_code)){
					
					if(hrStoreTabColMap.get(MAIN_TABLE_NAME+tmpHrColStrucSetMap.get("COL_CODE")) != null){
						
						hrStoreTabColMap.remove(MAIN_TABLE_NAME+tmpHrColStrucSetMap.get("COL_CODE"));
						
					}
					
				}

				hrStoreTabColMap.put(tmpHrColStrucSetMap.get("TAB_CODE").toString() + tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);
				
				hrSearchMap.put(tmpHrColStrucSetMap.get("TAB_CODE").toString() +"."+ tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);
				
			}
			
			/**取child_code*/
			if(child_code != null && !"".equals(child_code)){
				
				entityMap.put("tab_code", child_code);
				
				List<Map<String, Object>> hrStoreColSetChild = hrCommonMapper.queryHrStoreColSet(entityMap);
				
				for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSetChild) {
					
					hrSearchMap.put(tmpHrColStrucSetMap.get("TAB_CODE").toString() +"."+ tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);
					
				}
				
			}
			

			/**取外置表属性*/
			List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
			
			for (Map<String, Object> tmpHrFiiedMap : hrFiiedList) {
				
				hrFiiedMap.put(tmpHrFiiedMap.get("FIELD_TAB_CODE").toString(), tmpHrFiiedMap);
				
			}

			//第一步 构建columns 
			Map<String,String> fieldTabCodeMap = new HashMap<String,String>();//存储使用了那些代码表(field_tab_code)

			//CITE_SQL 用来拼接is_cite=1的SQL
			StringBuffer citeSQL = new StringBuffer();
			
			//LEFT_SQL 用来拼接left join sql
			StringBuffer joinSQL = new StringBuffer();
			
			//exists 用来拼接exists child sql
			StringBuffer childSQL = new StringBuffer();
			
			StringBuffer tmpSql = new StringBuffer("select "); 
			
			for (String key : hrStoreTabColMap.keySet()) {
				
				Map<String,Object> map = hrStoreTabColMap.get(key);
				
				tmpSql.append(map.get("TAB_CODE") + "." + map.get("COL_CODE")+",");
				
				if("04".equals(map.get("COM_TYPE_CODE"))){
				
					tmpSql.append("substr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",instr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",'/',-1,1)+1) as  SHORT_" + map.get("COL_CODE")+",");
				
				}
				
				//处理引用代码表
				if(map.get("FIELD_TAB_CODE") == null || "".equals(map.get("FIELD_TAB_CODE"))){continue;}
				
				//为了防止一个表重复使用 同一个字段 所以使用别名
				String alias = map.get("FIELD_TAB_CODE") + "_" + map.get("COL_CODE");
			
				//如果别名过长可能会引起oracle错误 所以判断截取
				if(alias.length() > 30){alias = alias.substring(0, 30);}
				
				tmpSql.append(alias+".field_col_name as "+map.get("COL_CODE")+"_TEXT,");

				Map<String,Object> tmpHrFiiedMap = hrFiiedMap.get(map.get("FIELD_TAB_CODE"));
				
				Integer isCite = Integer.parseInt(tmpHrFiiedMap.get("IS_CITE").toString());

				if(isCite == 0){
					
					fieldTabCodeMap.put(map.get("FIELD_TAB_CODE").toString(), map.get("FIELD_TAB_CODE").toString());

					joinSQL.append(" left join hr_fiied "+alias+" on to_char(");
					
					joinSQL.append(map.get("TAB_CODE")+"."+map.get("COL_CODE"));

					
					if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){//拼接变更字段
						
						joinSQL.append("||'@'||"+map.get("TAB_CODE")+"."+map.get("CHANGE_COL_CODE"));
					}

					joinSQL.append(") = " +alias+".field_col_code and ");
					
					joinSQL.append(alias+".field_tab_code = '"+ map.get("FIELD_TAB_CODE") +"' ");
					
				}else{
					
					citeSQL.append(alias+" as (").append(tmpHrFiiedMap.get("CITE_SQL") == null?"select null as FIELD_COL_CODE,null as FIELD_COL_NAME from dual":tmpHrFiiedMap.get("CITE_SQL")).append("),");
					
					joinSQL.append(" left join "+alias+" "+alias+" on to_char(");
					
					joinSQL.append(map.get("TAB_CODE")+"."+map.get("COL_CODE"));
					
					if(MAIN_TABLE_NAME.equals(main_code)) {
						
						if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){//拼接变更字段
							joinSQL.append("||'@'||"+map.get("TAB_CODE")+"." + map.get("CHANGE_COL_CODE"));
						}	
					}
				
					
					if(!MAIN_TABLE_NAME.equals(main_code)&&alias.equals("SYS_DEPT_DEPT_ID") && map.get("COL_CODE").equals("DEPT_ID")) {
						joinSQL.append(") =  substr(" +alias+".field_col_code,1 ,instr("+alias+".field_col_code,'@')-1 )");
					}else {
					joinSQL.append(") = " +alias+".field_col_code ");
					}
				}

			}
			
			

			//exists 用来拼接exists child sql
			StringBuffer hrStoreCondSQL = new StringBuffer();
			
			/**档案库人员限定配置*/
			List<Map<String, Object>> hrStoreConditionList = hrCommonMapper.queryHrStoreCondition(entityMap);
			
			for (Map<String, Object> map : hrStoreConditionList) {
               
				if(map.get("TAB_CODE").toString()!= null&& map.get("COL_CODE") != null){
					
					//joinSQL.append(" left join "+map.get("TAB_CODE")+" "+map.get("TAB_CODE")+" ON HOS_EMP.EMP_ID = "+map.get("TAB_CODE")+".EMP_ID "+" and HOS_EMP.GROUP_ID = "+map.get("TAB_CODE")+".GROUP_ID"+" AND  HOS_EMP.HOS_ID = "+map.get("TAB_CODE")+".HOS_ID");
					map.put(map.get("TAB_CODE").toString(),map.get("COL_CODE"));
					
				/*	hrStoreCondSQL.append(joinCondition(map));
				}else{
				
				map.putAll(hrStoreTabColMap.get(map.get("TAB_CODE").toString()+map.get("COL_CODE")));
				*/
				hrStoreCondSQL.append(joinCondition(map));
				}
			}
			tmpSql.deleteCharAt(tmpSql.length() - 1);

			//第二部 构建from
			tmpSql.append(" from ").append(main_code).append(" ").append(main_code);
			
			if(!MAIN_TABLE_NAME.equals(main_code)){

				tmpSql.append(" right join HOS_EMP HOS_EMP");

				tmpSql.append(" on ").append(main_code).append(".").append("emp_id").append(" = HOS_EMP.EMP_ID");
				
				tmpSql.append(" and HOS_EMP.GROUP_ID = @group_id and HOS_EMP.HOS_ID = @hos_id");

			}

			if(joinSQL.length() > 0){tmpSql.append(joinSQL);}

			//第四部 构建where 
			tmpSql.append(" where ");
			
			if(hrStoreTabColMap.get(main_code + "GROUP_ID") != null){tmpSql.append(" "+ main_code + ".GROUP_ID = @group_id" +" AND");}
			
			if(hrStoreTabColMap.get(main_code + "HOS_ID") != null){tmpSql.append(" "+ main_code + ".HOS_ID = @hos_id" +" AND");}
			
			Map<String,String> searchMap = getPageSearchMap(entityMap);
			
			for(String search_code :searchMap.keySet()){
				
				Map<String,Object> map = hrSearchMap.get(search_code);
				
				map.put("COL_VALUE", searchMap.get(search_code));
				
				if(search_code.indexOf(child_code) != -1){

					childSQL.append(" "+ search_code);
					
				/*	if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){//拼接变更
						
						childSQL.append("||'@'||"+child_code+"." + map.get("CHANGE_COL_CODE"));
						
						map.put("DATA_TYPE", "IS_CHANGE");
					}*/

					childSQL.append(" = "+ dataTypeStr(map) +" AND");
					
				}else{
					
					tmpSql.append(" "+ search_code);
					
					/*if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){//拼接变更
						
						tmpSql.append("||'@'||"+main_code+"." + map.get("CHANGE_COL_CODE"));
						
						map.put("DATA_TYPE", "IS_CHANGE");
					}*/
					
					tmpSql.append(" = "+ dataTypeStr(map) +" AND");
					
				}
				

			}
			
			if(entityMap.get("dept_code") != null && !"".equals(entityMap.get("dept_code"))){
			
				tmpSql.append(" HOS_EMP.DEPT_ID in (");
				
				tmpSql.append("select dept_id from hos_dept start with group_id = @group_id and hos_id = @hos_id and dept_code in ( "+entityMap.get("dept_code")+" ) ");
				
				tmpSql.append("connect by prior dept_code = super_code) AND");
			}
			
			if(childSQL.length() >0){
				
				tmpSql.append(" exists (select 1 from "+child_code+" "+child_code+ 
									" where "+child_code+".group_id = @group_id AND"+ 
									" "+child_code+".hos_id = @hos_id AND " + childSQL.toString() +
									" "+child_code+".emp_id = HOS_EMP.emp_id) AND");
			}
			
			tmpSql.append( " exists (select 1 from v_user_data_perm b"+
					           " where HOS_EMP.group_id = b.group_id"+
					              " and HOS_EMP.hos_id = b.hos_id"+
					              " and to_char(HOS_EMP.dept_id) = b.perm_code"+
					              " and b.user_id ="+entityMap.get("user_id")+
					              " and b.TABLE_CODE =upper('HOS_DEPT_DICT')"+
					              " and (b.is_read = 1)) AND");
			
			if(entityMap.get("EMP_ID") != null){
				
				tmpSql.append(" "+ main_code + ".EMP_ID = "+ entityMap.get("EMP_ID") +" AND" );
				
			}

		tmpSql.append(hrStoreCondSQL);
			
			//判断最后字符 
			Integer joinNum = joinSignIndex(tmpSql.substring(tmpSql.length() - 3, tmpSql.length()));
			
			if(joinNum >0){
				
				tmpSql.delete(tmpSql.length() - joinNum, tmpSql.length());
				
			}
			
			//第五步 构建order by
			tmpSql.append(" order by HOS_EMP.EMP_CODE");
			
			//第六步组装整体SQL
			StringBuffer sql = new StringBuffer();
			
			//withSQL 用来拼接is_cite=0的内置字典
			String hrFiiedSQL = hrFiiedTabStrucSql(fieldTabCodeMap);
			
			if(hrFiiedSQL.length() >0 || citeSQL.length() >0){sql.append("with ");}

			if(hrFiiedSQL.length() >0){sql.append(hrFiiedSQL);}
			
			if(citeSQL.length() >0){sql.append(citeSQL.deleteCharAt(citeSQL.length()-1));}
			
			sql.append(tmpSql);
			

			/** 处理表头 * */
			
			int defColWidth = MyConfig.getSysPara("06003") == null ? 120 : Integer.parseInt(MyConfig.getSysPara("06003"));// 表格默认列宽
			
			List<Map<String, Object>> columnsList = new ArrayList<Map<String, Object>>();
			
			LinkedHashMap<String, Object> columnsMap = null;	
			
			for (String key : hrStoreTabColMap.keySet()) {
				
				Map<String,Object> map = hrStoreTabColMap.get(key);
				
				if ("".equals(map.get("IS_VIEW_TAB")) || "0".equals(map.get("IS_VIEW_TAB").toString())) {continue;}

				//处理附件上传 显示短路径
				if("04".equals(map.get("COM_TYPE_CODE"))){
					
					columnsMap = new LinkedHashMap<String, Object>();
					
					columnsMap.put("name", "SHORT_"+map.get("COL_CODE"));
					
					columnsMap.put("display", map.get("COL_NAME_SHOW"));
					// 表格列宽度
					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
					// 对齐方式
					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
					
					columnsMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);

					columnsList.add(columnsMap);
					
					columnsMap = new LinkedHashMap<String, Object>();
					
					columnsMap.put("name", map.get("COL_CODE"));
					
					columnsMap.put("display", map.get("COL_NAME_SHOW")+"全路径");
					// 表格列宽度
					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
					// 对齐方式
					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
					
					columnsMap.put("hidden", true);

					columnsList.add(columnsMap);
					
				}else{
					
					columnsMap = new LinkedHashMap<String, Object>();
					
					columnsMap.put("name", map.get("COL_CODE"));
					
					columnsMap.put("display", map.get("COL_NAME_SHOW"));
					// 表格列宽度
					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
					// 对齐方式
					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
					
					columnsMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);

					columnsList.add(columnsMap);
					
				}

				//处理关联字段
				if(map.get("FIELD_TAB_CODE") !=null && !"".equals(map.get("FIELD_TAB_CODE"))){
					
					columnsMap = new LinkedHashMap<String, Object>();
					
					columnsMap.put("name", map.get("COL_CODE")+"_TEXT");
					
					columnsMap.put("display", map.get("COL_NAME_SHOW"));
					
					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));// 表格列宽度
					
					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));// 对齐方式
					
					columnsList.add(columnsMap);
					
				}

			}
			
			Map<String,Object> headMap = new HashMap<String,Object>();
			
			headMap.put("columns", columnsList);//主表表头
			
			headMap.put("tmpSQL", replaceConstant(sql.toString(),entityMap));
		
			return JSONArray.toJSONString(headMap);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败", e);
		}
		
	}
	
	@Override
	public String queryEmpChildHead(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			LinkedHashMap<String,Map<String,Object>> hrFiiedMap = new LinkedHashMap<String,Map<String,Object>>();

			LinkedHashMap<String,Map<String,Object>> citeFiiedMap = new LinkedHashMap<String,Map<String,Object>>();
			
			LinkedHashMap<String,Map<String,Object>> hrStoreTabColMap = new LinkedHashMap<String,Map<String,Object>>();

			/**通过档案库分类编码store_type_code获取所有数据表 比填写字段*/
			List<Map<String, Object>> hrStoreColSet = hrCommonMapper.queryHrStoreColSet(entityMap);
			
			if (hrStoreColSet == null || hrStoreColSet.size() == 0) {
				 
				return "{\"error\":\"档案库数据表未构建 或者 档案库数据列未设置!\"}";
				
			}
			
			/**按照tab_code 把对应内容放到 ArrayList*/
			for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSet) {
				
				hrStoreTabColMap.put(tmpHrColStrucSetMap.get("TAB_CODE").toString() + tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);
				
			}
			
			List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
			
			for (Map<String, Object> tmpHrFiiedMap : hrFiiedList) {
				
				hrFiiedMap.put(tmpHrFiiedMap.get("FIELD_TAB_CODE").toString(), tmpHrFiiedMap);
				
			}
			for (Map<String, Object> citeHrFiiedMap : hrFiiedList) {
							
				citeFiiedMap.put(citeHrFiiedMap.get("FIELD_TAB_CODE").toString(), citeHrFiiedMap);
							
			}
			String tab_code = entityMap.get("tab_code").toString();
			
			//第一步 构建columns 
			Map<String,String> fieldTabCodeMap = new HashMap<String,String>();//存储使用了那些代码表(field_tab_code)

			//CITE_SQL 用来拼接is_cite=1的SQL
			StringBuffer citeSQL = new StringBuffer();
			
			//LEFT_SQL 用来拼接left join sql
			StringBuffer joinSQL = new StringBuffer();
			
			StringBuffer tmpSql = new StringBuffer("select "); 
			
			if(!MAIN_TABLE_NAME.equals(tab_code)){tmpSql.append("HOS_EMP.EMP_NAME,");}
			
			for (Map<String, Object> map : hrStoreColSet) {
				
				tmpSql.append(map.get("TAB_CODE") + "." + map.get("COL_CODE")+",");
				
				if("04".equals(map.get("COM_TYPE_CODE"))){
					
					tmpSql.append("substr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",instr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",'/',-1,1)+1) as  SHORT_" + map.get("COL_CODE")+",");
				
				}
				
				//处理引用代码表
				map.putAll(hrStoreTabColMap.get(map.get("TAB_CODE").toString() + map.get("COL_CODE").toString()));
				
				if(map.get("FIELD_TAB_CODE") == null || "".equals(map.get("FIELD_TAB_CODE"))){continue;}
				
				//为了防止一个表重复使用 同一个字段 所以使用别名
				String alias = map.get("FIELD_TAB_CODE") + "_" + map.get("COL_CODE");
			
				//如果别名过长可能会引起oracle错误 所以判断截取
				if(alias.length() > 30){alias = alias.substring(0, 30);}
				
				tmpSql.append(alias+".field_col_name as "+map.get("COL_CODE")+"_TEXT,");
				
				Map<String,Object> tmpHrFiiedMap = hrFiiedMap.get(map.get("FIELD_TAB_CODE"));
				
				Integer isCite = Integer.parseInt(tmpHrFiiedMap.get("IS_CITE").toString());

				if(isCite == 0){
					
					fieldTabCodeMap.put(map.get("FIELD_TAB_CODE").toString(), map.get("FIELD_TAB_CODE").toString());

					joinSQL.append(" left join hr_fiied "+alias+" on to_char(");
					
					joinSQL.append(map.get("TAB_CODE")+"."+map.get("COL_CODE"));

					//拼接变更字段
					if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){
						
						joinSQL.append("||'@'||"+map.get("TAB_CODE")+"."+map.get("CHANGE_COL_CODE"));
					}

					joinSQL.append(") = " +alias+".field_col_code and ");
					
					joinSQL.append(alias+".field_tab_code = '"+ map.get("FIELD_TAB_CODE") +"' ");
					
				}else{
					
					citeSQL.append(alias+" as (").append(tmpHrFiiedMap.get("CITE_SQL") == null?"select null as FIELD_COL_CODE,null as FIELD_COL_NAME from dual":tmpHrFiiedMap.get("CITE_SQL")).append("),");
					
					joinSQL.append(" left join "+alias+" "+alias+" on to_char(");
					
					joinSQL.append(map.get("TAB_CODE")+"."+map.get("COL_CODE"));
					
					//拼接变更字段
					if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){
						
						joinSQL.append("||'@'||"+map.get("TAB_CODE")+"." + map.get("CHANGE_COL_CODE"));
						
					}
					
					if(alias.equals("SYS_DEPT_DEPT_ID") && map.get("COL_CODE").equals("DEPT_ID") ) {
						
						joinSQL.append(") =  substr(" +alias+".field_col_code,1 ,instr("+alias+".field_col_code,'@')-1 )");
						
					}else {
						
						joinSQL.append(") = " +alias+".field_col_code ");
					}
				}

			}
			
			tmpSql.deleteCharAt(tmpSql.length() - 1);

			//第二部 构建from
			tmpSql.append(" from ").append(tab_code).append(" ").append(tab_code);
			
			if(!MAIN_TABLE_NAME.equals(tab_code)){

				tmpSql.append(" left join HOS_EMP HOS_EMP");

				tmpSql.append(" on ").append(tab_code).append(".").append("emp_id").append(" = HOS_EMP.EMP_ID");
				
				tmpSql.append(" and HOS_EMP.GROUP_ID = @group_id and HOS_EMP.HOS_ID = @hos_id");

			}

			if(joinSQL.length() > 0){tmpSql.append(joinSQL);}

			//第四部 构建where 
			tmpSql.append(" where ");
			
			if(hrStoreTabColMap.get(tab_code + "GROUP_ID") != null){tmpSql.append(" "+ tab_code + ".GROUP_ID = @group_id" +" AND");}
			
			if(hrStoreTabColMap.get(tab_code + "HOS_ID") != null){tmpSql.append(" "+ tab_code + ".HOS_ID = @hos_id" +" AND");}
			
//			Map<String,String> searchMap = getPageSearchMap(entityMap);
//			
//			for(String col_code :searchMap.keySet()){
//				
//				Map<String,Object> map = hrStoreTabColMap.get(tab_code+col_code);
//				
//				map.put("COL_VALUE", searchMap.get(col_code));
//				
//				tmpSql.append(" "+ tab_code + "."+ col_code);
//
//				if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){//拼接变更
//					
//					tmpSql.append("||'@'||"+tab_code+"." + map.get("CHANGE_COL_CODE"));
//					
//					map.put("DATA_TYPE", "IS_CHANGE");
//				}
//				
//				tmpSql.append(" = "+ dataTypeStr(map) +" AND");
//
//			} 
//			
//			if(entityMap.get("dept_code") != null){
//			
//				tmpSql.append(" HOS_EMP.DEPT_ID in (");
//				
//				tmpSql.append("select dept_id from hos_dept start with group_id = @group_id and hos_id = @hos_id and dept_code = '"+entityMap.get("dept_code")+"' ");
//				
//				tmpSql.append("connect by prior dept_code = super_code) AND");
//			}
			
			if(entityMap.get("EMP_ID") != null){
				
				tmpSql.append(" "+ tab_code + ".EMP_ID = "+ entityMap.get("EMP_ID") +" AND");
				
			}

			/**档案库人员限定配置*/
			List<Map<String, Object>> hrStoreConditionList = hrCommonMapper.queryHrStoreCondition(entityMap);
			
			for (Map<String, Object> map : hrStoreConditionList) {

				if(hrStoreTabColMap.get(map.get("TAB_CODE").toString()+map.get("COL_CODE")) == null){continue;}
				
				map.putAll(hrStoreTabColMap.get(map.get("TAB_CODE").toString()+map.get("COL_CODE")));
				
				tmpSql.append(joinCondition(map));

			}
			
			//判断最后字符 
			Integer joinNum = joinSignIndex(tmpSql.substring(tmpSql.length() - 3, tmpSql.length()));
			
			if(joinNum >0){
				
				tmpSql.delete(tmpSql.length() - joinNum, tmpSql.length());
				
			}
			
			//第五步 构建order by
			tmpSql.append(" order by HOS_EMP.EMP_CODE");
			
			//第六步组装整体SQL
			StringBuffer sql = new StringBuffer();
			
			//withSQL 用来拼接is_cite=0的内置字典
			String hrFiiedSQL = hrFiiedTabStrucSql(fieldTabCodeMap);
			
			if(hrFiiedSQL.length() >0 || citeSQL.length() >0){sql.append("with ");}

			if(hrFiiedSQL.length() >0){sql.append(hrFiiedSQL);}
			
			if(citeSQL.length() >0){sql.append(citeSQL.deleteCharAt(citeSQL.length()-1));}
			
			sql.append(tmpSql);
			

			/** 处理表头 * */
			
			int defColWidth = MyConfig.getSysPara("06003") == null ? 120 : Integer.parseInt(MyConfig.getSysPara("06003"));// 表格默认列宽
			
			List<Map<String, Object>> columnsList = new ArrayList<Map<String, Object>>();
			
			LinkedHashMap<String, Object> columnsMap = null;	
			
			for (Map<String,Object> map: hrStoreColSet) {
				
				if ("".equals(map.get("IS_VIEW_TAB")) || "0".equals(map.get("IS_VIEW_TAB").toString())) {continue;}

				//处理附件上传 显示短路径
//				if("04".equals(map.get("COM_TYPE_CODE"))){
//					
//					columnsMap = new LinkedHashMap<String, Object>();
//					
//					columnsMap.put("name", "SHORT_"+map.get("COL_CODE"));
//					
//					columnsMap.put("display", map.get("COL_NAME_SHOW"));
//					// 表格列宽度
//					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
//					// 对齐方式
//					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
//					
//					columnsMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);
//					
////					Map<String, String> editFileMap = new HashMap<String, String>();
////					
////					editFileMap.put("url", FILE_UPLOAD_URL);
////					
////					editFileMap.put("keyField","file");
////					
////					columnsMap.put("fileModel", editFileMap);
//
//					columnsList.add(columnsMap);
//					
//					columnsMap = new LinkedHashMap<String, Object>();
//					
//					columnsMap.put("name", map.get("COL_CODE"));
//					
//					columnsMap.put("display", map.get("COL_NAME_SHOW")+"全路径");
//					// 表格列宽度
//					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
//					// 对齐方式
//					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
//					
//					columnsMap.put("hidden", true);
//
//					columnsList.add(columnsMap);
//					
//				}else{
//					
//					columnsMap = new LinkedHashMap<String, Object>();
//					
//					columnsMap.put("name", map.get("COL_CODE"));
//					
//					columnsMap.put("display", map.get("COL_NAME_SHOW"));
//					// 表格列宽度
//					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
//					// 对齐方式
//					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
//					
//					columnsMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);
//
//					columnsList.add(columnsMap);
//					
//				}
				
				columnsMap = new LinkedHashMap<String, Object>();
				
				columnsMap.put("name", map.get("COL_CODE"));
				
				columnsMap.put("display", map.get("COL_NAME_SHOW"));
				// 表格列宽度
				columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
				// 对齐方式
				columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
				
				columnsMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);
				
				Map<String, Object> editColumnsMap = new HashMap<String, Object>();
				
				// 默认值
				if ("1".equals(map.get("COL_CODE"))) {
					
					columnsMap.put("is_default", true);
					
					columnsMap.put("default_value", map.get("DEFAULT_VALUE"));
					
					columnsMap.put("default_text",  map.get("DEFAULT_TEXT"));
					
				}

				// 处理日期类型字段
				if ("03".equals(map.get("COM_TYPE_CODE"))) {
					
					editColumnsMap.put("type", "date");
					
				}
				
				// 处理文件上传类型字段
				if ("04".equals(map.get("COM_TYPE_CODE"))) {
					
//					columnsMap.put("hidden", true);
//					
//					columnsMap = new LinkedHashMap<String, Object>();
//					
//					columnsMap.put("name", "SHORT_"+map.get("COL_CODE"));
//					
//					columnsMap.put("display", map.get("COL_NAME_SHOW"));
//					// 表格列宽度
//					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
//					// 对齐方式
//					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
//					
//					columnsMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);
					
					Map<String, String> editFileMap = new HashMap<String, String>();
					
					editFileMap.put("url", FILE_UPLOAD_URL);
					
					editFileMap.put("keyField","file");
					
					columnsMap.put("fileModel", editFileMap);
					
				}
				
				columnsMap.put("editor", editColumnsMap);
				
				columnsList.add(columnsMap);
				
				//处理关联字段
				if(map.get("FIELD_TAB_CODE") !=null && !"".equals(map.get("FIELD_TAB_CODE"))){
					
					columnsMap = new LinkedHashMap<String, Object>();editColumnsMap = new HashMap<String, Object>();
					
					columnsMap.put("name", map.get("COL_CODE")+"_TEXT");
					
					columnsMap.put("display", map.get("COL_NAME_SHOW"));
					
			
					columnsMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));// 表格列宽度
					
					columnsMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));// 对齐方式
					
					if("01".equals(map.get("COM_TYPE_CODE"))){
						
						entityMap.put("col_code", map.get("COL_CODE"));
						
						if("0".equals(citeFiiedMap.get(map.get("FIELD_TAB_CODE")).get("IS_CITE").toString())){
							editColumnsMap.put("url", "queryHrHosSelect.do?isCheck=false&table_code="+map.get("FIELD_TAB_CODE"));
						}else {
							
							String sqlCite = String.valueOf(citeFiiedMap.get(map.get("FIELD_TAB_CODE")).get("CITE_SQL"));
							
							sqlCite = sqlCite.replaceAll("\n", " ");
							
							sqlCite = sqlCite.replaceAll("\r", " ");
							
							sqlCite = sqlCite.replaceAll("\t", " ");

							editColumnsMap.put("url","queryHrHosSelectCite.do?isCheck=false&sql="+sqlCite);
						}
						
						editColumnsMap.put("type", "select");
						
						//editColumnsMap.put("source", itemData);
						
						//url: 'queryHrHosConditionTabStruc.do?isCheck=false',
	
						editColumnsMap.put("keyField", map.get("COL_CODE"));
						
					}
					
					columnsMap.put("editor", editColumnsMap);
					
					columnsList.add(columnsMap);
					
				}

			}
			
			Map<String,Object> headMap = new HashMap<String,Object>();
			
			headMap.put("columns", columnsList);//主表表头
			
			headMap.put("tmpSQL", replaceConstant(sql.toString(),entityMap));
		
			return JSONArray.toJSONString(headMap);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败", e);
		}
		
	}
	/**
	 * 查询主集数据 主集分页查询
	 */
	@Override
	public String queryEmpGrid(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			entityMap.put("tmpQuerySql", entityMap.get("tmpSQL"));
			
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}	
			
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败", e);
		}
	}
	/**
	 * 查询子集数据 子集不分页
	 */
	@Override
	public String queryEmpChildGrid(Map<String, Object> entityMap) throws DataAccessException {
		try {

			entityMap.put("tmpQuerySql", entityMap.get("tmpSQL"));
			
			List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap);

			return ChdJson.toJson(list);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败", e);
		}
	}
	
	/**
	 * 查询简单统计表头
	 */
	@Override
	public String queryHrStatisticCustomHead(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			LinkedHashMap<String,Map<String,Object>> hrFiiedMap = new LinkedHashMap<String,Map<String,Object>>();

			LinkedHashMap<String,Map<String,Object>> hrStoreTabColMap = new LinkedHashMap<String,Map<String,Object>>();
			
			//1、通过档案库分类编码store_type_code获取所有数据表 比填写字段
			List<Map<String, Object>> hrStoreColSet = hrCommonMapper.queryHrStoreColSet(entityMap);
			
			for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSet) {
				
				hrStoreTabColMap.put(tmpHrColStrucSetMap.get("TAB_CODE").toString() + tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);

			}
			
			//2、取代码表
			List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
			
			for (Map<String, Object> tmpHrFiiedMap : hrFiiedList) {
				
				hrFiiedMap.put(tmpHrFiiedMap.get("FIELD_TAB_CODE").toString(), tmpHrFiiedMap);
				
			}
			
			List<Map<String, Object>> hrStatisticTabStrucList = hrCommonMapper.queryHrStatisticTabStruc(entityMap);
			
			//第一步 构建columns 
			
			Map<String,String> tabCodeMap = new HashMap<String,String>();//存储使用了那些数据表(tab_code)
			
			Map<String,String> fieldTabCodeMap = new HashMap<String,String>();//存储使用了那些代码表(field_tab_code)
			
			StringBuffer tmpSql = new StringBuffer("select "); 
			
			//CITE_SQL 用来拼接is_cite=1的SQL
			StringBuffer citeSQL = new StringBuffer();
			
			//LEFT_SQL 用来拼接left join sql
			StringBuffer joinSQL = new StringBuffer();
			
			for (Map<String, Object> map : hrStatisticTabStrucList) {
				
				tmpSql.append(map.get("TAB_CODE") + "." + map.get("COL_CODE")+",");
				
				if("04".equals(map.get("COM_TYPE_CODE"))){
					
					tmpSql.append("substr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",instr("+map.get("TAB_CODE") + "." + map.get("COL_CODE")+",'/',-1,1)+1) as  SHORT_" + map.get("COL_CODE")+",");
				
				}

				tabCodeMap.put(map.get("TAB_CODE").toString(), map.get("TAB_CODE").toString());
				
				//处理引用代码表
				map.putAll(hrStoreTabColMap.get(map.get("TAB_CODE").toString() + map.get("COL_CODE").toString()));
				
				if(map.get("FIELD_TAB_CODE") == null || "".equals(map.get("FIELD_TAB_CODE"))){continue;}
				
				//为了防止一个表重复使用 同一个字段 所以使用别名
				String alias = map.get("FIELD_TAB_CODE") + "_" + map.get("COL_CODE");
			
				//如果别名过长可能会引起oracle错误 所以判断截取
				if(alias.length() > 30){alias = alias.substring(0, 30);}
				
				tmpSql.append(alias+".field_col_name as "+map.get("COL_CODE")+"_TEXT,");
				
				Map<String,Object> tmpHrFiiedMap = hrFiiedMap.get(map.get("FIELD_TAB_CODE"));
				
				Integer isCite = Integer.parseInt(tmpHrFiiedMap.get("IS_CITE").toString());

				if(isCite == 0){
					
					fieldTabCodeMap.put(map.get("FIELD_TAB_CODE").toString(), map.get("FIELD_TAB_CODE").toString());

					joinSQL.append(" left join hr_fiied "+alias+" on to_char(");
					
					joinSQL.append(map.get("TAB_CODE")+"."+map.get("COL_CODE"));

					//拼接变更字段
					if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){
						
						joinSQL.append("||'@'||"+map.get("TAB_CODE")+"."+map.get("CHANGE_COL_CODE"));
					}

					joinSQL.append(") = " +alias+".field_col_code and ");
					
					joinSQL.append(alias+".field_tab_code = '"+ map.get("FIELD_TAB_CODE") +"' ");
					
				}else{
					
					citeSQL.append(alias+" as (").append(tmpHrFiiedMap.get("CITE_SQL") == null?"select null as FIELD_COL_CODE,null as FIELD_COL_NAME from dual":tmpHrFiiedMap.get("CITE_SQL")).append("),");
					
					joinSQL.append(" left join "+alias+" "+alias+" on to_char(");
					
					joinSQL.append(map.get("TAB_CODE")+"."+map.get("COL_CODE"));
					
					//拼接变更字段
					if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){
						joinSQL.append("||'@'||"+map.get("TAB_CODE")+"." + map.get("CHANGE_COL_CODE"));
					}
					
					joinSQL.append(") = " +alias+".field_col_code ");
					
				}

			}
			
			tmpSql.deleteCharAt(tmpSql.length() - 1);
			

			//第二部 构建from left join 
			if(tabCodeMap.containsKey("HOS_EMP")){//拼接默认主表 
				
				tmpSql.append(" from HOS_EMP HOS_EMP");
				
				tabCodeMap.remove("HOS_EMP");
				
			}else{
				
				tmpSql.append(" from HOS_EMP HOS_EMP");
				
			}
			
			for(String key : tabCodeMap.keySet()){

				tmpSql.append(" right join " + key + " " + key);
				
				//数据表构建的三个默认条件 1.职工ID必选 2.group_id、host_id可以不选
				tmpSql.append(" on HOS_EMP.EMP_ID = " + key + ".EMP_ID");
				
				if(hrStoreTabColMap.get(key + "GROUP_ID") != null){
					
					tmpSql.append(" and " + key + ".GROUP_ID = @group_id");
					
				}
				
				if(hrStoreTabColMap.get(key + "HOS_ID") != null){
					
					tmpSql.append(" and " + key + ".HOS_ID = @hos_id");
					
				}
				
			}
			
			if(joinSQL.length() > 0){tmpSql.append(joinSQL);}

			//第四部 构建where 
			
			tmpSql.append(" where HOS_EMP.GROUP_ID = @group_id AND HOS_EMP.HOS_ID = @hos_id");
			
			Map<String,String> searchMap = getPageSearchMap(entityMap);
			
			for(String col_code :searchMap.keySet()){
				
				tabCodeMap.put("HOS_EMP", "HOS_EMP");//放默认表进来
				
				for(String tab_code : tabCodeMap.keySet()){
					
					Map<String,Object> map = hrStoreTabColMap.get(tab_code+col_code);
					
					if(map == null){continue;}
					
					map.put("COL_VALUE", searchMap.get(col_code));
					
					tmpSql.append("AND "+ tab_code + "."+ col_code);

					if(map.get("IS_CHANGE") != null && Integer.parseInt(map.get("IS_CHANGE").toString()) == 1){//拼接变更
						
						tmpSql.append("||'@'||"+tab_code+"." + map.get("CHANGE_COL_CODE"));
						
						map.put("DATA_TYPE", "IS_CHANGE");
					}
					
					tmpSql.append(" = "+ dataTypeStr(map) +" ");
				}

			} 
			
			//第三部 构建 档案库人员限定配置 
			List<Map<String, Object>> hrStatisticConditionList = hrCommonMapper.queryHrStatisticCondition(entityMap);
			
			if(hrStatisticConditionList.size() > 0){
				tmpSql.append(" and ");
			}
			
			for (Map<String, Object> map : hrStatisticConditionList) {
				
				map.putAll(hrStoreTabColMap.get(map.get("TAB_CODE").toString()+map.get("COL_CODE")));
				tmpSql.append(joinCondition(map));

			}
			tmpSql.append(" and exists (select 1 from v_user_data_perm b"+
			           " where HOS_EMP.group_id = b.group_id"+
			              " and HOS_EMP.hos_id = b.hos_id"+
			              " and to_char(HOS_EMP.dept_id) = b.perm_code"+
			              " and b.user_id ="+entityMap.get("user_id")+
			              " and b.TABLE_CODE =upper('HOS_DEPT_DICT')"+
			              " and (b.is_read = 1))");
			//第五步 构建order by
			tmpSql.append(" order by HOS_EMP.EMP_CODE");
			
			
			//第六步组装整体SQL
			StringBuffer sql = new StringBuffer();
			
			//withSQL 用来拼接is_cite=0的内置字典
			String hrFiiedSQL = hrFiiedTabStrucSql(fieldTabCodeMap);
			
			if(hrFiiedSQL.length() >0 || citeSQL.length() >0){sql.append("with ");}

			if(hrFiiedSQL.length() >0){sql.append(hrFiiedSQL);}
			
			if(citeSQL.length() >0){sql.append(citeSQL.deleteCharAt(citeSQL.length()-1));}
			
			sql.append(tmpSql);

			/**
			 * 处理表头
			 * */
			
			int defColWidth = MyConfig.getSysPara("06003") == null ? 120 : Integer.parseInt(MyConfig.getSysPara("06003"));// 表格默认列宽

			List<Map<String, Object>> fieldItemsList = new ArrayList<Map<String, Object>>();
			
			for (Map<String,Object> map: hrStatisticTabStrucList) {
				
				map.putAll(hrStoreTabColMap.get(map.get("TAB_CODE").toString()+map.get("COL_CODE")));
				
				if ("".equals(map.get("IS_VIEW_TAB")) || "0".equals(map.get("IS_VIEW_TAB").toString())) {continue;}

				LinkedHashMap<String, Object> fieldItemMap = new LinkedHashMap<String, Object>();
				/*	
				fieldItemMap.put("name", map.get("COL_CODE"));
				
				fieldItemMap.put("display", map.get("COL_NAME_SHOW"));
				
				fieldItemMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));// 表格列宽度
				
				fieldItemMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));// 对齐方式
				
				fieldItemMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);// 对齐方式
				
				fieldItemsList.add(fieldItemMap);*/
				
				
				//处理附件上传 显示短路径
				if("04".equals(map.get("COM_TYPE_CODE"))){
					
					fieldItemMap = new LinkedHashMap<String, Object>();
					
					fieldItemMap.put("name", "SHORT_"+map.get("COL_CODE"));
					
					fieldItemMap.put("display", map.get("COL_NAME_SHOW"));
					// 表格列宽度
					fieldItemMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
					// 对齐方式
					fieldItemMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
					
					fieldItemMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);

					fieldItemsList.add(fieldItemMap);
					
					fieldItemMap = new LinkedHashMap<String, Object>();
					
					fieldItemMap.put("name", map.get("COL_CODE"));
					
					fieldItemMap.put("display", map.get("COL_NAME_SHOW")+"全路径");
					// 表格列宽度
					fieldItemMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
					// 对齐方式
					fieldItemMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
					
					fieldItemMap.put("hidden", true);

					fieldItemsList.add(fieldItemMap);
					
				}else{
					
					fieldItemMap = new LinkedHashMap<String, Object>();
					
					fieldItemMap.put("name", map.get("COL_CODE"));
					
					fieldItemMap.put("display", map.get("COL_NAME_SHOW"));
					// 表格列宽度
					fieldItemMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));
					// 对齐方式
					fieldItemMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));
					
					fieldItemMap.put("hidden", map.get("FIELD_TAB_CODE") !=null ? true : false);

					fieldItemsList.add(fieldItemMap);
					
				}
				if(map.get("FIELD_TAB_CODE") !=null && !"".equals(map.get("FIELD_TAB_CODE"))){
					
					fieldItemMap = new LinkedHashMap<String, Object>();
					
					fieldItemMap.put("name", map.get("COL_CODE")+"_TEXT");
					
					fieldItemMap.put("display", map.get("COL_NAME_SHOW"));
					
					fieldItemMap.put("width", map.get("COL_WIDTH") == null ? defColWidth : map.get("COL_WIDTH"));// 表格列宽度
					
					fieldItemMap.put("align", map.get("TEXT_ALIGN") == null ? "left" : map.get("TEXT_ALIGN"));// 对齐方式
					
					fieldItemsList.add(fieldItemMap);
					
				}

			}
			
			Map<String,Object> headMap = new HashMap<String,Object>();
			
			headMap.put("columns", fieldItemsList);//主表表头
			
			headMap.put("tmpSQL", replaceConstant(sql.toString(),entityMap));
			
			
			return JSONArray.toJSONString(headMap);
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败", e);
		}
	}
	
	/**
	 * 查询简单统计数据 分页
	 */
	@Override
	public String queryHrStatisticCustom(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			entityMap.put("tmpQuerySql", entityMap.get("tmpSQL"));
			
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			if (sysPage.getTotal() == -1) {

				List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap);

				return ChdJson.toJson(list);

			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(entityMap, rowBounds);

				PageInfo page = new PageInfo(list);

				return ChdJson.toJson(list, page.getTotal());

			}		
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("操作失败", e);
		}
		
	}

	@Override
	public String queryHrHosSelectCite(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> fiiedDataList = new ArrayList<Map<String,Object>>();
		String sql=entityMap.get("sql").toString();
		
		
		String tmpSQL= replaceConstant(sql.toString(),entityMap);
		StringBuffer buffer =new  StringBuffer();
		
		if(entityMap.containsKey("key")){
	

		
		buffer.append("select * from ( ");
		buffer.append(tmpSQL);
		buffer.append(" ) t1");
			buffer.append(" where  t1.field_col_code like ( '%'||UPPER('"+entityMap.get("key").toString()+"')||'%' ) or t1.field_col_name like '%"+entityMap.get("key").toString()+"%'  ");
              fiiedDataList = (List<Map<String, Object>>)hrCommonMapper.queryHrHosSelectCite(buffer.toString());
		}else{
			 fiiedDataList = (List<Map<String, Object>>)hrCommonMapper.queryHrHosSelectCite(tmpSQL);
		}
		
		for (Map<String, Object> map : fiiedDataList) {
			Map<String, Object> option = new HashMap<String, Object>();
			option.put("id", map.get("FIELD_COL_CODE"));
			option.put("text", map.get("FIELD_COL_NAME"));
			option.put("label", map.get("FIELD_COL_NAME"));
			options.add(option);
		}
		
		return JSONArray.toJSONString(options);
		
	}

	@Override
	public String queryHrHosSelect(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> options = new ArrayList<Map<String, Object>>();
		List<Map<String, Object>> list = hrCommonMapper.queryHrHosSelect(entityMap);
		for (Map<String, Object> map : list) {
			Map<String, Object> option = new HashMap<String, Object>();
			option.put("id", map.get("FIELD_COL_CODE"));
			option.put("text", map.get("FIELD_COL_NAME"));
			option.put("label", map.get("FIELD_COL_NAME"));
			options.add(option);
		}
		return JSONArray.toJSONString(options);
	}
    /**
                *          批量修改
     */
	@Override
	public String batchEmpUpate(Map<String, Object> mapVo) throws DataAccessException {

 		List<String> dataList= new ArrayList<String>();

		//获取主集和子集表名
  		String tab_code   = mapVo.get("table_code").toString();
		String child_code = mapVo.get("child_code").toString();
	
	
		
		mapVo.put("tmpQuerySql", mapVo.get("mainSQL").toString());

		
		
		// 前台修改数据
		@SuppressWarnings("rawtypes")
		Map modListVo = JSON.parseObject(String.valueOf(mapVo.get("modData")), Map.class);
		
		if (modListVo == null || modListVo.size() == 0) {
			
			return "{\"msg\":\"未获取到修改数据.\",\"state\":\"true\"}";
			
		}
		
		try {
			
			List<Map<String,Object>> list = hrCommonMapper.queryQuerySQL(mapVo);
		
			/**通过档案库分类编码store_type_code获取所有数据表 比填写字段*/
			List<Map<String, Object>> hrStoreColSetList = hrCommonMapper.queryHrStoreColSet(mapVo);

			LinkedHashMap<String,Map<String,Object>> hrStoreTabColMap = new LinkedHashMap<String,Map<String,Object>>();
			
			for (Map<String, Object> tmpHrColStrucSetMap : hrStoreColSetList) {
				
				hrStoreTabColMap.put(tmpHrColStrucSetMap.get("TAB_CODE").toString()+tmpHrColStrucSetMap.get("COL_CODE"), tmpHrColStrucSetMap);
				
			}

			for (Map<String, Object> empDataMap : list) {
				//主集修改
				StringBuffer mainTmpSql = new StringBuffer();
				
				mainTmpSql.append(" update " + tab_code + "  set ");
				
				StringBuffer mainTmpSqlValue = new StringBuffer();
				
				//主集新增
				StringBuffer mainInsertSql = new StringBuffer();
			
				
				mainInsertSql.append(" insert into " + tab_code + " (group_id, hos_id,emp_id, ");
				
				StringBuffer mainInsertValue = new StringBuffer();
				
				//子集修改
				StringBuffer tmpSql = new StringBuffer();
				
				tmpSql.append(" update " + child_code + "  set ");
				
				StringBuffer tmpSqlValue = new StringBuffer();
				
				//子集新增
				StringBuffer childInsertSql = new StringBuffer();
						
				childInsertSql.append(" insert into " + child_code + " (group_id, hos_id,emp_id, ");
				
				StringBuffer childInsertValue = new StringBuffer();
				
			  for (Object key : modListVo.keySet()) {
				
				String value = (String) modListVo.get(key);
				 key=key.toString().split("\\.")[1];
			
					
					//处理主集tab_code
					if (hrStoreTabColMap.get(tab_code+key) != null) {
						
					/*	if ( empDataMap.get(key)!=value || MAIN_TABLE_NAME.equals(tab_code)) {*/
						
							Map<String,Object> map = new HashMap<String, Object>();
							if(key.equals("DEPT_ID")) {
								
								mainTmpSql.append("Dept_no ="+value.split("@")[1]+",");
								value= value.split("@")[0];
							}
							map.put("COL_VALUE",value );
							map.put("DATA_TYPE", hrStoreTabColMap.get(tab_code+key).get("DATA_TYPE"));
							
							mainTmpSqlValue.append(key + " = " + dataTypeStr(map) + " ,");
							
						/*} */		
						/*if(!MAIN_TABLE_NAME.equals(tab_code)) {
							
							if (empDataMap.get(key)==null || empDataMap.get(key).toString().equals("")  )  {
								
								
								mainInsertSql.append(key+",");
	                          Map<String,Object> map = new HashMap<String, Object>();
									
									map.put("COL_VALUE",value );
									map.put("DATA_TYPE", hrStoreTabColMap.get(tab_code+key).get("DATA_TYPE"));
									mainInsertValue.append(dataTypeStr(map)+",");
							
							}
						}*/
					
						
						
					}
					
					//处理子集child_code
					if(hrStoreTabColMap.get(child_code+key) != null && !child_code.equals(tab_code)){
						
				/*		if (empDataMap.get(key)!=null  && !empDataMap.get(key).toString().equals(value)) {*/
							
                            Map<String,Object> map = new HashMap<String, Object>();
							
							map.put("COL_VALUE",value );
							map.put("DATA_TYPE", hrStoreTabColMap.get(child_code+key).get("DATA_TYPE"));
						
							tmpSqlValue.append(key + " = " + dataTypeStr(map) + " ,");
							
						/*} 	*/
					/*	if (empDataMap.get(key)==null || empDataMap.get(key).toString().equals("")) {
							//定义新增 修改 查询
							//StringBuffer insertSql = new StringBuffer();
							
						
							childInsertSql.append(key+",");
                          Map<String,Object> map = new HashMap<String, Object>();
							
							map.put("COL_VALUE",value );
							map.put("DATA_TYPE", hrStoreTabColMap.get(child_code+key).get("DATA_TYPE"));
							childInsertValue.append(dataTypeStr(map)+",");
							
							
						}*/
					}

					
				}
			  
				//拼接主集新增sql
				mainInsertSql=mainInsertSql.deleteCharAt(mainInsertSql.length() - 1);
				mainInsertSql.append( " ) VALUES  ("+empDataMap.get("GROUP_ID")+","+empDataMap.get("HOS_ID")+","+empDataMap.get("EMP_ID")+",");
			   if(mainInsertValue.length()>1) {
				   mainInsertValue=mainInsertValue.deleteCharAt(mainInsertValue.length() - 1);
					
					mainInsertValue.append(")");
					
					mainInsertSql.append(mainInsertValue);
					
					dataList.add(mainInsertSql.toString());   
			   }
			  //拼接主集修改sql
			   if(mainTmpSqlValue.length()>1) {
				   mainTmpSqlValue.deleteCharAt(mainTmpSqlValue.length() - 1);
				    mainTmpSql.append(mainTmpSqlValue);
					mainTmpSql.append(" where");
					mainTmpSql.append(" emp_id =" + empDataMap.get("EMP_ID"));
					mainTmpSql.append(" and group_id =" + empDataMap.get("GROUP_ID"));
					mainTmpSql.append(" and hos_id =" + empDataMap.get("HOS_ID"));
					//tmpSql.append(conditionSql);
					// mapBufferMap.put("updateSql", tmpSql);
					dataList.add(mainTmpSql.toString());
			   }
			 
				
				//拼接子集新增sql
				childInsertSql=childInsertSql.deleteCharAt(childInsertSql.length() - 1);
				
			  childInsertSql.append( " ) VALUES  ("+empDataMap.get("GROUP_ID")+","+empDataMap.get("HOS_ID")+","+empDataMap.get("EMP_ID")+",");
				
			  if(childInsertValue.length()>1) {
				  
				  childInsertValue=childInsertValue.deleteCharAt(childInsertValue.length() - 1);
				  
				     childInsertValue.append(")");
				     
					childInsertSql.append(childInsertValue);
					
					dataList.add(childInsertSql.toString());  
			  }
			  //拼接子集修改sql
			  if(tmpSqlValue.length()>1) {
				  tmpSqlValue.deleteCharAt(tmpSqlValue.length() - 1);
				    tmpSql.append(tmpSqlValue);
					tmpSql.append(" where");
					tmpSql.append(" emp_id =" + empDataMap.get("EMP_ID"));
					tmpSql.append(" and group_id =" + empDataMap.get("GROUP_ID"));
					tmpSql.append(" and hos_id =" + empDataMap.get("HOS_ID"));
					// mapBufferMap.put("updateSql", tmpSql);
					dataList.add(tmpSql.toString());
			  }
			  

			}
			
			
          
			if (dataList.size() > 0 ) {
				
				List<String> stringSqlList= new  ArrayList<String>();
				
				for (int i=0;  i<  dataList.size(); i++) {
					
					stringSqlList.add(dataList.get(i));
					
					if( i>0 && i%500==0 || i == dataList.size() - 1) {
						
						baseCRUDMapper.batchEmpUpate(stringSqlList);
						
					}
                     
				}
				
			}

			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
	} catch (Exception e) {
		
		logger.error(e.getMessage(), e);
		
		throw new SysException("操作失败", e);
	}
	
	}

	@Override
	public String queryQueFormLeft(Map<String, Object> entityMap, int colNum) throws DataAccessException {
		entityMap.put("is_view", 1);
		List<HrColumn> hrColStrucList = hrCommonMapper.queryColJoinQueSetList(entityMap);
		return queryQueFormLeft(entityMap, colNum, hrColStrucList);
	}
	/**
	 * 查询表单信息
	 * 
	 * @param entityMap
	 * @param colNum
	 * @param hrColStrucList
	 * @return
	 * @throws DataAccessException
	 */
	private String queryQueFormLeft(Map<String, Object> entityMap, int colNum, List<HrColumn> hrColStrucList) throws DataAccessException {
		
		LinkedHashMap<String,Map<String,Object>> citeFiiedMap = new LinkedHashMap<String,Map<String,Object>>();
		//int defFieldWidth = MyConfig.getSysPara("06004") == null ? 180 : Integer.parseInt(MyConfig.getSysPara("06004"));
		
		
		// 更新数据集
				Map modlistVo = JSON.parseObject(String.valueOf(entityMap.get("Param")), Map.class);
		Map<String, Object> data = null;
		
		if (entityMap.get("EMP_ID") != null && StringUtils.isNotBlank(entityMap.get("EMP_ID").toString())) {
			// data = (Map<String, Object>) entityMap.get("data");
			List<Map<String, Object>> dataList = buildQueryGridDataSQL(entityMap);
			data = dataList.get(0);
		}
		
		// 组件（下拉框、文本框等）
		List<Map<String, Object>> comTypeList = hrColStrucMapper.queryHrComType(entityMap);
		Map<String, Object> comTypeMap = new HashMap<String, Object>();
		if (comTypeList != null && comTypeList.size() > 0) {
			for (Map<String, Object> map : comTypeList) {
				String key = String.valueOf(map.get("com_type_code"));
				String val = String.valueOf(map.get("com_type_nature"));
				comTypeMap.put(key, val);
			}
		}
		List<Map<String, Object>> hrFiiedList = hrCommonMapper.queryHrFiied(entityMap);
		
		
		for (Map<String, Object> citeHrFiiedMap : hrFiiedList) {
						
			citeFiiedMap.put(citeHrFiiedMap.get("FIELD_TAB_CODE").toString(), citeHrFiiedMap);
						
		}
		Map<String, Object> formMap = new HashMap<String, Object>();
		formMap.put("colNum", colNum);// 页面form表单列的个数
		
		List<Map<String, Object>> fieldItems = new ArrayList<Map<String, Object>>();
		
		for (HrColumn col : hrColStrucList) {
			if(col.getTab_code().equals(entityMap.get("tab_code"))) {
			 for(Object key : modlistVo.keySet()){
				   String value = (String) modlistVo.get(key);
				   if(!("SEARCH_"+entityMap.get("tab_code")+"."+col.getCol_code()).equals(key)) {
						continue;
					}else {
						// 字段类型
						String type = "text";
						if (comTypeMap != null && comTypeMap.size() > 0 && col.getCom_type_code() != null) {
							type = String.valueOf(comTypeMap.get(col.getCom_type_code()));
						}
						
						// 字段宽度
						//int field_width = col.getField_width() == null ? 1 : col.getField_width();
						
						Map<String, Object> fieldItem = new HashMap<String, Object>();
						
						// 是否区间查询
						if (col.getIs_section() != null && col.getIs_section() == 1) {
							if ("DATE".equals(col.getData_type())) {
								fieldItem.put("id", col.getCol_code().toUpperCase());
								fieldItem.put("name", col.getCol_name_show());
								fieldItem.put("type", type);
								Map<String, Object> rangeMap = new LinkedHashMap<String, Object>();
								rangeMap.put("range", true);
								fieldItem.put("OPTIONS", rangeMap);
								rangeMap.put("defaultValue", value);
								fieldItem.put("required",false);
								
							} else {
								fieldItem.put("id", col.getCol_code().toUpperCase());
								fieldItem.put("name", col.getCol_name_show());
								fieldItem.put("type", "range");
								Map<String, Object> rangeIdMap = new LinkedHashMap<String, Object>();
								String[] rangeIdArr = { col.getCol_code().toUpperCase() + "_BEG", col.getCol_code().toUpperCase() + "_END" };
								rangeIdMap.put("rangeId", rangeIdArr);
								fieldItem.put("OPTIONS", rangeIdMap);
								rangeIdMap.put("defaultValue", value);
								fieldItem.put("required",false);
							
							}
						} else {
							
							fieldItem.put("id", col.getCol_code().toUpperCase());
							fieldItem.put("name", col.getCol_name_show());
							fieldItem.put("type", type);
							
						
						
							
							// 代码表数据
							Map<String, Object> optionsMap = new HashMap<String, Object>();
							if (StringUtils.isNotBlank(col.getField_tab_code())) {
						
								entityMap.put("tab_code", col.getTab_code());
								entityMap.put("col_code", col.getCol_code());

								if("0".equals(citeFiiedMap.get(col.getField_tab_code()).get("IS_CITE").toString())){
									Map<String,Object> urlMap= new HashMap<String, Object>();
									urlMap.put("group_id", SessionManager.getGroupId());
									urlMap.put("hos_id", SessionManager.getHosId());
									urlMap.put("table_code", col.getField_tab_code());
									
									List<Map<String, Object>> list = hrCommonMapper.queryHrHosSelectLeft(urlMap);
									for (Map<String, Object> map : list) {
										Map<String, Object> option = new HashMap<String, Object>();
										option.put("id", map.get("FIELD_COL_CODE"));
										if(value.equals(map.get("FIELD_COL_CODE").toString())) {
											optionsMap.put("defaultValue", map.get("FIELD_COL_NAME"));
										}
										
									
									}
									
								}else {
									
									String sqlCite = String.valueOf(citeFiiedMap.get(col.getField_tab_code()).get("CITE_SQL"));
									
									sqlCite = sqlCite.replaceAll("\n", " ");
									
									sqlCite = sqlCite.replaceAll("\r", " ");
									
									sqlCite = sqlCite.replaceAll("\t", " ");

									optionsMap.put("url","queryHrHosSelectCite.do?isCheck=false&sql="+sqlCite);
								
									String tmpSQL= replaceConstant(sqlCite,entityMap);
									
								
									List<Map<String, Object>> fiiedDataList = (List<Map<String, Object>>)hrCommonMapper.queryHrHosSelectCite(tmpSQL);
									for (Map<String, Object> map : fiiedDataList) {
										if(value.equals(map.get("FIELD_COL_CODE").toString())) {
											optionsMap.put("defaultValue", map.get("FIELD_COL_NAME"));
							
									}
									
								}
						
								
							
							}
							
							fieldItem.put("OPTIONS", optionsMap);
						}
						fieldItems.add(fieldItem);
						
					}
				
				   
				  }
			}
			if(col.getTab_code().equals(entityMap.get("child_code")) && !entityMap.get("child_code").toString().equals(entityMap.get("tab_code").toString()) &&!col.getCol_code().equals("EMP_ID")) { {

				 for(Object key : modlistVo.keySet()){
					   String value = (String) modlistVo.get(key);
					   if(!("SEARCH_"+entityMap.get("child_code")+"."+col.getCol_code()).equals(key)) {
							continue;
						}else {
							// 字段类型
							String type = "text";
							if (comTypeMap != null && comTypeMap.size() > 0 && col.getCom_type_code() != null) {
								type = String.valueOf(comTypeMap.get(col.getCom_type_code()));
							}
							
							// 字段宽度
							int field_width = col.getField_width() == null ? 1 : col.getField_width();
							
							Map<String, Object> fieldItem = new HashMap<String, Object>();
							
							// 是否区间查询
							if (col.getIs_section() != null && col.getIs_section() == 1) {
								if ("DATE".equals(col.getData_type())) {
									fieldItem.put("id", col.getCol_code().toUpperCase());
									fieldItem.put("name", col.getCol_name_show());
									fieldItem.put("type", type);
									Map<String, Object> rangeMap = new LinkedHashMap<String, Object>();
									rangeMap.put("range", true);
									fieldItem.put("OPTIONS", rangeMap);
									rangeMap.put("defaultValue", value);
									fieldItem.put("required",false);
								
								} else {
									fieldItem.put("id", col.getCol_code().toUpperCase());
									fieldItem.put("name", col.getCol_name_show());
									fieldItem.put("type", "range");
									Map<String, Object> rangeIdMap = new LinkedHashMap<String, Object>();
									String[] rangeIdArr = { col.getCol_code().toUpperCase() + "_BEG", col.getCol_code().toUpperCase() + "_END" };
									rangeIdMap.put("rangeId", rangeIdArr);
									fieldItem.put("OPTIONS", rangeIdMap);
									rangeIdMap.put("defaultValue", value);
									fieldItem.put("required",false);
								
								}
							} else {
								
								fieldItem.put("id", col.getCol_code().toUpperCase());
								fieldItem.put("name", col.getCol_name_show());
								
							
								
						
							
								// 代码表数据
								Map<String, Object> optionsMap = new HashMap<String, Object>();
								if (StringUtils.isNotBlank(col.getField_tab_code())) {
									// entityMap.put("field_tab_code", col.getField_tab_code());
									entityMap.put("tab_code", col.getTab_code());
									entityMap.put("col_code", col.getCol_code());

									if("0".equals(citeFiiedMap.get(col.getField_tab_code()).get("IS_CITE").toString())){
										Map<String,Object> urlMap= new HashMap<String, Object>();
										urlMap.put("group_id", SessionManager.getGroupId());
										urlMap.put("hos_id", SessionManager.getHosId());
										urlMap.put("table_code", col.getField_tab_code());
										
										List<Map<String, Object>> list = hrCommonMapper.queryHrHosSelectLeft(urlMap);
										for (Map<String, Object> map : list) {
											Map<String, Object> option = new HashMap<String, Object>();
											option.put("id", map.get("FIELD_COL_CODE"));
											if(value.equals(map.get("FIELD_COL_CODE").toString())) {
												optionsMap.put("defaultValue", map.get("FIELD_COL_NAME"));
											}
											
										
										}
									}else {
										
										String sqlCite = String.valueOf(citeFiiedMap.get(col.getField_tab_code()).get("CITE_SQL"));
										
										sqlCite = sqlCite.replaceAll("\n", " ");
										
										sqlCite = sqlCite.replaceAll("\r", " ");
										
										sqlCite = sqlCite.replaceAll("\t", " ");

										optionsMap.put("url","queryHrHosSelectCite.do?isCheck=false&sql="+sqlCite);
										String tmpSQL= replaceConstant(sqlCite,entityMap);
										
										
										List<Map<String, Object>> fiiedDataList = (List<Map<String, Object>>)hrCommonMapper.queryHrHosSelectCite(tmpSQL);
										for (Map<String, Object> map : fiiedDataList) {
											if(value.equals(map.get("FIELD_COL_CODE").toString())) {
												optionsMap.put("defaultValue", map.get("FIELD_COL_NAME"));
											}
											
										//	options.add(option);
										}
									}
								
									
									
								}
								
								fieldItem.put("OPTIONS", optionsMap);
							}
							fieldItems.add(fieldItem);
							
						}
					
					   
					  }
					
				
			}
			
			
		}
		}
		formMap.put("fieldItems", fieldItems);
		}
		return JSONArray.toJSONString(formMap);
	}
	public static Date fromDoubleToDateTime(double OADate) 
	{
	    long num = (long) ((OADate * 86400000.0) + ((OADate >= 0.0) ? 0.5 : -0.5));
	    if (num < 0L) {
	        num -= (num % 0x5265c00L) * 2L;
	    }
	    num += 0x3680b5e1fc00L;
	    num -=  62135596800000L;

	    return new Date(num);
	}
}
