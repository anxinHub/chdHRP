package com.chd.hrp.hpm.serviceImpl.customreport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
import com.chd.hrp.hpm.dao.AphiCustomTemplateReportConfMapper;
import com.chd.hrp.hpm.dao.AphiCustomTemplateReportMapper;
import com.chd.hrp.hpm.dao.AphiDeptDictMapper;
import com.chd.hrp.hpm.entity.AphiCustomTemplateReportConf;
import com.chd.hrp.hpm.entity.AphiDeptDict;
import com.chd.hrp.hpm.service.customreport.AphiCustomTemplateReportService;
import com.github.pagehelper.PageInfo;


@Service("aphiCustomTemplateReportService")
public class AphiCustomTemplateReportServiceImpl implements AphiCustomTemplateReportService {
	
	private static Logger logger = Logger.getLogger(AphiCustomTemplateReportServiceImpl.class);
	
	@Resource(name = "aphiCustomTemplateReportMapper")
	private final AphiCustomTemplateReportMapper aphiCustomTemplateReportMapper = null;
	
	@Resource(name = "aphiCustomTemplateReportConfMapper")
	private final AphiCustomTemplateReportConfMapper aphiCustomTemplateReportConfMapper = null;
	
	@Resource(name = "aphiDeptDictMapper")
	private final AphiDeptDictMapper aphiDeptDictMapper = null;
	
