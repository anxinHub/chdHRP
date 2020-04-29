package com.chd.hrp.hr.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.ibatis.session.RowBounds;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.SessionManager;
import com.chd.base.SysPage;
import com.chd.base.exception.SysException;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hr.dao.DeleteMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.DeleteService;

@Service("deleteService")
public class DeleteServiceImpl implements DeleteService {
	private static Logger logger = Logger.getLogger(DeleteServiceImpl.class);

	@Resource(name = "deleteMapper")
	private final DeleteMapper deleteMapper = null;

	// 引入DAO操作
	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;
	
	
	@SuppressWarnings("unchecked")
	@Override
	public String deleteBaseInfo(String paramVo,String servletPath)
			throws DataAccessException {


		
		try {	
			@SuppressWarnings("rawtypes")
		List<Map> listVo = JSONArray.parseArray(paramVo, Map.class);
		List<Map> listV = new ArrayList<Map>();
		List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();
		List<String> dataList= new ArrayList<String>();
		for (Map map : listVo) {
			/**
			 * 添加一些基本参数
			 */
			map.put("group_id", SessionManager.getGroupId());
			map.put("hos_id", SessionManager.getHosId());
			map.put("copy_code", SessionManager.getCopyCode());
			map.put("user_id", SessionManager.getUserId());
			
			StringBuilder sb = new StringBuilder();
			/**
			 * 从数据表构建取数据表结构
			 */
			HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(map);
			
			if(hrTableStruc != null && hrTableStruc.getTab_sql() != null && !"".equals(hrTableStruc.getTab_sql())){
				/**
				 * 转换成list方便提取sql
				 */
				list1=toListMap( hrTableStruc.getTab_sql());
				
			}
			
			
		
			String sql = new String() ; /*new String(actrc.getTemplate_sql(),"UTF-8")*/;
			
			
			for (Map<String, Object> sqlmap : list1) {
				/**
				 * 提取sql
				 */
				
				//通过sql_code匹配delete方法取出sql

				if(sqlmap.get("sql_code").toString().equals(servletPath)){
					
					sql=sqlmap.get("sql_statement").toString();
					
                Map<String, Object> sqlMap=new HashMap<String, Object>();
					
              //获取数据列
				HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(map);
				//数据列中文替换成英文例如编码替换成code
				
				sql=matchAndReplaceCol(sql,hrTableStruc1);
				/**
				 *  替换特殊字符 example 五笔码 拼音码 代理主键  当前时间等 变更表的时候使用
				 */
				sqlMap=matchAndReplace(sql,map);
				
					sqlMap  = matchAndReplaceSql(sqlMap.get("add_Sql").toString(),map);//匹配替换
					
					
					dataList.add(sqlMap.get("result").toString());
				
				}
			}
					
				
			
		}
		//通用新增sql	
		if (dataList.size() > 0 ) {
					
					List<String> stringSqlList= new  ArrayList<String>();
					
					for (int i=0;  i<  dataList.size(); i++) {
						stringSqlList.add(dataList.get(i));
						/**
						 * 500条提交一次数据
						 */
						if( i>0 && i==500 || i == dataList.size() - 1) {
							deleteMapper.deleteBatchBase(stringSqlList);
							stringSqlList.clear();
						}
	                     
					}
					
				}
	/*	for (HosStation hrStation : listVo) {
			str = str + hrBaseService.isExistsDataByTable("HOS_STATION", hrStation.getStation_code()) == null ? "" : hrBaseService.isExistsDataByTable(
					"HOS_STATION", hrStation.getStation_code());
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}

		}
		if (!falg) {
			return ("{\"error\":\"删除失败，选择的岗位被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}");
		}*/
			
		
			
			
			
			
			
			return "{\"msg\":\"删除成功.\",\"state\":\"true\"}";
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}


	}
	public Map<String, Object>  matchAndReplaceSql(String sql,Map<String,Object> paraMap){
		Map<String, Object>  map=new HashMap<String, Object>();
		String result = sql.toLowerCase()         					//将所有字符都转成小写 为分割处理做准备
			   	   .replaceAll("\n|\t|\r|\\s{1,}", " ");	//去掉所有换行符 制表符 多个空格转换为单个空格  
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		//(@)(.*?),
		String value=null;
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(paraMap.get(column.toLowerCase()) == null){
				value="";
			}else{
			
			 value = String.valueOf(paraMap.get(column.toLowerCase()));
			}
			//获取长度
		/*	int length=getStringLength(value,getEncoding(value));
			int dataLength=0;
			if(length>0){
				result = "-2";
				map.put("value", value);
				break;
				
			}*/
			result = result.replace(key, "'" + value + "'");
			
		}
		map.put("result", result);
		
