/**
* @Copyright: Copyright (c) 2015-2-14 
* @Company: 鏅烘収浜戝悍锛堝寳浜級鏁版嵁绉戞妧鏈夐檺鍏徃
*/
package com.chd.hrp.sys.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.ChdJson;
import com.chd.base.util.Plupload;
import com.chd.base.util.StringTool;
import com.chd.base.util.UploadUtil;
import com.chd.hrp.sys.dao.notGeneral.SysFunUtilMapper;
import com.chd.hrp.sys.entity.Store;
import com.chd.hrp.sys.entity.StoreType;
import com.chd.hrp.sys.service.base.SysBaseService;
import com.chd.hrp.sys.serviceImpl.StoreDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreServiceImpl;
import com.chd.hrp.sys.serviceImpl.StoreTypeServiceImpl;

/**
* @Title. @Description.
* @Author: LiuYingDuo
* @email: bell@s-chd.com
* @Version: 1.0
*/


@Controller
public class StoreController extends BaseController{
	private static Logger logger = Logger.getLogger(StoreController.class);
	
	
	@Resource(name = "storeService")
	private final StoreServiceImpl storeService = null;
   
	@Resource(name = "storeDictService")
	private final StoreDictServiceImpl storeDictService = null;
	
	@Resource(name = "storeTypeService")
	private final StoreTypeServiceImpl storeTypeService = null;
	
	// 寮曞叆Service鏈嶅姟
	@Resource(name = "sysBaseService")
	private final SysBaseService sysBaseService = null;
	
	@Resource(name = "sysFunUtilMapper")
	private final SysFunUtilMapper sysFunUtilMapper = null;
	
	// 缁存姢椤甸潰璺宠浆
	@RequestMapping(value = "/hrp/sys/store/storeMainPage", method = RequestMethod.GET)
	public String storeMainPage(Model mode) throws Exception {

		return "hrp/sys/store/storeMain";

	}

	// 娣诲姞椤甸潰
	@RequestMapping(value = "/hrp/sys/store/storeAddPage", method = RequestMethod.GET)
	public String storeAddPage(Model mode) throws Exception {

		return "hrp/sys/store/storeAdd";

	}
	
	//鍙樻洿椤甸潰
	@RequestMapping(value = "/hrp/sys/store/storeChangePage", method = RequestMethod.GET)
	public String storeChangePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Store store = new Store();
		store = storeService.queryStoreByCode(mapVo);
		mode.addAttribute("group_id", store.getGroup_id());
		mode.addAttribute("hos_id", store.getHos_id());
		mode.addAttribute("store_id", store.getStore_id());
		mode.addAttribute("store_code", store.getStore_code());
		mode.addAttribute("type_code", store.getType_code());
		mode.addAttribute("type_name", store.getType_name());
		mode.addAttribute("store_name", store.getStore_name());
		mode.addAttribute("spell_code", store.getSpell_code());
		mode.addAttribute("wbx_code", store.getWbx_code());
		mode.addAttribute("is_stop", store.getIs_stop());
		mode.addAttribute("note", store.getNote());
		
