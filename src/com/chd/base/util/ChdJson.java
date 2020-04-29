/**
 * @Copyright: Copyright (c) 2015年10月22日 下午5:41:22
 * @Company: 智慧云康（北京）数据科教有限公司
 */
package com.chd.base.util;

import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.nutz.json.Json;
import org.nutz.json.JsonFormat;
import org.nutz.lang.util.NutMap;
import org.nutz.lang.util.NutType;
import org.nutz.mapl.Mapl;

/**
 * @Title. @Description. <br>
 *         适配json 格式
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
public class ChdJson extends Json {

	private static Logger logger = Logger.getLogger(ChdJson.class);

	/**
	 * @param list
	 *            结果集
	 * @param totalCount
	 *            总条数
	 * @return json 的数据格式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJson(List<?> list) {

		ViewToLgerUI view = new ViewToLgerUI();
		view.setTotal(list.size());
		view.setRows(list);
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}

	/**
	 * @param list
	 *            结果集
	 * @param totalCount
	 *            总条数
	 * @return json 的数据格式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJson(List<?> list, int totalCount) {

		ViewToLgerUI view = new ViewToLgerUI();
		view.setTotal(totalCount);
		view.setRows(list);
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd").setUnicodeLower(false));

		// logger.debug(viewJson);
		return viewJson;
	}

	/**
	 * @param list
	 *            结果集
	 * @param totalCount
	 *            总条数
	 * @return json 的数据格式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJson(List<?> list, Long totalCount) {

		ViewToLgerUI view = new ViewToLgerUI();
		view.setTotal(totalCount.intValue());
		view.setRows(list);
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}

	/**
	 * @param list
	 *            结果集
	 * @param totalCount
	 *            总条数
	 * @return json 的数据格式
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJsonLower(List<Map<String, Object>> list, Long totalCount) {
		ViewToLgerUI view = new ViewToLgerUI();

		view.setTotal(totalCount.intValue());

		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();

		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map viewMap = new HashMap();

				for (String key : map.keySet()) {

					viewMap.put(key.toLowerCase(), map.get(key));

				}

				viewList.add(viewMap);

			}

		}

		view.setRows(viewList);
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}
	
	

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJsonLower(List<Map<String, Object>> list) {

		ViewToLgerUI view = new ViewToLgerUI();
		
		view.setTotal(list.size());
		
		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
		
		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map viewMap = new HashMap();

				for (String key : map.keySet()) {

					viewMap.put(key.toLowerCase(), map.get(key));

				}

				viewList.add(viewMap);

			}

		}

		view.setRows(viewList);
		
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}
	
	/**
	 * 用于财务分类出库报表,将map中的KEY转换成大写
	 * @param list
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static String toJsonUpper(List<Map<String, Object>> list) {
		
		ViewToLgerUI view = new ViewToLgerUI();
		
		view.setTotal(list.size());
		
		List<Map<String, Object>> viewList = new ArrayList<Map<String, Object>>();
		
		if (list.size() > 0) {
			
			for (Map<String, Object> map : list) {
				
				Map viewMap = new HashMap();
				
				for (String key : map.keySet()) {
					
					viewMap.put(key.toUpperCase(), map.get(key));
					
				}
				
				viewList.add(viewMap);
				
			}
			
		}
		
		view.setRows(viewList);
		
		String viewJson = Json.toJson(view, JsonFormat.full().setDateFormat("yyyy-MM-dd"));
		// logger.debug(viewJson);
		return viewJson;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String,Object>> toListLower(List<Map<String,Object>> list) {

		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();
		
		if (list.size() > 0) {

			for (Map<String, Object> map : list) {

				Map returnMap = new HashMap();

				for (String key : map.keySet()) {

					returnMap.put(key.toLowerCase(), map.get(key));

				}

				returnList.add(returnMap);

			}

		}
		
		return returnList;
	}

	/**
	 * @param reader
	 *            json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static ViewToLgerUI fromJsonAsLgerUI(Reader reader) {
		ViewToLgerUI view = Json.fromJson(ViewToLgerUI.class, reader);
		List<Map<String, Object>> list = (List<Map<String, Object>>) Json.fromJson(NutType.list(NutType.mapStr(Object.class)), Json.toJson(view.getRows()));
		view.setRows(list);
		return view;
	}

	/**
	 * @param clazz
	 *            转换对象
	 * @param reader
	 *            json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static <T> ViewToLgerUI fromJsonAsLgerUI(Class<T> clazz, Reader reader) {
		ViewToLgerUI view = Json.fromJson(ViewToLgerUI.class, reader);
		List<T> list = (List<T>) Json.fromJsonAsList(clazz, Json.toJson(view.getRows()));
		view.setRows(list);
		return view;
	}

	/**
	 * @param reader
	 *            json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static List<Map<String, Object>> fromJsonAsList(Reader reader) {
		List<Map<String, Object>> list = (List<Map<String, Object>>) Json.fromJson(NutType.list(NutType.mapStr(Object.class)), reader);
		return list;
	}

	/**
	 * @param clazz
	 *            转换对象
	 * @param reader
	 *            json
	 * @return
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public static <T> List<T> fromJsonAsList(Class<T> clazz, Reader reader) {
		List<T> list = (List<T>) Json.fromJsonAsList(clazz, reader);
		return list;
	}

	/**
	 * @param reader
	 *            json
	 * @return
	 */
	public static Object fromJson(Reader reader) {
		return Json.fromJson(reader);
	}

	/**
	 * 校验字段值是否合法
	 * @param str
	 * @return
	 */
	public static boolean validateJSON(String str) {
		if (str != null && !"null".equals(str) && !"".equals(str)) {

			return true;
		}

		return false;
	}
}
