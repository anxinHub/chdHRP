package com.chd.hrp.hr.serviceImpl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.ViewToLgerUI;
import com.chd.hrp.hr.dao.HosCommonMapper;
import com.chd.hrp.hr.dao.QueryMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.dao.sc.HrTableDesignMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.entity.sc.HrTableColumnConfig;
import com.chd.hrp.hr.entity.sc.HrTableColumnFormConfig;
import com.chd.hrp.hr.entity.sc.HrTableColumnGridConfig;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.QueryService;
import com.chd.hrp.hr.util.StringUtils;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;

@Service("queryService")
public class QueryServiceImpl implements QueryService {
	private static Logger logger = Logger.getLogger(QueryServiceImpl.class);

	@Resource(name = "queryMapper")
	private final QueryMapper queryMapper = null;

	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;

	@Resource(name = "hrTableDesignMapper")
	private final HrTableDesignMapper hrTableDesignMapper = null;

	@Resource(name = "hrFiledTabStrucMapper")
	private final HrFiledTabStrucMapper hrFiledTabStrucMapper = null;
	
	@Resource(name = "hosCommonMapper")
	private final HosCommonMapper hosCommonMapper = null;
	
	@SuppressWarnings("unchecked")
	@Override
	public String query(Map<String, Object> entityMap) throws DataAccessException {

		try {
			// 根据查询设计器查询
			HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);
			//根据设计器编码在hr_table_design中未找到对应数据
			if(hrTableDesign == null) {
				throw new SysException("{\"error\":\"请查看查询设计器[设计器编码]是否正确.\",\"state\":\"false\"}");
			}
			//根据设计器编码在hr_table_design中未找到对应SQL语句
			if(hrTableDesign.getDesign_query_sql() == null || "".equals(hrTableDesign.getDesign_query_sql())) {
				throw new SysException("{\"error\":\"请查看查询设计器SQL语句是否设置.\",\"state\":\"false\"}");
			}
			
			String querySql= matchAndReplaceSql(hrTableDesign.getDesign_query_sql(), entityMap);
			if(entityMap.containsKey("store_type_code")){
				/**档案库人员限定配置*/
				List<Map<String, Object>> hrStoreConditionList = hosCommonMapper.queryHrStoreCondition(entityMap);
				StringBuffer sql= new StringBuffer();
				sql.append("select * from ( ");
				
				
				sql.append(querySql+" ) ");
				for (Map<String, Object> map : hrStoreConditionList) {
					sql.append("  where");
					if(map.get("TAB_CODE").toString()!= null&& map.get("COL_CODE") != null){
						
						map.put(map.get("TAB_CODE").toString(),map.get("COL_CODE"));
				
						sql.append(joinCondition(map));
					}
				}	
				//替换取出SQL语句
				entityMap.put("sql",sql.toString());
			}else{
				//替换取出SQL语句
				entityMap.put("sql", querySql);
			}
		
			

			SysPage sysPage = new SysPage();
			sysPage = (SysPage) entityMap.get("sysPage");
			if (sysPage.getTotal() == -1) {
				List<Map<String, Object>> list = (List<Map<String, Object>>) queryMapper.query(entityMap);
				return toJsonLower(list);
			} else {
				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				List<Map<String, Object>> list = (List<Map<String, Object>>) queryMapper.query(entityMap,rowBounds);
				PageInfo page = new PageInfo(list);
				return toJsonLowerPage(list, page.getTotal());
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException("{\"error\":\"操作失败,数据库异常.\",\"state\":\"false\"}");
		}
	}

	@Override
	public Map<String, Object> queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		// 暂时的方法后期要根据查询设计器修改
		List<Map<String, Object>> list1 = new ArrayList<Map<String, Object>>();

		HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(entityMap);

		if (hrTableStruc != null && hrTableStruc.getTab_sql() != null && !"".equals(hrTableStruc.getTab_sql())) {

			list1 = toListMap(hrTableStruc.getTab_sql());

		}

		String sql = new String();

		for (Map<String, Object> map : list1) {

			// 如果sql_code匹配update方法取出sql

			if (map.get("sql_code").toString().equals(entityMap.get("design_code"))) {

				sql = map.get("sql_statement").toString();

			}
		}

		String add_sql = sql;
		// 获取数据列
		HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(entityMap);
		// 数据列中文替换成英文例如编码替换成code

		add_sql = matchAndReplaceColBycode(add_sql, hrTableStruc1);

		String sqlMap;

		sqlMap = matchAndReplaceSql(add_sql, entityMap);// 匹配替换