		return "hrp/sys/store/storeChange";
	}

	// 淇濆瓨
	@RequestMapping(value = "/hrp/sys/store/addStore", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String storeJson = storeService.addStore(mapVo);

		return JSONObject.parseObject(storeJson);
		
	}
	
	@RequestMapping(value = "/hrp/sys/store/addStoreDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addStoreDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		
		String storeJson = storeDictService.addStoreDict(mapVo);

		return JSONObject.parseObject(storeJson);
		
	}

	// 鏌ヨ
	@RequestMapping(value = "/hrp/sys/store/queryStore", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		//不同系统过滤筛选出相应系统的仓库
		String modCode = SessionManager.getModCode();//获取系统编码
		//物流管理系统modCode=04
		//耐用品管理modCode=0413
		if(modCode!=null && modCode.startsWith("04")) {
			mapVo.put("is_mat", "1");
		}
		//固定资产管理系统modCode=05
		if(modCode!=null && modCode.startsWith("05")) {
			mapVo.put("is_ass", "1");
		}
		//药品管理系统modCode=08
		if(modCode!=null && modCode.startsWith("08")) {
			mapVo.put("is_med", "1");
		}
		//供应商平台系统modCode=10
		if(modCode!=null && modCode.startsWith("10")) {
			mapVo.put("is_sup", "1");
		}		
				
		
		String store = storeService.queryStore(getPage(mapVo));
		return JSONObject.parseObject(store);
	}
	
	//鍒犻櫎
	@RequestMapping(value = "/hrp/sys/store/deleteStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteStore(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String storeJson = "";
		String str = "";
		boolean falg = true;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			mapVo.put("group_id", id.split("@")[0]);
			mapVo.put("hos_id", id.split("@")[1]);
			mapVo.put("store_id", id.split("@")[2]);
            mapVo.put("store_code", id.split("@")[3]);
            
            str = str + sysBaseService.isExistsDataByTable("HOS_STORE", id.split("@")[2])== null ? ""
					: sysBaseService.isExistsDataByTable("HOS_STORE", id.split("@")[2]);
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			
            listVo.add(mapVo);
        }
		
		if (!falg) {
			return JSONObject.parseObject("{\"error\":\"删除失败，选择的仓库被以下业务使用：【" + str.substring(0, str.length() - 1)
			+ "】。\",\"state\":\"false\"}");
		}
		
		storeJson = storeService.deleteBatchStore(listVo);
	   return JSONObject.parseObject(storeJson);	
	}
	
	
	// 淇敼椤甸潰璺宠浆
	@RequestMapping(value = "/hrp/sys/store/storeUpdatePage", method = RequestMethod.GET)
	
	public String storeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
        Store store = new Store();
		store = storeService.queryStoreByCode(mapVo);
		mode.addAttribute("group_id", store.getGroup_id());
		mode.addAttribute("hos_id", store.getHos_id());
		mode.addAttribute("store_id", store.getStore_id());
		mode.addAttribute("store_code", store.getStore_code());
		mode.addAttribute("type_code", store.getType_code());
		mode.addAttribute("type_name", store.getType_name());
		mode.addAttribute("store_name", store.getStore_name());
		mode.addAttribute("sort_code", store.getSort_code());
		mode.addAttribute("spell_code", store.getSpell_code());
		mode.addAttribute("wbx_code", store.getWbx_code());
		mode.addAttribute("is_stop", store.getIs_stop());
		mode.addAttribute("note", store.getNote());
		mode.addAttribute("is_mat", store.getIs_mat());
		mode.addAttribute("is_ass", store.getIs_ass());
		mode.addAttribute("is_med", store.getIs_med());
		mode.addAttribute("is_sup", store.getIs_sup());
		
		/*mode.addAttribute("matInvDict", store);*/
		return "hrp/sys/store/storeUpdate";
	}
		
	// 淇敼淇濆瓨
	@RequestMapping(value = "/hrp/sys/store/updateStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String storeJson = storeService.updateStore(mapVo);
		
		return JSONObject.parseObject(storeJson);
	}
	
	// 瀵煎叆
	@RequestMapping(value = "/hrp/sys/store/importStore", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importStore(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String storeJson = storeService.importStore(mapVo);
		
		return JSONObject.parseObject(storeJson);
	}

	// 瀵煎叆椤甸潰
		@RequestMapping(value = "/hrp/sys/store/sysStoreImportPage", method = RequestMethod.GET)
		public String sysFacImportPage(Model mode) throws Exception {

			return "hrp/sys/store/storeImport";

		}

		// 涓嬭浇瀵煎叆妯＄増
		@RequestMapping(value = "hrp/sys/store/downTemplate", method = RequestMethod.GET)
		public String downTemplate(Plupload plupload, HttpServletRequest request,
				HttpServletResponse response, Model mode) throws IOException {
			printTemplate(request, response, "sys\\医院信息", "库房.xls");
			return null;
		}

		/**
		 * 瀵煎叆搴撴埧淇℃伅<BR>
		 * 
		 */
		@RequestMapping(value = "/hrp/sys/store/readSysStoreFiles", method = RequestMethod.POST)
		public String readSysStoreFiles(Plupload plupload,
				HttpServletRequest request, HttpServletResponse response, Model mode)
				throws IOException {

			Map<String, Object> entityMap = new HashMap<String, Object>();

			entityMap.put("group_id", SessionManager.getGroupId());

			entityMap.put("hos_id", SessionManager.getHosId());

			entityMap.put("copy_code", SessionManager.getCopyCode());

			entityMap.put("proj_code", "HOS_STORE");
			
			entityMap.put("mod_code", "00");

			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);

			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules
					.get("rules_level_length");

			String rules_v = rules.get("rules_view").toString();

			String s_view = Strings.removeFirst(rules_v);

			List<Store> list_err = new ArrayList<Store>();

			List<Map<String, Object>> aList = new ArrayList<Map<String, Object>>();

			List<String[]> list = UploadUtil.readFile(plupload, request, response);

			try {

				for (int i = 1; i < list.size(); i++) {

					StringBuffer err_sb = new StringBuffer();

					Store store = new Store();

					String temp[] = list.get(i);// 琛�

					Map<String, Object> mapVo = new HashMap<String, Object>();

					if (mapVo.get("group_id") == null) {

						mapVo.put("group_id", SessionManager.getGroupId());

					}
					if (mapVo.get("hos_id") == null) {

						mapVo.put("hos_id", SessionManager.getHosId());

					}

					if (mapVo.get("copy_code") == null) {

						mapVo.put("copy_code", SessionManager.getCopyCode());

					}

					if (StringUtils.isNotEmpty(temp[0])) {

						Object store_code = temp[0].length();

						if (store_code != rules_level_length.get(1)) {

							err_sb.append("缂栫爜涓嶇鍚堣姹�,璇烽噸鏂版坊鍔�,缂栫爜瑙勫垯闀垮害锛�" + s_view + "");
						}

						mapVo.put("store_code", temp[0]);

						store.setStore_code(temp[0]);

					} else {

						err_sb.append("搴撴埧缂栫爜涓嶈兘涓虹┖锛�");
					}

					if (StringUtils.isNotEmpty(temp[1])) {

						mapVo.put("store_name", temp[1]);

						store.setStore_name(temp[1]);

					} else {

						err_sb.append("搴撴埧鍚嶇О涓嶈兘涓虹┖!");
					}

					if (StringUtils.isNotEmpty(temp[2])) {

						Map<String, Object> kindMap = new HashMap<String, Object>();

						kindMap.put("group_id", SessionManager.getGroupId());

						kindMap.put("hos_id", SessionManager.getHosId());

						kindMap.put("type_code", temp[2]);

						StoreType storeType = storeTypeService.queryStoreTypeByCode(kindMap);

						if (storeType == null) {

							err_sb.append("搴撴埧鍒嗙被缂栫爜涓嶅瓨鍦�!");
						}
						
						mapVo.put("type_code", temp[2]);
						
						store.setType_code(temp[2]);

					} else {

						err_sb.append("搴撴埧鍒嗙被缂栫爜涓嶈兘涓虹┖!");
					}

					if (StringUtils.isNotEmpty(temp[3])) {

						mapVo.put("type_name", temp[3]);

						store.setType_name(temp[3]);

					} else {

						err_sb.append("搴撴埧鍒嗙被鍚嶇О涓嶈兘涓虹┖!");
					}


					/* 澶囨敞榛樿鍙互涓虹┖ */
					if (StringUtils.isNotEmpty(temp[4])) {

						mapVo.put("note", temp[4]);
						
						store.setNote(temp[4]);

					} else {

						mapVo.put("note", "");
						
						store.setNote("");
					}
					
					if (StringUtils.isNotEmpty(temp[5])) {

						mapVo.put("is_stop", temp[5]);

						store.setIs_stop(Integer.parseInt(temp[5].toString()));

					} else {

						err_sb.append("鏄惁鍋滅敤涓嶈兘涓虹┖!");
					}
					
					Map<String, Object> utilMap = new HashMap<String, Object>();
					
					utilMap.put("group_id", entityMap.get("group_id"));
					
					utilMap.put("hos_id", entityMap.get("hos_id"));
					
					utilMap.put("copy_code", "");
					
					utilMap.put("field_table", "HOS_STORE");
					
					utilMap.put("field_key1", "");
					
					utilMap.put("field_value1", "");
					
					utilMap.put("field_key2", "");
					
					utilMap.put("field_value2", "");

					utilMap.remove("field_key2");
					
				    utilMap.put("field_sort", "sort_code");
				    
					int count = sysFunUtilMapper.querySysMaxSort(utilMap);
					
					mapVo.put("sort_code", count);
					



					Store store2 = storeService.queryStoreByCode(mapVo);

					if (store2 != null) {

						err_sb.append("搴撴埧缂栫爜宸插瓨鍦�!");

					}

					if (err_sb.toString().length() > 0) {

						store.setError_type(err_sb.toString());

						list_err.add(store);

					} else {

						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("store_name").toString()));

						mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("store_name").toString()));

						storeService.addImportStore(mapVo);

					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				Store data_exc = new Store();

				data_exc.setError_type("瀵煎叆绯荤粺鍑洪敊");

				list_err.add(data_exc);
			}

			response.getWriter().print(ChdJson.toJson(list_err, list_err.size()));

			return null;
		}

		/**
		 * 
		 * 鎵归噺娣诲姞
		 */
		@RequestMapping(value = "/hrp/sys/store/addBatchSysStore", method = RequestMethod.POST)
		@ResponseBody
		public Map<String, Object> addBatchSysStore(
				@RequestParam(value = "ParamVo") String paramVo, Model mode)
				throws Exception {

			Map<String, Object> entityMap = new HashMap<String, Object>();

			entityMap.put("group_id", SessionManager.getGroupId());

			entityMap.put("hos_id", SessionManager.getHosId());

			entityMap.put("copy_code", SessionManager.getCopyCode());

			entityMap.put("proj_code", "HOS_STORE");
			
			entityMap.put("mod_code", "00");

			Map<Object, Object> rules = sysBaseService.getHosRules(entityMap);

			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");

			String rules_v = rules.get("rules_view").toString();

			String s_view = Strings.removeFirst(rules_v);

			List<Store> list_err = new ArrayList<Store>();

			JSONArray json = JSONArray.parseArray(paramVo);

			Map<String, Object> mapVo = new HashMap<String, Object>();

			if (mapVo.get("group_id") == null) {

				mapVo.put("group_id", SessionManager.getGroupId());

			}

			if (mapVo.get("hos_id") == null) {

				mapVo.put("hos_id", SessionManager.getHosId());

			}

			if (mapVo.get("copy_code") == null) {

				mapVo.put("copy_code", SessionManager.getCopyCode());

			}

			String s = null;

			Iterator it = json.iterator();

			try {

				while (it.hasNext()) {

					StringBuffer err_sb = new StringBuffer();

					JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

					Store store = new Store();

					mapVo.put("store_code", jsonObj.get("store_code").toString());

					mapVo.put("store_name", jsonObj.get("store_name").toString());

					mapVo.put("type_code", jsonObj.get("type_code").toString());

					mapVo.put("type_name", jsonObj.get("type_name").toString());

					mapVo.put("is_stop",Integer.parseInt(jsonObj.get("is_stop").toString()));

					mapVo.put("note", jsonObj.get("note").toString());

					Object store_code = mapVo.get("store_code").toString().length();

					if (store_code != rules_level_length.get(1)) {

						err_sb.append("缂栫爜涓嶇鍚堣姹�,璇烽噸鏂版坊鍔�,缂栫爜瑙勫垯闀垮害锛�" + s_view + "");
					}

					StoreType storeType = storeTypeService.queryStoreTypeByCode(mapVo);

					if (storeType == null) {

						err_sb.append("搴撴埧鍒嗙被缂栫爜涓嶅瓨鍦�!");

					}
					
					Map<String, Object> storeMap = new HashMap<String, Object>();
					
					storeMap.put("group_id", mapVo.get("group_id"));
					
					storeMap.put("hos_id", mapVo.get("hos_id"));
					
					storeMap.put("store_code", mapVo.get("store_code"));

					Store store2 = storeService.queryStoreByCode(mapVo);

					if (store2 != null) {

						err_sb.append("缂栫爜宸插瓨鍦�!");

					}

                   Map<String, Object> utilMap = new HashMap<String, Object>();
					
					utilMap.put("group_id", entityMap.get("group_id"));
					
					utilMap.put("hos_id", entityMap.get("hos_id"));
					
					utilMap.put("copy_code", "");
					
					utilMap.put("field_table", "HOS_STORE");
					
					utilMap.put("field_key1", "");
					
					utilMap.put("field_value1", "");
					
					utilMap.put("field_key2", "");
					
					utilMap.put("field_value2", "");

					utilMap.remove("field_key2");
					
				    utilMap.put("field_sort", "sort_code");
				    
					int count = sysFunUtilMapper.querySysMaxSort(utilMap);
					
					mapVo.put("sort_code", count);

					if (err_sb.toString().length() > 0) {

						store.setStore_code(mapVo.get("store_code").toString());

						store.setStore_name(mapVo.get("store_name").toString());

						store.setType_code(mapVo.get("type_code").toString());

						store.setType_name(mapVo.get("type_name").toString());

						store.setIs_stop(Integer.parseInt(mapVo.get("is_stop").toString()));

						store.setNote(mapVo.get("note").toString());
						
						store.setError_type(err_sb.toString());
						
						list_err.add(store);

					} else {

						mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("store_code").toString()));

						mapVo.put("wbx_code",StringTool.toWuBi(mapVo.get("store_code").toString()));
						
						storeService.addImportStore(mapVo);
					}
				}

			} catch (Exception e) {
				// TODO: handle exception
				return JSONObject
						.parseObject("{\"msg\":\"瀵煎叆绯荤粺鍑洪敊,璇烽噸鏂板鍏�.\",\"state\":\"err\"}");
			}

			if (list_err.size() > 0) {

				return JSONObject.parseObject(ChdJson.toJson(list_err,
						list_err.size()));

			} else {

				return JSONObject
						.parseObject("{\"msg\":\"瀵煎叆鎴愬姛.\",\"state\":\"true\"}");

			}
		}
}

