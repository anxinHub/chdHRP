/**
 * 2015-1-1 LoadMenuFile.java author:pengjin
 */
package com.chd.base.startup;

import java.io.File;
import java.io.FileInputStream;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.nutz.lang.Lang;
import org.springframework.beans.factory.InitializingBean;

import com.chd.base.util.core.Coder;
import com.chd.hrp.sys.dao.ModMapper;
import com.chd.hrp.sys.entity.Mod;
import com.chd.hrp.sys.entity.SysMenu;

public class LoadMenuFile implements InitializingBean {

	private static Logger logger = Logger.getLogger(LoadMenuFile.class);

	private SAXReader reader = new SAXReader();

	private Document doc;

	private String mod_code = "";

	private int menuId = 0;

	private SysMenu menu = null;

	private List<SysMenu> list_menu = null;

//	private static List<Map<String, Object>> list_map_menu = null;
//	private static List<Map<String, Object>> list_map_top_menu = null;
	private static List<String> menu_nodes = null;
	


	@Resource(name = "modMapper")
	private ModMapper modMapper = null;

	private static String hospital = "人民医院";

	private static Map<String, List<SysMenu>> menu_map = new HashMap<String, List<SysMenu>>();

//	private static Map<String, List<Map<String, Object>>> list_menu_map = new HashMap<String, List<Map<String, Object>>>();
//	private static Map<String, List<Map<String, Object>>> list_menu_top_map = new HashMap<String, List<Map<String, Object>>>();

	private static List<Mod> mod_list = new ArrayList<Mod>();

	private static Map<String, String> mod_map = new HashMap<String, String>();

	private Map<String, List<String>> chdModMenuMap = new HashMap<String, List<String>>();
//	private static Map<String, Map<String, List<String>>> chdModCodeNodes = new HashMap<String, Map<String, List<String>>>();

	public void afterPropertiesSet() throws Exception {

		String url = LoadSystemInfo.getProjectUrl();

		if (null == url) {

			logger.error("项目路径异常。");

			return;

		}

		if (!LoadSystemInfo.isConn()) {// 验证HRP数据源

			return;

		}
		if (LoadChdInfo.get$001()!=null) {

			return;

		}

		loadMod();/* 加载系统模块 */

		if (mod_map == null || mod_map.size() == 0) {
			logger.debug("系统模块加载失败！");
			return;
		}

		loadMenuFile(new File(url + "WEB-INF/menu"));/* 加载menu文件夹 */

		if (menu_map == null || menu_map.size() == 0) {

			logger.debug("系统菜单加载失败！");

		}

	}

	public void loadMenuFile(File file) {/* 加载菜单文件 */

		File flist[] = file.listFiles();

		if (flist == null || flist.length == 0) {

			logger.debug(file + "，路径下没有找到菜单文件。");

			return;

		}
		for (File f : flist) {

			if (f.isDirectory()) {

				// 这里将列出所有的文件夹
				// System.out.println("Dir==>" + f.getAbsolutePath());

			} else {

				// 这里将列出所有的文件
				// System.out.println(f.getName().substring(f.getName().lastIndexOf(".")+1));
				// --扩展名

				if (f.getName().substring(f.getName().lastIndexOf(".") + 1).toLowerCase().equals("xml")) {

					String fileName = f.getName();

					logger.debug("load menu file:" + fileName);

					fileName = fileName.substring(0, fileName.lastIndexOf("."));

					try {

						doc = reader.read(f);

						Element root = doc.getRootElement();

						if (root == null) {

							return;

						}

						Element SysElement = (Element) root.elements().get(0);

						mod_code = SysElement.attributeValue("code");

						// 根据系统编码控制菜单

						if (mod_map.get(mod_code) == null) {

							continue;

						}

//						if (null != mod_code && !mod_code.equals("")) {
//
//							// sysMenuMapper.drpDelMenu(mod_code);//删除
//
//						}

						menuId = 0;

						list_menu = new ArrayList<SysMenu>();
//						list_map_menu = new ArrayList<Map<String, Object>>();
//						list_map_top_menu = new ArrayList<Map<String, Object>>();

						LoadMenuXml(mod_code, SysElement, fileName, 0);

						menu_map.put(mod_code, list_menu);
//						list_menu_map.put(mod_code, list_map_menu);
//						list_menu_top_map.put(mod_code, list_map_top_menu);
						
						//获取菜单每个节点下的所有带permid的节点
//						chdModCodeNodes.put(mod_code, serializeTree(list_map_menu));


					}
					catch (Exception e) {

						logger.error("加载menu目录文件异常：" + e.getMessage(), e);

					}

				}

			}

		}

	}

