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
import com.chd.hrp.htc.entity.task.basic.HtcFassetDict;
import com.chd.hrp.htc.service.task.basic.HtcFassetDictService;

/**
 * 
 * @Title. 
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email:  bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcFassetDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcFassetDictController.class);
	
	
	@Resource(name = "htcFassetDictService")
	private final HtcFassetDictService htcFassetDictService = null;
	
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/htcFassetDictMainPage", method = RequestMethod.GET)
	public String htcFassetDictMainPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/fassetdict/htcFassetDictMain";

	}
	
	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/htcFassetDictAddPage", method = RequestMethod.GET)
	public String htcFassetDictAddPage(Model mode) throws Exception {

		return "hrp/htc/task/basic/fassetdict/htcFassetDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/addHtcFassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcFassetDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String fassetDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));
		
		try {
			fassetDictJson = htcFassetDictService.addHtcFassetDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetDictJson = e.getMessage();
		}

		return JSONObject.parseObject(fassetDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/queryHtcFassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcFassetDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("user_id", SessionManager.getUserId());
		
		String fassetDict = htcFassetDictService.queryHtcFassetDict(getPage(mapVo));

		return JSONObject.parseObject(fassetDict);	
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/deleteHtcFassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteFassetDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String fassetDictJson = "";
		
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("asset_code", ids[3]);
			listVo.add(mapVo);
		}
		try {
			fassetDictJson = htcFassetDictService.deleteBatchHtcFassetDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(fassetDictJson);		
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/htcFassetDictUpdatePage", method = RequestMethod.GET)
	public String htcFassetDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcFassetDict fassetDict = htcFassetDictService.queryHtcFassetDictByCode(mapVo);
		mode.addAttribute("group_id", fassetDict.getGroup_id());
		mode.addAttribute("hos_id", fassetDict.getHos_id());
		mode.addAttribute("copy_code", fassetDict.getCopy_code());
		mode.addAttribute("asset_code", fassetDict.getAsset_code());
		mode.addAttribute("asset_name", fassetDict.getAsset_name());
		mode.addAttribute("asset_type_code", fassetDict.getAsset_type_code());
		mode.addAttribute("asset_type_name", fassetDict.getAsset_type_name());
		mode.addAttribute("asset_model", fassetDict.getAsset_model());
		mode.addAttribute("prim_value", fassetDict.getPrim_value());
		mode.addAttribute("start_date", fassetDict.getStart_date());
		mode.addAttribute("end_date", fassetDict.getEnd_date());
		mode.addAttribute("dep_year", fassetDict.getDep_year());
		mode.addAttribute("dept_id", fassetDict.getDept_id());
		mode.addAttribute("dept_no", fassetDict.getDept_no());
		mode.addAttribute("dept_code", fassetDict.getDept_code());
		mode.addAttribute("dept_name", fassetDict.getDept_name());
		mode.addAttribute("spell_code", fassetDict.getSpell_code());
		mode.addAttribute("wbx_code", fassetDict.getWbx_code());
		return "hrp/htc/task/basic/fassetdict/htcFassetDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/fassetdict/updateHtcFassetDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcFassetDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String fassetDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_name").toString()));
		
		try {
			fassetDictJson = htcFassetDictService.updateHtcFassetDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetDictJson = e.getMessage();
		}
		
		return JSONObject.parseObject(fassetDictJson);
	}
	
}

