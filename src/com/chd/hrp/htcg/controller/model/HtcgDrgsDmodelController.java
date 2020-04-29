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
import com.chd.hrp.htcg.service.model.HtcgDrgsDmodelService;

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
public class HtcgDrgsDmodelController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrgsDmodelController.class);
	
	
	@Resource(name = "htcgDrgsDmodelService")
	private final HtcgDrgsDmodelService htcgDrgsDmodelService = null;
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/model/drgsdmodel/htcgDrgsDmodelMainPage", method = RequestMethod.GET)
	public String htcgDrgsDmodelMainPage(Model mode) throws Exception {
		return "hrp/htcg/model/drgsdmodel/htcgDrgsDmodelMain";

	}
	
	
	// 查询页面动态医嘱分类
	@RequestMapping(value = "/hrp/htcg/model/drgsdmodel/queryHtcgRecipeType", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgRecipeType(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgsDmodelJson = htcgDrgsDmodelService.queryHtcgRecipeType(getPage(mapVo));
		return JSONObject.parseObject(drgsDmodelJson);
	
	}
		
	// 生成
	@RequestMapping(value = "/hrp/htcg/model/drgsdmodel/initHtcgDrgsDmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> initHtcgDrgsDmodel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drgsDmodelJson;
		 try {
			    mapVo.put("group_id", SessionManager.getGroupId());
				mapVo.put("hos_id", SessionManager.getHosId());
				mapVo.put("copy_code", SessionManager.getCopyCode());
				drgsDmodelJson = htcgDrgsDmodelService.initHtcgDrgsDmodel(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			drgsDmodelJson = e.getMessage();
		}
		 return JSONObject.parseObject(drgsDmodelJson);
	}
	// 查询
	@RequestMapping(value = "/hrp/htcg/model/drgsdmodel/queryHtcgDrgsDmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> queryHtcgDrgsDmodel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgsDmodel = htcgDrgsDmodelService.queryHtcgDrgsDmodel(getPage(mapVo));
		return JSONObject.parseObject(drgsDmodel);
		
	}
	
	// 保存记录
	@RequestMapping(value = "/hrp/htcg/model/drgsdmodel/saveHtcgDrgsDmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveHtcgDrgsDmodel(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		String drgsPmodelNoteJson = null;
		try {
			drgsPmodelNoteJson = htcgDrgsDmodelService.saveHtcgDrgsDmodel(mapVo);
		} catch (Exception e) {
			// TODO: handle exception
			drgsPmodelNoteJson = e.getMessage();
		}
		return JSONObject.parseObject(drgsPmodelNoteJson);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/model/drgsdmodel/deleteHtcgDrgsDmodel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrgsDmodel(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		String drgsdmodelJson ;
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
	            mapVo.put("clp_d_step", ids[8]);
	            listVo.add(mapVo);
        }
		try{
			drgsdmodelJson = htcgDrgsDmodelService.deleteBatchHtcgDrgsDmodel(listVo);
		}catch(Exception e){
			drgsdmodelJson= e.getMessage();
		}
	   return JSONObject.parseObject(drgsdmodelJson);
			
	}
	
}

