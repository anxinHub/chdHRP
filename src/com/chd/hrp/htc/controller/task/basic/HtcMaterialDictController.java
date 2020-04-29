package com.chd.hrp.htc.controller.task.basic;

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
import com.chd.hrp.htc.entity.task.basic.HtcMaterialDict;
import com.chd.hrp.htc.service.task.basic.HtcMaterialDictService;

/**
 * 2015-3-18 author:alfred
 */
@Controller
public class HtcMaterialDictController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcMaterialDictController.class);

	@Resource(name = "htcMaterialDictService")
	private final HtcMaterialDictService htcMaterialDictService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/htcMaterialDictMainPage", method = RequestMethod.GET)
	public String htcMaterialDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/materialdict/htcMaterialDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/htcMaterialDictAddPage", method = RequestMethod.GET)
	public String htcMaterialDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/materialdict/htcMaterialDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/addHtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcMaterialDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String materialDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
  		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_name").toString()));
  		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_name").toString()));
  		mapVo.put("is_stop", "0");
  		
  		try {
			materialDictJson = htcMaterialDictService.addHtcMaterialDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/queryHtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcMaterialDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
  		
		String materialDict = htcMaterialDictService
				.queryHtcMaterialDict(getPage(mapVo));

		return JSONObject.parseObject(materialDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/deleteHtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcMaterialDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {

		String materialDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("mate_code", ids[3]);
			listVo.add(mapVo);
		}
  		
  		try {
			materialDictJson = htcMaterialDictService.deleteBatchHtcMaterialDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/htcMaterialDictUpdatePage", method = RequestMethod.GET)
	public String htcMaterialDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		HtcMaterialDict materialDict = htcMaterialDictService.queryHtcMaterialDictByCode(mapVo);
		mode.addAttribute("group_id", materialDict.getGroup_id());
		mode.addAttribute("hos_id", materialDict.getHos_id());
		mode.addAttribute("copy_code", materialDict.getCopy_code());
		mode.addAttribute("mate_code", materialDict.getMate_code());
		mode.addAttribute("mate_name", materialDict.getMate_name());
		mode.addAttribute("mate_mode", materialDict.getMate_mode());
		mode.addAttribute("mate_type_code", materialDict.getMate_type_code());
		mode.addAttribute("mate_type_name", materialDict.getMate_type_name());
		mode.addAttribute("meas_code", materialDict.getMeas_code());
		mode.addAttribute("meas_name", materialDict.getMeas_name());
		mode.addAttribute("price", materialDict.getPrice());
		mode.addAttribute("fac_no", materialDict.getFac_no());
		mode.addAttribute("fac_id", materialDict.getFac_id());
		mode.addAttribute("fac_code", materialDict.getFac_code());
		mode.addAttribute("fac_name", materialDict.getFac_name());
		mode.addAttribute("is_stop", materialDict.getIs_stop());
		return "hrp/htc/task/basic/materialdict/htcMaterialDictUpdate";

	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/updateHtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcMaterialDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String materialDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
  		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_name").toString()));
  		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_name").toString()));

		try {
			materialDictJson = htcMaterialDictService.updateHtcMaterialDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialDictJson);
	}
	
	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/materialdict/synchroHtcMaterialDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcMaterialDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		String materialDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
  		try {
			materialDictJson = htcMaterialDictService.synchroHtcMaterialDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialDictJson);

	}
}
