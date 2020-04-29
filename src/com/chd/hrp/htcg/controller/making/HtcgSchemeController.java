package com.chd.hrp.htcg.controller.making;
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
import com.chd.hrp.htcg.entity.making.HtcgScheme;
import com.chd.hrp.htcg.serviceImpl.making.HtcgSchemeServiceImpl;

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
public class HtcgSchemeController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgSchemeController.class);
	
	
	@Resource(name = "htcgSchemeService")
	private final HtcgSchemeServiceImpl htcgSchemeService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/scheme/htcgSchemeMainPage", method = RequestMethod.GET)
	public String htcgSchemeMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/scheme/htcgSchemeMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/scheme/htcgSchemeAddPage", method = RequestMethod.GET)
	public String htcgSchemeAddPage(Model mode) throws Exception {

		return "hrp/htcg/making/scheme/htcgSchemeAdd";

	}
	

	// 保存
	@RequestMapping(value = "/hrp/htcg/making/scheme/addHtcgScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
		
		String schemeJson = "";
				System.out.println("mapVo = " + mapVo);
		try {
			schemeJson = htcgSchemeService.addHtcgScheme(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeJson = e.getMessage();
		}

		return JSONObject.parseObject(schemeJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/scheme/queryHtcgScheme", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		String scheme = htcgSchemeService.queryHtcgScheme(getPage(mapVo));

		return JSONObject.parseObject(scheme);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/making/scheme/deleteHtcgScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteScheme(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id",ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code",ids[2]);
            mapVo.put("scheme_code", ids[3]);//实际实体类变量
            listVo.add(mapVo);
        }
		String schemeJson = "";
		try {
			schemeJson = htcgSchemeService.deleteBatchHtcgScheme(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeJson = e.getMessage();
		}
	   return JSONObject.parseObject(schemeJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/scheme/htcgSchemeUpdatePage", method = RequestMethod.GET)
	
	public String htcgSchemeUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgScheme scheme =  htcgSchemeService.queryHtcgSchemeByCode(mapVo);
		mode.addAttribute("group_id", scheme.getGroup_id());
		mode.addAttribute("hos_id", scheme.getHos_id());
		mode.addAttribute("copy_code", scheme.getCopy_code());
		mode.addAttribute("scheme_code", scheme.getScheme_code());
		mode.addAttribute("scheme_name", scheme.getScheme_name());
		mode.addAttribute("scheme_note", scheme.getScheme_note());
		mode.addAttribute("spell_code", scheme.getSpell_code());
		mode.addAttribute("wbx_code", scheme.getWbx_code());
		mode.addAttribute("is_stop", scheme.getIs_stop());
		return "hrp/htcg/making/scheme/htcgSchemeUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/scheme/updateHtcgScheme", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateScheme(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("scheme_name").toString()));
        mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("scheme_name").toString()));
		String schemeJson = "";
		try {
			schemeJson = htcgSchemeService.updateHtcgScheme(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeJson);
	}


}

