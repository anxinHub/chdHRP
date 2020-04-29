/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.ibatis.jdbc.SQL;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.MyConfig;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.dao.sc.HrTableTypeMapper;
import com.chd.hrp.hr.entity.sc.HrTableColumn;
import com.chd.hrp.hr.entity.sc.HrTableColumnConfig;
import com.chd.hrp.hr.entity.sc.HrTableColumnFormConfig;
import com.chd.hrp.hr.entity.sc.HrTableColumnGridConfig;
import com.chd.hrp.hr.entity.sc.HrTableSql;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.entity.sc.HrTableType;
import com.chd.hrp.hr.service.base.HrTableManagerService;
import com.chd.hrp.hr.service.sc.HrTableStrucService;
import com.chd.hrp.hr.util.FileUtil;
import com.chd.hrp.hr.util.StringUtils;
import com.chd.hrp.hr.util.UIComponentBuilder;
import com.github.pagehelper.StringUtil;

/**
 * 
 * @Description: 数据表构建
 * @Table: HR_TAB_STRUC
 * @Author: zn
 * 
 */

@Service("hrTableStrucService")
public class HrTableStrucServiceImpl implements HrTableStrucService {

	private static Logger logger = Logger.getLogger(HrTableStrucServiceImpl.class);

	// 引入DAO操作
	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;

	@Resource(name = "hrTableTypeMapper")
	private final HrTableTypeMapper hrTableTypeMapper = null;

	@Resource(name = "hrTableManagerService")
	private final HrTableManagerService hrTableManagerService = null;

