package com.chd.base.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.poi.ss.formula.functions.T;
import org.nutz.json.Json;
import org.nutz.lang.Lang;
import org.springframework.dao.DataAccessException;

import com.chd.base.SessionManager;
import com.chd.base.startup.LoadMenuFile;
import com.chd.hrp.sys.entity.SysMenu;
import com.chd.hrp.sys.entity.User;

public class ViewToZtree {

	private static Logger logger = Logger.getLogger(ViewToZtree.class);

	private static List<String> menu_nodes = null;

	private static Map<String, List<String>> List_menu_nodes = null;

	/**
	 * 递归获取整个树
	 * 
	 * @param root
	 *            获取根节点的集合 <br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 * @param data
	 *            获取所有节点的机会<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 * @return
	 */
	public static String zTreeData(ZtreeEntity root, ZtreeEntity data) {
		List<Map<String, Object>> tree = serializeTree(root, data);
		ZtreeVo ztree = new ZtreeVo();
		ztree.setRows(tree);
		return Json.toJson(ztree);
	}

	/**
	 * 递归获取整个树
	 * 
	 * @param root
	 *            获取根节点的集合 <br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 * @param data
	 *            获取所有节点的集合<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 * @param isCheckData
	 *            目前选中的集合<br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 * @return
	 */
	public static String zTreeData(ZtreeEntity root, ZtreeEntity data, List<String> isCheckData) {
		// 获取每个节点下的所有末级节点，用于权限判断
		serializeLastTreeNode(data.getData());
		List<Map<String, Object>> tree = serializeTree(root, data, isCheckData);
		ZtreeVo ztree = new ZtreeVo();
		ztree.setRows(tree);
		return Json.toJson(ztree);
	}

	/**
	 * 递归获取整个树
	 * 
	 * @param root
	 *            |获取根节点的集合<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|<br>
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @param data
	 *            获取所有节点的机会<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @param parm
	 *            附加参数 方便不同树状结构的体现<br>
	 *            说明:Map 的格式如下<br>
	 *            map.put("列名","列名");<br>
	 *            map.put("列名","列名");
	 * @return
	 */
	public static String zTreeData(ZtreeEntity root, ZtreeEntity data, Map<String, Object> parm) {
		List<Map<String, Object>> tree = serializeTree(root, data, parm);
		ZtreeVo ztree = new ZtreeVo();
		ztree.setRows(tree);
		return Json.toJson(ztree);
	}

	/**
	 * 递归获取整个树
	 * 
	 * @param root
	 *            获取根节点的集合 <br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @param data
	 *            获取所有节点的机会<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @return
	 */
	private static List<Map<String, Object>> serializeTree(ZtreeEntity root, ZtreeEntity data) {
		/*
		 * 作为树的载体
		 */
		List<Map<String, Object>> tempDate = new ArrayList<Map<String, Object>>();

		// 当顶层菜单有数据的时候 依次循环
		while (root.next()) {
			// 首先生成本菜单数据
			Map<String, Object> obj = transStrToZtree(root);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", root.getData("id"));
			ZtreeEntity rsNext = searchNode(data, m);
			// 如果有子菜单
			if (rsNext.getData().size() > 0) {
				// 递归生成子菜单数据 并与父菜单关联
				obj.put("children", serializeTree(rsNext, data));

			}
			// 将本菜单放入载体
			tempDate.add(obj);

		}
		return tempDate;
	}

	/**
	 * 递归获取整个树
	 * 
	 * @param root
	 *            获取根节点的集合 <br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @param data
	 *            获取所有节点的机会<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @return
	 */
	private static List<Map<String, Object>> serializeTree(ZtreeEntity root, ZtreeEntity data, List<String> isCheckData) {
		/*
		 * 作为树的载体
		 */
		List<Map<String, Object>> tempDate = new ArrayList<Map<String, Object>>();

		// 当顶层菜单有数据的时候 依次循环
		while (root.next()) {
			// 首先生成本菜单数据
			Map<String, Object> obj = transStrToZtree(root, isCheckData);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", root.getData("id"));
			ZtreeEntity rsNext = searchNode(data, m);
			// 如果有子菜单
			if (rsNext.getData().size() > 0) {
				// 递归生成子菜单数据 并与父菜单关联
				obj.put("children", serializeTree(rsNext, data, isCheckData));

			}
			// 将本菜单放入载体
			tempDate.add(obj);

		}
		return tempDate;
	}

