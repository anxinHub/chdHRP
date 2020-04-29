
/*
 *
 */package com.chd.hrp.htcg.controller.info;
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
import com.chd.hrp.htcg.entity.info.HtcgDrugDict;
import com.chd.hrp.htcg.service.info.HtcgDrugDictService;

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
public class HtcgDrugDictController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrugDictController.class);
	
	
	@Resource(name = "htcgDrugDictService")
	private final HtcgDrugDictService htcgDrugDictService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/htcgDrugDictMainPage", method = RequestMethod.GET)
	public String htcgDrugDictMainPage(Model mode) throws Exception {

		return "hrp/htcg/info/htcgdrugdict/htcgDrugDictMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/htcgDrugDictAddPage", method = RequestMethod.GET)
	public String htcgDrugDictAddPage(Model mode) throws Exception {

		return "hrp/htcg/info/htcgdrugdict/htcgDrugDictAdd";

	}

	// 保存
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/addHtcgDrugDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcgDrugDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drug_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drug_name").toString()));
		String htcDrugDictJson;
		try {
			htcDrugDictJson = htcgDrugDictService.addHtcgDrugDict(mapVo);
		} catch (Exception e) {
			htcDrugDictJson=e.getMessage();
		}

		return JSONObject.parseObject(htcDrugDictJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/queryHtcgDrugDict", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgDrugDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String htcDrugDict = htcgDrugDictService.queryHtcgDrugDict(getPage(mapVo));

		return JSONObject.parseObject(htcDrugDict);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/deleteHtcgDrugDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrugDict(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id",ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]);
            mapVo.put("drug_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String htcDrugDictJson;
		try {
			htcDrugDictJson = htcgDrugDictService.deleteBatchHtcgDrugDict(listVo);
		} catch (Exception e) {
			htcDrugDictJson=e.getMessage();
		}
	
	   return JSONObject.parseObject(htcDrugDictJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/htcgDrugDictUpdatePage", method = RequestMethod.GET)
	
	public String htcgDrugDictUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

        HtcgDrugDict htcDrugDict = htcgDrugDictService.queryHtcgDrugDictByCode(mapVo);
		mode.addAttribute("group_id", htcDrugDict.getGroup_id());
		mode.addAttribute("hos_id", htcDrugDict.getHos_id());
		mode.addAttribute("copy_code", htcDrugDict.getCopy_code());
		mode.addAttribute("drug_code", htcDrugDict.getDrug_code());
		mode.addAttribute("drug_name", htcDrugDict.getDrug_name());
		mode.addAttribute("drug_type_code", htcDrugDict.getDrug_type_code());
		mode.addAttribute("drug_type_name", htcDrugDict.getDrug_type_name());
		mode.addAttribute("mode_code", htcDrugDict.getMode_code());
		mode.addAttribute("unit_code", htcDrugDict.getUnit_code());
		mode.addAttribute("price", htcDrugDict.getPrice());
		mode.addAttribute("fac_id", htcDrugDict.getFac_id());
		mode.addAttribute("fac_no", htcDrugDict.getFac_no());
		mode.addAttribute("fac_code", htcDrugDict.getFac_code());
		mode.addAttribute("fac_name", htcDrugDict.getFac_name());
		mode.addAttribute("spell_code", htcDrugDict.getSpell_code());
		mode.addAttribute("wbx_code", htcDrugDict.getWbx_code());
		mode.addAttribute("is_stop", htcDrugDict.getIs_stop());
		
		return "hrp/htcg/info/htcgdrugdict/htcgDrugDictUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/updateHtcgDrugDict", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgDrugDict(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drug_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drug_name").toString()));
        
		String htcDrugDictJson;
		try {
			htcDrugDictJson = htcgDrugDictService.updateHtcgDrugDict(mapVo);
		} catch (Exception e) {
			htcDrugDictJson=e.getMessage();
		}
		
		return JSONObject.parseObject(htcDrugDictJson);
	}
	
	@RequestMapping(value = "/hrp/htcg/info/htcgdrugdict/importHtcgDrugDict", method = RequestMethod.POST)
	@ResponseBody
	public  Map<String, Object> importHtcgDrugDict(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {

		String htcDrugDictJson = "";
		try {

			htcDrugDictJson =  htcgDrugDictService.importHtcgDrugDict(mapVo);

		}
		catch (Exception e) {

			htcDrugDictJson = e.getMessage();

		}
		return JSONObject.parseObject(htcDrugDictJson);
	}
}