		entityMap.put("sql", sqlMap);

		Map<String, Object> map= queryMapper.queryByCode(entityMap);
		 
		 return toJsonLowerMap(map);
	}

	public String matchAndReplaceSql(String querySql, Map<String, Object> paraMap) {
		String column = null;
		String columnCol = null;
		// 将所有字符都转成小写 为分割处理做准备
		querySql = querySql.replaceAll("\n|\t|\r|\\s{1,}", " "); // 去掉所有换行符 制表符 多个空格转换为单个空格

		String[] splSql = querySql.split("(WHERE |AND |OR |ORDER |GROUP )");

		if (splSql.length == 0)
			return querySql;

		for (int i = 1; i < splSql.length; i++) {

			Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(splSql[i].toLowerCase());// 正则查找标识符变量

			while (matcher.find()) {

				columnCol = matcher.group();// 获取匹配到的参数

				column = columnCol.toLowerCase().replaceAll("\\[", "").replaceAll("\\]", "");

				if (paraMap.get(column) != null && !"".equals(paraMap.get(column))) {
					if (splSql[i].toString().indexOf(")") != -1 && columnCol.indexOf(")") != -1
							&& splSql[i].toString().indexOf("IN") == -1 && splSql[i].toString().indexOf("in") == -1) {

						querySql = querySql.replace(columnCol, "'" + paraMap.get(column) + "' ) ");// 取参数值
						querySql = querySql.replace(columnCol.toUpperCase(), "'" + paraMap.get(column) + "' ) ");// 取参数值大写
					} else if (splSql[i].toString().indexOf("IN (") != -1 || splSql[i].toString().indexOf("in (") != -1) {
						querySql = querySql.replace(columnCol, paraMap.get(column).toString());// 取参数值
						querySql = querySql.replace(columnCol.toUpperCase(), paraMap.get(column).toString());// 取参数值大写

					} else {
						querySql = querySql.replace(columnCol, "'" + paraMap.get(column) + "' ");// 取参数值
						querySql = querySql.replace(columnCol.toUpperCase(), "'" + paraMap.get(column) + "' ");// 取参数值大写
					}
				} else {

					// 当传入变量不存在是 删除该查询语句
					if (i == 1) {// 当i=1时 一定是where语句后第一条 所以没有 and、or 条件

						querySql = querySql.replace(splSql[i], "");

					} else {

						if (i+1<splSql.length&&splSql[i].toString().indexOf("TO_DATE") != -1
								&& splSql[i + 1].toString().indexOf("BY") == -1
								&& splSql[i].toString().indexOf("IN") == -1
								&& splSql[i].toString().indexOf("in") == -1) {

							querySql = querySql.replace("AND " + splSql[i], "").replace("OR " + splSql[i], "");
						} else if (i + 1 < splSql.length && splSql[i + 1].toString().indexOf("BY") != -1
								&& splSql[i].toString().indexOf("TO_DATE") != -1) {

							querySql = querySql.replace("AND " + splSql[i], ")").replace("OR " + splSql[i], ")");
						} else if (i + 1 < splSql.length && splSql[i + 1].toString().indexOf("BY") != -1
								&& splSql[i].toString().indexOf("TO_DATE") == -1) {

							querySql = querySql.replace("AND " + splSql[i], ")").replace("OR " + splSql[i], ")");
						} else if (splSql[i].toString().indexOf(")") != -1 && splSql[i].toString().indexOf("IN") == -1
								&& splSql[i].toString().indexOf("in") == -1) {

							querySql = querySql.replace("AND " + splSql[i], ")").replace("OR " + splSql[i], ")");
						} else if (splSql[i].toString().indexOf("IN (") != -1
								|| splSql[i].toString().indexOf("in (") != -1) {

							querySql = querySql.replace("AND " + splSql[i], "").replace("OR " + splSql[i], "");
						} else {
							querySql = querySql.replace("AND " + splSql[i], "").replace("OR " + splSql[i], "");
						}
					}

				}

			}

		}

		return querySql;
	}

	/**
	 * 替换成英文ByCODE
	 * 
	 * @param sql
	 * @return
	 */
	public String matchAndReplaceColBycode(String sql, HrTableStruc hrTableStruc1) {
		Map<String, Object> map = new HashMap<String, Object>();
		String result = sql // 将所有字符都转成小写 为分割处理做准备
				.replaceAll("\n|\t|\r|\\s{1,}", " "); // 去掉所有换行符 制表符 多个空格转换为单个空格
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);// #\\{.*?\\}
		// (@)(.*?),
		String value = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = toListMap(hrTableStruc1.getTab_col());
		/**
		 * 遍历数据列名称和code
		 */
		Map<String, Map<String, Object>> tabMap= new HashMap<String, Map<String, Object>>();
		Map<String, Object> tabDateMap= new HashMap<String,Object>();
		//获取数据列的数据格式
		for (Map<String, Object> map2 : list) {
		
				Map<String, Object> colMap=new HashMap<String, Object>();
	
				colMap.put("col_code", map2.get("col_code"));
				colMap.put("col_name", map2.get("col_name"));
				colMap.put("data_type_code", map2.get("data_type_code"));
				tabMap.put(map2.get("col_name").toString(),colMap);
		
		
		}
		while (matcher.find()) {

			String key = matcher.group();// 获取匹配到的参数

			String column = key.replaceAll("\\[", "").replaceAll("\\]", "");

			if (tabMap.get(column).get("col_name") == null) {
				value = key;
			} else {
               if(tabMap.get(column).get("data_type_code").equals("DATE")){
   				value =  " TO_DATE( [" +String.valueOf(tabMap.get(column).get("col_code").toString().toLowerCase()) + "] ,'yyyy-MM-dd')" ;

               }else{
				value = "[" + String.valueOf(tabMap.get(column).get("col_code").toString().toLowerCase()) + "]";
               }
			}

			result = result.replace(key, value);

		}

		return result;
	}

	public static List<Map<String, Object>> toListMap(String json) {
		List<Object> list = JSON.parseArray(json);

		List<Map<String, Object>> listw = new ArrayList<Map<String, Object>>();
		for (Object object : list) {
			Map<String, Object> ageMap = new HashMap<String, Object>();
			Map<String, Object> ret = (Map<String, Object>) object;// 取出list里面的值转为map
			listw.add(ret);
		}
		return listw;

	}

	@Override
	public List<Map<String, Object>> queryBaseInfoPtint(Map<String, Object> entityMap) throws DataAccessException {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		String sql = new String();
		/* new String(actrc.getTemplate_sql(),"UTF-8") */;

		// 根据查询设计器查询

		HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);

		if (hrTableDesign != null && hrTableDesign.getDesign_query_sql() != null
				&& !"".equals(hrTableDesign.getDesign_query_sql())) {
			sql = hrTableDesign.getDesign_query_sql().toString();

			entityMap.put("sql", matchAndReplaceSql(sql, entityMap));

			list = (List<Map<String, Object>>) queryMapper.query(entityMap);
			return toList(list, entityMap);

		} else {
			return list;
		}
	}
	
	@Override
	public Map<String, Object> queryBaseInfoPrintTemp(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, Object> reMap = new HashMap<String, Object>();

		List<Map<String, Object>> list= new ArrayList<Map<String,Object>>();

		HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);

		if (hrTableDesign != null && hrTableDesign.getDesign_query_sql() != null 
				&& !"".equals(hrTableDesign.getDesign_query_sql())) {
			String sql = hrTableDesign.getDesign_query_sql().toString();
	
			entityMap.put("sql",matchAndReplaceSql(sql,entityMap));
			
			list = (List<Map<String, Object>>) queryMapper.query(entityMap);
			
			String idColumn = "EMP_ID";
			if(entityMap.containsKey("id_column") && StringUtils.isNotEmpty(entityMap.get("id_column").toString())){
				idColumn = entityMap.get("id_column").toString();
			}
			
			for (Map<String, Object> map : list) {
				map.put("ID", map.get(idColumn).toString().toUpperCase());
			}
			reMap.put("main", list);

		}
		
		return reMap;
	}

	/**
	 * 转换内置的代码表数据
	 * 
	 * @param list
	 * @param list12
	 * @return
	 */
	public String toJsonLower(List<Map<String, Object>> list) {

		Map<String, Object> entityMap = new HashMap<String, Object>();

		ViewToLgerUI view = new ViewToLgerUI();

		view.setTotal(list.size());

		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();

		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map viewMap = new HashMap();

				for (String key : map.keySet()) {
					Object value = map.get(key);
					viewMap.put(key.toLowerCase(), value);

				}

				viewList.add(viewMap);

			}

		}

		view.setRows(viewList);

		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}

	/**
	 * 带分页替换代码表内置的数据
	 * 
	 * @param list
	 * @param totalCount
	 * @param list12
	 * @return
	 */
	public String toJsonLowerPage(List<Map<String, Object>> list, Long totalCount) {

		Map<String, Object> entityMap = new HashMap<String, Object>();

		ViewToLgerUI view = new ViewToLgerUI();

		view.setTotal(totalCount.intValue());

		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();

		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map viewMap = new HashMap();

				for (String key : map.keySet()) {

					Object value = map.get(key);
					viewMap.put(key.toLowerCase(), value);

				}

				viewList.add(viewMap);

			}

		}

		view.setRows(viewList);
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}

	public String toJson(List<?> list) {

		ViewToLgerUI view = new ViewToLgerUI();
		view.setTotal(list.size());
		view.setRows(list);
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}

	public List<Map<String, Object>> toList(List<Map<String, Object>> list, Map<String, Object> entityMap) {

		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();

		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map viewMap = new HashMap();

				for (String key : map.keySet()) {
					Object value = map.get(key);
					viewMap.put(key.toLowerCase(), value);

				}

				viewList.add(viewMap);

			}

		}

		return viewList;
	}
   //转换小写

	public Map<String, Object>  toJsonLowerMap( Map<String, Object> entityMap) {

		

	
		

				Map viewMap = new HashMap();

				for (String key : entityMap.keySet()) {
					Object value = entityMap.get(key);
					viewMap.put(key.toLowerCase(), value);

		}

		return viewMap;
	}
	@Override
	public String queryTree(Map<String, Object> entityMap) throws DataAccessException {

		try {

			String sql = new String();
			/* new String(actrc.getTemplate_sql(),"UTF-8") */;

			// 根据查询设计器查询

			HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);

			if (hrTableDesign != null && hrTableDesign.getDesign_query_sql() != null
					&& !"".equals(hrTableDesign.getDesign_query_sql())) {

				sql = hrTableDesign.getDesign_query_sql().toString();

				entityMap.put("sql", matchAndReplaceSql(sql, entityMap));

				List<Map<String, Object>> list = (List<Map<String, Object>>) queryMapper.query(entityMap);
				Map<String, Object> top = new HashMap<String, Object>();
				/*
				 * top.put("id", 0); top.put("pId", ""); top.put("DEPT_ID", 0);
				 * if(SessionManager.getTypeCode().equals("0")){ top.put("name",
				 * SessionManager.getHosSimple()); }else{ top.put("name",
				 * SessionManager.getGroupSimple()); }
				 * 
				 * list.add(top);
				 */
				return JSONArray.toJSONString(toList(list, entityMap));

			} else {
				return "{\"error\":\"操作失败,查询设计器未构建.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败,数据库异常.\",\"state\":\"false\"}");

		}

	}

	@Override
	public String queryJson(Map<String, Object> entityMap) throws DataAccessException {

		try {

			String sql = new String();
			/* new String(actrc.getTemplate_sql(),"UTF-8") */;

			// 根据查询设计器查询

			HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);

			if (hrTableDesign != null && hrTableDesign.getDesign_query_sql() != null
					&& !"".equals(hrTableDesign.getDesign_query_sql())) {

				sql = hrTableDesign.getDesign_query_sql().toString();

				entityMap.put("sql", matchAndReplaceSql(sql, entityMap));

				List<Map<String, Object>> list = (List<Map<String, Object>>) queryMapper.query(entityMap);
				return toJson(list);

			} else {
				return "{\"error\":\"操作失败,查询设计器未构建.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败,数据库异常.\",\"state\":\"false\"}");

		}
	}

	@Override
	public String queryJsonDetail(Map<String, Object> entityMap) throws DataAccessException {

		try {
			List<Map<String, Object>> reDataList = new ArrayList<Map<String, Object>>();// 返回页面List

			String sql = new String();
			/* new String(actrc.getTemplate_sql(),"UTF-8") */;

			// 根据查询设计器查询

			HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);

			if (hrTableDesign != null && hrTableDesign.getDesign_query_sql() != null
					&& !"".equals(hrTableDesign.getDesign_query_sql())) {

				// 获取数据列
				HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(entityMap);

				sql = hrTableDesign.getDesign_query_sql().toString();

				entityMap.put("sql", matchAndReplaceSql(sql, entityMap));

				List<Map<String, Object>> dataList = (List<Map<String, Object>>) queryMapper.query(entityMap);

				if (dataList == null || dataList.size() == 0) {
					return "{\"error\":\"操作失败,查询设计器未构建.\",\"state\":\"false\"}";

				}

				ArrayList<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();

				for (Map<String, Object> columnMap : dataList) {
					HrTableStruc hrTableStruc = hrTableStrucMapper.queryColSetByCode(entityMap);

					HrTableColumnConfig config = JSONObject.parseObject(hrTableStruc.getTab_view_col(),
							HrTableColumnConfig.class);
					Map<String, HrTableColumnFormConfig> formConfigMap = new HashMap<>();
					if (config.getFormSetData() != null && config.getFormSetData().size() > 0) {
						for (HrTableColumnFormConfig formConfig : config.getFormSetData()) {
							formConfigMap.put(formConfig.getCol_code(), formConfig);
						}
					}

					for (String key : columnMap.keySet()) {
						Map<String, Object> viewValue1 = new HashMap<String, Object>();
						HrTableColumnFormConfig tmpMap = formConfigMap.get(key);

						
						
						if (key.indexOf("_NAME") != -1) {
							continue;
						}
                       if(tmpMap!=null){
                    	   
                       
						if ("".equals(tmpMap.getIs_view()) || "0".equals(tmpMap.getIs_view().toString())) {
							continue;
						}

						viewValue1.put("text", tmpMap.getView_name());
						
						viewValue1.put("sort", tmpMap.getSort());


						viewValue1.put("com_type", tmpMap.getCom_type_code());

						if (tmpMap.getField_tab_code() != null && !"".equals(tmpMap.getField_tab_code())) {

							viewValue1.put("value", columnMap.get(key + "_TEXT"));

						} else {

							viewValue1.put("value", columnMap.get(key));

						}

						viewList.add(viewValue1);

					}
					}
				}

				HashMap<String, Object> reDataMap = new HashMap<String, Object>();

				reDataMap.put("tab_code", hrTableStruc1.getTab_code());

				reDataMap.put("tab_name", hrTableStruc1.getTab_name());
				  Collections.sort(viewList, new Comparator<Map<String, Object>>() {
			            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
			                Integer name1 = Integer.valueOf(o1.get("sort").toString()) ;//name1是从你list里面拿出来的一个 
			                Integer name2 = Integer.valueOf(o2.get("sort").toString()) ; //name1是从你list里面拿出来的第二个name
			                return name1.compareTo(name2);
			            }
			        });
				reDataMap.put("data", viewList);
				reDataList.add(reDataMap);
				return toJson(reDataList);

			} else {
				return "{\"error\":\"操作失败,查询设计器未构建.\",\"state\":\"false\"}";
			}

		} catch (Exception e) {

			logger.error(e.getMessage(), e);

			throw new SysException("{\"error\":\"操作失败,数据库异常.\",\"state\":\"false\"}");

		}
	}

	@Override
	public String queryHrExcelColumn(Map<String, Object> entityMap)
			throws DataAccessException {
		
		try {
			
			HrTableStruc hrTableStruc = hrTableStrucMapper.queryColSetByCode(entityMap);
			if (hrTableStruc != null && StringUtil.isNotEmpty(hrTableStruc.getTab_view_col())) {
				HrTableColumnConfig data = JSONObject.parseObject(hrTableStruc.getTab_view_col(),
						HrTableColumnConfig.class);
				List<Map<String, Object>> listData = new ArrayList<Map<String, Object>>();
				List<HrTableColumnGridConfig> list=data.getGridSetData();
				for (HrTableColumnGridConfig hrTableColumnGridConfig : list) {
					if(hrTableColumnGridConfig.getIs_view()==1){
						Map<String, Object> gridMap=new HashMap<String, Object>();
						gridMap.put("col_code", hrTableColumnGridConfig.getCol_code().toLowerCase());
						gridMap.put("col_name", hrTableColumnGridConfig.getCol_name());
						listData.add(gridMap);
					}
						
				}
			return  toJsonLower(listData);
		}
			} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e);
		}
		return null;
		
	}
	private String joinCondition(Map<String,Object> map){
		
		StringBuffer conditionSql = new StringBuffer();
		
		conditionSql.append(" ");
		/*conditionSql.append("and  ");*/
		if(map.get("L_BRACKET") != null){conditionSql.append(map.get("L_BRACKET"));}
		
		conditionSql.append(map.get("COL_CODE")+" " + map.get("CON_SIGN_NAME"));
if(map.get("COL_CODE").equals("DEPT_ID")){
	conditionSql.append(dataTypeStr(map).split("@")[0]);//根据字段数据类型 拼接语句
	
}else{
	conditionSql.append(dataTypeStr(map));
}
		
		
		if(map.get("R_BRACKET") != null){conditionSql.append(map.get("R_BRACKET"));}
		
		if(map.get("JOIN_SIGN_NAME") != null){conditionSql.append(" "+ map.get("JOIN_SIGN_NAME"));}
		
		
		return conditionSql.toString();
		
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
}