	/**
	 * 递归获取整个树
	 * 
	 * @param root
	 *            获取根节点的集合<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @param data
	 *            获取所有节点的集合<br>
	 *            说明:集合中必须包含 列名 id pid name 三列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @param parm
	 *            附加参数 方便不同树状结构的体现<br>
	 *            说明:Map 的格式如下<br>
	 *            map.put("列名","列名");<br>
	 *            map.put("列名","列名");
	 * @return
	 */
	private static List<Map<String, Object>> serializeTree(ZtreeEntity root, ZtreeEntity data, Map<String, Object> parm) {
		/*
		 * 作为树的载体
		 */
		List<Map<String, Object>> tempDate = new ArrayList<Map<String, Object>>();

		// 当顶层菜单有数据的时候 依次循环
		while (root.next()) {
			// 首先生成本菜单数据
			Map<String, Object> obj = transStrToZtree(root, parm);
			Map<String, Object> m = new HashMap<String, Object>();
			m.put("id", root.getData("id"));
			ZtreeEntity rsNext = searchNode(data, m);
			// 如果有子菜单
			if (rsNext.getData().size() > 0) {
				// 递归生成子菜单数据 并与父菜单关联
				obj.put("children", serializeTree(rsNext, data, parm));

			}
			// 将本菜单放入载体
			tempDate.add(obj);

		}
		return tempDate;
	}

	/**
	 * 获取当前结点的信息
	 * 
	 * @param rs
	 * @return
	 */
	private static Map<String, Object> transStrToZtree(ZtreeEntity rs) {

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.putAll(rs.getMap());
		obj.put("isParent", "true");// 是不是父节点
		obj.put("level", rs.getData("pid"));// 层级
		obj.put("url", "");// 节点链接地址
		obj.put("order", rs.getData("id"));// 菜单排序
		obj.put("uniqueCode", rs.getData("pid"));// 上下级关联
		obj.put("hidden", "true");// 是否显示

		return obj;
	}

	/**
	 * 获取当前结点的信息
	 * 
	 * @param rs
	 * @return
	 */
	private static Map<String, Object> transStrToZtree(ZtreeEntity rs, List<String> isCheckData) {

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.putAll(rs.getMap());
		obj.put("isParent", "true");// 是不是父节点
		obj.put("level", rs.getData("pid"));// 层级
		obj.put("url", "");// 节点链接地址
		obj.put("order", rs.getData("id"));// 菜单排序
		obj.put("uniqueCode", rs.getData("pid"));// 上下级关联
		obj.put("hidden", "true");// 是否显示
		List<String> result = new ArrayList<String>();// 中间转换集合
		List<String> menu_nodes = List_menu_nodes.get(rs.getData("id").toString());
		if (menu_nodes != null) {
			// 需要中间转换集合
			result.clear();
			result.addAll(isCheckData);
			result.retainAll(menu_nodes); // 交集
		}

		if (isCheckData.contains(rs.getData("permid"))) {
			obj.put("checked", "true");
		} else {
			if (result.size() > 0) {
				obj.put("checked", "true");
			} else {
				obj.put("checked", "false");
			}
		}

		return obj;
	}

	/**
	 * 获取当前节点的信息
	 * 
	 * @param rs
	 *            传递的结果集
	 * @param parm
	 *            附加参数 方便不同树状结构的体现
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unused" })
	private static Map<String, Object> transStrToZtree(ZtreeEntity rs, Map<String, Object> parm) {

		Map<String, Object> obj = new HashMap<String, Object>();
		obj.putAll(rs.getMap());
		obj.put("isParent", "true");// 是不是父节点
		obj.put("level", rs.getData("pid"));// 层级
		obj.put("url", "");// 节点链接地址
		obj.put("order", rs.getData("id"));// 菜单排序
		obj.put("uniqueCode", rs.getData("pid"));// 上下级关联
		obj.put("hidden", "true");// 是否显示
		// 遍历 取附加的属性
		Iterator iter = parm.entrySet().iterator();
		while (iter.hasNext()) {
			Map.Entry entry = (Map.Entry) iter.next();
			Object key = entry.getKey();
			Object val = entry.getValue();
			obj.put(val.toString(), rs.getData(val.toString()));
		}

		return obj;
	}

	/**
	 * 查询当前节点的子节点
	 * 
	 * @param rs
	 *            当前结果集
	 * @param map
	 *            当前节点的id 用于判断是否存在子节点
	 * @return
	 */
	private static ZtreeEntity searchNode(ZtreeEntity rs, Map<String, Object> map) {
		ZtreeEntity rsNext = new ZtreeEntity();
		List<Map<String, Object>> tempDate = new ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> it = rs.getData().iterator();

		while (it.hasNext()) {
			Map<String, Object> n = it.next();
			if (n.get("pid").equals(map.get("id"))) {
				tempDate.add(n);
			}
		}
		rsNext.setData(tempDate);

		return rsNext;
	}

