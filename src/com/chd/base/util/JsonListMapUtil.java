/** 
* 2014-8-6 
* JsonListMapUtil.java 
* author:pengjin
*/ 
package com.chd.base.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.springframework.dao.DataAccessException;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.chd.base.exception.SysException;
import com.github.pagehelper.Page;

public class JsonListMapUtil {
	
	private static Logger logger = Logger.getLogger(JsonListMapUtil.class);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** 
     * 功能描述:通过传入Map<String,Object>,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param map 列表对象 
     * @return java.lang.String 
     */  
    public static String mapToJson(Map<String,Object> m) {  
        StringBuilder json = new StringBuilder(); 
       
        json.append("{");
		if (m != null && m.size() > 0) {
			
			for (Map.Entry<String, Object> entry : m.entrySet()) {
				json.append("\"").append(entry.getKey()).append("\"");
				json.append(":");
				json.append(objectToJson(entry.getValue()));
				json.append(",");
			}
			json.append("mainNum:" + m.size() + "}");
		} else {
			json.append("mainNum:0}");
		}

		logger.debug(json.toString());
		return json.toString();
    }
	
	
	/** 
     * 功能描述:通过传入List<Map<String,Object>>,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param list 列表对象 
     * @return java.lang.String 
     */  
    public static String listToJson(List<Map<String,Object>> list) {  
        StringBuilder json = new StringBuilder(); 
       
        json.append("{Rows:[");  
        if (list != null && list.size() > 0) {
        	
        	for(Map<String, Object> m : list){
        		if(m!=null && m.size()>0){
	        		json.append("{");
					for (Map.Entry<String, Object> entry : m.entrySet()) {
						json.append("\"").append(entry.getKey()).append("\"");  
		                json.append(":"); 
		                json.append(objectToJson(entry.getValue()));
		                json.append(",");  
					}
					json.setCharAt(json.length() - 1, '}');
					json.append(",");
        		}
			}
           
            json.setCharAt(json.length() - 1, ']'); 
            json.append(",Total:"+list.size()+"}");  
        } else {  
            json.append("],Total:0}");
        }
        logger.debug(json.toString());
        return json.toString();  
    }
    
    /** 
     * 功能描述:通过传入List<Map<String,Object>>,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param list 列表对象 
     * @param totalCount 分页使用，总记录数
     * @param objBean 汇总
     * @return java.lang.String 
     */  
    public static String listToJson(List<Map<String,Object>> list,int totalCount,Map<String,Object> sumMap) {  
        StringBuilder json = new StringBuilder(); 
       
        json.append("{Rows:[");  
        if (list != null && list.size() > 0) {
        	//汇总行
        	if(sumMap!=null && sumMap.size()>0){
        		json.append("{");
        		for (Map.Entry<String, Object> entry : sumMap.entrySet()) {
					json.append("\"").append(entry.getKey()).append("\"");  
	                json.append(":"); 
	                json.append(objectToJson(entry.getValue()));
	                json.append(",");  
				}
        		json.setCharAt(json.length() - 1, '}');
				json.append(",");
        	}
        	
        	for(Map<String, Object> m : list){
        		if(m!=null && m.size()>0){
	        		json.append("{");
					for (Map.Entry<String, Object> entry : m.entrySet()) {
						json.append("\"").append(entry.getKey()).append("\"");  
		                json.append(":"); 
		                json.append(objectToJson(entry.getValue()));
		                json.append(",");  
					}
					json.setCharAt(json.length() - 1, '}');
					json.append(",");
        		}
			}
           
            json.setCharAt(json.length() - 1, ']'); 
            json.append(",Total:"+totalCount+"}");  
        } else {  
            json.append("],Total:0}");
        }
        logger.debug(json.toString());
        return json.toString();  
    }
    
    /** 
     * 功能描述:通过传入List<Map<String,Object>>,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param list 列表对象 
     * @param totalCount 分页使用，总记录数
     * @return java.lang.String 
     */  
    public static String listToJson(List<Map<String,Object>> list,int totalCount) {  
        StringBuilder json = new StringBuilder(); 
       
        json.append("{Rows:[");  
        if (list != null && list.size() > 0) {
        	
        	for(Map<String, Object> m : list){
        		if(m!=null && m.size()>0){
	        		json.append("{");
					for (Map.Entry<String, Object> entry : m.entrySet()) {
						json.append("\"").append(entry.getKey()).append("\"");  
		                json.append(":"); 
		                json.append(objectToJson(entry.getValue()));
		                json.append(",");  
					}
					json.setCharAt(json.length() - 1, '}');
					json.append(",");
        		}
			}
           
            json.setCharAt(json.length() - 1, ']'); 
            json.append(",Total:"+totalCount+"}");  
        } else {  
            json.append("],Total:0}");
        }
        logger.debug(json.toString());
        return json.toString();  
    }
    
