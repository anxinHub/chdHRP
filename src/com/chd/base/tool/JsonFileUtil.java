package com.chd.base.tool;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.nutz.json.Json;
import org.nutz.lang.Files;

public class JsonFileUtil {

	private static Map<String, Object> ioc = new HashMap<String, Object>();

	private static Map<String, Object> dataSource = new HashMap<String, Object>();

	private static Map<String, Object> dao = new HashMap<String, Object>();

	private static Map<String, Object> events = new HashMap<String, Object>();

	private static Map<String, Object> fields = new HashMap<String, Object>();

	private static String TYPE = "type";

	private static String EVENTS = "events";

	private static String FIELDS = "fields";

	private static String CREATE = "create";

	private static String DEPOSE = "depose";

	private static String DRIVERCLASSNAME = "driverClassName";

	private static String URL = "url";

	private static String USERNAME = "username";

	private static String PASSWORD = "password";

	private static String TEATWHILEIDLE = "testWhileIdle";

	private static String VALIDATIONQUERY = "validationQuery";

	private static String MAXACTIVE = "maxActive";

	private static String MAXWAIT = "maxWait";

	private static String REFER = "refer";

	private static List<Map<String, Object>> args = new ArrayList<Map<String, Object>>();

	public static void init(Map<String, Object> map) {

		events.put(CREATE, "init");
		events.put(DEPOSE, "close");
		fields.put(DRIVERCLASSNAME, map.get(DRIVERCLASSNAME)==null?"oracle.jdbc.driver.OracleDriver":map.get(DRIVERCLASSNAME));
		fields.put(URL, map.get(URL)==null?"jdbc:oracle:thin:@localhost:1521:ORCL":map.get(DRIVERCLASSNAME));
		fields.put(USERNAME, map.get(USERNAME)==null?"hrp":map.get(DRIVERCLASSNAME));
		fields.put(PASSWORD, map.get(PASSWORD)==null?"chd":map.get(DRIVERCLASSNAME));
		fields.put(TEATWHILEIDLE, true);
		fields.put(VALIDATIONQUERY, map.get(VALIDATIONQUERY)==null?"select 1 from dual":map.get(DRIVERCLASSNAME));
		fields.put(MAXACTIVE, 100);
		fields.put(MAXWAIT, 5000);

		dataSource.put(TYPE, map.get(TYPE)==null?"com.alibaba.druid.pool.DruidDataSource":map.get(DRIVERCLASSNAME));
		dataSource.put(EVENTS, events);
		dataSource.put(FIELDS, fields);

		Map<String, Object> m = new HashMap<String, Object>();
		m.put(REFER, map.get("dataSource")==null?"dataSource":map.get(DRIVERCLASSNAME));
		args.add(m);
		dao.put("args", args);
		dao.put("type", "org.nutz.dao.impl.NutDao");

		ioc.put("dataSource", dataSource);
		ioc.put("dao", dao);
	}

	public static Map<String, Object> getIoc() {
		return ioc;
	}

	public static void setIoc(Map<String, Object> ioc) {
		JsonFileUtil.ioc = ioc;
	}

	public static Map<String, Object> getDataSource() {
		return dataSource;
	}

	public static void setDataSource(Map<String, Object> dataSource) {
		JsonFileUtil.dataSource = dataSource;
	}

	public static Map<String, Object> getDao() {
		return dao;
	}

	public static void setDao(Map<String, Object> dao) {
		JsonFileUtil.dao = dao;
	}

	public static void main(String[] args) {
		
		
		Map<String, Object> m = new HashMap<String, Object>();
		JsonFileUtil.init(m);
		System.out.println(Json.toJson(ioc));

		URL url=ClassLoader.getSystemResource("");
		String path=url+"ioc/dao.js";
		String new_path=path.replace("file:/", "");
		// 删除已存在的文件
		Files.deleteFile(new File(new_path));
		// 创建文件
		try {
			File f = Files.createFileIfNoExists(new_path);
			Files.appendWrite(f, Json.toJson(ioc));
			// / 验证读取文件内容
			// List<Map<String, Object>> list = (List<Map<String, Object>>)
			// Json.fromJson(NutType.list(NutType.mapStr(Object.class)), new
			// FileReader(f));
			// System.out.println(list.size());
		}
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
