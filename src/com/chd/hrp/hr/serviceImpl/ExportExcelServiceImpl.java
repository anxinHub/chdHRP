package com.chd.hrp.hr.serviceImpl;

import java.util.ArrayList;
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
import com.chd.base.util.ChdJson;
import com.chd.base.util.ReadFiles;
import com.chd.hrp.hr.dao.ImportMapper;
import com.chd.hrp.hr.dao.QueryMapper;
import com.chd.hrp.hr.dao.base.HrSelectMapper;
import com.chd.hrp.hr.dao.sc.HrFiledTabStrucMapper;
import com.chd.hrp.hr.dao.sc.HrTableDesignMapper;
import com.chd.hrp.hr.dao.sc.HrTableStrucMapper;
import com.chd.hrp.hr.entity.sc.HrFiledTabStruc;
import com.chd.hrp.hr.entity.sc.HrTableDesign;
import com.chd.hrp.hr.entity.sc.HrTableStruc;
import com.chd.hrp.hr.service.ExportExcelService;
import com.chd.hrp.hr.service.ImportService;
import com.chd.hrp.hr.util.ParameterHandler;
import com.github.pagehelper.PageInfo;

@Service("exportExcelService")
public class ExportExcelServiceImpl implements ExportExcelService {
	private static Logger logger = Logger.getLogger(ExportExcelServiceImpl.class);

	
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
	
	@Resource(name = "hrTableDesignMapper")
	private final HrTableDesignMapper hrTableDesignMapper = null;
	@Override
	public List<List<String>> queryExportData(Map<String, Object> entityMap)
			throws DataAccessException {
	
		try {
			List<List<String>> reList = new ArrayList<List<String>>();
			String sql = new String() ; /*new String(actrc.getTemplate_sql(),"UTF-8")*/;
			Map<String, Object> jsonMap=new HashMap<String, Object>();
			Map<String, Object> sqlMap=new HashMap<String, Object>();
			HrTableDesign hrTableDesign = hrTableDesignMapper.queryByCode(entityMap);

				
					if(hrTableDesign.getDesign_query_col()!= null && !"".equals(hrTableDesign.getDesign_query_col())){
						
					
								sql=jsonMap.get("sql_statement").toString();
			
					sqlMap  = matchAndReplaceSql(sql,entityMap);//匹配替换
					
					entityMap.put("sql",sqlMap.get("result"));
					}/*else{
						return "{\"error\":\"操作失败,查询设计器未构建.\",\"state\":\"false\"}";
					}*/
			
			
			entityMap.put("sql",sqlMap.get("result"));
			SysPage sysPage = new SysPage();

			sysPage = (SysPage) entityMap.get("sysPage");

		
				
			HrTableStruc hrTableStruc1=	hrTableStrucMapper.queryTabColsByCode(entityMap);
					
					List<Map<String, Object>> list = (List<Map<String, Object>>) queryMapper.query(entityMap);
					for (Map<String,Object> o : list) {
						List<String> l = new ArrayList<String>();
						if(hrTableStruc1 != null && hrTableStruc1.getTab_col() != null && !"".equals(hrTableStruc1.getTab_col())){
							List<Map> data = JSONArray.parseArray(hrTableStruc1.getTab_col(), Map.class);
						for (Map map : data) {
						
					
							
						
								if(!map.get("col_code").toString().toLowerCase().equals("group_id")&&!map.get("col_code").toString().toLowerCase().equals("hos_id")){
									if(o.get(map.get("col_code").toString().toUpperCase())!=null){
										l.add(o.get(map.get("col_code").toString().toUpperCase()).toString());
									}  
									
								}
								
							
					
							}
						reList.add(l);
			        		   
					}
						
						
					}
					return reList;

				
			

		}
			
		 catch (Exception e) {
			
			logger.error(e.getMessage(), e);
			
			throw new SysException("{\"error\":\"操作失败,数据库异常.\",\"state\":\"false\"}");
			
		}
	}


	@Override
	public HrTableStruc queryTabColsByCodeByExport(Map<String, Object> entityMap)
			throws DataAccessException {
		return hrTableStrucMapper.queryTabColsByCode(entityMap);
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
	public String  matchAndReplaceCol(String sql,HrTableStruc hrTableStruc1){
		Map<String, Object>  map=new HashMap<String, Object>();
		String result = sql;
		Matcher matcher = Pattern.compile("\\[.*?\\]").matcher(result);//#\\{.*?\\}
		//(@)(.*?),
		String value=null;
		List<Map<String, Object>> list = new ArrayList<Map<String,Object>>();
		list=toListMap( hrTableStruc1.getTab_col());
		Map<String, Object> tabMap= new HashMap<String, Object>();
		for (Map<String, Object> map2 : list) {
			tabMap.put(map2.get("col_name").toString(), map2.get("col_code"));
		}
		while (matcher.find()) {
			
			String key = matcher.group() ;//获取匹配到的参数
			
			String column = key.replaceAll("\\[", "").replaceAll("\\]","");
			
			if(tabMap.get(column.toLowerCase()) == null){
				value=key;
			}else{
			
			 value = "["+String.valueOf(tabMap.get(column.toLowerCase()))+"]";
			}
	
			result = result.replace(key, value);
			
		}
		
		return result;
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


	
private String replaceConstant(String replaceStr,Map<String, Object> entityMap){
		
		replaceStr = replaceStr.replaceAll("@group_id", entityMap.get("group_id").toString());
		
		replaceStr = replaceStr.replaceAll("@hos_id", entityMap.get("hos_id").toString());
		
		replaceStr = replaceStr.replaceAll("@copy_code", entityMap.get("copy_code").toString());
		
		replaceStr = replaceStr.replaceAll("@tab_code", '\''+entityMap.get("field_tab_code").toString()+'\'');
		
		return replaceStr;
		
	}


}
