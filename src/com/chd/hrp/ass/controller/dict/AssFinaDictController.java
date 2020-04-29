/**
 * @Description:
 * @Copyright: Copyright (c) 2015-9-16 下午9:54:34
 * @Company: 智慧云康（北京）数据科技有限公司
 * @网站：www.s-chd.com
 */
package com.chd.hrp.ass.controller.dict;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.nutz.lang.Strings;
import org.springframework.dao.DataAccessException;
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
import com.chd.hrp.ass.entity.dict.AssFinaDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssFinaDictService;

/**
 * @Description: 050101 财务分类字典
 * @Table: ASS_FINA_TYPE
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class AssFinaDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssFinaDictController.class);

	// 引入Service服务
	@Resource(name = "assFinaDictService")
	private final AssFinaDictService assFinaDictService = null;

	// 引入Service服务
	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/assFinaDictMainPage", method = RequestMethod.GET)
	public String assFinaDictMainPage(Model mode) throws Exception {
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_FINA_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		int first_length =0;
		if(level_length!=null){
			// 获取第一级长度
			 first_length = (Integer) level_length.get(1);
			
		}
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);
		return "hrp/ass/assfinadict/assFinaDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/assFinaDictAddPage", method = RequestMethod.GET)
	public String assFinaDictAddPage(Model mode) throws Exception {
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
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_FINA_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		int first_length =0;
		if(level_length!=null){
			// 获取第一级长度
			 first_length = (Integer) level_length.get(1);
			
		}
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);

		return "hrp/ass/assfinadict/assFinaDictAdd";

	}

	/**
	 * @Description 添加数据 050101 财务分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/addAssFinaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssFinaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String assFinaDictJson = "";
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fina_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fina_type_name").toString()));
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_FINA_DICT");
		mapVo.put("mod_code", "05");

		String fina_type_code = mapVo.get("fina_type_code").toString();

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		if (null != mapVo.get("fina_type_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> rules_level_length = (Map<Object, Object>) rules.get("rules_level_length");
			Object level = level_map.get(fina_type_code.length());

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			
			//当第一级为0时 不验证规则
			if(!rules_level_length.get(1).toString().equals("0")){
				
				if (null != level) {
					int int_level = (Integer) level;
					mapVo.put("type_level", level);
					if (int_level == 1) {
						mapVo.put("type_level", level);
						mapVo.put("super_code", "");
					} else {
						mapVo.put("type_level", level);
						int v_level = int_level - 1;
						int end = (Integer) rules_level_length.get(v_level);
						mapVo.put("super_code", fina_type_code.substring(0, end));
					}
				} else {
					assFinaDictJson = "{\"error\":\"编码不符合要求,请重新添加.编码规则：" + s_view + "\"}";
					return JSONObject.parseObject(assFinaDictJson);
				}
				
			}
			
			

		}

		try{
		assFinaDictJson = assFinaDictService.addAssFinaDict(mapVo);
		}catch(Exception e){
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		return JSONObject.parseObject(assFinaDictJson);

	}

	/**
	 * @Description 更新跳转页面 050101 财务分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/assFinaDictUpdatePage", method = RequestMethod.GET)
	public String assFinaDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}

		AssFinaDict AssFinaDict = new AssFinaDict();

		AssFinaDict = assFinaDictService.queryAssFinaDictByCode(mapVo);

		mode.addAttribute("group_id", AssFinaDict.getGroup_id());
		mode.addAttribute("hos_id", AssFinaDict.getHos_id());
		mode.addAttribute("copy_code", AssFinaDict.getCopy_code());
		mode.addAttribute("fina_type_code", AssFinaDict.getFina_type_code());
		mode.addAttribute("fina_type_name", AssFinaDict.getFina_type_name());
		mode.addAttribute("super_code", AssFinaDict.getSuper_code());

		mode.addAttribute("spell_code", AssFinaDict.getSpell_code());
		mode.addAttribute("wbx_code", AssFinaDict.getWbx_code());
		mode.addAttribute("is_last", AssFinaDict.getIs_last());
		mode.addAttribute("type_level", AssFinaDict.getType_level());
		mode.addAttribute("is_stop", AssFinaDict.getIs_stop());
		mode.addAttribute("is_budg", AssFinaDict.getIs_budg());
		
		//是否停用字段，当分类被引用时，不允许修改，是否末级分类字段， 当分类被引用时，不允许修改。zhaon
		String fina_type_code = mapVo.get("fina_type_code").toString();
		Boolean isDisabled = false;
		//Boolean lastIsDisabled = false;
		String str = assBaseService.isExistsDataByTable("ASS_FINA_DICT", fina_type_code);
		if (Strings.isNotBlank(str)) {
			isDisabled = true;
		}
		
		/*mapVo.put("fina_type_code", AssFinaDict.getFina_type_code());
		List<AssFinaDict> child = assFinaDictService.queryAssFinaDictChild(mapVo);
		if (child ==null || child.size()==0) {
			lastIsDisabled = true;
		}*/
		
		mode.addAttribute("isDisabled",isDisabled);
		//mode.addAttribute("lastIsDisabled",lastIsDisabled);

		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_FINA_DICT");
		mapVo.put("mod_code", "05");
		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
		Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
		String rules_v = rules.get("rules_view").toString();
		String rules_view = Strings.removeFirst(rules_v);
		int first_length =0;
		if(level_length!=null){
			// 获取第一级长度
			 first_length = (Integer) level_length.get(1);
			
		}
		// 显示编码规则
		mode.addAttribute("rules_view", rules_view);
		mode.addAttribute("first_length", first_length);
		return "hrp/ass/assfinadict/assFinaDictUpdate";

	}

	/**
	 * @Description 更新数据 050101 财务分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/updateAssFinaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssFinaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String AssFinaDictJson = "" ;
				
		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		
		//存在下级分类时，不允许修改 zhaon
		String retErrot;
		List<AssFinaDict> child = assFinaDictService.queryAssFinaDictChild(mapVo);
		if (child !=null && child.size()>0) {
			retErrot = "{\"error\":\"存在下级分类，不允许修改\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fina_type_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fina_type_name").toString()));
		// 添加编码规则判断
		mapVo.put("proj_code", "ASS_FINA_DICT");
		mapVo.put("mod_code", "05");

		String fina_type_code = mapVo.get("fina_type_code").toString();

		Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
		if (null != mapVo.get("fina_type_code")) {
			Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
			Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
			Object level = level_map.get(fina_type_code.length());

			String rules_v = rules.get("rules_view").toString();
			String s_view = Strings.removeFirst(rules_v);
			
			//当第一级为0时 不验证规则
			if(!level_length.get(1).toString().equals("0")){
			
			if (null != level) {
				int int_level = (Integer) level;
				mapVo.put("type_level", level);
				mapVo.put("super_code", "");
				if (int_level == 1) {
					mapVo.put("type_level", level);
					mapVo.put("super_code", "");
				} else {
					mapVo.put("type_level", level);
					int v_level = int_level - 1;
					int end = (Integer) level_length.get(v_level);
					mapVo.put("super_code", fina_type_code.substring(0, end));
				}
			}
			}

		}
		try{
		 
			AssFinaDictJson = assFinaDictService.updateAssFinaDict(mapVo);
		
		}catch(Exception e){
			
			return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
		}
		
		return JSONObject.parseObject(AssFinaDictJson);
	}

	/**
	 * @Description 删除数据 050101 财务分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/deleteAssFinaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssFinaDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		String AssFinaDictJson = "";
		String retErrot = "";
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			String str = assBaseService.isExistsDataByTable("ASS_FINA_DICT", ids[3]);

			if (Strings.isNotBlank(str)) {
				retErrot = "{\"error\":\"删除失败，选择的资产类别被以下业务使用：【" + str.substring(0, str.length() - 1) + "】。\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("fina_type_code", ids[3]);
			
			//存在下级分类时，不允许修改 zhaon
			List<AssFinaDict> child = assFinaDictService.queryAssFinaDictChild(mapVo);
			if (child !=null && child.size()>0) {
				retErrot = "{\"error\":\"存在下级分类，请先删除下级\",\"state\":\"false\"}";
				return JSONObject.parseObject(retErrot);
			}

			try{
			AssFinaDictJson = assFinaDictService.deleteAssFinaDict(mapVo);
			// listVo.add(mapVo);
			}catch(Exception e){
				return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
			}
		}

		// String AssFinaDictJson =
		// AssFinaDictService.deleteBatchAssFinaDict(listVo);

		return JSONObject.parseObject(AssFinaDictJson);

	}

	/**
	 * @Description 查询数据 050101 财务分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/queryAssFinaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssFinaDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		String AssFinaDict = assFinaDictService.queryAssFinaDict(getPage(mapVo));

		return JSONObject.parseObject(AssFinaDict);

	}

	/**
	 * @Description 下载导入模版 050101 财务分类字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assfinadict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode) throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "财务分类字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050101 财务分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/assFinaDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String readAssFinaDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		try {
			
			String reJson=assFinaDictService.readAssFinaDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	}
		

	/**
	 * @Description 批量添加数据 050101 财务分类字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/addBatchAssFinaDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssFinaDict(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		Map<String, Object> is_last_map = new HashMap<String, Object>();
		is_last_map.put("是", "1");
		is_last_map.put("否", "0");
		is_last_map.put("1", "1");
		is_last_map.put("0", "0");
		List<AssFinaDict> list_err = new ArrayList<AssFinaDict>();

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

		Iterator it = json.iterator();

		try {

			while (it.hasNext()) {

				StringBuffer err_sb = new StringBuffer();

				AssFinaDict AssFinaDict = new AssFinaDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("fina_type_code"))) {

					AssFinaDict.setFina_type_code((String) jsonObj.get("fina_type_code"));

					mapVo.put("fina_type_code", jsonObj.get("fina_type_code"));

				} else {

					err_sb.append("分类编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("fina_type_name"))) {

					AssFinaDict.setFina_type_name((String) jsonObj.get("fina_type_name"));

					mapVo.put("fina_type_name", jsonObj.get("fina_type_name"));

				} else {

					err_sb.append("分类名称为空  ");

				}
				
				if (StringTool.isNotBlank(is_last_map.get(jsonObj.get("is_last").toString()))) {

					AssFinaDict.setIs_last(Integer.valueOf(is_last_map.get(jsonObj.get("is_last").toString()).toString()));

					mapVo.put("is_last", jsonObj.get("is_last"));

				} else {

					err_sb.append("是否末级分类为空或者不符合要求  ");

				}
				
				if (StringTool.isNotBlank(is_last_map.get(jsonObj.get("is_stop").toString()))) {

					AssFinaDict.setIs_stop(Integer.valueOf(is_last_map.get(jsonObj.get("is_stop").toString()).toString()));

					mapVo.put("is_stop", jsonObj.get("is_stop"));

				} else {

					err_sb.append("是否停用为空  ");

				}
				if (StringTool.isNotBlank(is_last_map.get(jsonObj.get("is_budg").toString()))) {
					
					AssFinaDict.setIs_budg(Integer.valueOf(is_last_map.get(jsonObj.get("is_budg").toString()).toString()));
					
					mapVo.put("is_budg", jsonObj.get("is_budg"));
					
				} else {
					
					err_sb.append("是否预算为空  ");
					
				}

				
				// 添加编码规则判断
				mapVo.put("proj_code", "ASS_FINA_DICT");
				mapVo.put("mod_code", "05");

				String fina_type_code = mapVo.get("fina_type_code").toString();

				Map<Object, Object> rules = assBaseService.getHosRules(mapVo);
				if (null != mapVo.get("fina_type_code")) {
					Map<Object, Object> level_map = (Map<Object, Object>) rules.get("rules_type_level");
					Map<Object, Object> level_length = (Map<Object, Object>) rules.get("rules_level_length");
					Object level = level_map.get(fina_type_code.length());

					String rules_v = rules.get("rules_view").toString();
					String s_view = Strings.removeFirst(rules_v);
					if (null != level) {
						int int_level = (Integer) level;
						mapVo.put("type_level", level);
						mapVo.put("super_code", "");
						if (int_level == 1) {
							mapVo.put("type_level", level);
							mapVo.put("super_code", "");
						} else {
							mapVo.put("type_level", level);
							int v_level = int_level - 1;
							int end = (Integer) level_length.get(v_level);
							mapVo.put("super_code", fina_type_code.substring(0, end));
						}
					}

				}

				
				Map<String, Object> map_code = new HashMap<String, Object>();
				map_code.put("group_id", mapVo.get("group_id"));
				map_code.put("hos_id", mapVo.get("hos_id"));
				map_code.put("copy_code", mapVo.get("copy_code"));
				map_code.put("fina_type_code", mapVo.get("fina_type_code"));

				AssFinaDict data_exc_extis_code = assFinaDictService.queryAssFinaDictByUniqueness(map_code);

				if (data_exc_extis_code != null) {

					err_sb.append("编码已经存在！ ");

				}
				Map<String, Object> map_name = new HashMap<String, Object>();
				map_name.put("group_id", mapVo.get("group_id"));
				map_name.put("hos_id", mapVo.get("hos_id"));
				map_name.put("copy_code", mapVo.get("copy_code"));
				map_name.put("fina_type_code", mapVo.get("fina_type_code"));

				AssFinaDict data_exc_extis_name = assFinaDictService.queryAssFinaDictByUniqueness(map_name);

				if (data_exc_extis_name != null) {

					err_sb.append("名称已经存在！ ");

				}

				// 判断上级编码是否为空 不为空则反查上级编码所属资产性质
				if (StringTool.isNotBlank(mapVo.get("super_code"))) {
					Map<String, Object> map_super = new HashMap<String, Object>();
					map_super.put("group_id", mapVo.get("group_id"));
					map_super.put("hos_id", mapVo.get("hos_id"));
					map_super.put("copy_code", mapVo.get("copy_code"));
					map_super.put("fina_type_code", mapVo.get("super_code"));

					AssFinaDict data_exc_extis_super = assFinaDictService.queryAssFinaDictByUniqueness(map_super);

					if (null != data_exc_extis_super) {

						// 判断上级编码是否为末级
						if (data_exc_extis_super.getIs_last() == 1) {
							Map<String, Object> update_is_last = new HashMap<String, Object>();
							update_is_last.put("group_id", mapVo.get("group_id"));
							update_is_last.put("hos_id", mapVo.get("hos_id"));
							update_is_last.put("copy_code", mapVo.get("copy_code"));
							update_is_last.put("fina_type_code", data_exc_extis_super.getFina_type_code());
							update_is_last.put("is_last", "0");
							update_is_last.put("super_code", data_exc_extis_super.getSuper_code());
							
							assFinaDictService.updateAssFinaDict(update_is_last);
						}
					} else {
						err_sb.append("上级编码不存在或者！ ");
					}
				}

				if (err_sb.toString().length() > 0) {

					AssFinaDict.setError_type(err_sb.toString());

					list_err.add(AssFinaDict);

				} else {

					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("fina_type_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("fina_type_name").toString()));

					try{
					String dataJson = assFinaDictService.addAssFinaDict(mapVo);
					}catch(Exception e){
						return JSONObject.parseObject("{\"error\":\""+e.getMessage()+" \"}");
					}
				}

			}

		}
		catch (DataAccessException e) {

			AssFinaDict data_exc = new AssFinaDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

	/**
	 * @Description 查询数据 050101 财务分类字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assfinadict/queryAssFinaDictByTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssFinaDictByTree(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		if (mapVo.get("acct_year") == null) {

			mapVo.put("acct_year", SessionManager.getAcctYear());

		}
		List<?> l_map = assFinaDictService.queryAssFinaDictByTree(mapVo);
		return JSONObject.parseObject(ChdJson.toJson(l_map));
	}
}