	/**
	 * 查询当前节点的子节点
	 * 
	 * @param rs
	 *            当前结果集
	 * @param map
	 *            当前节点的id 用于判断是否存在子节点
	 * @return
	 */
	private static List<String> search(ZtreeEntity rs, Map<String, Object> map) {
		List<String> rsNext = new ArrayList<String>();
		Iterator<Map<String, Object>> it = rs.getData().iterator();

		while (it.hasNext()) {
			Map<String, Object> n = it.next();
			if (n.get("pid").equals(map.get("id"))) {
				rsNext.add(n.get("permid").toString());
			}
		}
		// rsNext.setData(tempDate);

		return rsNext;
	}

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
		List<Map<String, Object>> p = new ArrayList<Map<String, Object>>();
		Iterator<Map<String, Object>> it = rs.iterator();
		while (it.hasNext()) {
			Map<String, Object> n = it.next();
			if (n.get("pid").toString().equals(id)) {
				if (!Lang.isEmpty(n.get("permid"))) {
					menu_nodes.add(n.get("permid").toString());
				} else {
					p.add(n);
				}
			}
		}
		if (p.size() > 0) {
			for (Map<String, Object> map : p) {
				search(rs, map.get("id").toString());
			}
		}

	}

	/**
	 * 获取每个节点下的所有末级节点，用于权限判断<br>
	 * 
	 * @param root
	 *            说明:集合中必须包含 列名 id pid name 四列 <br>
	 *            集合返回类型为 List[Map[String,Object]]<br>
	 *            列名 |---id---|---pid---|---name---|---permid---...<br>
	 *            列名 |---1 ---|---0 ---|---菜单名称--|---权限标识---
	 *            <p>
	 *            -----------------------------------
	 *            </p>
	 * @return
	 */
	private static Map<String, List<String>> serializeLastTreeNode(List<Map<String, Object>> root) {
		/*
		 * 作为树的载体
		 */
		Map<String, List<String>> tempDate = new HashMap<String, List<String>>();
		Iterator<Map<String, Object>> it = root.iterator();
		while (it.hasNext()) {
			Map<String, Object> n = it.next();
			menu_nodes = new ArrayList<String>();
			search(root, n.get("id").toString());
			tempDate.put(n.get("id").toString(), menu_nodes);

		}
		setList_menu_nodes(tempDate);
		return tempDate;
	}

	/**
	 * 获取 list_menu_nodes
	 * 
	 * @return list_menu_nodes
	 */
	private static Map<String, List<String>> getList_menu_nodes() {
		return List_menu_nodes;
	}

	/**
	 * 设置 list_menu_nodes
	 * 
	 * @param list_menu_nodes
	 */
	private static void setList_menu_nodes(Map<String, List<String>> list_menu_nodes) {
		List_menu_nodes = list_menu_nodes;
	}

	/**此方法作废，代码低效
	 * 根据模块 根据 用户 过滤查看菜单权限
	 * 
	 * @param mod_code
	 *            模块编码
	 * @param user
	 *            用户对象
	 * @param liUserPerm
	 *            用户功能权限
	 * @param liGroupPerm
	 *            角色功能权限
	 * @return MAP集合 key值固定 <br>
	 *         key=root 表示 当前模块下的一级菜单 <br>
	 *         key=data 表示 当前模块下菜单 带子菜单<br>
	 *         key=userPerm 表示 当前用户的功能权限
	 * @throws DataAccessException
	 */
