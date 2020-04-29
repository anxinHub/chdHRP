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
import com.chd.hrp.htc.entity.task.basic.HtcFassetTypeDict;
import com.chd.hrp.htc.service.task.basic.HtcFassetTypeDictService;
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
public class HtcFassetTypeDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcFassetTypeDictController.class);
	
	
	@Resource(name = "htcFassetTypeDictService")
	private final HtcFassetTypeDictService htcFassetTypeDictService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/htcFassetTypeDictMainPage", method = RequestMethod.GET)
	public String htcFassetTypeDictMainPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/fassettypedict/htcFassetTypeDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/htcFassetTypeDictAddPage", method = RequestMethod.GET)
	public String htcFassetTypeDictAddPage(Model mode) throws Exception {
		return "hrp/htc/task/basic/fassettypedict/htcFassetTypeDictAdd";

	}
	// 保存
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/addHtcFassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcFassetTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String fassetTypeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));
		try {
			fassetTypeDictJson = htcFassetTypeDictService.addHtcFassetTypeDict(mapVo);
		} catch (Exception e) {
			fassetTypeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(fassetTypeDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/queryHtcFassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcFassetTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String fassetTypeDict = htcFassetTypeDictService.queryHtcFassetTypeDict(getPage(mapVo));
		return JSONObject.parseObject(fassetTypeDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/deleteHtcFassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcFassetTypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String fassetTypeDictJson = "";
		List<Map<String, Object>> listVo = new ArrayList<Map<String, Object>>();
		for (String id : paramVo.split(",")) {
			Map<String, Object> mapVo = new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
			mapVo.put("asset_type_code", ids[3]);
			listVo.add(mapVo);
		}
		try {
			fassetTypeDictJson = htcFassetTypeDictService.deleteBatchHtcFassetTypeDict(listVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetTypeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(fassetTypeDictJson);	
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/htcFassetTypeDictUpdatePage", method = RequestMethod.GET)
	public String htcFassetTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		HtcFassetTypeDict fassetTypeDict = htcFassetTypeDictService.queryHtcFassetTypeDictByCode(mapVo);
		mode.addAttribute("group_id", fassetTypeDict.getGroup_id());
		mode.addAttribute("hos_id", fassetTypeDict.getHos_id());
		mode.addAttribute("copy_code", fassetTypeDict.getCopy_code());
		mode.addAttribute("asset_type_code", fassetTypeDict.getAsset_type_code());
		mode.addAttribute("asset_type_name", fassetTypeDict.getAsset_type_name());
		mode.addAttribute("supper_code", fassetTypeDict.getSupper_code());
		mode.addAttribute("spell_code", fassetTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", fassetTypeDict.getWbx_code());
		mode.addAttribute("is_last", fassetTypeDict.getIs_last());
		mode.addAttribute("is_stop", fassetTypeDict.getIs_stop());
		return "hrp/htc/task/basic/fassettypedict/htcFassetTypeDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/updateHtcFassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcFassetTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String fassetTypeDictJson = "";
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("asset_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("asset_type_name").toString()));
		try {
			fassetTypeDictJson = htcFassetTypeDictService.updateHtcFassetTypeDict(mapVo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			fassetTypeDictJson = e.getMessage();
		}
		return JSONObject.parseObject(fassetTypeDictJson);
	}
	
	//同步固定资产类型
	@RequestMapping(value = "/hrp/htc/task/basic/fassettypedict/synchroHtcFassetTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> synchroHtcFassetTypeDict(Model mode)throws Exception {
		
       String fassetTypeDictJson = "";
       
        Map<String, Object> mapVo = new HashMap<String, Object>();
		
        mapVo.put("group_id", SessionManager.getGroupId());
		
	    mapVo.put("hos_id", SessionManager.getHosId());
	
		mapVo.put("copy_code", SessionManager.getCopyCode());

		try {
	  		
			fassetTypeDictJson = htcFassetTypeDictService.synchroHtcFassetTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO: handle exception
			fassetTypeDictJson = e.getMessage();
		}

		return JSONObject.parseObject(fassetTypeDictJson);

	}
}

