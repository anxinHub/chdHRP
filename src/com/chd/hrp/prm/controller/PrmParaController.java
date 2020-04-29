package com.chd.hrp.prm.controller;

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
import com.chd.hrp.prm.entity.PrmPara;
import com.chd.hrp.prm.service.PrmParaService;

@Controller
public class PrmParaController extends BaseController{
	private static Logger logger = Logger.getLogger(PrmParaController.class);
	
	
	@Resource(name = "prmParaService")
	private final PrmParaService prmParaService = null;
   
    
	/**
	*系统参数<BR>
	*维护页面跳转
	*/
	
	@RequestMapping(value = "/hrp/prm/prmpara/prmParaMainPage", method = RequestMethod.GET)
	public String prmParaMainPage(Model mode) throws Exception {

		return "hrp/prm/prmpara/prmParaMain";

	}
	/**
	*系统参数<BR>
	*维护页面跳转
	*/
	// 添加页面
	@RequestMapping(value = "/hrp/prm/prmpara/prmParaAddPage", method = RequestMethod.GET)
	public String prmParaAddPage(Model mode) throws Exception {

		return "hrp/prm/prmpara/prmParaAdd";

	}
	/**
	*系统参数<BR>
	*保存
	*/
	@RequestMapping(value = "/hrp/prm/prmpara/addPrmPara", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> addPrmPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		String prmParaJson = prmParaService.addPrmPara(mapVo);

		return JSONObject.parseObject(prmParaJson);
		
	}
	/**
	*系统参数<BR>
	*查询
	*/
	@RequestMapping(value = "/hrp/prm/prmpara/queryPrmPara", method = RequestMethod.POST)
	@ResponseBody
	
	public Map<String, Object> queryPrmPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		mapVo.put("group_id", SessionManager.getGroupId());
        mapVo.put("hos_id", SessionManager.getHosId());
        mapVo.put("copy_code", SessionManager.getCopyCode());
        mapVo.put("mod_code", SessionManager.getModCode());
        mapVo.put("is_stop", 0);
		String prmPara = prmParaService.queryPrmPara(getPage(mapVo));

		return JSONObject.parseObject(prmPara);
		
	}
	/**
	*系统参数<BR>
	*删除
	*/
	@RequestMapping(value = "/hrp/prm/prmpara/deletePrmPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deletePrmPara(@RequestParam(value="ParamVo") String paramVo, Model mode) throws Exception {
		
		List<Map<String, Object>> listVo= new ArrayList<Map<String,Object>>();
		for ( String id: paramVo.split(",")) {
			Map<String, Object> mapVo=new HashMap<String, Object>();
			
            mapVo.put("temp", id);//实际实体类变量
            listVo.add(mapVo);
        }
		String prmParaJson = prmParaService.deleteBatchPrmPara(listVo);
	   return JSONObject.parseObject(prmParaJson);
			
	}
	
	/**
	*系统参数<BR>
	*修改页面跳转
	*/
	@RequestMapping(value = "/hrp/prm/prmpara/prmParaUpdatePage", method = RequestMethod.GET)
	
	public String prmParaUpdatePage(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
        PrmPara prmPara = new PrmPara();
		prmPara = prmParaService.queryPrmParaByCode(mapVo);
		mode.addAttribute("para_code", prmPara.getPara_code());
		mode.addAttribute("para_name", prmPara.getPara_name());
		mode.addAttribute("group_id", prmPara.getGroup_id());
		mode.addAttribute("hos_id", prmPara.getHos_id());
		mode.addAttribute("copy_code", prmPara.getCopy_code());
		mode.addAttribute("mod_code", prmPara.getMod_code());
		mode.addAttribute("para_type", prmPara.getPara_type());
		mode.addAttribute("para_json", prmPara.getPara_json());
		mode.addAttribute("para_value", prmPara.getPara_value());
		mode.addAttribute("note", prmPara.getNote());
		mode.addAttribute("is_stop", prmPara.getIs_stop());
		return "hrp/prm/prmpara/prmParaUpdate";
	}
	/**
	*系统参数<BR>
	*修改保存
	*/	
	
	@RequestMapping(value = "/hrp/prm/prmpara/updatePrmPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> updatePrmPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
	   
		String prmParaJson = prmParaService.updatePrmPara(mapVo);
		
		return JSONObject.parseObject(prmParaJson);
	}
	/**
	*系统参数<BR>
	*导入
	*/
	
	@RequestMapping(value = "/hrp/prm/prmpara/importPrmPara", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> importPrmPara(@RequestParam Map<String, Object> mapVo, Model mode) throws Exception {
		
		String prmParaJson = prmParaService.importPrmPara(mapVo);
		
		return JSONObject.parseObject(prmParaJson);
	}

}
