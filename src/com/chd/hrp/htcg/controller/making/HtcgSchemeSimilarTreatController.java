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
import com.chd.hrp.htcg.entity.making.HtcgSchemeSimilarTreat;
import com.chd.hrp.htcg.service.making.HtcgSchemeSimilarTreatService;
import com.chd.hrp.htcg.serviceImpl.making.HtcgSchemeSimilarTreatServiceImpl;

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
public class HtcgSchemeSimilarTreatController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgSchemeSimilarTreatController.class);
	
	
	@Resource(name = "htcgSchemeSimilarTreatService")
	private final HtcgSchemeSimilarTreatService htcgSchemeSimilarTreatService = null;
    
	
	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/htcgSchemeSimilarTreatAddPage", method = RequestMethod.GET)
	public String schemeSimilarTreatAddPage(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
		mode.addAttribute("scheme_code", mapVo.get("scheme_code"));
		mode.addAttribute("drgs_code", mapVo.get("drgs_code"));
		return "hrp/htcg/making/schemerecipemergerule/htcgSchemeSimilarTreatAdd";

	}
	// 保存
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/addHtcgSchemeSimilarTreat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addHtcgSchemeSimilarTreat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		String schemeSimilarTreatJson = "";
		try {
			schemeSimilarTreatJson = htcgSchemeSimilarTreatService.addHtcgSchemeSimilarTreat(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeSimilarTreatJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeSimilarTreatJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/queryHtcgSchemeSimilarTreat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgSchemeSimilarTreat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		String schemeSimilarTreat = htcgSchemeSimilarTreatService.queryHtcgSchemeSimilarTreat(getPage(mapVo));
		return JSONObject.parseObject(schemeSimilarTreat);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/deleteHtcgSchemeSimilarTreat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgSchemeSimilarTreat(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for (String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			String[] ids = id.split("@");
			mapVo.put("group_id", ids[0]);
			mapVo.put("hos_id", ids[1]);
			mapVo.put("copy_code", ids[2]); 
            mapVo.put("scheme_code", ids[3]);
            mapVo.put("drgs_code", ids[4]);
            mapVo.put("charge_nature_code", ids[5]);
            mapVo.put("charge_code", ids[6]);
            listVo.add(mapVo);
        }
		String schemeSimilarTreatJson = "";
		try {
			schemeSimilarTreatJson = htcgSchemeSimilarTreatService.deleteBatchHtcgSchemeSimilarTreat(listVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeSimilarTreatJson = e.getMessage();
		}
	   return JSONObject.parseObject(schemeSimilarTreatJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/htcgSchemeSimilarTreatUpdatePage", method = RequestMethod.GET)
	public String htcgSchemeSimilarTreatUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgSchemeSimilarTreat schemeSimilarTreat = htcgSchemeSimilarTreatService.queryHtcgSchemeSimilarTreatByCode(mapVo);
		mode.addAttribute("group_id", schemeSimilarTreat.getGroup_id());
		mode.addAttribute("hos_id", schemeSimilarTreat.getHos_id());
		mode.addAttribute("copy_code", schemeSimilarTreat.getCopy_code());
		mode.addAttribute("scheme_code", schemeSimilarTreat.getScheme_code());
		mode.addAttribute("scheme_name", schemeSimilarTreat.getScheme_name());
		mode.addAttribute("drgs_code", schemeSimilarTreat.getDrgs_code());
		mode.addAttribute("drgs_name", schemeSimilarTreat.getDrgs_name());
		mode.addAttribute("charge_nature_code", schemeSimilarTreat.getCharge_nature_code());
		mode.addAttribute("charge_nature_name", schemeSimilarTreat.getCharge_nature_name());
		mode.addAttribute("charge_code", schemeSimilarTreat.getCharge_code());
		mode.addAttribute("charge_name", schemeSimilarTreat.getCharge_name());
		mode.addAttribute("similar_code", schemeSimilarTreat.getSimilar_code());
		mode.addAttribute("similar_name", schemeSimilarTreat.getSimilar_name());
		return "hrp/htcg/making/schemerecipemergerule/htcgSchemeSimilarTreatUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/schemerecipemergerule/updateHtcgSchemeSimilarTreat", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgSchemeSimilarTreat(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode()); 
		String schemeSimilarTreatJson = "";
		try {
			schemeSimilarTreatJson = htcgSchemeSimilarTreatService.updateHtcgSchemeSimilarTreat(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			schemeSimilarTreatJson = e.getMessage();
		}
		return JSONObject.parseObject(schemeSimilarTreatJson);
	}

}