	// 解析菜单文件
	String code=null;
	public void LoadMenuXml(String mod_code, Element e, String fileName, int pid) throws SQLException {

		List<Element> childNodes = e.elements();
		for (Element e1 : childNodes) {// 控制一级菜单
			if (LoadChdInfo.get$100() == 1 && e1.attributeValue("code") != null && !e1.attributeValue("code").equals("")) {

				boolean isMod = false;

				if (chdModMenuMap != null && chdModMenuMap.size() > 0) {

					List<String> chdMenuList = chdModMenuMap.get(mod_code);

					if (chdMenuList != null && chdMenuList.size() > 0) {

						for (String mstr : chdMenuList) {

							if (mstr.equals(e1.attributeValue("code"))) {

								isMod = true;

								break;

							}
						}
					}

				}
				if (!isMod) {

					continue;

				}

			}

			menuId++;

			menu = new SysMenu();

			menu.setId(menuId);

			menu.setPid(pid);

			menu.setMenu_name(e1.attributeValue("name"));

			menu.setMenu_url(e1.attributeValue("url"));

			if (e1.attributeValue("accordion") != null && e1.attributeValue("accordion").equals("true")) {

				menu.setIs_accordion(true);

			} else {

				menu.setIs_accordion(false);

			}
			//所有节点都存1级菜单的code
			if(e1.attributeValue("code")!=null){
				code=e1.attributeValue("code");
			}
			menu.setPerm_id(e1.attributeValue("permid"));
			menu.setCode(code);
			menu.setIs_view(e1.attributeValue("is_view"));
			logger.debug(menuId + " " + pid + " "+e1.attributeValue("code")+ " " + e1.attributeValue("name") + " " + e1.attributeValue("permid"));

			list_menu.add(menu);

//			// 用于ztree 树状结构
//			Map<String, Object> map = new HashMap<String, Object>();
//			map.put("id", menuId);
//			map.put("pid", pid);
//			map.put("name", e1.attributeValue("name"));
//			map.put("url", e1.attributeValue("url"));
//			map.put("permid", e1.attributeValue("permid"));
//			map.put("code", e1.attributeValue("code"));
//
//			list_map_menu.add(map);
//			//accordion控制左侧菜单手风琴，不能代表一级菜单
//			if(e1.attributeValue("code")!=null && !e1.attributeValue("code").equals("")){
//				list_map_top_menu.add(map);
//			}
			LoadMenuXml(mod_code, e1, fileName, menuId);

		}

	}

	// 加载系统模块
	public void loadMod() {
		/*
		 * modMap.put("00", "系统平台"); modMap.put("01", "HPM系统"); modMap.put("02",
		 * "HTC系统");
		 */

		Map<String, Object> map = new HashMap<String, Object>();// 空map便于条件查询

		mod_list = modMapper.queryMod(map);

		if (LoadChdInfo.get$100() == 0) {// 内部使用

			for (Mod mod : mod_list) {

				mod_map.put(mod.getMod_code(), mod.getMod_name());

			}

		} else {

			loadChdLin();

			if (chdModMenuMap != null && chdModMenuMap.size() > 0) {

				for (Mod mod : mod_list) {

					for (Map.Entry<String, List<String>> entry : chdModMenuMap.entrySet()) {
						
						if (entry.getKey().equals(mod.getMod_code()) || entry.getKey().equals(mod.getParent_code())) {

							mod_map.put(mod.getMod_code(), mod.getMod_name());
							continue;	
						}
						


					}

				}

			}

		}

	}

	private void loadChdLin() {

		Element infoModule = null;

		try {

			SAXReader saxReader = new SAXReader();

			Document document = saxReader.read(new FileInputStream(new File(LoadSystemInfo.getProjectUrl() + "/WEB-INF/License.xml")));

			infoModule = (Element) document.selectSingleNode("/sentinel_ldk:license/module");

		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("license文件异常", e);
		}

		String infoXml = "";

		if (infoModule != null && !infoModule.getText().equals("")) {

			try {

				infoXml = new String(Coder.decryptBASE64(infoModule.getText()), "UTF-8");

			}
			catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("license文件异常", e);
			}

		}