		return map;
	}
	/**
	 * 替换成英文
	 * @param sql
	 * @param hrTableStruc1
	 * @return
	 */
	public String  matchAndReplaceCol(String sql,HrTableStruc hrTableStruc1){
		Map<String, Object>  map=new HashMap<String, Object>();
		String result = sql        					//将所有字符都转成小写 为分割处理做准备
			   	   .replaceAll("\n|\t|\r|\\s{1,}", " ");	//去掉所有换行符 制表符 多个空格转换为单个空格  
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		//(@)(.*?),
		String value=null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list=toListMap( hrTableStruc1.getTab_col());
		Map<String, Object> tabMap= new HashMap<String, Object>();
		/**
		 * 遍历数据列名称和code
		 */
		for (Map<String, Object> map2 : list) {
			tabMap.put(map2.get("col_name").toString(), map2.get("col_code"));
		}
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(tabMap.get(column) == null){
				value=key;
			}else{
			
			 value = "["+String.valueOf(tabMap.get(column))+"]";
			}
	
			result = result.replace(key, value);
			
		}
		
		return result;
	}
	/**
	 * 替换特殊字符 example 五笔码 拼音码 代理主键  当前时间等
	 * [系统当前时间]
            如果类型 = 当前日期 
           1.默认 "yyyy-mm-dd"
     2.如果含有冒号 则根据冒号进行解析 例如[系统当前时间:yyyy-mm]
            如果类型 = 当前时间
           1.默认 "yyyy-mm-dd 09:39:39.123456" 小数点后面6位
      2.如果还有冒号 根据冒号进行解析 [系统当前时间:yyyy-mm-dd 09:39:39.123456]


         [拼音码]  = [拼音码:科室名称/dept_name]
         [五笔码]  = [笔码:科室名称/dept_name]
         [代理主键]  
                 默认值 取15位UUID
               如果含有冒号 则代表当前是第二条SQL 去第一条SQL已经生成的主键值 例如 [代理主键:dept_id] 
        [自增序列] 暂不处理
	 * @param add_sql
	 * @param entityMap 
	 * @return
	 */
	private Map<String, Object> matchAndReplace(String add_sql, Map<String, Object> entityMap) {
		Map<String, Object>  map=new HashMap<String, Object>();
		
		StringBuffer sql =  new StringBuffer();
		
        //获取当前日期
        Date date = new Date();
        
		String result = add_sql;
		if(result.indexOf(";")!=-1){
		//Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		String[] splSql = result.split(";");
		
		for(int i = 0;i < splSql.length;i++) {
			String tab_code;
			if(splSql[i].indexOf("INTO")!=-1){
				//截取表名
				tab_code=splSql[i].substring(splSql[i].lastIndexOf("INTO")+4,splSql[i].indexOf("(")).replaceAll("\n|\t|\r|\\s{1,}", " ").replaceAll(" ", "");
				
			}else{
				tab_code=splSql[i].substring(splSql[i].lastIndexOf("FROM")+4,splSql[i].indexOf("WHERE")).replaceAll("\n|\t|\r|\\s{1,}", " ").replaceAll(" ", "");

			}
			//String[] splSql=str.split("(AND |OR  )");
			Map<String, Object> keyMap=new HashMap<String, Object>();
			keyMap.put("group_id", SessionManager.getGroupId());
			keyMap.put("hos_id", SessionManager.getHosId());
			keyMap.put("copy_code", SessionManager.getCopyCode());
			keyMap.put("tab_code", tab_code);
			//获取代理主键uuid
			String uuid=UUIDLong.absStringUUID();
			
		//(@)(.*?),
		String value=null;
		//获取数据列
		HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(keyMap);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		if(hrTableStruc1==null){continue;}
		list=toListMap( hrTableStruc1.getTab_col());
		
		Map<String, Map<String, Object>> tabMap= new HashMap<String, Map<String, Object>>();
		Map<String, Object> tabDateMap= new HashMap<String,Object>();
		//获取数据列的数据格式
		for (Map<String, Object> map2 : list) {
			
			if(map2.get("value_mode_code")!=null&&map2.get("value_mode_code").toString().equals("03")){
				Map<String, Object> colMap=new HashMap<String, Object>();
				colMap.put("field_tab_code", map2.get("field_tab_code"));
				colMap.put("field_tab_name", map2.get("field_tab_name"));
				colMap.put("col_code", map2.get("col_code"));
				colMap.put("col_name", map2.get("col_name"));
				colMap.put(map2.get("col_name").toString(), map2.get("data_type_code"));
				tabMap.put(map2.get("field_tab_name").toString(),colMap);
			}else{
				tabDateMap.put(map2.get("col_code").toString(),map2.get("data_type_code"));
				
			}
		
		}
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(splSql[i]);//正则查找标识符变量
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(key.indexOf("五笔码")!=-1){
				String str1=column.substring(0, column.indexOf(":"));
				String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
				value=StringTool.toWuBi(entityMap.get(str2).toString());
		
				splSql[i] = splSql[i].replace(key, "[" + tabMap.get("五笔码").get("col_code").toString().toLowerCase() + "]");
				entityMap.put(tabMap.get("五笔码").get("col_code").toString().toLowerCase(), value);
				
			}else if(key.indexOf("拼音码")!=-1){
				String str1=column.substring(0, column.indexOf(":"));
				String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
				value=StringTool.toPinyinShouZiMu(entityMap.get(str2).toString());
				splSql[i] = splSql[i].replace(key,"[" + tabMap.get("拼音码").get("col_code").toString().toLowerCase() + "]");
				entityMap.put(tabMap.get("拼音码").get("col_code").toString().toLowerCase(), value);		
				}else if(key.indexOf("代理主键")!=-1){
			/*	String str1=column.substring(0, column.indexOf(":"));
				String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
				*/
				//result = result.replace(key, uuid);
					splSql[i] = splSql[i].replace(key,"[" + tabMap.get("代理主键").get("col_code").toString().toLowerCase()+ "]");

				entityMap.put(tabMap.get("代理主键").get("col_code").toString().toLowerCase(), uuid);	
			}else if(key.indexOf("当前时间")!=-1){
			
			
					/**
					 * 如果类型 = 当前时间
                          1.默认 "yyyy-mm-dd 09:39:39.123456" 小数点后面6位
                          2.如果还有冒号 根据冒号进行解析 [系统当前时间:yyyy-mm-dd 09:39:39.123456]
					 */
					//yyyy-MM-dd HH:mm:ss
					if( column.indexOf(":")!=-1){
						String str1=column.substring(0, column.indexOf(":"));
						String str2=column.substring(str1.length()+1, column.length());
						 //格式化日期
				        SimpleDateFormat df = new SimpleDateFormat(str2);
						/*result = result.replace(key, " TO_DATE('" + + "','"+str2+"')");
						entityMap.put("", uuid);*/
				        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
						entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
						
					}else{
						 //格式化日期
				        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					
				        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd HH:mm:ss')");
						entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
						
					}
					
					
				
				
			}else if(key.indexOf("当前日期")!=-1){
				/**
				 * 如果类型 = 当前日期 
                1.默认 "yyyy-mm-dd"
                 2.如果含有冒号 则根据冒号进行解析 例如[系统当前时间:yyyy-mm]
				 */
				if( column.indexOf(":")!=-1){
					
					String str1=column.substring(0, column.indexOf(":"));
					String str2=column.substring(str1.length()+1, column.length());
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat(str2);
//					result = result.replace(key, " TO_DATE('" + df.format(date) + "','"+str2+"')");
//					entityMap.put("", uuid);
			        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
					entityMap.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}else{
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				/*	result = result.replace(key, " TO_DATE('" + df.format(date) + "','yyyy-MM-dd')");
					entityMap.put("", uuid);*/
			        splSql[i] =splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd')");
					entityMap.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}
				
			
			}else{
				if(tabDateMap.get(column)!=null&&tabDateMap.get(column).equals("DATE")&&entityMap.get(column)!=null&&entityMap.get(column)!=""){
				
					splSql[i] = splSql[i].replace(key, " TO_DATE(" +key + ",'yyyy-MM-dd')");
					//entityMap.put(column, " TO_DATE(" +key + ",'yyyy-MM-dd')");
				}
				
				
			}
	
			
		}
		if(i<splSql.length-1){
			sql.append(splSql[i]+";");
		}else{
			sql.append(splSql[i]);
		}
		
		}
	}else{

		//获取代理主键uuid
		String uuid=UUIDLong.absStringUUID();
		
	//(@)(.*?),
	String value=null;
	//获取数据列
	HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(entityMap);
	
	List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
	
	list=toListMap( hrTableStruc1.getTab_col());
	
	Map<String, Map<String, Object>> tabMap= new HashMap<String, Map<String, Object>>();
	Map<String, Object> tabDateMap= new HashMap<String,Object>();
	//获取数据列的数据格式
	for (Map<String, Object> map2 : list) {
		
		if(map2.get("value_mode_code")!=null&&map2.get("value_mode_code").toString().equals("03")){
			Map<String, Object> colMap=new HashMap<String, Object>();
			colMap.put("field_tab_code", map2.get("field_tab_code"));
			colMap.put("field_tab_name", map2.get("field_tab_name"));
			colMap.put("col_code", map2.get("col_code"));
			colMap.put("col_name", map2.get("col_name"));
			colMap.put(map2.get("col_name").toString(), map2.get("data_type_code"));
			tabMap.put(map2.get("field_tab_name").toString(),colMap);
		}else{
			tabDateMap.put(map2.get("col_code").toString(),map2.get("data_type_code"));
			
		}
	
	}
	Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//正则查找标识符变量
	while (matcher.find()) {
		
		String key = matcher.group() ;//获取匹配到的参数
		
		String column = key.replaceAll("\\[", "").replaceAll("\\]","");
		
		if(key.indexOf("五笔码")!=-1){
			String str1=column.substring(0, column.indexOf(":"));
			String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
			value=StringTool.toWuBi(entityMap.get(str2).toString());
	
			result = result.replace(key, "[" + tabMap.get("五笔码").get("col_code").toString().toLowerCase() + "]");
			entityMap.put(tabMap.get("五笔码").get("col_code").toString().toLowerCase(), value);
			
		}else if(key.indexOf("拼音码")!=-1){
			String str1=column.substring(0, column.indexOf(":"));
			String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
			value=StringTool.toPinyinShouZiMu(entityMap.get(str2).toString());
			result = result.replace(key,"[" + tabMap.get("拼音码").get("col_code").toString().toLowerCase() + "]");
			entityMap.put(tabMap.get("拼音码").get("col_code").toString().toLowerCase(), value);		
			}else if(key.indexOf("代理主键")!=-1){
		/*	String str1=column.substring(0, column.indexOf(":"));
			String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
			*/
			//result = result.replace(key, uuid);
				result = result.replace(key,"[" + tabMap.get("代理主键").get("col_code").toString().toLowerCase()+ "]");

			entityMap.put(tabMap.get("代理主键").get("col_code").toString().toLowerCase(), uuid);	
		}else if(key.indexOf("当前时间")!=-1){
		
		
				/**
				 * 如果类型 = 当前时间
                      1.默认 "yyyy-mm-dd 09:39:39.123456" 小数点后面6位
                      2.如果还有冒号 根据冒号进行解析 [系统当前时间:yyyy-mm-dd 09:39:39.123456]
				 */
				//yyyy-MM-dd HH:mm:ss
				if( column.indexOf(":")!=-1){
					String str1=column.substring(0, column.indexOf(":"));
					String str2=column.substring(str1.length()+1, column.length());
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat(str2);
					/*result = result.replace(key, " TO_DATE('" + + "','"+str2+"')");
					entityMap.put("", uuid);*/
			        result = result.replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
					entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}else{
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				
			        result = result.replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd HH:mm:ss')");
					entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}
				
				
			
			
		}else if(key.indexOf("当前日期")!=-1){
			/**
			 * 如果类型 = 当前日期 
            1.默认 "yyyy-mm-dd"
             2.如果含有冒号 则根据冒号进行解析 例如[系统当前时间:yyyy-mm]
			 */
			if( column.indexOf(":")!=-1){
				
				String str1=column.substring(0, column.indexOf(":"));
				String str2=column.substring(str1.length()+1, column.length());
				 //格式化日期
		        SimpleDateFormat df = new SimpleDateFormat(str2);
//				result = result.replace(key, " TO_DATE('" + df.format(date) + "','"+str2+"')");
//				entityMap.put("", uuid);
		        result = result.replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
				entityMap.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
				
			}else{
				 //格式化日期
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			/*	result = result.replace(key, " TO_DATE('" + df.format(date) + "','yyyy-MM-dd')");
				entityMap.put("", uuid);*/
		        result =result.replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd')");
				entityMap.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
				
			}
			
		
		}else{
			if(tabDateMap.get(column)!=null&&tabDateMap.get(column).equals("DATE")&&entityMap.get(column)!=null&&entityMap.get(column)!=""){
			
				result = result.replace(key, " TO_DATE(" +key + ",'yyyy-MM-dd')");
				//entityMap.put(column, " TO_DATE(" +key + ",'yyyy-MM-dd')");
			}
			
			
		}

		
	}
	
		sql.append(result);
	
	
	
	}
		entityMap.put("add_Sql", sql.toString());
		return entityMap;
	}
public static List<Map<String, Object>> toListMap(String json){
    List<Object> list =JSON.parseArray(json);
     
    List< Map<String,Object>> listw = new ArrayList<Map<String,Object>>();
    for (Object object : list){
    Map<String,Object> ageMap = new HashMap<String,Object>();
    Map <String,Object> ret = (Map<String, Object>) object;//取出list里面的值转为map
    /*for (Entry<String, Object> entry : ret.entrySet()) { 
        ageMap.put(entry.getKey());
        System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue()); 
        listw.add(ageMap);  //添加到list集合  成为 list<map<String,Object>> 集合
    }  */
    listw.add(ret);
    }
    return listw;
     
}

}
