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
import com.chd.hrp.htc.entity.task.basic.HtcMaterialTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcMaterialTypeDictService;

/**
 * 2015-3-18 author:alfred
 */

@Controller
public class HtcMaterialTypeDictController extends BaseController {

	private static Logger logger = Logger
			.getLogger(HtcMaterialTypeDictController.class);

	@Resource(name = "htcMaterialTypeDictService")
	private final HtcMaterialTypeDictService htcMaterialTypeDictService = null;


	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/htcMaterialTypeDictMainPage", method = RequestMethod.GET)
	public String htcMaterialTypeDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/materialtypedict/htcMaterialTypeDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/htcMaterialTypeDictAddPage", method = RequestMethod.GET)
	public String htcMaterialTypeDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/materialtypedict/htcMaterialTypeDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/addHtcMaterialTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcMaterialTypeDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		String materialTypeDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_type_name").toString()));
		
		try {
			materialTypeDictJson = htcMaterialTypeDictService.addHtcMaterialTypeDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialTypeDictJson);

	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/queryHtcMaterialTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcMaterialTypeDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
  		
		String materialTypeDict = htcMaterialTypeDictService.queryHtcMaterialTypeDict(getPage(mapVo));

		return JSONObject.parseObject(materialTypeDict);

	}

	// 删除
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/deleteHtcMaterialTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcMaterialTypeDict(
			@RequestParam(value = "ParamVo", required = true) String paramVo,
			Model mode) throws Exception {
		
		String materialTypeDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("mate_type_code", ids[3]);
			listVo.add(mapVo);
		}
  		
  		try {
			materialTypeDictJson = htcMaterialTypeDictService.deleteBatchHtcMaterialTypeDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialTypeDictJson);

	}

	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/htcMaterialTypeDictUpdatePage", method = RequestMethod.GET)
	public String htcMaterialTypeDictUpdatePage(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {
		HtcMaterialTypeDict materialTypeDict =  htcMaterialTypeDictService.queryHtcMaterialTypeDictByCode(mapVo);
		mode.addAttribute("group_id",materialTypeDict.getGroup_id());
		mode.addAttribute("hos_id",materialTypeDict.getHos_id());
		mode.addAttribute("copy_code",materialTypeDict.getCopy_code());
		mode.addAttribute("mate_type_code",materialTypeDict.getMate_type_code());
		mode.addAttribute("mate_type_name",materialTypeDict.getMate_type_name());
		mode.addAttribute("supper_code", materialTypeDict.getSupper_code());
		mode.addAttribute("is_last", materialTypeDict.getIs_last());
		mode.addAttribute("is_stop", materialTypeDict.getIs_stop());
		return "hrp/htc/task/basic/materialtypedict/htcMaterialTypeDictUpdate";
	}

	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/updateHtcMaterialTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcMaterialTypeDict(
			@RequestParam Map<String, Object> mapVo, Model mode)
			throws Exception {

		String materialTypeDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
  		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("mate_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("mate_type_name").toString()));
  		
		try {
			materialTypeDictJson = htcMaterialTypeDictService.updateHtcMaterialTypeDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			materialTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialTypeDictJson);
	}

    //同步材料类别 
	@RequestMapping(value = "/hrp/htc/task/basic/materialtypedict/synchroHtcMaterialTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcMaterialTypeDict(Model mode)throws Exception {
		
       String materialTypeDictJson = "";
       
        Map<String, Object> mapVo = new HashMap<String, Object>();
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
	  		
			materialTypeDictJson = htcMaterialTypeDictService.synchroHtcMaterialTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			materialTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(materialTypeDictJson);

	}
	
}
