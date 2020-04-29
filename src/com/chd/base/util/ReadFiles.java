package com.chd.base.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 导入时候动态 columns和contents做匹配
 * @param map
 * @return
 * @throws Exception
 */
public class ReadFiles {

	public static List<Map<String, List<String>>> readFiles(Map<String, Object> map) throws Exception {
		/**
		 * 定义list接受返回数据
		 */
		List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
		
		// 接收动态columns 共用的import.jsp页面
		String columns = map.get("para").toString();
		JSONObject json=JSONObject.parseObject(columns);
		JSONArray jsonColumns=JSONObject.parseArray(json.getString("column"));
		if (jsonColumns == null || jsonColumns.size() == 0) {
			return list;
		}
		// 表头拼接
		String content = map.get("content").toString();
		List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
		if (liData == null || liData.size() == 0) {
			return list;
		}

		List<String> rowList = null;
		// columns和表头做数据匹配
		for (Map<String, List<String>> item : liData) {
			Map<String, List<String>> mapVo1 = new HashMap<String, List<String>>();
			
			int nullSize = 0;//过滤空行,如果空列数==总列数 退出当前循环
			for (int j = 0; j < jsonColumns.size(); j++) {
				JSONObject jsonObj = JSONObject.parseObject(jsonColumns.getString(j));
				rowList = item.get(jsonObj.getString("display"));
				
				if (rowList == null || rowList.get(1) == null) {
					nullSize++;
					//return list;
				}
				// 需要columns的name和rowList,存放map中，rowList数据为方便前台验证
				mapVo1.put(jsonObj.getString("name"), rowList);
			}
			
			if(nullSize == jsonColumns.size()){
				continue;
			}
			list.add(mapVo1);
		}

		return list;

	}
	//动态columns 直接获取grid.columns
	public static List<Map<String, List<String>>> readFileColumns(Map<String, Object> map) throws Exception {
		/**
		 * 定义list接受返回数据
		 */
		List<Map<String, List<String>>> list = new ArrayList<Map<String, List<String>>>();
		//动态columns 单独jsp页面使用
			String columns = map.get("columns").toString();
		    JSONArray jsonColumns = JSONObject.parseArray(columns);
		// 接收动态columns 共用的import.jsp页面
		/*String columns = map.get("para").toString();
		JSONObject json=JSONObject.parseObject(columns);
		JSONArray jsonColumns=JSONObject.parseArray(json.getString("column"));*/
		if (jsonColumns == null || jsonColumns.size() == 0) {
			return list;
		}
		// 表头拼接
		String content = map.get("content").toString();
		List<Map<String, List<String>>> liData = SpreadTableJSUtil.toListMap(content, 1);
		if (liData == null || liData.size() == 0) {
			return list;
		}

		List<String> rowList = null;
		// columns和表头做数据匹配
		for (Map<String, List<String>> item : liData) {
			Map<String, List<String>> mapVo1 = new HashMap<String, List<String>>();

			for (int j = 0; j < jsonColumns.size(); j++) {

				JSONObject jsonObj = JSONObject.parseObject(jsonColumns.getString(j));
				rowList = item.get(jsonObj.getString("display"));
				if (rowList == null ) {
					return list;
				}
				// 需要columns的name和rowList,存放map中，rowList数据为方便前台验证
				mapVo1.put(jsonObj.getString("name"), rowList);
			}

			list.add(mapVo1);
		}

		return list;

	}
}
