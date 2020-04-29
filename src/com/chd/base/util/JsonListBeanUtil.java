/** 
* 2014-4-10 
* JsonUtil.java 
* author:pengjin
* 通过List<?>转换成json格式的String
*/ 
package com.chd.base.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;



public class JsonListBeanUtil {
	private static Logger logger = Logger.getLogger(JsonListBeanUtil.class);
	private static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	private static SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	 /** 
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param list 列表对象 
     * @param totalCount 分页使用，总记录数
     * @param objBean 增加汇总行
     * @return java.lang.String 
     */  
    public static String listToJson(List<?> list,int totalCount,Object objBean) {  
        StringBuilder json = new StringBuilder(); 
       
        json.append("{Rows:[");  
        if (list != null && list.size() > 0) {
        	if(objBean!=null){
        		json.append(objectToJson(objBean)); 
        	}
            for (Object obj : list) {  
                json.append(objectToJson(obj));  
                json.append(",");  
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
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param list 列表对象 
     * @param totalCount 分页使用，总记录数,-1不分页取list的size,否则取物理记录数
     * @return java.lang.String 
     */  
    public static String listToJson(List<?> list,int totalCount) {  
        StringBuilder json = new StringBuilder(); 
     
        json.append("{Rows:[");  
        if (list != null && list.size() > 0) {  
            for (Object obj : list) {  
                json.append(objectToJson(obj));  
                json.append(",");  
            }  
            json.setCharAt(json.length() - 1, ']'); 
            if(totalCount==-1) json.append(",Total:"+list.size()+"}"); 
            else json.append(",Total:"+totalCount+"}"); 
        } else {  
            json.append("],Total:0}");  
        }  
        logger.debug(json.toString());
        return json.toString();  
    } 
    
    
    /** 
     * 功能描述:通过传入一个列表对象,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     * @param list 列表对象 
     * @return java.lang.String 
     */  
    public static String listToJson(List<?> list) {  
        StringBuilder json = new StringBuilder();  
        json.append("{Rows:[");  
        if (list != null && list.size() > 0) {  
            for (Object obj : list) {  
                json.append(objectToJson(obj));  
                json.append(",");  
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
     * @param object 
     *            任意对象 
     * @return java.lang.String 
     */  
    public static String objectToJson(Object object) {  
        StringBuilder json = new StringBuilder();  
                
        if (object == null) {  
            json.append("\"\"");  
        	//json.append("''");  
        } else if (object instanceof String || object instanceof Boolean  ) {
        	//System.out.println("------String、Boolean-------"+(String)object.toString().replaceAll("\"", "&quot;"));
            //json.append("\"").append((String)object.toString().replaceAll("\"", "&quot;")).append("\"");
        	json.append("\"").append(StringTool.string2Json(object.toString())).append("\"");
        } else if (object instanceof Integer || object instanceof Float || object instanceof Double || object instanceof Long) {
        	//System.out.println("------Integer、Float、Double、Long-------"+object);
            json.append(object);
        } else if (object instanceof Date) {
        	//System.out.println("------Date-------"+object);
        	if(object.toString().indexOf("00:00:00")==-1){
        		json.append("\"").append(dateTimeFormat.format(object)).append("\""); 
        	}else{
        		json.append("\"").append(dateFormat.format(object)).append("\""); 
        	}
        	 
        } else {
        	//System.out.println("==============="+object);
            json.append(beanToJson(object));  
        }  
        
        return json.toString();  
    }  
  
    /** 
     * 功能描述:传入任意一个 javabean 对象生成一个指定规格的字符串 
     *  
     * @param bean 
     *            bean对象 
     * @return String 
     */  
    public static String beanToJson(Object bean) {  
        StringBuilder json = new StringBuilder();  
        json.append("{");  
        PropertyDescriptor[] props = null;  
        try {  
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)  
                    .getPropertyDescriptors();  
        } catch (IntrospectionException e) {  
        }  
        if (props != null) {  
            for (int i = 0; i < props.length; i++) {  
                try {  
               
                    String name = objectToJson(props[i].getName());
                    Object obj=props[i].getReadMethod().invoke(bean);
                    String value = objectToJson(obj);
                    json.append(name);  
                    json.append(":"); 
                    json.append(value);
                    json.append(",");  
                } catch (Exception e) {  
                }  
            }  
            json.setCharAt(json.length() - 1, '}');  
        } else {  
            json.append("}");  
        }  
        return json.toString();  
    }  
   
    
    /** 
     * 功能描述:通过传入一个二维数组,调用指定方法将列表中的数据生成一个JSON规格指定字符串 
     *  
     * @param list 
     *            列表对象 
     * @return java.lang.String 
     */  
    public static String StringArrayToJson(String [][] list) {  
        StringBuilder json = new StringBuilder();  
        json.append("{Rows:[");  
        if (list != null && list.length > 0) {  
            for (int i=0;i< list.length;i++) {
            	json.append("{");  
            	for(int j=0;j<list[i].length;j++){
            		json.append("\"td"+j+"\":\""+list[i][j]+"\",");  
            	}
            	json.append("}"); 
            }  
            json.setCharAt(json.length() - 1, ']'); 
            json.append(",Total:"+list.length+"}");  
        } else {  
            json.append("],Total:0}");  
        }  
      
        return json.toString();  
    } 
    
  
}
