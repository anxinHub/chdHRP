package com.chd.hrp.htc.controller.info.basic;
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
import com.chd.hrp.htc.entity.info.basic.HtcCostTypeDict;
import com.chd.hrp.htc.service.info.basic.HtcCostTypeDictService;

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
public class HtcCostTypeDictController extends BaseController{
	
	private static Logger logger = Logger.getLogger(HtcCostTypeDictController.class);
	
	@Resource(name = "htcCostTypeDictService")
	private final HtcCostTypeDictService htcCostTypeDictService = null;
    
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/htcCostTypeDictMainPage", method = RequestMethod.GET)
	public String htcCostTypeDictMainPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/costtypedict/htcCostTypeDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/htcCostTypeDictAddPage", method = RequestMethod.GET)
	public String htcCostTypeDictAddPage(Model mode) throws Exception {

		return "hrp/htc/info/basic/costtypedict/htcCostTypeDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/addHtcCostTypeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcCostTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costTypeDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_type_name").toString()));
		
		try {
			
			costTypeDictJson = htcCostTypeDictService.addHtcCostTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return JSONObject.parseObject(costTypeDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/queryHtcCostTypeDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcCostTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String costTypeDict = htcCostTypeDictService.queryHtcCostTypeDict(getPage(mapVo));

		return JSONObject.parseObject(costTypeDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/deleteHtcCostTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcCostTypeDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String costTypeDictJson = "";
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			String[] ids = id.split("@");
			
			Map<String, Object> mapVo = new HashMap<String, Object>();
  
			mapVo.put("cost_type_id",ids[0]); 
			
            listVo.add(mapVo);
        }
		
		try {
			
			costTypeDictJson = htcCostTypeDictService.deleteBatchHtcCostTypeDict(listVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JSONObject.parseObject(costTypeDictJson);
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/htcCostTypeDictUpdatePage", method = RequestMethod.GET)
	
	public String htcCostTypeDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        
		HtcCostTypeDict costTypeDict = htcCostTypeDictService.queryHtcCostTypeDictByCode(mapVo);
		
		mode.addAttribute("cost_type_id", costTypeDict.getCost_type_id());
		mode.addAttribute("cost_type_code", costTypeDict.getCost_type_code());
		mode.addAttribute("cost_type_name", costTypeDict.getCost_type_name());
		mode.addAttribute("spell_code", costTypeDict.getSpell_code());
		mode.addAttribute("wbx_code", costTypeDict.getWbx_code());
		
		return "hrp/htc/info/basic/costtypedict/htcCostTypeDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htc/info/basic/costtype/updateHtcCostTypeDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcCostTypeDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String costTypeDictJson = "";
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());	
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("cost_type_name").toString()));
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("cost_type_name").toString()));
		
		try {
			
			costTypeDictJson = htcCostTypeDictService.updateHtcCostTypeDict(mapVo);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return JSONObject.parseObject(costTypeDictJson);
	}
    
    

}

