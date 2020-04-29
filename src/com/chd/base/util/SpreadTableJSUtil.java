package com.chd.base.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.exception.SysException;

public class SpreadTableJSUtil {

	/**
	 * spread表格转List<Map<String,Object>>
	 * @param content：spread表格json
	 * @param headIndex ：表头行数
	 * @return去掉空行，把表头转为map的key值
	 * @throws Exception
	 */
	public static List<Map<String,List<String>>> toListMap(String content,int headIndex) throws Exception {
		if(content.equals("")){
			return null;
		}
		
		//提取spread表格
		JSONObject json=JSONObject.parseObject(content);
		JSONObject jsonSheet=JSONObject.parseObject(json.getString("sheets"));
		
		if(jsonSheet==null || jsonSheet.size()==0){
			return null;
		}
		
		//循环遍历sheet
		JSONObject sheetObject=null;
		for (String sheet : jsonSheet.keySet()) {  
			sheetObject=jsonSheet.getJSONObject(sheet);
			break;//只能导入第一个sheet
		}
		
		if(sheetObject==null){
			return null;
		}
		
		JSONObject data=sheetObject.getJSONObject("data");
		if(data==null){
			return null;
		}
		
		JSONObject dataTable=data.getJSONObject("dataTable");
		if(dataTable==null || dataTable.size()==0){
			return null;
		}
		
		//拼表头，做为key
		JSONObject rowHeadObject=dataTable.getJSONObject(String.valueOf(headIndex-1));
		Map<String,Object> headMap=new HashMap<String,Object>();
		for (String cell : rowHeadObject.keySet()) {
			JSONObject cellObject=rowHeadObject.getJSONObject(cell);
			if(cellObject.getString("value")!=null && !cellObject.getString("value").equals("")){
				headMap.put(cell, cellObject.getString("value"));
			}
		}
		
		
		List<Map<String,List<String>>> imList=new ArrayList<Map<String,List<String>>>();
		Map<String,List<String>> dataMap=null;
		List<String> rowList = null;
		
		Integer[] l1= new Integer[dataTable.size()];
		int i=0;
		for(String s1: dataTable.keySet()){
			l1[i] = Integer.parseInt(s1);
			i++;
		}
		Arrays.sort(l1);
		
		for (int j=0;j<l1.length;j++) {  
			String rows = l1[j]+"";
			for (String row : dataTable.keySet()) { 
				if(rows.equalsIgnoreCase(row)){
					if(Integer.parseInt(row)<headIndex){
						//跳过表头
						continue;
					}
					dataMap=new HashMap<String,List<String>>();
					JSONObject rowObject=dataTable.getJSONObject(row);
					//遍历列
					/*for (String cell : rowObject.keySet()) {
						JSONObject cellObject=rowObject.getJSONObject(cell);
						if(headMap.get(cell)!=null){
							rowList = new ArrayList<String>();
							rowList.add((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1));
							rowList.add(cellObject.getString("value"));
							dataMap.put(headMap.get(cell).toString(),rowList);
						}
						
					}*/
					//修改  zhaon
					for (String cell : headMap.keySet()) {
						JSONObject cellObject=rowObject.getJSONObject(cell);
						rowList = new ArrayList<String>();
						rowList.add((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1));
						rowList.add(cellObject == null ? null : getStringNoBlank(cellObject.getString("value")));
						dataMap.put(headMap.get(cell).toString(),rowList);
					}
					imList.add(dataMap);
				}else{
					continue;
				}
			}
		}	
		
		//遍历行
		/*for (String row : dataTable.keySet()) {  
			if(Integer.parseInt(row)<headIndex){
				//跳过表头
				continue;
			}
			dataMap=new HashMap<String,List<String>>();
			JSONObject rowObject=dataTable.getJSONObject(row);
			//遍历列
			for (String cell : rowObject.keySet()) {
				JSONObject cellObject=rowObject.getJSONObject(cell);
				if(headMap.get(cell)!=null){
					rowList = new ArrayList<String>();
					rowList.add((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1));
					rowList.add(cellObject.getString("value"));
					dataMap.put(headMap.get(cell).toString(),rowList);
				}
				
			}
			imList.add(dataMap);
		}*/
		
		return imList;
	}
	
	//去除前后空格与回车
	public static String getStringNoBlank(String str) {
		if(str!=null && !"".equals(str)) {
			str.replaceAll("\n", "");
			Pattern p = Pattern.compile("(^\\s*)|(\\s*$)");
			Matcher m = p.matcher(str);
			String strNoBlank = m.replaceAll("");
			return strNoBlank;
		}else {
			return  "";
		}
	}
	