	//查询
	@Override
	public String queryAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
	
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			
			if(entityMap.get("is_perm") != null && "1".equals(String.valueOf(entityMap.get("is_perm")))){//有科室权限控制
				
				if(sql.toLowerCase().indexOf("v_user_data_perm") != -1){//权限表存在
					
					
					StringBuffer sb = new StringBuffer(sql);
					int permTableIndex = sql.toLowerCase().indexOf("v_user_data_perm")+18;//权限表出现的位置
					int lastWhereIndex = sql.toLowerCase().lastIndexOf("where");//最后一个where出现的位置
					//取科室权限表别名
					String alias = sql.substring(permTableIndex,lastWhereIndex);//权限表出现的位置到最后一个where之间
					alias = alias.replaceAll(" ", "");	
					
					if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
						String dept_id = String.valueOf(entityMap.get("dept_id"));
						//sql = sql.replace("b.dept_id", "'" + dept_code + "'");
						String deptWhere = " " + alias + ".perm_code = '" + dept_id + "' AND ";
						sb.insert(lastWhereIndex + 5, deptWhere);
						
						sql = sb.toString();
					}
				}
			}
			
			Matcher matcher01 = Pattern.compile("#\\[.*?\\]").matcher(sql);
			
			while (matcher01.find()) {
				
				String key01 = matcher01.group();//获取匹配到的参数

				Matcher matcher02 = Pattern.compile("#\\{.*?\\}").matcher(key01);
				
				String key02 = "";
				
				while (matcher02.find()) {
					
					key02 = matcher02.group().replace("#", "").replace("{", "").replace("}", "");//获取匹配到的参数

				}
				if(entityMap.get(key02) != null && !"".equals(entityMap.get(key02))){
					
					String key = key01.replaceAll("#\\{"+key02+"\\}", String.valueOf(entityMap.get(key02)));
					
					sql= sql.replace(key01, key.replace("#[", "").replace("]", ""));
					
				}else{
					
					sql = sql.replace(key01, "");
				}
				
			}

			entityMap.put("sql",sql);
			
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			
			if (sysPage.getTotal() == -1) {
				
				try {
					
					list = aphiCustomTemplateReportMapper.queryAphiCustomTemplateReportForParseSql(entityMap);
					
				} catch (Exception e) {
					// TODO: handle exception
					
					logger.error(e.getMessage(), e);
					
					return "{\"error\":\"操作失败,数据库异常,请在配置中检查SQL.\",\"state\":\"false\"}";
				}
				
				return ChdJson.toJsonLower(list);


			} else {

				RowBounds rowBounds = new RowBounds(sysPage.getPage(), sysPage.getPagesize());
				
				try {
					
					list = aphiCustomTemplateReportMapper.queryAphiCustomTemplateReportForParseSql(entityMap, rowBounds);
					
				} catch (Exception e) {
					// TODO: handle exception
					
					logger.error(e.getMessage(), e);
					
					return "{\"error\":\"操作失败,数据库异常,请在配置中检查SQL.\",\"state\":\"false\"}";
				}

				PageInfo page = new PageInfo(list);

				return ChdJson.toJsonLower(list, page.getTotal());

			}
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败,数据库异常.\",\"state\":\"false\"}");
			
		}
	}

	@Override
	public String initAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			//1.获取当前模板查询语句块
			Map<String,Object> queryMap = new HashMap<String,Object>();
			queryMap.put("group_id", entityMap.get("group_id"));
			queryMap.put("hos_id", entityMap.get("hos_id"));
			queryMap.put("copy_code", entityMap.get("copy_code"));
			queryMap.put("template_code", entityMap.get("template_code"));
			queryMap.put("user_id", entityMap.get("user_id"));
			queryMap.put("template_type", "01");
			AphiCustomTemplateReportConf queryObj = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(queryMap);
			if(queryObj == null){
				return "{\"warn\":\"模板配置数据中未找到查询语句块,请先配置.\",\"state\":\"false\"}";
			}
			
			String querySql = new String(queryObj.getTemplate_sql(),"UTF-8");
			//String querySql = queryObj.getTemplate_sql();
			queryMap.put("sql",querySql);
			
			
			//获取当前年月的上一个月
			String acct_yearm = DateUtil.getTopYear_Month(String.valueOf(entityMap.get("acct_yearm")));
			queryMap.put("acct_year", acct_yearm.substring(0, 4));
			queryMap.put("acct_month", acct_yearm.substring(4, 6));
			
			
			//查询上月数据
			List<Map<String, Object>> list = aphiCustomTemplateReportMapper.queryAphiCustomTemplateReportForParseSql(queryMap);
			if(list.size() == 0){
				return "{\"warn\":\"未找到上月数据,不能生成.\",\"state\":\"false\"}";
			}
			
			
			
			AphiCustomTemplateReportConf addObj = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			
			if(addObj == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String initSql = new String(addObj.getTemplate_sql(),"UTF-8");
			//String initSql = addObj.getTemplate_sql();
			
			entityMap.put("sql",initSql);
			
			aphiCustomTemplateReportMapper.initAphiCustomTemplateReportForParseSql(entityMap);
			
			return "{\"msg1\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false\"}");
		}
	}

	@Override
	public String updateAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String sql = actrc.getTemplate_sql();
			
			sql = matchAndReplaceSql(sql,entityMap);//匹配替换
			
			entityMap.put("sql",sql);
			
			aphiCustomTemplateReportMapper.updateAphiCustomTemplateReportForParseSql(entityMap);
			
			return "{\"msg1\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false\"}");
		}
	}

	@Override
	public String auditAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String sql = actrc.getTemplate_sql();
			
			entityMap.put("sql",sql);
			
			aphiCustomTemplateReportMapper.auditAphiCustomTemplateReportForParseSql(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false\"}");
		}
	}

	@Override
	public String reAuditAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String sql = actrc.getTemplate_sql();
			
			entityMap.put("sql",sql);
			
			aphiCustomTemplateReportMapper.reAuditAphiCustomTemplateReportForParseSql(entityMap);
			
			return "{\"msg\":\"操作成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \",\"state\":\"false\"}");
			
		}
	}

	@Override
	public List<Map<String,Object>> queryAphiCustomTemplateReportForParseSqlPrint(Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			
			if(entityMap.get("is_perm") != null && "1".equals(String.valueOf(entityMap.get("is_perm")))){//有科室权限控制
				
				if(sql.toLowerCase().indexOf("v_user_data_perm") != -1){//权限表存在
					
					
					StringBuffer sb = new StringBuffer(sql);
					int permTableIndex = sql.toLowerCase().indexOf("v_user_data_perm")+18;//权限表出现的位置
					int lastWhereIndex = sql.toLowerCase().lastIndexOf("where");//最后一个where出现的位置
					//取科室权限表别名
					String alias = sql.substring(permTableIndex,lastWhereIndex);//权限表出现的位置到最后一个where之间
					alias = alias.replaceAll(" ", "");	
					
					if(entityMap.get("dept_id") != null && !"".equals(entityMap.get("dept_id"))){
						String dept_id = String.valueOf(entityMap.get("dept_id"));
						//sql = sql.replace("b.dept_id", "'" + dept_code + "'");
						String deptWhere = " " + alias + ".perm_code = '" + dept_id + "' AND ";
						sb.insert(lastWhereIndex + 5, deptWhere);
						
						sql = sb.toString();
					}
				}
			}
			
			Matcher matcher01 = Pattern.compile("#\\[.*?\\]").matcher(sql);
			
			while (matcher01.find()) {
				
				String key01 = matcher01.group();//获取匹配到的参数

				Matcher matcher02 = Pattern.compile("#\\{.*?\\}").matcher(key01);
				
				String key02 = "";
				
				while (matcher02.find()) {
					
					key02 = matcher02.group().replace("#", "").replace("{", "").replace("}", "");//获取匹配到的参数

				}
				if(entityMap.get(key02) != null && !"".equals(entityMap.get(key02))){
					
					String key = key01.replaceAll("#\\{"+key02+"\\}", String.valueOf(entityMap.get(key02)));
					
					sql= sql.replace(key01, key.replace("#[", "").replace("]", ""));
					
				}else{
					
					sql = sql.replace(key01, "");
				}
				
			}
			
			entityMap.put("sql",sql);
			
			return  aphiCustomTemplateReportMapper.queryAphiCustomTemplateReportForParseSql(entityMap);
				
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}

	@Override
	public String importAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException{
		// TODO Auto-generated method stub
		
		try {
			
			
			//1.查询模板配置数据
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			//1.1从模版中取出配置的SQL
			String template_sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String template_sql = actrc.getTemplate_sql();
			String add_sql = template_sql.substring(template_sql.toLowerCase().indexOf("insert"),template_sql.lastIndexOf(");")+2);//取出添加语句
			
			//2.判断表头是否为空
			String columns=entityMap.get("columns").toString();
			JSONArray jsonColumns = JSONObject.parseArray(columns);	
			if(jsonColumns==null || jsonColumns.size()==0){
				return "{\"error\":\"表头为空！\",\"state\":\"false\"}";
			}
			
			//3.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMapOrderly(content,1);
			if(liData==null || liData.size()==0){
				return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
			}
			
			Matcher matcher3 = Pattern.compile("#\\[.*?\\]").matcher(add_sql);
			String import_sql = null;
			
			if(matcher3.find()){//可能横向、可能纵向
				
				//1.判断纵向
				//1.1取出添加语句中的参数
				List<String> addList = getSqlParaList(add_sql,null);
				
				//1.2取出模版中的表头列
				List<String> headList = getExcelHeadList(liData);
				
				/*if(headList.size() < addList.size()){
					return "{\"warn\":\"模板列数不能小于添加参数个数\",\"state\":\"false\"}";
				}*/
				
				//1.3如果添加参数与表头列个数、名称一致,就是纵向导入
				if(addParaCompareExcelHead(addList,headList)){
					//导入
					import_sql = import_2(entityMap,actrc);
					
				}else{//横向
					
					import_sql = import_1(entityMap,actrc);
				}	
			}else{
				
				import_sql = import_2(entityMap,actrc);
			}
			
			if(import_sql.indexOf("\"warn\"") != -1){
				return import_sql;
			}
			
			entityMap.put("sql",import_sql);
			
			aphiCustomTemplateReportMapper.initAphiCustomTemplateReportForParseSql(entityMap);
			
			return "{\"msg\":\"导入成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO Auto-generated catch block

			logger.error(e.getMessage(), e);
			
			return "{\"warn\":\"操作失败 "+e.getMessage()+"\",\"state\":\"false\"}";
			
			//throw new SysException();
		}
		
	}
	
	
	@Override
	public String deleteAphiCustomTemplateReportForParseSql(Map<String, Object> entityMap) throws DataAccessException {
		
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String sql = actrc.getTemplate_sql();
			
			JSONArray jsonData = JSONArray.parseArray(String.valueOf(entityMap.get("data")));
			Iterator iterator = jsonData.iterator();
			
			
			while(iterator.hasNext()){
				
				String del_sql = sql;
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				for (String cell:jsonObj.keySet()) {
					mapVo.put(cell, jsonObj.get(cell));
				}
				
				del_sql = matchAndReplaceSql(del_sql,mapVo);//匹配替换
				
				entityMap.put("sql",del_sql);
				
				aphiCustomTemplateReportMapper.deleteAphiCustomTemplateReportForParseSql(entityMap);
			}
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
	
	
	
	/**
	 * @Description 替换删除SQL语句
	 * @param delete_sql-删除SQL语句
	 * @param dataList-去重后的删除参数
	 * @param headMap-表头列
	 * @return String
	 * @throws Exception
	 */
	public StringBuffer replaceDeleteSql(String delete_sql,List<Map<String,String>> dataList,StringBuffer import_sql,Map<String,String> headMap){
		
		for(Map<String,String> map:dataList){
			
			String sql = delete_sql;
			Matcher matcher = Pattern.compile("#\\{.*?\\}").matcher(sql);//将配置的SQL语句作用到正则表达式上,获取匹配对象
			
			while (matcher.find()) {
				String key = matcher.group();
				String head = key.replace("#", "").replace("{", "").replace("}", "");
				
				if(headMap.get(head) == null){
					return new StringBuffer().append("-1").append(":").append("模板配置中的删除参数名称【"+head+"】必须在EXCEL表头中存在");
				}
				
				String value = map.get(head);
				sql = sql.replace(key,"'" + value + "'");
			}
			
			import_sql.insert(0, sql + ";\r\n");
			
		}
		
		return import_sql;
	}
	
	
	
	

	/**
	 * @Description 替换添加SQL语句
	 * @param importSql-模板配置SQL
	 * @param valueList-excel值
	 * @param headMap-表头列
	 * @return String
	 * @throws Exception
	 */
	//将读取出来的值替换到导入的SQL中
	public String replaceAddSql(String add_sql,List<Object> valueList,Map<String,String> headMap){
		
		String sql = add_sql.replaceAll("\\[", "{").replaceAll("\\]", "}");
		Matcher matcher = Pattern.compile("#\\{.*?\\}").matcher(sql);//将配置的SQL语句作用到正则表达式上,获取匹配对象
		List<String> columnList = getMapToList(headMap);
		
		
		
		//-1：导入模板的列数与配置中SQL添加参数个数不匹配
		//-2：模板配置中的添加参数,在EXCEL中的表头中不存在
		//-3:模板配置中添加参数与EXCEL对应列中的表头不一致(从左往右)
		int x = 0;
		while (matcher.find()) {
			
			if(x+1 >valueList.size()){//避免数组角标越界
				return "-1";
			}
			String key = matcher.group() ;//获取匹配到的参数
			
			String head = key.replace("#", "").replace("{", "").replace("}", "");
			if(headMap.get(head) == null){
				return "-2" + ":" + "配置中的添加参数【" + head + "】必须在EXCEL表头中存在";
			}
			
			if(!head.equals(columnList.get(x))){
				return "-3";
			}
			//根据模板SQL进行替换
			sql = sql.replace(key, "'" + valueList.get(x) + "'");//将匹配的参数,依次赋值
			x++;
		}
		
		if(x != valueList.size()){
			return "-1";
		}
		
		return sql;
	}
	
	
	//获取删除参数列表
	public Map<String,String> getDeleteParam(String delete_sql){
		
		String sql =  delete_sql;
		
		Matcher matcher = Pattern.compile("#\\{.*?\\}").matcher(sql);//将配置的SQL语句作用到正则表达式上,获取匹配对象
		Map<String,String> paraMap = new HashMap<String,String>();
		while (matcher.find()) {
			String key = matcher.group() ;//获取匹配到的参数
			
			key = key.replaceAll("#", "").replaceAll("\\{", "").replaceAll("\\}", "");
			paraMap.put(key,key);
		}
		
		return paraMap;
		
	}
	
	
	//List去重
	public List<Map<String,String>> distinctList(List<Map<String,String>> deleteParaList){
		
		List<Map<String, String>> list = deleteParaList;
	    for (int i = 0; i < list.size(); i++) {
	    	Map<String, String> m1 = list.get(i);
	      
		    for (int j = i+1; j < list.size(); j++) {
		    	Map<String, String> m2 = list.get(j);
		    	if(m1.equals(m2)){
		    		list.remove(j);
		    		continue;
		    	}
		    }
	    }
	    
	    return list;
	}
	
	
	public List<String> getMapToList(Map<String,String> headMap){
		
		List<String> list = new ArrayList<String>();
		for(Map.Entry<String,String> map:headMap.entrySet()){
			
			list.add(map.getKey());
		}
		
		return list;
	}
	
	
	public Map<String,String> getListToMap(List<String> list){
		
		Map<String,String> map = new LinkedHashMap<String,String>();
		
		for(String key:list){
			
			map.put(key, key);
		}
		
		return map;
	}
	
	
	public String matchAndReplaceSql(String sql,Map<String,Object> paraMap){
		
		String result = sql;
		Matcher matcher = Pattern.compile("#\\{.*?\\}").matcher(result);
		
		
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("#", "").replaceAll("\\{", "").replaceAll("\\}", "");
			
			if(paraMap.get(column) == null){
				result = "-1";
				break;
			}
			
			String value = String.valueOf(paraMap.get(column));
			
			result = result.replace(key, "'" + value + "'");
			
		}
		
		
		return result;
	}

	public List<String> getSqlParaList(String add_Sql,String reg){
		
		List<String> list = new ArrayList<String>();
		
		if(reg == null || "".equals(reg)){
			
			String sql = add_Sql;
			Matcher matcher = Pattern.compile("#\\[.*?\\]").matcher(sql);
			
			while (matcher.find()) {
				
				String key = matcher.group().replace("#", "").replace("[", "").replace("]", "") ;//获取匹配到的参数
				list.add(key);
			}
			
			
			String sql_2 = add_Sql;
			Matcher matcher_2 = Pattern.compile("#\\{.*?\\}").matcher(sql_2);
			
			while (matcher_2.find()) {
				
				String key = matcher_2.group().replace("#", "").replace("{", "").replace("}", "") ;//获取匹配到的参数
				list.add(key);
			}
			
		}else{
			
			String sql = add_Sql;
			Matcher matcher = Pattern.compile(reg).matcher(sql);
			
			while (matcher.find()) {
				
				String key = matcher.group() ;//获取匹配到的参数
				key = key.replace("#", "").replace("[", "").replace("]", "") ;
				key = key.replace("#", "").replace("{", "").replace("}", "") ;
				list.add(key);
			}
		}
		
		return list;
	}
	
	
	public List<String> getExcelHeadList(List<Map<String,List<String>>> liData){
		
		List<String> list = new ArrayList<String>();
		
		int x = 0;
		for(Map<String,List<String>> item : liData ){
			if(x > 0){
				break;
			}
			
			for(Map.Entry<String, List<String>> entry : item.entrySet()){
				String key = entry.getKey();//列名称
				list.add(key);
			}
			x++;
		}
		
		
		return list;
	}
	
	public boolean addParaCompareExcelHead(List<String> addParaList,List<String> ExcelHeadList){
		
		if(addParaList.size() != ExcelHeadList.size()){
			
			return false;
		}
		
		for(int x = 0 ; x < addParaList.size() ; x++){
			
			String addPara = addParaList.get(x);
			
			String excelHead = ExcelHeadList.get(x);
			
			if(!addPara.equals(excelHead)){
				
				return false;
			}
		}
		
		return true;
	}
	
	
	public Map<String,String> getDeptMap(){
		
		Map<String,Object> queryMap = new HashMap<String,Object>();
		
		queryMap.put("group_id", SessionManager.getGroupId()); 
		queryMap.put("hos_id", SessionManager.getHosId()); 
		queryMap.put("copy_code", SessionManager.getCopyCode()); 
		queryMap.put("user_id", SessionManager.getUserId());
		queryMap.put("is_stop","0"); 
		List<AphiDeptDict> deptList = aphiDeptDictMapper.queryPrmDeptDict(queryMap);
		
		Map<String,String> deptMap = new HashMap<String,String>();
		for(AphiDeptDict dept : deptList){
			
			String dept_code = dept.getDept_code();
			String dept_name = dept.getDept_name();
			
			deptMap.put(dept_code, dept_code);
			deptMap.put(dept_name, dept_code);
		}
		
		
		return deptMap;
	}
	
	
	/*public Map<String,Object> matchPara(String add_sql,String reg){
		
		Map<String,Object> map = new HashMap<String,Object>();
		
		String sql =  add_sql;
		Matcher matcher = Pattern.compile(reg).matcher(sql);
		while (matcher.find()) {
			String key = matcher.group() ;//获取匹配到的参数
			map.put(key,key);
		}
		
		return map;
	}
	
	public String replaceAddSql_2(String add_sql,Map<String,Object> map){
		
		String sql = add_sql;
		
		Matcher matcher = Pattern.compile("#\\[.*?\\]").matcher(sql);
		while (matcher.find()) {
			String para = matcher.group() ;//获取匹配到的参数
			
			String key  = para.replaceAll("#", "").replaceAll("\\[", "").replaceAll("\\]", "");
			
			Object value = map.get(key);
			
			sql.replace(para, String.valueOf(value));
		}
		
		Matcher matcher_2 = Pattern.compile("#\\{.*?\\}").matcher(sql);
		while (matcher_2.find()) {
			String para = matcher.group() ;//获取匹配到的参数
			
			String key  = para.replaceAll("#", "").replaceAll("\\{", "").replaceAll("\\}", "");
			
			Object value = map.get(key);
			
			sql.replace(para, String.valueOf(value));
		}
		
		return null;
	}
	*/
	
	
	
	/*
	 	横向导入
	 */
	public String import_1(Map<String,Object> entityMap,AphiCustomTemplateReportConf actrc){
		
		try {
			
			//1.1从模版中取出配置的SQL
			String template_sql = new String(actrc.getTemplate_sql(),"UTF-8");
			
			//String template_sql = actrc.getTemplate_sql();
			String delete_sql = template_sql.substring(template_sql.toLowerCase().indexOf("delete"),template_sql.indexOf(";"));//取出删除语句
			String add_sql = template_sql.substring(template_sql.toLowerCase().indexOf("insert"),template_sql.lastIndexOf(");")+2);//取出添加语句
			
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMapOrderly(content,1);
			
			
			//3.1声明
			StringBuffer import_sql = new StringBuffer();//用于存储SQL
			Map<String,String> deleteParaMap = getDeleteParam(delete_sql);//获取删除参数列表
			List<Map<String,String>> deletelist = new ArrayList<Map<String,String>>(); //存储所有数据行的删除参数,用于去重
			Map<String,String> excelHeadsMap = new LinkedHashMap<String,String>();//存储Excel中表头名称
			Map<String,String> deptMap = getDeptMap();//获取字典中所有科室
			
			
			//1.2取出模版中的表头列
			List<String> headList = getExcelHeadList(liData);
			
			//取出固定参数
			List<String> fiexdAddList = getSqlParaList(add_sql,"#\\[.*?\\]");
			Map<String,String> map = getListToMap(fiexdAddList);
			//List<Map<String,Object>> valueList = new ArrayList<Map<String,Object>>();
			
			
			//取非固定列表头
			for(Map<String,List<String>> item : liData ){
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					String key = entry.getKey() ;
					if(map.get(key) != null){
						continue;
					}
					
					excelHeadsMap.put(key, key);
				}
				break;//取非固定列的表头一次
			}
			
			
			for(Map.Entry<String, String> entry:excelHeadsMap.entrySet()){//多少列,多少条语句
				
				
				Map<String,String> deleteMap = new HashMap<String,String>();//删除参数列表
				
				for(Map<String,List<String>> item : liData ){//遍历所有行
					
					
					String sql = add_sql;
					sql = sql.replaceAll("\\[", "{").replaceAll("\\]", "}");
					
					for(int x = 0 ; x < fiexdAddList.size() ; x++){//遍历所有固定列、替换
						
						String key = fiexdAddList.get(x);
						List<String> obj = item.get(key);
						
						String value = obj.get(1);
						if (!"备注".equals(key)) {
							if(value == null || "".equals(value)){
								return "{\"warn\":\"属性值不能为空\",\"state\":\"false\",\"row_cell\":\"" +obj.get(0) +"\"}";
							}
						}else {
							if (value == null || "".equals(value)) {
								value = " ";
							}
						}
						
						if("科室编码".equals(key)){
							value = deptMap.get(value);//如果传入是科室名称,取出科室编码
							if(value == null){
								return "{\"warn\":\"科室编码不存在\",\"state\":\"false\",\"row_cell\":\"" +obj.get(0) +"\"}";
							}
						}
						
						//将删除语句中的参数存起来,用于去重后组装删除SQL
						if(deleteParaMap.get(key) != null){
							deleteMap.put(key, value);
						}
						
						
						sql = sql.replace("#{" + key + "}", "'" + value + "'");
					}
					
					
					List<String> obj = item.get(entry.getKey());
					String value = obj.get(1);
					if (!"备注".equals(entry.getKey())) {
						if(value == null || "".equals(value)){
							return "{\"warn\":\"属性值不能为空\",\"state\":\"false\",\"row_cell\":\"" +obj.get(0) +"\"}";
						}
					}else {
						if (value == null || "".equals(value)) {
							value = " ";
						}
					}
					
					if("科室编码".equals(entry.getKey())){
						value = deptMap.get(value);//如果当前列值是科室名称,取出科室编码
						if(value == null){
							return "{\"warn\":\"科室编码不存在\",\"state\":\"false\",\"row_cell\":\"" +obj.get(0) +"\"}";
						}
					}
					
					Matcher matcher = Pattern.compile("#\\{.*?\\}").matcher(sql);
					int x = 1;
					while (matcher.find()) {
						String para = matcher.group() ;//获取匹配到的参数
						if(x == 1){
							sql = sql.replace(para, "'" + entry.getKey() + "'");
						}else{
							sql = sql.replace(para, "'" + value + "'");
						}
						x++;
					}
					
					//将删除语句中的参数存起来,用于去重后组装删除SQL
					if(deleteParaMap.get(entry.getKey()) != null){
						deleteMap.put(entry.getKey(), value);
					}
					
					import_sql.append(sql + "\r\n");
				}
				
				//存储删除参数
				deletelist.add(deleteMap);
			}
			
			//去除重复的删除参数
			List<Map<String,String>> newDeleteList = distinctList(deletelist);
			
			
			Map<String,String> headMap = getListToMap(headList);
			//替换删除语句
			import_sql = replaceDeleteSql(delete_sql,newDeleteList,import_sql,headMap);
			if(import_sql.toString().startsWith("-1")){
				String msg = import_sql.toString().split(":")[1];
				return "{\"warn\":\"" + msg + "\",\"state\":\"false\"}";
			}
			
			return import_sql.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
		
	}
	
	
	/*
 	纵向导入
	 */
	public String import_2(Map<String,Object> entityMap,AphiCustomTemplateReportConf actrc){
		
		try {
			
			//1.1从模版中取出配置的SQL
			String template_sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String template_sql = actrc.getTemplate_sql();
			String delete_sql = template_sql.substring(template_sql.toLowerCase().indexOf("delete"),template_sql.indexOf(";"));//取出删除语句
			String add_sql = template_sql.substring(template_sql.toLowerCase().indexOf("insert"),template_sql.lastIndexOf(");")+2);//取出添加语句
			
			
			//3.判断数据是否为空
			String content=entityMap.get("content").toString();
			List<Map<String,List<String>>> liData=SpreadTableJSUtil.toListMapOrderly(content,1);
			
			//3.1声明
			StringBuffer err_sb = new StringBuffer();//用于记录重复数据
			Map<String,String> exitMap = new HashMap<String,String>();//用于判断数据是否重复
			StringBuffer import_sql = new StringBuffer();//用于存储SQL
			Map<String,String> deleteParaMap = getDeleteParam(delete_sql);//获取删除参数列表
			List<Map<String,String>> deletelist = new ArrayList<Map<String,String>>(); //存储所有数据行的删除参数,用于去重
			Map<String,String> excelHeadsMap = new LinkedHashMap<String,String>();//存储Excel中表头名称
			Map<String,String> deptMap = getDeptMap();//获取字典中所有科室
			
			
			int x = 0;//取表头标记,只取一次
			//4.遍历导入数据所有行
			for(Map<String,List<String>> item : liData ){
				
				String exitKey = "";//用于判断数据是否重复
				String msg = "";//用于记录重复数据提示信息
				Map<String,String> deleteMap = new HashMap<String,String>();//删除参数列表
				List<Object> valueList = new ArrayList<Object>();//存储读取到的当前行每一列的值,用于替换时将值替换到模板的添加SQL中
				
				
				//5.遍历所有列
				for(Map.Entry<String, List<String>> entry : item.entrySet()){
					
					//存表头名称
					String key = entry.getKey();//列名称
					if(x == 0){
						excelHeadsMap.put(key, key);//存储表头名称
					}
					
					
					//取值判断
					List<String> obj = item.get(key) ;//根据列名获取列值, obj的值是obj[0]=坐标,obj[1]是值
					String value = obj.get(1);//获取列值
					if (!"备注".equals(key)) {
						if(obj.get(1) == null){
							return "{\"warn\":\"属性值不能为空\",\"state\":\"false\",\"row_cell\":\"" +obj.get(0) +"\"}";
						}
					}else {
						if (value == null || "".equals(value)) {
							value = " ";
						}
					}
					
					
					//记录值、重复判断信息、重复提示信息
					exitKey += "&"+value;//记录所有值,用于判断数据是否重复
					msg += key + ":" + value + ",";//记录信息,用于提示
					
					
					if("科室编码".equals(key)){
						//到科室Map中取科室编码,存入List
						String dept_code = deptMap.get(value);
						if(dept_code == null){
							return "{\"warn\":\"科室编码不存在\",\"state\":\"false\",\"row_cell\":\"" +obj.get(0) +"\"}";
						}
						
						valueList.add(dept_code);
					}else{
						valueList.add(value);//存储当前行的值
					}
					
					
					//将删除语句中的参数存起来,用于去重后组装删除SQL
					if(deleteParaMap.get(key) != null){
						deleteMap.put(key, value);
					}
				}
				x++;
				
				//存储删除参数
				deletelist.add(deleteMap);
				
				//6.当前行遍历完之后,判断导入数据是否重复
				if(exitMap.get(exitKey) != null){
					msg = msg.substring(0, msg.length()-1);
					err_sb.append(msg + "<br/>");
				}else{
					exitMap.put(exitKey, exitKey);
				}
				
				
				//把值替换到添加SQL语句中
				String addsql = replaceAddSql(add_sql,valueList,excelHeadsMap);
				if("-1".equals(addsql)){
					return "{\"warn\":\"导入模板的列数与配置中SQL添加参数个数不匹配\",\"state\":\"false\"}";
				}
				
				if(addsql.startsWith("-2")){
					String warn = addsql.split(":")[1];
					return "{\"warn\":\"" + warn +"\",\"state\":\"false\"}";
				}
				if("-3".equals(addsql)){
					return "{\"warn\":\"配置导入语句中SQL,添加参数与EXCEL表头顺序必须一致\",\"state\":\"false\"}";
				}
				
				//将替换过后的SQL存到StringBuffer中
				import_sql.append(addsql + "\r\n");
			}
			
			
			//去除重复的删除参数
			List<Map<String,String>> newDeleteList = distinctList(deletelist);
			//替换删除语句
			import_sql = replaceDeleteSql(delete_sql,newDeleteList,import_sql,excelHeadsMap);
			if(import_sql.toString().startsWith("-1")){
				String msg = import_sql.toString().split(":")[1];
				return "{\"warn\":\"" + msg + "\",\"state\":\"false\"}";
			}
			
			
			if(err_sb.length() > 0){
				 return "{\"warn\":\"以下数据【" +err_sb.toString() + "】数据重复！\",\"state\":\"false\"}";
			}
			
			
			return import_sql.toString();
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}

	@Override
	public String addAphiCustomTemplateReportForParseSql(
			Map<String, Object> entityMap) throws DataAccessException {
		// TODO Auto-generated method stub
		try {
			
			AphiCustomTemplateReportConf actrc = aphiCustomTemplateReportConfMapper.queryAphiCustomTemplateReportConfByCode(entityMap);
			if(actrc == null){
				return "{\"warn\":\"未找到模板配置数据.\",\"state\":\"false\"}";
			}
			
			String sql = new String(actrc.getTemplate_sql(),"UTF-8");
			//String sql = actrc.getTemplate_sql();
			
			JSONArray jsonData = JSONArray.parseArray(String.valueOf(entityMap.get("data")));
			Iterator iterator = jsonData.iterator();
			
			
			while(iterator.hasNext()){
				
				String add_sql = sql;
				JSONObject jsonObj = JSONObject.parseObject(iterator.next().toString());
				
				Map<String,Object> mapVo = new HashMap<String,Object>();
				for (String cell:jsonObj.keySet()) {
					mapVo.put(cell, jsonObj.get(cell));
				}
				
				add_sql = matchAndReplaceSql(add_sql,mapVo);//匹配替换
				
				entityMap.put("sql",add_sql);
				
				aphiCustomTemplateReportMapper.addAphiCustomTemplateReportForParseSql(entityMap);
			}
			
			
			return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			// TODO: handle exception
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}
}
