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
import com.chd.hrp.ass.entity.dict.AssDepreMethodDict;
import com.chd.hrp.ass.entity.dict.AssDict;
import com.chd.hrp.ass.entity.dict.AssTypeDict;
import com.chd.hrp.ass.entity.dict.AssUsageDict;
import com.chd.hrp.ass.service.base.AssBaseService;
import com.chd.hrp.ass.service.dict.AssAcceptItemAffiService;
import com.chd.hrp.ass.service.dict.AssAcceptItemDictService;
import com.chd.hrp.ass.service.dict.AssCheckItemAffiService;
import com.chd.hrp.ass.service.dict.AssCheckItemDictService;
import com.chd.hrp.ass.service.dict.AssDepreMethodDictService;
import com.chd.hrp.ass.service.dict.AssDictService;
import com.chd.hrp.ass.service.dict.AssNoDictService;
import com.chd.hrp.ass.service.dict.AssTypeDictService;
import com.chd.hrp.ass.service.dict.AssUsageDictService;
import com.chd.hrp.sys.entity.FacDict;
import com.chd.hrp.sys.entity.SupDict;
import com.chd.hrp.sys.entity.Unit;
import com.chd.hrp.sys.service.UnitService;
import com.chd.hrp.sys.serviceImpl.FacDictServiceImpl;
import com.chd.hrp.sys.serviceImpl.SupDictServiceImpl;

/**
 * @Description: 050102 资产字典
 * @Table: ASS_DICT
 * @Author: bell
 * @email: bell@s-chd.com
 * @Version: 1.0
 */
 
@Controller
public class AssDictController extends BaseController {

	private static Logger logger = Logger.getLogger(AssDictController.class);

	// 引入Service服务
	@Resource(name = "assDictService")
	private final AssDictService assDictService = null;

	@Resource(name = "assNoDictService")
	private final AssNoDictService assNoDictService = null;

	@Resource(name = "assBaseService")
	private final AssBaseService assBaseService = null;

	@Resource(name = "assTypeDictService")
	private final AssTypeDictService assTypeDictService = null;

	@Resource(name = "unitService")
	private final UnitService unitService = null;

	@Resource(name = "assDepreMethodDictService")
	private final AssDepreMethodDictService assDepreMethodDictService = null;

	@Resource(name = "assUsageDictService")
	private final AssUsageDictService assUsageDictService = null;

	@Resource(name = "facDictService")
	private final FacDictServiceImpl facDictService = null;

	@Resource(name = "supDictService")
	private final SupDictServiceImpl supDictService = null;

	@Resource(name = "assAcceptItemDictService")
	private final AssAcceptItemDictService assAcceptItemDictService = null;

	@Resource(name = "assCheckItemDictService")
	private final AssCheckItemDictService assCheckItemDictService = null;

	@Resource(name = "assAcceptItemAffiService")
	private final AssAcceptItemAffiService assAcceptItemAffiService = null;

	@Resource(name = "assCheckItemAffiService")
	private final AssCheckItemAffiService assCheckItemAffiService = null;

