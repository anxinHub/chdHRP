package com.chd.hrp.ass.serviceImpl.assquery;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.ChdJson;
import com.chd.hrp.ass.dao.assquery.AssDefinedQueryMapper;
import com.chd.hrp.ass.service.assquery.AssDefinedQueryService;
import com.github.pagehelper.PageInfo;

@Service("assDefinedQueryService")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class AssDefinedQueryServiceImpl implements AssDefinedQueryService {

	private static Logger logger = Logger.getLogger(AssDefinedQueryServiceImpl.class);
	                  
	@Resource(name = "assDefinedQueryMapper")
	private final AssDefinedQueryMapper assDefinedQueryMapper = null;

	@Override
	public String queryAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException {
		logger.debug("AssDefinedQueryServiceImpl.queryAssDefinedQuery");
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assDefinedQueryMapper.queryAssDefinedQuery(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = assDefinedQueryMapper.queryAssDefinedQuery(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public List<String> queryFields(Map<String, Object> entityMap) throws DataAccessException {
		String sql = getSql(String.valueOf(entityMap.get("sqlStr")));
		if(sql == null || "".equals(sql) ){
			throw new SysException("请输入SQL语句");
		}
		
		if(sql_inj(sql)){
			throw new SysException("请输入SQL查询语句!");
		}
		
		entityMap.put("sqlStr", sql);
		try {
			List<Map<String, Object>> result = assDefinedQueryMapper.queryFields(entityMap);
			Set fieldsSet= result.get(0).keySet();
			List<String> fileds = new ArrayList<String>(fieldsSet);
			return fileds;
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new SysException("SQL语法错误");
		}
	}
	
	private String getSql(String sql){
		String regex = "\\$\\{\\w+\\}";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(sql);
		while(m.find()){
			String whereParam = m.group();
			sql = sql.replace(whereParam, "'' or 1=1 or 1=''");
			sql = sql.replace("'''", "''");
		}
		//兼容图表
		regex = "\\{\\w+\\}";
		p = Pattern.compile(regex);
		m = p.matcher(sql);
		while(m.find()){
			String whereParam = m.group();
			sql = sql.replace(whereParam, " 1=1 ");
		}
		return sql;
	}
	
	/**
	 * 防止sql注入
	 * @param str 输入sql
	 * @return 是否存在注入关键字
	 */
	private static boolean sql_inj(String str) {
		if(str == null || str.equals("")){
			return false;
		}
		String inj_str = "exec|insert|delete|update|chr|mid|master|truncate|char|declare";
		String inj_stra[] = inj_str.split("\\|");
		for (int i = 0; i < inj_stra.length; i++) {
			if (str.indexOf(inj_stra[i]) >= 0) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 根据字段类型 进行处理
	 * @param fieldType 字段类型
	 * @param value 值
	 * @return
	 */
	public static String applyType(String fieldType, String value) {
		if(value != null && !"".equals(value)){
			String result = "";
			if("text".equalsIgnoreCase(fieldType)){
				result = "'" +value+ "'";
			}else if("date".equalsIgnoreCase(fieldType)){
				result = "TO_DATE('"+value+"','yyyy-MM-dd')";
			}else if("number".equalsIgnoreCase(fieldType)){
				result = value;
			}else{
				result = value;
			}
			return result;
		}else{
			return "";
		}
	}

	@Override
	public Map<String, Object> queryByCode(Map<String, Object> entityMap) throws DataAccessException {
		return assDefinedQueryMapper.queryByCode(entityMap);
	}

	@Override
	public String addAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException {
		
		if(sql_inj(entityMap.get("r_sql").toString())){
			throw new SysException("请注意,请输入SQL查询语句!");
		}
		
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> assDefinedQuery = assDefinedQueryMapper.queryByCode(entityMap);

		if (assDefinedQuery != null) {

			return "{\"error\":\"数据重复,请重新添加.\"}";

		}

		try {
			int state = assDefinedQueryMapper.addAssDefinedQuery(entityMap);
			
			if(state > 0){
				String rhead_id = String.valueOf(entityMap.get("rhead_id"));
				List<Map> itemList = JSON.parseArray(entityMap.get("item").toString(), Map.class);
				
				for (Map map : itemList) {
					map.put("rhead_id", rhead_id);
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("field_name", map.get("field_name"));
					map.put("field_text", map.get("field_text"));
					map.put("s_flag", map.get("s_flag") == null ? 1 : Integer.parseInt(map.get("s_flag").toString()));
					map.put("s_mode", map.get("s_mode") == null ? 1 : Integer.parseInt(map.get("s_mode").toString()));
					map.put("field_type", map.get("field_type") == null ? "text" : map.get("field_type"));
					map.put("is_show", map.get("is_show") == null ? 1 : Integer.parseInt(map.get("is_show").toString()));
					map.put("order_num", map.get("order_num") == null ? null : map.get("order_num"));
					map.put("replace_va", map.get("replace_va") == null ? null : map.get("replace_va"));
					map.put("operator", map.get("where_operator") == null ? null :map.get("where_operator"));
					if(map.get("field_name") != null && !"".equals(map.get("field_name").toString().trim())){
						items.add(map);
					}
				}
				
				if(items.size() > 0 ){
					assDefinedQueryMapper.addBatchAssReportItem(items);
				}
			}
			

			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}
	
	@Override
	public String updateAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException {
		
		if(sql_inj(entityMap.get("r_sql").toString())){
			throw new SysException("请注意,请输入SQL查询语句!");
		}
		
		List<Map<String,Object>> items = new ArrayList<Map<String,Object>>();
		
		Map<String,Object> assDefinedQuery = assDefinedQueryMapper.queryByCode(entityMap);

		try {
			
			assDefinedQuery.put("rhead_name", entityMap.get("rhead_name"));
			assDefinedQuery.put("rhead_code", entityMap.get("rhead_code"));
			assDefinedQuery.put("r_sql", entityMap.get("r_sql"));
			assDefinedQuery.put("note", entityMap.get("note") == null ? "" :entityMap.get("note"));
			int state = assDefinedQueryMapper.updateAssDefinedQuery(assDefinedQuery);
			
			if(state > 0){
				assDefinedQueryMapper.deleteAssReportItemByRheadId(entityMap);
				String rhead_id = String.valueOf(entityMap.get("rhead_id"));
				List<Map> itemList = JSON.parseArray(entityMap.get("item").toString(), Map.class);
				
				for (Map map : itemList) {
					map.put("rhead_id", rhead_id);
					map.put("group_id", entityMap.get("group_id"));
					map.put("hos_id", entityMap.get("hos_id"));
					map.put("copy_code", entityMap.get("copy_code"));
					map.put("field_name", map.get("field_name"));
					map.put("field_text", map.get("field_text"));
					map.put("s_flag", map.get("s_flag") == null ? 1 : Integer.parseInt(map.get("s_flag").toString()));
					map.put("s_mode", map.get("s_mode") == null ? 1 : Integer.parseInt(map.get("s_mode").toString()));
					map.put("field_type", map.get("field_type") == null ? "text" : map.get("field_type"));
					map.put("is_show", map.get("is_show") == null ? 1 : Integer.parseInt(map.get("is_show").toString()));
					map.put("order_num", map.get("order_num") == null ? null : map.get("order_num"));
					map.put("replace_va", map.get("replace_va") == null ? null : map.get("replace_va"));
					map.put("operator", map.get("where_operator") == null ? null :map.get("where_operator"));
					if(map.get("field_name") != null && !"".equals(map.get("field_name").toString().trim())){
						items.add(map);
					}
				}
				
				if(items.size() > 0 ){
					assDefinedQueryMapper.addBatchAssReportItem(items);
				}
			}
			

			return "{\"msg\":\"更新成功.\",\"state\":\"true\"}";

		} catch (Exception e) {

			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());

		}
	}

	@Override
	public List<Map<String, Object>> queryAssReportItem(Map<String, Object> entityMap) throws DataAccessException {
		return assDefinedQueryMapper.queryAssReportItem(entityMap);
	}

	@Override
	public String queryAssDefinedQueryRun(Map<String, Object> entityMap) throws DataAccessException {
		
		Map<String,Object> assDefinedQuery = assDefinedQueryMapper.queryByCode(entityMap);
		
		List<Map<String,Object>> items = assDefinedQueryMapper.queryAssReportItem(entityMap);
		
		StringBuilder where = new StringBuilder();
		
		//拼接查询条件
		for (Map<String,Object> item : items) {
			String s_flag = String.valueOf(item.get("s_flag"));//是否查询1是2否
			String filedName = String.valueOf(item.get("field_name"));
			String queryMode = String.valueOf(item.get("s_mode"));//查询方式1单条2范围
			String filedType = String.valueOf(item.get("field_type"));//字段类型
			String operator = String.valueOf(item.get("where_operator"));
			
			if("1".equals(s_flag)){
				if(entityMap.containsKey(filedName) && entityMap.get(filedName) != null && !"".equals(entityMap.get(filedName))){
					String value = entityMap.get(filedName).toString();
					where.append("AND ");
					where.append(filedName);
					if("like".equals(operator) && "text".equals(filedType)){
						where.append(" like ");
						where.append("'%"+value+"%'");
					} else {
						where.append(" = ");
						where.append(applyType(filedType,value));
					}
					
				}
			}
		}
		
		entityMap.put("r_sql", getSql(assDefinedQuery.get("r_sql").toString()));
		
		entityMap.put("where_sql", where.toString());
		
		SysPage sysPage = new SysPage();

		sysPage = (SysPage) entityMap.get("sysPage");

		if (sysPage.getTotal() == -1) {

			List<Map<String, Object>> list = assDefinedQueryMapper.queryAssDefinedQueryRun(entityMap);

			return ChdJson.toJson(list);

		} else {

			RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());

			List<Map<String, Object>> list = assDefinedQueryMapper.queryAssDefinedQueryRun(entityMap, rowBounds);

			PageInfo page = new PageInfo(list);

			return ChdJson.toJson(list, page.getTotal());

		}
	}

	@Override
	public String deleteAssDefinedQuery(Map<String, Object> entityMap) throws DataAccessException {
		try {
			
			List<Map> params = JSON.parseArray(entityMap.get("param").toString(),Map.class);
			for (Map map : params) {
				map.put("group_id", entityMap.get("group_id"));
				map.put("hos_id", entityMap.get("hos_id"));
				map.put("copy_code", entityMap.get("copy_code"));
				assDefinedQueryMapper.deleteAssReportItemByRheadId(map);
			}
			assDefinedQueryMapper.deleteBatchAssDefinedQuery(params);
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";

		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new SysException(e.getMessage());
		}
	}

}