    /** 
     * @param object 意对象 
     * @return java.lang.String 
     */  
    public static String objectToJson(Object object) {  
        StringBuilder json = new StringBuilder();  
         
        if (object == null) {  
            json.append("\"\"");  
        	//json.append("''");  
        } else if (object instanceof Boolean  ) {
        	//logger.debug("------Boolean-------"+(String)object.toString().replaceAll("\"", "&quot;"));
        	json.append("\"").append(object.toString()).append("\"");
        } else if (object instanceof Integer || object instanceof Float || object instanceof Double || object instanceof Long) {
        	//logger.debug("------Integer、Float、Double、Long-------"+object);
            json.append(object);
        } else if (object instanceof Date) {
        	//logger.debug("------Date-------"+object);
        	if(object.toString().indexOf("00:00:00")==-1){
        		json.append("\"").append(dateTimeFormat.format(object)).append("\""); 
        	}else{
        		json.append("\"").append(dateFormat.format(object)).append("\""); 
        	}
        }else{
        	//logger.debug("------String------"+(String)object.toString().replaceAll("\"", "&quot;"));
        	json.append("\"").append(StringTool.string2Json(object.toString())).append("\"");
        }
        
        return json.toString();  
    } 
    /**
	 * 用fastjson 将json字符串解析为一个 JavaBean
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> T getPerson(String jsonString, Class<T> cls) {
		T t = null;
		try {
			t = JSONArray.parseObject(jsonString, cls);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return t;
	}

	/**
	 * 用fastjson 将json字符串 解析成为一个 List<JavaBean> 及 List<String>
	 * 
	 * @param jsonString
	 * @param cls
	 * @return
	 */
	public static <T> List<T> getPersons(String jsonString, Class<T> cls) {
		List<T> list = new ArrayList<T>();
		try {
			list = JSON.parseArray(jsonString, cls);
		}
		catch (Exception e) {
		}
		return list;
	}

	/**
	 * 用fastjson 将jsonString 解析成 List<Map<String,Object>>
	 * 
	 * @param jsonString
	 * @return
	 */
	public static List<Map<String, Object>> getListMap(String jsonString) throws DataAccessException {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			// 两种写法
			// list = JSON.parseObject(jsonString, new
			// TypeReference<List<Map<String, Object>>>(){}.getType());
			if(jsonString != null && jsonString != ""){
				//回车\r
				jsonString = jsonString.replace("\r", "\\u000D");
				//换行\n 
				jsonString = jsonString.replace("\n", "\\u000A");
				//处理中间值存在空
				jsonString = jsonString.replace(":,", ":\"\",");
				//处理末尾值存在空
				jsonString = jsonString.replace(":}", ":\"\"}");
				//处理特殊金额
				jsonString = jsonString.replace(":.", ":0.");
			    //对斜线的转义
				jsonString = jsonString.replace("\\", "\\\\");
			}

			list = JSON.parseObject(jsonString, new TypeReference<List<Map<String, Object>>>() {});
		}
		catch (Exception e) {
			throw new SysException("json串解析失败！"); 
		}
		return list;
	}

	/**
	 * 用fastjson 将jsonString 解析成 Map<String,Object>
	 * 
	 * @param jsonString
	 * @return
	 */
	public static Map<String, Object> getMap(String jsonString) {
		Map<String, Object> map = Json.fromJson(HashMap.class, Lang.inr(jsonString));
		return map;
	}
	
	/** 
     * 功能描述:通过传入List<Map<String,Object>>,调用指定方法将列表中的数据生成一个JSON规格指定字符串的Rows部分 
     * @param list 列表对象 
     * @return java.lang.String 
     */  
    public static String listToString(List<Map<String,Object>> list) {  
        StringBuilder json = new StringBuilder(); 
       
        json.append("[");  
        if (list != null && list.size() > 0) {
        	
        	for(Map<String, Object> m : list){
        		if(m!=null && m.size()>0){
	        		json.append("{");
					for (Map.Entry<String, Object> entry : m.entrySet()) {
						json.append("\"").append(entry.getKey()).append("\"");  
		                json.append(":"); 
		                json.append(objectToJson(entry.getValue()));
		                json.append(",");  
					}
					json.setCharAt(json.length() - 1, '}');
					json.append(",");
        		}
			}
           
            json.setCharAt(json.length() - 1, ']'); 
        } else {  
            json.append("]");
        }
        logger.debug(json.toString());
        return json.toString();  
    }
    
	/**
	 * xml返回的List<Map<String, Object>>在没有指定resultMap时Map中的键为大写字母需用此方法转换成小写
	 * @param list
	 * @return
	 */
  	public static List<Map<String, Object>> toListMapLower(List<Map<String, Object>> list) {
  		
  		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
  		
  		if (list.size() > 0) {
  			
  			for (Map<String, Object> map : list) {
  				
  				if(map != null && map.size() > 0){
	  				viewList.add(toMapLower(map));
  				}
  			}
  		}
  		return viewList;
  	}
    
	/**
	 * xml返回的Map<String, Object>在没有指定resultMap时Map中的键为大写字母需用此方法转换成小写
	 * @param list
	 * @return
	 */
  	public static Map<String, Object> toMapLower(Map<String, Object> map) {
  		
  		Map<String, Object> viewMap = new HashMap<String, Object>();

			if(map != null && map.size() > 0){
				
	  			for (String key : map.keySet()) {
	  				
	  				viewMap.put(key.toLowerCase(), map.get(key));
	  				
	  			}
	  			
			}
  			
  		return viewMap;
  	}
  	
  	
  	/**
	 * 将List<Object> 转换成List<Map<String,Object>>
	 * @param entryList
	 * @return List<Map<String,Object>>
	 */
  	public  static List<Map<String,Object>> beanToListMap(List<?> entryList){
		
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
	   for(Object entry : entryList){
		   
	      String s = JSON.toJSONString(entry);
	      
	      Map<String,Object> map = JSONObject.parseObject(s,Map.class);

	      list.add(map);
	   }
	   
	   return list;
	}
}
