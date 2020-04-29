package com.chd.hrp.hr.serviceImpl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hr.dao.InsertMapper;
import com.chd.hrp.hr.dao.QueryMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.InsertService;

@Service("insertService")
public class InsertServiceImpl implements InsertService {
	private static Logger logger = Logger.getLogger(InsertServiceImpl.class);

	@Resource(name = "insertMapper")
	private final InsertMapper insertMapper = null;
	
	@Resource(name = "queryMapper")
	private final QueryMapper queryMapper = null;
	// 引入DAO操作
	@Resource(name = "hrTableStrucMapper")
	private final HrTableStrucMapper hrTableStrucMapper = null;
	
	@Resource(name = "hrSelectMapper")
	private final HrSelectMapper hrSelectMapper = null;
	
	//引入DAO操作
	@Resource(name = "hrFiledTabStrucMapper")
	private final HrFiledTabStrucMapper hrFiledTabStrucMapper = null;

	@Override
	public String addBaseInfo(Map<String, Object> entityMap)
			throws DataAccessException {
		try {
			
			  List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				
				
				StringBuilder sb = new StringBuilder();
				//查询是否有数据表构建
				HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabSqlsByCode(entityMap);
				//queryTabColsByCode
				if(hrTableStruc != null && hrTableStruc.getTab_sql() != null && !"".equals(hrTableStruc.getTab_sql())){
					//转换成list<Map>
					list=toListMap( hrTableStruc.getTab_sql());
				
				}
			/**
			 * 解析整行保存的表格数据
			 */
			if(entityMap.containsKey("ParamVo")){
				List<String> dataList= new ArrayList<String>();
				List<Map> listVo = JSONArray.parseArray(entityMap.get("ParamVo").toString(), Map.class);
				/**
				 * 遍历所有数据
				 */
				for (Map map : listVo) {
					
					Map<String, Object> saveMap= new HashMap<String, Object>();
					/**
					 * 遍历存入map中
					 */
					for(Object key : map.keySet()){
		        		   Object value = map.get(key);
		        		   saveMap.put(key.toString(), value);
				}
					
					for (Object key  : entityMap.keySet()) {
						 Object value = entityMap.get(key);
		        		   saveMap.put(key.toString(), value);
					}
					
				
					String sql = new String() ; /*new String(actrc.getTemplate_sql(),"UTF-8")*/;
					
					
					for (Map<String, Object> sqlmap : list) {
						//如果sql_code匹配add方法取出sql
						if(sqlmap.get("sql_code").toString().equals(saveMap.get("design_code"))){
							
							sql=sqlmap.get("sql_statement").toString();
							
						}
					}
				       
						String add_sql = sql;
						//获取数据列
						HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(entityMap);
						
						//数据列中文替换成英文例如编码替换成code
						add_sql=matchAndReplaceCol(add_sql);
						
						/**
						 *  替换特殊字符 example 五笔码 拼音码 代理主键  时间戳等
						 */
						Map<String, Object> sqlMap=new HashMap<String, Object>();
						sqlMap=matchAndReplace(add_sql,saveMap);
						
						//替换参数
						sqlMap = matchAndReplaceSql(sqlMap.get("add_Sql").toString(),sqlMap);//匹配替换
						
						if(sqlMap.get("result").equals("-2")){
							
							return "{\"error\":\"添加失败,"+sqlMap.get("value")+"长度太长\",\"state\":\"true\"}";
							
						}
						
						if(!sqlMap.get("result").equals("-1")){
							/**
							 * 减少后台请求次数
							 */
							dataList.add(sqlMap.get("result").toString());
							
						
							
						}else{
							
							return "{\"error\":\"添加失败.\"state\":\"true\"}";
						}
					
					
				}
				
				//通用新增sql	
				if (dataList.size() > 0 ) {
							
							List<String> stringSqlList= new  ArrayList<String>();
							
							for (int i=0;  i<  dataList.size(); i++) {
								stringSqlList.add(dataList.get(i));
								/**
								 * 每500条提交一次减少请求
								 */
								if( i>0 && i==500 || i == dataList.size() - 1) {
									insertMapper.addBatch(stringSqlList);
									stringSqlList.clear();
								}
			                     
							}
							return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
						}
			}
	    
		//单条添加
			String sql = new String() ; /*new String(actrc.getTemplate_sql(),"UTF-8")*/;
			for (Map<String, Object> map : list) {
				//如果sql_code匹配add方法取出sql
				if(map.get("sql_code").toString().equals(entityMap.get("design_code"))){
					
					sql=map.get("sql_statement").toString();
					
				}
			}
				
		       
				String add_sql = sql;
				//获取数据列
				HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(entityMap);
				
				//数据列中文替换成英文例如编码替换成code
				add_sql=matchAndReplaceCol(add_sql);
				Map<String, Object> sqlMap=new HashMap<String, Object>();
				/**
				 *  替换特殊字符 example 五笔码 拼音码 代理主键  时间戳等
				 */
				sqlMap=matchAndReplace(add_sql,entityMap);
	
				//替换参数
				sqlMap = matchAndReplaceSql(entityMap.get("add_Sql").toString(),entityMap);//匹配替换
				
				if(sqlMap.get("result").equals("-2")){
					
					return "{\"error\":\"添加失败,"+sqlMap.get("value")+"长度太长\",\"state\":\"true\"}";
					
				}
				
				if(!sqlMap.get("result").equals("-1")){
					
					entityMap.put("sql",sqlMap.get("result"));
					
					insertMapper.add(entityMap);
					if(entityMap.get("tab_code").toString().equals("HOS_EMP")){
						return "{\"emp_id\":\"" + entityMap.get("emp_id") + "\",\"msg\":\"保存成功.\",\"state\":\"true\"}";
					}else{
					return "{\"msg\":\"添加成功.\",\"state\":\"true\"}";
					}
				}else{
					
					return "{\"error\":\"添加失败.\"state\":\"true\"}";
				}
			
			
			
			
			
			
		} catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败 \"}");
		}
	}


	

    /**
     * 替换code为值
     * @param sql
     * @param paraMap
     * @return
     */
	public Map<String, Object>  matchAndReplaceSql(String sql,Map<String,Object> paraMap){
		Map<String, Object>  map=new HashMap<String, Object>();
		String result = sql.toLowerCase()         					//将所有字符都转成小写 为分割处理做准备
			   	   .replaceAll("\n|\t|\r|\\s{1,}", " ");	//去掉所有换行符 制表符 多个空格转换为单个空格  
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		
		//获取数据列
		HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(paraMap);
		
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		
		list=toListMap( hrTableStruc1.getTab_col());
		
		Map<String, Object> tabMap= new HashMap<String, Object>();
		for (Map<String, Object> map2 : list) {
			tabMap.put(map2.get("col_code").toString(), map2.get("filed_length"));
		}
		//filed_length
		//(@)(.*?),
		String value=null;
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(paraMap.get(column.toLowerCase()) == null){
				value="";
			}else{
			
			 value =paraMap.get(column.toLowerCase()).toString();
			}
			//获取长度
			int length=getStringLength(value,getEncoding(value));
			if(tabMap.get(key)!=null){
				int dataLength=Integer.parseInt(tabMap.get(column).toString());
			
					if(length-dataLength>0){
						result = "-2";
						map.put("value", value);
						break;
						
						
			}
			
			
			}
			
			result = result.replace(key, "'" + value + "'");
			
		}
		map.put("result", result);
		
		return map;
	}
	/**
	 * 替换汉字为英文
	 * @param sql
	 * @param hrTableStruc1
	 * @return
	 */
	public String  matchAndReplaceCol(String sql){
		Map<String, Object>  map=new HashMap<String, Object>();
		StringBuffer sqlSql =  new StringBuffer();
		String result = sql;
		
		if(result.indexOf(";")!=-1){
			//Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
			String[] splSql = result.split(";");
			
			for(int i = 0;i < splSql.length;i++) {
				//截取表名
				String tab_code=splSql[i].substring(splSql[i].lastIndexOf("INTO")+4,splSql[i].indexOf("(")).replaceAll("\n|\t|\r|\\s{1,}", " ").replaceAll(" ", "");
				//String[] splSql=str.split("(AND |OR  )");
				Map<String, Object> keyMap=new HashMap<String, Object>();
				keyMap.put("group_id", SessionManager.getGroupId());
				keyMap.put("hos_id", SessionManager.getHosId());
				keyMap.put("copy_code", SessionManager.getCopyCode());
				keyMap.put("tab_code", tab_code);
			String value=null;
			//获取数据列
			HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(keyMap);
			
			List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
			
			list=toListMap( hrTableStruc1.getTab_col());
	
		Map<String, Object> tabMap= new HashMap<String, Object>();
		for (Map<String, Object> map2 : list) {
			tabMap.put(map2.get("col_name").toString(), map2.get("col_code"));
		}
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(splSql[i]);//#\\{.*?\\}
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			 //   特殊字符 example 五笔码 拼音码 代理主键  时间戳等单独处理                                                                                
			if(tabMap.get(column) == null||key.indexOf("代理主键")!=-1||key.indexOf("五笔码")!=-1||key.indexOf("拼音码 ")!=-1||key.indexOf("系统时间戳")!=-1){
				value=key;
			}else{
			
			 value = "["+String.valueOf(tabMap.get(column))+"]";
			}
	
			splSql[i] = splSql[i].replace(key, value);
			
		}
		if(i<splSql.length-1) {
			sqlSql.append(splSql[i]+";");
		}else {
		sqlSql.append(splSql[i]);}
			}
			}else{

				//截取表名
				String tab_code=result.substring(result.lastIndexOf("INTO")+4,result.indexOf("(")).replaceAll("\n|\t|\r|\\s{1,}", " ").replaceAll(" ", "");
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
			
			list=toListMap( hrTableStruc1.getTab_col());
	
		Map<String, Object> tabMap= new HashMap<String, Object>();
		for (Map<String, Object> map2 : list) {
			tabMap.put(map2.get("col_name").toString(), map2.get("col_code"));
		}
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			 //   特殊字符 example 五笔码 拼音码 代理主键  时间戳等单独处理                                                                                
			if(tabMap.get(column) == null||key.indexOf("代理主键")!=-1||key.indexOf("五笔码")!=-1||key.indexOf("拼音码 ")!=-1||key.indexOf("系统时间戳")!=-1){
				value=key;
			}else{
			
			 value = "["+String.valueOf(tabMap.get(column))+"]";
			}
	
			result = result.replace(key, value);
			
		}
		sqlSql.append(result);
				
			}
		return sqlSql.toString();
	}
	/**
	 * 替换特殊字符 example 五笔码 拼音码 代理主键  时间戳等
	 * [系统时间戳]
            如果类型 = 日期型 
           1.默认 "yyyy-mm-dd"
     2.如果含有冒号 则根据冒号进行解析 例如[系统时间戳:yyyy-mm]
            如果类型 = 时间戳
           1.默认 "yyyy-mm-dd 09:39:39.123456" 小数点后面6位
      2.如果还有冒号 根据冒号进行解析 [系统时间戳:yyyy-mm-dd 09:39:39.123456]


         [拼音码]  = [拼音码:科室名称/dept_name]
         [五笔码]  = [笔码:科室名称/dept_name]
         [代理主键]  
                 默认值 取15位UUID
               如果含有冒号 则代表当前是第二条SQL 去第一条SQL已经生成的主键值 例如 [代理主键:dept_id] 
        [序列主键] 暂不处理
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
		//有变更表
		if(result.indexOf(";")!=-1){
		//Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		String[] splSql = result.split(";");
		
		for(int i = 0;i < splSql.length;i++) {
			//截取表名
			String tab_code=splSql[i].substring(splSql[i].lastIndexOf("INTO")+4,splSql[i].indexOf("(")).replaceAll("\n|\t|\r|\\s{1,}", " ").replaceAll(" ", "");
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
		
				splSql[i] = splSql[i].replace(key, "[" + tabMap.get("五笔码").get("col_code").toString().toLowerCase());
				entityMap.put(tabMap.get("五笔码").get("col_code").toString().toLowerCase(), value);
				
			}else if(key.indexOf("拼音码")!=-1){
				String str1=column.substring(0, column.indexOf(":"));
				String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
				value=StringTool.toPinyinShouZiMu(entityMap.get(str2).toString());
				splSql[i] = splSql[i].replace(key,"[" + tabMap.get("拼音码").get("col_code").toString().toLowerCase());
				entityMap.put(tabMap.get("拼音码").get("col_code").toString().toLowerCase(), value);		
				}else if(key.indexOf("代理主键")!=-1){
					splSql[i] = splSql[i].replace(key,"[" + tabMap.get("代理主键").get("col_code").toString().toLowerCase()+ "]");

				entityMap.put(tabMap.get("代理主键").get("col_code").toString().toLowerCase(), uuid);	
			}else if(key.indexOf("序列主键")!=-1){
				//处理序列主键nextval
	             value=tab_code+"_seq.nextval";
	             Map<String, Object>  nextMap=new HashMap<String, Object>();
	             nextMap.put("nextval", value);
					int id = queryMapper.querySeqByTabCode(nextMap);

	             splSql[i] = splSql[i].replace(key, String.valueOf(id));
	             entityMap.put(tabMap.get("序列主键").get("col_code").toString().toLowerCase(), id);	

			}else if(key.indexOf("当前时间")!=-1){
			
			
					/**
					 * 如果类型 = 时间戳
                          1.默认 "yyyy-mm-dd 09:39:39.123456" 小数点后面6位
                          2.如果还有冒号 根据冒号进行解析 [系统时间戳:yyyy-mm-dd 09:39:39.123456]
					 */
					//yyyy-MM-dd hh24:mi:ss
					if( column.indexOf(":")!=-1){
						String str1=column.substring(0, column.indexOf(":"));
						String str2=column.substring(str1.length()+1, column.length());
						 //格式化日期
				        SimpleDateFormat df = new SimpleDateFormat(str2);
				        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
						entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
						
					}else{
						 //格式化日期
				        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
					
				        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd hh24:mi:ss')");
						entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
						
					}
					
					
				
				
			}else if(key.indexOf("当前日期")!=-1){
				/**
				 * 如果类型 = 日期型 
                1.默认 "yyyy-mm-dd"
                 2.如果含有冒号 则根据冒号进行解析 例如[系统时间戳:yyyy-mm]
				 */
				if( column.indexOf(":")!=-1){
					
					String str1=column.substring(0, column.indexOf(":"));
					String str2=column.substring(str1.length()+1, column.length());
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat(str2);
			        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
					entityMap.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}else{
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			
			        splSql[i] =splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd')");
					entityMap.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}
				
			
			}else{
				if(tabDateMap.get(column)!=null&&tabDateMap.get(column).equals("DATE")&&entityMap.get(column.toLowerCase())!=null&&entityMap.get(column.toLowerCase())!=""&&!entityMap.get(column.toLowerCase()).toString().equals("")){
				
					splSql[i] = splSql[i].replace(key, " TO_DATE(" +key + ",'yyyy-MM-dd')");
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
	
			result = result.replace(key, "[" + tabMap.get("五笔码").get("col_code").toString().toLowerCase());
			entityMap.put(tabMap.get("五笔码").get("col_code").toString().toLowerCase(), value);
			
		}else if(key.indexOf("拼音码")!=-1){
			String str1=column.substring(0, column.indexOf(":"));
			String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
			value=StringTool.toPinyinShouZiMu(entityMap.get(str2).toString());
			result = result.replace(key,"[" + tabMap.get("拼音码").get("col_code").toString().toLowerCase());
			entityMap.put(tabMap.get("拼音码").get("col_code").toString().toLowerCase(), value);		
			}else if(key.indexOf("代理主键")!=-1){
		/*	String str1=column.substring(0, column.indexOf(":"));
			String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
			*/
			//result = result.replace(key, uuid);
				result = result.replace(key,"[" + tabMap.get("代理主键").get("col_code").toString().toLowerCase()+ "]");

			entityMap.put(tabMap.get("代理主键").get("col_code").toString().toLowerCase(), uuid);	
		}else if(key.indexOf("序列主键")!=-1){
			//处理序列主键nextval
            value=entityMap.get("tab_code")+"_seq.nextval";
            Map<String, Object>  nextMap=new HashMap<String, Object>();
            nextMap.put("nextval", value);
				int id = queryMapper.querySeqByTabCode(nextMap);

				result = result.replace(key, String.valueOf(id));
            entityMap.put(tabMap.get("序列主键").get("col_code").toString().toLowerCase(), id);	
		}else if(key.indexOf("当前时间")!=-1){
		
		
				/**
				 * 如果类型 = 时间戳
                      1.默认 "yyyy-mm-dd 09:39:39.123456" 小数点后面6位
                      2.如果还有冒号 根据冒号进行解析 [系统时间戳:yyyy-mm-dd 09:39:39.123456]
				 */
				//yyyy-MM-dd hh24:mi:ss
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
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
				
			        result = result.replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd hh24:mi:ss')");
					entityMap.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}
				
				
			
			
		}else if(key.indexOf("当前日期")!=-1){
			/**
			 * 如果类型 = 日期型 
            1.默认 "yyyy-mm-dd"
             2.如果含有冒号 则根据冒号进行解析 例如[系统时间戳:yyyy-mm]
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
			if(tabDateMap.get(column)!=null&&tabDateMap.get(column).equals("DATE")&&entityMap.get(column.toLowerCase())!=null&&entityMap.get(column.toLowerCase())!=""&&!entityMap.get(column.toLowerCase()).toString().equals("")){
			
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
	/**
	* 根据字符编码得到字符串实际占用长度
	*/
	public static int getStringLength(String str,String encoding) throws DataAccessException{
		try{
	 if(isNullOrEmpty(str)){
	        return 0;
         }else{
	   return str.getBytes(encoding).length;
	}} catch (Exception e) {
		
		logger.error(e.getMessage(), e);
		
		throw new SysException("{\"error\":\"操作失败 \"}");
	}
	}

	/**
	* 判断字段是否为空
	* @return true 为空， false 不为空
	*/
	public static boolean isNullOrEmpty(String str){
		
	return null == str || "".equals(str);
	}
	/**
	 * 判断常用字符串格式
	 * @param str
	 * @return
	 */
	public  String getEncoding(String str) { 
		String encode = "GB2312"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GB2312
		String s = encode; 
		return s; //是的话，返回“GB2312“，以下代码同理
		} 
		} catch (Exception exception) { 
		} 
		encode = "ISO-8859-1"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是ISO-8859-1
		String s1 = encode; 
		return s1; 
		} 
		} catch (Exception exception1) { 
		} 
		encode = "UTF-8"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是UTF-8
		String s2 = encode; 
		return s2; 
		} 
		} catch (Exception exception2) { 
		} 
		encode = "GBK"; 
		try { 
		if (str.equals(new String(str.getBytes(encode), encode))) { //判断是不是GBK
		String s3 = encode; 
		return s3; 
		} 
		} catch (Exception exception3) { 
		}encode = "UTF-16";   		
        try {    
      if(str.equals(new String(str.getBytes(), encode))) { 
    	  String s4 = encode; 
  		return s4; 
            }    
        } catch(Exception ex) {} 
		
	encode = "ASCII";    
        try 
	{    
            if(str.equals(new String(str.getBytes(), encode)))
	{    
                return "字符串<< " + str + " >>中仅由数字和英文字母组成，无法识别其编码格式";    
            }    
        } 
	catch(Exception ex) {} 

		return ""; //如果都不是，说明输入的内容不属于常见的编码格式。
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