	@Override
	public String addHrTableStruc(Map<String, Object> entityMap) throws DataAccessException {

		// 查询表名是否存在
		int count = hrTableStrucMapper.queryByCodeOrName(entityMap);

		if (count > 0) {
			throw new SysException("数据表表名或名称已存在");
		}

		try {
			hrTableStrucMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String updateHrTableStruc(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrTableStrucMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteHrTableStruc(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// 获取对象
			HrTableStruc hrTableStruc = hrTableStrucMapper.queryByCode(entityMap);
			
			if(hrTableStruc == null){
				entityMap.put("type_tab_code", entityMap.get("tab_code"));
			}
	
			/*if (hrTableStruc != null && hrTableStruc.getIs_innr() == 1) {
				throw new SysException("内置表无法删除");
			}*/
		
			int state = hrTableStrucMapper.delete(entityMap);
			if (state > 0) {
				// 删除表
				//hrTableManagerService.dorpTableByName(entityMap.get("tab_code").toString().toUpperCase());
			}
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteBatchHrTableStruc(List<Map<String, Object>> entityList) throws DataAccessException {
		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryHrTableStruc(Map<String, Object> entityMap) throws DataAccessException {
		List<HrTableStruc> list = (List<HrTableStruc>) hrTableStrucMapper.query(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public HrTableStruc queryHrTableStrucByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrTableStrucMapper.queryByCode(entityMap);
	}

	@Override
	public String queryHrTableStrucTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> tree = hrTableStrucMapper.queryHrTableStrucTree(entityMap);
		return JSONArray.toJSONString(tree);
	}

	@Override
	public String queryHrTabCodeSeq() throws DataAccessException {
		return hrTableStrucMapper.queryHrTabCodeSeq();
	}

	@Override
	public String updateTabColByTabCode(Map<String, Object> entityMap) throws DataAccessException {
		try {

			if (entityMap.get("tab_code") == null || StringUtils.isEmpty(entityMap.get("tab_code").toString())) {
				throw new SysException("请选择数据表");
			}

			String table = entityMap.get("tab_code").toString();

			// 更新数据列
			int flag = hrTableStrucMapper.updateTabColByTabCode(entityMap);

			if (flag > 0) {
				// 生成列配置
				generateDataColumnConfig(entityMap);

				// 生成数据表SQL
				String tabSql = sqlStatement(table, entityMap.get("tab_col").toString());

				// 备份自定义SQL并与生成SQL组装一块保存
				HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(entityMap);
				List<HrTableSql> backup = new ArrayList<HrTableSql>();
				List<HrTableSql> sqlList = hrTableStruc.getTableSql();
				for (HrTableSql hrTableSql : sqlList) {
					if (hrTableSql.getIs_custom() != null && hrTableSql.getIs_custom() == 1) {
						backup.add(hrTableSql);
					}
				}
				List<HrTableSql> newTabSql = JSONArray.parseArray(tabSql, HrTableSql.class);
				if (backup.size() > 0) {
					Iterator<HrTableSql> ite = newTabSql.iterator();
					for (HrTableSql old : backup) {
						while(ite.hasNext()) {
							if(old.getSql_code().equals(ite.next().getSql_code())){
								ite.remove();
								break;
							}
						}
					}
					newTabSql.addAll(0, backup);
				}
				tabSql = JSONArray.toJSONString(newTabSql);

				entityMap.put("tab_sql", tabSql);

				// 更新数据表SQL
				hrTableStrucMapper.updateTabSqlByTabCode(entityMap);
			}

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private void generateDataColumnConfig(Map<String, Object> entityMap) {
		// 获取表构建
		HrTableStruc struc = hrTableStrucMapper.queryColSetByCode(entityMap);
		// 获取列信息
		List<HrTableColumn> columns = JSONArray.parseArray(struc.getTab_col(), HrTableColumn.class);
		// 获取列配置信息
		HrTableColumnConfig config = JSONObject.parseObject(struc.getTab_view_col(), HrTableColumnConfig.class);
		if(config == null){
			config = new HrTableColumnConfig();
		}
		List<HrTableColumnGridConfig> gridConfigList = new ArrayList<HrTableColumnGridConfig>();
		List<HrTableColumnFormConfig> searchConfigList = new ArrayList<HrTableColumnFormConfig>();
		List<HrTableColumnFormConfig> formConfigList = new ArrayList<HrTableColumnFormConfig>();
		
		Map<String, HrTableColumnGridConfig> gridConfigMap = new HashMap<>();
		if (config.getGridSetData() != null && config.getGridSetData().size() > 0) {
			for (HrTableColumnGridConfig gridConfig : config.getGridSetData()) {
				gridConfigMap.put(gridConfig.getCol_code(), gridConfig);
			}
		}

		Map<String, HrTableColumnFormConfig> searchConfigMap = new HashMap<>();
		if (config.getSearchSetData() != null && config.getSearchSetData().size() > 0) {
			for (HrTableColumnFormConfig searchConfig : config.getSearchSetData()) {
				searchConfigMap.put(searchConfig.getCol_code(), searchConfig);
			}
		}

		Map<String, HrTableColumnFormConfig> formConfigMap = new HashMap<>();
		if (config.getFormSetData() != null && config.getFormSetData().size() > 0) {
			for (HrTableColumnFormConfig formConfig : config.getFormSetData()) {
				formConfigMap.put(formConfig.getCol_code(), formConfig);
			}
		}

		for (HrTableColumn column : columns) {
			// "03"为系统项，不需要设置
			if ("03".equals(column.getValue_mode_code())){
				continue;
			}
			if (gridConfigMap.size() > 0 && gridConfigMap.containsKey(column.getCol_code())) {
				HrTableColumnGridConfig exist = gridConfigMap.get(column.getCol_code());
				exist.setCol_code(column.getCol_code());
				exist.setCol_name(column.getCol_name());
				exist.setValue_mode_code(column.getValue_mode_code());
				exist.setField_tab_code(column.getField_tab_code());
				exist.setStatus(column.getStatus() != null && ("add".equals(column.getStatus()) || "update".equals(column.getStatus())) ? 1 : 0);
				gridConfigList.add(exist);
			} else {
				HrTableColumnGridConfig.Builder builder = new HrTableColumnGridConfig.Builder(
						struc.getTab_code(), struc.getTab_name(), column.getCol_code(), column.getCol_name())
						.sort(column.getSort())
						.valueMode(column.getValue_mode_code(), column.getField_tab_code())
						.status(1);

				if ("02".equals(column.getValue_mode_code())) {
					builder.component("01", "下拉框");
				}
				gridConfigList.add(builder.build());
			}
			
			if (searchConfigMap.size() > 0 && searchConfigMap.containsKey(column.getCol_code())) {
				HrTableColumnFormConfig exist = searchConfigMap.get(column.getCol_code());
				exist.setCol_code(column.getCol_code());
				exist.setCol_name(column.getCol_name());
				exist.setValue_mode_code(column.getValue_mode_code());
				exist.setField_tab_code(column.getField_tab_code());
				exist.setStatus(column.getStatus() != null && ("add".equals(column.getStatus()) || "update".equals(column.getStatus())) ? 1 : 0);
				searchConfigList.add(exist);
			} else {
				HrTableColumnFormConfig.Builder builder = new HrTableColumnFormConfig.Builder(
						struc.getTab_code(), struc.getTab_name(), column.getCol_code(), column.getCol_name())
						.sort(column.getSort())
						.valueMode(column.getValue_mode_code(), column.getField_tab_code())
						.status(1);

				if ("02".equals(column.getValue_mode_code())) {
					builder.component("01", "下拉框");
				}
				searchConfigList.add(builder.build());
			}
			
			if (formConfigMap.size() > 0 && formConfigMap.containsKey(column.getCol_code())) {
				HrTableColumnFormConfig exist = formConfigMap.get(column.getCol_code());
				exist.setCol_code(column.getCol_code());
				exist.setCol_name(column.getCol_name());
				exist.setValue_mode_code(column.getValue_mode_code());
				exist.setField_tab_code(column.getField_tab_code());
				exist.setStatus(column.getStatus() != null && ("add".equals(column.getStatus()) || "update".equals(column.getStatus())) ? 1 : 0);
				formConfigList.add(exist);
			} else {
				HrTableColumnFormConfig.Builder builder = new HrTableColumnFormConfig.Builder(
						struc.getTab_code(), struc.getTab_name(), column.getCol_code(), column.getCol_name())
						.sort(column.getSort())
						.valueMode(column.getValue_mode_code(), column.getField_tab_code())
						.status(1);

				if ("02".equals(column.getValue_mode_code())) {
					builder.component("01", "下拉框");
				}
				formConfigList.add(builder.build());
			}

		}
		
		config.setGridSetData(gridConfigList);
		config.setSearchSetData(searchConfigList);
		config.setFormSetData(formConfigList);
		
		entityMap.put("tab_view_col", JSONObject.toJSONString(config));
		updateColSetByTabCode(entityMap);
	}

	@Override
	public String updateTabSqlByTabCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrTableStrucMapper.updateTabSqlByTabCode(entityMap);
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String updateColSetByTabCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			int i = hrTableStrucMapper.updateColSetByTabCode(entityMap);

			// 生成data文件
			if (i > 0) {
				genJsFile(entityMap);
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	private void genJsFile(Map<String, Object> entityMap) {
		if (entityMap.get("tab_view_col") != null) {
			JSONObject gridJson = new JSONObject();
			JSONObject searchJson = new JSONObject();
			JSONObject formJson = new JSONObject();
			String tab_view_col = entityMap.get("tab_view_col").toString();
			HrTableColumnConfig config = JSONObject.parseObject(tab_view_col, HrTableColumnConfig.class);
			
			UIComponentBuilder etBuilder = new UIComponentBuilder(config);
			UIComponentBuilder ligerBuilder = new UIComponentBuilder(config, true);

			gridJson.put("et", etBuilder.columns());
			gridJson.put("liger", ligerBuilder.columns());

			JSONObject etSearch = new JSONObject();
			etSearch.put("colNum",
					Integer.parseInt(MyConfig.getSysPara("06002") == null ? "2" : MyConfig.getSysPara("06002")));
			etSearch.put("fieldItems", etBuilder.searchFields());
			searchJson.put("et", etSearch);
			searchJson.put("liger", ligerBuilder.searchFields());

			JSONObject etForm = new JSONObject();
			etForm.put("colNum",
					Integer.parseInt(MyConfig.getSysPara("06001") == null ? "3" : MyConfig.getSysPara("06001")));
			etForm.put("fieldItems", etBuilder.formFields());
			formJson.put("et", etForm);
			formJson.put("liger", ligerBuilder.formFields());

			// lib\hrp\hr\data\集团ID\医院ID\form|search|grid|query
			String webRoot = System.getProperty("hrp.root");
			String path = webRoot + "\\lib\\hrp\\hr\\data\\" + entityMap.get("group_id") + "\\"
					+ entityMap.get("hos_id");
			String fileName = entityMap.get("tab_code").toString() + ".data";
			String gridpath = path + "\\grid\\" + fileName;
			String searchpath = path + "\\search\\" + fileName;
			String formpath = path + "\\form\\" + fileName;
			try {
				FileUtil.writeString(gridJson.toJSONString(), gridpath, StandardCharsets.UTF_8);
				FileUtil.writeString(searchJson.toJSONString(), searchpath, StandardCharsets.UTF_8);
				FileUtil.writeString(formJson.toJSONString(), formpath, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new SysException("文件写入失败");
			}
		}

	}

	@Override
	public String queryTabColsByCode(Map<String, Object> entityMap) throws DataAccessException {
		HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabColsByCode(entityMap);
		if (hrTableStruc != null) {
			List<HrTableColumn> data = hrTableStruc.getTableColumn();
			Collections.sort(data);
			return ChdJson.toJson(data);
		}
		return "{\"Total\":0,\"Rows\":[]}";
	}

	@Override
	public String queryTabSqlsByCode(Map<String, Object> entityMap) throws DataAccessException {
		HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(entityMap);
		/*
		 * List<HrTableSql> tableSqlList = new ArrayList<HrTableSql>();
		 * if(hrTableStruc != null){ tableSqlList = hrTableStruc.getTableSql();
		 * 
		 * //增加父类 HrTableSql parent = new HrTableSql();
		 * parent.setSql_code(hrTableStruc.getTab_code());
		 * parent.setSql_name(hrTableStruc.getTab_name());
		 * 
		 * for (HrTableSql hrTableSql : tableSqlList) {
		 * hrTableSql.setTab_code(hrTableStruc.getTab_code()); }
		 * tableSqlList.add(0, parent); }
		 */
		return hrTableStruc.getTab_sql();
	}

	@Override
	public String queryColSetByCode(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> re = new HashMap<String, Object>();
		HrTableStruc hrTableStruc = hrTableStrucMapper.queryColSetByCode(entityMap);
		if (hrTableStruc != null && StringUtil.isNotEmpty(hrTableStruc.getTab_view_col())) {
			HrTableColumnConfig data = JSONObject.parseObject(hrTableStruc.getTab_view_col(),
					HrTableColumnConfig.class);
			Collections.sort(data.getGridSetData());
			Collections.sort(data.getSearchSetData());
			Collections.sort(data.getFormSetData());
			re.put("gridSetData", ChdJson.toJson(data.getGridSetData()));
			re.put("searchSetData", ChdJson.toJson(data.getSearchSetData()));
			re.put("formSetData", ChdJson.toJson(data.getFormSetData()));
			return JSONObject.toJSONString(re);
		} else {
			return "{\"gridSetData\":{\"Total\":0,\"Rows\":[]},\"searchSetData\":{\"Total\":0,\"Rows\":[]},\"formSetData\":{\"Total\":0,\"Rows\":[]}}";
		}
	}

	@Override
	public String updateHrTableStrucIsStop(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrTableStrucMapper.updateHrTableStrucIsStop(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	@Override
	public String generateSqlStatement(Map<String, Object> entityMap) throws DataAccessException {

		String reJson;

		String table = entityMap.get("tab_code").toString();

		HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabColsByCode(entityMap);

		if (hrTableStruc != null && StringUtil.isNotEmpty(hrTableStruc.getTab_col())) {
			reJson = sqlStatement(table, hrTableStruc.getTab_col());
		} else {
			return "{\"error\":\"数据表字段未维护\"}";
		}

		return reJson;
	}

	@Override
	public String queryHrTableStrucExport(Map<String, Object> entityMap) throws DataAccessException {
		List<HrTableStruc> list = hrTableStrucMapper.queryHrTableStrucExport(entityMap);
		if(list == null || list.size() == 0){
			entityMap.put("type_tab_code", entityMap.get("tab_code"));
			entityMap.put("tab_code", null);
			list = hrTableStrucMapper.queryHrTableStrucExport(entityMap);
		}
		
		return JSONArray.toJSONString(list, true);
	}

	@Override
	public String addBatchTableStruc(List<HrTableStruc> list) throws DataAccessException {
		try {
			hrTableStrucMapper.addBatch(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String copyHrTableStrucByOld(Map<String, Object> entityMap) throws DataAccessException {
		try {
			// hrTableStrucMapper.copyTableTypeByOld(entityMap);
			entityMap.put("type_tab_code", "pf");
			HrTableType tableType = hrTableTypeMapper.queryByCode(entityMap);
			if (tableType == null) {
				throw new SysException("内置数据表分类未维护");
			}
			List<Map<String, Object>> oldTableList = hrTableStrucMapper.queryOldTableStruc(entityMap);
			if (oldTableList.size() > 0) {
				for (Map<String, Object> tab : oldTableList) {
					String tabCode = tab.get("tab_code").toString();
					entityMap.put("tab_code", tabCode);
					List<Map<String, Object>> oldColumnList = hrTableStrucMapper.queryOldColumnByTabCode(entityMap);

					String tab_col = JSONArray.toJSONString(oldColumnList);
					tab.put("tab_col", tab_col);

					String tab_sql = sqlStatement(tabCode, tab_col);
					tab.put("tab_sql", tab_sql);
					
				}

				hrTableStrucMapper.addBatch(oldTableList);
			}
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

	public String sqlStatement(String table, String column) {

		JSONArray reJson = new JSONArray();

		SQL insertSql = new SQL().INSERT_INTO(table);
		SQL updateSql = new SQL().UPDATE(table);
		SQL deleteSql = new SQL().DELETE_FROM(table);
		SQL queryByCodeSql = new SQL().FROM(table);
		

		List<HrTableColumn> list = JSONArray.parseArray(column, HrTableColumn.class);
		for (HrTableColumn col : list) {
			String colCode = col.getCol_code();
			String colName = "[" + col.getCol_name() + "]";
			insertSql.VALUES(colCode, colName);
			queryByCodeSql.SELECT(colCode);
			if (col.getIs_pk() != null && col.getIs_pk() == 1) {
				updateSql.WHERE(colCode + " = " + colName);
				deleteSql.WHERE(colCode + " = " + colName);
				queryByCodeSql.WHERE(colCode + " = " + colName);
			} else {
				updateSql.SET(colCode + " = " + colName);
			}
		}

		Map<String, String> insertObj = new HashMap<String, String>();
		Map<String, String> updateObj = new HashMap<String, String>();
		Map<String, String> deleteObj = new HashMap<String, String>();
		Map<String, String> queryByCodeObj = new HashMap<String, String>();
		
		Map<String, String> importObj = new HashMap<String, String>();

		table = StringUtils.removePrefixAndUnderlineToCamel(table, "hr");

		insertObj.put("sql_code", "add" + table);
		insertObj.put("sql_name", "新增");
		insertObj.put("sql_statement", insertSql.toString());
		
		importObj.put("sql_code", "import" + table);
		importObj.put("sql_name", "导入");
		importObj.put("sql_statement", insertSql.toString());

		updateObj.put("sql_code", "update" + table);
		updateObj.put("sql_name", "修改");
		updateObj.put("sql_statement", updateSql.toString());

		deleteObj.put("sql_code", "delete" + table);
		deleteObj.put("sql_name", "删除");
		deleteObj.put("sql_statement", deleteSql.toString());

		queryByCodeObj.put("sql_code", "queryByCode" + table);
		queryByCodeObj.put("sql_name", "查询");
		queryByCodeObj.put("sql_statement", queryByCodeSql.toString());

		reJson.add(insertObj);
		reJson.add(updateObj);
		reJson.add(deleteObj);
		reJson.add(queryByCodeObj);
		reJson.add(importObj);

		return reJson.toJSONString();
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String queryHrTableColByCodes(Map<String, Object> entityMap) throws DataAccessException {

		// 查询条件
		if (entityMap.get("condition") != null) {
			List<Map> condition = JSONArray.parseArray(entityMap.get("condition").toString(), Map.class);
			if (condition.size() > 0) {
				entityMap.put("tab_codes", condition.get(0).get("value"));
			}
		}

		List<HrTableStruc> list = hrTableStrucMapper.queryTabStrucByCodes(entityMap);
		List<HrTableColumn> reData = new ArrayList<HrTableColumn>();
		for (HrTableStruc hrTableStruc : list) {
			if (hrTableStruc != null && StringUtil.isNotEmpty(hrTableStruc.getTab_col())) {
				List<HrTableColumn> data = hrTableStruc.getTableColumn();
				for (HrTableColumn col : data) {
					col.setTab_code(hrTableStruc.getTab_code());
					col.setTab_name(hrTableStruc.getTab_name());
				}
				Collections.sort(data);
				reData.addAll(data);
			}
		}

		List<HrTableColumn> dbCols = hrTableStrucMapper.queryDBTableColByCodes(entityMap);
		reData.addAll(dbCols);

		if (entityMap.get("func") != null && StringUtils.isNotEmpty(entityMap.get("func").toString())) {
			List<Map> funcList = JSONArray.parseArray(entityMap.get("func").toString(), Map.class);
			for (Map func : funcList) {
				for (HrTableColumn col : reData) {
					if (func.get("tab_code").toString().equals(col.getTab_code())
							&& func.get("col_code").toString().equals(col.getCol_code())) {
						col.setFunc(func.get("func").toString());
						col.setFunc_text(func.get("func_text").toString());
						col.setParam(func.get("param") != null ? func.get("param").toString() : "");
					}
				}
			}
		}

		if (reData.size() > 0) {
			return ChdJson.toJson(reData);
		} else {
			return "{\"Total\":0,\"Rows\":[]}";
		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public String queryHrTableColByCodes2(Map<String, Object> entityMap) throws DataAccessException {

		// 查询条件
		if (entityMap.get("condition") != null) {
			List<Map> condition = JSONArray.parseArray(entityMap.get("condition").toString(), Map.class);
			if (condition.size() > 0) {
				entityMap.put("tab_codes", condition.get(0).get("value"));
			}
		}

		List<HrTableStruc> list = hrTableStrucMapper.queryTabStrucByCodes(entityMap);
		List<HrTableColumn> reData = new ArrayList<HrTableColumn>();
		for (HrTableStruc hrTableStruc : list) {
			if (hrTableStruc != null && StringUtil.isNotEmpty(hrTableStruc.getTab_col())) {
				List<HrTableColumn> data = hrTableStruc.getTableColumn();
				for (HrTableColumn col : data) {
					col.setTab_code(hrTableStruc.getTab_code());
					col.setTab_name(hrTableStruc.getTab_name());
					col.setCol_code(hrTableStruc.getTab_code() + "." + col.getCol_code());
					col.setCol_name(hrTableStruc.getTab_name() + "." + col.getCol_name());
				}
				Collections.sort(data);
				reData.addAll(data);
			}
		}

		List<HrTableColumn> dbCols = hrTableStrucMapper.queryDBTableColByCodes(entityMap);
		reData.addAll(dbCols);

		if (reData.size() > 0) {
			return JSONArray.toJSONString(reData);
		} else {
			return "[]";
		}

	}

	@Override
	public String saveSqlStatement(Map<String, Object> entityMap) throws DataAccessException {
		String sqlCode = entityMap.get("sql_code") == null || StringUtils.isEmpty(entityMap.get("sql_code").toString())
				? null : entityMap.get("sql_code").toString();

		try {
			HrTableSql newSql = JSONObject.parseObject(entityMap.get("tab_sql").toString(), HrTableSql.class);

			HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(entityMap);
			if (hrTableStruc.getTableSql().size() > 0) {
				List<HrTableSql> sqlList = hrTableStruc.getTableSql();
				if (sqlCode == null) {
					sqlList.add(newSql);
				} else {
					for (HrTableSql hrTableSql : sqlList) {
						if (sqlCode.equals(hrTableSql.getSql_code())) {
							hrTableSql.setSql_code(newSql.getSql_code());
							hrTableSql.setSql_name(newSql.getSql_name());
							hrTableSql.setSql_statement(newSql.getSql_statement());
							hrTableSql.setIs_custom(newSql.getIs_custom());
							hrTableSql.setIs_proc_jfunc(newSql.getIs_proc_jfunc());
						}
					}
				}

				entityMap.put("tab_sql", JSONArray.toJSONString(sqlList));
			} else {
				JSONArray jsonArr = new JSONArray();
				jsonArr.add(newSql);
				entityMap.put("tab_sql", jsonArr.toJSONString());
			}

			hrTableStrucMapper.updateTabSqlByTabCode(entityMap);

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteSqlStatement(Map<String, Object> entityMap) throws DataAccessException {
		String sqlCode = entityMap.get("sql_code") == null || StringUtils.isEmpty(entityMap.get("sql_code").toString())
				? null : entityMap.get("sql_code").toString();

		if (sqlCode == null) {
			throw new SysException("未选择操作节点");
		}

		try {

			HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(entityMap);
			if (hrTableStruc.getTableSql().size() > 0) {
				List<HrTableSql> sqlList = hrTableStruc.getTableSql();
				Iterator<HrTableSql> itr = sqlList.iterator();
				while(itr.hasNext()){
					if(sqlCode.equals(itr.next().getSql_code())){
						itr.remove();
					}
				}
				entityMap.put("tab_sql", JSONArray.toJSONString(sqlList));
				hrTableStrucMapper.updateTabSqlByTabCode(entityMap);
			}

			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@SuppressWarnings("unchecked")
	@Override
	public String queryHrTableWords(Map<String, Object> entityMap) throws DataAccessException {
		Map<String, Object> re = new HashMap<String, Object>();
		List<HrTableStruc> list = (List<HrTableStruc>) hrTableStrucMapper.query(entityMap);
		for (HrTableStruc hrTableStruc : list) {
			List<HrTableColumn> cols = hrTableStruc.getTableColumn();
			List<String> colCodes = new ArrayList<String>();
			for (HrTableColumn hrTableColumn : cols) {
				colCodes.add(hrTableColumn.getCol_code());
			}
			re.put(hrTableStruc.getTab_code(), colCodes);
		}
		return JSONObject.toJSONString(re);
	}

	@Override
	public String queryHrFiledTableStruc(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String, Object>> list = hrTableStrucMapper.queryHrFiledTableStruc(entityMap);
		return ChdJson.toJson(list);
	}

	@Override
	public String createTableStruc(Map<String, Object> entityMap) throws DataAccessException {
		HrTableStruc struc = hrTableStrucMapper.queryByCode(entityMap);
		String table = struc.getTab_code();
		// 建表
		Map<String, List<HrTableColumn>> tableMap = new HashMap<String, List<HrTableColumn>>();
		List<HrTableColumn> columns = struc.getTableColumn();
		if (columns.size() > 0) {
			Collections.sort(columns);
			tableMap.put(table.toUpperCase(), columns);
			hrTableManagerService.createTableStruc(tableMap);
		} else {
			throw new SysException("未维护表结构字段，表结构创建失败。");
		}
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}
	
	@Override
	public String alterTableStruc(Map<String, Object> entityMap) throws DataAccessException {
		hrTableManagerService.alterTableStruc(entityMap);
		return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
	}

}