		if (infoXml != null && !infoXml.equals("")) {

			infoXml = "<?xml version=\"1.0\" encoding=\"utf-8\"?><root>" + infoXml + "</root>";

			Document document = null;

			try {

				document = DocumentHelper.parseText(infoXml);

			}
			catch (DocumentException e) {

				logger.error("license文件异常", e);

			}

			List<Element> moduleList = new ArrayList<Element>();

			List<Element> menuList = new ArrayList<Element>();

			List<String> chdMenuList = null;

			moduleList = document.selectNodes("/root/module");

			if (moduleList != null && moduleList.size() > 0) {

				for (Element e : moduleList) {

					menuList = e.elements();

					if (menuList != null && menuList.size() > 0) {

						chdMenuList = new ArrayList<String>();

						for (Element ec : menuList) {

							chdMenuList.add(ec.attributeValue("code"));

						}

						chdModMenuMap.put(e.attributeValue("code"), chdMenuList);// 模块编码,一级菜单
					}

				}

			}

		}

	}

	public static List<Map<String, Object>> getChdModMap(List<Map<String, Object>> li) {

		List<Map<String, Object>> liMap = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = null;

		if (null != li && li.size() > 0) {

			for (Map<String, Object> m : li) {

				for (Map.Entry<String, Object> entry : m.entrySet()) {

					if (mod_map.get(entry.getKey()) != null) {

						map = new HashMap<String, Object>();

						map.put("id", entry.getKey());

						map.put("text", entry.getValue());

					}
				}

			}

			liMap.add(map);

		}

		return liMap;
	}

	public static Map<String, List<SysMenu>> getMenuMap() {
		return menu_map;
	}

	public static List<Mod> getModList() {
		return mod_list;
	}

	public static Map<String, String> getModMap() {
		return mod_map;
	}

	public static String getHospital() {
		return hospital;
	}

//	/**
//	 * 获取 list_menu_map
//	 * 
//	 * @return list_menu_map
//	 */
//	public static Map<String, List<Map<String, Object>>> getList_menu_map() {
//		return list_menu_map;
//	}

	/**
	 * 查询当前节点的子节点
	 * 
	 * @param rs
	 *            当前结果集
	 * @param map
	 *            当前节点的id 用于判断是否存在子节点
	 * @return
	 */
	private static void search(List<Map<String, Object>> rs, String id) {
		List<Map<String, Object>> p=new ArrayList<Map<String,Object>>();
		Iterator<Map<String, Object>> it = rs.iterator();
		while (it.hasNext()) {
			Map<String, Object> n = it.next();
			if (n.get("pid").toString().equals(id)) {
				if (!Lang.isEmpty(n.get("permid"))) {
					menu_nodes.add(n.get("permid").toString());
				}else{
					p.add(n);
				}
			}
		}
		if(p.size()>0){
			for (Map<String, Object> map : p) {
				search(rs,map.get("id").toString());
			}
		}
		
	}

	private static Map<String, List<String>> serializeTree(List<Map<String, Object>> root) {
		/*
		 * 作为树的载体
		 */
		Map<String, List<String>> tempDate = new HashMap<String, List<String>>();

		Iterator<Map<String, Object>> it = root.iterator();
		while (it.hasNext()) {
			Map<String, Object> n = it.next();

			menu_nodes=new ArrayList<String>();
			search(root,n.get("id").toString());
			
			tempDate.put(n.get("id").toString(), menu_nodes);
			
		}
		return tempDate;
	}

	
//	/**
//	 * 获取 chdModCodeNodes
//	 * @return chdModCodeNodes
//	 */
//	public static Map<String, Map<String, List<String>>> getChdModCodeNodes() {
//		return chdModCodeNodes;
//	}
//
//	
//	/**
//	 * 获取 list_map_menu
//	 * @return list_map_menu
//	 */
//	public static List<Map<String, Object>> getList_map_menu() {
//		return list_map_menu;
//	}
//
//	
//	/**
//	 * 获取 list_map_top_menu
//	 * @return list_map_top_menu
//	 */
//	public static List<Map<String, Object>> getList_map_top_menu() {
//		return list_map_top_menu;
//	}
//	/**
//	 * 获取 list_menu_top_map
//	 * @return list_menu_top_map
//	 */
//	public static Map<String, List<Map<String, Object>>> getList_menu_top_map() {
//		return list_menu_top_map;
//	}
}
