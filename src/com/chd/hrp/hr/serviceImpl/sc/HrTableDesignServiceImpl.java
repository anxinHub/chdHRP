/** 
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @网站：www.s-chd.com
 */

package com.chd.hrp.hr.serviceImpl.sc;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.base.util.IdGen;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.dao.sc.HrTableDesignMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumn;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnSort;
import com.chd.hrp.hr.entity.sc.HrTableDesignQueryColumnTable;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.sc.HrTableDesignService;
import com.chd.hrp.hr.util.FileUtil;
import com.chd.hrp.hr.util.SelectBuilder;
import com.chd.hrp.hr.util.StringUtils;

/**
 * 
 * @Description: 查询设计器
 * @Table: HR_TABLE_DESIGN
 * @Author: zn
 * @Version: 1.0
 */

@Service("hrTableDesignService")
public class HrTableDesignServiceImpl implements HrTableDesignService {

	private static Logger logger = Logger.getLogger(HrTableDesignServiceImpl.class);
	
	// 引入DAO操作
	@Resource(name = "hrTableDesignMapper")
	private final HrTableDesignMapper hrTableDesignMapper = null;
	
	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;
	
	@Resource(name = "hrFiledTabStrucMapper")
	private final HrFiledTabStrucMapper hrFiledTabStrucMapper = null;

