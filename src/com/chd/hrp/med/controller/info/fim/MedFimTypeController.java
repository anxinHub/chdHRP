package com.chd.hrp.med.controller.info.fim;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.chd.base.BaseController;
import com.chd.base.SessionManager;
import com.chd.base.util.StringTool;
import com.chd.hrp.med.entity.MedFimType;
import com.chd.hrp.med.service.info.fim.MedFimTypeService;

@Controller
public class MedFimTypeController extends BaseController {
	/**
	 * 2017年7月6日 新增药品财务功能
	 * 
	 * 
	 * 
	 */
	private static Logger logger = Logger.getLogger(MedFimTypeController.class);
	
	@Resource(name = "medFimTypeService")
	private final MedFimTypeService medFimTypeService = null;

	/**
	 * @Description 主页面跳转
	 * @param mode
	 * @return String
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/fim/medFimTypeMainPage", method = RequestMethod.GET)
	public String medFimTypeMainPage(Model mode) throws Exception {
		return "hrp/med/info/fim/medFimTypeMain";
	}

	/**
	 * 查询数据，加载页面
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/fim/queryMedFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryMedFimType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {

			mapVo.put("group_id", SessionManager.getGroupId());

		}

		if (mapVo.get("hos_id") == null) {

			mapVo.put("hos_id", SessionManager.getHosId());

		}

		if (mapVo.get("copy_code") == null) {

			mapVo.put("copy_code", SessionManager.getCopyCode());

		}
		String medBillMain = medFimTypeService.queryMedBillMain(mapVo);

		return JSONObject.parseObject(medBillMain);
	}

	/**
	 * 跳转添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hrp/med/info/fim/addMedFimTypePage", method = RequestMethod.GET)
	public String addMedFimTypePage() {
		return "/hrp/med/info/fim/medFimTypeAdd";

	}

	/**
	 * 添加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/hrp/med/info/fim/addMedFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addMedFimType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"fim_type_name").toString()));
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("fim_type_name").toString()));
		String medfimtypeJson = medFimTypeService.add(mapVo);

		return JSONObject.parseObject(medfimtypeJson);

	}

	/**
	 * 修改数据
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/fim/updateMedFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateMedFimType(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get(
				"fim_type_name").toString()));
		mapVo.put("wbx_code",
				StringTool.toWuBi(mapVo.get("fim_type_name").toString()));
		String medfimtypeJson = medFimTypeService.update(mapVo);

		return JSONObject.parseObject(medfimtypeJson);

	}

	/**
	 * 修改药品财务页面跳转
	 * 
	 * @param mapVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/fim/updateMedFimTypePage", method = RequestMethod.GET)
	public String updateMedFimTypePage(@RequestParam Map<String, Object> mapVo,
			Model mode) throws Exception {

		if (mapVo.get("group_id") == null) {
			mapVo.put("group_id", SessionManager.getGroupId());
		}

		if (mapVo.get("hos_id") == null) {
			mapVo.put("hos_id", SessionManager.getHosId());
		}

		if (mapVo.get("copy_code") == null) {
			mapVo.put("copy_code", SessionManager.getCopyCode());
		}
		MedFimType medfimtype = new MedFimType();
		medfimtype = medFimTypeService.updatepage(mapVo);
		mode.addAttribute("fim_type_code", medfimtype.getFim_type_code());
		mode.addAttribute("fim_type_name", medfimtype.getFim_type_name());
		mode.addAttribute("is_stop", medfimtype.getIs_stop());
		mode.addAttribute("wbx_code", medfimtype.getWbx_code());
		mode.addAttribute("spell_code", medfimtype.getSpell_code());
		return "hrp/med/info/fim/medFimTypeUpdate";

	}

	/**
	 * 删除
	 * 
	 * @param paramVo
	 * @param mode
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/hrp/med/info/fim/deleteMedFimType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteMedLocationType(
			@RequestParam(value = "ParamVo") String paramVo, Model mode)
			throws Exception {

		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();

		for (String id : paramVo.split(",")) {

			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");

			// 表的主键
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			mapVo.put("fim_type_code", ids[3]);

			listVo.add(mapVo);

		}

		String medFimTypeJson = medFimTypeService.deleteBatch(listVo);

		return JSONObject.parseObject(medFimTypeJson);

	}

}