//	public static Map<String, Object> getTreeData(String mod_code, User user, List<String> liUserPerm, List<String> liGroupPerm)
//	        throws DataAccessException {
//
//		// 调用公用类 方法有说明
//		ZtreeEntity root = new ZtreeEntity();
//		ZtreeEntity data = new ZtreeEntity();
//		List<Map<String, Object>> t_root = LoadMenuFile.getList_menu_top_map().get(mod_code);
//		List<Map<String, Object>> t_data = LoadMenuFile.getList_menu_map().get(mod_code);
//		List<String> result = new ArrayList<String>();// 中间转换集合
//
//		List<Map<String, Object>> new_t_root = new ArrayList<Map<String, Object>>();
//		List<Map<String, Object>> new_t_data = new ArrayList<Map<String, Object>>();
//		if (mod_code.equals("00")) {
//			// 进行排序的菜单
//			// List<Map<String, Object>> orderBylist_data =
//			// getOrderByList(t_data);
//			// List<Map<String, Object>> orderBylist_root =
//			// getOrderByList(t_root);
//			List<Map<String, Object>> orderBylist_data = t_data;
//			List<Map<String, Object>> orderBylist_root = t_root;
//			Map<String, Map<String, Object>> mapList = new HashMap<String, Map<String, Object>>();
//			for (Map<String, Object> u : orderBylist_data) {
//				if (u.get("code") != null) {
//					mapList.put(u.get("code").toString(), u);
//				}
//			}
//			Map<String, Map<String, Object>> mapListRoot = new HashMap<String, Map<String, Object>>();
//			for (Map<String, Object> u : orderBylist_root) {
//				if (u.get("code") != null) {
//					mapListRoot.put(u.get("code").toString(), u);
//				}
//			}
//
//			if (user.getUser_code().equals("admin") || user.getType_code() == 3 || user.getType_code() == 4) {
//				if (mapList != null) {
//					// 集团菜单所在位置
//					int group_index = (Integer) mapList.get("groupManager").get("id");
//					// 医院管理所在位置
//					//int hos_index = (Integer) mapList.get("hosManager").get("id");
//
//					int group_index_root = (Integer) mapListRoot.get("hosInfo").get("id");
//					int hos_index_root = (Integer) mapListRoot.get("hosInfo").get("id");
//
//					if (user.getUser_code().equals("admin")) {// 超级管理员
//						new_t_data = new ArrayList<Map<String, Object>>();
//						new_t_root = new ArrayList<Map<String, Object>>();
//						for (int i = 0; i < group_index - 1; i++) {
//							new_t_data.add(orderBylist_data.get(i));
//						}
//						for (Map<String, Object> map : orderBylist_root) {
//							if (group_index_root >= (Integer) map.get("id")) {
//								new_t_root.add(map);
//							}
//						}
//						t_data = new_t_data;
//						t_root = new_t_root;
//					} else if (user.getType_code() == 3) {// 集团管理员
//						new_t_data = new ArrayList<Map<String, Object>>();
//						new_t_root = new ArrayList<Map<String, Object>>();
//						for (int i = group_index - 1; i < orderBylist_root.size(); i++) {
//							new_t_data.add(orderBylist_data.get(i));
//						}
////						for (Map<String, Object> map : orderBylist_root) {
////							if (group_index_root <= (Integer) map.get("id") && hos_index >= (Integer) map.get("id")) {
////								new_t_root.add(map);
////							}
////						}
//						t_data = new_t_data;
//						t_root = new_t_root;
//					} else if (user.getType_code() == 4) {// 医院管理员
//						new_t_data = new ArrayList<Map<String, Object>>();
//						new_t_root = new ArrayList<Map<String, Object>>();
////						for (int i = hos_index - 1; i < orderBylist_data.size(); i++) {
////							new_t_data.add(orderBylist_data.get(i));
////						}
//						for (Map<String, Object> map : orderBylist_root) {
//							if (hos_index_root <= (Integer) map.get("id")) {
//								new_t_root.add(map);
//							}
//						}
//						t_data = new_t_data;
//						t_root = new_t_root;
//					}
//				}
//
//			} else {
//				int hos_info_index = (Integer) mapList.get("hosInfo").get("id");
//				int hos_info_index_root = (Integer) mapListRoot.get("hosInfo").get("id");
//					new_t_data = new ArrayList<Map<String, Object>>();
//					new_t_root = new ArrayList<Map<String, Object>>();
//					for (int i = hos_info_index - 1; i < orderBylist_data.size(); i++) {
//						new_t_data.add(orderBylist_data.get(i));
//					}
//					for (Map<String, Object> map : orderBylist_root) {
//						if (hos_info_index_root <= (Integer) map.get("id")) {
//							new_t_root.add(map);
//						}
//					}
//					t_data = new_t_data;
//					t_root = new_t_root;
//			}
//
//		}
//		if (liUserPerm != null && liUserPerm.size() > 0) {// 功能权限合并，统一放进result里面进行比较验证
//			result.addAll(liUserPerm);
//		}
//		if (liGroupPerm != null && liGroupPerm.size() > 0) {// 功能权限合并，统一放进result里面进行比较验证
//			result.addAll(liGroupPerm);
//		}
//
//		root.setData(t_root == null ? new ArrayList<Map<String, Object>>() : t_root);
//		data.setData(t_data == null ? new ArrayList<Map<String, Object>>() : t_data);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("root", root);
//		map.put("data", data);
//		map.put("userPerm", result == null ? new ArrayList<String>() : result);
//		return map;
//	}

	/**此方法作废，代码低效
	 * 根据模块 根据 用户 过滤查看菜单权限
	 * 
	 * @param mod_code
	 *            模块编码
	 * @param liGroupPerm
	 *            角色功能权限
	 * @return MAP集合 key值固定 <br>
	 *         key=root 表示 当前模块下的一级菜单 <br>
	 *         key=data 表示 当前模块下菜单 带子菜单<br>
	 *         key=userPerm 表示 当前用户的功能权限
	 * @throws DataAccessException
	 */