	@RequestMapping(value = "/hrp/ass/assdict/assDictAcceptItemAffiPage", method = RequestMethod.GET)
	public String assDictAcceptItemAffiPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("ass_id", mapVo.get("ass_id"));
		return "hrp/ass/assdict/assDictAcceptItemAffi";
	}

	@RequestMapping(value = "/hrp/ass/assdict/assDictCheckItemAffiPage", method = RequestMethod.GET)
	public String assDictCheckItemAffiPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("ass_id", mapVo.get("ass_id"));
		return "hrp/ass/assdict/assDictCheckItemAffi";
	}

	@RequestMapping(value = "/hrp/ass/assdict/assDictImportAcceptItemPage", method = RequestMethod.GET)
	public String assDictImportAcceptItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("ass_id", mapVo.get("ass_id"));
		return "hrp/ass/assdict/assDictImportAcceptItem";
	}

	@RequestMapping(value = "/hrp/ass/assdict/assDictImportCheckItemPage", method = RequestMethod.GET)
	public String assDictImportCheckItemPage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mode.addAttribute("ass_id", mapVo.get("ass_id"));
		return "hrp/ass/assdict/assDictImportCheckItem";
	}

	@RequestMapping(value = "/hrp/ass/assdict/assDictMainPage", method = RequestMethod.GET)
	public String assDictMainPage(Model mode) throws Exception {

		return "hrp/ass/assdict/assDictMain";

	}

	/**
	 * @Description 添加页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/assDictAddPage", method = RequestMethod.GET)
	public String assDictAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		Map<String, Object> mapVo1 = new HashMap<String, Object>();
		mapVo1.put("group_id", SessionManager.getGroupId());
		mapVo1.put("hos_id", SessionManager.getHosId());
		mapVo1.put("copy_code", SessionManager.getCopyCode());
		mapVo1.put("ass_naturs", mapVo.get("ass_naturs"));
		mapVo1.put("ass_type_code", mapVo.get("ass_type_code"));
		List<AssTypeDict> list = assDictService.queryNodict(mapVo1);
		if(list.size() > 0 || list == null){
			mode.addAttribute("ass_naturs", mapVo.get("ass_naturs"));
			mode.addAttribute("ass_type_code", mapVo.get("ass_type_code"));
			mode.addAttribute("ass_type_name", mapVo.get("ass_type_name"));
			mode.addAttribute("ass_type_id", mapVo.get("ass_type_id"));
			mode.addAttribute("manage_depre_amount", mapVo.get("manage_depre_amount"));
		}else{
			mode.addAttribute("ass_naturs", mapVo.get("ass_naturs"));
		}
		
		return "hrp/ass/assdict/assDictAdd";

	}
	
	
	@RequestMapping(value = "/hrp/ass/assdict/assDictUpdateBatchPage", method = RequestMethod.GET)
	public String assDictUpdateBatchPage(@RequestParam Map<String, Object> map,Model mode) throws Exception {
		mode.addAttribute("paramVo", map.get("paramVo"));
		return "hrp/ass/assdict/assDictUpdateBatch";

	}
	
	@RequestMapping(value = "/hrp/ass/assdict/updateBatchDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateBatchDict(@RequestParam Map<String, Object> map, Model mode) throws Exception {
		
		String assDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : map.get("paramVo").toString().split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_id", ids[3]);
			mapVo.put("is_measure", map.get("is_measure").toString());
			mapVo.put("measure_type", map.get("measure_type").toString());
			mapVo.put("is_s_measure", map.get("is_s_measure").toString());
			mapVo.put("common_name", map.get("common_name").toString());
			listVo.add(mapVo);
		}
		try {
			assDictJson = assDictService.updateBatchDict(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
		return JSONObject.parseObject(assDictJson);
	}
  /**
   * 查询资产编码 queryasstypeid
   */
	@RequestMapping(value = "/hrp/ass/assdict/queryasstypeid", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object>  queryasstypeid(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		
		try {
			String	assDictJson = assDictService.queryasstypeid(mapVo);
			
			return JSONObject.parseObject(assDictJson);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		
	}
	
	/**
	 * @Description 添加数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/addAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		if (mapVo.get("fac_id") != null && !mapVo.get("fac_id").toString().equals("")) {
			String[] fac = mapVo.get("fac_id").toString().split("@");
			mapVo.put("fac_id", fac[0]);
			mapVo.put("fac_no", fac[1]);
		}
		if (mapVo.get("ven_id") != null && !mapVo.get("ven_id").toString().equals("")) {
			String[] ven = mapVo.get("ven_id").toString().split("@");
			mapVo.put("ven_id", ven[0]);
			mapVo.put("ven_no", ven[1]);
		}
		if (mapVo.get("is_depre").equals("0")) {
    	  //mapVo.put("ass_depre_code ", "");
    	  //mapVo.put("depre_years", "");
      	}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));

		String assDictJson = "";

		try {
			assDictJson = assDictService.addAssDict(mapVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assDictJson);

	}

	/**
	 * @Description 更新跳转页面 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/assDictUpdatePage", method = RequestMethod.GET)
	public String assDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		AssDict assDict = new AssDict();

		assDict = assDictService.queryAssDictByCode(mapVo);

		mode.addAttribute("group_id", assDict.getGroup_id());
		mode.addAttribute("hos_id", assDict.getHos_id());
		mode.addAttribute("copy_code", assDict.getCopy_code());
		mode.addAttribute("ass_id", assDict.getAss_id());
		mode.addAttribute("ass_code", assDict.getAss_code());
		mode.addAttribute("ass_name", assDict.getAss_name());
		mode.addAttribute("ass_type_id", assDict.getAss_type_id());
		mode.addAttribute("ass_type_name", assDict.getAss_type_name());
		mode.addAttribute("acc_type_code", assDict.getAcc_type_code());
		mode.addAttribute("acc_type_name", assDict.getAcc_type_name());
		mode.addAttribute("ass_unit", assDict.getAss_unit());
		mode.addAttribute("ass_unit_name", assDict.getAss_unit_name());
		mode.addAttribute("is_measure", assDict.getIs_measure());
		mode.addAttribute("is_depre", assDict.getIs_depre());
		mode.addAttribute("ass_depre_code", assDict.getAss_depre_code());
		mode.addAttribute("ass_depre_name", assDict.getAss_depre_name());
		mode.addAttribute("depre_years", assDict.getDepre_years());
		mode.addAttribute("is_stop", assDict.getIs_stop());
		mode.addAttribute("ass_spec", assDict.getAss_spec());
		mode.addAttribute("ass_model", assDict.getAss_model());
		mode.addAttribute("fac_id", assDict.getFac_id());
		mode.addAttribute("fac_code", assDict.getFac_code());
		mode.addAttribute("fac_no", assDict.getFac_no());
		mode.addAttribute("fac_no_name", assDict.getFac_no_name());
		mode.addAttribute("ven_id", assDict.getVen_id());
		mode.addAttribute("ven_code", assDict.getVen_code());
		mode.addAttribute("ven_no", assDict.getVen_no());
		mode.addAttribute("ven_no_name", assDict.getVen_no_name());
		mode.addAttribute("usage_code", assDict.getUsage_code());
		mode.addAttribute("usage_name", assDict.getUsage_name());
		mode.addAttribute("gb_code", assDict.getGb_code());
		mode.addAttribute("gb_name", assDict.getGb_name());
		mode.addAttribute("spell_code", assDict.getSpell_code());
		mode.addAttribute("wbx_code", assDict.getWbx_code());
		mode.addAttribute("is_ins", assDict.getIs_ins());
		mode.addAttribute("is_accept", assDict.getIs_accept());
		mode.addAttribute("is_check", assDict.getIs_check());
		mode.addAttribute("ass_brand", assDict.getAss_brand());
		mode.addAttribute("is_bar", assDict.getIs_bar());
		mode.addAttribute("bar_type", assDict.getBar_type());
		mode.addAttribute("is_manage_depre", assDict.getIs_manage_depre());
		mode.addAttribute("manage_depr_method", assDict.getManage_depr_method());
		mode.addAttribute("manage_depre_amount", assDict.getManage_depre_amount());
		
		mode.addAttribute("reg_no", assDict.getReg_no());
		
		mode.addAttribute("price", assDict.getPrice());
		
		mode.addAttribute("measure_type", assDict.getMeasure_type());
		
		mode.addAttribute("is_s_measure", assDict.getIs_s_measure());	
		
		mode.addAttribute("measure_king_code", assDict.getMeasure_king_code());
		mode.addAttribute("measure_king_name", assDict.getMeasure_king_name());
		
		mode.addAttribute("is_fae", assDict.getIs_fae());
		
		mode.addAttribute("common_name", assDict.getCommon_name());
		
		mode.addAttribute("type_code", assDict.getType_code());
		mode.addAttribute("type_name", assDict.getType_name());
		
		//是否停用字段，当分类被引用时，不允许修改，是否末级分类字段， 当分类被引用时，不允许修改。zhaon
		Boolean isDisabled = false;
		String str = assBaseService.isExistsDataByTable("ass_dict", mapVo.get("ass_id").toString());
		str += assBaseService.isExistsDataByTable("ASS_NO_DICT", mapVo.get("ass_id").toString());
		if (Strings.isNotBlank(str)) {
			isDisabled = true;
		}
		
		mode.addAttribute("isDisabled",isDisabled);

		return "hrp/ass/assdict/assDictUpdate";
	}

	/**
	 * @Description 更新数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/updateAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String assDictJson = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		 if (mapVo.get("is_depre").toString().equals("0")) {
	    	  mapVo.put("ass_depre_code ", "");
	    	  mapVo.put("depre_years", "");
		}

		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));

		try {

			assDictJson = assDictService.updateAssDict(mapVo);

		} catch (Exception e) {

			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}

		return JSONObject.parseObject(assDictJson);
	}

	/**
	 * @Description 删除数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/deleteAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assDictJson = "";
		boolean falg = true;
		String str = "";
		String retErrot = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			str = str + assBaseService.isExistsDataByTable("ASS_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_DICT", ids[3]);
			str = str + assBaseService.isExistsDataByTable("ASS_NO_DICT", ids[3]) == null ? ""
					: assBaseService.isExistsDataByTable("ASS_NO_DICT", ids[3]);
			if (Strings.isNotBlank(str)) {
				falg = false;
				continue;
			}
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("ass_id", ids[3]);
			mapVo.put("is_stop", "1");
			listVo.add(mapVo);
		}

		if (!falg) {
			retErrot = "{\"error\":\"删除失败，选择的资产字典被以下业务使用：【" + str.substring(0, str.length() - 1)
					+ "】。\",\"state\":\"false\"}";
			return JSONObject.parseObject(retErrot);
		}

		try {
			assDictJson = assDictService.deleteBatchAssDict(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assDictJson);
	}
	
	
	/**
	 * @Description 删除数据 中间表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/deleteAssAcceptItemAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssAcceptItemAffi(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("accept_item_code", ids[3]);
			mapVo.put("ass_id", ids[4]);
			listVo.add(mapVo);
		}
		try {
			assDictJson = assAcceptItemAffiService.deleteBatchAssAcceptItemAffi(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assDictJson);
	}
	
	/**
	 * @Description 删除数据 中间表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/deleteAssCheckItemAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteAssCheckItemAffi(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		String assDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();

			String[] ids = id.split("@");
			// 表的主键
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("check_item_code", ids[3]);
			mapVo.put("ass_id", ids[4]);
			listVo.add(mapVo);
		}
		try {
			assDictJson = assCheckItemAffiService.deleteBatchAssCheckItemAffi(listVo);
		} catch (Exception e) {
			return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
		}
		return JSONObject.parseObject(assDictJson);
	}

	/**
	 * @Description 查询数据 050102 资产字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/queryAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String assDict = assDictService.queryAssDict(getPage(mapVo));

		return JSONObject.parseObject(assDict);
	}

	/**
	 * @Description 查询数据 050107 验收项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/queryDictAssAcceptItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryDictAssAcceptItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_exists_affi", "true");

		String assAcceptItemDict = assAcceptItemDictService.queryAssAcceptItemDict(getPage(mapVo));

		return JSONObject.parseObject(assAcceptItemDict);
	}

	/**
	 * @Description 查询数据 050109 检查项目字典
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/queryAssDictCheckItemDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssDictCheckItemDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckItemDict = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		mapVo.put("is_exists_affi", "true");

		assCheckItemDict = assCheckItemDictService.queryAssCheckItemDict(getPage(mapVo));

		return JSONObject.parseObject(assCheckItemDict);
	}

	/**
	 * @Description 查询数据 050107 验收项目字典中间表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/queryAssAcceptItemDictByAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssAcceptItemDictByAffi(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		String assAcceptItemDict = assAcceptItemAffiService.queryAssAcceptItemAffi(getPage(mapVo));

		return JSONObject.parseObject(assAcceptItemDict);
	}

	/**
	 * @Description 查询数据 050109 检查项目字典中间表
	 * @param mapVo
	 * @param mode
	 * @return Map<String, Object>
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/ass/assdict/queryAssCheckItemDictByAffi", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssCheckItemDictByAffi(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String assCheckItemDict = "";

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());

		assCheckItemDict = assCheckItemAffiService.queryAssCheckItemAffi(getPage(mapVo));

		return JSONObject.parseObject(assCheckItemDict);
	}



	/**
	 * @Description 下载导入模版 050102 资产字典
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "hrp/ass/assdict/downTemplate", method = RequestMethod.GET)
	public String downTemplate(Plupload plupload, HttpServletRequest request, HttpServletResponse response, Model mode)
			throws IOException {

		printTemplate(request, response, "ass\\downTemplate", "资产字典.xls");

		return null;
	}

	/**
	 * @Description 导入数据 050102 资产字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assdict/assDictImportPage", method = RequestMethod.POST)
	@ResponseBody
	public String readAssDictFiles(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		try {
			
			String reJson=assDictService.readAssTypeDictFiles(mapVo);
			
			return reJson;
			
		} catch (Exception e) {
			
			return "{\"error\":\""+e.getMessage()+"\"}";
			
		}
	
	}

	/**
	 * @Description 批量添加数据 050102 资产字典
	 * @param plupload
	 * @param request
	 * @param response
	 * @param mode
	 * @return String
	 * @throws IOException
	 */
	@RequestMapping(value = "/hrp/ass/assdict/addBatchAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addBatchAssDict(@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<AssDict> list_err = new ArrayList<AssDict>();

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

				AssDict assDict = new AssDict();

				JSONObject jsonObj = JSONObject.parseObject(it.next().toString());

				if (StringTool.isNotBlank(jsonObj.get("ass_code"))) {

					assDict.setAss_code((String) jsonObj.get("ass_code"));

					mapVo.put("ass_code", jsonObj.get("ass_code"));

				} else {

					err_sb.append("资产编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_name"))) {

					assDict.setAss_name((String) jsonObj.get("ass_name"));

					mapVo.put("ass_name", jsonObj.get("ass_name"));

				} else {

					err_sb.append("资产名称为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_type_code"))) {

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("ass_type_code1", jsonObj.get("ass_type_code"));
					List<AssTypeDict> assType = assTypeDictService.queryAssTypeDictList(map);// 资产分类
					assDict.setAss_type_code((String) jsonObj.get("ass_type_code"));
					if (assType.size() > 0) {
						mapVo.put("ass_type_id", assType.get(0).getAss_type_id());
					} else {
						err_sb.append("资产分类编码错误 ");
					}

				} else {

					err_sb.append("类别编码为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("acc_type_code"))) {

					assDict.setAcc_type_code((String) jsonObj.get("acc_type_code"));

					mapVo.put("acc_type_code", jsonObj.get("acc_type_code"));

				} else {

					mapVo.put("acc_type_code", "");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_unit"))) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("unit_code", jsonObj.get("ass_unit"));
					assDict.setAss_unit((String) jsonObj.get("ass_unit"));
					Unit unitList = unitService.queryUnitByCode(map);// 计量单位
					if (unitList != null) {
						mapVo.put("ass_unit", unitList.getUnit_code());
					} else {
						err_sb.append("单位编码错误 ");
					}

				} else {

					mapVo.put("ass_unit", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("is_measure"))) {

					assDict.setIs_measure(Integer.valueOf((String) jsonObj.get("is_measure")));

					mapVo.put("is_measure", getYesNo((String) jsonObj.get("is_measure")));

				} else {

					err_sb.append("是否计量为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_depre"))) {

					assDict.setIs_depre(Integer.valueOf((String) jsonObj.get("is_depre")));

					mapVo.put("is_depre", getYesNo((String) jsonObj.get("is_depre")));

				} else {

					err_sb.append("是否折旧为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_depre_code"))) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("ass_depre_code", jsonObj.get("ass_depre_code"));
					assDict.setAss_depre_code((String) jsonObj.get("ass_depre_code"));
					AssDepreMethodDict assDepreMethodDict = assDepreMethodDictService
							.queryAssDepreMethodDictByCode(map);
					if (assDepreMethodDict != null) {
						mapVo.put("ass_depre_code", assDepreMethodDict.getAss_depre_code());
					} else {
						err_sb.append("折旧方法编码错误 ");
					}
				} else {
					mapVo.put("ass_depre_code", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("depre_years"))) {

					assDict.setDepre_years(Integer.valueOf((String) jsonObj.get("depre_years")));

					mapVo.put("depre_years", jsonObj.get("depre_years"));

				} else {

					err_sb.append("折旧年限为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("is_stop"))) {

					assDict.setIs_stop(Integer.valueOf((String) jsonObj.get("is_stop")));

					mapVo.put("is_stop", getYesNo((String) jsonObj.get("is_stop")));

				} else {

					err_sb.append("是否停用为空  ");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_spec"))) {

					assDict.setAss_spec((String) jsonObj.get("ass_spec"));

					mapVo.put("ass_spec", jsonObj.get("ass_spec"));

				} else {

					mapVo.put("ass_spec", "");

				}
				if (StringTool.isNotBlank(jsonObj.get("ass_model"))) {

					assDict.setAss_model((String) jsonObj.get("ass_model"));

					mapVo.put("ass_model", jsonObj.get("ass_model"));

				} else {

					mapVo.put("ass_model", "");

				}
				if (StringTool.isNotBlank(jsonObj.get("fac_code"))) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("fac_code", jsonObj.get("fac_code"));
					assDict.setFac_code((String) jsonObj.get("fac_code"));
					assDict.setFac_name((String) jsonObj.get("fac_name"));
					FacDict facDict = facDictService.queryFacDictByCode(map);
					if (facDict != null) {
						mapVo.put("fac_id", facDict.getFac_id());
						mapVo.put("fac_no", facDict.getFac_no());
					} else {
						err_sb.append("生产厂商法编码错误 ");
					}
				} else {
					mapVo.put("fac_id", "");
					mapVo.put("fac_no", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("ven_code"))) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("sup_code", jsonObj.get("ven_code"));
					assDict.setVen_code((String) jsonObj.get("ven_code"));
					assDict.setVen_name((String) jsonObj.get("ven_name"));
					SupDict supDict = supDictService.querySupDictByCode(map);
					if (supDict != null) {
						mapVo.put("ven_id", supDict.getSup_id());
						mapVo.put("ven_no", supDict.getSup_no());
					} else {
						err_sb.append("供应商编码错误 ");
					}
				} else {
					mapVo.put("ven_id", "");
					mapVo.put("ven_no", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("usage_code"))) {

					mapVo.put("usage_code", jsonObj.get("usage_code"));

					Map<String, Object> map = new HashMap<String, Object>();
					map.put("group_id", SessionManager.getGroupId());
					map.put("hos_id", SessionManager.getHosId());
					map.put("copy_code", SessionManager.getCopyCode());
					map.put("equi_usage_code", jsonObj.get("usage_code"));
					assDict.setUsage_code((String) jsonObj.get("usage_code"));
					AssUsageDict assUsageDict = assUsageDictService.queryAssUsageDictByCode(map);
					if (assUsageDict != null) {
						mapVo.put("usage_code", assUsageDict.getEqui_usage_code());
					} else {
						err_sb.append("资产用途编码错误 ");
					}
				} else {
					mapVo.put("usage_code", "");
				}
				if (StringTool.isNotBlank(jsonObj.get("gb_code"))) {

					assDict.setGb_code((String) jsonObj.get("gb_code"));

					mapVo.put("gb_code", jsonObj.get("gb_code"));

				} else {
					mapVo.put("gb_code", "");
				}

				AssDict data_exc_extis = assDictService.queryAssDictByCode(mapVo);

				if (data_exc_extis != null) {

					err_sb.append("编码已经存在！ ");
				}
				if (err_sb.toString().length() > 0) {

					assDict.setError_type(err_sb.toString());

					list_err.add(assDict);

				} else {
					mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("ass_name").toString()));

					mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("ass_name").toString()));

					String dataJson = assDictService.addAssDict(mapVo);
					Map<String, Object> vo = new HashMap<String, Object>();
					vo.put("group_id", mapVo.get("group_id"));
					vo.put("hos_id", mapVo.get("hos_id"));
					vo.put("copy_code", mapVo.get("copy_code"));
					vo.put("ass_code", mapVo.get("ass_code"));
					AssDict ad = assDictService.queryAssDictByUniqueness(vo);
					mapVo.put("ass_no", 0);
					mapVo.put("ass_id", ad.getAss_id());
					try {
						String assNoDictJson = assNoDictService.addAssNoDict(mapVo);
					} catch (Exception e) {
						return JSONObject.parseObject("{\"error\":\"" + e.getMessage() + " \"}");
					}
				}

			}

		} catch (DataAccessException e) {

			AssDict data_exc = new AssDict();

			list_err.add(data_exc);

			return JSONObject.parseObject("{\"msg\":\"导入系统出错,请重新导入.\",\"state\":\"err\"}");

		}

		if (list_err.size() > 0) {

			return JSONObject.parseObject(ChdJson.toJson(list_err, list_err.size()));

		} else {

			return JSONObject.parseObject("{\"msg\":\"导入成功.\",\"state\":\"true\"}");

		}

	}

	public String getYesNo(String str) {
		if (str.equals("是")) {
			return "1";
		} else if (str.equals("否")) {
			return "0";
		} else if (str.equals("1")) {
			return "1";
		} else {
			return "0";
		}
	}

	@RequestMapping(value = "/hrp/ass/assdict/queryAssNoDictTree", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryAssTypeDictByTree(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());

		mapVo.put("hos_id", SessionManager.getHosId());

		mapVo.put("copy_code", SessionManager.getCopyCode());
		String l_map = assNoDictService.queryAssNoDictTree(mapVo);
		return JSONObject.parseObject(l_map);
	}
	@RequestMapping(value = "/hrp/ass/assdict/checkAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> checkAssDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String l_map = assNoDictService.queryAssDictByCheck(mapVo);
		return JSONObject.parseObject(l_map);
	}
	
	
	@RequestMapping(value = "/hrp/ass/assdict/copyAssDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> copyAssDict(@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		
		mapVo.put("hos_id", SessionManager.getHosId());
		
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String l_map = assDictService.copyAssDict(mapVo);
		return JSONObject.parseObject(l_map);
	}
}