	/**
	 * spread表格转List<Map<String,Object>>  Map有序
	 * @param content：spread表格json
	 * @param headIndex ：表头行数
	 * @return去掉空行，把表头转为map的key值
	 * @throws Exception
	 */
	public static List<Map<String,List<String>>> toListMapOrderly(String content,int headIndex) throws Exception {
		if(content.equals("")){
			return null;
		}
		
		//提取spread表格
		JSONObject json=JSONObject.parseObject(content);
		JSONObject jsonSheet=JSONObject.parseObject(json.getString("sheets"));
		
		if(jsonSheet==null || jsonSheet.size()==0){
			return null;
		}
		
		//循环遍历sheet
		JSONObject sheetObject=null;
		for (String sheet : jsonSheet.keySet()) {  
			sheetObject=jsonSheet.getJSONObject(sheet);
			break;//只能导入第一个sheet
		}
		
		if(sheetObject==null){
			return null;
		}
		
		JSONObject data=sheetObject.getJSONObject("data");
		if(data==null){
			return null;
		}
		
		JSONObject dataTable=data.getJSONObject("dataTable");
		if(dataTable==null || dataTable.size()==0){
			return null;
		}
		
		//拼表头，做为key
		JSONObject rowHeadObject=dataTable.getJSONObject(String.valueOf(headIndex-1));
		Map<String,Object> headMap=new LinkedHashMap<String,Object>();
		
		int cellCount = 0 ;//列数标记,从0开始
		String cellCountStr = "";//用于按顺序读取表头
		for (String cell : rowHeadObject.keySet()) {
			
			cellCountStr = String.valueOf(cellCount);
			
			JSONObject cellObject=rowHeadObject.getJSONObject(cellCountStr);
			if(cellObject.getString("value")!=null && !cellObject.getString("value").equals("")){
				headMap.put(cellCountStr, cellObject.getString("value"));
			}
			
			cellCount++;
		}
		
		
		List<Map<String,List<String>>> imList=new ArrayList<Map<String,List<String>>>();
		Map<String,List<String>> dataMap=null;
		List<String> rowList = null;
		
		Integer[] l1= new Integer[dataTable.size()];
		int i=0;
		for(String s1: dataTable.keySet()){
			l1[i] = Integer.parseInt(s1);
			i++;
		}
		Arrays.sort(l1);
		
		for (int j=0;j<l1.length;j++) {  
			String rows = l1[j]+"";
			for (String row : dataTable.keySet()) { 
				if(rows.equalsIgnoreCase(row)){
					if(Integer.parseInt(row)<headIndex){
						//跳过表头
						continue;
					}
					dataMap=new LinkedHashMap<String,List<String>>();
					JSONObject rowObject=dataTable.getJSONObject(row);
					//遍历列
					/*for (String cell : rowObject.keySet()) {
						JSONObject cellObject=rowObject.getJSONObject(cell);
						if(headMap.get(cell)!=null){
							rowList = new ArrayList<String>();
							rowList.add((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1));
							rowList.add(cellObject.getString("value"));
							dataMap.put(headMap.get(cell).toString(),rowList);
						}
						
					}*/
					//修改  zhaon
					for (String cell : headMap.keySet()) {
						JSONObject cellObject=rowObject.getJSONObject(cell);
						rowList = new ArrayList<String>();
						rowList.add((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1));
						rowList.add(cellObject == null ? null:cellObject.getString("value"));
						dataMap.put(headMap.get(cell).toString(),rowList);
					}
					imList.add(dataMap);
				}else{
					continue;
				}
			}
		}	
		
		//遍历行
		/*for (String row : dataTable.keySet()) {  
			if(Integer.parseInt(row)<headIndex){
				//跳过表头
				continue;
			}
			dataMap=new HashMap<String,List<String>>();
			JSONObject rowObject=dataTable.getJSONObject(row);
			//遍历列
			for (String cell : rowObject.keySet()) {
				JSONObject cellObject=rowObject.getJSONObject(cell);
				if(headMap.get(cell)!=null){
					rowList = new ArrayList<String>();
					rowList.add((Integer.parseInt(row)+1)+"："+(Integer.parseInt(cell)+1));
					rowList.add(cellObject.getString("value"));
					dataMap.put(headMap.get(cell).toString(),rowList);
				}
				
			}
			imList.add(dataMap);
		}*/
		
		return imList;
	}
}
