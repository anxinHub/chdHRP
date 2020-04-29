package com.chd.hrp.htcg.controller.model;

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
import com.chd.hrp.htcg.service.model.HtcgDrgsPmodelService;

/**
 * @Title.
 * @Description.
 * @Copyright: Copyright (c) 2015-2-14 下午9:54:34
 * @Company: 杭州亦童科技有限公司
 * @Author: LiuYingDuo
 * @email: bell@s-chd.com
 * @Version: 1.0
 */

@Controller
public class HtcgDrgsPmodelController extends BaseController {

	private static Logger logger = Logger.getLogger(HtcgDrgsPmodelController.class);

	@Resource(name = "htcgDrgsPmodelService")
	private final HtcgDrgsPmodelService htcgDrgsPmodelService = null;

	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/model/drgspmodel/htcgDrgsPmodelMainPage", method = RequestMethod.GET)
	public String htcgDrgsPmodelMainPage(Model mode) throws Exception {
		return "hrp/htcg/model/drgspmodel/htcgDrgsPmodelMain";

	}

	// 查询页面动态医嘱分类
	@RequestMapping(value = "/hrp/htcg/model/drgspmodel/queryHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgRecipeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgsPmodelJson = htcgDrgsPmodelService.queryHtcgRecipeType(getPage(mapVo));
		return JSONObject.parseObject(drgsPmodelJson);
	
	}
	// 生成
	@RequestMapping(value = "/hrp/htcg/model/drgspmodel/initHtcgDrgsPmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgDrgsDmodel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drgsPmodelJson;
		 try {
		 	mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			drgsPmodelJson = htcgDrgsPmodelService.initHtcgDrgsPmodel(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			drgsPmodelJson = e.getMessage();
		}
		 return JSONObject.parseObject(drgsPmodelJson);
	}
	
	// 查询
	@RequestMapping(value = "/hrp/htcg/model/drgspmodel/queryHtcgDrgsPmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrgsPmodel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgsPmodel = htcgDrgsPmodelService.queryHtcgDrgsPmodel(getPage(mapVo));
		return JSONObject.parseObject(drgsPmodel);

	}
	
	// 保存记录
	@RequestMapping(value = "/hrp/htcg/model/drgspmodel/saveHtcgDrgsPmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgDrgsPmodel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgsPmodelNoteJson = htcgDrgsPmodelService.saveHtcgDrgsPmodel(mapVo);
		return JSONObject.parseObject(drgsPmodelNoteJson);
		
	}

	
	// 删除
	@RequestMapping(value = "/hrp/htcg/model/drgspmodel/deleteHtcgDrgsPmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrgsPmodel(@RequestParam(value = "ParamVo") String paramVo, Model mode) throws Exception {
		String drgsPmodelJson ;
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String  id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
				String[] ids = id.split("@");
				mapVo.put("group_id", ids[0]);
				mapVo.put("hos_id", ids[1]);
				mapVo.put("copy_code", ids[2]);
	            mapVo.put("period_type_code", ids[3]);
	            mapVo.put("acc_year", ids[4]);
	            mapVo.put("acc_month", ids[5]);
	            mapVo.put("scheme_code", ids[6]);
	            mapVo.put("drgs_code", ids[7]);
	            mapVo.put("clp_p_step", ids[8]);
	            listVo.add(mapVo);
        }
		try{
			drgsPmodelJson = htcgDrgsPmodelService.deleteBatchHtcgDrgsPmodel(listVo);
		}catch(Exception e){
			drgsPmodelJson= e.getMessage();
		}
	   return JSONObject.parseObject(drgsPmodelJson);

	}
}
