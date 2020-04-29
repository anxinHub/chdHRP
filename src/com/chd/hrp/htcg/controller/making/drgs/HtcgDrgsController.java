package com.chd.hrp.htcg.controller.making.drgs;
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
import com.chd.hrp.htcg.entity.making.drgs.HtcgDrgs;
import com.chd.hrp.htcg.service.making.drgs.HtcgDrgsService;

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
public class HtcgDrgsController extends BaseController{
	private static Logger logger = Logger.getLogger(HtcgDrgsController.class);
	
	
	@Resource(name = "htcgDrgsService")
	private final HtcgDrgsService htcgDrgsService = null;
    
	
	// 维护页面跳转
	@RequestMapping(value = "/hrp/htcg/making/drgs/htcgDrgsMainPage", method = RequestMethod.GET)
	public String htcgDrgsMainPage(Model mode) throws Exception {

		return "hrp/htcg/making/drgs/htcgDrgsMain";

	}

	// 添加页面
	@RequestMapping(value = "/hrp/htcg/making/drgs/htcgDrgsAddPage", method = RequestMethod.GET)
	public String htcgDrgsAddPage(Model mode) throws Exception {

		return "hrp/htcg/making/drgs/htcgDrgsAdd";

	}
	 
	// 保存
	@RequestMapping(value = "/hrp/htcg/making/drgs/addHtcgDrgs", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addHtcgDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drgsJson ;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		mapVo.put("spell_code", StringTool.toPinyinShouZiMu(mapVo.get("drgs_name").toString())); 
		mapVo.put("wbx_code", StringTool.toWuBi(mapVo.get("drgs_name").toString()));
		try{
			
			drgsJson = htcgDrgsService.addHtcgDrgs(mapVo);
			
		}catch(Exception e){
			
			drgsJson= e.getMessage();
			
		}
		return JSONObject.parseObject(drgsJson);
		
	}

	// 查询
	@RequestMapping(value = "/hrp/htcg/making/drgs/queryHtcgDrgs", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryHtcgDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {

		String drgsJson ;
		
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		
		try{
			
			drgsJson = htcgDrgsService.queryHtcgDrgs(getPage(mapVo));
			
		}catch(Exception e){
			
			drgsJson= e.getMessage();
			
		}
		
		return JSONObject.parseObject(drgsJson);
		
	}
	
	//删除
	@RequestMapping(value = "/hrp/htcg/making/drgs/deleteHtcgDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleteHtcgDrgs(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		String drgsJson ;
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		
		for ( String id: paramVo.split(",")) {
			
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
			mapVo.put("group_id", SessionManager.getGroupId());
			mapVo.put("hos_id", SessionManager.getHosId());
			mapVo.put("copy_code", SessionManager.getCopyCode());
			
            mapVo.put("drgs_code", id);//实际实体类变量
            listVo.add(mapVo);
        }
		
		try{
			
			drgsJson = htcgDrgsService.deleteBatchHtcgDrgs(listVo);
			
		}catch(Exception e){
			
			drgsJson= e.getMessage();
			
		}
	   return JSONObject.parseObject(drgsJson);
			
	}
	
	
	// 修改页面跳转
	@RequestMapping(value = "/hrp/htcg/making/drgs/htcgDrgsUpdatePage", method = RequestMethod.GET)
	
	public String htcgDrgsUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        HtcgDrgs drgs =  htcgDrgsService.queryHtcgDrgsByCode(mapVo);
		mode.addAttribute("group_id", drgs.getGroup_id());
		mode.addAttribute("hos_id", drgs.getHos_id());
		mode.addAttribute("copy_code", drgs.getCopy_code());
		mode.addAttribute("drgs_code", drgs.getDrgs_code());
		mode.addAttribute("drgs_name", drgs.getDrgs_name());
		mode.addAttribute("drgs_type_code", drgs.getDrgs_type_code());
		mode.addAttribute("drgs_type_name", drgs.getDrgs_type_name());
		mode.addAttribute("spell_code", drgs.getSpell_code());
		mode.addAttribute("wbx_code", drgs.getWbx_code());
		mode.addAttribute("drgs_note", drgs.getDrgs_note());
		return "hrp/htcg/making/drgs/htcgDrgsUpdate";
	}
		
	// 修改保存
	@RequestMapping(value = "/hrp/htcg/making/drgs/updateHtcgDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updateHtcgDrgs(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String drgsJson ;
		mapVo.put("group_id", SessionManager.getGroupId());
		mapVo.put("hos_id", SessionManager.getHosId());
		mapVo.put("copy_code", SessionManager.getCopyCode());
		try{
			
			drgsJson = htcgDrgsService.updateHtcgDrgs(mapVo);
			
		}catch(Exception e){
			
			drgsJson= e.getMessage();
			
		}
		return JSONObject.parseObject(drgsJson);
	}
	
	// 导入
	@RequestMapping(value = "/hrp/htcg/making/drgs/importHtcgDrgs", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importHtcgDrgs(@RequestParam Map<String, Object> mapVo,Model mode) throws Exception {
 
		String drgsJson ;
		
		try {

			drgsJson = htcgDrgsService.importHtcgDrgs(mapVo);
		}
		catch (Exception e) {
			drgsJson= e.getMessage();
		}

		return JSONObject.parseObject(drgsJson);
	} 

}