	@Override
	public String addHrTableDesign(Map<String, Object> entityMap) throws DataAccessException {
		
		// 查询表名是否存在
		int count = hrTableDesignMapper.queryByCodeOrName(entityMap);

		if (count > 0 ) {
			throw new SysException("设计器编码或名称已存在");
		}
		
		//更新非末级
		/*if(entityMap.get("super_code") != null && StringUtil.isNotEmpty(entityMap.get("super_code").toString())){
			entityMap.put("is_last", "0");
			hrTableDesignMapper.updateLastStateByCode(entityMap);
		}*/
		
		entityMap.put("is_last", 1);
		
		//查询最大排序号
		int maxNo = hrTableDesignMapper.queryMaxSrotNO(entityMap);
		
		entityMap.put("design_sort", maxNo);
		
		entityMap.put("design_level", 0);
		
		try {
			hrTableDesignMapper.add(entityMap);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String updateHrTableDesign(Map<String, Object> entityMap) throws DataAccessException {

		try {
			hrTableDesignMapper.update(entityMap);
			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public String deleteHrTableDesign(Map<String, Object> entityMap) throws DataAccessException {
		
		// 获取对象
		HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);
		
		if(hrTableDesign == null){
			entityMap.put("super_code", entityMap.get("design_code"));
		}

		/*if (hrTableDesign != null && hrTableDesign.getIs_last() != 1) {
			throw new SysException("非末级无法删除");
		}*/

		try {
			//HrTableDesign design = hrTableDesignMapper.queryByCode(entityMap);
			hrTableDesignMapper.delete(entityMap);
			/*if(state > 0){
				int isExsitLast = hrTableDesignMapper.queryChildNode(entityMap);
				if(isExsitLast == 0){
					entityMap.put("super_code", design.getSuper_code());
					entityMap.put("is_last", "1");
					hrTableDesignMapper.updateLastStateByCode(entityMap);
				}
			}*/
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}

	}

	@Override
	public HrTableDesign queryHrTableDesignByCode(Map<String, Object> entityMap) throws DataAccessException {
		return hrTableDesignMapper.queryByCode(entityMap);
	}
	
	@Override
	public String queryHrTableDesignTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> tree = hrTableDesignMapper.queryHrTableDesignTree(entityMap);
		return JSONArray.toJSONString(tree);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> generateSqlStatement(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String, String> reJson = new HashMap<String, String>();
		
		boolean autoGenOnCond = entityMap.containsKey("autoGenOnCond") && Boolean.parseBoolean(entityMap.get("autoGenOnCond").toString());
		
		//先更新再设置
		if(entityMap.containsKey("design_query_col") && StringUtils.isNotEmpty(entityMap.get("design_query_col").toString())){
			updateDesignQueryColByCode(entityMap);
		}
		
		//获取查询设计器
		HrTableDesign design = hrTableDesignMapper.queryByCode(entityMap);
		List<HrTableStruc> tableStrucList = (List<HrTableStruc>) hrTableStrucMapper.query(entityMap);
		List<HrFiledTabStruc> filedTabStrucList = (List<HrFiledTabStruc>) hrFiledTabStrucMapper.query(entityMap);
		
		SelectBuilder builder = new SelectBuilder(design, tableStrucList, filedTabStrucList, autoGenOnCond);
		String sql = builder.select().where().groupBy().orderBy().getSql();
		
		entityMap.put("design_query_sql", sql);
		updateDesignQuerySqlByCode(entityMap);
		
		reJson.put("sql_statement", sql);
		
		return reJson;
		
	}
	
	@Override
	public String updateDesignQueryColByCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrTableDesignMapper.updateDesignQueryColByCode(entityMap);
			
			genJsFile(entityMap);
			
			//列显示设置
			/*if((!entityMap.containsKey("design_query_page") 
					|| StringUtils.isEmpty(entityMap.get("design_query_page").toString())
					|| "[]".equals(entityMap.get("design_query_page").toString()))
					&& count > 0){
				genDesignQueryPage(entityMap);
			}*/
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> genDesignQueryPage(Map<String, Object> entityMap){
		//先更新更设置
		if(entityMap.containsKey("design_query_col") && StringUtils.isNotEmpty(entityMap.get("design_query_col").toString())){
			updateDesignQueryColByCode(entityMap);
		}
		
		HrTableDesign design = hrTableDesignMapper.queryByCode(entityMap);
		List<HrTableStruc> tableStrucList = (List<HrTableStruc>) hrTableStrucMapper.query(entityMap);
		List<HrFiledTabStruc> filedTabStrucList = (List<HrFiledTabStruc>) hrFiledTabStrucMapper.query(entityMap);
		
		SelectBuilder builder = new SelectBuilder(design, tableStrucList, filedTabStrucList);
		
		//代码表，如果是变更ID，默认不显示
		Map<String, Map<String, String>> filedTable = builder.filedTableHandle();
		
		String query_col = design.getDesign_query_col();
		HrTableDesignQueryColumn obj = JSONObject.parseObject(query_col, HrTableDesignQueryColumn.class);
		List<HrTableDesignQueryColumnTable> list = obj.getTableData();
		
		JSONArray colSetArr = new JSONArray();
		Set<String> noCol = new HashSet<String>();
		int sort = 0;
		for (HrTableDesignQueryColumnTable tab : list) {
			if(tab.getCol_code() != null && StringUtils.isNotEmpty(tab.getCol_code())){
				String[] cols = tab.getCol_code().split(";");
				String[] names = tab.getCol_name().split(";");
				for(int i=0; i<cols.length; i++ ){
					JSONObject colSetObj = new JSONObject();
					colSetObj.put("tab_code", tab.getTab_code());
					colSetObj.put("tab_name", tab.getTab_name());
					colSetObj.put("col_code", cols[i]);
					colSetObj.put("col_name", names[i]);
					colSetObj.put("is_view", "1");
					//变更ID默认不显示
					if(noCol.contains(cols[i])){
						colSetObj.put("is_view", "0");
					}
					colSetObj.put("sort", ++sort);
					
					//如果是代码表字段需加上col_code+_name;
					if(filedTable.containsKey(tab.getTab_code() + cols[i])){
						Map<String, String> filedTab = filedTable.get(tab.getTab_code() + cols[i]);
						if(filedTab.containsKey("noCol")){
							noCol.add(filedTab.get("noCol"));
						}
						colSetObj.put("is_view", "0");
						JSONObject colSetObj2 = new JSONObject();
						colSetObj2.put("tab_code", tab.getTab_code());
						colSetObj2.put("tab_name", tab.getTab_name());
						colSetObj2.put("col_code", cols[i] + "_NAME");
						colSetObj2.put("col_name", names[i]);
						colSetObj2.put("is_view", "1");
						colSetObj2.put("sort", ++sort);
						colSetArr.add(colSetObj2);
					}
					colSetArr.add(colSetObj);
				}
			}
		}
		entityMap.put("design_query_page", colSetArr.toJSONString());
		//updateDesignQueryPageByCode(entityMap);
		return entityMap;
	}
	
	@Override
	public String queryDesignQueryColByCode(Map<String, Object> entityMap) throws DataAccessException {
		HrTableDesign obj = hrTableDesignMapper.queryByCode(entityMap);
		if(obj != null && obj.getDesign_query_col() != null && StringUtils.isNotEmpty(obj.getDesign_query_col())){
			HrTableDesignQueryColumn designQueryColumn = JSONObject.parseObject(obj.getDesign_query_col(), HrTableDesignQueryColumn.class);
			List<HrTableDesignQueryColumnSort> sortData = designQueryColumn.getSortData();
			Collections.sort(sortData);
			Map<String, Object> re = new HashMap<String, Object>();
			//re.put("json", obj.getDesign_query_col());
			re.put("tableData", designQueryColumn.getTableData());
			re.put("conditionData", designQueryColumn.getConditionData());
			re.put("groupData", designQueryColumn.getGroupData());
			re.put("sortData", sortData);
			re.put("sql", obj.getDesign_query_sql());
			re.put("page", obj.getDesign_query_page());
			return JSONObject.toJSONString(re);
		}else{
			return "{\"sql\":\"\",\"page\":null,\"tableData\":null,\"conditionData\":null,\"groupData\":null,\"sortData\":null}";
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	@Override
	public String queryDesignQueryPageByCode(Map<String, Object> entityMap) throws DataAccessException {
		HrTableDesign obj = hrTableDesignMapper.queryByCode(entityMap);
		if(obj != null){
			List<Map> list = JSONArray.parseArray(obj.getDesign_query_page(), Map.class);
			if(list != null && list.size() > 0){
				Collections.sort(list, new Comparator<Map>(){

					@Override
					public int compare(Map o1, Map o2) {
						return Integer.valueOf(o1.get("sort").toString()).compareTo(Integer.valueOf(o2.get("sort").toString()));
					}
					
				});
			}
			
			return JSONArray.toJSONString(list);
		}else{
			return "[]";
		}
		
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public String queryHrTableDesign(Map<String, Object> entityMap) throws DataAccessException {
		List<HrTableDesign> list = (List<HrTableDesign>) hrTableDesignMapper.query(entityMap);
		return ChdJson.toJson(list);
	}
	
	@Override
	public String queryHrTableDesignExport(Map<String, Object> entityMap) throws DataAccessException {
		List<HrTableDesign> list = hrTableDesignMapper.queryHrTableDesignExport(entityMap);
		if(list == null || list.size() == 0){
			entityMap.put("design_code", entityMap.get("super_code"));
			entityMap.put("super_code", null);
			list = hrTableDesignMapper.queryHrTableDesignExport(entityMap);
		}
		return JSONArray.toJSONString(list, true);
	}

	@Override
	public String addBatchTableDesign(List<HrTableDesign> list) throws DataAccessException {
		try {
			hrTableDesignMapper.addBatch(list);
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
		
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String queryDBTableTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> tree = hrTableDesignMapper.queryDBTableTree(entityMap);
		HrTableDesign design = hrTableDesignMapper.queryByCode(entityMap);
		if(design != null && StringUtils.isNotEmpty(design.getDesign_query_col())){
			String col = design.getDesign_query_col();
			JSONObject jsonObj = JSONObject.parseObject(col);
			List<Map> list = JSONArray.parseArray(jsonObj.getString("tableData"), Map.class);
			for (Map<String, Object> t : tree) {
				for (Map m : list) {
					if(m.get("tab_code").equals(t.get("tab_code"))){
						t.put("ischecked", true);
					}
				}
			}
		}
		
		return JSONArray.toJSONString(tree);
	}

	@Override
	public String updateDesignQuerySqlByCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrTableDesignMapper.updateDesignQuerySqlByCode(entityMap);
			
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@Override
	public String updateDesignQueryPageByCode(Map<String, Object> entityMap) throws DataAccessException {
		try {
			hrTableDesignMapper.updateDesignQueryPageByCode(entityMap);
			
			genJsFile(entityMap);
			return "{\"msg\":\"保存成功.\",\"state\":\"true\"}";
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}
	
	@SuppressWarnings("rawtypes")
	private void genJsFile(Map<String, Object> entityMap){
		if (entityMap.get("design_query_page") != null) {
			JSONArray jsonArr = new JSONArray();
			List<Map> list = JSONArray.parseArray(entityMap.get("design_query_page").toString(), Map.class);
			Collections.sort(list, new Comparator<Map>(){

				@Override
				public int compare(Map o1, Map o2) {
					return Integer.valueOf(o1.get("sort").toString()).compareTo(Integer.valueOf(o2.get("sort").toString()));
				}
				
			});
			for (Map map : list) {
				JSONObject json = new JSONObject();
				json.put("col_code", map.get("col_code").toString().toLowerCase());
				json.put("is_display", map.get("is_view") != null && "1".equals(map.get("is_view")) ? true : false );
				jsonArr.add(json);
			}
			//lib\hrp\hr\data\集团ID\医院ID\form|search|grid|query
			String webRoot = System.getProperty("hrp.root");
			String path = webRoot + "\\lib\\hrp\\hr\\data\\" + entityMap.get("group_id") + "\\" + entityMap.get("hos_id");
			String fileName = entityMap.get("design_code").toString() + ".data";
			String querypath = path + "\\query\\" + fileName;
			try {
				FileUtil.writeString(jsonArr.toJSONString(), querypath, StandardCharsets.UTF_8);
			} catch (IOException e) {
				throw new SysException("文件写入失败");
			}
		}
	}

	@SuppressWarnings("rawtypes")
	@Override
	public String queryHrStatisticTableTree(Map<String, Object> entityMap) throws DataAccessException {
		List<Map<String,Object>> tree = hrTableDesignMapper.queryHrStatisticTableTree(entityMap);
		HrTableDesign design = hrTableDesignMapper.queryByCode(entityMap);
		if(design != null && StringUtils.isNotEmpty(design.getDesign_query_col())){
			String col = design.getDesign_query_col();
			JSONObject jsonObj = JSONObject.parseObject(col);
			List<Map> list = JSONArray.parseArray(jsonObj.getString("tableData"), Map.class);
			for (Map<String, Object> t : tree) {
				for (Map m : list) {
					if(m.get("tab_code").equals(t.get("tab_code"))){
						t.put("ischecked", true);
					}
				}
			}
		}
		
		return JSONArray.toJSONString(tree);
	}

	@Override
	public String saveHrStatisticDesign(Map<String, Object> entityMap) throws DataAccessException {
		String design_code = entityMap.get("design_code").toString();
		if(StringUtils.isNotEmpty(design_code)){
			updateHrTableDesign(entityMap);
		}else{
			design_code = "query" + IdGen.randomBase62(6) + ".do";
			entityMap.put("design_code", design_code);
			addHrTableDesign(entityMap);
		}
		
		entityMap.put("autoGenOnCond", true);
		Map<String, String> sql = generateSqlStatement(entityMap);
		if(StringUtils.isEmpty(sql.get("sql_statement"))){
			throw new SysException("SQL语句生成失败，请检查设置。");
		}
		entityMap.put("design_query_sql", sql.get("sql_statement"));
		
		updateDesignQuerySqlByCode(entityMap);
		
		genDesignQueryPage(entityMap);
		
		return "{\"msg\":\"操作成功.\",\"state\":\"true\",\"design_code\":\"" + design_code + "\"}";
	}
	
}