//	public static Map<String, Object> getTreeData(String mod_code, List<String> liGroupPerm) throws DataAccessException {
//
//		// 调用公用类 方法有说明
//		ZtreeEntity root = new ZtreeEntity();
//		ZtreeEntity data = new ZtreeEntity();
//		List<Map<String, Object>> t_root = LoadMenuFile.getList_menu_top_map().get(mod_code);
//		List<Map<String, Object>> t_data = LoadMenuFile.getList_menu_map().get(mod_code);
//		List<String> result = new ArrayList<String>();// 中间转换集合
//
//		List<Map<String, Object>> new_t_root = new ArrayList<Map<String, Object>>();
//		List<Map<String, Object>> new_t_data = new ArrayList<Map<String, Object>>();
//		if (mod_code.equals("00")) {
//			// 进行排序的菜单
//			// List<Map<String, Object>> orderBylist_data =
//			// getOrderByList(t_data);
//			// List<Map<String, Object>> orderBylist_root =
//			// getOrderByList(t_root);
//			List<Map<String, Object>> orderBylist_data = t_data;
//			List<Map<String, Object>> orderBylist_root = t_root;
//			Map<String, Map<String, Object>> mapList = new HashMap<String, Map<String, Object>>();
//			for (Map<String, Object> u : orderBylist_data) {
//				if (u.get("code") != null) {
//					mapList.put(u.get("code").toString(), u);
//				}
//			}
//			Map<String, Map<String, Object>> mapListRoot = new HashMap<String, Map<String, Object>>();
//			for (Map<String, Object> u : orderBylist_root) {
//				if (u.get("code") != null) {
//					mapListRoot.put(u.get("code").toString(), u);
//				}
//			}
//			int hos_info_index = (Integer) mapList.get("hosInfo").get("id");
//			int hos_info_index_root = (Integer) mapListRoot.get("hosInfo").get("id");
//			new_t_data = new ArrayList<Map<String, Object>>();
//			new_t_root = new ArrayList<Map<String, Object>>();
//			for (int i = hos_info_index - 1; i < orderBylist_data.size(); i++) {
//				new_t_data.add(orderBylist_data.get(i));
//			}
//			for (Map<String, Object> map : orderBylist_root) {
//				if (hos_info_index_root <= (Integer) map.get("id")) {
//					new_t_root.add(map);
//				}
//			}
//			t_data = new_t_data;
//			t_root = new_t_root;
//
//		}
//		if (liGroupPerm != null && liGroupPerm.size() > 0) {// 功能权限合并，统一放进result里面进行比较验证
//			result.addAll(liGroupPerm);
//		}
//		root.setData(t_root == null ? new ArrayList<Map<String, Object>>() : t_root);
//		data.setData(t_data == null ? new ArrayList<Map<String, Object>>() : t_data);
//		Map<String, Object> map = new HashMap<String, Object>();
//		map.put("root", root);
//		map.put("data", data);
//		map.put("userPerm", result == null ? new ArrayList<String>() : result);
//		return map;
//	}

	/*
	 * 按照id从小到大的顺序排序。
	 */
	private static List<Map<String, Object>> getOrderByList(List<Map<String, Object>> li) {
		Collections.sort(li, new Comparator<Map<String, Object>>() {

			public int compare(Map<String, Object> arg0, Map<String, Object> arg1) {
				int hits0 = (Integer) arg0.get("id");
				int hits1 = (Integer) arg1.get("id");
				if (hits1 < hits0) {
					return 1;
				} else if (hits1 == hits0) {
					return 0;
				} else {
					return -1;
				}
			}
		});
		return li;
	}

}
