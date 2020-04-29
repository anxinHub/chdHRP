package com.chd.hrp.hr.serviceImpl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.chd.base.SessionManager;
import com.chd.base.exception.SysException;
import com.chd.base.util.ReadFiles;
import com.chd.base.util.StringTool;
import com.chd.base.util.UUIDLong;
import com.chd.hrp.hr.dao.ImportMapper;
import com.chd.hrp.hr.dao.QueryMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.ImportService;

@Service("importService")
public class ImportServiceImpl implements ImportService {
	private static Logger logger = Logger.getLogger(ImportServiceImpl.class);

	@Resource(name = "importMapper")
	private final ImportMapper importMapper = null;
	
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

	@SuppressWarnings("unchecked")
	@Override
	public String importData(Map<String, Object> entityMap) throws DataAccessException{
		
		int successNum = 0;
		
		int countNum=2;
		List<String> dataList= new ArrayList<String>();

		List<Map<String,Object>> saveList = new ArrayList<Map<String,Object>>();
		//判断是否有重复数据
		Map<String, Object> errMap = new HashMap<String, Object>();			
		
			try {
				List<Map<String, Object>> listSql=new ArrayList<Map<String,Object>>();
				
				List<Map<String, List<String>>> list = ReadFiles.readFiles(entityMap);
				if(list==null || list.size()==0){
					return "{\"error\":\"没有数据！\",\"state\":\"false\"}";
				}
				//查询表及列信息
				HrTableStruc hrTableStruc = hrTableStrucMapper.queryTabColsByCode(entityMap);
				/**
				 * 查询表构建sql
				 */
				HrTableStruc hrTableStrucSql = hrTableStrucMapper.queryTabSqlsByCode(entityMap);
				 String sqlByCode = new String() ; /*new String(actrc.getTemplate_sql(),"UTF-8")*/;
					
					
				 sqlByCode=" SELECT * FROM "+entityMap.get("tab_code")+" WHERE group_id = [group_id] and hos_id = [hos_id]  ";
					
			if(list != null && list.size() > 0){
				for (Map<String, List<String>> map : list) {
					//主键Map
					Map<String, Object> keyMap= new  HashMap<String, Object>();
					//存储Map
					  Map<String, Object> commitMap= new HashMap<String, Object>();
					  
					  StringBuffer isPk = new StringBuffer();//验证重复数据
					  //查询表及列信息不为空
					if(hrTableStruc != null && hrTableStruc.getTab_col() != null && !"".equals(hrTableStruc.getTab_col())){
						
						List<Map> data = JSONArray.parseArray(hrTableStruc.getTab_col(), Map.class);
						
					   for (Map map2 : data) {
						 
						   Map<String, Object> byCodeMap= new HashMap<String, Object>();
						   
						   byCodeMap.put("group_id", SessionManager.getGroupId());
						   
						   byCodeMap.put("hos_id", SessionManager.getHosId());
						   //添加主键，为判断是否有重复数据
						   if(map2.get("is_pk")!=null&&!map2.get("value_mode_code").toString().equals("03")){
						   
							if(map2.get("is_pk").toString().equals("1")&&!map2.get("col_code").toString().toLowerCase().equals("hos_id")&&!map2.get("col_code").toString().toLowerCase().equals("group_id")){
								
								byCodeMap.put(map2.get("col_code").toString().toLowerCase(), map.get(map2.get("col_code").toString().toLowerCase()).get(1));
							
								sqlByCode+=" and "+map2.get("col_code").toString().toLowerCase()+" = [" +map2.get("col_code").toString().toLowerCase()+"]";

								keyMap.put(map2.get("col_code").toString().toLowerCase(), map.get(map2.get("col_code").toString().toLowerCase()).get(1));
								//过滤空行
								if (map.get(map2.get("col_code").toString().toLowerCase()).get(1)== null ) {
									continue;
								}
								
						/*	   Map<String, Object>	isExistence=queryMapper.queryByCode(byCodeMap);
							   if(isExistence!=null){
									return "{\"error\":\"导入失败！" + map2.get("col_name")+"已存在相同的记录\",\"state\":\"false\"}";

								   
							   }*/
							}
						   }
						 //过滤空数据
							
						   if(!map2.get("col_code").toString().toLowerCase().equals("dept_no")){
						   //相关代码表数据是否存在
						   if(map2.get("value_mode_code").toString().equals("02")&&map.get(map2.get("col_code").toString().toLowerCase()).get(1)!= null ){
							   
							   entityMap.put("field_tab_code", map2.get("field_tab_code"));
							   
								HrFiledTabStruc		hrFiledTabStruc=hrFiledTabStrucMapper.queryByCode(entityMap);
								
								if(hrFiledTabStruc!=null){
									//代码表引用的sql
									String sql= hrFiledTabStruc.getRelated_sql();
									   Map<String, Object> sqlMapByCode = new HashMap<String, Object>();
								
										//外部引用的
										sqlMapByCode= matchAndReplaceSql(sql,entityMap);//匹配替换
									
									entityMap.put("sql", sqlMapByCode.get("result"));
									
									//取出相关代码表数据
									List<Map<String, Object>> fieldTabList  = (List<Map<String, Object>>) queryMapper.query(entityMap);
									//为了提示错误信息
									if(fieldTabList==null){
							
										
										return "{\"error\":\"导入失败！" + map2.get("col_name")+"相关代码不存在\",\"state\":\"false\"}";

									}
									//判断相关代码是否存在
									Map<String, Object> filedTabMap= new HashMap<String, Object>();
									  Map<String, Map<String, Object>> selectMap = new HashMap<String, Map<String, Object>>();
									// 查询所有下拉框编码及名称
									Map<String, Object> Data = new HashMap<String, Object>();
									for (Map<String, Object> map3 : fieldTabList) {
									
										    if(map2.get("col_code").toString().toLowerCase().toString().equals("dept_id")){
										    	Data.put(map3.get("ID").toString().split("@")[0], map3.get("ID"));
										    }else{
											Data.put(map3.get("ID").toString(), map3.get("ID"));}
											Data.put(map3.get("TEXT").toString(), map3.get("ID"));
											selectMap.put(map2.get("col_code").toString().toLowerCase(), Data);
											
										}
										 if(map2.get("col_code").toString().toLowerCase().toString().equals("dept_id")){
											 //selectMap.get(map2.get("col_code").toString().toLowerCase().toString()).get(map.get(map2.get("col_code").toString().toLowerCase().toString()).get(1))	
												if (selectMap.get(map2.get("col_code").toString().toLowerCase().toString()).toString().split("@").length < 2) {
													
													return "{\"error\":\"导入失败！请在代码构建功能中维护部门ID与变更ID数据,格式:【dept_id||'@'||dept_no as field_col_code 】\",\"state\":\"false\"}";
												
												}else{
														 commitMap.put(map2.get("col_code").toString().toLowerCase(), selectMap.get(map2.get("col_code").toString().toLowerCase()).get(map.get(map2.get("col_code").toString().toLowerCase().toString()).get(1)).toString().split("@")[0]);  
											        	   commitMap.put("dept_no", selectMap.get(map2.get("col_code").toString().toLowerCase()).get(map.get(map2.get("col_code").toString().toLowerCase().toString()).get(1)).toString().split("@")[1]);  

										        	  
												}
												
												
							           }else{
							        	
							        	   if(!map2.get("col_code").toString().toLowerCase().equals("dept_no")&&StringUtils.isNotBlank(map.get(map2.get("col_code").toString().toLowerCase()).get(1))){
									        	   commitMap.put(map2.get("col_code").toString().toLowerCase(),selectMap.get(map2.get("col_code").toString().toLowerCase().toString()).get(map.get(map2.get("col_code").toString().toLowerCase().toString()).get(1)));  
	 
							        	   }
							        	   
							           }
												
									}
									
								}
					   }
						   
							if(!map2.get("value_mode_code").toString().equals("02")&&!map2.get("value_mode_code").toString().equals("03")&&!map2.get("col_code").toString().toLowerCase().equals("dept_no")){
								 if (map.get(map2.get("col_code").toString().toLowerCase()).get(1)== null ) {
										continue;
									}
								if(map2.get("data_type_code").toString().equals("DATE")){
									 DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
					    			 boolean flag = false ;
					    			 String reg = "[\\d]{4}[-][\\d]{2}-[\\d]{2}";
					    			 String dateStr=null;
					    			
					    			 
					    			 if(map.get(map2.get("col_code").toString().toLowerCase()).get(1) .toString().indexOf("OADate")==1){
					    				 Date date=	fromDoubleToDateTime(Double.parseDouble(map.get(map2.get("col_code").toString().toLowerCase()).get(1) .toString().replace("OADate", "").replace("()", "").replace("/", "").replace("(", "").replace(")", "")));
							    			
					    				 
						    			 dateStr = df.format(date);
										
										flag = dateStr.matches(reg);
					    			 }else if (map.get(map2.get("col_code").toString().toLowerCase()).get(1).length()==8){
					    				//处理20190101类型
					    				 String reg1 = "[\\d]{4}[\\d]{2}[\\d]{2}";
					    				 flag = map.get(map2.get("col_code").toString().toLowerCase()).get(1).toString().matches(reg1);
					    				 dateStr=map.get(map2.get("col_code").toString().toLowerCase()).get(1).toString();
					    			 }
					    			 else{
					    				 //处理文本格式2019-01-01
					    				 flag = map.get(map2.get("col_code").toString().toLowerCase()).get(1).toString().matches(reg);
					    				 dateStr=map.get(map2.get("col_code").toString().toLowerCase()).get(1).toString();
					    			 }
					    			 if(flag == false){//判断日期格式是否正确
					    					return "{\"error\":\"导入失败！第"+countNum+"行"+"日期格式错误 ";
											//return "{\"error\":\"导入失败！请在代码构建功能中维护部门ID与变更ID数据,格式:【dept_id||'@'||dept_no as field_col_code 】\",\"state\":\"false\"}";

										}else{
											// 处理日期类型
											commitMap.put(map2.get("col_code").toString().toLowerCase(),  dateStr );
								}
					    			 
								}else{
									commitMap.put(map2.get("col_code").toString().toLowerCase(),  map.get(map2.get("col_code").toString().toLowerCase()).get(1));
	
								}
									
							}
							
							   
							
					}
					   commitMap.put("group_id", SessionManager.getGroupId());
						commitMap.put("hos_id", SessionManager.getHosId());
							commitMap.put("user_id", SessionManager.getUserId());
							commitMap.put("user_code", SessionManager.getUserCode());
					   Map<String, Object> sqlMapByCode = matchAndReplaceSql(sqlByCode,commitMap);//匹配替换
						
						entityMap.put("sql",sqlMapByCode.get("result"));
							   Map<String, Object>	isExistence=queryMapper.queryByCode(entityMap);
					   if(isExistence!=null){
							return "{\"error\":\"导入失败！已存在相同的记录\",\"state\":\"false\"}";

						   
					   }
					
						commitMap.put("tab_code", entityMap.get("tab_code"));
					    
					  
						for (Entry<String, Object> entry : keyMap.entrySet()) {
							
							if(entry.getValue().toString()!=null&&!entry.getValue().toString().equals("")){
								isPk.append(entry.getValue().toString());
							
							}
							
						}
						
						
						if (errMap.containsKey(isPk.toString())) {
							
							return "{\"error\":\"导入失败！第" +countNum+ "行数据重复\",\"state\":\"false\"}";
						}
						
						errMap.put(isPk.toString(), countNum);
						
						saveList.add(commitMap);
						countNum++;
						
					}
					
				}}
			/**
			 * 从数据表构建取数据表结构
			 */
			if(hrTableStrucSql != null && hrTableStrucSql.getTab_sql() != null && !"".equals(hrTableStrucSql.getTab_sql())){
				/**
				 * 转换成list方便提取sql
				 */
				listSql=toListMap( hrTableStrucSql.getTab_sql());
			}
				
				String sql = new String() ;
				
				for (Map<String, Object> mapSql : listSql) {
					/**
					 * 提取sql
					 */
					String sql_code=mapSql.get("sql_code").toString();
					/**
					 * 根据add_tab_code来匹配sql
					 */
					if(mapSql.get("sql_code").toString().equals(entityMap.get("design_code"))){
						
						sql=mapSql.get("sql_statement").toString();
					}
				}
					
			       
					String add_sql = sql;
				
					//获取数据列
					HrTableStruc hrTableStruc1 = hrTableStrucMapper.queryTabColsByCode(entityMap);
					//数据列中文替换成英文例如编码替换成code
					
					add_sql=matchAndReplaceCol(add_sql);
					
					
					for (Map map2 : saveList) {
						/**
						 *  替换特殊字符 example 五笔码 拼音码 代理主键  时间戳等
						 */
						/**
						 *  替换特殊字符 example 五笔码 拼音码 代理主键  时间戳等
						 */
						Map<String, Object> sqlMap=new HashMap<String, Object>();
						sqlMap=matchAndReplace(add_sql,map2);
						
						//替换参数
						sqlMap = matchAndReplaceSql(sqlMap.get("add_Sql").toString(),sqlMap);//匹配替换
						
						if(sqlMap.get("result").equals("-2")){
							
							return "{\"error\":\"导入失败！" + sqlMap.get("value")+"字段长度太长\",\"state\":\"false\"}";
						
						}
						
						if(!sqlMap.get("result").equals("-1")){
							
							map2.put("sql",sqlMap.get("result"));
							/**
							 * 减少后台请求,批量提交
							 */
							dataList.add(sqlMap.get("result").toString());
							successNum++;
							
						}else{
							
							return "{\"error\":\"导入失败.\"state\":\"false\"}";
							
						}
					}
					//通用新增sql	
					if (dataList.size() > 0 ) {
								
								List<String> stringSqlList= new  ArrayList<String>();
								
								for (int i=0;  i<  dataList.size(); i++) {
									stringSqlList.add(dataList.get(i));
									/**
									 * 500条提交一次
									 */
									if( i>0 && i==500 || i == dataList.size() - 1) {
										importMapper.addBatch(stringSqlList);
										stringSqlList.clear();
									}
				                     
								}
								
							}
					return "{\"msg\":\"导入成功.成功条数:" + successNum+ "\",\"state\":\"true\"}";

			} catch (Exception e) {
				logger.error(e.getMessage());
				return "{\"error\":\"导入失败！\"}";
			}
	}
	public Map<String, Object>  matchAndReplaceSql(String sql,Map<String,Object> paraMap){
		Map<String, Object>  map=new HashMap<String, Object>();
		String result = sql;
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
	/**
	 * 替换特殊字符 example 五笔码 拼音码 代理主键  时间戳等
	 * [系统时间戳]
            如果类型 = 当前日期 
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
		
		for(String key: entityMap.keySet()){
			map.put(key, entityMap.get(key));
			
		}
		
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
				map.put(tabMap.get("五笔码").get("col_code").toString().toLowerCase(), value);
				
			}else if(key.indexOf("拼音码")!=-1){
				String str1=column.substring(0, column.indexOf(":"));
				String str2=column.substring(str1.length()+1, column.length()).toLowerCase().replaceAll("\n|\t|\r|\\s{1,}", "");
				value=StringTool.toPinyinShouZiMu(entityMap.get(str2).toString());
				splSql[i] = splSql[i].replace(key,"[" + tabMap.get("拼音码").get("col_code").toString().toLowerCase());
				map.put(tabMap.get("拼音码").get("col_code").toString().toLowerCase(), value);		
				}else if(key.indexOf("代理主键")!=-1){
					splSql[i] = splSql[i].replace(key,"[" + tabMap.get("代理主键").get("col_code").toString().toLowerCase()+ "]");

					map.put(tabMap.get("代理主键").get("col_code").toString().toLowerCase(), uuid);	
			}else if(key.indexOf("序列主键")!=-1){
				//处理序列主键nextval
	             value=tab_code+"_seq.nextval";
	             Map<String, Object>  nextMap=new HashMap<String, Object>();
	             nextMap.put("nextval", value);
					int id = queryMapper.querySeqByTabCode(nextMap);

	             splSql[i] = splSql[i].replace(key, String.valueOf(id));
	             map.put(tabMap.get("序列主键").get("col_code").toString().toLowerCase(), id);	

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
				        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
				        map.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
						
					}else{
						 //格式化日期
				        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh24:mi:ss");
					
				        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd hh24:mi:ss')");
				        map.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
						
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
//					result = result.replace(key, " TO_DATE('" + df.format(date) + "','"+str2+"')");
//					entityMap.put("", uuid);
			        splSql[i] = splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'"+str2+"')");
			        map.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}else{
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
				/*	result = result.replace(key, " TO_DATE('" + df.format(date) + "','yyyy-MM-dd')");
					entityMap.put("", uuid);*/
			        splSql[i] =splSql[i].replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd')");
			        map.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}
				
			
			}else{
				if(tabDateMap.get(column)!=null&&tabDateMap.get(column).equals("DATE")&&entityMap.get(column.toLowerCase())!=null&&entityMap.get(column.toLowerCase())!=""){
				
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
			map.put(tabMap.get("五笔码").get("col_code").toString().toLowerCase(), value);
			
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

				map.put(tabMap.get("代理主键").get("col_code").toString().toLowerCase(), uuid);	
		}else if(key.indexOf("序列主键")!=-1){
			//处理序列主键nextval
            value=entityMap.get("tab_code")+"_seq.nextval";
			
			result = result.replace(key, value);
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
			        map.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
					
				}else{
					 //格式化日期
			        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh24:mi:ss");
				
			        result = result.replace(key," TO_DATE(" + "[" + tabMap.get("当前时间").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd hh24:mi:ss')");
			        map.put(tabMap.get("当前时间").get("col_code").toString().toLowerCase(),  df.format(date));
					
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
		        map.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
				
			}else{
				 //格式化日期
		        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			/*	result = result.replace(key, " TO_DATE('" + df.format(date) + "','yyyy-MM-dd')");
				entityMap.put("", uuid);*/
		        result =result.replace(key," TO_DATE(" + "[" + tabMap.get("当前日期").get("col_code").toString().toLowerCase() + "]"+ ",'yyyy-MM-dd')");
		        map.put(tabMap.get("当前日期").get("col_code").toString().toLowerCase(),  df.format(date));
				
			}
			
		
		}else{
			if(tabDateMap.get(column)!=null&&tabDateMap.get(column).equals("DATE")&&entityMap.get(column.toLowerCase())!=null&&entityMap.get(column.toLowerCase())!=""){
			
				result = result.replace(key, " TO_DATE(" +key + ",'yyyy-MM-dd')");

			

			
			}
			
			
		}

		
	}
	
		sql.append(result);
	
	
	
	}
		map.put("add_Sql", sql.toString());
		return map;
	}

	
private String replaceConstant(String replaceStr,Map<String, Object> entityMap){
		
		replaceStr = replaceStr.replaceAll("@group_id", entityMap.get("group_id").toString());
		
		replaceStr = replaceStr.replaceAll("@hos_id", entityMap.get("hos_id").toString());
		
		replaceStr = replaceStr.replaceAll("@copy_code", entityMap.get("copy_code").toString());
		
		replaceStr = replaceStr.replaceAll("@tab_code", '\''+entityMap.get("field_tab_code").toString()+'\'');
		
		return replaceStr;
		
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
